package com.example.demo.dto;

import com.example.demo.model.Pacijent;
import com.example.demo.model.ZdravstveniKarton;

public class PacijentDTO {

	private Long id;

	private ZdravstveniKarton zdravstveniKarton;

	private String ime;

	private String prezime;

	private String lbo;

	private String korisnickoIme;

	private String lozinka;

	private String email;
	
	

	public PacijentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PacijentDTO(Pacijent pacijent) {
		super();
		// TODO Auto-generated constructor stub
		this.id = pacijent.getId();
		this.zdravstveniKarton = pacijent.getZdravstveniKarton();
		this.ime = pacijent.getIme();
		this.prezime = pacijent.getPrezime();
		this.lbo = pacijent.getLbo();
		this.lozinka = pacijent.getLozinka();
		this.email = pacijent.getEmail();
	}

	public PacijentDTO(Long id, ZdravstveniKarton zdravstveniKarton, String ime, String prezime, String lbo,
			String lozinka, String email) {
		super();
		this.id = id;
		this.zdravstveniKarton = zdravstveniKarton;
		this.ime = ime;
		this.prezime = prezime;
		this.lbo = lbo;
		this.lozinka = lozinka;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ZdravstveniKarton getZdravstveniKarton() {
		return zdravstveniKarton;
	}

	public void setZdravstveniKarton(ZdravstveniKarton zdravstveniKarton) {
		this.zdravstveniKarton = zdravstveniKarton;
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

	public String getLbo() {
		return lbo;
	}

	public void setLbo(String lbo) {
		this.lbo = lbo;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
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
