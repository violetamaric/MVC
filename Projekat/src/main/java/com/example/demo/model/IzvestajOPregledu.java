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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class IzvestajOPregledu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ZdravstveniKarton zdravstveniKarton;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Dijagnoza dijagnoza;
	
	@OneToMany(mappedBy = "izvestajOPregledu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Recept> listaRecepata = new HashSet<Recept>();
	
	@OneToOne(fetch=FetchType.LAZY)
	private Pregled pregled;
	
	@Column(name="sadrzaj")
	private String sadrzaj;
	
	public IzvestajOPregledu() {
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
	public ZdravstveniKarton getZdravstveniKarton() {
		return zdravstveniKarton;
	}
	public void setZdravstveniKarton(ZdravstveniKarton zdravstveniKarton) {
		this.zdravstveniKarton = zdravstveniKarton;
	}
	public Dijagnoza getDijagnoza() {
		return dijagnoza;
	}
	public void setDijagnoza(Dijagnoza dijagnoza) {
		this.dijagnoza = dijagnoza;
	}
	public Set<Recept> getListaRecepata() {
		return listaRecepata;
	}
	public void setListaRecepata(Set<Recept> listaRecepata) {
		this.listaRecepata = listaRecepata;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Pregled getPregled() {
		return pregled;
	}
	public void setPregled(Pregled pregled) {
		this.pregled = pregled;
	}
	public String getSadrzaj() {
		return sadrzaj;
	}
	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}
	
	
}
