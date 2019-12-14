package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LekarDTO;
import com.example.demo.model.Lekar;
import com.example.demo.service.PregledService;

@RestController
@RequestMapping(value = "/api/pregled", produces = MediaType.APPLICATION_JSON_VALUE)

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PregledDTO;
import com.example.demo.model.Pregled;
import com.example.demo.service.PregledService;


public class PregledController {
	@Autowired
	private PregledService pregledService;
	

//	@PutMapping(path="/new", consumes = "application/json")
//	@CrossOrigin(origins = "http://localhost:3000")
//	public ResponseEntity<LekarDTO> updateLekar(@RequestBody LekarDTO lekarDTO) {

//
//		Pregled pregled = pregledService.findByEmail(lekarDTO.getEmail());
//
////		System.out.println("Lekar update: " + lekar.getEmail());
////		if (lekar == null) {
////			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
////		}
//		
//
//		lekar.setIme(lekarDTO.getIme());
//		lekar.setPrezime(lekarDTO.getPrezime());
//		lekar.setTelefon(lekarDTO.getTelefon());
//
//		lekar = lekarService.save(lekar);
//		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
//	}

	
	@GetMapping(value = "/{id}")
	public ResponseEntity<PregledDTO> getLekar(@PathVariable Long id) {

		Pregled pregled = pregledService.findOne(id);

		// studen must exist
		if (pregled == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.OK);
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<PregledDTO>> getAll() {

		List<Pregled> pregledi = pregledService.findAll();

		// convert students to DTOs
		List<PregledDTO> pregledDTO = new ArrayList<>();
		for (Pregled p : pregledi) {
			pregledDTO.add(new PregledDTO(p));
		}

		return new ResponseEntity<>(pregledDTO, HttpStatus.OK);
	}
	
	

}
