package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Pacijent;
import com.example.demo.repository.MedicinskaSestraRepository;

@Service
public class MedicinskaSestraService {
	@Autowired
	private MedicinskaSestraRepository medicinskaSestraRepository;
	
	public MedicinskaSestra findByEmailAndLozinka(String email, String lozinka) {
		return medicinskaSestraRepository.findByEmailAndLozinka(email, lozinka);
	}
	
}
