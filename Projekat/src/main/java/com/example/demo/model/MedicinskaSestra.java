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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
public class MedicinskaSestra implements UserDetails{
	
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
	
	@OneToMany(mappedBy = "medicinskaSestra", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Recept> recepti = new HashSet<Recept>();
	
	//kalendar
	@OneToMany(mappedBy = "medicinskaSestra", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<OdmorOdsustvoMedicinskaSestra> listaOdmorOdsustvo = new HashSet<OdmorOdsustvoMedicinskaSestra>();

	@OneToMany(mappedBy = "medicinskaSestra", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<RadniDan> listaRadnihDana = new HashSet<RadniDan>();

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "medicinska_sestra_authority",
			joinColumns = @JoinColumn(name = "medicinska_sestra_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private Set<Authority> authorities;
	

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
	public Set<Pacijent> getListaPacijenataMedSestra() {
		return listaPacijenataMedSestra;
	}
	public void setListaPacijenataMedSestra(Set<Pacijent> listaPacijenataMedSestra) {
		this.listaPacijenataMedSestra = listaPacijenataMedSestra;
	}
	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}
	public Set<OdmorOdsustvoMedicinskaSestra> getListaOdmorOdsustvo() {
		return listaOdmorOdsustvo;
	}
	public void setListaOdmorOdsustvo(Set<OdmorOdsustvoMedicinskaSestra> listaOdmorOdsustvo) {
		this.listaOdmorOdsustvo = listaOdmorOdsustvo;
	}
	public Set<RadniDan> getListaRadnihDana() {
		return listaRadnihDana;
	}
	public void setListaRadnihDana(Set<RadniDan> listaRadnihDana) {
		this.listaRadnihDana = listaRadnihDana;
	}
	
	
	

	
	
}
