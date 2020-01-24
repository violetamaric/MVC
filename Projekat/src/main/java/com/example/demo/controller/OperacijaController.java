package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OperacijaDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.model.Operacija;
import com.example.demo.model.Pregled;
import com.example.demo.service.OperacijaService;

@RestController
@RequestMapping(value = "/api/operacije", produces = MediaType.APPLICATION_JSON_VALUE)
public class OperacijaController {
	@Autowired
	private OperacijaService operacijaService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<OperacijaDTO>> getAll() {

		List<Operacija> operacije = operacijaService.findAll();

		// convert students to DTOs
		List<OperacijaDTO> operacijeDTO = new ArrayList<>();
		for (Operacija o : operacije) {
			operacijeDTO.add(new OperacijaDTO(o));
		}

		return new ResponseEntity<>(operacijeDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/operacijePacijenta")
	public ResponseEntity<List<OperacijaDTO>> preuzimanjeOperacijaPacijenta(Principal pr) {

		List<Operacija> operacije = operacijaService.findAll();

		// convert students to DTOs
		List<OperacijaDTO> operacijeDTO = new ArrayList<>();
		for (Operacija o : operacije) {
			if (o.getPacijent().getEmail().equals(pr.getName())) {
				operacijeDTO.add(new OperacijaDTO(o));
			}

		}

		return new ResponseEntity<>(operacijeDTO, HttpStatus.OK);
	}

}
