package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.IzvestajOKliniciService;

@RestController
public class IzvestajOKliniciController {
	@Autowired
	private IzvestajOKliniciService izvestajOKliniciService;
}
