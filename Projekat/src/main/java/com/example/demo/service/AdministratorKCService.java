package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AdministratorKC;
import com.example.demo.repository.AdministratorKCRepository;

@Service
public class AdministratorKCService {

	@Autowired
	private AdministratorKCRepository administratorKCRepository;
	
//	public AdministratorKC findAdminKCByEmailAndLozinka(String email, String lozinka) {
//		return administratorKCRepository.fidAdminKCByEmailAndLozinka(email, lozinka);
//	}

	public List<AdministratorKC> findAll(){
		return administratorKCRepository.findAll();
	}
	public AdministratorKC findById(Long id) {
		return administratorKCRepository.findById(id).orElseGet(null);
	}
	
	public AdministratorKC findByEmail(String email) {
		return administratorKCRepository.findByEmail(email);
	}
	
	public AdministratorKC save(AdministratorKC administratorKC) {
		return administratorKCRepository.save(administratorKC);
	}
	
}
