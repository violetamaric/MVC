package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.IzvestajOPregledu;

public class IzvestajOPregleduDTO {

	private Long id;
	private Long zkID;
	private String dijagnozaN;
	private String dijagnozaO;
	private Long dijagnozaID;
	private Date datum;
	private String sadrzaj;
	private Long lekarID;
	private String imeL;
	private String prezimeL;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getZkID() {
		return zkID;
	}
	public void setZkID(Long zkID) {
		this.zkID = zkID;
	}
	public String getDijagnozaN() {
		return dijagnozaN;
	}
	public void setDijagnozaN(String dijagnozaN) {
		this.dijagnozaN = dijagnozaN;
	}
	public String getDijagnozaO() {
		return dijagnozaO;
	}
	public void setDijagnozaO(String dijagnozaO) {
		this.dijagnozaO = dijagnozaO;
	}
	public Long getDijagnozaID() {
		return dijagnozaID;
	}
	public void setDijagnozaID(Long dijagnozaID) {
		this.dijagnozaID = dijagnozaID;
	}
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public String getSadrzaj() {
		return sadrzaj;
	}
	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}
	public Long getLekarID() {
		return lekarID;
	}
	public void setLekarID(Long lekarID) {
		this.lekarID = lekarID;
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
	public IzvestajOPregleduDTO(IzvestajOPregledu IOP) {
		this.id = IOP.getId();
		this.zkID = IOP.getZdravstveniKarton().getId();
		this.dijagnozaID = IOP.getDijagnoza().getId();
		this.dijagnozaN = IOP.getDijagnoza().getNaziv();
		this.dijagnozaO = IOP.getDijagnoza().getOpis();
		this.datum = IOP.getPregled().getDatum();
		this.sadrzaj = IOP.getSadrzaj();
		this.lekarID = IOP.getPregled().getLekar().getId();
		this.imeL = IOP.getPregled().getLekar().getIme();
		this.prezimeL = IOP.getPregled().getLekar().getPrezime();
	}
	public IzvestajOPregleduDTO(Long id, Long zkID, String dijagnozaN, String dijagnozaO, Long dijagnozaID, Date datum,
			String sadrzaj, Long lekarID, String imeL, String prezimeL) {
		super();
		this.id = id;
		this.zkID = zkID;
		this.dijagnozaN = dijagnozaN;
		this.dijagnozaO = dijagnozaO;
		this.dijagnozaID = dijagnozaID;
		this.datum = datum;
		this.sadrzaj = sadrzaj;
		this.lekarID = lekarID;
		this.imeL = imeL;
		this.prezimeL = prezimeL;
	}
	
	
}