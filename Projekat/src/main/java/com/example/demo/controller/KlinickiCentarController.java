package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdministratorKlinikeDTO;
import com.example.demo.dto.KlinickiCentarDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.model.AdministratorKC;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.KlinickiCentar;
import com.example.demo.model.Klinika;
import com.example.demo.service.AdministratorKCService;
import com.example.demo.service.KlinickiCentarService;

@CrossOrigin
@RestController
@RequestMapping(value="/api/kc", produces = MediaType.APPLICATION_JSON_VALUE)
public class KlinickiCentarController {
	
	@Autowired
	private KlinickiCentarService klinickiCentarService;
	
	@Autowired
	private AdministratorKCService administratorKCService;
	
	
	//vrati mi podatke o klinickom centru
	@GetMapping(value = "/klinickiCentar")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<KlinickiCentarDTO> getKlinickiCentar() {
		
//		AdministratorKC administratorKC = administratorKCService.findByEmail(p.getName());
//		KlinickiCentar kc = administratorKC.getKlinickiCentar();
		
		List<KlinickiCentar> listaKC = klinickiCentarService.find();
		KlinickiCentar kc = listaKC.get(0);
		
		KlinickiCentarDTO kcDTO = new KlinickiCentarDTO(kc);
		 
		return new ResponseEntity<>(kcDTO, HttpStatus.OK);
	}
	
	//vrati mi listu klinika u klinickom centru
	@GetMapping(value = "/listaKlinika")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<List<KlinikaDTO>> getListaKlinika(Principal p) {

		AdministratorKC administratorKC = administratorKCService.findByEmail(p.getName());
		
		KlinickiCentar klinickiCentar = administratorKC.getKlinickiCentar();
		
		List<KlinikaDTO> lista = new ArrayList<>();
		
		for (Klinika k : klinickiCentar.getListaKlinika()) {
			KlinikaDTO kcDTO = new KlinikaDTO(k);
			lista.add(kcDTO);
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);

		
	}
	
	//vrati mi listu svih admina klinika u klinickom centru
	@GetMapping(value = "/listaAdministratoraKlinika")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KC')")
	public ResponseEntity<List<AdministratorKlinikeDTO>> getListaAdministratoraKlinika(Principal p) {

		AdministratorKC administratorKC = administratorKCService.findByEmail(p.getName());
		
		KlinickiCentar klinickiCentar = administratorKC.getKlinickiCentar();
		List<AdministratorKlinikeDTO> lista = new ArrayList<>();
		for (Klinika k : klinickiCentar.getListaKlinika() ) {
			for(AdministratorKlinike a : k.getListaAdminKlinike()) {
				AdministratorKlinikeDTO aDTO = new AdministratorKlinikeDTO(a);
				lista.add(aDTO);
			}
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	
}
