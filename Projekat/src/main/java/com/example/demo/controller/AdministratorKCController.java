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
import com.example.demo.model.AdministratorKC;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Dijagnoza;
import com.example.demo.model.KlinickiCentar;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lek;
import com.example.demo.model.Pacijent;
import com.example.demo.service.AdministratorKCService;
import com.example.demo.service.AdministratorKlinikeService;
import com.example.demo.service.DijagnozaService;
import com.example.demo.service.EmailService;
import com.example.demo.service.KlinickiCentarService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekService;
import com.example.demo.service.PacijentService;



@RestController
@RequestMapping(value="/api/administratoriKC", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdministratorKCController {
	
	@Autowired
	private AdministratorKCService administratorKCService;
	
	@Autowired
	private AdministratorKlinikeService administratorKlinikeService;
	
	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private KlinikaService klinikaService;

	@Autowired
	private KlinickiCentarService KCService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private LekService lekService;
	
	@Autowired
	private DijagnozaService dijagnozaService;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	//vrati mi sve admnistratore kc
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
	
	//vrati mi trenutnog admnistratora kc
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

		System.out.println("ADMIN KC IZMENA");
		AdministratorKC aKC = administratorKCService.findByEmail(akcDTO.getEmail());
		
		if(akcDTO.getIme() != null && akcDTO.getIme() != "") {
			System.out.println("izmenjeno ime admina");
			aKC.setIme(akcDTO.getIme());	
		}
		if(akcDTO.getPrezime() != null && akcDTO.getPrezime() != "") {
			System.out.println("izmenjeno prezime admina");
			aKC.setPrezime(akcDTO.getPrezime());
		}
		if(akcDTO.getLozinka() != null && akcDTO.getLozinka() != "") {
			System.out.println("izmenjena lozinka admina");
			aKC.setLozinka(akcDTO.getLozinka());
		}
		aKC = administratorKCService.save(aKC);
		return new ResponseEntity<>(new AdministratorKCDTO(aKC), HttpStatus.OK);
	}

	//potvrda registracije
	@PostMapping(path = "/potvrda/{email}", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<String> potvrdaRegistracijePacijenata(@PathVariable String email){
		System.out.println("------------------------------------");
		Pacijent p = pacijentService.findByEmail(email);
		PacijentDTO pDTO = new PacijentDTO(p);

		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);	
	
		if(kc.getZahteviZaRegistraciju().isEmpty()) {
			System.out.println("prazna listaaa");
			return new ResponseEntity<>("U listi ne postoji pacijent", HttpStatus.BAD_REQUEST);
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
		String text = "Postovani " + pDTO.getIme() + " " + pDTO.getPrezime() 
					+ ",\n\nMolimo Vas da potvrdite vasu registraciju klikom na sledeci link: http://localhost:3000 .";

		System.out.println(text);
		
		//slanje emaila
		try {
			emailService.poslatiOdgovorPacijentu(pDTO, subject, text);
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Odobreno", HttpStatus.OK);
	}
	
	//odbijanje registracije pacijenata
	@PostMapping(path = "/odbijanje/{email}/{razlog}", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<String> odbijanjeRegistracijePacijenata(@PathVariable String email, @PathVariable String razlog){
		System.out.println("------------------------------------");
		Pacijent p = pacijentService.findByEmail(email);
		PacijentDTO pDTO = new PacijentDTO(p);

		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);

//		if(kc.getZahteviZaRegistraciju().isEmpty()) {
//			System.out.println("prazna listaaa");
//			return new ResponseEntity<>("U listi ne postoji taj pacijent", HttpStatus.BAD_REQUEST);
//		}else {
			System.out.println("Uspesno obrisan pacijent");
			kc.getZahteviZaRegistraciju().remove(p);
//			pacijentService.delete(p);
			kc.setZahteviZaRegistraciju(kc.getZahteviZaRegistraciju());
			kc = KCService.save(kc);
			
//		}

		String subject ="Odobijena registracija";
		String text = "Postovani " + pDTO.getIme() + " " + pDTO.getPrezime() 
					+ ",\n\nVasa registracija je odbijena od strane administratorskog tima Klinickog Centra. \nRazlog odbijanja: \n"
					+ razlog + "\n\nS postovanjem,\nKlinicki Centar";
		System.out.println(text);
			
		//slanje emaila
		try {
			emailService.poslatiOdgovorPacijentu(pDTO, subject, text);
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Odbijeno", HttpStatus.OK);
	}
	
	//dodavanje nove klinike
	@PostMapping(path = "/dodavanjeKlinike", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<KlinikaDTO> dodavanjeKlinike(@RequestBody KlinikaDTO klinikaDTO) {
		System.out.println("------------------------------------------------------");
		Klinika klinika = new Klinika();
		if(klinikaDTO.getNaziv() != "" && klinikaDTO.getNaziv() != null) {
			
			klinika.setNaziv(klinikaDTO.getNaziv());
			klinika.setOpis(klinikaDTO.getOpis());
			klinika.setAdresa(klinikaDTO.getAdresa());
			klinika.setOcena(klinikaDTO.getOcena());
			
			List<KlinickiCentar> listaKC = KCService.find();
			KlinickiCentar kc = listaKC.get(0);
			
			klinika.setKlinickiCentar(kc);
			klinika = klinikaService.save(klinika);
			
			kc.getListaKlinika().add(klinika);
			kc = KCService.save(kc);
			System.out.println("------------------------------------------------------");
		}
		
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.CREATED);
	}
	
	//dodavanje novog administratora klinike
	@PostMapping(path = "/dodavanjeAdminaKlinike", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<AdministratorKlinikeDTO> dodavanjeAdminaKlinike(@RequestBody AdministratorKlinikeDTO akDTO) {
		System.out.println("------------------------------------------------------");
		AdministratorKlinike ak = new AdministratorKlinike();
		ak.setIme(akDTO.getIme());
		ak.setPrezime(akDTO.getPrezime());
		ak.setEmail(akDTO.getEmail());
		ak.setLozinka(akDTO.getLozinka());
		ak.setTelefon(akDTO.getTelefon());
			
		Klinika k = klinikaService.findById(akDTO.getIdKlinike());
		ak.setKlinika(k);
		ak = administratorKlinikeService.save(ak);
		k.getListaAdminKlinike().add(ak);
		k = klinikaService.save(k);

		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>(new AdministratorKlinikeDTO(ak), HttpStatus.CREATED);
	}
	
	//dodavanje novog administratora kc
	@PostMapping(path = "/dodavanjeAdminaKC", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<AdministratorKCDTO> dodavanjeAdminaKC(@RequestBody AdministratorKCDTO akDTO) {
		System.out.println("------------------------------------------------------");
		AdministratorKC ak = new AdministratorKC();
		ak.setIme(akDTO.getIme());
		ak.setPrezime(akDTO.getPrezime());
		ak.setEmail(akDTO.getEmail());
		ak.setLozinka(akDTO.getLozinka());

		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);
		ak.setKlinickiCentar(kc);
		
		ak = administratorKCService.save(ak);
		
		kc.getListaAdminKC().add(ak);
		kc = KCService.save(kc);
		
		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>(new AdministratorKCDTO(ak), HttpStatus.CREATED);
	}
	
	//dodavanje  novog leka
	@PostMapping(path = "/dodavanjeLeka", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<LekDTO> dodavanjeLeka(@RequestBody LekDTO lekDTO) {
		System.out.println("------------------------------------------------------");
		Lek lek = new Lek();
		lek.setNaziv(lekDTO.getNaziv());
		lek.setSifra(lekDTO.getSifra());
		
		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);
		
		lek.setKlinickiCentar(kc);
			
		lek = lekService.save(lek);		
		kc.getListaLekova().add(lek);
		kc = KCService.save(kc);

		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>(new LekDTO(lek), HttpStatus.CREATED);
	}
	
	//dodavanje nove dijagnoze
	@PostMapping(path = "/dodavanjeDijagnoze", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<DijagnozaDTO> dodavanjeDijagnoze(@RequestBody DijagnozaDTO dijagnozaDTO) {
		System.out.println("------------------------------------------------------");
		Dijagnoza dijagnoza = new Dijagnoza();
		
		dijagnoza.setNaziv(dijagnozaDTO.getNaziv());
		dijagnoza.setOpis(dijagnozaDTO.getOpis());
		
		dijagnoza.setOznaka(dijagnozaDTO.getOznaka());
		
		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);
		
		dijagnoza.setKlinickiCentar(kc);
			
		dijagnoza = dijagnozaService.save(dijagnoza);		
		kc.getListaDijagnoza().add(dijagnoza);
		kc = KCService.save(kc);

		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>(new DijagnozaDTO(dijagnoza), HttpStatus.CREATED);
	}

	//brisanje klinike
	//brisanje admina klinike
	//brisanje admina kc
	//izmena klinike
	//izmena admina klinike
	
	//brisanje leka
	@PostMapping(path = "/brisanjeLeka", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<String> brisanjeLeka(@RequestBody LekDTO lekDTO) {
		System.out.println("------------------------------------------------------");
		System.out.println("pocinje");
		Lek lek = lekService.findById(lekDTO.getId());
		
		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);
			
		
		if(kc.getListaLekova().contains(lek)) {
			
			Set<Lek> lista = kc.getListaLekova();
			lista.remove(lek);
			kc.getListaLekova().clear();
			kc.setListaLekova(lista);
			
			lekService.delete(lek);	
			
			kc = KCService.save(kc);
			System.out.println("obrisano");
		}
		
		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("uspesno obrisan lek", HttpStatus.OK);
	}
	
	//brisanje dijagnoze
	@PostMapping(path = "/brisanjeDijagnoze", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<String> brisanjeDijagnoze(@RequestBody DijagnozaDTO dijagnozaDTO) {
		System.out.println("------------------------------------------------------");
		Dijagnoza dijagnoza = dijagnozaService.findById(dijagnozaDTO.getId());	
		
		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);
			
		if(kc.getListaDijagnoza().contains(dijagnoza)) {
			
			Set<Dijagnoza> lista = kc.getListaDijagnoza();
			lista.remove(dijagnoza);
			kc.getListaDijagnoza().clear();
			kc.setListaDijagnoza(lista);
			
			dijagnozaService.delete(dijagnoza);	
			
			kc = KCService.save(kc);
			System.out.println("obrisano");
		}

		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("uspesno brisanje dijagnoze", HttpStatus.OK);
	}

	//izmena leka
	@PutMapping(path = "/izmenaLeka", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<LekDTO> izmenaLeka(@RequestBody LekDTO lekDTO) {
		System.out.println("------------------------------------------------------");
		Lek lek = lekService.findById(lekDTO.getId());
		
		if(lekDTO.getNaziv() != null && lekDTO.getNaziv() != "") {
			System.out.println("izmenjen naziv leka");
			lek.setNaziv(lekDTO.getNaziv());	
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		if(lekDTO.getSifra() != null && lekDTO.getSifra() != "") {
			System.out.println("izmenjena sifra leka");
			//TODO 2: STAVITI ZABRANU DA NE MOZE ISTA SIFRA
			lek.setSifra(lekDTO.getSifra());	
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		lek = lekService.save(lek);

		System.out.println("------------------------------------------------------");
					
		return new ResponseEntity<>(new LekDTO(lek), HttpStatus.CREATED);
	}
	
	//izmena dijagnoze
	@PutMapping(path = "/izmenaDijagnoze", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<DijagnozaDTO> izmenaDijagnoze(@RequestBody DijagnozaDTO dijagnozaDTO) {
		System.out.println("------------------------------------------------------");
		Dijagnoza dijagnoza = dijagnozaService.findById(dijagnozaDTO.getId());	
		
		if(dijagnozaDTO.getNaziv() != null && dijagnozaDTO.getNaziv() != "") {
			System.out.println("izmenjen naziv dijagnoze");
			dijagnoza.setNaziv(dijagnozaDTO.getNaziv());	
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		if(dijagnozaDTO.getOznaka() != null && dijagnozaDTO.getOznaka() != "") {
			System.out.println("izmenjena oznaka dijagnoze");
			//TODO 2: STAVITI ZABRANU DA NE MOZE ISTA SIFRA
			dijagnoza.setOznaka(dijagnozaDTO.getOznaka());	
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(dijagnozaDTO.getOpis() != null && dijagnozaDTO.getOpis() != "") {
			System.out.println("izmenjen opis dijagnoze");
			//TODO 2: STAVITI ZABRANU DA NE MOZE ISTA SIFRA
			dijagnoza.setOpis(dijagnozaDTO.getOpis());	
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		dijagnoza = dijagnozaService.save(dijagnoza);

		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>(new DijagnozaDTO(dijagnoza), HttpStatus.CREATED);
	}

	//vracanje leka
		@GetMapping(path = "/getLek/{id}")
		@CrossOrigin(origins = "http://localhost:3000")
		public ResponseEntity<LekDTO> getLek(@PathVariable Long id) {
			System.out.println("------------------------------------------------------");

			Lek lek = lekService.findById(id);
			
			LekDTO lekDTO = new LekDTO(lek);
			
			System.out.println("------------------------------------------------------");
			return new ResponseEntity<>(lekDTO, HttpStatus.OK);
		}
	//vracanje dijagnoze
		@GetMapping(path = "/getDijagnoza/{id}")
		@CrossOrigin(origins = "http://localhost:3000")
		public ResponseEntity<DijagnozaDTO> getDijagnoza(@PathVariable Long id) {
			System.out.println("------------------------------------------------------");

			Dijagnoza dijagnoza = dijagnozaService.findById(id);
			
			DijagnozaDTO dijagnozaDTO = new DijagnozaDTO(dijagnoza);
			
			System.out.println("------------------------------------------------------");
			return new ResponseEntity<>(dijagnozaDTO, HttpStatus.OK);
		}
	
	
}
