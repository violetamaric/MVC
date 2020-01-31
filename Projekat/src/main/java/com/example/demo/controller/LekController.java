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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LekDTO;
import com.example.demo.model.AdministratorKC;
import com.example.demo.model.KlinickiCentar;
import com.example.demo.model.Lek;
import com.example.demo.service.AdministratorKCService;
import com.example.demo.service.KlinickiCentarService;
import com.example.demo.service.LekService;

@CrossOrigin
@RestController
@RequestMapping(value="/api/lekovi", produces = MediaType.APPLICATION_JSON_VALUE)
public class LekController {
	@Autowired
	private LekService lekService;
	
	@Autowired
	private KlinickiCentarService KCService;
	
	@Autowired
	private AdministratorKCService administratorKCService;
	
	//vrati mi listu lekova u klinickom centru
	@GetMapping(value = "/listaLekova")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC') or hasAuthority('LEKAR')")
	public ResponseEntity<List<LekDTO>> getListaLekova() {

//		AdministratorKC administratorKC = administratorKCService.findByEmail(p.getName());	
		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar klinickiCentar = listaKC.get(0);
		List<LekDTO> lista = new ArrayList<>();
		
		for (Lek k : klinickiCentar.getListaLekova()) {
			LekDTO kcDTO = new LekDTO(k);
			lista.add(kcDTO);
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	//dodavanje novog leka 
	@PostMapping(path = "/dodavanjeLeka", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<LekDTO> dodavanjeLeka(@RequestBody LekDTO lekDTO) {
		System.out.println("------------------------------------------------------");
		Lek lek = new Lek();
		lek.setNaziv(lekDTO.getNaziv());
		lek.setSifra(lekDTO.getSifra());
		
		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);
		
		lek.setKlinickiCentar(kc);
			
		lek = lekService.save(lek);		
		kc.getListaLekova().add(lek);
		kc = KCService.save(kc);

		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>(new LekDTO(lek), HttpStatus.CREATED);
	}

	
	//brisanje leka
	@PostMapping(path = "/brisanjeLeka", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<String> brisanjeLeka(@RequestBody LekDTO lekDTO) {
		System.out.println("------------------------------------------------------");
		System.out.println("pocinje");
		Lek lek = lekService.findById(lekDTO.getId());
		
		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);
			
		
		if(kc.getListaLekova().contains(lek)) {
			
			Set<Lek> lista = kc.getListaLekova();
			lista.remove(lek);
			kc.getListaLekova().clear();
			kc.setListaLekova(lista);
			
			lekService.delete(lek);	
			
			kc = KCService.save(kc);
			System.out.println("obrisano");
		}
		
		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("uspesno obrisan lek", HttpStatus.OK);
	}
	
	
	//izmena leka
	@PutMapping(path = "/izmenaLeka", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<LekDTO> izmenaLeka(@RequestBody LekDTO lekDTO) {
		System.out.println("------------------------------------------------------");
		Lek lek = lekService.findById(lekDTO.getId());
		
		if(lekDTO.getNaziv() != null && lekDTO.getNaziv() != "") {
			System.out.println("izmenjen naziv leka");
			lek.setNaziv(lekDTO.getNaziv());	
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		if(lekDTO.getSifra() != null && lekDTO.getSifra() != "") {
			System.out.println("izmenjena sifra leka");
			//TODO 2: STAVITI ZABRANU DA NE MOZE ISTA SIFRA
			lek.setSifra(lekDTO.getSifra());	
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		lek = lekService.save(lek);

		System.out.println("------------------------------------------------------");
					
		return new ResponseEntity<>(new LekDTO(lek), HttpStatus.CREATED);
	}
	
	
	//vracanje leka
	@GetMapping(path = "/getLek/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<LekDTO> getLek(@PathVariable Long id) {
			System.out.println("------------------------------------------------------");

			Lek lek = lekService.findById(id);
			
			LekDTO lekDTO = new LekDTO(lek);
			
			System.out.println("------------------------------------------------------");
			return new ResponseEntity<>(lekDTO, HttpStatus.OK);
		}

}


