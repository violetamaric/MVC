package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DijagnozaDTO;
import com.example.demo.dto.IzvestajOPregleduDTO;
import com.example.demo.model.Dijagnoza;
import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.model.KlinickiCentar;
import com.example.demo.model.Lek;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.ZdravstveniKarton;
import com.example.demo.service.DijagnozaService;
import com.example.demo.service.IzvestajOPregleduService;
import com.example.demo.service.LekService;
import com.example.demo.service.PacijentService;
import com.example.demo.service.PregledService;

@RestController
public class IzvestajOPregleduController {
	@Autowired
	private IzvestajOPregleduService izvestajService;
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private PacijentService pacijentService;
	@Autowired
	private DijagnozaService dijagnozaService;
	@Autowired
	private LekService lekService;
	
	
	@PostMapping(path = "/zavrsetakPregleda", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<String> zavrsetakPregleda(@RequestBody IzvestajOPregleduDTO izDTO) {
		System.out.println("------------------------------------------------------");
		
		IzvestajOPregledu iz = new IzvestajOPregledu();
		Pregled pregled = pregledService.findById(izDTO.getPregledID());
		Pacijent pacijent = pregled.getPacijent();
		ZdravstveniKarton zk = pacijent.getZdravstveniKarton();
		
		Dijagnoza dijagnoza = dijagnozaService.findById(izDTO.getDijagnozaID());
		//nisu sad lekovi vec recepti :(
		
		
//		List<KlinickiCentar> listaKC = KCService.find();
//		KlinickiCentar kc = listaKC.get(0);
//		
//		dijagnoza.setKlinickiCentar(kc);
//			
//		dijagnoza = dijagnozaService.save(dijagnoza);		
//		kc.getListaDijagnoza().add(dijagnoza);
//		kc = KCService.save(kc);

		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("Uspesno", HttpStatus.CREATED);
	}
}
