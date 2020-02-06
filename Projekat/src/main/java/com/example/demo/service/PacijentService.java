package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pacijent;
import com.example.demo.repository.PacijentRepository;

@Service
public class PacijentService {

	@Autowired
	private PacijentRepository pacijentRepository;

	public Pacijent findByLbo(String lbo) {
		return pacijentRepository.findOneByLbo(lbo);
	}
	public Pacijent findByEmail(String email) {
		return pacijentRepository.findByEmail(email);
	}
	public Pacijent findByID(Long id) {
		return pacijentRepository.findById(id).orElseGet(null);
	}
	public List<Pacijent> findAll() {
		return pacijentRepository.findAll();
	}
	public Pacijent save(Pacijent pacijent) {
		return pacijentRepository.save(pacijent);
	}
	public void delete(Pacijent pacijent) {
		pacijentRepository.delete(pacijent);
	}
	
	public Pacijent findByEmailAndLozinka(String email, String lozinka) {
		return pacijentRepository.findByEmailAndLozinka(email, lozinka);
	}
	

	
}
