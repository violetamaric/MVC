package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.PregledRepository;

@Service
public class PregledService {
	@Autowired
	private PregledRepository pregledRepository;
}
