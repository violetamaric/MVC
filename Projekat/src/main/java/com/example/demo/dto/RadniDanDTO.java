package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.RadniDan;

public class RadniDanDTO {
	private Long id;
	private Date datumPocetka; 
	private Date datumKraja;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDatumPocetka() {
		return datumPocetka;
	}
	public void setDatumPocetka(Date datumPocetka) {
		this.datumPocetka = datumPocetka;
	}
	public Date getDatumKraja() {
		return datumKraja;
	}
	public void setDatumKraja(Date datumKraja) {
		this.datumKraja = datumKraja;
	}
	public RadniDanDTO(Long id, Date datumPocetka, Date datumKraja) {
		super();
		this.id = id;
		this.datumPocetka = datumPocetka;
		this.datumKraja = datumKraja;
	}
	public RadniDanDTO(RadniDan rd) {
		super();
		this.id = rd.getId();
		this.datumPocetka = rd.getDatumPocetka();
		this.datumKraja = rd.getDatumKraja();
	}
	public RadniDanDTO() {
		super();
	}
	
}
