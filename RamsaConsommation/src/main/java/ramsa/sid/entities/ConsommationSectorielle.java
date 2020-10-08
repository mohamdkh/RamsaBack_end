package ramsa.sid.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity

public class ConsommationSectorielle {

	@Id
	@GeneratedValue(
		    strategy= GenerationType.AUTO,
		    generator="native"
		)
	@GenericGenerator(
		    name = "native",
		    strategy = "native"
		)
	private Long id;
	private int NbrConsom;
	private String SecteursCom;
	private int annee;
	private String periode;
	private double TotalConsom;
	private int consommationBrut;
	private double dureeMoy;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getConsommationBrut() {
		return consommationBrut;
	}
	public void setConsommationBrut(int consommationBrut) {
		this.consommationBrut = consommationBrut;
	}
	public int getNbrConsom() {
		return NbrConsom;
	}
	public void setNbrConsom(int nbrConsom) {
		NbrConsom = nbrConsom;
	}
	public String getSecteursCom() {
		return SecteursCom;
	}
	public void setSecteursCom(String secteursCom) {
		SecteursCom = secteursCom;
	}
	public int getAnnee() {
		return annee;
	}
	public void setAnnee(int annee) {
		this.annee = annee;
	}
	public String getPeriode() {
		return periode;
	}
	public void setPeriode(String periode) {
		this.periode = periode;
	}
	public double getTotalConsom() {
		return TotalConsom;
	}
	public void setTotalConsom(double totalConsom) {
		TotalConsom = totalConsom;
	}
	public double getDureeMoy() {
		return dureeMoy;
	}
	public void setDureeMoy(double dureeMoy) {
		this.dureeMoy = dureeMoy;
	}
	
}
