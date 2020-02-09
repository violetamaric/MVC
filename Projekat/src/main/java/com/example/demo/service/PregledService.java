package com.example.demo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.transaction.annotation.Transactional;
import com.example.demo.model.Pregled;
import com.example.demo.repository.PregledRepository;

@Service
public class PregledService {
	@Autowired
	private PregledRepository pregledRepository;
	
	
	public Pregled findOne(Long id) {
		return pregledRepository.findById(id).orElseGet(null);
	}
	public Pregled findById(Long id) {
		return pregledRepository.findById(id).orElseGet(null);
	}
	
	public List<Pregled> findAll() {
		return pregledRepository.findAll();
	}
	
//	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public Pregled save(Pregled Pregled) {
		return pregledRepository.save(Pregled);
	}
	
	public void delete(Pregled pregled) {
		pregledRepository.delete(pregled);
	}
	
	public void deleteById(Long id) {
		pregledRepository.deleteById(id);
	}
	
	public void deleteAll() {
		pregledRepository.deleteAll();
	}
}
