package com.example.demo.dto;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.model.ZdravstveniKarton;

public class ZdravstveniKartonDTO {

	private Long id;
	private double visina;
	private double tezina;
	private String krvnaGrupa;
	private Long pacijentID;
	private String imeP;
	private String prezimeP;
	private Set<IzvestajOPregleduDTO> listaIzvestaja = new HashSet<IzvestajOPregleduDTO>();

	public ZdravstveniKartonDTO() {
		super();
	}

	public ZdravstveniKartonDTO(ZdravstveniKarton zk) {
		this.id = zk.getId();
		this.visina = zk.getVisina();
		this.tezina = zk.getTezina();
		this.krvnaGrupa = zk.getKrvnaGrupa();
//		if(zk.getPacijent() != null) {
		this.pacijentID = zk.getPacijent().getId();
		this.imeP = zk.getPacijent().getIme();
		this.prezimeP = zk.getPacijent().getPrezime();
//		}

		for (IzvestajOPregledu IOP : zk.getListaIzvestajaOPregledu()) {
			this.listaIzvestaja.add(new IzvestajOPregleduDTO(IOP));
		}

	}

	public ZdravstveniKartonDTO(Long id, double visina, double tezina, String krvnaGrupa, Long pacijentID, String imeP,
			String prezimeP, Set<IzvestajOPregleduDTO> listaIzvestaja) {
		super();
		this.id = id;
		this.visina = visina;
		this.tezina = tezina;
		this.krvnaGrupa = krvnaGrupa;
		this.pacijentID = pacijentID;
		this.imeP = imeP;
		this.prezimeP = prezimeP;
		this.listaIzvestaja = listaIzvestaja;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getVisina() {
		return visina;
	}

	public void setVisina(double visina) {
		this.visina = visina;
	}

	public double getTezina() {
		return tezina;
	}

	public void setTezina(double tezina) {
		this.tezina = tezina;
	}

	public String getKrvnaGrupa() {
		return krvnaGrupa;
	}

	public void setKrvnaGrupa(String krvnaGrupa) {
		this.krvnaGrupa = krvnaGrupa;
	}

	public Long getPacijentID() {
		return pacijentID;
	}

	public void setPacijentID(Long pacijentID) {
		this.pacijentID = pacijentID;
	}

	public String getImeP() {
		return imeP;
	}

	public void setImeP(String imeP) {
		this.imeP = imeP;
	}

	public String getPrezimeP() {
		return prezimeP;
	}

	public void setPrezimeP(String prezimeP) {
		this.prezimeP = prezimeP;
	}

	public Set<IzvestajOPregleduDTO> getListaIzvestaja() {
		return listaIzvestaja;
	}

	public void setListaIzvestaja(Set<IzvestajOPregleduDTO> listaIzvestaja) {
		this.listaIzvestaja = listaIzvestaja;
	}

	@Override
	public String toString() {
		return "ZdravstveniKartonDTO [id=" + id + ", visina=" + visina + ", tezina=" + tezina + ", krvnaGrupa="
				+ krvnaGrupa + ", pacijentID=" + pacijentID + ", imeP=" + imeP + ", prezimeP=" + prezimeP
				+ ", listaIzvestaja=" + listaIzvestaja + "]";
	}

}
