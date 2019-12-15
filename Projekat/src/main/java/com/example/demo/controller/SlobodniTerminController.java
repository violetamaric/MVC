package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.SlobodniTerminDTO;
import com.example.demo.model.SlobodniTermin;
import com.example.demo.service.SlobodniTerminService;

@RestController
@RequestMapping(value = "/api/ST", produces = MediaType.APPLICATION_JSON_VALUE)
public class SlobodniTerminController {

	@Autowired
	private SlobodniTerminService STService;
	
	@GetMapping(value = "/unapredDef")
	public ResponseEntity<List<SlobodniTerminDTO>> getAllUnapredDef() {

		List<SlobodniTermin> st = STService.findAll();

		// convert students to DTOs
		List<SlobodniTerminDTO> stDTO = new ArrayList<>();
		for (SlobodniTermin sstt : st) {
			stDTO.add(new SlobodniTerminDTO(sstt));
			System.out.println(new SlobodniTerminDTO(sstt));
			
			
		}

		return new ResponseEntity<>(stDTO, HttpStatus.OK);
	}
}
