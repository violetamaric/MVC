package com.example.demo.controller;

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
}
