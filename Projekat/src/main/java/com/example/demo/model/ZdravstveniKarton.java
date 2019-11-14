package com.example.demo.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ZdravstveniKarton {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="lbo", nullable=false)
	private String lbo; 
	
	private ArrayList<Pregled> listaPregleda;
	private ArrayList<Operacija> listaOperacija;
	
	public ZdravstveniKarton() {
		super();
	}

	public String getLbo() {
		return lbo;
	}

	public void setLbo(String lbo) {
		this.lbo = lbo;
	}

	public ArrayList<Pregled> getListaPregleda() {
		return listaPregleda;
	}

	public void setListaPregleda(ArrayList<Pregled> listaPregleda) {
		this.listaPregleda = listaPregleda;
	}

	public ArrayList<Operacija> getListaOperacija() {
		return listaOperacija;
	}

	public void setListaOperacija(ArrayList<Operacija> listaOperacija) {
		this.listaOperacija = listaOperacija;
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
