package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.MedicinskaSestra;
import com.example.demo.repository.MedicinskaSestraRepository;

@Service
public class MedicinskaSestraService {
	@Autowired
	private MedicinskaSestraRepository medicinskaSestraRepository;
	
	public List<MedicinskaSestra> findAll(){
		return medicinskaSestraRepository.findAll();
	}
	
	public MedicinskaSestra findById(Long id) {
		return medicinskaSestraRepository.findById(id).orElseGet(null);
	}
	
	public MedicinskaSestra findByEmail(String email) {
		return medicinskaSestraRepository.findByEmail(email);
	}
	
	public MedicinskaSestra findByEmailAndLozinka(String email, String lozinka) {
		return medicinskaSestraRepository.findByEmailAndLozinka(email, lozinka);
	}
	
	public MedicinskaSestra save(MedicinskaSestra medicinskaSestra) {
		return medicinskaSestraRepository.save(medicinskaSestra);
	}
	
	
}
