package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
//@Transactional
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ZdravstveniKarton {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="visina", nullable=true)
	private double visina;
	
	@Column(name="tezina", nullable=true)
	private double tezina;
	
	@Column(name="krvnaGrupa", nullable=true)
	private String krvnaGrupa;
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Pacijent pacijent;
	
	@OneToMany(mappedBy = "zdravstveniKarton", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<IzvestajOPregledu> listaIzvestajaOPregledu = new HashSet<IzvestajOPregledu>();
	
//	@OneToMany(mappedBy = "zdravstveniKarton", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Set<Operacija> listaOperacija = new HashSet<Operacija>();
	
	public ZdravstveniKarton() {
		super();
	}
	public ZdravstveniKarton(ZdravstveniKarton zk) {
		this.id = zk.getId();
		this.visina = zk.getVisina();
		this.tezina = zk.getTezina();
		this.pacijent = zk.getPacijent();
		this.krvnaGrupa = zk.getKrvnaGrupa();
	}
	

	public String getKrvnaGrupa() {
		return krvnaGrupa;
	}
	public void setKrvnaGrupa(String krvnaGrupa) {
		this.krvnaGrupa = krvnaGrupa;
	}
//	public Set<Operacija> getListaOperacija() {
//		return listaOperacija;
//	}
//
//	public void setListaOperacija(Set<Operacija> listaOperacija) {
//		this.listaOperacija = listaOperacija;
//	}




	public Long getId() {
		return id;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((krvnaGrupa == null) ? 0 : krvnaGrupa.hashCode());
		result = prime * result + ((listaIzvestajaOPregledu == null) ? 0 : listaIzvestajaOPregledu.hashCode());
		result = prime * result + ((pacijent == null) ? 0 : pacijent.hashCode());
		long temp;
		temp = Double.doubleToLongBits(tezina);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(visina);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZdravstveniKarton other = (ZdravstveniKarton) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (krvnaGrupa == null) {
			if (other.krvnaGrupa != null)
				return false;
		} else if (!krvnaGrupa.equals(other.krvnaGrupa))
			return false;
		if (listaIzvestajaOPregledu == null) {
			if (other.listaIzvestajaOPregledu != null)
				return false;
		} else if (!listaIzvestajaOPregledu.equals(other.listaIzvestajaOPregledu))
			return false;
		if (pacijent == null) {
			if (other.pacijent != null)
				return false;
		} else if (!pacijent.equals(other.pacijent))
			return false;
		if (Double.doubleToLongBits(tezina) != Double.doubleToLongBits(other.tezina))
			return false;
		if (Double.doubleToLongBits(visina) != Double.doubleToLongBits(other.visina))
			return false;
		return true;
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


	public Pacijent getPacijent() {
		return pacijent;
	}


	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}


	public Set<IzvestajOPregledu> getListaIzvestajaOPregledu() {
		return listaIzvestajaOPregledu;
	}


	public void setListaIzvestajaOPregledu(Set<IzvestajOPregledu> listaIzvestajaOPregledu) {
		this.listaIzvestajaOPregledu = listaIzvestajaOPregledu;
	}

	
	
	
	
}
