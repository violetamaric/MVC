package com.example.demo.dto;

import com.example.demo.model.AdministratorKC;
import com.example.demo.model.KlinickiCentar;

public class AdministratorKCDTO {
	private Long id;
	private String ime;
	private String prezime;
	private String lozinka;
	private String email;
	//0-mora da promeni lozinku pri prvom logovanju
	//1-moze da se loguje lagano 
	//2-izbrisan 
	private int status; 
	
	public AdministratorKCDTO() {
		super();
	}
	public AdministratorKCDTO(Long id, String ime, String prezime, String lozinka, String email, int status) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.lozinka = lozinka;
		this.email = email;
		this.status = status;
	}
	public AdministratorKCDTO(AdministratorKC administratorKC) {
		super();
		this.id = administratorKC.getId();
		this.ime = administratorKC.getIme();
		this.prezime = administratorKC.getPrezime();
		this.lozinka = administratorKC.getLozinka();
		this.email = administratorKC.getEmail();
		this.status = administratorKC.getStatus();
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

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
