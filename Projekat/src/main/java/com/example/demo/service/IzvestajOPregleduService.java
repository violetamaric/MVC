package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.repository.IzvestajOPregleduRepository;

@Service
public class IzvestajOPregleduService {
	
	@Autowired
	private IzvestajOPregleduRepository izvestajOPregleduRepository;
	
	
	public IzvestajOPregledu save(IzvestajOPregledu lek) {
		return izvestajOPregleduRepository.save(lek);
	}
	public void delete(IzvestajOPregledu lek) {
		izvestajOPregleduRepository.delete(lek);
	}
	public IzvestajOPregledu findById(Long id) {
		return izvestajOPregleduRepository.findById(id).orElseGet(null);
	}
}
