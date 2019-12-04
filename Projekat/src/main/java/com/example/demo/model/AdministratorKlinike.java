package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AdministratorKlinike {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="ime", nullable=false)
	private String ime;
	
	@Column(name="prezime", nullable=false)
	private String prezime;
	
//	@Column(name="korisnickoIme", nullable=false)
//	private String korisnickoIme;
	
	@Column(name="lozinka", nullable=false)
	private String lozinka;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Klinika klinika; // samo id do klinike 
	
//	private Set<Pregled> listaPregleda;
//	private Set<Operacija> listaOperacija; //?? proveriti
//	
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
//	public String getKorisnickoIme() {
//		return korisnickoIme;
//	}
//	public void setKorisnickoIme(String korisnickoIme) {
//		this.korisnickoIme = korisnickoIme;
//	}
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
	public Klinika getKlinika() {
		return klinika;
	}
	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	//	public Set<Pregled> getListaPregleda() {
//		return listaPregleda;
//	}
//	public void setListaPregleda(Set<Pregled> listaPregleda) {
//		this.listaPregleda = listaPregleda;
//	}
//	public Set<Operacija> getListaOperacija() {
//		return listaOperacija;
//	}
//	public void setListaOperacija(Set<Operacija> listaOperacija) {
//		this.listaOperacija = listaOperacija;
//	}
	public AdministratorKlinike() {
		super();
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
}
