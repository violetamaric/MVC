package com.example.demo.dto;

import com.example.demo.model.TipPregleda;

public class TipPregledaDTO {

	private Long id;
	private String naziv;
	
	public TipPregledaDTO() {
		
	}
	public TipPregledaDTO(TipPregleda tp) {
		this.id = tp.getId();
		this.naziv = tp.getNaziv();
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
