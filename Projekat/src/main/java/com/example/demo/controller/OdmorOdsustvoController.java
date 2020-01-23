package com.example.demo.controller;

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

import com.example.demo.dto.OdmorOdsustvoMSDTO;
import com.example.demo.model.Klinika;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.OdmorOdsustvoMedicinskaSestra;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.MedicinskaSestraService;

@RestController
@RequestMapping(value="/api/odmorodsustvo", produces = MediaType.APPLICATION_JSON_VALUE)
public class OdmorOdsustvoController {
	@Autowired
	private MedicinskaSestraService medicinskaSestraService;
	
	
	@Autowired
	private KlinikaService klinikaService;
	
	
	//posalji zahtev za odmor odsustvo
	@PostMapping(path = "/posaljiZahtev", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('MED_SESTRA')")
	public ResponseEntity<OdmorOdsustvoMSDTO> slanjeZahteva(@RequestBody OdmorOdsustvoMSDTO ooDTO) {
		System.out.println("------------------------------------------------------");
			
		//TODO 1: treba proveriti da li je medicinska sestra ima operacije ili preglede u tom periodu
		//ako nema onda moze da se posalje zahtev adminu klinike.. 
		//ako ima onda mora da se obavesti da je taj datum zauzet
		//na frontu uraditi proveru da li je datum slobodan..
		//tako sto uzmem datum pocetka i kraja i proverim za sve dane izmedju koristeci neku metodu
		//iz backa za slobodne dane... ako nema pregled tad 
		
		
		//ili proveriti samo posto je med sestra zaposlena od 9-17 pa da li je u tom vremenu 
	
		MedicinskaSestra ms = medicinskaSestraService.findById(ooDTO.getIdMedSestre());
		if(ms != null) {
			Klinika k = klinikaService.findById(ms.getKlinika().getId());
			
			OdmorOdsustvoMedicinskaSestra ooms = new OdmorOdsustvoMedicinskaSestra();
			ooms.setDatumOd(ooDTO.getDatumOd());
			ooms.setDatumDo(ooDTO.getDatumDo());
			ooms.setOpis(ooDTO.getOpis());
			ooms.setTip(ooDTO.getTip());
			ooms.setStatus(false);
			ooms.setMedicinskaSestra(ms);
			ooms.setKlinika(k);
			
			k.getZahteviZaOdmorOdsustvoMedestre().add(ooms);
			k = klinikaService.save(k);
			System.out.println("------------------------------------------------------");
			return new ResponseEntity<>(new OdmorOdsustvoMSDTO(ooms), HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
