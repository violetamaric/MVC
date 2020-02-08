package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.transaction.annotation.Propagation;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
import com.example.demo.dto.OdmorOdsustvoLDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.SalaDTO;
import com.example.demo.dto.TerminDTO;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.OdmorOdsustvoLekar;
import com.example.demo.model.Operacija;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.Termin;
import com.example.demo.model.TipPregleda;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.PacijentService;
import com.example.demo.service.PregledService;
import com.example.demo.service.SalaService;
import com.example.demo.service.TerminService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/lekari", produces = MediaType.APPLICATION_JSON_VALUE)
//@Transactional(readOnly = true)
public class LekarController {

	@Autowired
	private LekarService lekarService;

	@Autowired
	private PacijentService pacijentiSevice;

	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private KlinikaService klinikaService;
	
	@Autowired
	private SalaService salaService;

	@Autowired
	private TerminService terminService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping(value = "/{id}")
	public ResponseEntity<LekarDTO> getLekar(@PathVariable Long id) {

		Lekar lekar = lekarService.findOne(id);

		// studen must exist
		if (lekar == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
	}

	@GetMapping(value = "/getLekarByEmail")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE') or hasAuthority('LEKAR')")
	public ResponseEntity<?> findByEmail(Principal p) {

		Lekar lekar = lekarService.findByEmail(p.getName());
		if (lekar == null) {
			System.out.println("Lekar nije pronadjen");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("Lekar je pronadjen : " + lekar);

		return ResponseEntity.ok(new LekarDTO(lekar));
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<LekarDTO>> getAll() {

		List<Lekar> lekari = lekarService.findAll();

		// convert students to DTOs
		List<LekarDTO> lekarDTO = new ArrayList<>();
		for (Lekar l : lekari) {
			lekarDTO.add(new LekarDTO(l));
		}

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}
	//lekari koji se mogu mijenjati i brisati
	@GetMapping(value = "/allIBlekari/{idKlinike}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<LekarDTO>> getAllBIlekari(@PathVariable Long idKlinike) {

//		List<Sala> ret = new ArrayList<Sala>();
		Klinika klinika = klinikaService.findById(idKlinike);
		List<Lekar> lekari = lekarService.findAll();
//		List<Pregled> pregledi = pregledService.findAll();
		// convert students to DTOs
		System.out.println("****************************************************************");
		Date datumDanasnji = new Date();
		List<LekarDTO> lekariDTO = new ArrayList<>();

		for (Lekar p : lekari) {
			if (p.getKlinika().getId() == klinika.getId()) {

				for (Operacija o: p.getListaOperacija()) {
					if (o.getDatum().before(datumDanasnji)) {
						for(Pregled pp : p.getListaPregleda()) {
							if (pp.getDatum().before(datumDanasnji)) {
								System.out.println("Datum: " + o.getDatum());
								if (o.getListaLekara().contains(p)) {
									if (!lekariDTO.contains(p)) {
//												ret.add(p);
										System.out.println(p);
										lekariDTO.add(new LekarDTO(p));
									}
								}
							}else {
								continue;
							}
						}
						

					} else {
						continue;
					}

				}
				if (p.getListaOperacija().size() == 0 && p.getListaPregleda().size() == 0) {
					System.out.println(p);
					lekariDTO.add(new LekarDTO(p));
				}

			}

		}

		System.out.println("****************************************************************");
		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}

	@PutMapping(path = "/update", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR') or hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<LekarDTO> updateLekar(@RequestBody LekarDTO lekarDTO) {

		// a student must exist
		System.out.println("LEKAR UPDRATE");
		Lekar lekar = lekarService.findByEmail(lekarDTO.getEmail());
//		System.out.println("Lekar update: " + lekar.getEmail());
//		if (lekar == null) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}

		lekar.setIme(lekarDTO.getIme());
		lekar.setPrezime(lekarDTO.getPrezime());
		lekar.setTelefon(lekarDTO.getTelefon());

		lekar = lekarService.save(lekar);
		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
	}

	// vrati mi listu svih paicjenata od prijavljenog lekara
	@GetMapping(value = "/listaPacijenataLekara")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<List<PacijentDTO>> getPacijenataLekara(Principal pa) {
		System.out.println("//////////////////// LEKAR LISTA PACIJENATA /////////////////////////		");
		Lekar lekar = lekarService.findByEmail(pa.getName());

		List<Pacijent> listaSvihP = pacijentiSevice.findAll();
		for (Pacijent pp : listaSvihP) {
			System.out.println("!!!! " + pp);
		}
		System.out.println("Lista pacijenata od lekara: " + lekar.getEmail());

		List<PacijentDTO> lista = new ArrayList<>();
		for (Pacijent p : listaSvihP) {
			if (p.getOdobrenaRegistracija() == 2) {

				System.out.println(p);
				PacijentDTO pDTO = new PacijentDTO(p);

				System.out.println("Pacijent dodat");
				lista.add(pDTO);
			}

		}
		System.out.println("*************");
		for (PacijentDTO pd : lista) {
			System.out.println(pd);
		}
		System.out.println("*************");
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	// vrati mi listu zakazanih pregleda od pacijenta kod tog lekara koji je
	// prijavljen
	@GetMapping(value = "/listaPregledaPacijenta")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<List<PregledDTO>> getListaPregledaPacijenta(Principal pa,
			@RequestBody PacijentDTO pacijentDTO) {

		Lekar lekar = lekarService.findByEmail(pa.getName());
		Pacijent pacijent = pacijentiSevice.findByEmail(pacijentDTO.getEmail());

		Set<Pregled> listaPregleda = pacijent.getListaPregleda();
		List<PregledDTO> lista = new ArrayList<>();

		for (Pregled pp : listaPregleda) {
			if (pp.getLekar().getId().equals(lekar.getId())) {
				System.out.println("ima zakazan pregled kod lekara ovog");
				System.out.println("Status pregleda " + pp.getStatus());
				lista.add(new PregledDTO(pp));
			}
			// System.out.println("Status pregleda " + pp.getStatus());
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	// vraca listu pregleda jednog lekara
	@GetMapping(value = "/listaPregleda/{email}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<PregledDTO>> getListaPregleda(@PathVariable String email) {
		System.out.println("*************");
		Lekar lekar = lekarService.findByEmail(email);

		Set<Pregled> listaRD = lekar.getListaPregleda();

		List<PregledDTO> lista = new ArrayList<PregledDTO>();
		for (Pregled rd : listaRD) {
			System.out.println(rd.getDatum());
			System.out.println(rd.getTrajanje());
			lista.add(new PregledDTO(rd));
		}

		System.out.println("*************");
		return new ResponseEntity<>(lista, HttpStatus.OK);

	}

	// vraca listu zauzetih termina lekara
	@GetMapping(value = "/listaZauzetihTermina/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<TerminDTO>> getListaZauzetihTermina(@PathVariable Long id) {
		System.out.println("*************");

		Lekar lekar = lekarService.findOne(id);
		System.out.println(lekar.getIme());

		Set<Termin> listaTermina = lekar.getListaZauzetihTermina();
		System.out.println(listaTermina.size());

		List<TerminDTO> listaTerminaDTO = new ArrayList<TerminDTO>();
		for (Termin rd : listaTermina) {
			listaTerminaDTO.add(new TerminDTO(rd));
		}

		System.out.println("*************");
		return new ResponseEntity<>(listaTerminaDTO, HttpStatus.OK);

	}

	@GetMapping(value = "/listaZauzetostiLekara/{id}/{datum}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<TerminDTO>> getListaZauzetostiLekara(@PathVariable Long id, @PathVariable Date datum) {
		System.out.println("*************");

		Lekar lekar = lekarService.findOne(id);
		System.out.println(lekar.getIme());
		System.out.println(datum);
		datum.setHours(0);
		datum.setMinutes(0);
		datum.setSeconds(0);

		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(datum);
		date = c.getTime();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);

		System.out.println(date);
		Date date2 = new Date();
		c = Calendar.getInstance();
		c.setTime(datum);
		c.add(Calendar.DATE, 1);
		date2 = c.getTime();
		System.out.println(date2);
		date2.setHours(0);
		date2.setMinutes(0);
		date2.setSeconds(0);
		System.out.println();
		System.out.println(date);
		System.out.println(date2);
		System.out.println();
		List<Termin> listaTermina = terminService.zauzetiTerminiLekara(id, date, date2);
		System.out.println(listaTermina.size());

		List<TerminDTO> listaTerminaDTO = new ArrayList<TerminDTO>();
		for (Termin rd : listaTermina) {
			System.out.println(new TerminDTO(rd));
			listaTerminaDTO.add(new TerminDTO(rd));
		}
		Set<OdmorOdsustvoLekar> listaool = lekar.getListaOdmorOdsustvo();
		int flag = 0;
		for (OdmorOdsustvoLekar ool : listaool) {
			if (ool.getDatumOd().compareTo(datum) * datum.compareTo(ool.getDatumDo()) >= 0) {
				flag = 1;
				break;
			}

		}
		if (flag == 1) {
			TerminDTO t = new TerminDTO();
			t.setDatumPocetka(datum);
			t.setTermin(9);
			listaTerminaDTO.add(t);

			t = new TerminDTO();
			t.setDatumPocetka(datum);
			t.setTermin(11);
			listaTerminaDTO.add(t);

			t = new TerminDTO();
			t.setDatumPocetka(datum);
			t.setTermin(13);
			listaTerminaDTO.add(t);

			t = new TerminDTO();
			t.setDatumPocetka(datum);
			t.setTermin(15);
			listaTerminaDTO.add(t);
		}
		for (TerminDTO t : listaTerminaDTO) {
			System.out.println(t.getDatumPocetka());
			System.out.println(t.getTermin());
		}
		System.out.println("*************");
		return new ResponseEntity<>(listaTerminaDTO, HttpStatus.OK);

	}


	@PutMapping(path = "/oceni/{id}/{ocena}/{pregled_id}", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<LekarDTO> oceniLekara(@PathVariable Long id, @PathVariable int ocena,
			@PathVariable Long pregled_id) {

		Lekar lekar = lekarService.findById(id);
		int temp = lekar.getOcena();
		lekar.setOcena((temp + ocena) / 2);
		lekarService.save(lekar);
		Pregled pregled = pregledService.findById(pregled_id);
		if (pregled.getStatus() == 3) {
			pregled.setStatus(5);
			pregledService.save(pregled);
		} else if (pregled.getStatus() == 4) {
			pregled.setStatus(6);
			pregledService.save(pregled);
		}

		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
	}

	// vraca listu odmora i odsustva kod lekara
	@GetMapping(value = "/listaOdmorOdsustvo")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<List<OdmorOdsustvoLDTO>> getListaOdmorOdsustvo(Principal p) {

		System.out.println("ODMOR ");
		Lekar lekar = lekarService.findByEmail(p.getName());

		if (lekar == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<OdmorOdsustvoLDTO> oolDTO = new ArrayList<>();
		for (OdmorOdsustvoLekar ool : lekar.getListaOdmorOdsustvo()) {
			System.out.println("Jedan zahtev: " + ool.getTip() + " " + ool.getStatus());
			if (ool.getStatus() == 1) {
				System.out.println("Jedan zahtev: " + ool.getTip() + " " + ool.getStatus());
				System.out.println(ool.getDatumOd() + " " + ool.getDatumDo());
				oolDTO.add(new OdmorOdsustvoLDTO(ool));
			}

		}

		return new ResponseEntity<>(oolDTO, HttpStatus.OK);
	}

	// VRACA MOZE ILI NE MOZE U ZAVISNOSTI DA LI MU JE DOSTUPAN ZK PACIJENTA
	@PostMapping(value = "/mogucPrikazZKPacijenta")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> getMogucPrikazZKPacijenta(@RequestBody PacijentDTO pacijentDTO, Principal p) {
		System.out.println("*************");

		Lekar lekar = lekarService.findByEmail(p.getName());

		Pacijent pacijent = pacijentiSevice.findByID(pacijentDTO.getId());

		Set<Pacijent> listaPacijenta = lekar.getListaPacijenata();
		// dodeli pacijente lekaru

		for (Pacijent pac : listaPacijenta) {
			if (pac.getId().equals(pacijent.getId())) {
				return new ResponseEntity<>("MOZE", HttpStatus.OK);
			}
		}

		return new ResponseEntity<>("NE MOZE", HttpStatus.OK);

	}


	//menjanje lozinke
	
	@PutMapping(path = "/promeniLozinku", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> promeniLozinku(@RequestBody PasswordChanger passCh, Principal pr) {

		// a student must exist
		System.out.println("Pacijent UPDRATE LOZINKA");
		Lekar adminKC = lekarService.findByEmail(pr.getName());
		
		
		System.out.println("LOZINKA: "+ adminKC.getLozinka());
		
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();
		System.out.println(username);
		if (authenticationManager != null) {
			System.out.println("PROMENJENA LOZINKA");

			final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, passCh.oldPassword));

			System.err.println("-----");
			SecurityContextHolder.getContext().setAuthentication(authentication);
			System.out.println("-----");
		} else {
			System.out.println("NE MOZE SE PROMENITI LOZINKA");

			return new ResponseEntity<>(new LekarDTO(adminKC), HttpStatus.OK);
		}
		
		adminKC.setLozinka(passwordEncoder.encode(passCh.newPassword));
		adminKC.setStatus(1);
		adminKC = lekarService.save(adminKC);
		return new ResponseEntity<>(new LekarDTO(adminKC), HttpStatus.OK);
	}
	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}

}
