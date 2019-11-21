package com.example.demo.dto;

import com.example.demo.model.KlinickiCentar;

public class KlinickiCentarDTO {
	private Long id;
	private String naziv;
	private String adresa;
	private String opis;
	
	
	public KlinickiCentarDTO(Long id, String naziv, String adresa, String opis) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
	}
	public KlinickiCentarDTO() {
		super();
	}
	public KlinickiCentarDTO(KlinickiCentar kc) {
		super();
		this.id = kc.getId();
		this.naziv = kc.getNaziv();
		this.adresa = kc.getAdresa();
		this.opis = kc.getOpis();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}

	
}
