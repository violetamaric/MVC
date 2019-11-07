package model;

import java.util.ArrayList;

public class KlinickiCentar {
	private String naziv;
	private String adresa;
	private String opis;
	private ArrayList<Klinika> listaKlinika;
	private ArrayList<AdministratorKC> listaAdminKC;
	//dijagnoza i sifrarnik 
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public ArrayList<Klinika> getListaKlinika() {
		return listaKlinika;
	}
	public void setListaKlinika(ArrayList<Klinika> listaKlinika) {
		this.listaKlinika = listaKlinika;
	}
	public ArrayList<AdministratorKC> getListaAdminKC() {
		return listaAdminKC;
	}
	public void setListaAdminKC(ArrayList<AdministratorKC> listaAdminKC) {
		this.listaAdminKC = listaAdminKC;
	}
	public KlinickiCentar() {
		super();
	}
	
	
}
