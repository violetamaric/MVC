package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.IzvestajOPregleduDTO;
import com.example.demo.dto.ReceptDTO;
import com.example.demo.model.Dijagnoza;
import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lek;
import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.Recept;
import com.example.demo.model.Sala;
import com.example.demo.model.Termin;
import com.example.demo.model.TipPregleda;
import com.example.demo.model.ZdravstveniKarton;
import com.example.demo.service.DijagnozaService;
import com.example.demo.service.IzvestajOPregleduService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekService;
import com.example.demo.service.LekarService;
import com.example.demo.service.PacijentService;
import com.example.demo.service.PregledService;
import com.example.demo.service.ReceptService;
import com.example.demo.service.SalaService;
import com.example.demo.service.TipPregledaService;
import com.example.demo.service.ZdravstveniKartonService;

@RestController
@RequestMapping(value = "/api/izvestajOP", produces = MediaType.APPLICATION_JSON_VALUE)
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
	@Autowired
	private ReceptService receptService;
	@Autowired
	private TipPregledaService TPService;
	@Autowired
	private SalaService salaService;
	@Autowired
	private LekarService lekarService;
	@Autowired
	private KlinikaService klinikaService;
	@Autowired
	private ZdravstveniKartonService zdravstveniKartonService;
	
	
	@PostMapping(path = "/zavrsetakPregleda", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<String> zavrsetakPregleda(@RequestBody IzvestajOPregleduDTO izDTO) {
		System.out.println("------------------------------------------------------");
		
		IzvestajOPregledu iz = new IzvestajOPregledu();
		
		//PREGLED
		Pregled pregled = pregledService.findById(izDTO.getPregledID());
		pregled.setStatus(3); //zavrsen pregled
		
		
		//posto kod liste pacijenata postoji taj pregled stavi da je isto zavrsen 
		Pacijent pacijent = pregled.getPacijent();
		if(pacijent.getListaPregleda().contains(pregled)) {
			for(Pregled p : pacijent.getListaPregleda()) {
				if(p.equals(pregled)) {
					p.setStatus(3);
					pacijent = pacijentService.save(pacijent);
				}
			}
		}
	
		//u tipu pregleda
		TipPregleda tp = pregled.getTipPregleda();
		if(tp.getListaPregleda().contains(pregled)) {
			for(Pregled p : tp.getListaPregleda()) {
				if(p.equals(pregled)) {
					p.setStatus(3);
					tp = TPService.save(tp);
				}
			}
		}
		//kod sala
		Sala sala = pregled.getSala();
		if(sala.getListaPregleda().contains(pregled)) {
			for(Pregled p : sala.getListaPregleda()) {
				if(p.equals(pregled)) {
					p.setStatus(3);
					sala = salaService.save(sala);
				}
			}
		}
		//kod lekara
		Lekar lekar = pregled.getLekar();
		if(lekar.getListaPregleda().contains(pregled)) {
			for(Pregled p : lekar.getListaPregleda()) {
				if(p.equals(pregled)) {
					p.setStatus(3);
					lekar = lekarService.save(lekar);
				}
			}
		}

		
		Klinika klinika = pregled.getKlinika();
		if(klinika.getListaPregleda().contains(pregled)) {
			for(Pregled p : klinika.getListaPregleda()) {
				if(p.equals(pregled)) {
					p.setStatus(3);
					klinika = klinikaService.save(klinika);
				}
			}
		}
		//dodali smo pregled izvestaju i izvestaj pregledu
		iz.setPregled(pregled);
		pregled.setIzvestajOPregledu(iz);
		pregled = pregledService.save(pregled);

		//ZDRAVSTVENI KARTON
		ZdravstveniKarton zk = pacijent.getZdravstveniKarton();
		iz.setZdravstveniKarton(zk);
		zk.getListaIzvestajaOPregledu().add(iz);
		zk = zdravstveniKartonService.save(zk);
		
		//DIJAGNOZA
		Dijagnoza dijagnoza = dijagnozaService.findById(izDTO.getDijagnozaID());
		iz.setDijagnoza(dijagnoza);
		dijagnoza.getListaIzvestajaOPregledu().add(iz);
		dijagnoza = dijagnozaService.save(dijagnoza);
		
		//RECEPTI
		List<ReceptDTO> recepti = izDTO.getRecepti();
		for(ReceptDTO rdto : recepti) {
			Recept r = new Recept();
			r.setIzvestajOPregledu(iz);
			r.setOveren(false);
			
			Lek l = lekService.findById(rdto.getLekID());
			r.setLek(l);
			
			//medicinska sestra nije dodeljena
			
			r = receptService.save(r);
			iz.getListaRecepata().add(r);
		}
		
		
		//SADRZAJ 
		iz.setSadrzaj(izDTO.getSadrzaj());
		
		iz = izvestajService.save(iz);

		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("Uspesno", HttpStatus.CREATED);
	}


}
