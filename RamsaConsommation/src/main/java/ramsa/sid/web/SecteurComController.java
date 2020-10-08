package ramsa.sid.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jdk.jfr.BooleanFlag;
import ramsa.sid.entities.AffectationComToHydraulique;
import ramsa.sid.entities.SecteurCommercial;
import ramsa.sid.entities.SecteurHydraulique;
import ramsa.sid.services.SecComService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("SecteurCommercial/")
public class SecteurComController {
	@Autowired
	private SecComService service;
	 @GetMapping(path = "ListSecteursCommerciaux")
	public List<SecteurCommercial> listSecteursComPerHydro(@RequestParam(name = "Etage") String etage, @RequestParam(name = "Nom") String secteurHydro){
		
		return service.listSecteursComPerHydro(secteurHydro, etage);
		
	}
	 @GetMapping(path = "ListAffectations")
	public List<AffectationComToHydraulique> ListAffectations(@RequestParam(name = "secteurCom") String secteurCom){
		return service.ListAffectationParSecteurCom(secteurCom);
	}
	 @GetMapping(path = "StatusCommercial")
		public String StatusCommercial( @RequestParam(name = "secteurCom") String secteurCom){
		 return service.StatusCommercial(secteurCom);
	 }
	 @GetMapping(path="DesactiverSecteur")
	 public boolean UpdateDateDesactivation(@RequestParam(name="secteurCom") String secteurCom) {
		 System.out.println(secteurCom);
		 service.UpdateDateDesactivation(secteurCom);
		 return true;
	 }
	 @GetMapping(path="SecteurIndefinies")
	 public List<String> GetSecteurIndefinies() {
		 return service.GetSecteurIndefinies();
	 }
	 @GetMapping(path="GetSecteurComPerNom")
	 public List<SecteurCommercial> GetSecteurComPerNom(@RequestParam(name = "secteurCom") String secteurCom) {
		 return service.GetSecteursParNom(secteurCom);
	 }
	 @PostMapping(path="AddSecteurCom")
	 public void AddSecteurCommercial(@RequestBody SecteurCommercial secteurCom) {
		 service.AddSecteurCommercial(secteurCom);	
	 }
	 @GetMapping(path="ValiderSecteur")
	 public void ValidationSecteurCom(@RequestParam(name = "secteurCom") String secteurCom) {
		 service.ValidationSecteurCom(secteurCom);
	 }
	 @GetMapping(path="CancelOperation")
	 public boolean  CancelOperation() {
		 return service.CancelOperation();
	 }
	 
}
