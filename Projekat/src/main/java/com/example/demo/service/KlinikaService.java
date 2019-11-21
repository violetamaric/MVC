package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Klinika;
import com.example.demo.repository.KlinikaRepository;

@Service
public class KlinikaService {
	@Autowired
	private KlinikaRepository klinikaRepository;

	public Klinika findByNaziv(String naziv) {
		return klinikaRepository.findByNaziv(naziv);
	}
	
	public Klinika findByAdresa(String adresa) {
		return klinikaRepository.findByAdresa(adresa);
	}
	
	public List<Klinika> findAll() {
		return klinikaRepository.findAll();
	}
}


