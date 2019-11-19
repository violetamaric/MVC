package com.example.demo.dto;

import com.example.demo.model.Klinika;

public class KlinikaDTO {

private Long id;
	
	private String naziv;
	private String adresa;
	private String opis;
	private int ocena;
	
	public KlinikaDTO() {
		super();
	}

	public KlinikaDTO(Long id, String naziv, String adresa, String opis, int ocena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.ocena = ocena;
	}

	public KlinikaDTO(Klinika klinika) {
		super();
		// TODO Auto-generated constructor stub
		this.id = klinika.getId();
		this.naziv = klinika.getNaziv();
		this.adresa = klinika.getAdresa();
		this.opis = klinika.getOpis();
		this.ocena = klinika.getOcena();
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

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	} 
	
	
	
}
