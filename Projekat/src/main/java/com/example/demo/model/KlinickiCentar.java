package com.example.demo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	
	@OneToMany(mappedBy = "klinickiCentar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Klinika> listaKlinika = new HashSet<Klinika>();
	
	@OneToMany(mappedBy = "klinickiCentar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<AdministratorKC> listaAdminKC = new HashSet<AdministratorKC>();
	//dijagnoza i sifrarnik 
	
	@OneToMany(mappedBy = "klinickiCentar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Lek> listaLekova = new HashSet<Lek>();
	
	@OneToMany(mappedBy = "klinickiCentar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Dijagnoza> listaDijagnoza = new HashSet<Dijagnoza>();
	
	@OneToMany(mappedBy = "klinickiCentar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pacijent> zahteviZaRegistraciju = new HashSet<Pacijent>();
	
	
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
	public Set<Klinika> getListaKlinika() {
		return listaKlinika;
	}
	public void setListaKlinika(Set<Klinika> listaKlinika) {
		this.listaKlinika = listaKlinika;
	}
	public Set<AdministratorKC> getListaAdminKC() {
		return listaAdminKC;
	}
	public void setListaAdminKC(Set<AdministratorKC> listaAdminKC) {
		this.listaAdminKC = listaAdminKC;
	}
	
	
	public Set<Lek> getListaLekova() {
		return listaLekova;
	}
	public void setListaLekova(Set<Lek> listaLekova) {
		this.listaLekova = listaLekova;
	}
	public Set<Dijagnoza> getListaDijagnoza() {
		return listaDijagnoza;
	}
	public void setListaDijagnoza(Set<Dijagnoza> listaDijagnoza) {
		this.listaDijagnoza = listaDijagnoza;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

	public Set<Pacijent> getZahteviZaRegistraciju() {
		return zahteviZaRegistraciju;
	}
	public void setZahteviZaRegistraciju(Set<Pacijent> zahteviZaRegistraciju) {
		this.zahteviZaRegistraciju = zahteviZaRegistraciju;
	}
	
	
	
}
