package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.repository.LekarRepository;
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
		System.out.println("find all preglede");
		System.out.println(pregledRepository.findAll());
		for(Pregled p:pregledRepository.findAll()) {
			System.out.println("-----------------------------------");
			System.out.println(p.getId());
			System.out.println("-----------------------------------");
		}
		return pregledRepository.findAll();
	}
	
	public Pregled save(Pregled Pregled) {
		return pregledRepository.save(Pregled);
	}
	@Transactional
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
