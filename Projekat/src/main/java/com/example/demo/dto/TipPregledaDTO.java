package com.example.demo.dto;

import com.example.demo.model.TipPregleda;

public class TipPregledaDTO {

	private Long id;
	private String naziv;
	private double cena;
	
	public TipPregledaDTO() {
		
	}
	public TipPregledaDTO(TipPregleda tp) {
		this.id = tp.getId();
		this.naziv = tp.getNaziv();
		this.cena = tp.getCena();
	}
	
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
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
	
}
