package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import com.example.demo.dto.AdministratorKCDTO;
import com.example.demo.dto.AdministratorKlinikeDTO;
import com.example.demo.dto.DijagnozaDTO;
import com.example.demo.dto.KlinickiCentarDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.LekDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.AdministratorKC;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Dijagnoza;
import com.example.demo.model.KlinickiCentar;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lek;
import com.example.demo.model.Pacijent;
import com.example.demo.service.AdministratorKCService;
import com.example.demo.service.EmailService;
import com.example.demo.service.KlinickiCentarService;
import com.example.demo.service.PacijentService;



@RestController
@RequestMapping(value="/api/administratoriKC", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdministratorKCController {
	
	@Autowired
	private AdministratorKCService administratorKCService;
	
	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private KlinickiCentarService KCService;
	
	@Autowired
	private EmailService emailService;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping(value = "/svi")
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
	@GetMapping(value = "/listaKlinika/{email}")
	public ResponseEntity<List<KlinikaDTO>> getListaKlinika(@PathVariable String email) {

		AdministratorKC administratorKC = administratorKCService.findByEmail(email);
		
		KlinickiCentar klinickiCentar = administratorKC.getKlinickiCentar();
		
		List<KlinikaDTO> lista = new ArrayList<>();
		
		for (Klinika k : klinickiCentar.getListaKlinika()) {
			KlinikaDTO kcDTO = new KlinikaDTO(k);
			lista.add(kcDTO);
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);

		
	}
	
	//vrati mi listu svih admina klinika u klinickom centru
	@GetMapping(value = "/listaAdministratoraKlinika/{email}")
	public ResponseEntity<List<AdministratorKlinikeDTO>> getListaAdministratoraKlinika(@PathVariable String email) {

		AdministratorKC administratorKC = administratorKCService.findByEmail(email);
		
		KlinickiCentar klinickiCentar = administratorKC.getKlinickiCentar();
		List<AdministratorKlinikeDTO> lista = new ArrayList<>();
		for (Klinika k : klinickiCentar.getListaKlinika() ) {
			for(AdministratorKlinike a : k.getListaAdminKlinike()) {
				AdministratorKlinikeDTO aDTO = new AdministratorKlinikeDTO(a);
				lista.add(aDTO);
			}
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	//vrati mi podatke o klinickom centru
	@GetMapping(value = "/klinickiCentar/{email}")
	public ResponseEntity<KlinickiCentarDTO> getKlinickiCentar(@PathVariable String email) {
		
		AdministratorKC administratorKC = administratorKCService.findByEmail(email);
		
		KlinickiCentar kc = administratorKC.getKlinickiCentar();
		KlinickiCentarDTO kcDTO = new KlinickiCentarDTO(kc);
		 
		return new ResponseEntity<>(kcDTO, HttpStatus.OK);
	}
	
	//vrati mi listu lekova u klinickom centru
	@GetMapping(value = "/listaLekova/{email}")
	public ResponseEntity<List<LekDTO>> getListaLekova(@PathVariable String email) {

		AdministratorKC administratorKC = administratorKCService.findByEmail(email);	
		KlinickiCentar klinickiCentar = administratorKC.getKlinickiCentar();	
		List<LekDTO> lista = new ArrayList<>();
		
		for (Lek k : klinickiCentar.getListaLekova()) {
			LekDTO kcDTO = new LekDTO(k);
			lista.add(kcDTO);
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);

		
	}
	
	//vrati mi listu dijagnoza u klinickom centru
	@GetMapping(value = "/listaDijagnoza/{email}")
	public ResponseEntity<List<DijagnozaDTO>> getListaDijagnoza(@PathVariable String email) {

		AdministratorKC administratorKC = administratorKCService.findByEmail(email);			
		KlinickiCentar klinickiCentar = administratorKC.getKlinickiCentar();			
		List<DijagnozaDTO> lista = new ArrayList<>();
			
		for (Dijagnoza k : klinickiCentar.getListaDijagnoza()) {
			DijagnozaDTO kcDTO = new DijagnozaDTO(k);
			lista.add(kcDTO);
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);	
	}

	//vrati mi listu zahteva od korisnika tj mejlove
	@GetMapping(value = "/listaZahtevaZaRegistraciju/{email}")
	public ResponseEntity<List<PacijentDTO>> getListaZahtevaZaRegistraciju(@PathVariable String email) {

		AdministratorKC administratorKC = administratorKCService.findByEmail(email);
		
		KlinickiCentar kc = administratorKC.getKlinickiCentar();
		List<PacijentDTO> lista = new ArrayList<PacijentDTO>();
		for(Pacijent p : kc.getZahteviZaRegistraciju()) {
			if(p.getOdobrenaRegistracija() == false) {
				PacijentDTO pDTO = new PacijentDTO(p);
				lista.add(pDTO);
			}
			
		}
		
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	//izmena podataka o adminu 
	@PutMapping(path="/izmena", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<AdministratorKCDTO> izmeniAdminaKC(@RequestBody AdministratorKCDTO akcDTO) {

		// a student must exist
		System.out.println("ADMIN KC IZMENA");
		AdministratorKC aKC = administratorKCService.findByEmail(akcDTO.getEmail());

//		System.out.println("Lekar update: " + lekar.getEmail());
//		if (lekar == null) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
		
		if(akcDTO.getIme() != null && akcDTO.getIme() != "") {
			System.out.println("izmenjeno ime admina");
			aKC.setIme(akcDTO.getIme());
			
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		
		
		if(akcDTO.getPrezime() != null && akcDTO.getPrezime() != "") {
			System.out.println("izmenjeno prezime admina");
			aKC.setPrezime(akcDTO.getPrezime());
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(akcDTO.getLozinka() != null && akcDTO.getLozinka() != "") {
			System.out.println("izmenjena lozinka admina");
			aKC.setLozinka(akcDTO.getLozinka());
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		aKC = administratorKCService.save(aKC);
		
		return new ResponseEntity<>(new AdministratorKCDTO(aKC), HttpStatus.OK);
	}

	//potvrda registracije NE RADI 
	@PostMapping(path = "/potvrda", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public String potvrdaRegistracijePacijenata(@RequestBody PacijentDTO pDTO){
		System.out.println("------------------------------------");
		
		System.out.println("Pacijent " + pDTO.getIme() + " " + pDTO.getEmail());
		
		Pacijent p = pacijentService.findByEmail(pDTO.getEmail());
		System.out.println(p.toString());
//		System.out.println("kc by pacijent " + p.getKlinickiCentar().getId());
		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);
		System.out.println("Klinicki centar" + kc.getId() + " " + kc.getNaziv());
		
		
	
		if(kc.getZahteviZaRegistraciju().isEmpty()) {
			System.out.println("prazna listaaa");
		}else {
			p.setOdobrenaRegistracija(true);
			p = pacijentService.save(p);
			System.out.println(p.getOdobrenaRegistracija());
			
			kc.getZahteviZaRegistraciju().remove(p);
			kc.setZahteviZaRegistraciju(kc.getZahteviZaRegistraciju());
			kc = KCService.save(kc);
			System.out.println(kc.getZahteviZaRegistraciju().toString());
		}

			
		String subject ="Odobrena registracija";
		String text = "Postovani " + pDTO.getIme() + " " + pDTO.getPrezime() + ",\n\nmolimo Vas da potvrdite vasu registraciju klikom na sledeci link: http://localhost:3000 .";

		System.out.println(text);
		
		//slanje emaila
		try {
			emailService.poslatiOdgovorPacijentu(pDTO, subject, text);
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
		}

		return "success";
	}
	
	//odbijanje registracije pacijenata
		@PostMapping(path = "/odbijanje", consumes = "application/json")
		@CrossOrigin(origins = "http://localhost:3000")
		public String odbijanjeRegistracijePacijenata(@RequestBody PacijentDTO pDTO){
			System.out.println("------------------------------------");
			
			System.out.println("Pacijent " + pDTO.getIme() + " " + pDTO.getEmail());
//			
			Pacijent p = pacijentService.findByEmail(pDTO.getEmail());
//			System.out.println(p.toString());
//			System.out.println("kc by pacijent " + p.getKlinickiCentar().getId());
//			List<KlinickiCentar> listaKC = KCService.find();
//			KlinickiCentar kc = listaKC.get(0);
//			System.out.println("Klinicki centar" + kc.getId() + " " + kc.getNaziv());
//			
//			
		
//			if(kc.getZahteviZaRegistraciju().isEmpty()) {
//				System.out.println("prazna listaaa");
//			}else {
//				p.setOdobrenaRegistracija(true);
//				p = pacijentService.save(p);
//				System.out.println(p.getOdobrenaRegistracija());
//				
//				kc.getZahteviZaRegistraciju().remove(p);
//				kc.setZahteviZaRegistraciju(kc.getZahteviZaRegistraciju());
//				kc = KCService.save(kc);
//				System.out.println(kc.getZahteviZaRegistraciju().toString());
//			}

				
			String subject ="Odobijena registracija";
			String text = "Postovani " + pDTO.getIme() + " " + pDTO.getPrezime() + ",\n\nVasa registracija je odbijena od strane administratorskog tima Klinickog Centra.";

			System.out.println(text);
			
			//slanje emaila
			try {
				emailService.poslatiOdgovorPacijentu(pDTO, subject, text);
			}catch( Exception e ){
				logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			}

			return "success";
		}
	
}
