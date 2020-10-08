package ramsa.sid.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ramsa.sid.dao.ConsommationRepository;
import ramsa.sid.dao.RendementRepository;
import ramsa.sid.dao.SecteurComRepository;
import ramsa.sid.dao.SecteurHydroRepository;
import ramsa.sid.dao.TypeReleveeRepository;
import ramsa.sid.entities.ConsommationSectorielle;
import ramsa.sid.entities.SecteurCommercial;
import ramsa.sid.entities.SecteurHydraulique;
import ramsa.sid.entities.Statistique;
import ramsa.sid.entities.TypeRelevee;

@Service
public class StatistiqueService {
	@Autowired
	private SecteurHydroRepository SecteurHydrorepos;
	@Autowired
	private SecteurComRepository SecteurComrepos;
	@Autowired
	private ConsommationRepository consommationRepos;
	@Autowired
	private RendementRepository rendementRepos;
	@Autowired
	private TypeReleveeRepository typesRepos;

	private List<Statistique> listStatistiques;
	private List<Statistique> Statistiques;
	private List<Statistique> liststat;
	private Statistique StatistiqueElement;
	private Long value;
	private List<SecteurCommercial> listSecteurCom;
	private List<Double> listconsommation;
	private List<Double> listconsommationTotale;
	private List<SecteurHydraulique> SecteursHydros;
	private int count = 0;
	private List<String> listPeriode = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
			"12");
	private List<String> AnneesDispos;
	private double ConsomValue;
	private List<String> listTypes;

	public List<Statistique> statistiqueConsomSecteur(String etage, String secteurHydro, int annee) {
		listSecteurCom = new ArrayList<SecteurCommercial>();
		SecteurHydrorepos.findAll().forEach(secteur -> {
			if (secteur.getEtage().equals(etage) && secteur.getNom().equals(secteurHydro)) {
				value = secteur.getId();
			}
		});
		SecteurComrepos.ListSecteurComPerHydro(value).forEach(elem -> {
			listSecteurCom.add(elem);
		});
		listStatistiques = new ArrayList<Statistique>();
		listconsommationTotale = new ArrayList<Double>();
		for (int i = 0; i < 12; i++) {
			listconsommationTotale.add(0.0);
		}
		typesRepos.findAll().forEach(type -> {
			listconsommation = new ArrayList<Double>();
			count = 0;
			listPeriode.forEach(periode -> {
				ConsomValue = 0;
				listSecteurCom.forEach(secteurCom -> {
					try {
						double consommationTotal = consommationRepos.SumOfConsumptionSecteur(
								secteurCom.getNomSecteurCom(), periode, annee, String.valueOf(type.getId_type()),
								secteurCom.getTauxAffectHydroToCom());
						ConsomValue += consommationTotal;
					} catch (Exception e) {
						System.out.println(e.getStackTrace());
					}
				});
				ConsomValue = (Math.round(ConsomValue * 100)) / 100;
				listconsommation.add(ConsomValue);
				listconsommationTotale.set(count, (int) ConsomValue + listconsommationTotale.get(count));
				count++;
			});
			StatistiqueElement = new Statistique();
			StatistiqueElement.setType(type.getDescription());
			StatistiqueElement.setListstatistiques(listconsommation);
			listStatistiques.add(StatistiqueElement);
		});
		StatistiqueElement = new Statistique();
		StatistiqueElement.setType("Consommation totale");
		StatistiqueElement.setListstatistiques(listconsommationTotale);
		listStatistiques.add(StatistiqueElement);

		return listStatistiques;

	}

	public List<Statistique> statistiqueConsommateurs(String etage, String secteurHydraulique, String annee) {
		listSecteurCom = new ArrayList<SecteurCommercial>();
		SecteurHydrorepos.findAll().forEach(secteur -> {
			if (secteur.getEtage().equals(etage) && secteur.getNom().equals(secteurHydraulique)) {
				value = secteur.getId();
			}
		});
		SecteurComrepos.ListSecteurComPerHydro(value).forEach(elem -> {
			listSecteurCom.add(elem);
		});
		listStatistiques = new ArrayList<Statistique>();
		listconsommationTotale = new ArrayList<Double>();
		for (int i = 0; i < 12; i++) {
			listconsommationTotale.add(0.0);
		}
		typesRepos.findAll().forEach(type -> {
			listconsommation = new ArrayList<Double>();
			count = 0;
			listPeriode.forEach(periode -> {
				ConsomValue = 0;
				listSecteurCom.forEach(secteurCom -> {
					try {
						double consommationTotal = consommationRepos.SumOfClients(secteurCom.getNomSecteurCom(),
								periode, Integer.parseInt(annee), String.valueOf(type.getId_type()));
						ConsomValue += consommationTotal;
					} catch (Exception e) {
						System.out.println(e.getCause());
					}
				});
				ConsomValue = (Math.round(ConsomValue * 100)) / 100;
				listconsommation.add(ConsomValue);
				listconsommationTotale.set(count, (int) ConsomValue + listconsommationTotale.get(count));
				count++;
			});
			StatistiqueElement = new Statistique();
			StatistiqueElement.setType(type.getDescription());
			StatistiqueElement.setListstatistiques(listconsommation);
			listStatistiques.add(StatistiqueElement);
		});
		StatistiqueElement = new Statistique();
		StatistiqueElement.setType("Total");
		StatistiqueElement.setListstatistiques(listconsommationTotale);
		listStatistiques.add(StatistiqueElement);

		return listStatistiques;
	}

	public List<Statistique> statistiqueConsomEtage(String etage, String annee) {

		SecteursHydros = new ArrayList<SecteurHydraulique>();
		SecteursHydros = SecteurHydrorepos.ListSecteursHydrauliques(etage);
		SecteursHydros.forEach(secteur -> {
			listSecteurCom = new ArrayList<SecteurCommercial>();
			SecteurHydrorepos.findAll().forEach(sect -> {
				if (secteur.getEtage().equals(etage) && sect.getNom().equals(secteur.getNom())) {
					value = secteur.getId();
				}
			});
			SecteurComrepos.ListSecteurComPerHydro(value).forEach(elem -> {
				listSecteurCom.add(elem);
			});

		});
		listStatistiques = new ArrayList<Statistique>();
		listconsommationTotale = new ArrayList<Double>();
		for (int i = 0; i < 12; i++) {
			listconsommationTotale.add(0.0);
		}
		typesRepos.findAll().forEach(type -> {
			listconsommation = new ArrayList<Double>();
			count = 0;
			listPeriode.forEach(periode -> {
				ConsomValue = 0;
				listSecteurCom.forEach(secteurCom -> {
					try {
						double consommationTotal = consommationRepos.SumOfConsumptionSecteur(
								secteurCom.getNomSecteurCom(), periode, Integer.parseInt(annee),
								String.valueOf(type.getId_type()), secteurCom.getTauxAffectHydroToCom());
						ConsomValue += consommationTotal;
					} catch (Exception e) {
						System.out.println(e.getStackTrace());
					}
				});
				ConsomValue = (Math.round(ConsomValue * 100)) / 100;
				listconsommation.add(ConsomValue);
				listconsommationTotale.set(count, (int) ConsomValue + listconsommationTotale.get(count));
				count++;
			});
			StatistiqueElement = new Statistique();
			StatistiqueElement.setType(type.getDescription());
			StatistiqueElement.setListstatistiques(listconsommation);
			listStatistiques.add(StatistiqueElement);
		});
		StatistiqueElement = new Statistique();
		StatistiqueElement.setType("Consommation totale");
		StatistiqueElement.setListstatistiques(listconsommationTotale);
		listStatistiques.add(StatistiqueElement);

		return listStatistiques;
	}

	public List<Statistique> statistiqueAnneeConsomSecteur(String etage, String secteur) {
		System.out.println(secteur+"++"+etage);
		AnneesDispos = new ArrayList<String>();
		listStatistiques = new ArrayList<Statistique>();
		listSecteurCom = new ArrayList<SecteurCommercial>();
		SecteurHydrorepos.findAll().forEach(secteurHydro -> {
			if ((secteurHydro.getEtage()).equals(etage) && secteurHydro.getNom().equals(secteur)) {
				System.out.println(secteur+"++"+etage);
				value = secteurHydro.getId();
			}
		});
		SecteurComrepos.ListSecteurComPerHydro(value).forEach(elem -> {
			System.out.println(elem.getNomSecteurCom());
			listSecteurCom.add(elem);
		});
		listStatistiques = new ArrayList<Statistique>();
		listconsommationTotale = new ArrayList<Double>();
		AnneesDispos = consommationRepos.listAnnee();
		AnneesDispos.forEach(annee -> {
			listconsommationTotale.add(0.0);
		});
		typesRepos.findAll().forEach(type -> {
			listconsommation = new ArrayList<Double>();
			count = 0;
			AnneesDispos.forEach(annee -> {
				ConsomValue = 0;
				listPeriode.forEach(periode -> {
					listSecteurCom.forEach(secteurCom -> {
						try {
							double consommationTotal = consommationRepos.SumOfConsumptionSecteur(
									secteurCom.getNomSecteurCom(), periode, Integer.parseInt(annee), String.valueOf(type.getId_type()),
									secteurCom.getTauxAffectHydroToCom());
							ConsomValue += consommationTotal;
						} catch (Exception e) {
							System.out.println(e.getStackTrace());
						}
					});
					
				});
				ConsomValue = (Math.round(ConsomValue * 100)) / 100;
				listconsommation.add(ConsomValue);
				listconsommationTotale.set(count, (int) ConsomValue + listconsommationTotale.get(count));
				count++;
			});
			StatistiqueElement = new Statistique();
			StatistiqueElement.setType(type.getDescription());
			StatistiqueElement.setListstatistiques(listconsommation);
			listStatistiques.add(StatistiqueElement);
		});
		StatistiqueElement = new Statistique();
		StatistiqueElement.setType("Consommateurs annuelle Totale");
		StatistiqueElement.setListstatistiques(listconsommationTotale);
		listStatistiques.add(StatistiqueElement);

		return listStatistiques;
	}
	
	public boolean saveTypeReleve(TypeRelevee typeRelevee) {
		count=0;
		typesRepos.findAll().forEach(type->{
			if(type.getId_type()==typeRelevee.getId_type())
				count++; 
		});
		if(count!=0) {
			return false;
		}
		typesRepos.save(typeRelevee);
		return true;
	}

}
