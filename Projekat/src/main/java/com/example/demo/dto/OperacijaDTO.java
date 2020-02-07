package com.example.demo.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.model.Lekar;
import com.example.demo.model.Operacija;

public class OperacijaDTO {

	private Long id;
	private Date datum;
	private String tipOperacije;
	private String pacijentEmail;
	private String imeP;
	private String prezimeP;
	private Long pacijentID;
	private Long klinikaID;
	private String nazivKl;
	private Long salaID;
	private String salaN;
	private int salaBR;
	private double cena;
	private int status;
	private int termin;
	
	private Set<Long> listaLekara = new HashSet<>();
//	private List<Long> lekariID = new ArrayList<>();
	
	

	public OperacijaDTO(Operacija operacija) {
		this.id = operacija.getId();
		this.datum = operacija.getDatum();
		this.pacijentEmail = operacija.getPacijent().getEmail();
		this.cena = operacija.getCena();
		this.pacijentID = operacija.getPacijent().getId();
		this.prezimeP = operacija.getPacijent().getPrezime();
		this.imeP = operacija.getPacijent().getIme();
		this.klinikaID = operacija.getKlinika().getId();
		this.nazivKl = operacija.getKlinika().getNaziv();
		this.tipOperacije = operacija.getTipOperacije();
		this.salaBR = operacija.getSala().getBroj();
		this.salaID = operacija.getSala().getId();
		this.salaN = operacija.getSala().getNaziv();
		this.status = operacija.getStatus();
		this.termin = operacija.getTermin();
		for(Lekar l:operacija.getListaLekara()) {
			this.listaLekara.add(l.getId());
		}
		
	}

	public int getTermin() {
		return termin;
	}

	public void setTermin(int termin) {
		this.termin = termin;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OperacijaDTO [id=" + id + ", datum=" + datum + ", tipOperacije=" + tipOperacije + ", pacijentID="
				+ pacijentID + ", klinikaID=" + klinikaID + ", salaID=" + salaID + ", salaN=" + salaN + ", salaBR="
				+ salaBR + ", status=" + status + ", termin=" + termin + ", listaLekara=" + listaLekara + "]";
	}
	

	public OperacijaDTO() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cena);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((datum == null) ? 0 : datum.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		result = prime * result + ((imeL == null) ? 0 : imeL.hashCode());
		result = prime * result + ((imeP == null) ? 0 : imeP.hashCode());
		result = prime * result + ((klinikaID == null) ? 0 : klinikaID.hashCode());
//		result = prime * result + ((lekarID == null) ? 0 : lekarID.hashCode());
		result = prime * result + ((nazivKl == null) ? 0 : nazivKl.hashCode());
		result = prime * result + ((pacijentEmail == null) ? 0 : pacijentEmail.hashCode());
		result = prime * result + ((pacijentID == null) ? 0 : pacijentID.hashCode());
//		result = prime * result + ((prezimeL == null) ? 0 : prezimeL.hashCode());
		result = prime * result + ((prezimeP == null) ? 0 : prezimeP.hashCode());
		result = prime * result + salaBR;
		result = prime * result + ((salaID == null) ? 0 : salaID.hashCode());
		result = prime * result + ((salaN == null) ? 0 : salaN.hashCode());
		result = prime * result + ((tipOperacije == null) ? 0 : tipOperacije.hashCode());
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
		OperacijaDTO other = (OperacijaDTO) obj;
		if (Double.doubleToLongBits(cena) != Double.doubleToLongBits(other.cena))
			return false;
		if (datum == null) {
			if (other.datum != null)
				return false;
		} else if (!datum.equals(other.datum))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
//		if (imeL == null) {
//			if (other.imeL != null)
//				return false;
//		} else if (!imeL.equals(other.imeL))
//			return false;
		if (imeP == null) {
			if (other.imeP != null)
				return false;
		} else if (!imeP.equals(other.imeP))
			return false;
		if (klinikaID == null) {
			if (other.klinikaID != null)
				return false;
		} else if (!klinikaID.equals(other.klinikaID))
			return false;
//		if (lekarID == null) {
//			if (other.lekarID != null)
//				return false;
//		} else if (!lekarID.equals(other.lekarID))
//			return false;
		if (nazivKl == null) {
			if (other.nazivKl != null)
				return false;
		} else if (!nazivKl.equals(other.nazivKl))
			return false;
		if (pacijentEmail == null) {
			if (other.pacijentEmail != null)
				return false;
		} else if (!pacijentEmail.equals(other.pacijentEmail))
			return false;
		if (pacijentID == null) {
			if (other.pacijentID != null)
				return false;
		} else if (!pacijentID.equals(other.pacijentID))
			return false;
//		if (prezimeL == null) {
//			if (other.prezimeL != null)
//				return false;
//		} else if (!prezimeL.equals(other.prezimeL))
//			return false;
		if (prezimeP == null) {
			if (other.prezimeP != null)
				return false;
		} else if (!prezimeP.equals(other.prezimeP))
			return false;
		if (salaBR != other.salaBR)
			return false;
		if (salaID == null) {
			if (other.salaID != null)
				return false;
		} else if (!salaID.equals(other.salaID))
			return false;
		if (salaN == null) {
			if (other.salaN != null)
				return false;
		} else if (!salaN.equals(other.salaN))
			return false;
		if (tipOperacije == null) {
			if (other.tipOperacije != null)
				return false;
		} else if (!tipOperacije.equals(other.tipOperacije))
			return false;
		return true;
	}

	public OperacijaDTO(Long id, Date datum, String tipOperacije, Long lekarID, String imeL, String prezimeL,
			String pacijentEmail, Long klinikaID, Long pacijentID, String imeP, String prezimeP, String nazivKl,
			Long salaID, String salaN, int salaBR, double cena, int status, int termin) {
		super();
		this.id = id;
		this.datum = datum;
		this.tipOperacije = tipOperacije;
		this.pacijentEmail = pacijentEmail;
		this.klinikaID = klinikaID;
		this.pacijentID = pacijentID;
		this.imeP = imeP;
		this.prezimeP = prezimeP;
		this.nazivKl = nazivKl;
		this.salaID = salaID;
		this.salaN = salaN;
		this.salaBR = salaBR;
		this.cena = cena;
		this.status = status;
		this.termin = termin;
	}

	public Set<Long> getListaLekara() {
		return listaLekara;
	}

	public void setListaLekara(Set<Long> listaLekara) {
		this.listaLekara = listaLekara;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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


	public String getPacijentEmail() {
		return pacijentEmail;
	}

	public void setPacijentEmail(String pacijentEmail) {
		this.pacijentEmail = pacijentEmail;
	}

	public Long getKlinikaID() {
		return klinikaID;
	}

	public void setKlinikaID(Long klinikaID) {
		this.klinikaID = klinikaID;
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

	public String getNazivKl() {
		return nazivKl;
	}

	public void setNazivKl(String nazivKl) {
		this.nazivKl = nazivKl;
	}

	public Long getSalaID() {
		return salaID;
	}

	public void setSalaID(Long salaID) {
		this.salaID = salaID;
	}

	public String getSalaN() {
		return salaN;
	}

	public void setSalaN(String salaN) {
		this.salaN = salaN;
	}

	public int getSalaBR() {
		return salaBR;
	}

	public void setSalaBR(int salaBR) {
		this.salaBR = salaBR;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}
	
	

}
