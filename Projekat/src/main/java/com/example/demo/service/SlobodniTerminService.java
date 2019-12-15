package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;
import com.example.demo.model.SlobodniTermin;
import com.example.demo.repository.SlobodniTerminRepository;

@Service
public class SlobodniTerminService {
	
	@Autowired
	private SlobodniTerminRepository STRepository;

	public List<SlobodniTermin> findAll() {
		return STRepository.findAll();
	}
	public void delete(SlobodniTermin st) {
		STRepository.delete(st);
	}
	
}
