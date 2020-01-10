package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.SlobodniTerminDTO;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.SlobodniTermin;
import com.example.demo.model.TipPregleda;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.SlobodniTerminService;
import com.example.demo.service.TipPregledaService;

@RestController
@RequestMapping(value = "/api/ST", produces = MediaType.APPLICATION_JSON_VALUE)
public class SlobodniTerminController {

	@Autowired
	private SlobodniTerminService STService;
	@Autowired 
	private KlinikaService klinikaService;
	@Autowired  
	private LekarService lekarSrvice;
	@Autowired
	private TipPregledaService tipPregledaService;
	
	@GetMapping(value = "/unapredDef")
	public ResponseEntity<List<SlobodniTerminDTO>> getAllUnapredDef() {

		List<SlobodniTermin> st = STService.findAll();

		// convert students to DTOs
		List<SlobodniTerminDTO> stDTO = new ArrayList<>();
		for (SlobodniTermin sstt : st) {
			stDTO.add(new SlobodniTerminDTO(sstt));
			System.out.println(new SlobodniTerminDTO(sstt));
			
			
		}

		return new ResponseEntity<>(stDTO, HttpStatus.OK);
	}
	

	@GetMapping(value = "preuzmiSTKlinike/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<SlobodniTerminDTO>> getPreglediKlinike(@PathVariable Long id) {

		Klinika klinika = klinikaService.findOne(id);
		List<SlobodniTermin> st = STService.findAll();
		List<SlobodniTerminDTO> lista = new ArrayList<SlobodniTerminDTO>();
		for(SlobodniTermin s : st) {
			if(s.getKlinika().getId()==klinika.getId()) {
				SlobodniTerminDTO sDTO = new SlobodniTerminDTO(s);
				lista.add(sDTO);
			}
		}
		
		System.out.println("Lista pregleda u klinici:" + klinika.getNaziv() + " ID: " + id);
		for(SlobodniTerminDTO ss: lista) {
			System.out.println(ss.getCena());
		}
		
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@PostMapping(path="/dodajNoviST", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<SlobodniTerminDTO> noviST(@RequestBody SlobodniTerminDTO stDTO) {
		System.out.println("dodavanje novog st");
		System.out.println(stDTO);
		SlobodniTermin st = new SlobodniTermin();
		st.setCena(stDTO.getCena());
		st.setPopust(stDTO.getPopust());
		st.setDatum(stDTO.getDatum());
		Klinika klinika = klinikaService.findById(stDTO.getKlinikaID());
		st.setKlinika(klinika);
		Lekar lekar = lekarSrvice.findById(stDTO.getLekarID());
		st.setLekar(lekar);

		st.setStatus(false);
		TipPregleda tp = tipPregledaService.findOne(stDTO.getTipPregledaID());
		st.setTipPregleda(tp);
		

		st = STService.save(st);
		

		return new ResponseEntity<>(new SlobodniTerminDTO(st), HttpStatus.OK);
	}
	
}
