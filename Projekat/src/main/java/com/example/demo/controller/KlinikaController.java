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
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Klinika;
import com.example.demo.service.KlinikaService;

@RestController
@RequestMapping(value = "/api/klinike", produces = MediaType.APPLICATION_JSON_VALUE)
public class KlinikaController {
	@Autowired
	private KlinikaService klinikaService;

	@GetMapping(value = "/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<KlinikaDTO> getKlinikaById(@PathVariable Long id) {

		Klinika k = klinikaService.findOne(id);
		System.out.println("Pretraga klinike po ID");
		// studen must exist
		if (k == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(k.getNaziv() + " " + k.getId());
		return new ResponseEntity<>(new KlinikaDTO(k), HttpStatus.OK);
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<KlinikaDTO>> getAll() {

		List<Klinika> klinike = klinikaService.findAll();

		// convert students to DTOs
		List<KlinikaDTO> klinikaDTO = new ArrayList<>();
		for (Klinika k : klinike) {
			klinikaDTO.add(new KlinikaDTO(k));
		}

		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findKlinikaByNaziv/{naziv}")
	public ResponseEntity<KlinikaDTO> getKlinikaByNaziv(@PathVariable String naziv) {
		System.out.println("find klinika by naziv");
		Klinika klinika = klinikaService.findByNaziv(naziv);
		if (klinika == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(klinika.getNaziv());
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
	}
	
	@GetMapping(value = "/findKlinikaByAdresa/{adresa}")
	public ResponseEntity<KlinikaDTO> getKlinikaByAdresa(@PathVariable String adresa) {
		System.out.println("find klinika by adresa");
		if(adresa.contains("%20"))
			adresa.replace("%20", " ");
	
		
		Klinika klinika = klinikaService.findByAdresa(adresa);
		
		if (klinika == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		System.out.println(klinika.getNaziv());
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
	}

	
	@PutMapping(path="/update", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<KlinikaDTO> updateAdminKlinike(@RequestBody KlinikaDTO klinikaDTO) {

		// a student must exist
		System.out.println("ADMIN KLINIKE UPDRATE");
		Klinika klinika = klinikaService.findById(klinikaDTO.getId());

//		System.out.println("Lekar update: " + lekar.getEmail());
//		if (lekar == null) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
		

		klinika.setNaziv(klinikaDTO.getNaziv());
		klinika.setAdresa(klinikaDTO.getAdresa());
		klinika.setOpis(klinikaDTO.getOpis());
		klinika.setOcena(klinikaDTO.getOcena());

		klinika = klinikaService.save(klinika);
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
	}
}
