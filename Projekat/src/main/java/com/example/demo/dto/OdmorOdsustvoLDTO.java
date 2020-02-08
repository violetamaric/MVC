package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.OdmorOdsustvoLekar;
import com.example.demo.model.TipOdmorOdsustvo;

public class OdmorOdsustvoLDTO {

	private Long id;
	private Date datumOd;
	private Date datumDo;
	private String opis;
	private int status; 
	private String tip;
	private Long idLekara;
	private String imeL;
	private String prezimeL;
	private String emailL;
	
	
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
	public String getEmailL() {
		return emailL;
	}
	public void setEmailL(String emailL) {
		this.emailL = emailL;
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
	
	
	public Long getIdLekara() {
		return idLekara;
	}
	public void setIdLekara(Long idLekara) {
		this.idLekara = idLekara;
	}
	
	public OdmorOdsustvoLDTO(Long id, Date datumOd, Date datumDo, String opis, int status, String tip, Long idLekara,
			String imeL, String prezimeL, String emailL) {
		super();
		this.id = id;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.opis = opis;
		this.status = status;
		this.tip = tip;
		this.idLekara = idLekara;
		this.imeL = imeL;
		this.prezimeL = prezimeL;
		this.emailL = emailL;
	}
	
	
	public OdmorOdsustvoLDTO(OdmorOdsustvoLekar l) {
		super();
		this.id = l.getId();
		this.datumOd = l.getDatumOd();
		this.datumDo = l.getDatumDo();
		this.opis = l.getOpis();
		this.status = l.getStatus();
		if(l.getTip() == TipOdmorOdsustvo.ODMOR) {
			this.tip = "ODMOR";
		}else {
			this.tip = "ODSUSTVO";
		}
		
		this.idLekara = l.getLekar().getId();
		this.imeL = l.getLekar().getIme();
		this.prezimeL = l.getLekar().getPrezime();
		this.emailL = l.getLekar().getEmail();
	}
	public OdmorOdsustvoLDTO() {
		super();
	}
	
}
