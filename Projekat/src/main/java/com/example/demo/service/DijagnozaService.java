package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Dijagnoza;
import com.example.demo.repository.DijagnozaRepository;

@Service
public class DijagnozaService {
	@Autowired
	private DijagnozaRepository dijagnozaRepository;
	
	public Dijagnoza save(Dijagnoza dijagnoza) {
		return dijagnozaRepository.save(dijagnoza);
	}
	public void delete(Dijagnoza dijagnoza) {
		dijagnozaRepository.delete(dijagnoza);
	}
	public Dijagnoza findById(Long id) {
		return dijagnozaRepository.findById(id).orElseGet(null);
	}
}
