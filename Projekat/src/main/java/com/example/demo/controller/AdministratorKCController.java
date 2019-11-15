package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AdministratorKCService;

@RestController
public class AdministratorKCController {
	@Autowired
	private AdministratorKCService administratorKCService;
}
