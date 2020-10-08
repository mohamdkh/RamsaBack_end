package ramsa.sid.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import ramsa.sid.dao.SecteurComRepository;
import ramsa.sid.dao.SecteurHydroRepository;
import ramsa.sid.entities.AffectationComToHydraulique;
import ramsa.sid.entities.SecteurCommercial;
import ramsa.sid.entities.SecteurHydraulique;

@Service
public class SecComService {
	@Autowired
	SecteurHydroRepository SecteurHydrorepos;
	@Autowired
	SecteurComRepository SecteurComrepos;
	private String status;
	private List<SecteurCommercial> listSecteurCom;
	private List<AffectationComToHydraulique> Affectations;
	private AffectationComToHydraulique AffectationEntity;
	private Long value;
	public List<SecteurCommercial> listSecteursComPerHydro(String SecHydro,String etage){
		this.listSecteurCom=new ArrayList<SecteurCommercial>();
		(SecteurHydrorepos.findAll()).forEach(item->{
			if(item.getEtage().equals(etage) && item.getNom().equals(SecHydro)) {
				this.listSecteurCom= SecteurComrepos.ListSecteurComPerHydro(item.getId());
			}
		});
		return this.listSecteurCom;
	}
	public List<AffectationComToHydraulique> ListAffectationParSecteurCom(String secteurCom){
		Affectations=new ArrayList<AffectationComToHydraulique>();
	
		SecteurComrepos.ListSecteurCom(secteurCom).forEach(elem->{
			AffectationEntity=new AffectationComToHydraulique();
			value=elem.getId_etage();
			AffectationEntity.setIdEtage(value);
			AffectationEntity.setSecteurCom(secteurCom);
			AffectationEntity.setTauxAffectation((int)(elem.getTauxAffectHydroToCom()*100));
			AffectationEntity.setSecteurHydro(SecteurHydrorepos.findById(value).get().getNom());
			if(elem.getDateDesactivation()!=null) {
				AffectationEntity.setStatus("désactivé");
			}
			else 
				AffectationEntity.setStatus("activé");
			Affectations.add(AffectationEntity);
		});
		return Affectations;
	}
	public String StatusCommercial(String secteurCom) {
		SecteurComrepos.ListSecteurCom(secteurCom).forEach(elem->{
			if(elem.getDateDesactivation()!=null) {
				status="*Le secteur a été désactivé le "+elem.getDateDesactivation().toLocaleString();
			}
			else {
				status="*Le secteur a été activé le "+ elem.getDateActivation().toLocaleString();
			}
		});
		return status;
	}
	
	public boolean UpdateDateDesactivation(String secteur) {
		SecteurComrepos.DesactiveSecteur(secteur, new Date());
		return true;
	}
	public List<String> GetSecteurIndefinies(){
		return SecteurComrepos.ListSecteurPerStatus("unfinished");
	}
	public List<SecteurCommercial> GetSecteursParNom(String secteurCom){
		listSecteurCom=new ArrayList<SecteurCommercial>();
		SecteurComrepos.findAll().forEach(elem->{
			if(elem.getNomSecteurCom().equals(secteurCom) && elem.getDateDesactivation()==null) {
				if(elem.getStatus()!=null) {
					if(!elem.getStatus().equals("unfinished"))
						listSecteurCom.add(elem);
				}
				else {
					listSecteurCom.add(elem);
				}
			}
		});
		SecteurHydrorepos.findAll().forEach(secteur->{
			listSecteurCom.forEach(item->{
				Long id=item.getId_etage();
				if(secteur.getId().equals(id)) {
					item.setStatus(secteur.getNom());
				}
			});
		});
		return listSecteurCom;
		
	}
	public void AddSecteurCommercial(SecteurCommercial secteurCom) {
		SecteurCommercial secteur=new SecteurCommercial();
		secteur.setDateActivation(new Date());
		secteur.setNomSecteurCom(secteurCom.getNomSecteurCom());
		secteur.setTauxAffectHydroToCom(secteurCom.getTauxAffectHydroToCom());
		secteur.setStatus("underway");
		SecteurHydrorepos.findAll().forEach(elem->{
			if(elem.getNom().equals(secteurCom.getStatus())) {
				secteur.setId_etage(elem.getId());
			}
		});
		SecteurComrepos.save(secteur);
	}
	public void ValidationSecteurCom(String secteurCom) {
		SecteurComrepos.DeleteIntialSecteurCom(secteurCom);
		SecteurComrepos.UpdateStatusCommercial(secteurCom, "active");
		
	}
	public boolean CancelOperation() {
		SecteurComrepos.findAll().forEach(elem->{
			if(elem.getStatus().equals("underway")) {
				SecteurComrepos.delete(elem);
			}
		});
		return true;
	}
}
