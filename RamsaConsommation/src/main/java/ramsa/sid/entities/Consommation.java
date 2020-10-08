package ramsa.sid.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class Consommation {
	private String LOC ; 
	private String CAT ; 
	private String SEC ; 
	private String TRN ; 
	private String ORD ; 
	private String Type;
	private String POLICE ;
	private String PERIODE ; 
	private  int ANNEE ;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date  DT_RLV1 ; 
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date  DT_RLV2 ; 
	private int CONSO ;
	
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getLOC() {
		return LOC;
	}
	public void setLOC(String lOC) {
		LOC = lOC;
	}
	public Consommation(String type,String lOC, String cAT, String sEC, String tRN, String oRD, String pOLICE, String pERIODE,
			int aNNEE, String dT_RLV1, String dT_RLV2, int cONSO) {
		super();
		String date1=dT_RLV1.substring(3, 5)+"/"+dT_RLV1.substring(0, 2)+"/"+dT_RLV1.substring(6);
		String date2=dT_RLV2.substring(3, 5)+"/"+dT_RLV2.substring(0, 2)+"/"+dT_RLV2.substring(6);
		Type=type;
		LOC = lOC;
		CAT = cAT;
		SEC = sEC;
		TRN = tRN;
		ORD = oRD;
		POLICE = pOLICE;
		PERIODE = pERIODE;
		ANNEE = aNNEE;
		DT_RLV1 =new Date(date1);
		DT_RLV2 = new Date(date2);
		CONSO = cONSO;
	}
	public String getCAT() {
		return CAT;
	}
	public void setCAT(String cAT) {
		CAT = cAT;
	}
	public String getSEC() {
		return SEC;
	}
	public void setSEC(String sEC) {
		SEC = sEC;
	}
	public String getTRN() {
		return TRN;
	}
	public void setTRN(String tRN) {
		TRN = tRN;
	}
	public String getORD() {
		return ORD;
	}
	public void setORD(String oRD) {
		ORD = oRD;
	}
	public String getPOLICE() {
		return POLICE;
	}
	public void setPOLICE(String pOLICE) {
		POLICE = pOLICE;
	}
	public String getPERIODE() {
		return PERIODE;
	}
	public void setPERIODE(String pERIODE) {
		PERIODE = pERIODE;
	}
	public int getANNEE() {
		return ANNEE;
	}
	public void setANNEE(int aNNEE) {
		ANNEE = aNNEE;
	}
	public Date getDT_RLV1() {
		return DT_RLV1;
	}
	public void setDT_RLV1(Date dT_RLV1) {
		DT_RLV1 = dT_RLV1;
	}
	public Date getDT_RLV2() {
		return DT_RLV2;
	}
	public void setDT_RLV2(Date dT_RLV2) {
		DT_RLV2 = dT_RLV2;
	}
	public int getCONSO() {
		return CONSO;
	}
	public void setCONSO(int cONSO) {
		CONSO = cONSO;
	}
}
