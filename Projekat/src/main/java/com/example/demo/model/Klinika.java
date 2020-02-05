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
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Klinika {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "naziv", nullable = false)
	private String naziv;

	@Column(name = "adresa", nullable = false)
	private String adresa;

	@Column(name = "opis", nullable = false)
	private String opis;

//	@Column(name="izvestajOKlinici", nullable=false)
//	private IzvestajOKlinici izvestajOKlinici;

	// termini sloobodni

	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pregled> listaPregleda = new HashSet<Pregled>();

	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Operacija> listaOperacija = new HashSet<Operacija>();

	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Lekar> listaLekara = new HashSet<Lekar>();

	@ManyToMany(mappedBy = "listaKlinika")
	private Set<TipPregleda> listaTipovaPregleda = new HashSet<TipPregleda>();

	@ManyToMany
	@JoinTable(name = "klinika_pacijent", joinColumns = @JoinColumn(name = "klinika_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "pacijent_id", referencedColumnName = "id"))
	private Set<Pacijent> listaPacijenata = new HashSet<Pacijent>();

	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<MedicinskaSestra> listaMedSestara = new HashSet<MedicinskaSestra>();

	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Sala> listaSala = new HashSet<Sala>();
	// cenovnik
	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<AdministratorKlinike> listaAdminKlinike = new HashSet<AdministratorKlinike>();

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private KlinickiCentar klinickiCentar;

//	private KlinickiCentar procitajKCiz() {
//
//		final KlinickiCentar cache = new KlinickiCentar();
//		KlinickiCentar kc = kcService.findOne();
//
//		cache = kc;
//
//		return cache;
//	}

	@Column(name = "ocena", nullable = false)
	private int ocena;
	// ocena 1-10

	@Version
	private Long version;

	public Klinika(String naziv, String adresa, String opis, int ocena) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.ocena = ocena;
	}

	public Klinika(String naziv, String adresa, String opis, KlinickiCentar klinickiCentar, int ocena) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.klinickiCentar = klinickiCentar;
		this.ocena = ocena;
	}
	

	public Klinika(Long id, String naziv, String adresa, String opis, Set<Pregled> listaPregleda,
			Set<Operacija> listaOperacija, Set<Lekar> listaLekara, Set<TipPregleda> listaTipovaPregleda,
			Set<Pacijent> listaPacijenata, Set<MedicinskaSestra> listaMedSestara, Set<Sala> listaSala,
			Set<AdministratorKlinike> listaAdminKlinike, KlinickiCentar klinickiCentar, int ocena, Long version,
			Set<OdmorOdsustvoLekar> zahteviZaOdmorOdsustvoLekara,
			Set<OdmorOdsustvoMedicinskaSestra> zahteviZaOdmorOdsustvoMedestre) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.listaPregleda = listaPregleda;
		this.listaOperacija = listaOperacija;
		this.listaLekara = listaLekara;
		this.listaTipovaPregleda = listaTipovaPregleda;
		this.listaPacijenata = listaPacijenata;
		this.listaMedSestara = listaMedSestara;
		this.listaSala = listaSala;
		this.listaAdminKlinike = listaAdminKlinike;
		this.klinickiCentar = klinickiCentar;
		this.ocena = ocena;
		this.version = version;
		this.zahteviZaOdmorOdsustvoLekara = zahteviZaOdmorOdsustvoLekara;
		this.zahteviZaOdmorOdsustvoMedestre = zahteviZaOdmorOdsustvoMedestre;
	}


	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<OdmorOdsustvoLekar> zahteviZaOdmorOdsustvoLekara = new HashSet<OdmorOdsustvoLekar>();

	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<OdmorOdsustvoMedicinskaSestra> zahteviZaOdmorOdsustvoMedestre = new HashSet<OdmorOdsustvoMedicinskaSestra>();

//	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Set<Termin> listaZauzetihTermina = new HashSet<Termin>();

//	public Set<TipPregleda> getListaTipovaPregleda() {
//		return listaTipovaPregleda;
//	}
//
//	public void setListaTipovaPregleda(Set<TipPregleda> listaTipovaPregleda) {
//		this.listaTipovaPregleda = listaTipovaPregleda;
//	}
//
//	public Set<OdmorOdsustvoLekar> getZahteviZaOdmorOdsustvoLekara() {
//		return zahteviZaOdmorOdsustvoLekara;
//	}
//
//	public void setZahteviZaOdmorOdsustvoLekara(Set<OdmorOdsustvoLekar> zahteviZaOdmorOdsustvoLekara) {
//		this.zahteviZaOdmorOdsustvoLekara = zahteviZaOdmorOdsustvoLekara;
//	}
//
//	public Set<OdmorOdsustvoMedicinskaSestra> getZahteviZaOdmorOdsustvoMedestre() {
//		return zahteviZaOdmorOdsustvoMedestre;
//	}
//
//	public void setZahteviZaOdmorOdsustvoMedestre(Set<OdmorOdsustvoMedicinskaSestra> zahteviZaOdmorOdsustvoMedestre) {
//		this.zahteviZaOdmorOdsustvoMedestre = zahteviZaOdmorOdsustvoMedestre;
//	}
//
//	public String getNaziv() {
//		return naziv;
//	}
//	
//	public Set<Pacijent> getListaPacijenata() {
//		return listaPacijenata;
//	}
//
//	public void setListaPacijenata(Set<Pacijent> listaPacijenata) {
//		this.listaPacijenata = listaPacijenata;
//	}
//
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public IzvestajOKlinici getIzvestajOKlinici() {
//		return izvestajOKlinici;
//	}
//	public void setIzvestajOKlinici(IzvestajOKlinici izvestajOKlinici) {
//		this.izvestajOKlinici = izvestajOKlinici;
//	}
//	public int getOcena() {
//		return ocena;
//	}
//	public void setOcena(int ocena) {
//		this.ocena = ocena;
//	}
//	public void setNaziv(String naziv) {
//		this.naziv = naziv;
//	}
//	public String getAdresa() {
//		return adresa;
//	}
//	public void setAdresa(String adresa) {
//		this.adresa = adresa;
//	}
//	public String getOpis() {
//		return opis;
//	}
//	public void setOpis(String opis) {
//		this.opis = opis;
//	}
//	public Set<Lekar> getListaLekara() {
//		return listaLekara;
//	}
//	public void setListaLekara(Set<Lekar> listaLekara) {
//		this.listaLekara = listaLekara;
//	}
//	public Set<MedicinskaSestra> getListaMedSestara() {
//		return listaMedSestara;
//	}
//	public void setListaMedSestara(Set<MedicinskaSestra> listaMedSestara) {
//		this.listaMedSestara = listaMedSestara;
//	}
//	public Set<Sala> getListaSala() {
//		return listaSala;
//	}
//	public void setListaSala(Set<Sala> listaSala) {
//		this.listaSala = listaSala;
//	}
//	public Set<AdministratorKlinike> getListaAdminKlinike() {
//		return listaAdminKlinike;
//	}
//	public void setListaAdminKlinike(Set<AdministratorKlinike> listaAdminKlinike) {
//		this.listaAdminKlinike = listaAdminKlinike;
//	}

//	public Set<Termin> getListaZauzetihTermina() {
//		return listaZauzetihTermina;
//	}
//
//
//	public void setListaZauzetihTermina(Set<Termin> listaZauzetihTermina) {
//		this.listaZauzetihTermina = listaZauzetihTermina;
//	}

//	public Klinika() {
//		super();
//	}
//	@Override
//	public boolean equals(Object obj) {
//		// TODO Auto-generated method stub
//		return super.equals(obj);
//	}
//	@Override
//	public int hashCode() {
//		// TODO Auto-generated method stub
//		return super.hashCode();
//	}
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return super.toString();
//	}
//	public Set<Pregled> getListaPregleda() {
//		return listaPregleda;
//	}
//	public void setListaPregleda(Set<Pregled> listaPregleda) {
//		this.listaPregleda = listaPregleda;
//	}
//	public Set<Operacija> getListaOperacija() {
//		return listaOperacija;
//	}
//	public void setListaOperacija(Set<Operacija> listaOperacija) {
//		this.listaOperacija = listaOperacija;
//	}
//	public KlinickiCentar getKlinickiCentar() {
//		return klinickiCentar;
//	}
//	public void setKlinickiCentar(KlinickiCentar klinickiCentar) {
//		this.klinickiCentar = klinickiCentar;
//	}
//
//	public Long getVersion() {
//		return version;
//	}
//
//	public void setVersion(Long version) {
//		this.version = version;
//	}
//	
//	

}
