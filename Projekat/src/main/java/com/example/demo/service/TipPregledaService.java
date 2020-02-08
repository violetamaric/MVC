package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Klinika;
import com.example.demo.model.Sala;
import com.example.demo.model.TipPregleda;
import com.example.demo.repository.TipPregledaRepository;

@Service
public class TipPregledaService {

	@Autowired
	private TipPregledaRepository TPRepository;
	
	public List<TipPregleda> findAll(){
		return TPRepository.findAll();
	}
	public List<TipPregleda>findByIdKlinike(Long id){
		return TPRepository.findByIdKlinike(id);
	}

	
	public TipPregleda findOne(Long id) {
		return TPRepository.findById(id).orElseGet(null);
	}
	public TipPregleda findByNaziv(String naziv) {
		return TPRepository.findByNaziv(naziv);
	}
	public TipPregleda save(TipPregleda klinika) {
		return TPRepository.save(klinika);
	}
	public void delete(TipPregleda klinika) {
		TPRepository.delete(klinika);
	}
}
