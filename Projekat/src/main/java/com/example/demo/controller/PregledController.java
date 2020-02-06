package com.example.demo.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdministratorKlinikeDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.SlobodniTerminDTO;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.Sala;
import com.example.demo.model.SlobodniTermin;
import com.example.demo.model.Termin;
import com.example.demo.model.TipPregleda;
import com.example.demo.service.EmailService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.PacijentService;
import com.example.demo.service.PregledService;
import com.example.demo.service.SalaService;
import com.example.demo.service.SlobodniTerminService;
import com.example.demo.service.TerminService;
import com.example.demo.service.TipPregledaService;

@RestController
@RequestMapping(value = "/api/pregledi", produces = MediaType.APPLICATION_JSON_VALUE)
public class PregledController {
	@Autowired
	private PregledService pregledService;
	@Autowired
	private KlinikaService klinikaService;
	@Autowired
	private LekarService lekarService;
	@Autowired
	private PacijentService pacijentService;
	@Autowired
	private TipPregledaService tipPregledaService;
	@Autowired
	private SlobodniTerminService STService;
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SalaService salaService;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);


	@Autowired
	private TerminService terminService;
	

	@PostMapping(path = "/new", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
//	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> noviPregled(@RequestBody PregledDTO pregledDTO) {
		
		//TODO 1: PROVERITI DA LI UOPSTE MOZE TOG DATUMA DA ZAKAZE
		System.out.println("dodavanje novog pregleda");
		System.out.println(pregledDTO);
		Pregled pregled = new Pregled();
		pregled.setCena(pregledDTO.getCena());
		pregled.setDatum(pregledDTO.getDatum());
		Klinika klinika = klinikaService.findById(pregledDTO.getKlinikaID());
		pregled.setKlinika(klinika);
		Lekar lekar = lekarService.findOne(pregledDTO.getLekarID());
		pregled.setLekar(lekar);
		pregled.setTermin(pregledDTO.getTermin());
		Pacijent pacijent = pacijentService.findByEmail(pregledDTO.getPacijentEmail());
		pregled.setPacijent(pacijent);
		pregled.setStatus(0);
		TipPregleda tp = tipPregledaService.findOne(pregledDTO.getTipPregledaID());
		pregled.setTipPregleda(tp);

		pregled = pregledService.save(pregled);
		
//		pacijent.getListaPregleda().add(pregled);
//		pacijent = pacijentService.save(pacijent);
		
		klinika.getListaPregleda().add(pregled);
		klinika = klinikaService.save(klinika);

		Set<AdministratorKlinike> ak = klinika.getListaAdminKlinike();

		for (AdministratorKlinike AK : ak) {
			AdministratorKlinikeDTO akDTO = new AdministratorKlinikeDTO(AK);
			String subject = "Zahtev za pregled";
			String text = "Postovani " + AK.getIme() + " " + AK.getPrezime() + ",\n\n imate novi zahtev za pregled.";

			System.out.println(text);

			// slanje emaila
			try {
				emailService.poslatiOdgovorAdminuK(akDTO, subject, text);
			} catch (Exception e) {
				logger.info("Greska prilikom slanja emaila: " + e.getMessage());
				return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.OK);
	}

	@PostMapping(path = "/newST", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
//	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> noviPregledST(@RequestBody PregledDTO pregledDTO) {
		System.out.println("dodavanje novog pregleda ST");
		System.out.println(pregledDTO);
		System.out.println(pregledDTO);
		Pregled pregled = new Pregled();
		pregled.setCena(pregledDTO.getCena());
		pregled.setDatum(pregledDTO.getDatum());
		Klinika klinika = klinikaService.findById(pregledDTO.getKlinikaID());
		pregled.setKlinika(klinika);
		System.out.println("Klinika: " + klinika.getId());
		Lekar lekar = lekarService.findOne(pregledDTO.getLekarID());
		pregled.setLekar(lekar);
		pregled.setTermin(pregledDTO.getTermin());
		Pacijent pacijent = pacijentService.findByEmail(pregledDTO.getPacijentEmail());
		pregled.setPacijent(pacijent);
		pregled.setStatus(1);
		TipPregleda tp = tipPregledaService.findOne(pregledDTO.getTipPregledaID());
		pregled.setTipPregleda(tp);
		pregled.setSala(salaService.findById(pregledDTO.getSalaID()));

		pregled = pregledService.save(pregled);
		
//		pacijent.getListaPregleda().add(pregled);
//		pacijent = pacijentService.save(pacijent);
//		
//		lekar.getListaPregleda().add(pregled);
		

		List<SlobodniTermin> st = STService.findAll();

		System.out.println();
		List<SlobodniTerminDTO> stDTO = new ArrayList<>();
		for (SlobodniTermin sstt : st) {
			System.out.println(sstt.getDatum());
			System.out.println(pregled.getDatum());

			if (sstt.getLekar().getId() == pregledDTO.getLekarID()
					&& sstt.getKlinika().getId() == pregledDTO.getKlinikaID()
					&& sstt.getTipPregleda().getId() == pregledDTO.getTipPregledaID()
					&& sstt.getCena() == pregled.getCena()) {
//					sstt.getDatum() == pregled.getDatum()) {
				System.out.println("menja se status");
				sstt.setStatus(true);
				STService.save(sstt);
				break;
			}

		}

		klinika.getListaPregleda().add(pregled);
		klinika = klinikaService.save(klinika);
		System.out.println("Sacuvana klinika");
//		Termin termin = new Termin();
//		termin.setDatumPocetka(pregled.getDatum());
//		termin.setTermin(pregled.getTermin());
//		termin.setLekar(pregled.getLekar());
//		termin.setSala(pregled.getSala());
//		terminService.save(termin);
//		System.out.println("Sacuvan termin: " + termin.getId());
//		
//		Lekar lekar2 = lekarService.findOne(pregled.getLekar().getId());
//		System.out.println("Lekar: "+ lekar2.getId());
//		Sala sala = salaService.findOne(pregled.getSala().getId());
//		System.out.println("Sala: " + sala.getId());
//		lekar.getListaZauzetihTermina().add(termin);
//		sala.getZauzetiTermini().add(termin);
//		lekarService.save(lekar2);
//		System.out.println("Sacuvan lekar");
//		salaService.save(sala);
//		System.out.println("Sacuvana sala");
		
		PacijentDTO pDTO = new PacijentDTO(pacijent);
		String subject ="Pregled je zakazan";
		String text = "Postovani " + pDTO.getIme() + " " + pDTO.getPrezime() 
					+ ",\n\nObavestavamo Vas da je Vas pregled uspesno prihvacen i zakazan.";

		System.out.println(text);
		
		//slanje emaila
		try {
			emailService.poslatiOdgovorPacijentu(pDTO, subject, text);
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")	
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<PregledDTO> getLekar(@PathVariable Long id) {

		Pregled pregled = pregledService.findOne(id);

		// studen must exist
		if (pregled == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<PregledDTO>> getAll() {

		List<Pregled> pregledi = pregledService.findAll();


		List<PregledDTO> pregledDTO = new ArrayList<>();
		for (Pregled p : pregledi) {
			pregledDTO.add(new PregledDTO(p));
		}

		return new ResponseEntity<>(pregledDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/preglediPacijenta")
	public ResponseEntity<List<PregledDTO>> preuzimanjePregledaPacijenta(Principal pr) {

		List<Pregled> pregledi = pregledService.findAll();

		// convert students to DTOs
		List<PregledDTO> pregledDTO = new ArrayList<>();
		for (Pregled p : pregledi) {
			if (p.getPacijent().getEmail().equals(pr.getName())) {
				pregledDTO.add(new PregledDTO(p));
			}

		}

		return new ResponseEntity<>(pregledDTO, HttpStatus.OK);
	}
	
	//vrati pregled pacijenta kod odredjenog lekara
	@GetMapping(value = "/pregledPacijenta/{id}" )
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<List<PregledDTO>> getPregledPacijenta(@PathVariable Long id, Principal pr) {
		Lekar lekar = lekarService.findByEmail(pr.getName());
		Pacijent pacijent = pacijentService.findByID(id);
		
		List<PregledDTO> pregledDTO = new ArrayList<>();
		//ako je pacijent od naseg lekara 
		if(lekar.getListaPacijenata().contains(pacijent)) {
			Set<Pregled> pregledi = pacijent.getListaPregleda();
			
			
			for (Pregled p : pregledi) {
			
				System.out.println("Status pregleda pacijenta " + pacijent.getIme() + " : " + p.getStatus());
				System.out.println("Lekar tog pregleda je : " + p.getLekar().getIme());
				if (p.getStatus() == 1 && p.getLekar().getId().equals(lekar.getId())) {
					
					pregledDTO.add(new PregledDTO(p));
				}

			}
		}
		

		return new ResponseEntity<>(pregledDTO, HttpStatus.OK);
	}

	//vrati mi preglede koji nisu pregledani od lekara
	@GetMapping(value = "/getPreglediLekara")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<List<PregledDTO>> getPreglediLekara(Principal p) {

		Lekar lekar = lekarService.findByEmail(p.getName());
		
		Set<Pregled> pregledi = lekar.getListaPregleda();
		
		List<PregledDTO> lista = new ArrayList<PregledDTO>();
		for (Pregled pre : pregledi) {
			System.out.println(pre.getStatus());
			if (pre.getStatus() == 1) {
				System.out.println("dodat");
				PregledDTO pregledDTO = new PregledDTO(pre);
				lista.add(pregledDTO);
			}
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping(value = "preuzmiPregledeKlinike/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<PregledDTO>> getPreglediKlinike(@PathVariable Long id) {

		Klinika klinika = klinikaService.findOne(id);
		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> lista = new ArrayList<PregledDTO>();
		for (Pregled s : pregledi) {
			if (s.getKlinika().getId() == klinika.getId()) {
				PregledDTO pregledDTO = new PregledDTO(s);
				lista.add(pregledDTO);
			}
		}

		System.out.println("Lista pregleda u klinici:" + klinika.getNaziv() + " ID: " + id);
		for (PregledDTO ss : lista) {
			System.out.println(ss.getCena());
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping(value = "preuzmiZahtevePregledaKlinike/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<PregledDTO>> getZahteviPreglediKlinike(@PathVariable Long id) {

		Klinika klinika = klinikaService.findOne(id);
		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> lista = new ArrayList<PregledDTO>();
		for (Pregled s : pregledi) {
			if (s.getKlinika().getId() == klinika.getId() && s.getStatus() == 0) {
				PregledDTO pregledDTO = new PregledDTO(s);
				lista.add(pregledDTO);
			}
		}

		System.out.println("Lista  zahtjeva pregleda u klinici:" + klinika.getNaziv() + " ID: " + id);
		for (PregledDTO ss : lista) {
			System.out.println(ss);
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@PutMapping(path = "/potvrda/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<PregledDTO> potvrdiPregled(@PathVariable Long id) {
		System.out.println("POTVRDA PREGLEDA");

		Pregled pregled = pregledService.findById(id);
		System.out.println(new PregledDTO(pregled));
		pregled.setStatus(1);
		System.out.println(new PregledDTO(pregled));
		Termin termin = new Termin();
		termin.setDatumPocetka(pregled.getDatum());
		termin.setTermin(pregled.getTermin());
		termin.setLekar(pregled.getLekar());
		termin.setSala(pregled.getSala());
		terminService.save(termin);
		Lekar lekar = lekarService.findOne(pregled.getLekar().getId());
		Sala sala = salaService.findOne(pregled.getSala().getId());
		pregledService.save(pregled);
		lekar.getListaZauzetihTermina().add(termin);
		sala.getZauzetiTermini().add(termin);
		lekarService.save(lekar);
		salaService.save(sala);
		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.OK);
	}

	@PutMapping(path = "/odbijanje/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<PregledDTO> odbijPregled(@PathVariable Long id) {
		System.out.println("DBIJANJE PREGLEDA");
		Pregled pregled = pregledService.findById(id);
		System.out.println(new PregledDTO(pregled));
		pregled.setStatus(2);
		System.out.println(new PregledDTO(pregled));
		pregledService.save(pregled);
		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.OK);
	}

	//otkazivanje pregleda
	@PutMapping(path = "/otkazivanje/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<PregledDTO> otkaziPregled(@PathVariable Long id) {
		System.out.println("OTKAZIVANEJ PREGLEDA");
		Pregled pregled = pregledService.findById(id);
		System.out.println(new PregledDTO(pregled));

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date date2 = cal.getTime();
		
		
		
		System.out.println();
		System.out.println(date);
		System.out.println(date2);
		System.out.println(pregled.getDatum().compareTo(date2));
		System.out.println(date2.compareTo(pregled.getDatum()));
		System.out.println();
		
		if(date2.compareTo(pregled.getDatum())*pregled.getDatum().compareTo(date)>=0) {
			System.out.println("datum je izmedju - ne moze se otkazati");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}else {
			System.out.println("datum je otkazan");
			pregled.setStatus(2);
			pregledService.save(pregled);
			return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.OK);

		}
		

		

		
	}
	
	
	//postupak rezervisanja sale za p
	@PostMapping(path = "/rezervisanje", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<String> rezervisanjeSale(@RequestBody PregledDTO pDTO){
		System.out.println("-----   REZERVISANJEEE -------------------------------");
		System.out.println(pDTO);
		Klinika klinika = klinikaService.findById(pDTO.getKlinikaID());
//		KlinickiCentar kc = listaKC.get(0);	
		
//		Pacijent p = pacijentService.findByEmail(paDTO.getEmail());
//		PacijentDTO pDTO = new PacijentDTO(p);
//		
////		Set<Pacijent> listaz = kc.getZahteviZaRegistraciju();
//		
//		if(kc.getZahteviZaRegistraciju().isEmpty()) {
//			System.out.println("prazna listaaa");
//			return new ResponseEntity<>("U listi ne postoji pacijent", HttpStatus.BAD_REQUEST);
//		}else {
//			
//			p.setOdobrenaRegistracija(true);
//			p = pacijentService.save(p);
//			System.out.println(p.getOdobrenaRegistracija());
//			
//			kc.getZahteviZaRegistraciju().remove(p);
//			kc.setZahteviZaRegistraciju(kc.getZahteviZaRegistraciju());
//			kc = KCService.save(kc);
//			System.out.println(kc.getZahteviZaRegistraciju().toString());
//		}
//
//			
//		String subject ="Odobrena registracija";
//		String text = "Postovani " + pDTO.getIme() + " " + pDTO.getPrezime() 
//					+ ",\n\nMolimo Vas da potvrdite vasu registraciju klikom na sledeci link: http://localhost:3000 .";
//
//		System.out.println(text);
//		
//		//slanje emaila
//		try {
//			emailService.poslatiOdgovorPacijentu(pDTO, subject, text);
//		}catch( Exception e ){
//			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
//			return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
//		}

		return new ResponseEntity<>("Odobreno", HttpStatus.OK);
	}
}
