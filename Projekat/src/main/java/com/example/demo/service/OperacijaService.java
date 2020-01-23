package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Operacija;
import com.example.demo.repository.OperacijaRepository;

@Service
public class OperacijaService {
	@Autowired
	private OperacijaRepository operacijaRepository;
	
	public List<Operacija> findAll() {
		return operacijaRepository.findAll();
	}
}
