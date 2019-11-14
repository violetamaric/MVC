package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.LekarService;

@RestController
public class LekarController {

	@Autowired
	private LekarService lekarService;
	private String a;
}
