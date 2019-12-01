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

import com.example.demo.dto.AdministratorKlinikeDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;
import com.example.demo.service.LekarService;


@RestController
@RequestMapping(value = "/api/lekari", produces = MediaType.APPLICATION_JSON_VALUE)
public class LekarController {

	@Autowired
	private LekarService lekarService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<LekarDTO> getLekar(@PathVariable Long id) {

		Lekar lekar = lekarService.findOne(id);

		// studen must exist
		if (lekar == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getLekarByEmail/{email}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<LekarDTO> findByEmail(@PathVariable String email){
		
		Lekar lekar = lekarService.findByEmail(email);
		if (lekar == null) {
			System.out.println("Lekar nije pronadjen");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("Lekar je pronadjen : "+ lekar.getEmail());
		
		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
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
	
	@PutMapping(path="/update", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
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
	
	//vrati mi listu svih paicjenata od prijavljenog lekara
	@GetMapping(value = "/listaPacijenataLekara/{email}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<PacijentDTO>> getPacijenataLekara(@PathVariable String email) {
		System.out.println("//////////////////// LEKAR LISTA PACIJENATA /////////////////////////		");
		Lekar lekar = lekarService.findByEmail(email);
		System.out.println("Lista pacijenata od lekara: " + lekar.getEmail());
		
		List<PacijentDTO> lista = new ArrayList<>();
		for (Pacijent p : lekar.getListaPacijenata() ) {
			
				System.out.println(p);
				PacijentDTO pDTO = new PacijentDTO();
				pDTO.setId(p.getId());
				pDTO.setIme(p.getIme());
				pDTO.setPrezime(p.getPrezime());
				pDTO.setEmail(p.getEmail());
				System.out.println("Pacijent dodat");
				lista.add(pDTO);
				
		}
		System.out.println("*************");
		for(PacijentDTO pd  : lista) {
			System.out.println(pd);
		}
		System.out.println("*************");
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
}
