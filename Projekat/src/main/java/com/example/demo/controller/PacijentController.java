package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.UserDTO;

import com.example.demo.model.KlinickiCentar;

import com.example.demo.model.Lekar;

import com.example.demo.model.Pacijent;
import com.example.demo.service.AdministratorKCService;
import com.example.demo.service.EmailService;
import com.example.demo.service.KlinickiCentarService;
import com.example.demo.service.PacijentService;

@RestController
@RequestMapping(value = "/api/pacijenti", produces = MediaType.APPLICATION_JSON_VALUE)
public class PacijentController {

	@Autowired
	private KlinickiCentarService KCService;
	
	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private EmailService emailService;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
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
	
	@GetMapping(value = "/findPacijentLbo/{lbo}")
	public ResponseEntity<PacijentDTO> getPacijentByLbo(@PathVariable String lbo) {
		System.out.println("find pacijent");
		Pacijent pacijent = pacijentService.findByLbo(lbo);
		if (pacijent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(pacijent.getEmail() + "++++");
		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/findPacijentEmail/{email}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<PacijentDTO> getPacijentByEmail(@PathVariable String email){
		
		System.out.println("Find pacijent by email");
		Pacijent pacijent = pacijentService.findByEmail(email);
		if(pacijent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
	}
	
	
	@PostMapping(path = "/register", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<PacijentDTO> savePacijent(@RequestBody PacijentDTO pacijentDTO) {

		Pacijent pacijent = new Pacijent();
		pacijent.setLbo(pacijentDTO.getLbo());
		pacijent.setIme(pacijentDTO.getIme());
		pacijent.setPrezime(pacijentDTO.getPrezime());
		pacijent.setEmail(pacijentDTO.getEmail());
		pacijent.setLozinka(pacijentDTO.getLozinka());
		pacijent.setAdresa(pacijentDTO.getAdresa());
		pacijent.setGrad(pacijentDTO.getGrad());
		pacijent.setDrzava(pacijentDTO.getDrzava());
		pacijent.setTelefon(pacijentDTO.getTelefon());
		pacijent.setOdobrenaRegistracija(false);
				
		
		System.out.println("--------------------------------------");
		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);
		pacijent.setKlinickiCentar(kc);
		pacijent = pacijentService.save(pacijent);
		
		kc.getZahteviZaRegistraciju().add(pacijent);
		kc = KCService.save(kc);
		
		System.out.println("dodat u zahteve za registraciju");
		
		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.CREATED);
	}
	
//	@PostMapping(path = "/signup", consumes = "application/json")
//	@CrossOrigin(origins = "http://localhost:3000")
//	public String signUpAsync(@RequestBody UserDTO userDTO){
//
//
//		//slanje emaila
//		try {
//			emailService.sendNotificaitionAsync(userDTO);
//		}catch( Exception e ){
//			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
//		}
//
//		return "success";
//	}
	
	
	@PutMapping(path="/update", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<PacijentDTO> updatePacijent(@RequestBody PacijentDTO pacijentDTO) {

		// a student must exist
		System.out.println("LEKAR UPDRATE");
		Pacijent pacijent = pacijentService.findByEmail(pacijentDTO.getEmail());

//		System.out.println("Lekar update: " + lekar.getEmail());
//		if (lekar == null) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
		

		pacijent.setIme(pacijentDTO.getIme());
		pacijent.setPrezime(pacijentDTO.getPrezime());
		pacijent.setTelefon(pacijentDTO.getTelefon());
		pacijent.setAdresa(pacijentDTO.getAdresa());
		pacijent.setGrad(pacijentDTO.getGrad());
		pacijent.setDrzava(pacijent.getDrzava());
		pacijent.setLbo(pacijentDTO.getLbo());

		pacijent = pacijentService.save(pacijent);
		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
	}
	

}