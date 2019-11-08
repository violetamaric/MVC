package model;

import java.util.ArrayList;

public class ZdravstveniKarton {
	
	private String lbo; //kljuc
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
	
	
	
	
}
