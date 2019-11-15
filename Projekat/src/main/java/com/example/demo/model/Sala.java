package com.example.demo.model;

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
public class Sala {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="broj", nullable=false)
	private String oznaka;
	
	@OneToMany(mappedBy = "sala", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pregled> listaPregleda = new HashSet<Pregled>();
	
	@OneToMany(mappedBy = "sala", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Operacija> listaOperacija = new HashSet<Operacija>();
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Klinika klinika;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getOznaka() {
		return oznaka;
	}
	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
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
	public Sala() {
		super();
	}
	
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	public Klinika getKlinika() {
		return klinika;
	}
	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	
	
	
}
