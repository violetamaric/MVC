package com.example.demo.model;

import java.sql.Time;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Pregled {

 //dodati satnicu pregleda i salu za brze preglede koje bira pacijent

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "datum", nullable = false)
//	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date datum;
	
	@Column(name = "termin", nullable = false)
	private int termin;

	//treba false ali kasnije promjeniti
	@Column(name = "trajanje", nullable = true)
	private Time trajanje; // dateTime

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private TipPregleda tipPregleda;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	private Sala sala;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Lekar lekar;

//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	private MedicinskaSestra medicinskaSestra;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Pacijent pacijent;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Klinika klinika;

	@Column(name = "cena", nullable = false)
	private double cena;

	//0-nije ni potvrdjeno ni odbijeno
	//1-potvrdjeno
	//2-odbijeno
	//3-zavrsen pregled
	//4-ocenjena samo klinika
	//5-ocenjen samo lekar
	//6-ocenjen i lekar i klinika
	@Column(name = "status", nullable = false)
	private int status;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "izvestajOPregledu_id")
	private IzvestajOPregledu izvestajOPregledu;

	public Pregled() {
		super();
	}

	public Pregled(Long id, Date datum, int termin, Time trajanje, TipPregleda tipPregleda, Sala sala, Lekar lekar,
			Pacijent pacijent, Klinika klinika, double cena, int status, IzvestajOPregledu izvestajOPregledu) {
		this.id = id;
		this.datum = datum;
		this.termin = termin;
		this.trajanje = trajanje;
		this.tipPregleda = tipPregleda;
		this.sala = sala;
		this.lekar = lekar;
		this.pacijent = pacijent;
		this.klinika = klinika;
		this.cena = cena;
		this.status = status;
		this.izvestajOPregledu = izvestajOPregledu;
	}

	public Pregled(Pregled p) {
		// TODO Auto-generated constructor stub
		this.id = p.getId();
		this.datum = p.getDatum();
		this.termin = p.getTermin();
		this.trajanje = p.getTrajanje();
		this.tipPregleda = p.getTipPregleda();
		this.sala = p.getSala();
		this.lekar = p.getLekar();
		this.pacijent = p.getPacijent();
		this.klinika = p.getKlinika();
		this.cena = p.getCena();
		this.status = p.getStatus();
		this.izvestajOPregledu = p.getIzvestajOPregledu();
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
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

	public int getTermin() {
		return termin;
	}

	public void setTermin(int termin) {
		this.termin = termin;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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



	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	// public MedicinskaSestra getMedicinskaSestra() {
//		return medicinskaSestra;
//	}
//	public void setMedicinskaSestra(MedicinskaSestra medicinskaSestra) {
//		this.medicinskaSestra = medicinskaSestra;
//	}
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

	public IzvestajOPregledu getIzvestajOPregledu() {
		return izvestajOPregledu;
	}

	public void setIzvestajOPregledu(IzvestajOPregledu izvestajOPregledu) {
		this.izvestajOPregledu = izvestajOPregledu;
	}

}
