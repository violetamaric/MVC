package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.ZdravstveniKartonDTO;
import com.example.demo.model.Authority;
import com.example.demo.model.Pacijent;
import com.example.demo.model.UserTokenState;
import com.example.demo.model.ZdravstveniKarton;
import com.example.demo.service.KlinickiCentarService;
import com.example.demo.service.PacijentService;
import com.example.demo.service.ZdravstveniKartonService;

@RestController
//@RequestMapping(value = "/api/pacijenti", produces=MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = "/api/pacijenti")
public class PacijentController {

	@Autowired
	private PacijentService pacijentService;

	@Autowired
	private KlinickiCentarService KCService;

	@Autowired
	private ZdravstveniKartonService ZKService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
//	@Autowired
//	private EmailService emailService;
//
//	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping(value = "/all", produces = "application/json;charset=UTF-8")
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

//	@GetMapping(value = "/findPacijentEmail/{email:.+}")
	@GetMapping(value = "/findPacijentEmail")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<PacijentDTO> getPacijentByEmail(Principal p) {
		System.out.println("find pacijent");
		System.out.println(p.getName());
		Pacijent pacijent = pacijentService.findByEmail(p.getName());
		System.out.println("pacijent " + pacijent);
		if (pacijent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(pacijent.getEmail() + "++++");
		return ResponseEntity.ok(new PacijentDTO(pacijent));
	}

	@GetMapping(value = "/findZK")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<ZdravstveniKartonDTO> getZK(Principal pr) {

		System.out.println("find pacijent");
		System.out.println("zk");

		Pacijent pacijent = pacijentService.findByEmail(pr.getName());
		System.out.println("Pacijent: " + pacijent.getZdravstveniKarton().getId());

		if (pacijent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		ZdravstveniKarton zk = pacijent.getZdravstveniKarton();
		zk.setPacijent(pacijent);
		ZKService.save(zk);
		System.out.println("____");
		System.out.println(zk);
//		zk.setPacijent(pacijent);
		System.out.println(zk);
		System.out.println("---------------- " + zk.getPacijent());
		System.out.println(pacijent.getEmail() + "++++");
//		Pacijent p = new Pacijent();
//		p.setEmail(pacijent.getEmail());
//		zk.setPacijent(p);
		return new ResponseEntity<>(new ZdravstveniKartonDTO(zk), HttpStatus.OK);
	}

	@GetMapping(value = "/findPacijentLekar/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<PacijentDTO> getPacijentByIdLekar(@PathVariable Long id) {

		System.out.println("find pacijent");
		System.out.println(id);
		Pacijent pacijent = pacijentService.findByID(id);
		System.out.println("pacijent " + pacijent);
		if (pacijent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(pacijent.getEmail() + "++++");
		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
	}

	// metoda za vracanje pacijenta- za med sestru I LEKARA
	@GetMapping(value = "/findPacijentEmailMS")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('MED_SESTRA') or hasAuthority('LEKAR')")
	public ResponseEntity<PacijentDTO> getPacijentByEmailMS(@RequestBody PacijentDTO pacijentDTO) {

		System.out.println("find pacijent");
		System.out.println(pacijentDTO.getEmail());
		Pacijent pacijent = pacijentService.findByEmail(pacijentDTO.getEmail());
		System.out.println("pacijent " + pacijent);
		if (pacijent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(pacijent.getEmail() + "++++");
		return ResponseEntity.ok(new PacijentDTO(pacijent));
	}

	// metoda za vracanje zdravstvenog kartona- za med sestru
	@GetMapping(value = "/findZKMS", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('MED_SESTRA') ")
	public ResponseEntity<ZdravstveniKarton> getZKMS(@RequestBody PacijentDTO pacijentDTO) {

		System.out.println("find pacijent");
		System.out.println("zk");

		Pacijent pacijent = pacijentService.findByEmail(pacijentDTO.getEmail());
		System.out.println("Pacijent: " + pacijent);
		if (pacijent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		ZdravstveniKarton zk = pacijent.getZdravstveniKarton();
		System.out.println(pacijent.getEmail() + "++++");
		Pacijent p = new Pacijent();
		p.setEmail(pacijent.getEmail());
		zk.setPacijent(p);
		return new ResponseEntity<>(new ZdravstveniKarton(zk), HttpStatus.OK);
	}

	@GetMapping(value = "/findByID/{id}")
	public ResponseEntity<?> getPacijentByID(@PathVariable Long id) {
		Pacijent pacijent = pacijentService.findByID(id);
		return ResponseEntity.ok(new PacijentDTO(pacijent));
	}

	@PutMapping(path = "/update", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> updatePacijent(@RequestBody PacijentDTO pacijentDTO) {

		// a student must exist
		System.out.println("Pacijent UPDRATE");
		Pacijent pacijent = pacijentService.findByEmail(pacijentDTO.getEmail());

		pacijent.setIme(pacijentDTO.getIme());
		pacijent.setPrezime(pacijentDTO.getPrezime());
		pacijent.setTelefon(pacijentDTO.getTelefon());
		pacijent.setAdresa(pacijentDTO.getAdresa());
		pacijent.setGrad(pacijentDTO.getGrad());
		pacijent.setDrzava(pacijent.getDrzava());
//		pacijent.setLbo(pacijentDTO.getLbo());

		pacijent = pacijentService.save(pacijent);
		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
	}

	@PutMapping(path = "/promeniLozinku", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> promeniLozinku(@RequestBody PasswordChanger passCh, Principal pr) {

		// a student must exist
		System.out.println("Pacijent UPDRATE LOZINKA");
		Pacijent pacijent = pacijentService.findByEmail(pr.getName());
		System.out.println("LOZINKA: "+ pacijent.getLozinka());
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

			return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
		}

		pacijent.setLozinka(passwordEncoder.encode(passCh.newPassword));
//		pacijent.setLbo(pacijentDTO.getLbo());

		pacijent = pacijentService.save(pacijent);
		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
	}
	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}


}