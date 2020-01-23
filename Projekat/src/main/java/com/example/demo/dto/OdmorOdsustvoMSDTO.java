package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.OdmorOdsustvoMedicinskaSestra;
import com.example.demo.model.TipOdmorOdsustvo;

public class OdmorOdsustvoMSDTO {

	private Long id;
	private Date datumOd;
	private Date datumDo;
	private String opis;
	private boolean status; //odobren/odbijen zahtev
	private TipOdmorOdsustvo tip;
	private Long idMedSestre;
	
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
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public TipOdmorOdsustvo getTip() {
		return tip;
	}
	public void setTip(TipOdmorOdsustvo tip) {
		this.tip = tip;
	}
	
	
	public Long getIdMedSestre() {
		return idMedSestre;
	}
	public void setIdMedSestre(Long idMedSestre) {
		this.idMedSestre = idMedSestre;
	}
	
	public OdmorOdsustvoMSDTO(Long id, Date datumOd, Date datumDo, String opis, boolean status, TipOdmorOdsustvo tip, Long idMedSestre) {
		super();
		this.id = id;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.opis = opis;
		this.status = status;
		this.tip = tip;
		this.idMedSestre = idMedSestre;
	}
	
	
	public OdmorOdsustvoMSDTO(OdmorOdsustvoMedicinskaSestra ms) {
		super();
		this.id = ms.getId();
		this.datumOd = ms.getDatumOd();
		this.datumDo = ms.getDatumDo();
		this.opis = ms.getOpis();
		this.status = ms.isStatus();
		this.tip = ms.getTip();
		this.idMedSestre = ms.getMedicinskaSestra().getId();
	}
	public OdmorOdsustvoMSDTO() {
		super();
	}
	
	
	
	
}
