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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Klinika {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="naziv", nullable=false)
	private String naziv;
	
	@Column(name="adresa", nullable=false)
	private String adresa;
	
	@Column(name="opis", nullable=false)
	private String opis;
	
//	@Column(name="izvestajOKlinici", nullable=false)
//	private IzvestajOKlinici izvestajOKlinici;
	
	//termini sloobodni
	
	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pregled> listaPregleda = new HashSet<Pregled>();
	
	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Operacija> listaOperacija = new HashSet<Operacija>();
	
	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Lekar> listaLekara = new HashSet<Lekar>();
	
	
	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<MedicinskaSestra> listaMedSestara = new HashSet<MedicinskaSestra>();
	
	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Sala> listaSala = new HashSet<Sala>();
	//cenovnik
	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<AdministratorKlinike> listaAdminKlinike = new HashSet<AdministratorKlinike>();
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private KlinickiCentar klinickiCentar;
	
	@Column(name="ocena", nullable=false)
	private int ocena; 
	//ocena 1-10
	
	
	public String getNaziv() {
		return naziv;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
//	public IzvestajOKlinici getIzvestajOKlinici() {
//		return izvestajOKlinici;
//	}
//	public void setIzvestajOKlinici(IzvestajOKlinici izvestajOKlinici) {
//		this.izvestajOKlinici = izvestajOKlinici;
//	}
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
	public Set<Lekar> getListaLekara() {
		return listaLekara;
	}
	public void setListaLekara(Set<Lekar> listaLekara) {
		this.listaLekara = listaLekara;
	}
	public Set<MedicinskaSestra> getListaMedSestara() {
		return listaMedSestara;
	}
	public void setListaMedSestara(Set<MedicinskaSestra> listaMedSestara) {
		this.listaMedSestara = listaMedSestara;
	}
	public Set<Sala> getListaSala() {
		return listaSala;
	}
	public void setListaSala(Set<Sala> listaSala) {
		this.listaSala = listaSala;
	}
	public Set<AdministratorKlinike> getListaAdminKlinike() {
		return listaAdminKlinike;
	}
	public void setListaAdminKlinike(Set<AdministratorKlinike> listaAdminKlinike) {
		this.listaAdminKlinike = listaAdminKlinike;
	}
	public Klinika() {
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
	public Set<Pregled> getListaPregleda() {
		return listaPregleda;
	}
	public void setListaPregleda(Set<Pregled> listaPregleda) {
		this.listaPregleda = listaPregleda;
	}
	public Set<Operacija> getListaOperacija() {
		return listaOperacija;
	}
	public void setListaOperacija(Set<Operacija> listaOperacija) {
		this.listaOperacija = listaOperacija;
	}
	public KlinickiCentar getKlinickiCentar() {
		return klinickiCentar;
	}
	public void setKlinickiCentar(KlinickiCentar klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
	}
	
	
	
}
