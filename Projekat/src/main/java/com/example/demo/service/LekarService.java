package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Lek;
import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;
import com.example.demo.repository.LekarRepository;




@Service
public class LekarService {

	@Autowired
	private LekarRepository lekarRepository;
	
	public Lekar findOne(Long id) {
		return lekarRepository.findById(id).orElseGet(null);
	}
	public Lekar findById(Long id) {
		return lekarRepository.findById(id).orElseGet(null);
	}
	public Lekar findByEmail(String email) {
		return lekarRepository.findByEmail(email);
	}
	
	public List<Lekar> findAll() {
		return lekarRepository.findAll();
	}
	
	public Lekar save(Lekar lekar) {
		return lekarRepository.save(lekar);
	}
	public void delete(Lekar lekar) {
		lekarRepository.delete(lekar);
	}
	public Lekar findByEmailAndLozinka(String email, String lozinka) {
		return lekarRepository.findByEmailAndLozinka(email, lozinka);
	}
}
