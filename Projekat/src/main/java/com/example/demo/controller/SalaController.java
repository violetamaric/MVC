package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.SalaDTO;
import com.example.demo.dto.TerminDTO;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.OdmorOdsustvoLekar;
import com.example.demo.model.Pregled;
import com.example.demo.model.Sala;
import com.example.demo.model.Termin;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.PregledService;
import com.example.demo.service.SalaService;
import com.example.demo.service.TerminService;

@RestController
@RequestMapping(value = "/api/sale", produces = MediaType.APPLICATION_JSON_VALUE)

public class SalaController {
	@Autowired
	private SalaService salaService;

	@Autowired
	private TerminService terminService;

	@Autowired
	private KlinikaService klinikaService;
	@Autowired
	private PregledService pregledService;
	@Autowired
	private LekarService lekarService;

	@GetMapping(value = "/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<SalaDTO> findById(@PathVariable Long id) {

		Sala sala = salaService.findOne(id);

		// studen must exist
		if (sala == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new SalaDTO(sala), HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> getAll() {

		List<Sala> sale = salaService.findAll();

		// convert students to DTOs
		List<SalaDTO> salaDTO = new ArrayList<>();
		for (Sala p : sale) {
			salaDTO.add(new SalaDTO(p));
		}

		return new ResponseEntity<>(salaDTO, HttpStatus.OK);
	}

	@GetMapping(value = "preuzmiSaleKlinike/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> getSaleKlinike(@PathVariable Long id) {

		Klinika klinika = klinikaService.findOne(id);
		List<Sala> sale = salaService.findAll();
		List<SalaDTO> lista = new ArrayList<SalaDTO>();
		for (Sala s : sale) {
			if (s.getKlinika().getId() == klinika.getId()) {
				SalaDTO salaDTO = new SalaDTO(s);
				lista.add(salaDTO);
			}
		}

		System.out.println("Lista sala u klinici:" + klinika.getNaziv() + " ID: " + id);
		for (SalaDTO ss : lista) {
			System.out.println(ss.getNaziv() + ss.getBroj());
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	// brisanje sale
	@PostMapping(path = "/brisanjeSale", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<String> brisanjeSale(@RequestBody SalaDTO salaDTO) {
		System.out.println("------------------------------------------------------");
		System.out.println("pocinje");
		System.out.println(salaDTO);
//		Long  id = salaDTO.getId();
		Sala sala = salaService.findById(salaDTO.getId());
		System.out.println(sala);
		Long idK = (Long) salaDTO.getKlinikaID();
		Klinika klinika = klinikaService.findById(idK);
		System.out.println("OK");

		if (klinika.getListaSala().contains(sala)) {

			Set<Sala> lista = klinika.getListaSala();
			System.out.println("--prije---");
			for (Sala s : lista) {
				System.out.println(s.getNaziv() + s.getBroj());
			}
			System.out.println("-----------");
			lista.remove(sala);

			klinika.getListaSala().clear();
			klinika.setListaSala(lista);
			salaService.delete(sala);
			System.out.println("--psole---");
			for (Sala s : lista) {
				System.out.println(s.getNaziv() + s.getBroj());
			}
			System.out.println("-----------");
			klinika = klinikaService.save(klinika);
			System.out.println("obrisano");
		}

		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("uspesno obrisana sala", HttpStatus.OK);
	}

	@PostMapping(path = "/dodajSalu", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<SalaDTO> novaSala(@RequestBody SalaDTO sDTO) {
		System.out.println("dodavanje nove sale");
		System.out.println(sDTO);
		Sala s = new Sala();
		s.setNaziv(sDTO.getNaziv());
		s.setBroj(sDTO.getBroj());
		Klinika k = klinikaService.findById(sDTO.getKlinikaID());
		s.setKlinika(k);

		s = salaService.save(s);

		return new ResponseEntity<>(new SalaDTO(s), HttpStatus.OK);
	}

	// izmena sale
	@PutMapping(path = "/izmenaSale", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<SalaDTO> izmenaSale(@RequestBody SalaDTO salaDTO) {
		System.out.println("------------------------------------------------------");
		Sala sala = salaService.findById(salaDTO.getId());

		Klinika klinika = klinikaService.findById(salaDTO.getKlinikaID());

		if (salaDTO.getNaziv() != null && salaDTO.getNaziv() != "") {
			System.out.println("izmenjen naziv sale");
			sala.setNaziv(salaDTO.getNaziv());
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		sala.setBroj(salaDTO.getBroj());

		sala.setId(salaDTO.getId());
		sala.setKlinika(klinika);
		sala = salaService.save(sala);

		System.out.println("------------------------------------------------------");

		return new ResponseEntity<>(new SalaDTO(sala), HttpStatus.CREATED);
	}

	@GetMapping(value = "/allTermini")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<TerminDTO>> getAllTermini() {

		List<Termin> termini = terminService.findAll();

		// convert students to DTOs
		List<TerminDTO> terminDTO = new ArrayList<>();
		for (Termin t : termini) {
			if (t.getSala() != null) {
				terminDTO.add(new TerminDTO(t));
			}

		}

		return new ResponseEntity<>(terminDTO, HttpStatus.OK);
	}

	//pronalazak sala slobodnih za taj teremin i datum
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
				// SalaDTO salaDTO = new SalaDTO(s);
				listaSalaKlinike.add(s);
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

//							System.out.println();
//							System.out.println("********************bbbbb " + sala.getId() +" " + sala.getBroj() + " " + termin.getTermin());
							
						}else if(termin.getDatumPocetka().equals(pregled.getDatum())){
							sala.getZauzetiTermini().add(termin);
							TerminDTO terDTO = new TerminDTO(termin);

							sdt.getZauzetiTermini().add(terDTO);
							sdt.setNaziv(sala.getNaziv());
							sdt.setBroj(sala.getBroj());
							sdt.setId(sala.getId());
							sdt.setKlinikaID(sala.getKlinika().getId());
//
//							System.out.println();
//							System.out.println("********************bbbbb " + sala.getId() + sala.getBroj() + " " + termin.getTermin());
						}
					}
					
					
				}
//				System.out.println();
				salaService.save(sala);
			

				
				listaSalaSlob.add(sdt);
			}
			
			
		}
		return new ResponseEntity<>(listaSalaSlob, HttpStatus.OK);
	}

	// rezervisanje sale
	@PostMapping(path = "/rezervisanjeSale", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<String> rezervisanjeSale(@RequestBody PregledDTO pDTO) {
		System.out.println();
		System.out.println("......... Rezervisanje sale ..... ");
		System.out.println(pDTO);
		System.out.println();
		List<Pregled> listaPregleda = pregledService.findAll();

		for (Pregled p : listaPregleda) {
			if (p.getId().equals(pDTO.getId())) {
				p.setStatus(1);
				Sala s = salaService.findById(pDTO.getSalaID());
				p.setSala(s);
				Lekar l = lekarService.findById(pDTO.getLekarID());
				p.setLekar(l);
				p.setTermin(pDTO.getTermin());
				p.setDatum(pDTO.getDatum());
				System.out.println(pregledService.findAll().size());
				pregledService.save(p);
				System.out.println(pregledService.findAll().size());
				Termin t = new Termin();
				t.setTermin(pDTO.getTermin());
				int idT = terminService.findAll().size() + 1;
				long idTermina = (long) idT;
				t.setId(idTermina);
				t.setDatumPocetka(pDTO.getDatum());
				t.setSala(s);
				t.setLekar(l);
				System.out.println(" TERMINI: " + terminService.findAll().size());
				terminService.save(t);
				System.out.println();
				
				System.out.println(t);
				
				System.out.println();

				System.out.println(" TERMINI222: " + terminService.findAll().size());
			}
		}

		System.out.println("REZERVISANOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

		return new ResponseEntity<>("uspesno rezervisana sala1", HttpStatus.OK);
	}

	@PostMapping(path = "/pronadjiLekaraZaPregled", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<LekarDTO>> slobodanLekar(@RequestBody PregledDTO pDTO) {
		
		List<LekarDTO> listaSlobodnihLekara = new ArrayList<LekarDTO>();
		Klinika klinika = klinikaService.findById(pDTO.getKlinikaID());
		
		System.out.println(pDTO);
		List<Lekar> listaSvihLekaraKlinike = new ArrayList<Lekar>();
		List<Lekar> listaSvihLekara = lekarService.findAll();
		for(Lekar l: listaSvihLekara ) {
			if(l.getKlinika().equals(klinika)){
				listaSvihLekaraKlinike.add(l);
			}
			
		}
		System.out.println();
		System.out.println(" SVI LEKARI KLINIKE *************** " + listaSvihLekaraKlinike.size());
		System.out.println(listaSvihLekaraKlinike.size());
		
		
		
		//Lekar lekar = lekarService.findByEmail(ooDTO.getEmailL());
		for(Lekar lekar: listaSvihLekaraKlinike) {
			System.out.println("-----> "  + lekar.getId() + " " + lekar.getIme() + " " + lekar.getPrezime());
			boolean flag = false;
//			Set<Termin> pregledi = lekar.getListaZauzetihTermina();
			for(Pregled t : pregledService.findAll()) {
				if(t.getStatus()==1) {
					if(t.getLekar().getId()==lekar.getId()) {
						System.out.println("LEKAR ISTI");
						if(t.getTermin()==pDTO.getTermin()) {
							System.out.println("TERMIN ISTI");
							if(t.getDatum().compareTo(pDTO.getDatum())==0) {
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
			
			for(OdmorOdsustvoLekar ool: listaool) {
				if(ool.getStatus() ==  1) {
					if(ool.getDatumOd().compareTo(pDTO.getDatum()) * pDTO.getDatum().compareTo(ool.getDatumDo()) >= 0) {
						System.out.println();
						System.out.println("Lekar na odmoru");
						System.out.println(lekar.getId() + " " + lekar.getIme() + " " + lekar.getPrezime());
						System.out.println();
						flag = true;
						break;
					}
					
			
				}
			}
			
			if(!flag) {
				LekarDTO l = new LekarDTO(lekar);
				listaSlobodnihLekara.add(l);
			}
			
		}
		
		for(LekarDTO l: listaSlobodnihLekara) {
			System.out.println(l);
		}
		
		System.out.println("*************** " + listaSlobodnihLekara.size());
		
		return new ResponseEntity<>(listaSlobodnihLekara, HttpStatus.OK);
	}
	
}
