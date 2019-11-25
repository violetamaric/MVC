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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((listaIzvestajaOPregledu == null) ? 0 : listaIzvestajaOPregledu.hashCode());
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		result = prime * result + ((opis == null) ? 0 : opis.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dijagnoza other = (Dijagnoza) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (listaIzvestajaOPregledu == null) {
			if (other.listaIzvestajaOPregledu != null)
				return false;
		} else if (!listaIzvestajaOPregledu.equals(other.listaIzvestajaOPregledu))
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		if (opis == null) {
			if (other.opis != null)
				return false;
		} else if (!opis.equals(other.opis))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Dijagnoza [id=" + id + ", opis=" + opis + ", naziv=" + naziv + ", listaIzvestajaOPregledu="
				+ listaIzvestajaOPregledu + "]";
	}
	
	
}
