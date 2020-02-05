package com.example.demo.dto;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.model.Sala;
import com.example.demo.model.Termin;

public class SalaDTO {
	private Long id;

	private String naziv;

	private int broj;

	private Long klinikaID;

	private Set<TerminDTO> zauzetiTermini = new HashSet<TerminDTO>();
	
	

	@Override
	public String toString() {
		return "SalaDTO [id=" + id + ", naziv=" + naziv + ", broj=" + broj + ", klinikaID=" + klinikaID
				+ ", zauzetiTermini=" + zauzetiTermini + "]";
	}

	public SalaDTO() {
	}

	public SalaDTO(Long id, String naziv, int broj, Long klinikaID) {
		this.id = id;
		this.naziv = naziv;
		this.broj = broj;
		this.klinikaID = klinikaID;
	}
	
	public SalaDTO(Sala s) {
		this.id = s.getId();
		this.naziv = s.getNaziv();
		this.broj = s.getBroj();
		this.klinikaID = s.getKlinika().getId();
		
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

	public int getBroj() {
		return broj;
	}

	public void setBroj(int broj) {
		this.broj = broj;
	}

	public Long getKlinikaID() {
		return klinikaID;
	}

	public void setKlinikaID(Long klinikaID) {
		this.klinikaID = klinikaID;
	}

	public Set<TerminDTO> getZauzetiTermini() {
		return zauzetiTermini;
	}

	public void setZauzetiTermini(Set<TerminDTO> zauzetiTermini) {
		this.zauzetiTermini = zauzetiTermini;
	}


	
}
