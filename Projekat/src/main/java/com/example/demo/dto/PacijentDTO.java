package com.example.demo.dto;

import javax.persistence.Column;

import com.example.demo.model.Pacijent;
import com.example.demo.model.ZdravstveniKarton;

public class PacijentDTO {

	private Long id;

	private ZdravstveniKarton zdravstveniKarton;

	private String ime;

	private String prezime;

	private String lbo;
	
	private String jmbg;

	private String lozinka;

	private String email;
	
	private String adresa;
	
	private String grad;
	
	private String drzava;

	private String telefon;
	
	private Boolean odobrenaRegistracija;

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
		this.odobrenaRegistracija = pacijent.getOdobrenaRegistracija();
		this.adresa = pacijent.getAdresa();
		this.grad = pacijent.getGrad();
		this.drzava = pacijent.getDrzava();
		this.telefon = pacijent.getTelefon();
		this.jmbg = pacijent.getJmbg();

	}

	public PacijentDTO(Long id, ZdravstveniKarton zdravstveniKarton, String ime, String prezime, String lbo,
			String lozinka, String email, Boolean odobrenaRegistracija, String jmbg) {
		super();
		this.id = id;
		this.zdravstveniKarton = zdravstveniKarton;
		this.ime = ime;
		this.prezime = prezime;
		this.lbo = lbo;
		this.lozinka = lozinka;
		this.email = email;
		this.odobrenaRegistracija = odobrenaRegistracija;
		this.jmbg = jmbg;
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

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
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

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	

	public Boolean getOdobrenaRegistracija() {
		return odobrenaRegistracija;
	}

	public void setOdobrenaRegistracija(Boolean odobrenaRegistracija) {
		this.odobrenaRegistracija = odobrenaRegistracija;
	}

	@Override
	public String toString() {
		return "PacijentDTO [id=" + id + ", zdravstveniKarton=" + zdravstveniKarton + ", ime=" + ime + ", prezime="
				+ prezime + ", lbo=" + lbo + ", lozinka=" + lozinka + ", email=" + email + ", adresa=" + adresa
				+ ", grad=" + grad + ", drzava=" + drzava + ", telefon=" + telefon + "]";
	}
	

}
