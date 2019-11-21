package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MedicinskaSestraDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Pacijent;
import com.example.demo.service.MedicinskaSestraService;

/*
 * http://localhost:8028/api/medicinskaSestra/sve
 * */

@RestController
@RequestMapping(value="/api/medicinskaSestra", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicinskaSestraController {
	@Autowired
	private MedicinskaSestraService medicinskaSestraService;
	
//	@GetMapping(value = "/sve")
//	public ResponseEntity<List<MedicinskaSestraDTO>> getAll() {
//
//		List<MedicinskaSestra> medSes = medicinskaSestraService.findAll();
//
//		List<MedicinskaSestraDTO> medSesDTO = new ArrayList<>();
//		for (MedicinskaSestra ms : medSes) {
//			medSesDTO.add(new MedicinskaSestraDTO(ms) );
//		}
//
//		return new ResponseEntity<>(medSesDTO, HttpStatus.OK);
//	}
//
//	@GetMapping(value = "/medicinskaSestra/{email}")
//	public ResponseEntity<MedicinskaSestraDTO> getMedicinskaSestraByEmail(@PathVariable String email){
//		
//		MedicinskaSestra ms = medicinskaSestraService.findByEmail(email);
//		if (ms == null) {
//			System.out.println("NIJE PRONADJENA");
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		System.out.println("PRONADJENA: "+ ms.getEmail());
//		
//		return new ResponseEntity<>(new MedicinskaSestraDTO(ms), HttpStatus.OK);
//	}
//
//	@GetMapping(value = "/listaPacijenata/{medSesId}")
//	public ResponseEntity<List<PacijentDTO>> getListaPacijenata(@PathVariable Long medSesId) {
//
//		MedicinskaSestra ms = medicinskaSestraService.findById(medSesId);
//		
//		List<PacijentDTO> lista = new ArrayList<>();
//		
//		for (Pacijent p : ms.getListaPacijenata()) {
//			PacijentDTO pDTO = new PacijentDTO();
//			pDTO.setId(p.getId());
//			pDTO.setAdresa(p.getAdresa());
//			pDTO.setDrzava(p.getDrzava());
//			pDTO.setEmail(p.getEmail());
//			pDTO.setGrad(p.getGrad());
//			pDTO.setIme(p.getIme());
//			pDTO.setPrezime(p.getPrezime());
//			pDTO.setLbo(p.getLbo());
//			pDTO.setLozinka(p.getLozinka());
//			pDTO.setTelefon(p.getTelefon());
//			pDTO.setZdravstveniKarton(p.getZdravstveniKarton());
//			lista.add(pDTO);
//		}
//
//		return new ResponseEntity<>(lista, HttpStatus.OK);
//
//		
//	}
}
