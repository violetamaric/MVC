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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.transaction.annotation.Transactional;

@Entity
//@Transactional
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TipPregleda{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="naziv", nullable=false)
	private String naziv;
	
	@ManyToMany
	@JoinTable(name = "tip_pregleda_klinika", joinColumns = @JoinColumn(name = "tip_pregleda_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "klinika_id", referencedColumnName = "id"))
	private Set<Klinika> listaKlinika = new HashSet<Klinika>();
	
	@OneToMany(mappedBy = "tipPregleda", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pregled> listaPregleda = new HashSet<Pregled>();

	public TipPregleda() {
		super();
	}
	
	public TipPregleda(TipPregleda tp) {
		super();
		this.id = tp.getId();
		this.naziv = tp.getNaziv();
		this.listaKlinika = tp.getListaKlinika();
		this.listaPregleda = tp.getListaPregleda();
	}
	

	public Set<Pregled> getListaPregleda() {
		return listaPregleda;
	}


	public void setListaPregleda(Set<Pregled> listaPregleda) {
		this.listaPregleda = listaPregleda;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Set<Klinika> getListaKlinika() {
		return listaKlinika;
	}

	public void setListaKlinika(Set<Klinika> listaKlinika) {
		this.listaKlinika = listaKlinika;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((listaKlinika == null) ? 0 : listaKlinika.hashCode());
		result = prime * result + ((listaPregleda == null) ? 0 : listaPregleda.hashCode());
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
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
		TipPregleda other = (TipPregleda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (listaKlinika == null) {
			if (other.listaKlinika != null)
				return false;
		} else if (!listaKlinika.equals(other.listaKlinika))
			return false;
		if (listaPregleda == null) {
			if (other.listaPregleda != null)
				return false;
		} else if (!listaPregleda.equals(other.listaPregleda))
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		return true;
	}
	
}
