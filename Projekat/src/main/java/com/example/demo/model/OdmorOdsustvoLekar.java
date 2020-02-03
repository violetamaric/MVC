package com.example.demo.model;

import java.sql.Time;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@Entity
public class OdmorOdsustvoLekar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
    @Column(updatable = false)
	private Date datumOd;

	@JsonFormat(pattern = "yyyy-MM-dd")
//	@Column(name="do", nullable=false)
	@Column(updatable = false)
	private Date datumDo;
	
	@Column(name="opis", nullable=false)
	private String opis;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Lekar lekar; 
	
	
	//0-ni odobreno ni odbijeno
	//1-odobreno
	//odbijeno
	@Column(name="status", nullable=false)
	private int status; //odobreno-1, odbijeno-0
	
	@Column(name="tip", nullable=false)
	private TipOdmorOdsustvo tip;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Klinika klinika;
	
//	private Time trajanje;
	
	
	public Long getId() {
		return id;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Lekar getLekar() {
		return lekar;
	}
	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}
	public Date getDatumOd() {
		return datumOd;
	}
	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}
	public Date getDatumDo() {
		return datumDo;
	}
	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public TipOdmorOdsustvo getTip() {
		return tip;
	}
	public void setTip(TipOdmorOdsustvo tip) {
		this.tip = tip;
	}
	public OdmorOdsustvoLekar() {
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
}
