package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Lekar;
import com.example.demo.repository.AdministratorKlinikeRepository;

@Service
public class AdministratorKlinikeService {

	@Autowired
	private AdministratorKlinikeRepository administratorKRepository;
	
	public AdministratorKlinike findOne(Long id) {
		return administratorKRepository.findById(id).orElseGet(null);
	}
	
	public AdministratorKlinike findByEmailAndLozinka(String email, String lozinka) {
		return administratorKRepository.findByEmailAndLozinka(email, lozinka);
	}
	
	public AdministratorKlinike findByEmail(String email) {
		return administratorKRepository.findByEmail(email);
	}
	
	public List<AdministratorKlinike> findAll() {
		return administratorKRepository.findAll();
	}
	
	public AdministratorKlinike save(AdministratorKlinike administrator) {
		return administratorKRepository.save(administrator);
	}
}
