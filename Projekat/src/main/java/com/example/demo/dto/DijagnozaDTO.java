package com.example.demo.dto;

import com.example.demo.model.Dijagnoza;

public class DijagnozaDTO {
	private Long id;
	private String naziv;
	private String opis;
	private String oznaka;
	
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
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	
	public String getOznaka() {
		return oznaka;
	}
	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}
	public DijagnozaDTO(Long id, String naziv, String opis, String oznaka) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.oznaka = oznaka;
	}
	public DijagnozaDTO() {
		super();
	}
	public DijagnozaDTO(Dijagnoza di) {
		super();
		this.id = di.getId();
		this.naziv = di.getNaziv();
		this.opis = di.getOpis();
		this.oznaka = di.getOznaka();
	}
	
}
