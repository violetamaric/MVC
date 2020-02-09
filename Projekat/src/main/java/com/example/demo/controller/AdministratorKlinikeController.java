package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

import com.example.demo.controller.AdministratorKCController.PasswordChanger;
import com.example.demo.dto.AdministratorKCDTO;
import com.example.demo.dto.AdministratorKlinikeDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.model.AdministratorKC;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.service.AdministratorKlinikeService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/adminKlinike", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdministratorKlinikeController {
	@Autowired
	private AdministratorKlinikeService administratorKlinikeService;

	@Autowired
	private LekarService lekarService;

	@Autowired
	private KlinikaService klinikaService;

	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAuthority( 'ADMIN_KLINIKE') or hasAuthority('ADMIN_KC')")
	public ResponseEntity<AdministratorKlinikeDTO> getAdminKlinike(@PathVariable Long id) {

		AdministratorKlinike ak = administratorKlinikeService.findOne(id);

		// studen must exist
		if (ak == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new AdministratorKlinikeDTO(ak), HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<AdministratorKlinikeDTO>> getAll() {

		List<AdministratorKlinike> adminiKlinike = administratorKlinikeService.findAll();

		// convert students to DTOs
		List<AdministratorKlinikeDTO> administratorKlinikeDTO = new ArrayList<>();
		for (AdministratorKlinike ak : adminiKlinike) {
			administratorKlinikeDTO.add(new AdministratorKlinikeDTO(ak));
		}

		return new ResponseEntity<>(administratorKlinikeDTO, HttpStatus.OK);
	}

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	
	
//	@GetMapping(value = "/getLekarByEmail")
//	@CrossOrigin(origins = "http://localhost:3000")
//	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
//	public ResponseEntity<?> findByEmailLekara(Principal p){
//		
//		Lekar lekar = lekarService.findByEmail(p.getName());
//		if (lekar == null) {
//			System.out.println("Lekar nije pronadjen");
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		System.out.println("Lekar je pronadjen : "+ lekar);
//		
//		return ResponseEntity.ok(new LekarDTO(lekar));
//	}
//	
	

	@GetMapping(value = "/getAdminKlinikeByEmail")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority( 'ADMIN_KLINIKE') or hasAuthority('ADMIN_KC')")
	public ResponseEntity<AdministratorKlinikeDTO> findByEmail(Principal p) {

		AdministratorKlinike adminiKlinike = administratorKlinikeService.findByEmail(p.getName());

		if (adminiKlinike == null) {
			System.out.println("admin klinike nije pronadjen");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("Admin klinike je pronadjen : " + adminiKlinike.getEmail());

		return ResponseEntity.ok(new AdministratorKlinikeDTO(adminiKlinike));
	}

	// izmjena podataka admina klinika
	@PutMapping(path = "/update", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority( 'ADMIN_KLINIKE') or hasAuthority('ADMIN_KC')")
	public ResponseEntity<AdministratorKlinikeDTO> updateAdminKlinike(@RequestBody AdministratorKlinikeDTO administratorKlinikeDTO) {


		// a student must exist
		System.out.println("ADMIN KLINIKE UPDRATE");
		AdministratorKlinike adminiKlinike = administratorKlinikeService.findByEmail(administratorKlinikeDTO.getEmail());
		
		if(administratorKlinikeDTO.getEmail() != "" && administratorKlinikeDTO.getEmail() != null ) {
			if(administratorKlinikeDTO.getIme() != "" && administratorKlinikeDTO.getIme() != null ) {
				adminiKlinike.setIme(administratorKlinikeDTO.getIme());
			}
			if(administratorKlinikeDTO.getPrezime() != "" && administratorKlinikeDTO.getPrezime() != null  ) {
				adminiKlinike.setPrezime(administratorKlinikeDTO.getPrezime());
			}
			if(administratorKlinikeDTO.getTelefon() != "" && administratorKlinikeDTO.getTelefon() != null) {
				adminiKlinike.setTelefon(administratorKlinikeDTO.getTelefon());
			}
//			if(administratorKlinikeDTO.getLozinka() != "" && administratorKlinikeDTO.getLozinka() != null) {
//				adminiKlinike.setLozinka(administratorKlinikeDTO.getLozinka());
//			}
		}
		
		
//		if(administratorKlinikeDTO.getIdKlinike() != 0 && administratorKlinikeDTO.getIdKlinike() != null) {
//			List<Klinika> klinike = klinikaService.findAll();
//			for(Klinika k : klinike) {
//				if(k.getId() == administratorKlinikeDTO.getIdKlinike()) {
//					administratorKlinikeDTO.setIdKlinike(k.getId());
//				}
//			}
//		}
		

		adminiKlinike = administratorKlinikeService.save(adminiKlinike);
		return new ResponseEntity<>(new AdministratorKlinikeDTO(adminiKlinike), HttpStatus.OK);
	}

	// dodavanje novog lekara
	@PostMapping(path = "/dodavanjeLekara", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<LekarDTO> dodavanjeLeka(@RequestBody LekarDTO lekarDTO) {
		System.out.println("------------------------------------------------------");

		Lekar lekar = new Lekar();
		lekar.setIme(lekarDTO.getIme());
		lekar.setPrezime(lekarDTO.getPrezime());
		lekar.setEmail(lekarDTO.getEmail());
		lekar.setTelefon(lekarDTO.getTelefon());
		lekar.setLozinka(lekarDTO.getLozinka());

		long id = (long) lekarDTO.getKlinikaID();
		System.out.println(id);
		Klinika k = klinikaService.findById(id);
		lekar.setKlinika(k);
		
//		int id =3;
//		long idd = (long) id;
		System.out.println("~~~~~~~~~~~~~~~~~KLINIKA IZABRANA SA ID: " + lekarDTO.getKlinikaID());
		Klinika klinika = klinikaService.findById(lekarDTO.getKlinikaID());
		System.out.println(klinika);

		lekar.setKlinika(klinika);

		lekar = lekarService.save(lekar);
		klinika.getListaLekara().add(lekar);
		klinika = klinikaService.save(klinika);
		System.out.println("Lekar je dodat");
		System.out.println(lekar);

		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.CREATED);
	}

	
	//promena lozinke
	@PutMapping(path = "/promeniLozinku", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<?> promeniLozinku(@RequestBody PasswordChanger passCh, Principal pr) {

		// a student must exist
		System.out.println("Pacijent UPDRATE LOZINKA");
		AdministratorKlinike adminKC = administratorKlinikeService.findByEmail(pr.getName());
		
		
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

			return new ResponseEntity<>(new AdministratorKlinikeDTO(adminKC), HttpStatus.OK);
		}

		adminKC.setLozinka(passwordEncoder.encode(passCh.newPassword));
//		pacijent.setLbo(pacijentDTO.getLbo());
		adminKC.setStatus(1);
		adminKC = administratorKlinikeService.save(adminKC);
		return new ResponseEntity<>(new AdministratorKlinikeDTO(adminKC), HttpStatus.OK);
	}
	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}

}
