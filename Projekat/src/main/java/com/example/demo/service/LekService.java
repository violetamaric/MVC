package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Lek;
import com.example.demo.repository.LekRepository;

@Service
public class LekService {
	
	@Autowired
	private LekRepository lekRepository;
	
	public Lek save(Lek lek) {
		return lekRepository.save(lek);
	}
	public void delete(Lek lek) {
		lekRepository.delete(lek);
	}
	public Lek findById(Long id) {
		return lekRepository.findById(id).orElseGet(null);
	}
}
