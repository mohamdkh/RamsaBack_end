package ramsa.sid.entities;

import java.util.List;

public class Statistique {
	private String type;
	private List<Double> liststatistiques;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Double> getListstatistiques() {
		return liststatistiques;
	}
	public void setListstatistiques(List<Double> liststatistiques) {
		this.liststatistiques = liststatistiques;
	}

}
