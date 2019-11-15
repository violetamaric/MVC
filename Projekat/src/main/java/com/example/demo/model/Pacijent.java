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
import javax.persistence.OneToOne;


@Entity
public class Pacijent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="zdravstveniKarton_id")
	private ZdravstveniKarton zdravstveniKarton;
	
	@Column(name="ime", nullable=false)
	private String ime;
	
	@Column(name="prezime",  nullable=false)
	private String prezime;
	
	@Column(name="lbo", nullable=false)
	private String lbo;
	
	@Column(name="korisnickoIme", nullable=false)
	private String korisnickoIme;
	
	@Column(name="lozinka", nullable=false)
	private String lozinka;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@ManyToMany
	@JoinTable(name = "medicinskaSestra_pacijent", joinColumns = @JoinColumn(name = "pacijent_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "lekar_id", referencedColumnName = "id"))
	private Set<MedicinskaSestra> listaMedicinskihSestara = new HashSet<MedicinskaSestra>();
	
	@ManyToMany(mappedBy = "listaPacijenata")	
	private Set<Lekar> listaLekara = new HashSet<Lekar>();
	
	@OneToMany(mappedBy = "pacijent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Operacija> listaOperacija = new HashSet<Operacija>();
	
	@OneToMany(mappedBy = "pacijent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pregled> listaPregleda = new HashSet<Pregled>();
	
	
	public Pacijent() {
		super();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ZdravstveniKarton getZdravstveniKarton() {
		return zdravstveniKarton;
	}
	public void setZdravstveniKarton(ZdravstveniKarton zdravstveniKarton) {
		this.zdravstveniKarton = zdravstveniKarton;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getLbo() {
		return lbo;
	}
	public void setLbo(String lbo) {
		this.lbo = lbo;
	}
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}



	public Set<MedicinskaSestra> getListaMedicinskihSestara() {
		return listaMedicinskihSestara;
	}

	public void setListaMedicinskihSestara(Set<MedicinskaSestra> listaMedicinskihSestara) {
		this.listaMedicinskihSestara = listaMedicinskihSestara;
	}

	public Set<Lekar> getListaLekara() {
		return listaLekara;
	}

	public void setListaLekara(Set<Lekar> listaLekara) {
		this.listaLekara = listaLekara;
	}

	public Set<Operacija> getListaOperacija() {
		return listaOperacija;
	}

	public void setListaOperacija(Set<Operacija> listaOperacija) {
		this.listaOperacija = listaOperacija;
	}

	public Set<Pregled> getListaPregleda() {
		return listaPregleda;
	}

	public void setListaPregleda(Set<Pregled> listaPregleda) {
		this.listaPregleda = listaPregleda;
	}
	
	
}
