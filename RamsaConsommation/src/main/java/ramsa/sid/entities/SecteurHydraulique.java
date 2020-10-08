package ramsa.sid.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class SecteurHydraulique {
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
	 public Long getId() {
		return id;
	}
	private String Etage;
	 private String Nom;
	 @JsonFormat(pattern="yyyy-MM-dd")
	 private Date dateActivation;
	 @JsonFormat(pattern="yyyy-MM-dd")
	 private Date dateDesactivation;
	public String getEtage() {
		return Etage;
	}
	public void setEtage(String etage) {
		Etage = etage;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public Date getDateActivation() {
		return dateActivation;
	}
	public void setDateActivation(Date dateActivation) {
		this.dateActivation = dateActivation;
	}
	public Date getDateDesactivation() {
		return dateDesactivation;
	}
	public void setDateDesactivation(Date dateDesactivation) {
		this.dateDesactivation = dateDesactivation;
	}
	 

}
