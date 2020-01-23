package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.OdmorOdsustvoLekar;
import com.example.demo.model.TipOdmorOdsustvo;

public class OdmorOdsustvoLDTO {

	private Long id;
	private Date datumOd;
	private Date datumDo;
	private String opis;
	private boolean status; //odobren/odbijen zahtev
	private TipOdmorOdsustvo tip;
	private Long idLekara;
	
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
	
	
	public Long getIdLekara() {
		return idLekara;
	}
	public void setIdLekara(Long idLekara) {
		this.idLekara = idLekara;
	}
	
	public OdmorOdsustvoLDTO(Long id, Date datumOd, Date datumDo, String opis, boolean status, TipOdmorOdsustvo tip, Long idLekara) {
		super();
		this.id = id;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.opis = opis;
		this.status = status;
		this.tip = tip;
		this.idLekara = idLekara;
	}
	
	
	public OdmorOdsustvoLDTO(OdmorOdsustvoLekar l) {
		super();
		this.id = l.getId();
		this.datumOd = l.getDatumOd();
		this.datumDo = l.getDatumDo();
		this.opis = l.getOpis();
		this.status = l.isStatus();
		this.tip = l.getTip();
		this.idLekara = l.getLekar().getId();
	}
	public OdmorOdsustvoLDTO() {
		super();
	}
	
}
