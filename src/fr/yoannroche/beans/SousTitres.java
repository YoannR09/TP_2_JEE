package fr.yoannroche.beans;

import java.util.List;

public class SousTitres {
	
	private int id;
	private int numeroLigne;
	private String temps;
	private String ligne1;
	private String ligne2;
	private String traduction1;
	private String traduction2;
	private int videoId;
	private List<SousTitres> sts;
	
	
	
	public List<SousTitres> getSts() {
		return sts;
	}
	public void setSts(List<SousTitres> sts) {
		this.sts = sts;
	}
	public int getVideoId() {
		return videoId;
	}
	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumeroLigne() {
		return numeroLigne;
	}
	public void setNumeroLigne(int numeroLigne) {
		this.numeroLigne = numeroLigne;
	}
	public String getTemps() {
		return temps;
	}
	public void setTemps(String temps) {
		this.temps = temps;
	}
	public String getLigne1() {
		return ligne1;
	}
	public void setLigne1(String ligne1) {
		this.ligne1 = ligne1;
	}
	public String getLigne2() {
		return ligne2;
	}
	public void setLigne2(String ligne2) {
		this.ligne2 = ligne2;
	}
	public String getTraduction1() {
		return traduction1;
	}
	public void setTraduction1(String traduction1) {
		this.traduction1 = traduction1;
	}
	public String getTraduction2() {
		return traduction2;
	}
	public void setTraduction2(String traduction2) {
		this.traduction2 = traduction2;
	}

}
