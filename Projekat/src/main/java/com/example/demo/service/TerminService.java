package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Termin;
import com.example.demo.repository.TerminRepository;
@Service
public class TerminService {
	@Autowired
	private TerminRepository terminRepository;
	
	
	public Termin findOne(Long id) {
		return terminRepository.findById(id).orElseGet(null);
	}
	public Termin findById(Long id) {
		return terminRepository.findById(id).orElseGet(null);
	}
	
	public List<Termin> findAll() {
		return terminRepository.findAll();
	}
	
	public Termin save(Termin termin) {
		return terminRepository.save(termin);
	}
	public void delete(Termin termin) {
		terminRepository.delete(termin);
	}
	public List<Termin> zauzetiTerminiLekara(Long id, Date pocDatum, Date krajDatum) {
		return terminRepository.zauzetiTerminiLekara(id, pocDatum, krajDatum);
	}
}
