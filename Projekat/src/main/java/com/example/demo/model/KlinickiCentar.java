package com.example.demo.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class KlinickiCentar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="naziv", nullable=false)
	private String naziv;
	
	@Column(name="adresa", nullable=false)
	private String adresa;
	
	@Column(name="opis", nullable=false)
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
