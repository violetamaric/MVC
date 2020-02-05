package com.example.demo.controller;

import java.io.Console;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.SlobodniTerminDTO;
import com.example.demo.dto.TipPregledaDTO;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.SlobodniTermin;
import com.example.demo.model.TipPregleda;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.SlobodniTerminService;
import com.example.demo.service.TipPregledaService;

@RestController
@RequestMapping(value = "/api/tipPregleda", produces = MediaType.APPLICATION_JSON_VALUE)

public class TipPregledaController {

	@Autowired
	private TipPregledaService TPService;
	@Autowired
	private KlinikaService klinikaService;
	@Autowired
	private SlobodniTerminService STService;
	
	@GetMapping(value = "/finByIdTP/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize(" hasAuthority('ADMIN_KLINIKE')" )
	public ResponseEntity<TipPregledaDTO> findById(@PathVariable Long id) {

		TipPregleda tp = TPService.findOne(id);
		System.out.println("ID TP---------------");
		// studen must exist
		if (tp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new TipPregledaDTO(tp), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{naziv}")
	@PreAuthorize(" hasAuthority('ADMIN_KLINIKE')" )
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<TipPregledaDTO> getTPByNaziv(@PathVariable String naziv) {

		TipPregleda tp = TPService.findByNaziv(naziv);

		// studen must exist
		if (tp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new TipPregledaDTO(tp), HttpStatus.OK);
	}
	
	@GetMapping(value = "/all")
	@PreAuthorize("hasAuthority('PACIJENT') or hasAuthority('ADMIN_KLINIKE')" )
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<TipPregledaDTO>> findAll() {

		List<TipPregleda> tp = TPService.findAll();
		List<TipPregledaDTO> listaTP = new ArrayList<>();

		for (TipPregleda tipP : tp) {
			listaTP.add(new TipPregledaDTO(tipP));
		}

		return new ResponseEntity<>(listaTP, HttpStatus.OK);

	}

	@GetMapping(value = "/tipPregledaKlinike/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<TipPregledaDTO>> gettipPregledaKlinike(@PathVariable Long id) {
		System.out.println("//////////////////// LEKAR LISTA TP KLINIKE /////////////////////////		");
//		Klinika klinika = klinikaService.findById(id);

		List<TipPregleda> listaTP = TPService.findByIdKlinike(id);

		List<TipPregledaDTO> tp = new ArrayList<>();
		for (TipPregleda p : listaTP) {
			TipPregledaDTO pDTO = new TipPregledaDTO(p);
			tp.add(pDTO);

		}
		System.out.println("*************");
		for (TipPregledaDTO pd : tp) {
			System.out.println(pd);
		}
		System.out.println("*************");
		return new ResponseEntity<>(tp, HttpStatus.OK);
	}

	// brisanje tp 
	@PostMapping(path = "/brisanjeTP", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<String> brisanjeTP(@RequestBody TipPregledaDTO tpDTO) {
		System.out.println("------------------------------------------------------");
		System.out.println("pocinje");
		// tp koji se brise
		TipPregleda tp = TPService.findByNaziv(tpDTO.getNaziv());
		
		List<TipPregleda> listaTP = TPService.findAll();


		Long idLong = tp.getId();

//		Klinika klinika = klinikaService.findById(idLong);
//		System.out.println("Klinika id ------------- : " + klinika.getId());

		if (listaTP.contains(tp)) {
			List<SlobodniTermin> listaST = STService.findAll();
			for(SlobodniTermin s: listaST) {
				if(s.getTipPregleda().getId().equals(tp.getId())) {
					listaST.remove(s);
					STService.delete(s);
				}
			}
			System.out.println("TP =============== " + tp.getNaziv());
//			Set<Lekar> lista = klinika.getListaLekara();
			listaTP.remove(tp);
//			klinika.getListaLekara().clear();
//			klinika.setListaLekara(lista);

			TPService.delete(tp);

//			tp = TPService.save(tp);
			System.out.println("obrisano");
		}
		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("uspesno obrisan TP !!!", HttpStatus.OK);
	}

	
	@PostMapping(path="/dodajNoviTP", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<TipPregledaDTO> noviTP(@RequestBody TipPregledaDTO tpDTO) {
		System.out.println("dodavanje novog tp");
		System.out.println(tpDTO);
		TipPregleda tp = new TipPregleda();
		tp.setNaziv(tpDTO.getNaziv());
	
		tp = TPService.save(tp);
		

		return new ResponseEntity<>(new TipPregledaDTO(tp), HttpStatus.OK);
	}
	
}

