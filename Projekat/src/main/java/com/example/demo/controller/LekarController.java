package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LekarDTO;
import com.example.demo.model.Lekar;
import com.example.demo.service.LekarService;


@RestController
@RequestMapping(value = "/api/lekari", produces = MediaType.APPLICATION_JSON_VALUE)
public class LekarController {

	@Autowired
	private LekarService lekarService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<LekarDTO> getLekar(@PathVariable Long id) {

		Lekar lekar = lekarService.findOne(id);

		if (lekar == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
	}

	
	@GetMapping(value = "/all")
	public ResponseEntity<List<LekarDTO>> getAll() {

		List<Lekar> lekari = lekarService.findAll();

		// convert students to DTOs
		List<LekarDTO> lekarDTO = new ArrayList<>();
		for (Lekar l : lekari) {
			lekarDTO.add(new LekarDTO(l));
		}

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<LekarDTO> updateLekar(@RequestBody LekarDTO lekarDTO) {

		// a student must exist
		Lekar lekar = lekarService.findOne(lekarDTO.getId());
		System.out.println("Lekar update " + lekar);

		if (lekar == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		lekar.setIme(lekarDTO.getIme());
		lekar.setPrezime(lekarDTO.getPrezime());
		lekar.setEmail(lekarDTO.getEmail());

		lekar = lekarService.save(lekar);
		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
	}
	
}
