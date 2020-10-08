package ramsa.sid.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ramsa.sid.dao.SecteurComRepository;
import ramsa.sid.dao.SecteurHydroRepository;
import ramsa.sid.entities.SecteurCommercial;
import ramsa.sid.entities.SecteurHydraulique;

@Service
public class SecHydroService {
	@Autowired
	SecteurHydroRepository repos;
	@Autowired
	SecteurComRepository reposCom;
	private int drapo;
	private List<SecteurHydraulique> listSecteurHydro;
	private String status;
	private List<String> listSecteurs;
	private Long value;
	private List<SecteurCommercial> listSecteurCom;
	public List<String> ListEtage(){
		return repos.ListEtages();
	}
	public List<SecteurHydraulique> listSecteursHydrauliques(String etage){
		return repos.ListSecteursHydrauliques(etage);
	}
	public String AddSecteurHydralique(SecteurHydraulique secteurHydro) {
		drapo=0;
		listSecteurHydro=new ArrayList<SecteurHydraulique>();
		listSecteurHydro=repos.ListSecteursHydrauliques(secteurHydro.getEtage());
		if(listSecteurHydro.size()==0) {
			secteurHydro.setDateActivation(new Date());
			repos.save(secteurHydro);
			return "";
		}
		else {
			listSecteurHydro.forEach(elem->{
				if(elem.getDateDesactivation()==null && elem.getNom().equals(secteurHydro.getNom())) {
					drapo=1;
				}
				else if(elem.getDateDesactivation()!=null && elem.getNom().equals(secteurHydro.getNom())) {
					elem.setDateDesactivation(null);
					repos.save(elem);
				}
			});
			if(drapo==1) {
				return "le secteur Hydraulique existe déjà";
			}
			else {
				secteurHydro.setDateActivation(new Date());
				repos.save(secteurHydro);
				return "";
			}
		}
	}
	public String StatusHydraulique(String etage,String secteur) {
		repos.ListSecteursHydrauliques(etage).forEach(elem->{
			if(elem.getNom().equals(secteur)) {
				if(elem.getDateDesactivation()!=null)
					status="*Le secteur a été désactivé le "+elem.getDateDesactivation().toLocaleString();
				else
					status="*Le secteur a été activé le "+ elem.getDateActivation().toLocaleString();
			}
		});
		return status;
	}
	
	public boolean UpdateDateDesactivation(String secteur) {
		listSecteurCom=new ArrayList<SecteurCommercial>();
		repos.UpdateStatusHydro(secteur, new Date());
		repos.findAll().forEach(elem->{
			if(elem.getNom().equals(secteur)) {
				value=elem.getId();
			}
		});
		listSecteurCom=reposCom.ListSecteurComPerHydro(value);
		if(listSecteurCom.size()!=0)
			listSecteurCom.forEach(item->{
				reposCom.DesactiveSecteur(item.getNomSecteurCom(), new Date());
			});
		return true;
	}
	public List<String> GetSecteurHydroActive(){
		listSecteurs=new ArrayList<String>();
		repos.findAll().forEach(elem->{
			if(elem.getDateDesactivation()==null) {
				listSecteurs.add(elem.getNom());
			}
		});
		return listSecteurs;
	}
	public List<String> AllSecteurHydroParEtage(String etage){
		listSecteurs=new ArrayList<String>();
		repos.findAll().forEach(elem->{
			if(elem.getEtage().equals(etage))
				listSecteurs.add(elem.getNom());
		});
		return listSecteurs;
	}
	public List<String> getSecteurHydrauliquesActivesApresDate(String etage,String annee,String periode){
		listSecteurs=new ArrayList<String>();
		String date=annee+"/"+periode+"/29";
		Date dateSearched=new Date(date);
		repos.findAll().forEach(elem->{
			if(elem.getEtage().equals(etage) && elem.getDateDesactivation()!=null) {
				if(elem.getDateDesactivation().after(dateSearched))
							listSecteurs.add(elem.getNom());
			}
			else if(elem.getEtage().equals(etage)) {
				listSecteurs.add(elem.getNom());
			}
		});
		return listSecteurs;
	}
	
}
