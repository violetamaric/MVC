package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.SalaRepository;

@Service
public class ZdravstveniKartonService {
	@Autowired
	private SalaRepository salaRepository;
}
