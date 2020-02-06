package com.example.demo.model;


import java.util.Collection;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Lekar implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="ime", unique=false, nullable=false)
	private String ime;
	
	@Column(name="prezime", unique=false, nullable=false)
	private String prezime;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="lozinka", nullable=false)
	private String lozinka;
	
	@Column(name="telefon", nullable=false)
	private String telefon;
	
	@ManyToMany
	@JoinTable(name = "lekar_pacijent", joinColumns = @JoinColumn(name = "lekar_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "pacijent_id", referencedColumnName = "id"))
	private Set<Pacijent> listaPacijenata = new HashSet<Pacijent>();
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Klinika klinika;
	
	//dodati za kalendar 
	
	@ManyToMany
	@JoinTable(name = "lekar_operacija", joinColumns = @JoinColumn(name = "lekar_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "operacija_id", referencedColumnName = "id"))
	private Set<Operacija> listaOperacija = new HashSet<Operacija>();
	
	@OneToMany(mappedBy = "lekar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pregled> listaPregleda = new HashSet<Pregled>();
	
	@OneToMany(mappedBy = "lekar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Termin> listaZauzetihTermina = new HashSet<Termin>();
	
	@OneToMany(mappedBy = "lekar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<OdmorOdsustvoLekar> listaOdmorOdsustvo = new HashSet<OdmorOdsustvoLekar>();
	
//	@OneToMany(mappedBy = "lekar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Set<SlobodniTermin> listaSlobodnihTermina = new HashSet<SlobodniTermin>();
	
	@Column(name="ocena", nullable=false)
	private int ocena; 
	
	@Version
	private Long version;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "lekar_authority",
			joinColumns = @JoinColumn(name = "lekar_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private Set<Authority> authorities;
	
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public int getOcena() {
		return ocena;
	}
	public void setOcena(int ocena) {
		this.ocena = ocena;
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
	
	public Set<Termin> getListaZauzetihTermina() {
		return listaZauzetihTermina;
	}
	public void setListaZauzetihTermina(Set<Termin> listaZauzetihTermina) {
		this.listaZauzetihTermina = listaZauzetihTermina;
	}
	public Set<OdmorOdsustvoLekar> getListaOdmorOdsustvo() {
		return listaOdmorOdsustvo;
	}
	public void setListaOdmorOdsustvo(Set<OdmorOdsustvoLekar> listaOdmorOdsustvo) {
		this.listaOdmorOdsustvo = listaOdmorOdsustvo;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Pacijent> getListaPacijenata() {
		return listaPacijenata;
	}
	public void setListaPacijenata(Set<Pacijent> listaPacijenata) {
		this.listaPacijenata = listaPacijenata;
	}
	public Klinika getKlinika() {
		return klinika;
	}
	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	public Lekar() {
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
	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public boolean sadrziTermin(Set<Termin> listaTermina, int termin){
	    return listaTermina.stream().filter(o -> o.getTermin() == termin).findFirst().isPresent();
	}
	@SuppressWarnings("deprecation")
	public boolean sadrziSlobodanTermin(Set<Termin> listaTermina, Date datum){
		int flag = 4;
		for(Termin t:listaZauzetihTermina) {
			if(t.getDatumPocetka().getDate() == datum.getDate() &&
					t.getDatumPocetka().getMonth() == datum.getMonth() &&
					t.getDatumPocetka().getYear() == datum.getYear()) {
				if(t.getTermin() == 9) {
					flag -=1;
				}else if(t.getTermin() == 11) {
					flag -=1;
				}else if(t.getTermin() == 13) {
					flag -=1;
				}else if(t.getTermin() == 15) {
					flag -=1;
				}
				if(flag == 0) {
					return false;
				}
				
			}
		}
//		for(SlobodniTermin st:listaSlobodnihTermina) {
//			if(st.getDatum().getDate() == datum.getDate() &&
//					st.getDatum().getMonth() == datum.getMonth() &&
//							st.getDatum().getYear() == datum.getYear()) {
//				if(st.getTermin() == 9) {
//					flag -=1;
//				}else if(st.getTermin() == 11) {
//					flag -=1;
//				}else if(st.getTermin() == 13) {
//					flag -=1;
//				}else if(st.getTermin() == 15) {
//					flag -=1;
//				}
//				if(flag == 0) {
//					return false;
//				}
//				
//			}
//		}
		return true;
//	    return listaTermina.stream().filter(o -> o.getTermin() == termin).findFirst().isPresent();

	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
//	public Set<SlobodniTermin> getListaSlobodnihTermina() {
//		return listaSlobodnihTermina;
//	}
//	public void setListaSlobodnihTermina(Set<SlobodniTermin> listaSlobodnihTermina) {
//		this.listaSlobodnihTermina = listaSlobodnihTermina;
//	}
	
	
	
}
