package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.PregledService;

@RestController
public class PregledController {
	@Autowired
	private PregledService pregledService;
}
