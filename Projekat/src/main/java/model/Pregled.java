package model;

import java.sql.Time;
import java.util.Date;

public class Pregled {
	
	private Date datum;
	private String tipPregleda;
	private Time trajanje;
	private Sala sala;
	private Lekar lekar;
	private double cena;
	
	
	public Pregled() {
		super();
	}
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public String getTipPregleda() {
		return tipPregleda;
	}
	public void setTipPregleda(String tipPregleda) {
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
	
	
}
