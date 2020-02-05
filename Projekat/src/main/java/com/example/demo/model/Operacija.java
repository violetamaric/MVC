package com.example.demo.model;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
@Entity
public class Operacija {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="datum", nullable=false)
	private Date datum;
	
	@Column(name="tipOperacije")
	private String tipOperacije;
	
	@Column(name="trajanje", nullable=true)
	private Time trajanje;
	
	@Column(name="cena", nullable=false)
	private double cena;
	
	@Column(name = "termin", nullable = false)
	private int termin;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Sala sala;
	
	@ManyToMany(mappedBy = "listaOperacija")
	private Set<Lekar> listaLekara = new HashSet<Lekar>();
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Pacijent pacijent;
	

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Klinika klinika;
	
	//0-nije ni potvrdjeno ni odbijeno
	//1-potvrdjeno
	//2-odbijeno
	//3-zavrsena operacija
	//4-ocenjena samo klinika
	//5-ocenjen samo lekar
	//6-ocenjen i lekar i klinika
	@Column(name = "status", nullable = false)
	private int status;
	
	
	
	public int getTermin() {
		return termin;
	}
	public void setTermin(int termin) {
		this.termin = termin;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public String getTipOperacije() {
		return tipOperacije;
	}
	public void setTipOperacije(String tipOperacije) {
		this.tipOperacije = tipOperacije;
	}
	public Time getTrajanje() {
		return trajanje;
	}
	public void setTrajanje(Time trajanje) {
		this.trajanje = trajanje;
	}
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Set<Lekar> getListaLekara() {
		return listaLekara;
	}
	public void setListaLekara(Set<Lekar> listaLekara) {
		this.listaLekara = listaLekara;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public Operacija() {
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
	public Pacijent getPacijent() {
		return pacijent;
	}
	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Klinika getKlinika() {
		return klinika;
	}
	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	
	
}
