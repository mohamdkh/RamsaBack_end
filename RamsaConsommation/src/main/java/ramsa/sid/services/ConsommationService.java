package ramsa.sid.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ramsa.sid.dao.ConsommationRepository;
import ramsa.sid.dao.SecteurComRepository;
import ramsa.sid.dao.SecteurHydroRepository;
import ramsa.sid.dao.TypeReleveeRepository;
import ramsa.sid.entities.Consommation;
import ramsa.sid.entities.ConsommationSectorielle;
import ramsa.sid.entities.SecteurCommercial;
import ramsa.sid.entities.TypeRelevee;

@Service
public class ConsommationService {
	@Autowired
	private ConsommationRepository repos;
	@Autowired
	private SecteurComRepository SecteurComRepos;
	@Autowired
	private SecteurHydroRepository SecteurHydroRepos;
	@Autowired 
	private TypeReleveeRepository typeReleveeRepos;
	private ConsommationSectorielle consommationElement;
	private ConsommationSectorielle ConsommationTotale;
	private List<String> listTypes;
	private List<String> listErreurs;
	private List<String> listsecteurs;
	private List<Consommation> listConsommation;
	private List<ConsommationSectorielle> ConsommationSectTolist;
	private List<ConsommationSectorielle> ConsommationSectTolist2;
	private List<ConsommationSectorielle> listConsommationSect;
	private List<SecteurCommercial> listSecteurCom;
	private int drapo1;
	private List<Integer> listAnnee;
	private List<SecteurCommercial> AllSecteurCom;
	private List<String> listPeriode = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
			"12");
	private List<String> listPeriode2 ; 
	// *Cas Admin
	private ConsommationSectorielle consommatinAdmin;

	public List<String> UploadData(InputStream inputStream) {
		ConsommationSectTolist=new ArrayList<ConsommationSectorielle>();
		listErreurs = new ArrayList<String>();
		AllSecteurCom=new ArrayList<SecteurCommercial>();
		listPeriode2=new ArrayList<String>();
		AllSecteurCom=SecteurComRepos.findAll();
		this.CsvToConsommations(inputStream);
		listAnnee.forEach(year->{
			listPeriode2.forEach(periode->{
				listsecteurs.forEach(secteur->{
					listConsommationSect=new ArrayList<ConsommationSectorielle>();
					this.CalculConsommationParSecteur(year, periode,secteur);
					ConsommationSectTolist.addAll(listConsommationSect);
				});
			});
		});
		Set<String> mySet2 = new HashSet<String>(listErreurs);
		listErreurs = new ArrayList<String>(mySet2);
		if(listErreurs.size()!=0) 
			listErreurs.forEach(secteurIndefinie->{
				SecteurCommercial secteurCom=new SecteurCommercial();
				secteurCom.setNomSecteurCom(secteurIndefinie);
				secteurCom.setStatus("unfinished");
				secteurCom.setDateActivation(new Date());
				SecteurComRepos.save(secteurCom);
			});
		//ConsommationSectTolist.forEach(elem->System.out.println(elem.getSecteursCom()+"++"+elem.getType()+"++"+elem.getTotalConsom()));
		repos.saveAll(ConsommationSectTolist);
		return listErreurs;
	}
	private void CalculConsommationParSecteur(int annee,String periode,String secteur) {
		try {
		Calendar dateActuel = Calendar.getInstance();
		dateActuel.set(annee, Integer.parseInt(periode) - 1, 15);
		int nombreJrsMois = dateActuel.getActualMaximum(Calendar.DATE);
		listTypes.forEach(type->{
			consommationElement=new ConsommationSectorielle();
			listConsommation.forEach(consom->{
				long diff = Duration.between(consom.getDT_RLV1().toInstant(), consom.getDT_RLV2().toInstant()).toDays();
				boolean isAdmin=consom.getANNEE()==annee && consom.getPERIODE().equals(periode) && 
						(consom.getLOC() + "-" + consom.getSEC()).equals(secteur) && consom.getType().equals(type);
				boolean isother=consom.getANNEE()==annee && consom.getPERIODE().equals(periode) && 
						(consom.getLOC() + "-" + consom.getSEC()).equals(secteur) &&
						consom.getType().equals(type) && Integer.parseInt(type)!=2;
					if(isother) {
						consommationElement.setDureeMoy(consommationElement.getDureeMoy()+(double) diff);
						consommationElement.setNbrConsom(consommationElement.getNbrConsom() + 1);
						consommationElement.setConsommationBrut(consom.getCONSO() + consommationElement.getConsommationBrut());
						consommationElement.setTotalConsom(consommationElement.getTotalConsom() + ((double) consom.getCONSO() / diff) * nombreJrsMois);
					}
					else if(isAdmin) {
					consommationElement.setDureeMoy(consommationElement.getDureeMoy() +(double) diff);
					
					consommationElement.setConsommationBrut(consom.getCONSO() + consommationElement.getConsommationBrut());
					consommationElement.setNbrConsom(consommationElement.getNbrConsom() + 1);
					consommationElement.setTotalConsom(
							consommationElement.getTotalConsom() + ((double) consom.getCONSO() / diff) * 90);
					
				}
			});
			if(consommationElement.getNbrConsom()!=0) {
				consommationElement.setDureeMoy(consommationElement.getDureeMoy()/consommationElement.getNbrConsom());
				consommationElement.setAnnee(annee);
				consommationElement.setPeriode(periode);
				consommationElement.setType(type);
				consommationElement.setSecteursCom(secteur);
				if(Integer.parseInt(consommationElement.getType())==2)
					InsertConsommationAdmin(consommationElement);
				else
					listConsommationSect.add(consommationElement);
			}
			
		});
		
			drapo1=0;
			AllSecteurCom.forEach(secteurCom->{
				if(secteurCom.getNomSecteurCom().equals(secteur) && secteurCom.getDateDesactivation()==null ) {
					drapo1=1;
				}
				else if(secteurCom.getNomSecteurCom().equals(secteur) && secteurCom.getDateDesactivation()!=null) {
					Date date = new Date(consommationElement.getAnnee() + "/" + consommationElement.getPeriode() + "/28");
					if (secteurCom.getDateDesactivation().before(date)) {
						drapo1 = 1;
					} 
				}
			});
			if(drapo1==0) {
				listErreurs.add(secteur);
			}
		}catch(Exception e) {
			System.out.println("error in CalculConsommationParSecteur"+e.getCause()+"++"+e.getStackTrace());
		}
	}

	private void InsertConsommationAdmin(ConsommationSectorielle consommatinA) {
		for (int i = 0; i < 3; i++) {
			consommatinAdmin = new ConsommationSectorielle();
			consommatinAdmin.setConsommationBrut(consommatinA.getConsommationBrut() / 3);
			consommatinAdmin.setDureeMoy(consommatinA.getDureeMoy() / 3);
			consommatinAdmin.setNbrConsom(consommatinA.getNbrConsom());
			consommatinAdmin.setSecteursCom(consommatinA.getSecteursCom());
			consommatinAdmin.setTotalConsom(consommatinA.getTotalConsom() / 3);
			consommatinAdmin.setType("2");
			consommatinAdmin.setAnnee(consommatinA.getAnnee());
			int value = (Integer.parseInt(consommatinA.getPeriode()) - i);
			listPeriode.forEach(p->{
				if(value==Integer.parseInt(p)) {
					consommatinAdmin.setPeriode(p);
				}
			});
			listConsommationSect.add(consommatinAdmin);
		
		}

	}

	public List<ConsommationSectorielle> GetConsommationData(String annee, String periode, String etage,
			String secteurHydro) {
		ConsommationTotale=new ConsommationSectorielle();
		listSecteurCom = new ArrayList<SecteurCommercial>();
		listsecteurs = new ArrayList<String>();
		listConsommationSect = new ArrayList<ConsommationSectorielle>();
		List<Long> listValues=new ArrayList<Long>();
		listValues=SecteurHydroRepos.ListIDSecteurHydraulique("%"+etage+"%","%"+secteurHydro+"%");
		listValues.forEach(valueElement->{
			SecteurComRepos.ListSecteurComPerHydro(valueElement).forEach(elem -> {
				listSecteurCom.add(elem);
			});			
		});
		listSecteurCom.forEach(item -> {
			String secteur = item.getNomSecteurCom();
			listsecteurs.add(secteur);
		});
		Set<String> mySet = new HashSet<String>(listsecteurs);
		listsecteurs = new ArrayList<String>(mySet);
		listsecteurs.forEach(elem->{
			System.out.println(elem);
		});
		List<TypeRelevee> TYPES=typeReleveeRepos.findAll();
		TYPES.forEach(type->{
			ConsommationSectTolist2=new  ArrayList<ConsommationSectorielle>();
			consommationElement=new ConsommationSectorielle();
			listsecteurs.forEach(secteur -> {
				List<ConsommationSectorielle> list=repos.ListConsommationparType(secteur,String.valueOf(type.getId_type()), "%"+periode+"%", Integer.parseInt(annee));
				ConsommationSectTolist2.addAll(list);
			});
			ConsommationSectTolist2.forEach(consomSecteur->{
				consommationElement.setConsommationBrut(consommationElement.getConsommationBrut()+consomSecteur.getConsommationBrut());
				consommationElement.setTotalConsom((int)consomSecteur.getTotalConsom()+(int)consommationElement.getTotalConsom());
				consommationElement.setDureeMoy(consommationElement.getDureeMoy()+consomSecteur.getDureeMoy());
				consommationElement.setNbrConsom(consommationElement.getNbrConsom()+consomSecteur.getNbrConsom());
			});
			consommationElement.setType(type.getDescription());
			if(ConsommationSectTolist2.size()!=0)
				consommationElement.setDureeMoy(consommationElement.getDureeMoy()/ConsommationSectTolist2.size());
			ConsommationTotale.setConsommationBrut(ConsommationTotale.getConsommationBrut()+consommationElement.getConsommationBrut());
			ConsommationTotale.setTotalConsom((int)(consommationElement.getTotalConsom()+ConsommationTotale.getTotalConsom()));
			ConsommationTotale.setDureeMoy(ConsommationTotale.getDureeMoy()+consommationElement.getDureeMoy());
			ConsommationTotale.setNbrConsom(ConsommationTotale.getNbrConsom()+consommationElement.getNbrConsom());
			listConsommationSect.add(consommationElement);
		});
		if(TYPES.size()!=0)
			ConsommationTotale.setDureeMoy(ConsommationTotale.getDureeMoy()/listConsommationSect.size());
		ConsommationTotale.setType("Total");
		listConsommationSect.add(ConsommationTotale);
		double scale=Math.pow(10, 2);
		listConsommationSect.forEach(item->{
			item.setDureeMoy((Math.round(item.getDureeMoy()*scale))/scale);
		});
		return listConsommationSect;
	}

	private void CsvToConsommations(InputStream inputStream) {

		listAnnee=new  ArrayList<Integer>();
		listTypes = new ArrayList<String>();
		listConsommation = new ArrayList<Consommation>();
		listsecteurs = new ArrayList<String>();
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				Consommation consom = new Consommation(csvRecord.get("TYPE"), csvRecord.get("LOC"),
						csvRecord.get("CAT"), csvRecord.get("SEC"), csvRecord.get("TRN"), csvRecord.get("ORD"),
						csvRecord.get("POLICE"), csvRecord.get("PERIODE"), Integer.parseInt(csvRecord.get("ANNEE")),
						csvRecord.get("DT_RLV1"), csvRecord.get("DT_RLV2"), Integer.parseInt(csvRecord.get("CONSO")));
				listConsommation.add(consom);
				listsecteurs.add(consom.getLOC() + "-" + consom.getSEC());
				listTypes.add(consom.getType());
				listAnnee.add(consom.getANNEE());
				listPeriode2.add(consom.getPERIODE());
			}

		} catch (Exception e) {

			System.out.println("Error in CsvToConsommations"+e.getCause()+"++"+e.getStackTrace());
		}
		Set<String> mySet = new HashSet<String>(listsecteurs);
		listsecteurs = new ArrayList<String>(mySet);
		Set<String> mySet1 = new HashSet<String>(listTypes);
		listTypes = new ArrayList<String>(mySet1);
		Set<Integer> mySet2 = new HashSet<Integer>(listAnnee);
		listAnnee = new ArrayList<Integer>(mySet2);
		Set<String> mySet3 = new HashSet<String>(listPeriode2);
		listPeriode2 = new ArrayList<String>(mySet3);
		System.out.println(listConsommation.size());
	}

	public List<String> GetAvailableYears() {
		return repos.listAnnee();
	}
	public List<TypeRelevee> GetTypes(){
		return typeReleveeRepos.findAll();
	}
	public Boolean DeleteType(TypeRelevee typeRelevee) {
		 typeReleveeRepos.deleteById(typeRelevee.getId_type());
		 return true;
	}
}
