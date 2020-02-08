package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.Termin;

public class TerminDTO {

	private Long id;

	private Date datumPocetka;

	private Integer termin;

	private int salaBR;

	private Long salaID;

	private String salaN;

//	private Long klinikaID;
//	
//	private String nazivKl;

	
	
	public TerminDTO(Termin termin) {
		this.id = termin.getId();
		this.datumPocetka = termin.getDatumPocetka();
		this.termin = termin.getTermin();
		this.salaBR = termin.getSala().getBroj();
		this.salaID = termin.getSala().getId();
		this.salaN = termin.getSala().getNaziv();
//		this.klinikaID = termin.getKlinika().getId();
//		this.nazivKl = termin.getKlinika().getNaziv();
	}

	public TerminDTO() {
}

	public TerminDTO(Long id, Date datumPocetka, Integer termin, int salaBR, Long salaID, String salaN) {
		super();
		this.id = id;
		this.datumPocetka = datumPocetka;
		this.termin = termin;
		this.salaBR = salaBR;
		this.salaID = salaID;
		this.salaN = salaN;
//		this.klinikaID = klinikaID;
//		this.nazivKl = nazivKl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatumPocetka() {
		return datumPocetka;
	}

	public void setDatumPocetka(Date datumPocetka) {
		this.datumPocetka = datumPocetka;
	}

	public Integer getTermin() {
		return termin;
	}

	public void setTermin(Integer termin) {
		this.termin = termin;
	}

	public int getSalaBR() {
		return salaBR;
	}

	public void setSalaBR(int salaBR) {
		this.salaBR = salaBR;
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

	@Override
	public String toString() {
		return "TerminDTO [id=" + id + ", datumPocetka=" + datumPocetka + ", termin=" + termin + ", salaBR=" + salaBR
				+ ", salaID=" + salaID + ", salaN=" + salaN + "]";
	}

//	public Long getKlinikaID() {
//		return klinikaID;
//	}
//
//	public void setKlinikaID(Long klinikaID) {
//		this.klinikaID = klinikaID;
//	}
//
//	public String getNazivKl() {
//		return nazivKl;
//	}
//
//	public void setNazivKl(String nazivKl) {
//		this.nazivKl = nazivKl;
//	}

}
