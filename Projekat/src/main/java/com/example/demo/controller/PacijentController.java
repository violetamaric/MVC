package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.Pacijent;
import com.example.demo.service.PacijentService;

@RestController
@RequestMapping(value = "/api/pacijenti", produces = MediaType.APPLICATION_JSON_VALUE)
public class PacijentController {

	@Autowired
	private PacijentService pacijentService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<PacijentDTO>> getAll() {

		List<Pacijent> pacijenti = pacijentService.findAll();

		// convert students to DTOs
		List<PacijentDTO> pacijentDTO = new ArrayList<>();
		for (Pacijent p : pacijenti) {
			pacijentDTO.add(new PacijentDTO(p));
		}

		return new ResponseEntity<>(pacijentDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findPacijent/{lbo}")
	public ResponseEntity<PacijentDTO> getStudentByLbo(@PathVariable String lbo) {
		System.out.println("find pacijent");
		Pacijent pacijent = pacijentService.findByLbo(lbo);
		if (pacijent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(pacijent.getEmail() + "++++");
		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<PacijentDTO> saveStudent(@RequestBody PacijentDTO pacijentDTO) {

		Pacijent pacijent = new Pacijent();
		pacijent.setLbo(pacijentDTO.getLbo());
		pacijent.setIme(pacijentDTO.getIme());
		pacijent.setPrezime(pacijentDTO.getIme());
		pacijent.setEmail(pacijentDTO.getEmail());
		pacijent.setLozinka(pacijentDTO.getLozinka());
		pacijent.setKorisnickoIme(pacijentDTO.getKorisnickoIme());

		pacijent = pacijentService.save(pacijent);
		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.CREATED);
	}
}
