package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;
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
	
		List<Lekar> listaSvihLekara =  lekarService.findAll();
	//	System.out.println("Lista pacijenata od lekara: " + lekar.getEmail());
		
		List<LekarDTO> lista = new ArrayList<>();
	
		for (Lekar l : listaSvihLekara ) {
			
			if(klinika.getId() == l.getKlinika().getId()) {
				//System.out.println(l.getKlinika().getId() + l.getIme());
				LekarDTO lDTO = new LekarDTO();
				lDTO.setId(l.getId());
				lDTO.setIme(l.getIme());
				lDTO.setPrezime(l.getPrezime());
				lDTO.setEmail(l.getEmail());
				lDTO.setTelefon(l.getTelefon());
				lista.add(lDTO);
			}	
//				System.out.println(p);
//				PacijentDTO pDTO = new PacijentDTO();
//				pDTO.setId(p.getId());
//				pDTO.setIme(p.getIme());
//				pDTO.setPrezime(p.getPrezime());
//				pDTO.setEmail(p.getEmail());
//				System.out.println("Pacijent dodat");
//				lista.add(pDTO);
//				
		}
//		System.out.println("*************");
//		for(PacijentDTO pd  : lista) {
//			System.out.println(pd);
//		}
//		System.out.println("*************");
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
}
