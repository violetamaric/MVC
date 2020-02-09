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

	@Column(name = "status", nullable = false)
	private int status;

	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY)
	private Set<Pregled> listaPregleda = new HashSet<Pregled>();

	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY)
	private Set<Operacija> listaOperacija = new HashSet<Operacija>();

	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY)
	private Set<Lekar> listaLekara = new HashSet<Lekar>();

	@ManyToMany(mappedBy = "listaKlinika")
	private Set<TipPregleda> listaTipovaPregleda = new HashSet<TipPregleda>();

	@ManyToMany
	@JoinTable(name = "klinika_pacijent", joinColumns = @JoinColumn(name = "klinika_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "pacijent_id", referencedColumnName = "id"))
	private Set<Pacijent> listaPacijenata = new HashSet<Pacijent>();

	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY)
	private Set<MedicinskaSestra> listaMedSestara = new HashSet<MedicinskaSestra>();

	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY)
	private Set<Sala> listaSala = new HashSet<Sala>();

	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY)
	private Set<AdministratorKlinike> listaAdminKlinike = new HashSet<AdministratorKlinike>();

	@ManyToOne(fetch = FetchType.EAGER)
	private KlinickiCentar klinickiCentar;

	@Column(name = "ocena", nullable = false)
	private int ocena;

	@Version
	private Long version;

	public Klinika(String naziv, String adresa, String opis, int ocena, int status) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.ocena = ocena;
		this.status = status;
	}

	public Klinika(String naziv, String adresa, String opis, KlinickiCentar klinickiCentar, int ocena, int status) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.klinickiCentar = klinickiCentar;
		this.ocena = ocena;
		this.status = status;
	}

	public Klinika(Long id, String naziv, String adresa, String opis, int status, Set<Pregled> listaPregleda,
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
		this.status = status;
		this.zahteviZaOdmorOdsustvoLekara = zahteviZaOdmorOdsustvoLekara;
		this.zahteviZaOdmorOdsustvoMedestre = zahteviZaOdmorOdsustvoMedestre;
	}

	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<OdmorOdsustvoLekar> zahteviZaOdmorOdsustvoLekara = new HashSet<OdmorOdsustvoLekar>();

	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<OdmorOdsustvoMedicinskaSestra> zahteviZaOdmorOdsustvoMedestre = new HashSet<OdmorOdsustvoMedicinskaSestra>();

}
