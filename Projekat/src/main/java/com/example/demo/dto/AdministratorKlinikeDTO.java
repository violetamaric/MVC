package com.example.demo.dto;

import com.example.demo.model.AdministratorKlinike;

public class AdministratorKlinikeDTO {
	
	private Long id;
	private String ime;
	private String prezime;
	private String email;

	private String lozinka;
	private Long idKlinike;
	private String nazivKlinike;

	private String telefon;

	//nisam sigurna
//	private String nazivKlinike; 
	
	public AdministratorKlinikeDTO() {
		super();
	}

	public AdministratorKlinikeDTO(AdministratorKlinike adminKlinike) {
		super();
		this.id = adminKlinike.getId();
		this.ime = adminKlinike.getIme();
		this.prezime = adminKlinike.getPrezime();
		this.email = adminKlinike.getEmail();
    this.telefon = adminKlinike.getTelefon();
		this.lozinka = adminKlinike.getLozinka();
		this.idKlinike = adminKlinike.getKlinika().getId();
		this.nazivKlinike = adminKlinike.getKlinika().getNaziv();
	}
	
	
	public AdministratorKlinikeDTO(Long id, String ime, String prezime, String email, String telefon, String lozinka, Long idKlinike, String nazivKlinike) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;

		this.lozinka = lozinka;
		this.idKlinike = idKlinike;

		this.telefon = telefon;
		this.nazivKlinike = nazivKlinike;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	
	
	public String getNazivKlinike() {
		return nazivKlinike;
	}
	public void setNazivKlinike(String nazivKlinike) {
		this.nazivKlinike = nazivKlinike;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public Long getIdKlinike() {
		return idKlinike;
	}

	public void setIdKlinike(Long idKlinike) {
		this.idKlinike = idKlinike;
	}
	
	
	
}
