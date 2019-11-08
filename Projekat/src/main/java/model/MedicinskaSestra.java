package model;

import java.util.ArrayList;

public class MedicinskaSestra {
	
	private String ime;
	private String prezime;
	private String email;
	private String korisnickoIme;
	private String lozinka;
	private ArrayList<Pacijent> listaPacijenata;
	private Klinika klinika;
	//kalendar
	
	
	
	public String getIme() {
		return ime;
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
	public MedicinskaSestra() {
		super();
	}
	
	
}
