package com.example.demo.model;

import java.util.Collection;
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
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pacijent implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "zdravstveniKarton_id")
	private ZdravstveniKarton zdravstveniKarton;

	@Column(name = "ime", nullable = false)
	private String ime;

	@Column(name = "prezime", nullable = false)
	private String prezime;

	@Column(name = "lbo", nullable = false)
	private String lbo;
	
	@Column(name = "jmbg", nullable = false)
	private String jmbg;

	@Column(name = "lozinka", nullable = false)
	private String lozinka;

	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "adresa", nullable = false)
	private String adresa;
	
	@Column(name = "grad", nullable = false)
	private String grad;
	
	@Column(name = "drzava", nullable = false)
	private String drzava;
	
	@Column(name = "telefon", nullable = false)
	private String telefon;
	
	@Column(name = "odobrenaRegistracija", nullable = true)
	private Boolean odobrenaRegistracija;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private KlinickiCentar klinickiCentar;

	@ManyToMany(mappedBy ="listaPacijenataMedSestra")
	//@JoinTable(name = "medicinskaSestra_pacijent", joinColumns = @JoinColumn(name = "pacijent_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "lekar_id", referencedColumnName = "id"))
	private Set<MedicinskaSestra> listaMedicinskihSestara = new HashSet<MedicinskaSestra>();

	@ManyToMany(mappedBy = "listaPacijenata")
	private Set<Lekar> listaLekara = new HashSet<Lekar>();

	@ManyToMany(mappedBy = "listaPacijenata")
	private Set<Klinika> listaKlinika = new HashSet<Klinika>();
	
	@OneToMany(mappedBy = "pacijent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Operacija> listaOperacija = new HashSet<Operacija>();

	@OneToMany(mappedBy = "pacijent", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Pregled> listaPregleda = new HashSet<Pregled>();
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "pacijent_authority",
			joinColumns = @JoinColumn(name = "pacijent_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private Set<Authority> authorities;

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
	

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
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

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	
	public Boolean getOdobrenaRegistracija() {
		return odobrenaRegistracija;
	}

	public void setOdobrenaRegistracija(Boolean odobrenaRegistracija) {
		this.odobrenaRegistracija = odobrenaRegistracija;
	}

	public KlinickiCentar getKlinickiCentar() {
		return klinickiCentar;
	}

	public void setKlinickiCentar(KlinickiCentar klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return lozinka;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public Set<Klinika> getListaKlinika() {
		return listaKlinika;
	}

	public void setListaKlinika(Set<Klinika> listaKlinika) {
		this.listaKlinika = listaKlinika;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}


	
	
	

}
