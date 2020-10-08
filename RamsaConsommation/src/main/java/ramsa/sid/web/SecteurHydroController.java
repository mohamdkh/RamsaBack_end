package ramsa.sid.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ramsa.sid.entities.SecteurHydraulique;
import ramsa.sid.services.SecHydroService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("SecteurHydraulique/")
public class SecteurHydroController {
	
	@Autowired
	private SecHydroService service;
	 @GetMapping(path = "ListEtage")
	 public List<String> getEtages(){
		 return service.ListEtage();
	 }
	 @GetMapping(path = "ListSecteursHydrauliques/{etage}")
	 public List<SecteurHydraulique> listSecteursHydrauliques(@PathVariable("etage") String etage){
		 return service.listSecteursHydrauliques(etage);
	 }
	 @RequestMapping(value="/AddSecteurHydraulique", method= RequestMethod.POST)
	 public String  AddSecteurHydraulique(@RequestBody SecteurHydraulique secteurHydro) {
			return service.AddSecteurHydralique(secteurHydro);
		}
	 @GetMapping(path = "StatusHydraulique")
		public String StatusHydraulique(@RequestParam(name = "Etage") String etage, @RequestParam(name = "Nom") String secteurHydro){
		System.out.println(etage+"++"+secteurHydro);
		 return service.StatusHydraulique(etage,secteurHydro);
	 }
	 @GetMapping(path="DesactiverSecteur")
	 public boolean UpdateDateDesactivation(@RequestParam(name="secteurHydro") String secteurHydro) {
		 service.UpdateDateDesactivation(secteurHydro);
		 return true;
	 }
	 @GetMapping(path="SecteurHydroActive")
	 public List<String> GetSecteurHydroActive() {
		 return service.GetSecteurHydroActive();
	 }
	 @GetMapping(path="AllSecteurHydroParEtage")
	 public List<String> AllSecteurHydroParEtage(@RequestParam(name="etage") String etage) {
		 return service.AllSecteurHydroParEtage(etage);
	 }
	 @GetMapping(path="getSecteurHydrauliquesActivesApresDate")
	 public List<String> getSecteurHydrauliquesActivesApresDate(@RequestParam(name="etage") String etage,
			 													@RequestParam(name="annee") String annee,
			 													@RequestParam(name="periode") String periode) {
		 return service.getSecteurHydrauliquesActivesApresDate(etage,annee,periode);
	 }
	 
}
