package com.example.demo.model;

import java.sql.Time;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class OdmorOdsustvoMedicinskaSestra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Time trajanje;
	private boolean status;
	private Enum tip;
	private MedicinskaSestra medicinskaSestra;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Time getTrajanje() {
		return trajanje;
	}
	public void setTrajanje(Time trajanje) {
		this.trajanje = trajanje;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
