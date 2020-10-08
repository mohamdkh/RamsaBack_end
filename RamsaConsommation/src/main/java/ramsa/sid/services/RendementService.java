package ramsa.sid.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ramsa.sid.dao.RendementRepository;
import ramsa.sid.dao.SecteurHydroRepository;
import ramsa.sid.entities.Rendement;

@Service
public class RendementService {
	@Autowired 
	private SecteurHydroRepository HydrauliqueRepos;
	@Autowired
	private RendementRepository repos;
	private Rendement Element;
	private List<Double> ListRendement;
	private List<String> listPeriode = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
			"12");
	public void insererRendement(String etage,String secteur,String periode,int annee,double rendement) {
		List<Long> listIdSecteur= HydrauliqueRepos.ListIDSecteurHydraulique(etage, secteur);
		Element=new Rendement();
		if(listIdSecteur.size()!=0) {
			listIdSecteur.forEach(id->{
				List<Rendement> list=repos.ExistList(id, annee, periode);
				if(list.size()!=0) {
					list.forEach(rendementElement->{
						rendementElement.setValeur(rendement);
						repos.save(rendementElement);
					});
				}
				else {
					Element.setAnnee(annee);
					Element.setIdSecteurHydrau(id);
					Element.setPeriode(periode);
					Element.setValeur(rendement);
					repos.save(Element);
				}
			});
			
		}
		
	}
	public double GetRendement(String etage,String secteur,String periode,int annee) {
		Element=new Rendement();
		List<Long> listIdSecteur= HydrauliqueRepos.ListIDSecteurHydraulique(etage, secteur);
		listIdSecteur.forEach(id->{
			List<Rendement> list=repos.ExistList(id, annee, periode);
			if(list.size()!=0) {
				list.forEach(elem->{
					Element.setValeur(elem.getValeur());
				});
			}
		});
		return Element.getValeur();
	}
	public List<Double> StatistiquesRendementSectoriel(String etage,String secteur,int annee){
		ListRendement=new ArrayList<Double>();
		List<Long> listIdSecteur= HydrauliqueRepos.ListIDSecteurHydraulique(etage, secteur);
		listPeriode.forEach(periode->{
			Element=new Rendement();
			listIdSecteur.forEach(id->{
			List<Rendement> list=repos.ExistList(id, annee, periode);
			if(list.size()!=0) {
				list.forEach(elem->{
					Element.setValeur(elem.getValeur());
				});
				}
			});
			ListRendement.add(Element.getValeur());
		});
		
		return ListRendement;
	}

}
