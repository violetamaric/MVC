package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.Pregled;

public class PregledDTO {
	private Long id;

	private Date datum;

	private Long tipPregledaID;
	private String nazivTP;

	private Long lekarID;
	private String imeL;
	private String prezimeL;

	private String pacijentEmail;

	private Long klinikaID;

	private Long pacijentID;
	private String imeP;
	private String prezimeP;

	private String nazivKl;

	private Long salaID;
	private String salaN;
	private int salaBR;

	private double cena;

	//0-nije ni potvrdjeno ni odbijeno
	//1-potvrdjeno
	//2-odbijeno
	//3-zavrsen pregled
	//4-ocenjena samo klinika
	//5-ocenjen samo lekar
	//6-ocenjen i lekar i klinika
	private int status;

	public PregledDTO() {
	}

	@Override
	public String toString() {
		return "PregledDTO [id=" + id + ", datum=" + datum + ", tipPregledaID=" + tipPregledaID + ", nazivTP=" + nazivTP
				+ ", lekarID=" + lekarID + ", imeL=" + imeL + ", prezimeL=" + prezimeL + ", pacijentEmail="
				+ pacijentEmail + ", klinikaID=" + klinikaID + ", pacijentID=" + pacijentID + ", imeP=" + imeP
				+ ", prezimeP=" + prezimeP + ", nazivKl=" + nazivKl + ", salaID=" + salaID + ", salaN=" + salaN
				+ ", cena=" + cena + ", status=" + status + "]";
	}

	public PregledDTO(Long id, Date datum, Long tipPregledaID, Long lekarID, String pacijentEmail, Long klinikaID,
			double cena, int status, Long sala) {
		this.id = id;
		this.datum = datum;
		this.tipPregledaID = tipPregledaID;
		this.lekarID = lekarID;
		this.pacijentEmail = pacijentEmail;
		this.klinikaID = klinikaID;
		this.cena = cena;
		this.status = status;
		this.salaID = sala;
	}

	public PregledDTO(Pregled pregled) {
		this.id = pregled.getId();
		this.datum = pregled.getDatum();
		this.tipPregledaID = pregled.getTipPregleda().getId();
		this.lekarID = pregled.getLekar().getId();
		this.pacijentEmail = pregled.getPacijent().getEmail();
		this.klinikaID = pregled.getKlinika().getId();
		this.cena = pregled.getCena();
		this.status = pregled.getStatus();
		this.nazivKl = pregled.getKlinika().getNaziv();
		this.imeL = pregled.getLekar().getIme();
		this.imeP = pregled.getPacijent().getIme();
		this.nazivTP = pregled.getTipPregleda().getNaziv();
		this.prezimeL = pregled.getLekar().getPrezime();
		this.prezimeP = pregled.getPacijent().getPrezime();
		if (pregled.getSala() != null) {

			this.salaID = pregled.getSala().getId();
			this.salaN = pregled.getSala().getNaziv();
			this.salaBR = pregled.getSala().getBroj();
		}
	}

	public String getPrezimeL() {
		return prezimeL;
	}

	public void setPrezimeL(String prezimeL) {
		this.prezimeL = prezimeL;
	}

	public String getPrezimeP() {
		return prezimeP;
	}

	public void setPrezimeP(String prezimeP) {
		this.prezimeP = prezimeP;
	}

	public String getNazivTP() {
		return nazivTP;
	}

	public void setNazivTP(String nazivTP) {
		this.nazivTP = nazivTP;
	}

	public String getImeL() {
		return imeL;
	}

	public void setImeL(String imeL) {
		this.imeL = imeL;
	}

	public String getImeP() {
		return imeP;
	}

	public void setImeP(String imeP) {
		this.imeP = imeP;
	}

	public String getNazivKl() {
		return nazivKl;
	}

	public void setNazivKl(String nazivKl) {
		this.nazivKl = nazivKl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Long getTipPregledaID() {
		return tipPregledaID;
	}

	public void setTipPregledaID(Long tipPregledaID) {
		this.tipPregledaID = tipPregledaID;
	}

	public Long getLekarID() {
		return lekarID;
	}

	public void setLekarID(Long lekarID) {
		this.lekarID = lekarID;
	}

	public String getPacijentEmail() {
		return pacijentEmail;
	}

	public void setPacijentEmail(String pacijentEmail) {
		this.pacijentEmail = pacijentEmail;
	}

	public Long getKlinikaID() {
		return klinikaID;
	}

	public void setKlinikaID(Long klinikaID) {
		this.klinikaID = klinikaID;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}



	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getPacijentID() {
		return pacijentID;
	}

	public void setPacijentID(Long pacijentID) {
		this.pacijentID = pacijentID;
	}

	public Long getSalaID() {
		return salaID;
	}

	public void setSalaID(Long salaID) {
		this.salaID = salaID;
	}

	public String getSalaN() {
		return salaN;
	}

	public void setSalaN(String salaN) {
		this.salaN = salaN;
	}

	public int getSalaBR() {
		return salaBR;
	}

	public void setSalaBR(int salaBR) {
		this.salaBR = salaBR;
	}
	

}