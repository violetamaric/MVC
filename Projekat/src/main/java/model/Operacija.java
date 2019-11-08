package model;

import java.sql.Time;
import java.util.Date;

public class Operacija {
	
	private Date datum;
	private String tipOperacije;
	private Time trajanje;
	private Sala sala;
	private Lekar lekar;
	private double cena;
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public String getTipOperacije() {
		return tipOperacije;
	}
	public void setTipOperacije(String tipOperacije) {
		this.tipOperacije = tipOperacije;
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
	public Operacija() {
		super();
	}
	
	
}
