package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LekDTO;
import com.example.demo.model.KlinickiCentar;
import com.example.demo.model.Lek;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.MedicinskaSestraService;

@RestController
@RequestMapping(value="/api/odmorodsustvo", produces = MediaType.APPLICATION_JSON_VALUE)
public class OdmorOdsustvoController {
	@Autowired
	private MedicinskaSestraService medicinskaSestraService;
	
	
	@Autowired
	private KlinikaService klinikaService;
	
	
//	//posalji zahtev za odmor odsustvo
//	@PostMapping(path = "/posaljiZahtev", consumes = "application/json")
//	@CrossOrigin(origins = "http://localhost:3000")
//	@PreAuthorize("hasAuthority('MED_SESTRA')")
//	public ResponseEntity<OdmorOdsustvoDTO> slanjeZahteva(@RequestBody OdmorOdsustvoDTO ooDTO) {
//		System.out.println("------------------------------------------------------");
////		Lek lek = new Lek();
////		lek.setNaziv(lekDTO.getNaziv());
////		lek.setSifra(lekDTO.getSifra());
////		
////		List<KlinickiCentar> listaKC = KCService.find();
////		KlinickiCentar kc = listaKC.get(0);
////		
////		lek.setKlinickiCentar(kc);
////			
////		lek = lekService.save(lek);		
////		kc.getListaLekova().add(lek);
////		kc = KCService.save(kc);
//
//		System.out.println("------------------------------------------------------");
//		return new ResponseEntity<>(new OdmorOdsustvoDTO(lek), HttpStatus.CREATED);
//	}
}
