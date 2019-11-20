package com.example.demo.dto;

import com.example.demo.model.Lekar;


public class LekarDTO {

	private Long id;

	private String ime;

	private String prezime;

	private String lozinka;

	private String email;

	public LekarDTO() {
		super();
	}

	public LekarDTO(Long id, String ime, String prezime, String lozinka, String email) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.lozinka = lozinka;
		this.email = email;
	}
	
	public LekarDTO(Lekar lekar) {
		super();
		// TODO Auto-generated constructor stub
		this.id = lekar.getId();
		this.ime = lekar.getIme();
		this.prezime = lekar.getPrezime();
		this.lozinka = lekar.getLozinka();
		this.email = lekar.getEmail();
	}

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

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
