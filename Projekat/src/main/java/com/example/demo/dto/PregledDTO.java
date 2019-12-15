package com.example.demo.dto;

import java.sql.Time;
import java.util.Date;

import com.example.demo.model.Pregled;

public class PregledDTO {
	private Long id;
	private Date datum;
	private Time trajanje; 
	private double cena;
	private boolean status;
//	private TipPregleda tipPregleda;
//	private Sala sala;
//	private Lekar lekar;
//	private Pacijent pacijent;
//	private Klinika klinika;
//	private IzvestajOPregledu izvestajOPregledu;
	
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
	public Time getTrajanje() {
		return trajanje;
	}
	public void setTrajanje(Time trajanje) {
		this.trajanje = trajanje;
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
	public PregledDTO(Long id, Date datum, Time trajanje, double cena, boolean status) {
		super();
		this.id = id;
		this.datum = datum;
		this.trajanje = trajanje;
		this.cena = cena;
		this.status = status;
	}
	public PregledDTO(Pregled p) {
		super();
		this.id = p.getId();
		this.datum = p.getDatum();
		this.trajanje = p.getTrajanje();
		this.cena = p.getCena();
		this.status = p.isStatus();
	}
	public PregledDTO() {
		super();
	}
	
	
	
}
