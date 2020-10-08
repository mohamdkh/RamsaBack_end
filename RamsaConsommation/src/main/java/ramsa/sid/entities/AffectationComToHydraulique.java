package ramsa.sid.entities;

public class AffectationComToHydraulique {
	 private String SecteurHydro;
	 private String SecteurCom;
	 private int tauxAffectation;
	 private Long idEtage;
	 public Long getIdEtage() {
		return idEtage;
	}
	public void setIdEtage(Long idEtage) {
		this.idEtage = idEtage;
	}
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSecteurHydro() {
		return SecteurHydro;
	}
	public void setSecteurHydro(String secteurHydro) {
		SecteurHydro = secteurHydro;
	}
	public String getSecteurCom() {
		return SecteurCom;
	}
	public void setSecteurCom(String secteurCom) {
		SecteurCom = secteurCom;
	}
	public int getTauxAffectation() {
		return tauxAffectation;
	}
	public void setTauxAffectation(int tauxAffectation) {
		this.tauxAffectation = tauxAffectation;
	}
}
