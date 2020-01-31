package com.example.demo.dto;

import com.example.demo.model.Recept;

public class ReceptDTO {
	private Long id;
	private Long lekID;
	private Long medSesID;
	private boolean overen;
	private Long izvestajID;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLekID() {
		return lekID;
	}
	public void setLekID(Long lekID) {
		this.lekID = lekID;
	}
	public Long getMedSesID() {
		return medSesID;
	}
	public void setMedSesID(Long medSesID) {
		this.medSesID = medSesID;
	}
	public boolean isOveren() {
		return overen;
	}
	public void setOveren(boolean overen) {
		this.overen = overen;
	}
	public Long getIzvestajID() {
		return izvestajID;
	}
	public void setIzvestajID(Long izvestajID) {
		this.izvestajID = izvestajID;
	}
	public ReceptDTO(Long id, Long lekID, Long medSesID, boolean overen, Long izvestajID) {
		super();
		this.id = id;
		this.lekID = lekID;
		this.medSesID = medSesID;
		this.overen = overen;
		this.izvestajID = izvestajID;
	}
	public ReceptDTO(Recept r) {
		super();
		this.id = r.getId();
		this.lekID = r.getLek().getId();
		this.medSesID = r.getMedicinskaSestra().getId();
		this.overen = r.isOveren();
		this.izvestajID = r.getIzvestajOPregledu().getId();
	}
	
	
	
}
