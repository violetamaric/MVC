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
public class Dijagnoza {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="opis", nullable=false)
	private String opis;
	
	@Column(name="naziv", nullable=false)
	private String naziv;
	
	@Column(name="oznaka", nullable=false)
	private String oznaka;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private KlinickiCentar klinickiCentar;
	
	@OneToMany(mappedBy = "dijagnoza", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<IzvestajOPregledu> listaIzvestajaOPregledu = new HashSet<IzvestajOPregledu>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Set<IzvestajOPregledu> getListaIzvestajaOPregledu() {
		return listaIzvestajaOPregledu;
	}

	public void setListaIzvestajaOPregledu(Set<IzvestajOPregledu> listaIzvestajaOPregledu) {
		this.listaIzvestajaOPregledu = listaIzvestajaOPregledu;
	}
	

	public KlinickiCentar getKlinickiCentar() {
		return klinickiCentar;
	}

	public void setKlinickiCentar(KlinickiCentar klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
	}

	public Dijagnoza() {
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
