package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Lekar;
import com.example.demo.model.Operacija;
import com.example.demo.repository.LekarRepository;
import com.example.demo.repository.OperacijaRepository;

@Service
public class OperacijaService {
	@Autowired
	private OperacijaRepository operacijaRepository;
	
	@Autowired 
	private LekarRepository lekarRepository;
	
	public List<Operacija> findAll() {
		return operacijaRepository.findAll();
	}
	
	public Operacija save(Operacija Operacija) {
		return operacijaRepository.save(Operacija);
	}
	
	public void delete(Operacija Operacija) {
		operacijaRepository.delete(Operacija);
	}

	public Operacija findOne(Long id) {
		return operacijaRepository.findById(id).orElseGet(null);
	}

	public Operacija findById(Long id) {
		return operacijaRepository.findById(id).orElseGet(null);
	}

	public List<Lekar> findLekare(Long id) {
		return lekarRepository.findLekare(id);
	}
	
}
