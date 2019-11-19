package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdministratorKCDTO;
import com.example.demo.dto.KlinickiCentarDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.model.AdministratorKC;
import com.example.demo.model.KlinickiCentar;
import com.example.demo.model.Klinika;
import com.example.demo.service.AdministratorKCService;

@RestController
@RequestMapping(value="/api/administratoriKC", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdministratorKCController {
	
	@Autowired
	private AdministratorKCService administratorKCService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<AdministratorKCDTO>> getAll() {

		List<AdministratorKC> administratoriKC = administratorKCService.findAll();

		System.out.println("ISPISANI SVI ADMINISTRATORI KC IZ BAZE");
		List<AdministratorKCDTO> administratoriKCDTO = new ArrayList<>();
		for (AdministratorKC aKC : administratoriKC) {
			administratoriKCDTO.add(new AdministratorKCDTO(aKC));
		}

		return new ResponseEntity<>(administratoriKCDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pronadjenAdministratorKC/{email}")
	public ResponseEntity<AdministratorKCDTO> getAdministratorKCByEmail(@PathVariable String email){
		
		AdministratorKC administratorKC = administratorKCService.findByEmail(email);
		if (administratorKC == null) {
			System.out.println("NIJE PRONADJEN");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("PRONADJEN: "+ administratorKC.getEmail());
		
		return new ResponseEntity<>(new AdministratorKCDTO(administratorKC), HttpStatus.OK);
	}
	
	//vrati mi listu klinika u klinickom centru
	@GetMapping(value = "/{administratorKCId}/listaKlinika")
	public ResponseEntity<List<KlinikaDTO>> getListaKlinika(@PathVariable Long administratorKCId) {

		AdministratorKC administratorKC = administratorKCService.findById(administratorKCId);

		KlinickiCentar klinickiCentar = administratorKC.getKlinickiCentar();
		List<Klinika> klinike = (List<Klinika>) klinickiCentar.getListaKlinika();
		List<KlinikaDTO> klinikeDTO = new ArrayList<>();
		for(Klinika k : klinike) {
			KlinikaDTO klinikaDTO = new KlinikaDTO();
			klinikaDTO.setId(k.getId());
			klinikaDTO.setNaziv(k.getNaziv());
			klinikaDTO.setAdresa(k.getAdresa());
			klinikaDTO.setOpis(k.getOpis());
			klinikaDTO.setOcena(k.getOcena());
			klinikeDTO.add(klinikaDTO);
		}

		return new ResponseEntity<>(klinikeDTO, HttpStatus.OK);
	}
	
	//vrati mi podatke o klinickom centru
	@GetMapping(value = "/{administratorKCId}/klinickiCentar")
	public ResponseEntity<KlinickiCentarDTO> getKlinickiCentar(@PathVariable Long administratorKCId) {

		AdministratorKC administratorKC = administratorKCService.findById(administratorKCId);

		KlinickiCentar klinickiCentar = administratorKC.getKlinickiCentar();
		KlinickiCentarDTO kcDTO = new KlinickiCentarDTO();
		kcDTO.setId(klinickiCentar.getId());
	    kcDTO.setNaziv(klinickiCentar.getNaziv());
	    kcDTO.setAdresa(klinickiCentar.getAdresa());
	    kcDTO.setOpis(klinickiCentar.getOpis());
	    
		return new ResponseEntity<>(kcDTO, HttpStatus.OK);
	}
	
	
	
	//za prijavu Administratora
//	@PostMapping(path = "/login", consumes = "application/json")
//	@CrossOrigin(origins = "http://localhost:3000")
//	public ResponseEntity<AdministratorKCDTO> login(@RequestBody AdministratorKCDTO administratorKCDTO) {
//		System.out.println("LOGIN");
//		AdministratorKC administratorKC = administratorKCService.findByEmail(administratorKCDTO.getEmail());
//		
//		if (administratorKC == null) {
//			System.out.println("NEUSPESNA PRIJAVA ADMINISTRATORA KC");
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
//		}
//		System.out.println("USPESNA PRIJAVA ADMINISTRATORA KC");
//		return new ResponseEntity<>(new AdministratorKCDTO(administratorKC), HttpStatus.OK);
//	}
	
	
	
	
}
