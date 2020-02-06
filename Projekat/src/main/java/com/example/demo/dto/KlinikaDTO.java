package com.example.demo.dto;

import java.util.ArrayList;
import java.util.Set;

import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.KlinickiCentar;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.OdmorOdsustvoLekar;
import com.example.demo.model.OdmorOdsustvoMedicinskaSestra;
import com.example.demo.model.Operacija;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.Sala;
import com.example.demo.model.TipPregleda;
import com.example.demo.model.Klinika.KlinikaBuilder;

import lombok.Builder;
//@Builder
public class KlinikaDTO {

	private Long id;
	
	private String naziv;
	private String adresa;
	private String opis;
	private int ocena;
//	private ArrayList<Long> listaAdministratoraKlinike = new ArrayList<Long>(); 
	//lista id-eva administratora klinike
	
	public KlinikaDTO() {
		super();
	}

	public KlinikaDTO(Long id, String naziv, String adresa, String opis, int ocena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.ocena = ocena;
		
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

//	public ArrayList<Long> getListaAdministratoraKlinike() {
//		return listaAdministratoraKlinike;
//	}
//
//	public void setListaAdministratoraKlinike(ArrayList<Long> listaAdministratoraKlinike) {
//		this.listaAdministratoraKlinike = listaAdministratoraKlinike;
//	} 
	
	
	
}
