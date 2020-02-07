package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.SlobodniTermin;

public class SlobodniTerminDTO {

	private Long id;

	private Date datum;

	private Long tipPregledaID;
	private String tipPregledaN;

	private Long lekarID;
	private String lekarIme;
	private String lekarPrezime;

	private Long klinikaID;
	private String klinikaN;

	private Long salaID;
	private String salaN;
	private int salaBR;

	private double cena;

	private boolean status;

	private double popust;

	private int termin;

	public SlobodniTerminDTO() {
		super();

	}

	public SlobodniTerminDTO(Long id, Date datum, Long tipPregledaID, String tipPregledaN, Long lekarID,
			String lekarIme, String lekarPrezime, Long klinikaID, String klinikaN, double cena, boolean status,
			double popust, int termin, Long salaID, String salaN, int salaBR) {
		super();
		this.id = id;
		this.datum = datum;
		this.tipPregledaID = tipPregledaID;
		this.tipPregledaN = tipPregledaN;
		this.lekarID = lekarID;
		this.lekarIme = lekarIme;
		this.lekarPrezime = lekarPrezime;
		this.klinikaID = klinikaID;
		this.klinikaN = klinikaN;
		this.cena = cena;
		this.status = status;
		this.popust = popust;
		this.termin = termin;
		this.salaBR = salaBR;
		this.salaID = salaID;
		this.salaN = salaN;
	}

	@Override
	public String toString() {
		return "SlobodniTerminDTO [id=" + id + ", datum=" + datum + ", tipPregledaID=" + tipPregledaID
				+ ", tipPregledaN=" + tipPregledaN + ", lekarID=" + lekarID + ", lekarIme=" + lekarIme
				+ ", lekarPrezime=" + lekarPrezime + ", klinikaID=" + klinikaID + ", klinikaN=" + klinikaN + ", cena="
				+ cena + ", status=" + status + ", popust=" + popust + "]";
	}

	public SlobodniTerminDTO(SlobodniTermin st) {
		this.id = st.getId();
		this.datum = st.getDatum();
		this.cena = st.getCena();
		this.status = st.isStatus();
		this.popust = st.getPopust();
		if (st.getTipPregleda() != null) {
			this.tipPregledaID = st.getTipPregleda().getId();
			this.tipPregledaN = st.getTipPregleda().getNaziv();
		}
		if (st.getKlinika() != null) {
			this.klinikaID = st.getKlinika().getId();
			this.klinikaN = st.getKlinika().getNaziv();
		}
		if (st.getLekar() != null) {
			this.lekarIme = st.getLekar().getIme();
			this.lekarPrezime = st.getLekar().getPrezime();
			this.lekarID = st.getLekar().getId();
		}
		if (st.getSala() != null) {
			this.salaBR = st.getSala().getBroj();
			this.salaN = st.getSala().getNaziv();
			this.salaID = st.getSala().getId();

		}

		this.termin = st.getTermin();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Long getTipPregledaID() {
		return tipPregledaID;
	}

	public void setTipPregledaID(Long tipPregledaID) {
		this.tipPregledaID = tipPregledaID;
	}

	public Long getLekarID() {
		return lekarID;
	}

	public void setLekarID(Long lekarID) {
		this.lekarID = lekarID;
	}

	public Long getKlinikaID() {
		return klinikaID;
	}

	public void setKlinikaID(Long klinikaID) {
		this.klinikaID = klinikaID;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public double getPopust() {
		return popust;
	}

	public void setPopust(double popust) {
		this.popust = popust;
	}

	public String getTipPregledaN() {
		return tipPregledaN;
	}

	public void setTipPregledaN(String tipPregledaN) {
		this.tipPregledaN = tipPregledaN;
	}

	public String getLekarIme() {
		return lekarIme;
	}

	public void setLekarIme(String lekarIme) {
		this.lekarIme = lekarIme;
	}

	public String getLekarPrezime() {
		return lekarPrezime;
	}

	public void setLekarPrezime(String lekarPrezime) {
		this.lekarPrezime = lekarPrezime;
	}

	public String getKlinikaN() {
		return klinikaN;
	}

	public void setKlinikaN(String klinikaN) {
		this.klinikaN = klinikaN;
	}

	public int getTermin() {
		return termin;
	}

	public void setTermin(int termin) {
		this.termin = termin;
	}

	public Long getSalaID() {
		return salaID;
	}

	public void setSalaID(Long salaID) {
		this.salaID = salaID;
	}

	public String getSalaN() {
		return salaN;
	}

	public void setSalaN(String salaN) {
		this.salaN = salaN;
	}

	public int getSalaBR() {
		return salaBR;
	}

	public void setSalaBR(int salaBR) {
		this.salaBR = salaBR;
	}

}
