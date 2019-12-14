package com.example.demo.dto;

import com.example.demo.model.Pregled;


public class PregledDTO {
	private Long id;

	private String datum;

	private Long tipPregledaID;

	private Long lekarID;

	private Long pacijentID;
	
	private Long klinikaID;
	
	private double cena;
	
	private boolean status;

	public PregledDTO() {
	}

	public PregledDTO(Long id, String datum, Long tipPregledaID, Long lekarID, Long pacijentID, Long klinikaID,
			double cena, boolean status) {
		this.id = id;
		this.datum = datum;
		this.tipPregledaID = tipPregledaID;
		this.lekarID = lekarID;
		this.pacijentID = pacijentID;
		this.klinikaID = klinikaID;
		this.cena = cena;
		this.status = status;
	}
	
	public PregledDTO(Pregled pregled){
		this.id = pregled.getId();
		this.datum = pregled.getDatum().toString();
		this.tipPregledaID = pregled.getTipPregleda().getId();
		this.lekarID = pregled.getLekar().getId();
		this.pacijentID = pregled.getPacijent().getId();
		this.klinikaID = pregled.getKlinika().getId();
		this.cena = pregled.getCena();
		this.status = pregled.isStatus();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
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

	public Long getPacijentID() {
		return pacijentID;
	}

	public void setPacijentID(Long pacijentID) {
		this.pacijentID = pacijentID;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}