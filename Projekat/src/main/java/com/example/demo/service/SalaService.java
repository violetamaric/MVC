package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pregled;
import com.example.demo.model.Sala;
import com.example.demo.repository.SalaRepository;

@Service
public class SalaService {
	@Autowired
	private SalaRepository salaRepository;
	
	
	public Sala findOne(Long id) {
		return salaRepository.findById(id).orElseGet(null);
	}
	public Sala findById(Long id) {
		return salaRepository.findById(id).orElseGet(null);
	}
	
	public List<Sala> findAll() {
		return salaRepository.findAll();
	}
	
	public Sala save(Sala sala) {
		return salaRepository.save(sala);
	}
	public void delete(Sala sala) {
		salaRepository.delete(sala);
	}
}
