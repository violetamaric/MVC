package com.example.demo.dto;

import com.example.demo.model.Klinika;
//@Builder
public class KlinikaDTO {

	private Long id;
	
	private String naziv;
	private String adresa;
	private String opis;
	private int ocena;
	private int status;
//	private ArrayList<Long> listaAdministratoraKlinike = new ArrayList<Long>(); 
	//lista id-eva administratora klinike
	
	public KlinikaDTO() {
		super();
	}

	public KlinikaDTO(Long id, String naziv, String adresa, String opis, int ocena, int status) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.ocena = ocena;
		this.status = status;
		
	}
//	public KlinikaDTO(Long id, String naziv, String adresa, String opis, int ocena, ArrayList<Long> lista) {
//		super();
//		this.id = id;
//		this.naziv = naziv;
//		this.adresa = adresa;
//		this.opis = opis;
//		this.ocena = ocena;
//		this.listaAdministratoraKlinike = lista;
//	}

	public KlinikaDTO(Klinika klinika) {
		super();
		
		this.id = klinika.getId();
		this.naziv = klinika.getNaziv();
		this.adresa = klinika.getAdresa();
		this.opis = klinika.getOpis();
		this.ocena = klinika.getOcena();
		this.status = klinika.getStatus();
//		for (AdministratorKlinike ak : klinika.getListaAdminKlinike()) {
//			this.listaAdministratoraKlinike.add(ak.getId());
//		}
		
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

//	public ArrayList<Long> getListaAdministratoraKlinike() {
//		return listaAdministratoraKlinike;
//	}
//
//	public void setListaAdministratoraKlinike(ArrayList<Long> listaAdministratoraKlinike) {
//		this.listaAdministratoraKlinike = listaAdministratoraKlinike;
//	} 
	
	
	
}
