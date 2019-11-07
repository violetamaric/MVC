package model;

import java.util.ArrayList;

public class Klinika {
	private String naziv;
	private String adresa;
	private String opis;
	//termini sloobodni
	private ArrayList<Lekar> listaLekara;
	private ArrayList<MedicinskaSestra> listaMedSestara;
	private ArrayList<Sala> listaSala;
	//cenovnik
	private ArrayList<AdministratorKlinike> listaAdminKlinike;
	private int ocena; 
	//ocena 1-10
	
	
	public String getNaziv() {
		return naziv;
	}
	public int getOcena() {
		return ocena;
	}
	public void setOcena(int ocena) {
		this.ocena = ocena;
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
	public ArrayList<Lekar> getListaLekara() {
		return listaLekara;
	}
	public void setListaLekara(ArrayList<Lekar> listaLekara) {
		this.listaLekara = listaLekara;
	}
	public ArrayList<MedicinskaSestra> getListaMedSestara() {
		return listaMedSestara;
	}
	public void setListaMedSestara(ArrayList<MedicinskaSestra> listaMedSestara) {
		this.listaMedSestara = listaMedSestara;
	}
	public ArrayList<Sala> getListaSala() {
		return listaSala;
	}
	public void setListaSala(ArrayList<Sala> listaSala) {
		this.listaSala = listaSala;
	}
	public ArrayList<AdministratorKlinike> getListaAdminKlinike() {
		return listaAdminKlinike;
	}
	public void setListaAdminKlinike(ArrayList<AdministratorKlinike> listaAdminKlinike) {
		this.listaAdminKlinike = listaAdminKlinike;
	}
	public Klinika() {
		super();
	}
	
	
	
}
