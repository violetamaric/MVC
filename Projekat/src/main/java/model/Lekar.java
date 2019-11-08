package model;

import java.util.ArrayList;

public class Lekar {
	
	private String ime;
	private String prezime;
	private String email;
	private String korisnickoIme;
	private String lozinka;
	
	
	private ArrayList<Pacijent> listaPacijenata;
	private Klinika klinika;
	//kalendar
	private ArrayList<Operacija> listaOperacija;
	private ArrayList<Pregled> listaPregleda;
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
	
	
}
