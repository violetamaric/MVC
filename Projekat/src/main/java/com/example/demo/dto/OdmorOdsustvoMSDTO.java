package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.OdmorOdsustvoMedicinskaSestra;
import com.example.demo.model.TipOdmorOdsustvo;

public class OdmorOdsustvoMSDTO {

	private Long id;
	private Date datumOd;
	private Date datumDo;
	private String opis;
	private int status; //odobren/odbijen zahtev
	private String tip;
	private Long idMedSestre;
	private String emailMS;
	private String imeMS;
	private String prezimeMS;
	
	public String getEmailMS() {
		return emailMS;
	}
	public void setEmailMS(String emailMS) {
		this.emailMS = emailMS;
	}
	public String getImeMS() {
		return imeMS;
	}
	public void setImeMS(String imeMS) {
		this.imeMS = imeMS;
	}
	public String getPrezimeMS() {
		return prezimeMS;
	}
	public void setPrezimeMS(String prezimeMS) {
		this.prezimeMS = prezimeMS;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDatumOd() {
		return datumOd;
	}
	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}
	public Date getDatumDo() {
		return datumDo;
	}
	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	
	
	public Long getIdMedSestre() {
		return idMedSestre;
	}
	public void setIdMedSestre(Long idMedSestre) {
		this.idMedSestre = idMedSestre;
	}
	
	public OdmorOdsustvoMSDTO(Long id, Date datumOd, Date datumDo, String opis, int status, String tip, Long idMedSestre, 
			String imeMS, String prezimeMS, String emailMS) {
		super();
		this.id = id;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.opis = opis;
		this.status = status;
		this.tip = tip;
		this.idMedSestre = idMedSestre;
		this.imeMS = imeMS;
		this.prezimeMS = prezimeMS;
		this.emailMS = emailMS;
	}
	
	
	public OdmorOdsustvoMSDTO(OdmorOdsustvoMedicinskaSestra ms) {
		super();
		this.id = ms.getId();
		this.datumOd = ms.getDatumOd();
		this.datumDo = ms.getDatumDo();
		this.opis = ms.getOpis();
		this.status = ms.getStatus();
		if(ms.getTip() == TipOdmorOdsustvo.ODMOR) {
			this.tip = "ODMOR";
		}else {
			this.tip = "ODSUSTVO";
		}
		this.idMedSestre = ms.getMedicinskaSestra().getId();
		this.emailMS = ms.getMedicinskaSestra().getEmail();
		this.imeMS = ms.getMedicinskaSestra().getIme();
		this.prezimeMS = ms.getMedicinskaSestra().getPrezime();
	}
	public OdmorOdsustvoMSDTO() {
		super();
	}
	
	
	
	
}
