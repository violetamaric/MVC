package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ReceptRepository;

@Service
public class ReceptService {
	@Autowired
	private ReceptRepository receptRepository;
}
