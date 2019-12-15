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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TipPregledaDTO;
import com.example.demo.model.TipPregleda;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.TipPregledaService;

@RestController
@RequestMapping(value = "/api/tipPregleda", produces = MediaType.APPLICATION_JSON_VALUE)

public class TipPregledaController {

	@Autowired
	private TipPregledaService TPService;
	@Autowired
	private KlinikaService klinikaService;

	@GetMapping(value = "/all")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<TipPregleda>> findAll() {

		List<TipPregleda> tp = TPService.findAll();
		List<TipPregleda> listaTP = new ArrayList<>();

		for (TipPregleda tipP : tp) 
			listaTP.add(new TipPregleda(tipP));
	return new ResponseEntity<>(listaTP, HttpStatus.OK);
		
	}

	@GetMapping(value = "/tipPregledaKlinike/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
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

}