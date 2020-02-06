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

import com.example.demo.dto.DijagnozaDTO;
import com.example.demo.model.AdministratorKC;
import com.example.demo.model.Dijagnoza;
import com.example.demo.model.KlinickiCentar;
import com.example.demo.service.AdministratorKCService;
import com.example.demo.service.DijagnozaService;
import com.example.demo.service.KlinickiCentarService;

@CrossOrigin
@RestController
@RequestMapping(value="/api/dijagnoze", produces = MediaType.APPLICATION_JSON_VALUE)
public class DijagnozaController {
	
	@Autowired
	private DijagnozaService dijagnozaService;
	
	@Autowired
	private KlinickiCentarService KCService;
	
	@Autowired
	private AdministratorKCService administratorKCService;
	
	//vrati mi listu dijagnoza u klinickom centru
	@GetMapping(value = "/listaDijagnoza")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC') or hasAuthority('LEKAR')")
	public ResponseEntity<List<DijagnozaDTO>> getListaDijagnoza() {

//		AdministratorKC administratorKC = administratorKCService.findByEmail(p.getName());	
		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar klinickiCentar = listaKC.get(0);
		
		List<DijagnozaDTO> lista = new ArrayList<>();
			
		for (Dijagnoza k : klinickiCentar.getListaDijagnoza()) {
			DijagnozaDTO kcDTO = new DijagnozaDTO(k);
			lista.add(kcDTO);
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);	
	}
	
	

	
	//dodavanje nove dijagnoze
	@PostMapping(path = "/dodavanjeDijagnoze", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<DijagnozaDTO> dodavanjeDijagnoze(@RequestBody DijagnozaDTO dijagnozaDTO) {
		System.out.println("------------------------------------------------------");
		Dijagnoza dijagnoza = new Dijagnoza();
		
		dijagnoza.setNaziv(dijagnozaDTO.getNaziv());
		dijagnoza.setOpis(dijagnozaDTO.getOpis());
		
		dijagnoza.setOznaka(dijagnozaDTO.getOznaka());
		
		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);
		
		dijagnoza.setKlinickiCentar(kc);
			
		dijagnoza = dijagnozaService.save(dijagnoza);		
		kc.getListaDijagnoza().add(dijagnoza);
		kc = KCService.save(kc);

		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>(new DijagnozaDTO(dijagnoza), HttpStatus.CREATED);
	}

	//brisanje dijagnoze
	@PostMapping(path = "/brisanjeDijagnoze", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<String> brisanjeDijagnoze(@RequestBody DijagnozaDTO dijagnozaDTO) {
		System.out.println("------------------------------------------------------");
		Dijagnoza dijagnoza = dijagnozaService.findById(dijagnozaDTO.getId());	
		
		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);
			
		if(kc.getListaDijagnoza().contains(dijagnoza)) {
			
			Set<Dijagnoza> lista = kc.getListaDijagnoza();
			lista.remove(dijagnoza);
			kc.getListaDijagnoza().clear();
			kc.setListaDijagnoza(lista);
			
			dijagnozaService.delete(dijagnoza);	
			
			kc = KCService.save(kc);
			System.out.println("obrisano");
		}

		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("uspesno brisanje dijagnoze", HttpStatus.OK);
	}
	
	//izmena dijagnoze
	@PutMapping(path = "/izmenaDijagnoze", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<DijagnozaDTO> izmenaDijagnoze(@RequestBody DijagnozaDTO dijagnozaDTO) {
		System.out.println("------------------------------------------------------");
		Dijagnoza dijagnoza = dijagnozaService.findById(dijagnozaDTO.getId());	
		
		if(dijagnozaDTO.getNaziv() != null && dijagnozaDTO.getNaziv() != "") {
			System.out.println("izmenjen naziv dijagnoze");
			dijagnoza.setNaziv(dijagnozaDTO.getNaziv());	
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		if(dijagnozaDTO.getOznaka() != null && dijagnozaDTO.getOznaka() != "") {
			System.out.println("izmenjena oznaka dijagnoze");
			//TODO 2: STAVITI ZABRANU DA NE MOZE ISTA SIFRA
			dijagnoza.setOznaka(dijagnozaDTO.getOznaka());	
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(dijagnozaDTO.getOpis() != null && dijagnozaDTO.getOpis() != "") {
			System.out.println("izmenjen opis dijagnoze");
			//TODO 2: STAVITI ZABRANU DA NE MOZE ISTA SIFRA
			dijagnoza.setOpis(dijagnozaDTO.getOpis());	
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		dijagnoza = dijagnozaService.save(dijagnoza);

		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>(new DijagnozaDTO(dijagnoza), HttpStatus.CREATED);
	}
	
	//vracanje dijagnoze
	@GetMapping(path = "/getDijagnoza/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<DijagnozaDTO> getDijagnoza(@PathVariable Long id) {
			System.out.println("------------------------------------------------------");

			Dijagnoza dijagnoza = dijagnozaService.findById(id);
			
			DijagnozaDTO dijagnozaDTO = new DijagnozaDTO(dijagnoza);
			
			System.out.println("------------------------------------------------------");
			return new ResponseEntity<>(dijagnozaDTO, HttpStatus.OK);
		}
	
	
}
