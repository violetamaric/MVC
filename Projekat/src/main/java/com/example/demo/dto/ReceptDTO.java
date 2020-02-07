package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.Recept;

public class ReceptDTO {
	private Long id;
	private Long lekID;
	private String nazivLeka;
	private Long medSesID;
	private boolean overen;
	private Long izvestajID;
	private Date datumIzvestaja; //datum pregleda
	private String imeL;
	private String prezimeL;
	private String imeP;
	private String prezimeP;
	private String jmbgP;
	
	
	public String getImeP() {
		return imeP;
	}
	public void setImeP(String imeP) {
		this.imeP = imeP;
	}
	public String getPrezimeP() {
		return prezimeP;
	}
	public void setPrezimeP(String prezimeP) {
		this.prezimeP = prezimeP;
	}
	public String getJmbgP() {
		return jmbgP;
	}
	public void setJmbgP(String jmbgP) {
		this.jmbgP = jmbgP;
	}
	public String getImeL() {
		return imeL;
	}
	public void setImeL(String imeL) {
		this.imeL = imeL;
	}
	public String getPrezimeL() {
		return prezimeL;
	}
	public void setPrezimeL(String prezimeL) {
		this.prezimeL = prezimeL;
	}
	public Date getDatumIzvestaja() {
		return datumIzvestaja;
	}
	public void setDatumIzvestaja(Date datumIzvestaja) {
		this.datumIzvestaja = datumIzvestaja;
	}
	public String getNazivLeka() {
		return nazivLeka;
	}
	public void setNazivLeka(String nazivLeka) {
		this.nazivLeka = nazivLeka;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLekID() {
		return lekID;
	}
	public void setLekID(Long lekID) {
		this.lekID = lekID;
	}
	public Long getMedSesID() {
		return medSesID;
	}
	public void setMedSesID(Long medSesID) {
		this.medSesID = medSesID;
	}
	public boolean isOveren() {
		return overen;
	}
	public void setOveren(boolean overen) {
		this.overen = overen;
	}
	public Long getIzvestajID() {
		return izvestajID;
	}
	public void setIzvestajID(Long izvestajID) {
		this.izvestajID = izvestajID;
	}
//	public ReceptDTO(Long id, Long lekID, Long medSesID, boolean overen, Long izvestajID, String nazivLeka) {
//		super();
//		this.id = id;
//		this.lekID = lekID;
//		this.medSesID = medSesID;
//		this.overen = overen;
//		this.izvestajID = izvestajID;
//		this.nazivLeka = nazivLeka;
//		
//	}
	public ReceptDTO(Recept r) {
		super();
		this.id = r.getId();
		this.lekID = r.getLek().getId();
		this.nazivLeka = r.getLek().getNaziv();
		if(r.getMedicinskaSestra() != null) {
			this.medSesID = r.getMedicinskaSestra().getId();
		}
		this.imeP = r.getIzvestajOPregledu().getPregled().getPacijent().getIme();
		this.prezimeP = r.getIzvestajOPregledu().getPregled().getPacijent().getPrezime();
		this.jmbgP = r.getIzvestajOPregledu().getPregled().getPacijent().getJmbg();
		
		this.overen = r.isOveren();
		this.izvestajID = r.getIzvestajOPregledu().getId();
		this.datumIzvestaja = r.getIzvestajOPregledu().getPregled().getDatum();
		this.imeL = r.getIzvestajOPregledu().getPregled().getLekar().getIme();
		this.prezimeL = r.getIzvestajOPregledu().getPregled().getLekar().getPrezime();
	}
	
	
	
}
