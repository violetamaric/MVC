package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.KlinickiCentar;
import com.example.demo.repository.KlinickiCentarRepository;

@Service
public class KlinickiCentarService {
	@Autowired
	private KlinickiCentarRepository klinickiCentarRepository;
	
	public KlinickiCentar save(KlinickiCentar KC) {
		return klinickiCentarRepository.save(KC);
	}
	
	public List<KlinickiCentar> find(){
		return klinickiCentarRepository.findAll();
	}
}
