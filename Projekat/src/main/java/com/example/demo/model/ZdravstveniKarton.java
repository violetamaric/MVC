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
import javax.persistence.OneToOne;

@Entity
public class ZdravstveniKarton {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="visina", nullable=false)
	private double visina;
	
	@Column(name="tezina", nullable=false)
	private double tezina;
	
	@Column(name="krvnaGrupa", nullable=true)
	private double krvnaGrupa;
	
	@OneToOne(fetch=FetchType.LAZY)
	private Pacijent pacijent;
	
	@OneToMany(mappedBy = "zdravstveniKarton", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<IzvestajOPregledu> listaIzvestajaOPregledu = new HashSet<IzvestajOPregledu>();
	
	@OneToMany(mappedBy = "zdravstveniKarton", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Operacija> listaOperacija = new HashSet<Operacija>();
	
	public ZdravstveniKarton() {
		super();
	}


	public Set<Operacija> getListaOperacija() {
		return listaOperacija;
	}

	public void setListaOperacija(Set<Operacija> listaOperacija) {
		this.listaOperacija = listaOperacija;
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


	public double getVisina() {
		return visina;
	}


	public void setVisina(double visina) {
		this.visina = visina;
	}


	public double getTezina() {
		return tezina;
	}


	public void setTezina(double tezina) {
		this.tezina = tezina;
	}


	public Pacijent getPacijent() {
		return pacijent;
	}


	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}


	public Set<IzvestajOPregledu> getListaIzvestajaOPregledu() {
		return listaIzvestajaOPregledu;
	}


	public void setListaIzvestajaOPregledu(Set<IzvestajOPregledu> listaIzvestajaOPregledu) {
		this.listaIzvestajaOPregledu = listaIzvestajaOPregledu;
	}
	
	
	
	
}
