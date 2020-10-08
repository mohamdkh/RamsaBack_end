package ramsa.sid.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ramsa.sid.entities.ConsommationSectorielle;
import ramsa.sid.entities.LoginModel;
import ramsa.sid.entities.Statistique;
import ramsa.sid.entities.TypeRelevee;
import ramsa.sid.services.ConsommationService;
import ramsa.sid.services.RendementService;
import ramsa.sid.services.StatistiqueService;

@CrossOrigin(origins = "*")
@RequestMapping("Consommation/")
@RestController
public class ConsommationController {
	@Autowired
	private ConsommationService service;
	@Autowired 
	private StatistiqueService statistiqueService;
	@Autowired
	private RendementService serviceRendement;
	private List<String> listErreurs;
	@PostMapping(value="UploadData")
	public List<String> UploadData(@RequestParam("file") MultipartFile file)  {
		listErreurs=new ArrayList<String>();
		try {
		listErreurs=service.UploadData(file.getInputStream());
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return listErreurs;
	}
	@GetMapping(path="GetConsommationData")
	public List<ConsommationSectorielle> GetConsommationData(@RequestParam(name = "periode") String periode,
															@RequestParam(name = "annee") String annee,
															@RequestParam(name = "etage") String etage,
															@RequestParam(name = "secteurHydraulique")  String secteurHydraulique) {
		return service.GetConsommationData(annee,periode,etage,secteurHydraulique);
		
	}
	
	@GetMapping(path="AnneesDisponibles")
	public List<String> GetAvailableYears(){
		return service.GetAvailableYears();
	}
	@GetMapping(path="statistiqueConsomSecteur")
	public List<Statistique> statistiqueConsomSecteur(	@RequestParam(name = "annee") String annee,
													@RequestParam(name = "etage") String etage,
													@RequestParam(name = "secteurHydro")  String secteurHydraulique){
		return statistiqueService.statistiqueConsomSecteur(etage,secteurHydraulique,Integer.parseInt(annee));
	}
	@GetMapping(path="statistiqueConsommateurs")
	public List<Statistique> statistiqueConsommateurs(	@RequestParam(name = "annee") String annee,
													@RequestParam(name = "etage") String etage,
													@RequestParam(name = "secteurHydro")  String secteurHydraulique){
		return statistiqueService.statistiqueConsommateurs(etage,secteurHydraulique,annee);
	}
	@GetMapping(path="statistiqueConsomEtage")
	public List<Statistique> statistiqueConsomEtage(	@RequestParam(name = "annee") String annee,
													@RequestParam(name = "etage") String etage){
		return statistiqueService.statistiqueConsomEtage(etage,annee);
	}
	@GetMapping(path="statistiqueAnneeConsomSecteur")
	public List<Statistique> statistiqueAnneeConsomSecteur(	@RequestParam(name = "Secteur") String Secteur,
													@RequestParam(name = "etage") String etage){
		return statistiqueService.statistiqueAnneeConsomSecteur(etage,Secteur);
	}
	@PostMapping(value="insererRendement")
	public void insererRendement(@RequestParam(name = "periode") String periode,
									@RequestParam(name = "annee") String annee,
									@RequestParam(name = "etage") String etage,
									@RequestParam(name = "secteur")  String secteurHydraulique,
									@RequestParam(name = "rendement")  String rendement) {
		serviceRendement.insererRendement(etage, secteurHydraulique, periode, Integer.parseInt(annee), Double.parseDouble(rendement));
	
	}
	@GetMapping(path="GetRendement")
	public double GetRendement(@RequestParam(name = "periode") String periode,
									@RequestParam(name = "annee") String annee,
									@RequestParam(name = "etage") String etage,
									@RequestParam(name = "secteur")  String secteurHydraulique) {
		return serviceRendement.GetRendement(etage, secteurHydraulique, periode, Integer.parseInt(annee));
	}
	@GetMapping(path="StatistiquesRendementSectoriel")
	public List<Double> StatistiquesRendementSectoriel(@RequestParam(name = "annee") String annee,
																@RequestParam(name = "etage") String etage,
																@RequestParam(name = "secteur")  String secteurHydraulique) {
		return serviceRendement.StatistiquesRendementSectoriel(etage, secteurHydraulique, Integer.parseInt(annee));
	}
	@RequestMapping(value="/saveTypeReleve", method= RequestMethod.POST)
	public Boolean  saveTypeReleve(@RequestBody TypeRelevee typeRelevee) {
		return  statistiqueService.saveTypeReleve( typeRelevee);
	}
	@GetMapping(path="/GetTypes")
	public List<TypeRelevee> GetTypes(){
		return service.GetTypes();
	}
	@RequestMapping(value="/DeleteType", method= RequestMethod.POST)
	public Boolean  DeleteType(@RequestBody TypeRelevee typeRelevee) {
		return  service.DeleteType(typeRelevee);
	}
}
