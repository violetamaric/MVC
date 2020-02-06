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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdministratorKlinikeDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.OperacijaDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.SalaDTO;
import com.example.demo.dto.SlobodniTerminDTO;
import com.example.demo.dto.TerminDTO;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.OdmorOdsustvoLekar;
import com.example.demo.model.Operacija;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.Sala;
import com.example.demo.model.SlobodniTermin;
import com.example.demo.model.Termin;
import com.example.demo.model.TipPregleda;
import com.example.demo.service.EmailService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.OperacijaService;
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
	private OperacijaService operacijaService;
	@Autowired
	private SalaService salaService;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);


	@Autowired
	private TerminService terminService;
	

	@PostMapping(path = "/new", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('PACIJENT')")
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
	@PreAuthorize("hasAuthority('PACIJENT')")
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
	public ResponseEntity<PregledDTO> getPregled(@PathVariable Long id) {

		Pregled pregled = pregledService.findOne(id);
		System.out.println(pregled);

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

	//postupak rezervisanja sale za p
	@PostMapping(path = "/rezervisanje", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<String> rezervisanjeSaleNMP(@RequestBody PregledDTO pDTO){
		System.out.println("-----   REZERVISANJEEE -------------------------------");
		System.out.println(pDTO);
		Klinika klinika = klinikaService.findById(pDTO.getKlinikaID());
////		KlinickiCentar kc = listaKC.get(0);	
//		
////		Pacijent p = pacijentService.findByEmail(paDTO.getEmail());
////		PacijentDTO pDTO = new PacijentDTO(p);
////		
//////		Set<Pacijent> listaz = kc.getZahteviZaRegistraciju();
////		
////		if(kc.getZahteviZaRegistraciju().isEmpty()) {
////			System.out.println("prazna listaaa");
////			return new ResponseEntity<>("U listi ne postoji pacijent", HttpStatus.BAD_REQUEST);
////		}else {
////			
////			p.setOdobrenaRegistracija(true);
////			p = pacijentService.save(p);
////			System.out.println(p.getOdobrenaRegistracija());
////			
////			kc.getZahteviZaRegistraciju().remove(p);
////			kc.setZahteviZaRegistraciju(kc.getZahteviZaRegistraciju());
////			kc = KCService.save(kc);
////			System.out.println(kc.getZahteviZaRegistraciju().toString());
////		}
////
////			
////		String subject ="Odobrena registracija";
////		String text = "Postovani " + pDTO.getIme() + " " + pDTO.getPrezime() 
////					+ ",\n\nMolimo Vas da potvrdite vasu registraciju klikom na sledeci link: http://localhost:3000 .";
////
////		System.out.println(text);
////		
////		//slanje emaila
////		try {
////			emailService.poslatiOdgovorPacijentu(pDTO, subject, text);
////		}catch( Exception e ){
////			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
////			return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
////		}

		return new ResponseEntity<>("Odobreno", HttpStatus.OK);
	}
	
	
	
	
	// pronalazak sala slobodnih za taj teremin i datum za PREGLED
	@GetMapping(value = "/pronadjiSaleZaTajTermin/{idP}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> getSaleTermin(@PathVariable Long idP) {

		Pregled pregled = pregledService.findById(idP);
		System.out.println(pregled.getDatum() + " " + pregled.getTermin());

		List<Sala> sale = salaService.findAll();
		List<Sala> listaSalaKlinike = new ArrayList<Sala>();
		for (Sala s : sale) {
			if (s.getKlinika().getId() == pregled.getKlinika().getId()) {
				if(s.getTipSale()==1) {
					listaSalaKlinike.add(s);
				}
			}
		}

		List<Sala> listaSalaTest = new ArrayList<Sala>();
		boolean flag = false;
		List<SalaDTO> listaSalaSlob = new ArrayList<SalaDTO>();
		List<Sala> listaZauzete = new ArrayList<Sala>();
		for (Sala sD : listaSalaKlinike) {
			System.out.println();
			System.out.println(sD.getId());
			flag = false;
			SalaDTO sdt = new SalaDTO();
			for (Termin t : terminService.findAll()) {

				System.out.println(t);
				if (t.getSala().getId() == sD.getId()) {
					System.out.println(t.getSala().getId() + " " + t.getTermin() + " " + t.getDatumPocetka());
					if (t.getDatumPocetka().equals(pregled.getDatum()) && t.getTermin().equals(pregled.getTermin())) {
						System.out.println("Istiiii termin i njega preskoci");
							sD.getZauzetiTermini().add(t);
							listaZauzete.add(sD);

						flag = true;
						break;

					} else if (t.getDatumPocetka().equals(pregled.getDatum())) {
						System.out.println("||||||||||||||||||||||||||||||||||||||||||||");
						if (!t.getTermin().equals(pregled.getTermin())) {
							sD.getZauzetiTermini().add(t);
							System.out.println();
							salaService.save(sD);
							listaSalaTest.add(sD);
							System.out.println("********************aaaa " + sD.getId() + sD.getBroj() + " " + t.getTermin());

							TerminDTO terminDTO = new TerminDTO(t);

							sdt.getZauzetiTermini().add(terminDTO);
						}
					}

				}

			}

			if (flag) {
				continue;
			}

			
			sdt.setNaziv(sD.getNaziv());
			sdt.setBroj(sD.getBroj());
			sdt.setId(sD.getId());
			sdt.setKlinikaID(sD.getKlinika().getId());

			System.out.println();
			System.out.println("DUZINAAAAAAAAAAAAAAAAAAAA : " + listaSalaTest.size());
			listaSalaSlob.add(sdt);
//			
			
		}

	
		if(listaZauzete.size()==listaSalaKlinike.size()) {
			listaZauzete = new ArrayList<Sala>();
			//znaci da su sve sale zauzete za taj termin, tada mi vrati sve sale sa 
			//disabled tim terminom, jer ima mogucnost da izabere novi termin i ako ima potrebe da mijenja lekara 
			for (Sala sala : listaSalaKlinike) {
				if(sala.getTipSale()==1) {
					SalaDTO sdt = new SalaDTO();
					for (Termin termin : terminService.findAll()) {
						if(sala.getId()==termin.getSala().getId()) {
							if (termin.getDatumPocetka().equals(pregled.getDatum()) && termin.getTermin().equals(pregled.getTermin())) {
								sala.getZauzetiTermini().add(termin);
								TerminDTO terDTO = new TerminDTO(termin);

								sdt.getZauzetiTermini().add(terDTO);
								sdt.setNaziv(sala.getNaziv());
								sdt.setBroj(sala.getBroj());
								sdt.setId(sala.getId());
								sdt.setKlinikaID(sala.getKlinika().getId());

//								System.out.println();
//								System.out.println("********************bbbbb " + sala.getId() +" " + sala.getBroj() + " " + termin.getTermin());
								
							}else if(termin.getDatumPocetka().equals(pregled.getDatum())){
								sala.getZauzetiTermini().add(termin);
								TerminDTO terDTO = new TerminDTO(termin);

								sdt.getZauzetiTermini().add(terDTO);
								sdt.setNaziv(sala.getNaziv());
								sdt.setBroj(sala.getBroj());
								sdt.setId(sala.getId());
								sdt.setKlinikaID(sala.getKlinika().getId());
	//
//								System.out.println();
//								System.out.println("********************bbbbb " + sala.getId() + sala.getBroj() + " " + termin.getTermin());
							}
						}
						
						
					}
//					System.out.println();
					salaService.save(sala);
				

					
					listaSalaSlob.add(sdt);
				}
			
				
			}
			
			
		}
		return new ResponseEntity<>(listaSalaSlob, HttpStatus.OK);
	}

	// rezervisanje sale i slanje mejla pacijentu i lekaru
	@PostMapping(path = "/rezervisanjeSale", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<String> rezervisanjeSale(@RequestBody PregledDTO pDTO) {
		System.out.println();
		System.out.println("......... Rezervisanje sale ..... ");
		System.out.println(pDTO);
		System.out.println();

		List<Pregled> listaPregleda = pregledService.findAll();
		Pacijent pacijent = null;
		Lekar l = null;
		for (Pregled p : listaPregleda) {
			if (p.getId().equals(pDTO.getId())) {
				p.setStatus(1);
				Sala s = salaService.findById(pDTO.getSalaID());
				p.setSala(s);
				pacijent = pacijentService.findByID(pDTO.getPacijentID());

				l = lekarService.findById(pDTO.getLekarID());
				p.setLekar(l);
				p.setTermin(pDTO.getTermin());
				p.setDatum(pDTO.getDatum());
				pregledService.save(p);
				Termin t = new Termin();
				t.setTermin(pDTO.getTermin());
				int idT = terminService.findAll().size() + 1;
				long idTermina = (long) idT;
				t.setId(idTermina);
				t.setDatumPocetka(pDTO.getDatum());
				t.setSala(s);
				t.setLekar(l);
				terminService.save(t);

			}
		}

		System.out.println("REZERVISANOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

		String subject = "Rezervisana sala";
		String text = "Postovani " + pacijent.getIme() + " " + pacijent.getPrezime()
				+ ",\n\nMolimo Vas da potvrdite vas pregled klikom na sledeci link: http://localhost:3000 .";

		System.out.println(text);

		PacijentDTO pd = new PacijentDTO(pacijent);
		LekarDTO ld = new LekarDTO(l);
		// slanje emaila
		try {
			emailService.poslatiOdgovorPacijentu(pd, subject, text);
		} catch (Exception e) {
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
		}

		String subject2 = "Rezervisana sala";
		String text2 = "Postovani " + l.getIme() + " " + l.getPrezime() + ",\n\nRezervisana je sala za Vas pregled! ";

		System.out.println(text);

		// slanje emaila
		try {
			emailService.poslatiOdgovorLekaru(ld, subject2, text2);
		} catch (Exception e) {
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>("Mail nije poslat2", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("uspesno rezervisana sala1", HttpStatus.OK);
	}

	// metoda koja vraca listu slobodnih lekara za taj datum i termin
		@PostMapping(path = "/pronadjiLekaraZaPregled", consumes = "application/json")
		@CrossOrigin(origins = "http://localhost:3000")
		@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
		public ResponseEntity<List<LekarDTO>> slobodanLekar(@RequestBody PregledDTO pDTO) {

			List<LekarDTO> listaSlobodnihLekara = new ArrayList<LekarDTO>();
			Klinika klinika = klinikaService.findById(pDTO.getKlinikaID());

			System.out.println(pDTO);
			List<Lekar> listaSvihLekaraKlinike = new ArrayList<Lekar>();
			List<Lekar> listaSvihLekara = lekarService.findAll();
			for (Lekar l : listaSvihLekara) {
				if (l.getKlinika().equals(klinika)) {
					listaSvihLekaraKlinike.add(l);
				}

			}
			System.out.println();
			System.out.println(" SVI LEKARI KLINIKE *************** " + listaSvihLekaraKlinike.size());
			System.out.println(listaSvihLekaraKlinike.size());

			// Lekar lekar = lekarService.findByEmail(ooDTO.getEmailL());
			for (Lekar lekar : listaSvihLekaraKlinike) {
				System.out.println("-----> " + lekar.getId() + " " + lekar.getIme() + " " + lekar.getPrezime());
				boolean flag = false;
//				Set<Termin> pregledi = lekar.getListaZauzetihTermina();
				for (Pregled t : pregledService.findAll()) {
					if (t.getStatus() == 1) {
						if (t.getLekar().getId() == lekar.getId()) {
							System.out.println("LEKAR ISTI");
							if (t.getTermin() == pDTO.getTermin()) {
								System.out.println("TERMIN ISTI");
								if (t.getDatum().compareTo(pDTO.getDatum()) == 0) {
									System.out.println("Zauzet lekar :");
									System.out.println(lekar.getId() + " " + lekar.getIme() + " " + lekar.getPrezime());
									System.out.println();
									flag = true;
									break;

								}

							}
						}
					}

				}

				Set<OdmorOdsustvoLekar> listaool = lekar.getListaOdmorOdsustvo();

				for (OdmorOdsustvoLekar ool : listaool) {
					if (ool.getStatus() == 1) {
						if (ool.getDatumOd().compareTo(pDTO.getDatum())
								* pDTO.getDatum().compareTo(ool.getDatumDo()) >= 0) {
							System.out.println();
							System.out.println("Lekar na odmoru");
							System.out.println(lekar.getId() + " " + lekar.getIme() + " " + lekar.getPrezime());
							System.out.println();
							flag = true;
							break;
						}

					}
				}

				if (!flag) {
					LekarDTO l = new LekarDTO(lekar);
					listaSlobodnihLekara.add(l);
				}

			}

			for (LekarDTO l : listaSlobodnihLekara) {
				System.out.println(l);
			}

			System.out.println("*************** " + listaSlobodnihLekara.size());

			return new ResponseEntity<>(listaSlobodnihLekara, HttpStatus.OK);
		}
}
