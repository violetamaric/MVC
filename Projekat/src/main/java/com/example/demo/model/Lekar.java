package com.example.demo.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Lekar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="ime", unique=false, nullable=false)
	private String ime;
	
	@Column(name="prezime", unique=false, nullable=false)
	private String prezime;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="korisnickoIme", nullable=false)
	private String korisnickoIme;
	
	@Column(name="lozinka", nullable=false)
	private String lozinka;
	
	private ArrayList<Pacijent> listaPacijenata;
	private Klinika klinika;
	//kalendar
	private ArrayList<Operacija> listaOperacija;
	private ArrayList<Pregled> listaPregleda;
	
	@Column(name="ocena", nullable=false)
	private int ocena; 


	
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
	public int getOcena() {
		return ocena;
	}
	public void setOcena(int ocena) {
		this.ocena = ocena;
	}
	public ArrayList<Operacija> getListaOperacija() {
		return listaOperacija;
	}
	public void setListaOperacija(ArrayList<Operacija> listaOperacija) {
		this.listaOperacija = listaOperacija;
	}
	public ArrayList<Pregled> getListaPregleda() {
		return listaPregleda;
	}
	public void setListaPregleda(ArrayList<Pregled> listaPregleda) {
		this.listaPregleda = listaPregleda;
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
	public ArrayList<Pacijent> getListaPacijenata() {
		return listaPacijenata;
	}
	public void setListaPacijenata(ArrayList<Pacijent> listaPacijenata) {
		this.listaPacijenata = listaPacijenata;
	}
	public Klinika getKlinika() {
		return klinika;
	}
	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	public Lekar() {
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
