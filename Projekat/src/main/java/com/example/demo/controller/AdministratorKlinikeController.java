package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdministratorKlinikeDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Lekar;
import com.example.demo.service.AdministratorKlinikeService;

@RestController
@RequestMapping(value = "/api/adminKlinike", produces = MediaType.APPLICATION_JSON_VALUE)

public class AdministratorKlinikeController {
	@Autowired
	private AdministratorKlinikeService administratorKlinikeService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<AdministratorKlinikeDTO> getAdminKlinike(@PathVariable Long id) {

		AdministratorKlinike ak = administratorKlinikeService.findOne(id);

		// studen must exist
		if (ak == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new AdministratorKlinikeDTO(ak), HttpStatus.OK);
	}
	@GetMapping(value = "/all")
	public ResponseEntity<List<AdministratorKlinikeDTO>> getAll() {

		List<AdministratorKlinike> adminiKlinike = administratorKlinikeService.findAll();

		// convert students to DTOs
		List<AdministratorKlinikeDTO> administratorKlinikeDTO = new ArrayList<>();
		for (AdministratorKlinike ak : adminiKlinike) {
			administratorKlinikeDTO.add(new AdministratorKlinikeDTO(ak));
		}

		return new ResponseEntity<>(administratorKlinikeDTO, HttpStatus.OK);
	}
	@GetMapping(value = "/getAdminKlinikeByEmail/{email}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<AdministratorKlinikeDTO> findByEmail(@PathVariable String email){
		
		AdministratorKlinike adminiKlinike = administratorKlinikeService.findByEmail(email);
		if (adminiKlinike == null) {
			System.out.println("admin klinike nije pronadjen");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("Admin klinike je pronadjen : "+ adminiKlinike.getEmail());
		
		return new ResponseEntity<>(new AdministratorKlinikeDTO(adminiKlinike), HttpStatus.OK);
	}
	
	@PutMapping(path="/update", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<AdministratorKlinikeDTO> updateAdminKlinike(@RequestBody AdministratorKlinikeDTO administratorKlinikeDTO) {

		// a student must exist
		System.out.println("ADMIN KLINIKE UPDRATE");
		AdministratorKlinike adminiKlinike = administratorKlinikeService.findByEmail(administratorKlinikeDTO.getEmail());

//		System.out.println("Lekar update: " + lekar.getEmail());
//		if (lekar == null) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
		

		adminiKlinike.setIme(administratorKlinikeDTO.getIme());
		adminiKlinike.setPrezime(administratorKlinikeDTO.getPrezime());
		adminiKlinike.setTelefon(administratorKlinikeDTO.getTelefon());

		adminiKlinike = administratorKlinikeService.save(adminiKlinike);
		return new ResponseEntity<>(new AdministratorKlinikeDTO(adminiKlinike), HttpStatus.OK);
	}

}
