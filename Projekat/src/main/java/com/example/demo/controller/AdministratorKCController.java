package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.PacijentController.PasswordChanger;
import com.example.demo.dto.AdministratorKCDTO;
import com.example.demo.dto.AdministratorKlinikeDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.LekDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.AdministratorKC;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Authority;
import com.example.demo.model.KlinickiCentar;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lek;
import com.example.demo.model.Pacijent;
import com.example.demo.model.ZdravstveniKarton;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.service.AdministratorKCService;
import com.example.demo.service.AdministratorKlinikeService;
import com.example.demo.service.EmailService;
import com.example.demo.service.KlinickiCentarService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.PacijentService;
import com.example.demo.service.ZdravstveniKartonService;

//
//@CrossOrigin
@CrossOrigin(origins = "http://localhost:3000")
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
	private ZdravstveniKartonService zkService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	@Autowired
	private AuthorityRepository authorityRepository;
	//vrati mi sve admnistratore kc
	@GetMapping(value = "/svi")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<AdministratorKCDTO>> getAll(Principal p) {
		AdministratorKC admin = administratorKCService.findByEmail(p.getName());
		List<AdministratorKC> administratoriKC = administratorKCService.findAll();

		System.out.println("ISPISANI SVI ADMINISTRATORI KC IZ BAZE");
		List<AdministratorKCDTO> administratoriKCDTO = new ArrayList<>();
		for (AdministratorKC aKC : administratoriKC) {
			if(aKC.getStatus() != 2) { //samo koji nisu obrisani
				if(!aKC.equals(admin)) {
					administratoriKCDTO.add(new AdministratorKCDTO(aKC));
				}
				
			}
			
		}

		return new ResponseEntity<>(administratoriKCDTO, HttpStatus.OK);
	}
	
	//vrati mi trenutnog admnistratora kc
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value = "/pronadjenAdministratorKC")
	public ResponseEntity<AdministratorKCDTO> getAdministratorKCByEmail(Principal pr){
//		System.out.println(email);
		AdministratorKC administratorKC = administratorKCService.findByEmail(pr.getName());
		if (administratorKC == null) {
			System.out.println("NIJE PRONADJEN");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("PRONADJEN: "+ administratorKC.getEmail());
		
		return new ResponseEntity<>(new AdministratorKCDTO(administratorKC), HttpStatus.OK);
	}
	
	//vrati mi admina kc pomocu id-a
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value = "/pronadjenAdminKC/{id}")
	public ResponseEntity<AdministratorKCDTO> getAdministratorKCByEmail(@PathVariable Long id){
		System.out.println(id);
		AdministratorKC administratorKC = administratorKCService.findById(id);
		if (administratorKC == null) {
			System.out.println("NIJE PRONADJEN");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("PRONADJEN: "+ administratorKC.getEmail());
		
		return new ResponseEntity<>(new AdministratorKCDTO(administratorKC), HttpStatus.OK);
	}


	//vrati mi listu zahteva od korisnika tj mejlove
	@GetMapping(value = "/listaZahtevaZaRegistraciju")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<PacijentDTO>> getListaZahtevaZaRegistraciju(Principal pr) {

		AdministratorKC administratorKC = administratorKCService.findByEmail(pr.getName());
		
		KlinickiCentar kc = administratorKC.getKlinickiCentar();
		List<PacijentDTO> lista = new ArrayList<PacijentDTO>();
		for(Pacijent p : kc.getZahteviZaRegistraciju()) {
			if(p.getOdobrenaRegistracija() == 0) {
				PacijentDTO pDTO = new PacijentDTO(p);
				lista.add(pDTO);
			}
			
		}
		
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	//izmena podataka o adminu 
	@PutMapping(path="/izmena", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
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
	@PostMapping(path = "/potvrda", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<String> potvrdaRegistracijePacijenata(@RequestBody PacijentDTO paDTO){
		System.out.println("------------------------------------");
		
		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);	
		
		Pacijent p = pacijentService.findByEmail(paDTO.getEmail());
		ZdravstveniKarton zk = new ZdravstveniKarton();
		zk.setPacijent(p);
		zk = zkService.save(zk);
		
		p.setZdravstveniKarton(zk);
		PacijentDTO pDTO = new PacijentDTO(p);
		
//		Set<Pacijent> listaz = kc.getZahteviZaRegistraciju();
		
		if(kc.getZahteviZaRegistraciju().isEmpty()) {
			System.out.println("prazna listaaa");
			return new ResponseEntity<>("U listi ne postoji pacijent", HttpStatus.BAD_REQUEST);
		}else {
			
			p.setOdobrenaRegistracija(1);
			p = pacijentService.save(p);
			System.out.println(p.getOdobrenaRegistracija());
			
			kc.getZahteviZaRegistraciju().remove(p);
			kc.setZahteviZaRegistraciju(kc.getZahteviZaRegistraciju());
			kc = KCService.save(kc);
			System.out.println(kc.getZahteviZaRegistraciju().toString());
		}

			
		String subject ="Odobrena registracija";
		String text = "Postovani " + pDTO.getIme() + " " + pDTO.getPrezime() 
					+ ",\n\nMolimo Vas da potvrdite vasu registraciju klikom na sledeci link: http://localhost:3000/potvrdaRegistracije/"+p.getId()+" .";

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
	
	//TODO 2: NE RADI
	//odbijanje registracije pacijenata
	@PostMapping(path = "/odbijanje/{razlog}", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<String> odbijanjeRegistracijePacijenata(@RequestBody PacijentDTO paDTO, @PathVariable String razlog){
		System.out.println("------------------------------------");
		Pacijent p = pacijentService.findByEmail(paDTO.getEmail());
		PacijentDTO pDTO = new PacijentDTO(p);
		p.setOdobrenaRegistracija(3); //pac je obrisan
		p = pacijentService.save(p);


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

		

		System.out.println("Uspesno obrisan pacijent");

		return new ResponseEntity<>("Odbijeno", HttpStatus.OK);
	}
	
	//TODO 3: brisanje klinike
	@PostMapping(path = "/brisanjeKlinike", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<String> brisanjeKlinike(@RequestBody KlinikaDTO akcDTO) {
		System.out.println("------------------------------------------------------");
		System.out.println("pocinje");
		Klinika aKC = klinikaService.findById(akcDTO.getId());
		aKC.setStatus(1); //obrisana klinika
//		aKC.builder().status(1);
		aKC = klinikaService.save(aKC);
		
		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("uspesno obrisana klinika", HttpStatus.OK);
	}
	
	//TODO 4: brisanje admina klinike
	@PostMapping(path = "/brisanjeAdminaKlinike", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<String> brisanjeAdminaKlinike(@RequestBody AdministratorKlinikeDTO akcDTO) {
		System.out.println("------------------------------------------------------");
		System.out.println("pocinje");
		AdministratorKlinike aKC = administratorKlinikeService.findByEmail(akcDTO.getEmail());
		aKC.setStatus(2);
		aKC = administratorKlinikeService.save(aKC);
		
		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("uspesno obrisan admin klinike", HttpStatus.OK);
	}
	
	//TODO 1 : brisanje admina kc
	@PostMapping(path = "/brisanjeAdminaKC", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<String> brisanjeAdminaKC(@RequestBody AdministratorKCDTO akcDTO) {
		System.out.println("------------------------------------------------------");
		System.out.println("pocinje");
		AdministratorKC aKC = administratorKCService.findByEmail(akcDTO.getEmail());
		aKC.setStatus(2);
		aKC = administratorKCService.save(aKC);
		
		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("uspesno obrisan admin klinickog centra", HttpStatus.OK);
	}
	
	
	//dodavanje nove klinike
	@PostMapping(path = "/dodavanjeKlinike", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<KlinikaDTO> dodavanjeKlinike(@RequestBody KlinikaDTO klinikaDTO) {
		System.out.println("------------------------------------------------------");
		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);
		if(klinikaDTO.getNaziv() != "" && klinikaDTO.getNaziv() != null) {
			
			Klinika klinika = Klinika.builder()
					.naziv(klinikaDTO.getNaziv())
					.opis(klinikaDTO.getOpis())
					.adresa(klinikaDTO.getAdresa())
					.ocena(klinikaDTO.getOcena())
					.status(0)
					.klinickiCentar(kc)
					.build();
			
//			klinika.setNaziv(klinikaDTO.getNaziv());
//			klinika.setOpis(klinikaDTO.getOpis());
//			klinika.setAdresa(klinikaDTO.getAdresa());
//			klinika.setOcena(klinikaDTO.getOcena());
			

			
//			klinika.setKlinickiCentar(kc);
			klinika = klinikaService.save(klinika);
			
			kc.getListaKlinika().add(klinika);
			kc = KCService.save(kc);
			System.out.println("------------------------------------------------------");
		}
		
		return new ResponseEntity<>(klinikaDTO, HttpStatus.CREATED);
	}
	
	//dodavanje novog administratora klinike
	@PostMapping(path = "/dodavanjeAdminaKlinike", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<AdministratorKlinikeDTO> dodavanjeAdminaKlinike(@RequestBody AdministratorKlinikeDTO akDTO) {
		System.out.println("------------------------------------------------------");
		AdministratorKlinike ak = new AdministratorKlinike();
		ak.setIme(akDTO.getIme());
		ak.setPrezime(akDTO.getPrezime());
		ak.setEmail(akDTO.getEmail());
		ak.setLozinka(passwordEncoder.encode(akDTO.getLozinka()));
		ak.setTelefon(akDTO.getTelefon());
		ak.setStatus(0); //mora da promeni lozinku
		Set<Authority> authorities = new HashSet<Authority>();
		authorities.add(authorityRepository.findByUloga("ADMIN_KLINIKE"));
		ak.setAuthorities(authorities);
	
			
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
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<AdministratorKCDTO> dodavanjeAdminaKC(@RequestBody AdministratorKCDTO akDTO) {
		System.out.println("------------------------------------------------------");
		AdministratorKC ak = new AdministratorKC();
		ak.setIme(akDTO.getIme());
		ak.setPrezime(akDTO.getPrezime());
		ak.setEmail(akDTO.getEmail());
		ak.setLozinka(passwordEncoder.encode(akDTO.getLozinka()));
		ak.setStatus(0); //mora da promeni lozinku prilikom prvog logovanja
		Set<Authority> authorities = new HashSet<Authority>();
		authorities.add(authorityRepository.findByUloga("ADMIN_KC"));
		ak.setAuthorities(authorities);
		
		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);
		ak.setKlinickiCentar(kc);
		
		ak = administratorKCService.save(ak);
		
		kc.getListaAdminKC().add(ak);
		kc = KCService.save(kc);
		
		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>(new AdministratorKCDTO(ak), HttpStatus.CREATED);
	}
	
	//menjanje lozinke 
	@PutMapping(path = "/promeniLozinku", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<?> promeniLozinku(@RequestBody PasswordChanger passCh, Principal pr) {

		// a student must exist
		System.out.println("Pacijent UPDRATE LOZINKA");
		AdministratorKC adminKC = administratorKCService.findByEmail(pr.getName());
		
		
		System.out.println("LOZINKA: "+ adminKC.getLozinka());
		
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();
		System.out.println(username);
		if (authenticationManager != null) {
			System.out.println("PROMENJENA LOZINKA");

			final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, passCh.oldPassword));
//			Collection<?> roles = pacijent.getAuthorities();
//
//			String jwt = tokenUtils.tokenPacijent(pacijent, (Authority) roles.iterator().next());
//
//			int expiresIn = tokenUtils.getExpiredIn();
//
//			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, ((Authority) roles.iterator().next()).getUloga(),((Pacijent)authentication.getPrincipal()).getEmail()));
			System.err.println("-----");
			SecurityContextHolder.getContext().setAuthentication(authentication);
			System.out.println("-----");
		} else {
			System.out.println("NE MOZE SE PROMENITI LOZINKA");

			return new ResponseEntity<>(new AdministratorKCDTO(adminKC), HttpStatus.OK);
		}

		adminKC.setLozinka(passwordEncoder.encode(passCh.newPassword));
//		pacijent.setLbo(pacijentDTO.getLbo());
		adminKC.setStatus(1);
		adminKC = administratorKCService.save(adminKC);
		return new ResponseEntity<>(new AdministratorKCDTO(adminKC), HttpStatus.OK);
	}
	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}

	
}
