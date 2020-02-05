package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdministratorKlinikeDTO;
import com.example.demo.dto.OperacijaDTO;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.Operacija;
import com.example.demo.model.Pacijent;
import com.example.demo.service.EmailService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.OperacijaService;
import com.example.demo.service.PacijentService;

@RestController
@RequestMapping(value = "/api/operacije", produces = MediaType.APPLICATION_JSON_VALUE)
public class OperacijaController {
	@Autowired
	private OperacijaService operacijaService;
	@Autowired
	private LekarService lekarService;
	@Autowired
	private PacijentService pacijentService;
	@Autowired
	private KlinikaService klinikaService;
	
	@Autowired
	private EmailService emailService;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
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
			System.out.println("======");
			System.out.println(o.getListaLekara().size());
			if (o.getPacijent().getEmail().equals(pr.getName())) {
//				if(o.getStatus() == 3) ako su zavrsene
				operacijeDTO.add(new OperacijaDTO(o));
			}

		}

		return new ResponseEntity<>(operacijeDTO, HttpStatus.OK);
	}

	//vracanje liste svih operacija lekara
	@GetMapping(value = "/operacijeLekara")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<List<OperacijaDTO>> preuzimanjeOperacijaLekara(Principal pr) {

		Lekar lekar = lekarService.findByEmail(pr.getName());
		
		Set<Operacija> operacije = lekar.getListaOperacija();
		List<OperacijaDTO> operacijeDTO = new ArrayList<>();
		
		for (Operacija o : operacije) {

			System.out.println(o.getStatus());
			if (o.getStatus() == 1) {
				System.out.println("dodata");
				operacijeDTO.add(new OperacijaDTO(o));
			}

		}

		return new ResponseEntity<>(operacijeDTO, HttpStatus.OK);
	}
	
	//zakazivanje operacije od strane lekara
	@PostMapping(path = "/novaOperacija", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> novaOperacija(@RequestBody OperacijaDTO operacijaDTO, Principal pr ) {
		
		Lekar lekar = lekarService.findByEmail(pr.getName());
		Klinika klinika = lekar.getKlinika();
		Pacijent pacijent = pacijentService.findByEmail(operacijaDTO.getPacijentEmail());
		
		System.out.println("dodavanje nove operacije");
		System.out.println(operacijaDTO);
		Operacija operacija = new Operacija();
		operacija.setDatum(operacijaDTO.getDatum());
		operacija.setTermin(operacijaDTO.getTermin());
		operacija.setStatus(0);
		operacija.setTipOperacije(operacijaDTO.getTipOperacije());
		operacija.setPacijent(pacijent);
		operacija.setKlinika(klinika);
		
		operacija = operacijaService.save(operacija);
		
		
		klinika.getListaOperacija().add(operacija);
		klinika = klinikaService.save(klinika);
		
		
		pacijent.getListaOperacija().add(operacija);
		pacijent = pacijentService.save(pacijent);
		


		Set<AdministratorKlinike> ak = klinika.getListaAdminKlinike();

		for (AdministratorKlinike AK : ak) {
			AdministratorKlinikeDTO akDTO = new AdministratorKlinikeDTO(AK);
			String subject = "Zahtev za operaciju";
			String text = "Postovani " + AK.getIme() + " " + AK.getPrezime() + ",\n\n imate novi zahtev za operaciju.";

			System.out.println(text);

			// slanje emaila
			try {
				emailService.poslatiOdgovorAdminuK(akDTO, subject, text);
			} catch (Exception e) {
				logger.info("Greska prilikom slanja emaila: " + e.getMessage());
				return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity<>(new OperacijaDTO(operacija), HttpStatus.OK);
	}
	
	
}
