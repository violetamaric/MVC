package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.IzvestajOKliniciRepository;

@Service
public class IzvestajOKliniciService {
	@Autowired
	private IzvestajOKliniciRepository izvestajOKliniciRepository;
}
