package com.example.demo.controller;

import java.security.Principal;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

@CrossOrigin(origins = "http://localhost:3000")
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
//	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> noviPregled(@RequestBody PregledDTO pregledDTO) {

		System.out.println("dodavanje novog pregleda");
		System.out.println(pregledDTO);
		Pregled pregled = new Pregled();
//		pregled.setCena(pregledDTO.getCena());
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
		pregled.setCena(tp.getCena());
		pregled = pregledService.save(pregled);


//		klinika.getListaPregleda().add(pregled);
//		klinika = klinikaService.save(klinika);

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
		String subject = "Pregled je zakazan";
		String text = "Postovani " + pDTO.getIme() + " " + pDTO.getPrezime()
				+ ",\n\nObavestavamo Vas da je Vas pregled uspesno prihvacen i zakazan.";

		System.out.println(text);

		// slanje emaila
		try {
			emailService.poslatiOdgovorPacijentu(pDTO, subject, text);
		} catch (Exception e) {
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
//			
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
	
	//vrati listu pregleda pacijenta kod odredjenog lekara
	@GetMapping(value = "/pregledPacijenta/{id}" )
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<List<PregledDTO>> getPregledPacijenta(@PathVariable Long id, Principal pr) {
		Lekar lekar = lekarService.findByEmail(pr.getName());
		Pacijent pacijent = pacijentService.findByID(id);


		
		
		Set<Pregled> pregledi = pacijent.getListaPregleda();
		List<PregledDTO> pregledDTO = new ArrayList<>();
			
		for (Pregled p : pregledi) {
			
			
			System.out.println("Status pregleda pacijenta " + pacijent.getIme() + " : " + p.getStatus());
			System.out.println("Lekar tog pregleda je : " + p.getLekar().getIme());
			if (p.getStatus() == 1 && p.getLekar().getId().equals(lekar.getId())) {
					
				pregledDTO.add(new PregledDTO(p));


			}
	
			
		}

		return new ResponseEntity<>(pregledDTO, HttpStatus.OK);
	}

	//vrati pregled pacijenta
	@GetMapping(value = "/getPregledPac/{id}" )
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> getPregledPac(@PathVariable Long id) {
		
		Pregled pregled = pregledService.findById(id);
		
		if(pregled == null) {
			return new ResponseEntity<>("greska", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.OK);
	}
	
	//vrati mi preglede koji nisu pregledani od lekara
	@GetMapping(value = "/getPreglediLekara")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<List<PregledDTO>> getPreglediLekara(Principal p) {

		Lekar lekar = lekarService.findByEmail(p.getName());

		List<Pregled> pregledi = pregledService.findAll();

		List<PregledDTO> lista = new ArrayList<PregledDTO>();
		for (Pregled pre : pregledi) {
			System.out.println(pre.getStatus());
			if (pre.getStatus() == 1 && pre.getLekar().getId() == lekar.getId()) {
				
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
			if (s.getKlinika().getId() == klinika.getId() && (s.getStatus()!=0 || s.getStatus()!=2)) {
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

	//zahtjevi za pregled klinike
	@GetMapping(value = "preuzmiZahtevePregledaKlinike/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<PregledDTO>> getZahteviPreglediKlinike(@PathVariable Long id) {

		Klinika klinika = klinikaService.findOne(id);
		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> lista = new ArrayList<PregledDTO>();
		for (Pregled s : pregledi) {
			if (s.getKlinika().getId() == klinika.getId() && s.getStatus() == 0 && s.getSala()==null) {
				PregledDTO pregledDTO = new PregledDTO(s);
				lista.add(pregledDTO);
			}
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
//		Termin termin = new Termin();
//		termin.setDatumPocetka(pregled.getDatum());
//		termin.setTermin(pregled.getTermin());
//		termin.setLekar(pregled.getLekar());
//		termin.setSala(pregled.getSala());
//		terminService.save(termin);
		Lekar lekar = lekarService.findOne(pregled.getLekar().getId());
		Sala sala = salaService.findOne(pregled.getSala().getId());
		pregledService.save(pregled);
		lekar.getListaPregleda().add(pregled);
		//sala.getZauzetiTermini().add(termin);
		lekarService.save(lekar);
	//	salaService.save(sala);
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
		
		List<Termin> terBrisanje = new ArrayList<Termin>();
		
		for(Termin t : terminService.findAll()) {
			if(t.getDatumPocetka().compareTo(pregled.getDatum())==0 && t.getTermin() == pregled.getTermin() && t.getSala().getId() == pregled.getSala().getId() && t.getLekar().getId() == pregled.getLekar().getId()) {
					Sala s = salaService.findById(t.getSala().getId());
					s.getZauzetiTermini().remove(t);
					salaService.save(s);
					
					Lekar l = lekarService.findById(t.getLekar().getId());
					l.getListaZauzetihTermina().remove(t);
					lekarService.save(l);
					
					terBrisanje.add(t);
					
			}
		}
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
		System.out.println(terBrisanje.size());
		for(Termin tt : terBrisanje) {
			terminService.delete(tt);
		}
		
		
		
		System.out.println(terBrisanje.size());
		
		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.OK);
	}

	// otkazivanje pregleda
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

		if (date2.compareTo(pregled.getDatum()) * pregled.getDatum().compareTo(date) >= 0) {
			System.out.println("datum je izmedju - ne moze se otkazati");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} else {
			System.out.println("datum je otkazan");
			pregled.setStatus(2);
			pregledService.save(pregled);
			List<Termin> terBrisanje = new ArrayList<Termin>();
			
			for(Termin t : terminService.findAll()) {
				if(t.getDatumPocetka().compareTo(pregled.getDatum())==0 && t.getTermin() == pregled.getTermin() && t.getSala().getId() == pregled.getSala().getId() && t.getLekar().getId() == pregled.getLekar().getId()) {
						Sala s = salaService.findById(t.getSala().getId());
						s.getZauzetiTermini().remove(t);
						salaService.save(s);
						
						Lekar l = lekarService.findById(t.getLekar().getId());
						l.getListaZauzetihTermina().remove(t);
						lekarService.save(l);
						
						terBrisanje.add(t);
						
				}
			}
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
			System.out.println(terBrisanje.size());
			for(Termin tt : terBrisanje) {
				terminService.delete(tt);
			}
			
			
			
			System.out.println(terBrisanje.size());
			return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.OK);

		}

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
				if (s.getTipSale() == 1) {
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
							System.out.println(
									"********************aaaa " + sD.getId() + sD.getBroj() + " " + t.getTermin());

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

		if (listaZauzete.size() == listaSalaKlinike.size()) {
			listaZauzete = new ArrayList<Sala>();
			// znaci da su sve sale zauzete za taj termin, tada mi vrati sve sale sa
			// disabled tim terminom, jer ima mogucnost da izabere novi termin i ako ima
			// potrebe da mijenja lekara
			for (Sala sala : listaSalaKlinike) {
				if (sala.getTipSale() == 1) {
					SalaDTO sdt = new SalaDTO();
					for (Termin termin : terminService.findAll()) {
						if (sala.getId() == termin.getSala().getId()) {
							if (termin.getDatumPocetka().equals(pregled.getDatum())
									&& termin.getTermin().equals(pregled.getTermin())) {
								sala.getZauzetiTermini().add(termin);
								TerminDTO terDTO = new TerminDTO(termin);

								sdt.getZauzetiTermini().add(terDTO);
								sdt.setNaziv(sala.getNaziv());
								sdt.setBroj(sala.getBroj());
								sdt.setId(sala.getId());
								sdt.setKlinikaID(sala.getKlinika().getId());

//								System.out.println();
//								System.out.println("********************bbbbb " + sala.getId() +" " + sala.getBroj() + " " + termin.getTermin());

							} else if (termin.getDatumPocetka().equals(pregled.getDatum())) {
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
//	@Async
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
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
//				p.setStatus(0); //treba nula
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
				t = terminService.save(t);
				l.getListaZauzetihTermina().add(t);
				lekarService.save(l);
				s.getZauzetiTermini().add(t);
				salaService.save(s);
				//odradi ko operacije ovako!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//				l.getListaZauzetihTermina().add(t);
//				lekarService.save(l);
//				Set<Pregled> lekarPregledii = l.getListaPregleda();
//				lekarPregledii.add(p);
//				l.setListaPregleda(lekarPregledii);
////				le.getListaOperacija().add(p);
//				lekarService.save(l);
				//premjestiti kod vioeletekad pacijent potvrdi pregled,
				//a a ko odbije pregled izbaciti iz liste zauzetih termina !

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



	//zakazivanje pregleda (lekar zakazuje)
	@PostMapping(value = "/zakazivanjePregledaLekar")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> zakazivanjePregledaLekar(@RequestBody PregledDTO pregledDTO, Principal pr) {
		System.out.println("*************");

		Lekar lekar = lekarService.findByEmail(pr.getName());
		
		System.out.println("dodavanje novog pregleda");
//		System.out.println(pregledDTO);
		
		Pregled pregled = new Pregled();
		
		pregled.setDatum(pregledDTO.getDatum());
		
		Klinika klinika = lekar.getKlinika();
		pregled.setKlinika(klinika);
		
		pregled.setLekar(lekar); 
		pregled.setTermin(pregledDTO.getTermin());
		
		Pacijent pacijent = pacijentService.findByEmail(pregledDTO.getPacijentEmail());
		pregled.setPacijent(pacijent);
		pregled.setStatus(0);
		
		TipPregleda tp = tipPregledaService.findOne(pregledDTO.getTipPregledaID());
		pregled.setTipPregleda(tp);
		pregled.setCena(tp.getCena()); 
		
		pregled = pregledService.save(pregled);
		
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
	
	
	@PostMapping(value = "/zakazivanjeOperacijeLekar")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> zakazivanjeOperacijeLekar(@RequestBody OperacijaDTO operacijaDTO, Principal pr) {
		System.out.println("*************");

		Lekar lekar = lekarService.findByEmail(pr.getName());
		
		System.out.println("dodavanje nove operacije");
//		System.out.println(pregledDTO);
		Operacija operacija = new Operacija();
		
		
		operacija.setDatum(operacijaDTO.getDatum());
		
		Klinika klinika = lekar.getKlinika();
		operacija.setKlinika(klinika);
		
//		operacija.setLekar(lekar); 
		operacija.setTermin(operacijaDTO.getTermin());
		
		Pacijent pacijent = pacijentService.findByEmail(operacijaDTO.getPacijentEmail());
		operacija.setPacijent(pacijent);
		operacija.setStatus(0);
		
		
		operacija.setTipOperacije(operacijaDTO.getTipOperacije());
		
		operacija.setCena(3000); 
		
		operacija = operacijaService.save(operacija);
		
		klinika.getListaOperacija().add(operacija);
		klinika = klinikaService.save(klinika);
		
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
	
	//vrati mi listu termina za neki datum (lekar zakazuje)
	@PostMapping(value = "/getTerminiLekaraZaDatum")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> getTerminiLekaraZaDatum(@RequestBody PregledDTO pregledDTO, Principal pr) {

		System.out.println("*************");

		Lekar lekar = lekarService.findByEmail(pr.getName());
		System.out.println(lekar.getIme());
		System.out.println(pregledDTO.getDatum());
		pregledDTO.getDatum().setHours(0);
		pregledDTO.getDatum().setMinutes(0);
		pregledDTO.getDatum().setSeconds(0);
		System.out.println(pregledDTO.getDatum());
		
//
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(pregledDTO.getDatum());
		date = c.getTime();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		

		System.out.println(date);
		
		Date date2 = new Date();
		c = Calendar.getInstance();
		c.setTime(pregledDTO.getDatum());
		c.add(Calendar.DATE, 1);
		date2 = c.getTime();
		System.out.println(date2);
		date2.setHours(0);
		date2.setMinutes(0);
		date2.setSeconds(0);
		System.out.println("----------------------------");
		System.out.println(date);
		System.out.println(date2);
		System.out.println();
		List<Termin> listaTermina = terminService.zauzetiTerminiLekara(lekar.getId(), date, date2);
		
		System.out.println(listaTermina.size());
		//1-brisi 9
		//2-brisi 11
		//3-brisi 13
		//4-brisi 15
		int fleg = 0;
		List<Integer> termini = new ArrayList<>();
		termini.add(9);
		termini.add(11);
		termini.add(13);
		termini.add(15);
		System.out.println(termini);

		List<TerminDTO> listaTerminaDTO = new ArrayList<TerminDTO>();
		for (Termin rd : listaTermina) {
			
			System.out.println(new TerminDTO(rd));
			listaTerminaDTO.add(new TerminDTO(rd));
		}
		for(TerminDTO tdto : listaTerminaDTO) {
			System.out.println(tdto.getTermin());
			
			if(termini.contains(tdto.getTermin())) {
				for(int i = 0; i < termini.size(); i++) {
					if(termini.get(i) == tdto.getTermin()) {
						termini.remove(i);
					}
				}
			}
		}
		System.out.println(termini);
		
		

		Set<OdmorOdsustvoLekar> listaool = lekar.getListaOdmorOdsustvo();
		for(OdmorOdsustvoLekar ool: listaool) {
			if(ool.getStatus() ==  1) {
				if( ool.getDatumOd().compareTo(pregledDTO.getDatum()) * pregledDTO.getDatum().compareTo(ool.getDatumDo()) >= 0) {
					System.out.println("-------------nalazi se odsustvo i odmor-------------");
					termini.clear();
					return new ResponseEntity<>(termini, HttpStatus.OK);
				}
			}
		}
		
		System.out.println(termini);
		System.out.println("*************");
		return new ResponseEntity<>(termini, HttpStatus.OK);
	}
	


//	@GetMapping( consumes = "application/json")
	@GetMapping(value = "/automatska")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
//	@Scheduled(cron = "20 44 03 * * ?")
    public ResponseEntity<List<SalaDTO>> automaticSchedule() {
		
		   List<SalaDTO> saD = new ArrayList<SalaDTO>();
		
        System.out.println("Automatska fja"); 
        List<Pregled> zahteviPregled = new ArrayList<Pregled>();
        
        //preuzeti zah za pregled kojim treba dodijeliti salu 
        for(Pregled pp: pregledService.findAll()) {
        	if(pp.getStatus()==0 && pp.getSala()==null) {
        		zahteviPregled.add(pp);
        	}
        }
        //nadje samo salo za taj pregled sa te klinike
        System.out.println("Automatska fja2");
        //preuzete sale klinike za pregled
        List<Sala> salePregled = new ArrayList<Sala>();
        for(Sala s: salaService.findAll()) {
        	for(Pregled pp : zahteviPregled) {
        		if(s.getTipSale()==1) {
        			if(s.getKlinika().getId() == pp.getKlinika().getId()) {
        				if(!salePregled.contains(s))
        					salePregled.add(s);
        			}
        		}
            	
            		
        	}
        	
        	
        }
        
        List<SalaDTO> listaSalaSlob = new ArrayList<SalaDTO>();
		List<Sala> listaZauzete = new ArrayList<Sala>();
        System.out.println(salaService.findAll().size());
        System.out.println(salePregled.size());
       
        List<Sala> listaSalaTest = new ArrayList<Sala>();
		boolean flag = false;
        for(Pregled pregled: zahteviPregled) {
        	try {
				
			} catch (Exception e) {
				// TODO: handle exception
			}
//        	  for (Sala sD : salePregled) {
//      			System.out.println();
//      			System.out.println(sD.getId());
//      			flag = false;
//      			SalaDTO sdt = new SalaDTO();
//      			for (Termin t : terminService.findAll()) {
//
//      				System.out.println(t);
//      				if (t.getSala().getId() == sD.getId()) {
//      					System.out.println(t.getSala().getId() + " " + t.getTermin() + " " + t.getDatumPocetka());
//      					if (t.getDatumPocetka().equals(pregled.getDatum()) && t.getTermin().equals(pregled.getTermin())) {
//      						System.out.println("Istiiii termin i njega preskoci");
//      						sD.getZauzetiTermini().add(t);
//      						listaZauzete.add(sD);
//
//      						flag = true;
//      						break;
//
//      					} else if (t.getDatumPocetka().equals(pregled.getDatum())) {
//      						System.out.println("||||||||||||||||||||||||||||||||||||||||||||");
//      						if (!t.getTermin().equals(pregled.getTermin())) {
//      							sD.getZauzetiTermini().add(t);
//      							System.out.println();
//      							salaService.save(sD);
//      							listaSalaTest.add(sD);
//      							System.out.println(
//      									"********************aaaa " + sD.getId() + sD.getBroj() + " " + t.getTermin());
//
//      							TerminDTO terminDTO = new TerminDTO(t);
//
//      							sdt.getZauzetiTermini().add(terminDTO);
//      						}
//      					}
//
//      				}
//
//      			}
//
//      			if (flag) {
//      				continue;
//      			}
//      			System.out.println("DUZINAAAAAAAAAAAAAAAAAAAA : " + listaSalaTest.size());
//    			listaSalaSlob.add(sdt);
//    			Sala sala = salaService.findById(sdt.getId());
//    			pregled.setSala(sala);
//              }
        }
      
//        System.out.println("Automatska fja3");
//        List<Sala> slobodne = new ArrayList<Sala>();
//        
//      
//        System.out.println("Trazim sve slobodne termine za taj pregled");
//        	for (Pregled p : zahteviPregled) {
//        		System.out.println("a");
//            for(Sala s: salePregled) {
//            	System.out.println("bbbb");
//            	for(Termin t: s.getZauzetiTermini()) {
//            		if(p.getDatum().compareTo(t.getDatumPocetka())==0) {
//            				System.out.println("NANANNA");
//            			if ( p.getTermin()!=t.getTermin()) {
//            				if(!slobodne.contains(s)) {
//            					slobodne.add(s);
//            				}
//            				
//            				break;
//            				//p.setSala(s);
////            				p.setDatum(t.getDatumPocetka());
////            				p.setTermin(t.getTermin());
////            				p.setKlinika(s.getKlinika());
////            				break;
//            			}
//            		}
//            	}
//            }
//            System.out.println(p);
//         
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//            for(Sala ss: slobodne) {
//            	System.out.println(ss);
//            	SalaDTO s = new SalaDTO(ss);
//            	saD.add(s);
//            }
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//        	}
//        	System.out.println(saD.size());
//       try {
//    	   System.out.println("try catch");
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("NE RADI");
//		}
        
//      
//            try {
//                medicalExaminationService.saveExamination(r.getDate(), r.getPrice(), r.getDuration(), r.getDiscount(), availableRooms.get(0).getId(),
//                        r.getClinic().getId(), r.getDoctor().getId(), r.getPatient().getId(), r.getType().getId(), r.getId(), false);
//
//            } catch (IndexOutOfBoundsException ioobe) {
//                List<MedicalExaminationRoom> availableRooms2;
//                int addDays = 1;
//                Date newDate;
//                do {
//                    Calendar c = Calendar.getInstance();
//                    c.setTime(r.getDate());
//                    c.add(Calendar.DATE, addDays);
//                    newDate = c.getTime();
//                    availableRooms2 = medicalExaminationRoomService.getAvailableRooms(r.getClinic().getId(), newDate);
//                    addDays++;
//                } while (availableRooms2.size() == 0);
//                medicalExaminationService.saveExamination(newDate, r.getPrice(), r.getDuration(), r.getDiscount(), availableRooms2.get(0).getId(),
//                        r.getClinic().getId(), r.getDoctor().getId(), r.getPatient().getId(), r.getType().getId(), r.getId(), false);
//
//            }
        return new ResponseEntity<>(saD, HttpStatus.OK);
    }

}
