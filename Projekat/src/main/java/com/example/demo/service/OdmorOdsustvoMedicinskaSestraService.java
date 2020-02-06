package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OdmorOdsustvoMedicinskaSestra;
import com.example.demo.repository.OdmorOdsustvoMedicinskeSestreRepository;

@Service
public class OdmorOdsustvoMedicinskaSestraService {
	
	@Autowired
	private OdmorOdsustvoMedicinskeSestreRepository odmorOdsustvoMSRepository;
	
	public List<OdmorOdsustvoMedicinskaSestra> findAll(){
		return odmorOdsustvoMSRepository.findAll();
	}
	
	public OdmorOdsustvoMedicinskaSestra findById(Long id) {
		return odmorOdsustvoMSRepository.findById(id).orElseGet(null);
	}
	
	public OdmorOdsustvoMedicinskaSestra save(OdmorOdsustvoMedicinskaSestra ooms) {
		return odmorOdsustvoMSRepository.save(ooms);
	}
	public void delete(OdmorOdsustvoMedicinskaSestra ooms) {
		odmorOdsustvoMSRepository.delete(ooms);
	}
}
