package model;

import java.util.ArrayList;

public class AdministratorKlinike {
	
	private String ime;
	private String prezime;
	private String korisnickoIme;
	private String lozinka;
	private String email;
	private Klinika klinika; // samo id do klinike 
	private ArrayList<Pregled> listaPregleda;
	private ArrayList<Operacija> listaOperacija; //?? proveriti
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
	public Klinika getKlinika() {
		return klinika;
	}
	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	public ArrayList<Pregled> getListaPregleda() {
		return listaPregleda;
	}
	public void setListaPregleda(ArrayList<Pregled> listaPregleda) {
		this.listaPregleda = listaPregleda;
	}
	public ArrayList<Operacija> getListaOperacija() {
		return listaOperacija;
	}
	public void setListaOperacija(ArrayList<Operacija> listaOperacija) {
		this.listaOperacija = listaOperacija;
	}
	public AdministratorKlinike() {
		super();
	}
	
	
}
