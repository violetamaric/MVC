package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.IzvestajOKliniciService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class IzvestajOKliniciController {
	@Autowired
	private IzvestajOKliniciService izvestajOKliniciService;
}
