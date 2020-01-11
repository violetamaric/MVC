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

import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.LekarDTO;

import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.TipPregledaDTO;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;
import com.example.demo.model.TipPregleda;

import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;

@RestController
@RequestMapping(value = "/api/klinike", produces = MediaType.APPLICATION_JSON_VALUE)
public class KlinikaController {
	@Autowired
	private KlinikaService klinikaService;
	@Autowired
	private LekarService lekarService;
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAuthority( 'ADMIN_KLINIKE') or hasAuthority('ADMIN_KC')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<KlinikaDTO> getKlinikaById(@PathVariable Long id) {

		Klinika k = klinikaService.findOne(id);
		System.out.println("Pretraga klinike po ID");
		// studen must exist
		if (k == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(k.getNaziv() + " " + k.getId());
		return new ResponseEntity<>(new KlinikaDTO(k), HttpStatus.OK);
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<KlinikaDTO>> getAll() {

		List<Klinika> klinike = klinikaService.findAll();

		// convert students to DTOs
		List<KlinikaDTO> klinikaDTO = new ArrayList<>();
		for (Klinika k : klinike) {
			klinikaDTO.add(new KlinikaDTO(k));
		}

		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findKlinikaByNaziv/{naziv}")
	public ResponseEntity<KlinikaDTO> getKlinikaByNaziv(@PathVariable String naziv) {
		System.out.println("find klinika by naziv");
		Klinika klinika = klinikaService.findByNaziv(naziv);
		if (klinika == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(klinika.getNaziv());
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
	}
	
	@GetMapping(value = "/findKlinikaByAdresa/{adresa}")
	public ResponseEntity<KlinikaDTO> getKlinikaByAdresa(@PathVariable String adresa) {
		System.out.println("find klinika by adresa");
		if(adresa.contains("%20"))
			adresa.replace("%20", " ");
	
		
		Klinika klinika = klinikaService.findByAdresa(adresa);
		
		if (klinika == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		System.out.println(klinika.getNaziv());
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
	}

	
	@PutMapping(path="/update", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority( 'ADMIN_KLINIKE') or hasAuthority('ADMIN_KC')")
	public ResponseEntity<KlinikaDTO> updateKliniku(@RequestBody KlinikaDTO klinikaDTO) {

		// a student must exist
		System.out.println(" KLINIKa UPDRATE");
		Klinika klinika = klinikaService.findById(klinikaDTO.getId());

//		System.out.println("Lekar update: " + lekar.getEmail());
//		if (lekar == null) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
		

		klinika.setNaziv(klinikaDTO.getNaziv());
		klinika.setAdresa(klinikaDTO.getAdresa());
		klinika.setOpis(klinikaDTO.getOpis());
		klinika.setOcena(klinikaDTO.getOcena());

		klinika = klinikaService.save(klinika);
		System.out.println("Izmjenjena k: " + klinika);
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
	}
	
	@GetMapping(value = "/listaLekaraKlinika/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<LekarDTO>> getKlinikaLekari(@PathVariable Long id) {
		System.out.println("//////////////////// KLINIKA LISTA LEKARA /////////////////////////		");
		Klinika klinika = klinikaService.findById(id);
//		List<Lekar> listaSvihLekara =  lekarService.findAll();

		List<LekarDTO> lista = new ArrayList<>();
	
		for (Lekar l : klinika.getListaLekara() ) {
			
//			if(klinika.getId() == l.getKlinika().getId()) {
				LekarDTO lDTO = new LekarDTO(l);
				lista.add(lDTO);
//			}
		}
//		for(Lekar ll : listaSvihLekara) {
//			if(!lista.contains(ll.getEmail())) {
//				System.out.println("Prazna listaaaaaaaaaaaaaaaaaaaaaaaa!!!!! ");
//				lista = null;
//			}
//		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	

	// brisanje lekara
	@PostMapping(path = "/brisanjeLekara", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<String> brisanjeLekara(@RequestBody LekarDTO lekarDTO) {
		System.out.println("------------------------------------------------------");
		System.out.println("pocinje");
		//lekar koji se brise
		Lekar lekar = lekarService.findByEmail(lekarDTO.getEmail());
		
		List<Klinika> listaKlinika = klinikaService.findAll();
		System.out.println("Id LEKAR KLINIKA: " + lekar.getKlinika().getId());

		Long idLong = lekar.getKlinika().getId();

		Klinika klinika = klinikaService.findById(idLong);
		System.out.println("Klinika id ------------- : " + klinika.getId());

		if (klinika.getListaLekara().contains(lekar)) {
			System.out.println("LEKAR =============== " + lekar);
			Set<Lekar> lista = klinika.getListaLekara();
			lista.remove(lekar);
			klinika.getListaLekara().clear();
			klinika.setListaLekara(lista);	
			
			lekarService.delete(lekar);

			klinika = klinikaService.save(klinika);
			System.out.println("obrisano");
		}
		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("uspesno obrisan lekar !!!", HttpStatus.OK);
	}
	
	@GetMapping(value = "/pacijentiKlinike/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<PacijentDTO>> getPacijentiKlinike(@PathVariable Long id) {
		System.out.println("//////////////////// Klinika i lista pacijenata /////////////////////////		");
//		Klinika klinika = klinikaService.findById(id);

		List<Pacijent> listaPacijenataKlinike = klinikaService.findByIdKlinike(id);
		System.out.println("***********");
		
		for (Pacijent kp :listaPacijenataKlinike) {
			System.out.println(kp);

		}
		List<PacijentDTO> lista = new ArrayList<PacijentDTO>();
		for(Pacijent pp :listaPacijenataKlinike) {
			PacijentDTO pD = new PacijentDTO(pp);
			lista.add(pD);
		}
		System.out.println("*************");

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	

//	// brisanje lekara
//	@PostMapping(path = "/brisanjeLekara", consumes = "application/json")
//	@CrossOrigin(origins = "http://localhost:3000")
//	public ResponseEntity<String> brisanjeLekara(@RequestBody LekarDTO lekarDTO) {
//		System.out.println("------------------------------------------------------");
//		System.out.println("pocinje");
//		//lekar koji se brise
//		Lekar lekar = lekarService.findByEmail(lekarDTO.getEmail());
//		
//		List<Klinika> listaKlinika = klinikaService.findAll();
//		System.out.println("Id LEKAR KLINIKA: " + lekar.getKlinika().getId());
//
//		Long idLong = lekar.getKlinika().getId();
//
//		Klinika klinika = klinikaService.findById(idLong);
//		System.out.println("Klinika id ------------- : " + klinika.getId());
//
//		if (klinika.getListaLekara().contains(lekar)) {
//			System.out.println("LEKAR =============== " + lekar);
//			Set<Lekar> lista = klinika.getListaLekara();
//			lista.remove(lekar);
//			klinika.getListaLekara().clear();
//			klinika.setListaLekara(lista);	
//			
//			lekarService.delete(lekar);
//
//			klinika = klinikaService.save(klinika);
//			System.out.println("obrisano");
//		}
//		System.out.println("------------------------------------------------------");
//		return new ResponseEntity<>("uspesno obrisan lekar !!!", HttpStatus.OK);
//	}

//	// brisanje lekara
//	@PostMapping(path = "/brisanjeLekara", consumes = "application/json")
//	@CrossOrigin(origins = "http://localhost:3000")
//	public ResponseEntity<String> brisanjeLekara(@RequestBody LekarDTO lekarDTO) {
//		System.out.println("------------------------------------------------------");
//		System.out.println("pocinje");
//		//lekar koji se brise
//		Lekar lekar = lekarService.findByEmail(lekarDTO.getEmail());
//		
//		List<Klinika> listaKlinika = klinikaService.findAll();
//		System.out.println("Id LEKAR KLINIKA: " + lekar.getKlinika().getId());
//
//		Long idLong = lekar.getKlinika().getId();
//
//		Klinika klinika = klinikaService.findById(idLong);
//		System.out.println("Klinika id ------------- : " + klinika.getId());
//
//		if (klinika.getListaLekara().contains(lekar)) {
//			System.out.println("LEKAR =============== " + lekar);
//			Set<Lekar> lista = klinika.getListaLekara();
//			lista.remove(lekar);
//			klinika.getListaLekara().clear();
//			klinika.setListaLekara(lista);	
//			
//			lekarService.delete(lekar);
//
//			klinika = klinikaService.save(klinika);
//			System.out.println("obrisano");
//		}
//		System.out.println("------------------------------------------------------");
//		return new ResponseEntity<>("uspesno obrisan lekar !!!", HttpStatus.OK);
//	}

}
