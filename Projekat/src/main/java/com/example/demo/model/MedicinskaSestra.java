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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class MedicinskaSestra {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="ime", nullable=false)
	private String ime;
	
	@Column(name="prezime", nullable=false)
	private String prezime;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="brTelefona", nullable=false)
	private String brTelefona;
	
	@Column(name="lozinka", nullable=false)
	private String lozinka;
	
	@ManyToMany
	@JoinTable(name = "medicinska_sestra_pacijent", joinColumns = @JoinColumn(name = "pacijent_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "medicinska_sestra_id", referencedColumnName = "id"))
	private Set<Pacijent> listaPacijenataMedSestra = new HashSet<Pacijent>();
	
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Klinika klinika;
	//kalendar
	
	@OneToMany(mappedBy = "medicinskaSestra", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Recept> recepti = new HashSet<Recept>();
	
	@OneToMany(mappedBy = "medicinskaSestra", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<RadniDan> listaRadnihDana = new HashSet<RadniDan>();
	
	
	
	public Set<Pacijent> getListaPacijenataMedSestra() {
		return listaPacijenataMedSestra;
	}
	public void setListaPacijenataMedSestra(Set<Pacijent> listaPacijenataMedSestra) {
		this.listaPacijenataMedSestra = listaPacijenataMedSestra;
	}
	public Set<RadniDan> getListaRadnihDana() {
		return listaRadnihDana;
	}
	public void setListaRadnihDana(Set<RadniDan> listaRadnihDana) {
		this.listaRadnihDana = listaRadnihDana;
	}
	public String getIme() {
		return ime;
	}
	public String getBrTelefona() {
		return brTelefona;
	}
	public void setBrTelefona(String brTelefona) {
		this.brTelefona = brTelefona;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Pacijent> getListaPacijenata() {
		return listaPacijenataMedSestra;
	}
	public void setListaPacijenata(Set<Pacijent> listaPacijenata) {
		this.listaPacijenataMedSestra = listaPacijenata;
	}
	public Klinika getKlinika() {
		return klinika;
	}
	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	public MedicinskaSestra() {
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
	public Set<Recept> getRecepti() {
		return recepti;
	}
	public void setRecepti(Set<Recept> recepti) {
		this.recepti = recepti;
	}
	
	
}
