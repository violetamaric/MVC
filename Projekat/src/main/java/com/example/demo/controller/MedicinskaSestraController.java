package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.AdministratorKlinikeController.PasswordChanger;
import com.example.demo.dto.AdministratorKlinikeDTO;
import com.example.demo.dto.MedicinskaSestraDTO;
import com.example.demo.dto.OdmorOdsustvoMSDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.ReceptDTO;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.model.Klinika;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.OdmorOdsustvoMedicinskaSestra;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.Recept;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.MedicinskaSestraService;
import com.example.demo.service.ReceptService;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value="/api/medicinskaSestra", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicinskaSestraController {
	@Autowired
	private MedicinskaSestraService medicinskaSestraService;
	
	@Autowired
	private ReceptService receptService;
	
	@Autowired
	private KlinikaService klinikaService;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	
	//vrati sve medicinske sestre
	@GetMapping(value = "/sve")
	@PreAuthorize("hasAuthority('MED_SESTRA')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<MedicinskaSestraDTO>> getAll() {

		List<MedicinskaSestra> medSes = medicinskaSestraService.findAll();

		List<MedicinskaSestraDTO> medSesDTO = new ArrayList<>();
		for (MedicinskaSestra ms : medSes) {
			medSesDTO.add(new MedicinskaSestraDTO(ms) );
		}

		return new ResponseEntity<>(medSesDTO, HttpStatus.OK);
	}

	//vrati odredjenu med sestru
	@GetMapping(value = "/medicinskaSestra")
	@PreAuthorize("hasAuthority('MED_SESTRA')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<MedicinskaSestraDTO> getMedicinskaSestraByEmail(Principal p){
		
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(p.getName());
		if (ms == null) {
			System.out.println("NIJE PRONADJENA");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("PRONADJENA: "+ ms.getEmail());
		
		return new ResponseEntity<>(new MedicinskaSestraDTO(ms), HttpStatus.OK);
	}
	
	@GetMapping(value = "/medSestra/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<MedicinskaSestraDTO> getMedicinskaSestraById(@PathVariable Long id){
		
		MedicinskaSestra ms = medicinskaSestraService.findById(id);
		if (ms == null) {
			System.out.println("NIJE PRONADJENA");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("PRONADJENA: "+ ms.getEmail());
		
		return new ResponseEntity<>(new MedicinskaSestraDTO(ms), HttpStatus.OK);
	}

	//vrati listu pacijenata
	@GetMapping(value = "/listaPacijenata")
	@PreAuthorize("hasAuthority('MED_SESTRA')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<PacijentDTO>> getListaPacijenata(Principal pr) {
		System.out.println("//////////////////// MED SESTRA LISTA PACIJENATA ////////////////////////");
		
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(pr.getName());
		
		List<Pacijent> listaPacijenataKlinike = klinikaService.findByIdKlinike(ms.getKlinika().getId());
		
		//List<Pacijent> listaSvihP =  pacijenti.findAll();
		System.out.println("Lista pacijenata od MED SESTRE: " + ms.getEmail());
		List<PacijentDTO> lista = new ArrayList<>();
			
		for (Pacijent p : listaPacijenataKlinike ) {
			
				System.out.println(p);
				
				if(p.getOdobrenaRegistracija() == 2 ) {
					PacijentDTO pDTO = new PacijentDTO(p);
					System.out.println("Pacijent dodat");
					lista.add(pDTO);
				}
						
		}
		
		System.out.println("*************");
//		for(PacijentDTO pd  : lista) {
//			System.out.println(pd);
//		}
		System.out.println("*************");
		return new ResponseEntity<>(lista, HttpStatus.OK);

		
	}
	
	//izmeni medicinsku sestru
	@PutMapping(path="/izmena", consumes = "application/json")
	@PreAuthorize("hasAuthority('MED_SESTRA')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<MedicinskaSestraDTO> izmeniMedicinskuSestru(@RequestBody MedicinskaSestraDTO msDTO) {

		// a student must exist
		System.out.println("MED SESTRA IZMENA");
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(msDTO.getEmail());


		if(msDTO.getIme() != null && msDTO.getIme() != "") {
			System.out.println("izmenjeno ime admina");
			ms.setIme(msDTO.getIme());
			
		}
		if(msDTO.getPrezime()!= null && msDTO.getPrezime() != "") {
			System.out.println("izmenjeno prezime admina");
			ms.setPrezime(msDTO.getPrezime());
			
		}
		if(msDTO.getLozinka()!= null && msDTO.getLozinka() != "") {
			System.out.println("izmenjena lozinka admina");
			ms.setLozinka(msDTO.getLozinka());
			
		}
		if(msDTO.getBrTelefona()!= null && msDTO.getBrTelefona() != "") {
			System.out.println("izmenjena lozinka admina");
			ms.setBrTelefona(msDTO.getBrTelefona());
			
		}
		
		ms = medicinskaSestraService.save(ms);
		return new ResponseEntity<>(new MedicinskaSestraDTO(ms), HttpStatus.OK);
	}

	
	
	//vraca listu odmora i odsustva
	@GetMapping(value = "/listaOdmor")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('MED_SESTRA')")
	public ResponseEntity<List<OdmorOdsustvoMSDTO>> getOdmor(Principal p) {

		System.out.println("ODMOR ");
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(p.getName());
		
		if (ms == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<OdmorOdsustvoMSDTO> omsDTO = new ArrayList<>();
		for(OdmorOdsustvoMedicinskaSestra oms : ms.getListaOdmorOdsustvo()) {
			System.out.println("Jedan zahtev: " + oms.getTip()+ " " + oms.getStatus() );
			if(oms.getStatus() == 1) {
				System.out.println("Jedan zahtev: " + oms.getTip()+ " " + oms.getStatus() );
				System.out.println(oms.getDatumOd() + " " + oms.getDatumDo());
				omsDTO.add(new OdmorOdsustvoMSDTO(oms));
			}
			
			
		}
		
		return new ResponseEntity<>(omsDTO, HttpStatus.OK);
	}
	
	
	//vraca listu recepata
	@GetMapping(value = "/listaRecepata")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('MED_SESTRA')")
	public ResponseEntity<List<ReceptDTO>> getlistaRecepata(Principal p) {
		System.out.println("//////////////////// MED SESTRA LISTA Recepata ////////////////////////");
		
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(p.getName());

		List<ReceptDTO> lista = new ArrayList<ReceptDTO>();
		
		Klinika klinika = ms.getKlinika();
		Set<Pregled> preglediKlinike = klinika.getListaPregleda();
		
		for(Pregled pregled: preglediKlinike) {
			IzvestajOPregledu iop = pregled.getIzvestajOPregledu();
			if(iop != null) {
				Set<Recept> recepti = iop.getListaRecepata();
				for(Recept rec : recepti) {
					if(rec.isOveren() == false) {
						System.out.println("RECEPT : "+ rec.getLek().getNaziv());
						lista.add(new ReceptDTO(rec));
					}
					
				}
			}
			
		}
		

		System.out.println("//////////////////// MED SESTRA LISTA Recepata ////////////////////////");
		return new ResponseEntity<>(lista, HttpStatus.OK);

		
	}
	
	
	//overa recepta
	@PutMapping(path = "/overa", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('MED_SESTRA')")
	public ResponseEntity<?> overaRecepta(@RequestBody Long rec) {
		System.out.println("OVERA RECEPTA");
		Recept recept = receptService.findByID(rec);
		recept.setOveren(true);
		recept = receptService.save(recept);
		System.out.println("OVERA RECEPTA");
		return new ResponseEntity<>("overen", HttpStatus.OK);
	}

	//promena lozinke
	@PutMapping(path = "/promeniLozinku", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('MED_SESTRA')")
	public ResponseEntity<?> promeniLozinku(@RequestBody PasswordChanger passCh, Principal pr) {

		// a student must exist
		System.out.println("Pacijent UPDRATE LOZINKA");
		MedicinskaSestra adminKC = medicinskaSestraService.findByEmail(pr.getName());
		
		
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

			return new ResponseEntity<>(new MedicinskaSestraDTO(adminKC), HttpStatus.OK);
		}

		adminKC.setLozinka(passwordEncoder.encode(passCh.newPassword));
//		pacijent.setLbo(pacijentDTO.getLbo());
		adminKC.setStatus(1);
		adminKC = medicinskaSestraService.save(adminKC);
		return new ResponseEntity<>(new MedicinskaSestraDTO(adminKC), HttpStatus.OK);
	}
	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}

}
