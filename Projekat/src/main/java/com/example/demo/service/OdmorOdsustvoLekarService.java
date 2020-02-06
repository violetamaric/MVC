package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OdmorOdsustvoLekar;
import com.example.demo.repository.OdmorOdsustvoLekaraRepository;

@Service
public class OdmorOdsustvoLekarService {
	@Autowired
	private OdmorOdsustvoLekaraRepository odmorOdsustvoLekarRepository;
	
	public List<OdmorOdsustvoLekar> findAll(){
		return odmorOdsustvoLekarRepository.findAll();
	}
	
	public OdmorOdsustvoLekar findById(Long id) {
		return odmorOdsustvoLekarRepository.findById(id).orElseGet(null);
	}
	
	public OdmorOdsustvoLekar save(OdmorOdsustvoLekar ool) {
		return odmorOdsustvoLekarRepository.save(ool);
	}
	
	public void delete(OdmorOdsustvoLekar ool) {
		odmorOdsustvoLekarRepository.delete(ool);
	}
}
