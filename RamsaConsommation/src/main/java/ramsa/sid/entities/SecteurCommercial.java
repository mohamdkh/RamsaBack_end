package ramsa.sid.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class SecteurCommercial {
	@Id
	@GeneratedValue(
		    strategy= GenerationType.AUTO,
		    generator="native"
		)
	@GenericGenerator(
		    name = "native",
		    strategy = "native"
		)
	private long id;
	private long id_etage;
	 private String NomSecteurCom;
	 private double TauxAffectHydroToCom;
	 @JsonFormat(pattern="yyyy-MM-dd")
	private Date dateActivation;
	 @JsonFormat(pattern="yyyy-MM-dd")
	private Date dateDesactivation;
	 private String status;
	public long getId_etage() {
		return id_etage;
	}
	public void setId_etage(long id_etage) {
		this.id_etage = id_etage;
	}
	public String getNomSecteurCom() {
		return NomSecteurCom;
	}
	public void setNomSecteurCom(String nomSecteurCom) {
		NomSecteurCom = nomSecteurCom;
	}
	public double getTauxAffectHydroToCom() {
		return TauxAffectHydroToCom;
	}
	public void setTauxAffectHydroToCom(double tauxAffectHydroToCom) {
		TauxAffectHydroToCom = tauxAffectHydroToCom;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
