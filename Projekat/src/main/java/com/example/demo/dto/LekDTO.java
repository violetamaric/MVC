package com.example.demo.dto;

import com.example.demo.model.Lek;

public class LekDTO {
	private Long id;
	private String naziv;
	private String sifra;
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
	public String getSifra() {
		return sifra;
	}
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	public LekDTO(Long id, String naziv, String sifra) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.sifra = sifra;
	}
	public LekDTO() {
		super();
	}
	public LekDTO(Lek lek) {
		super();
		this.id = lek.getId();
		this.naziv = lek.getNaziv();
		this.sifra = lek.getSifra();
	}
}
