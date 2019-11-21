package com.example.demo.dto;

import com.example.demo.model.Klinika;
import com.example.demo.model.MedicinskaSestra;

public class MedicinskaSestraDTO{

	private Long id;
	private String ime;
	private String prezime;
	private String email;
	private Klinika klinika;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Klinika getKlinika() {
		return klinika;
	}
	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	public MedicinskaSestraDTO(Long id, String ime, String prezime, String email, Klinika klinika) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.klinika = klinika;
	}
	public MedicinskaSestraDTO() {
		super();
	}
	public MedicinskaSestraDTO(MedicinskaSestra ms) {
		super();
		this.id = ms.getId();
		this.ime = ms.getIme();
		this.prezime = ms.getPrezime();
		this.email = ms.getEmail();
		this.klinika = ms.getKlinika();
	}
	
	
}
