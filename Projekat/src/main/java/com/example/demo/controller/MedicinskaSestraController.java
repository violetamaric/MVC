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

import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.MedicinskaSestraDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.Lekar;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Pacijent;
import com.example.demo.service.MedicinskaSestraService;
import com.example.demo.service.PacijentService;



@RestController
@RequestMapping(value="/api/medicinskaSestra", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicinskaSestraController {
	@Autowired
	private MedicinskaSestraService medicinskaSestraService;
	
	@Autowired
	private PacijentService pacijenti;
	
	@GetMapping(value = "/sve")
	public ResponseEntity<List<MedicinskaSestraDTO>> getAll() {

		List<MedicinskaSestra> medSes = medicinskaSestraService.findAll();

		List<MedicinskaSestraDTO> medSesDTO = new ArrayList<>();
		for (MedicinskaSestra ms : medSes) {
			medSesDTO.add(new MedicinskaSestraDTO(ms) );
		}

		return new ResponseEntity<>(medSesDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/medicinskaSestra/{email}")
	public ResponseEntity<MedicinskaSestraDTO> getMedicinskaSestraByEmail(@PathVariable String email){
		
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(email);
		if (ms == null) {
			System.out.println("NIJE PRONADJENA");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("PRONADJENA: "+ ms.getEmail());
		
		return new ResponseEntity<>(new MedicinskaSestraDTO(ms), HttpStatus.OK);
	}

	
	@GetMapping(value = "/listaPacijenata/{email}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<PacijentDTO>> getListaPacijenata(@PathVariable String email) {
		System.out.println("//////////////////// MED SESTRA LISTA PACIJENATA ////////////////////////");
		
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(email);
		
		List<Pacijent> listaSvihP =  pacijenti.findAll();
		System.out.println("Lista pacijenata od MED SESTRE: " + ms.getEmail());
		List<PacijentDTO> lista = new ArrayList<>();
			
		for (Pacijent p : listaSvihP ) {
			//DODAJ MAGDALENA 
				System.out.println(p);
				if(p.getOdobrenaRegistracija() == true) {
					PacijentDTO pDTO = new PacijentDTO(p);
					System.out.println("Pacijent dodat");
					lista.add(pDTO);
				}
						
		}
		
		System.out.println("*************");
		for(PacijentDTO pd  : lista) {
			System.out.println(pd);
		}
		System.out.println("*************");
		return new ResponseEntity<>(lista, HttpStatus.OK);

		
	}
	
	
	
	@PutMapping(path="/izmena", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<MedicinskaSestraDTO> izmeniMedicinskuSestru(@RequestBody MedicinskaSestraDTO msDTO) {

		// a student must exist
		System.out.println("MED SESTRA IZMENA");
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(msDTO.getEmail());

//		System.out.println("Lekar update: " + lekar.getEmail());
//		if (lekar == null) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
		if(msDTO.getIme() != null && msDTO.getIme() != "") {
			System.out.println("izmenjeno ime admina");
			ms.setIme(msDTO.getIme());
			
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		if(msDTO.getPrezime()!= null && msDTO.getPrezime() != "") {
			System.out.println("izmenjeno prezime admina");
			ms.setPrezime(msDTO.getPrezime());
			
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(msDTO.getLozinka()!= null && msDTO.getLozinka() != "") {
			System.out.println("izmenjena lozinka admina");
			ms.setLozinka(msDTO.getLozinka());
			
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		if(msDTO.getBrTelefona()!= null && msDTO.getBrTelefona() != "") {
			System.out.println("izmenjena lozinka admina");
			ms.setBrTelefona(msDTO.getBrTelefona());
			
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		
		ms = medicinskaSestraService.save(ms);
		return new ResponseEntity<>(new MedicinskaSestraDTO(ms), HttpStatus.OK);
	}
}
