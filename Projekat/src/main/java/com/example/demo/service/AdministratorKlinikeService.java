package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AdministratorKlinike;
import com.example.demo.repository.AdministratorKlinikeRepository;

@Service
public class AdministratorKlinikeService {

	@Autowired
	private AdministratorKlinikeRepository administratorKRepository;
	
	public AdministratorKlinike findByEmailAndLozinka(String email, String lozinka) {
		return administratorKRepository.findByEmailAndLozinka(email, lozinka);
	}
	public AdministratorKlinike findById(Long id) {
		return administratorKRepository.findById(id).orElseGet(null);
	}
	public AdministratorKlinike save(AdministratorKlinike administratorKlinike) {
		return administratorKRepository.save(administratorKlinike);
	}
	public void delete(AdministratorKlinike administratorKlinike) {
		administratorKRepository.delete(administratorKlinike);
	}
}
