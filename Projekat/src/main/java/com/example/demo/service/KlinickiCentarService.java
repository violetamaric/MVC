package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.KlinickiCentarRepository;

@Service
public class KlinickiCentarService {
	@Autowired
	private KlinickiCentarRepository klinickiCentarRepository;
}
