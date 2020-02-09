package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.SalaDTO;
import com.example.demo.dto.SlobodniTerminDTO;
import com.example.demo.dto.TerminDTO;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.Sala;
import com.example.demo.model.SlobodniTermin;
import com.example.demo.model.Termin;
import com.example.demo.model.TipPregleda;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.PregledService;
import com.example.demo.service.SalaService;
import com.example.demo.service.SlobodniTerminService;
import com.example.demo.service.TerminService;
import com.example.demo.service.TipPregledaService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/ST", produces = MediaType.APPLICATION_JSON_VALUE)
public class SlobodniTerminController {

	@Autowired
	private SlobodniTerminService STService;
	@Autowired
	private KlinikaService klinikaService;
	@Autowired
	private LekarService lekarService;
	@Autowired
	private TipPregledaService tipPregledaService;
	@Autowired
	private PregledService pregledService;

	@Autowired
	private SalaService salaService;

	@Autowired
	private TerminService terminService;

	@GetMapping(value = "/unapredDef")
//	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<List<SlobodniTerminDTO>> getAllUnapredDef() {

		List<SlobodniTermin> st = STService.findAll();

		Date danasnjiDatum = new Date();
		List<SlobodniTerminDTO> stDTO = new ArrayList<>();
		for (SlobodniTermin sstt : st) {

			if (!sstt.isStatus() && sstt.getDatum().after(danasnjiDatum)) {
				SlobodniTerminDTO sd = new SlobodniTerminDTO(sstt);
				TipPregleda tp = tipPregledaService.findOne(sstt.getTipPregleda().getId());
				sd.setCenaTP(tp.getCena());
				stDTO.add(sd);

			}

		}

		return new ResponseEntity<>(stDTO, HttpStatus.OK);
	}

	@GetMapping(value = "preuzmiSTKlinike/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<SlobodniTerminDTO>> getPreglediKlinike(@PathVariable Long id) {

		Date danasnjiDatum = new Date();
		Klinika klinika = klinikaService.findOne(id);
		List<SlobodniTermin> st = STService.findAll();
		List<SlobodniTerminDTO> lista = new ArrayList<SlobodniTerminDTO>();
		for (SlobodniTermin s : st) {
			if (s.getKlinika().getId() == klinika.getId()) {
				if (!s.isStatus() && s.getDatum().after(danasnjiDatum)) {
					SlobodniTerminDTO sDTO = new SlobodniTerminDTO(s);
					TipPregleda tp = tipPregledaService.findOne(s.getTipPregleda().getId());
					sDTO.setCenaTP(tp.getCena());
					sDTO.setCena(tp.getCena());
					lista.add(sDTO);
				}
			}
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@PostMapping(path = "/dodajNoviST", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<SlobodniTerminDTO> noviST(@RequestBody SlobodniTerminDTO stDTO) {
		System.out.println("dodavanje novog st");
		System.out.println(stDTO);
		SlobodniTermin st = new SlobodniTermin();

		st.setPopust(stDTO.getPopust());
		st.setDatum(stDTO.getDatum());
		st.setTermin(stDTO.getTermin());
		st.setStatus(false);
		Klinika klinika = klinikaService.findById(stDTO.getKlinikaID());
		st.setKlinika(klinika);
		Lekar lekar = lekarService.findById(stDTO.getLekarID());
		st.setLekar(lekar);

		st.setStatus(false);
		TipPregleda tp = tipPregledaService.findOne(stDTO.getTipPregledaID());
		st.setTipPregleda(tp);
		SlobodniTerminDTO slobodniTerm = new SlobodniTerminDTO(st);
		slobodniTerm.setCenaTP(tp.getCena());
		slobodniTerm.setCena(tp.getCena());

		Sala sala = salaService.findOne(stDTO.getSalaID());
		st.setSala(sala);

		Termin t = new Termin();
		t.setDatumPocetka(stDTO.getDatum());
		t.setLekar(lekar);
		t.setSala(sala);
		t.setTermin(stDTO.getTermin());
		terminService.save(t);

		st = STService.save(st);

		Set<Termin> lzt = lekar.getListaZauzetihTermina();
		lzt.add(t);
		lekarService.save(lekar);
		Set<Termin> lzts = sala.getZauzetiTermini();
		lzts.add(t);
		salaService.save(sala);

		return new ResponseEntity<>(slobodniTerm, HttpStatus.OK);
	}

	@GetMapping(value = "preuzmiSaleKlinikeZaPregled/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> getSaleKlinikeZaPRegled(@PathVariable Long id) {

		Klinika klinika = klinikaService.findOne(id);
		List<Sala> sale = salaService.findAll();
		List<SalaDTO> lista = new ArrayList<SalaDTO>();
		for (Sala s : sale) {
			if (s.getKlinika().getId() == klinika.getId()) {
				if (s.getTipSale() == 1) {
					SalaDTO salaDTO = new SalaDTO(s);
					lista.add(salaDTO);
				}

			}
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	// pronalazak sala slobodnih za taj teremin i za taj datum-PREGLED
	@PostMapping(path = "/pronadjiSaleZaTajTerminST", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> getSaleTerminST(@RequestBody PregledDTO pregled) {

		
		System.out.println("8888 " + pregled + " ////////////////////");

		List<Sala> sale = salaService.findAll();
		List<Sala> listaSalaKlinike = new ArrayList<Sala>();
		for (Sala s : sale) {
			if (s.getKlinika().getId() == pregled.getKlinikaID()) {
				if (s.getTipSale() == 1) {
					listaSalaKlinike.add(s);
				}
			}
		}
//
		List<Sala> listaSalaTest = new ArrayList<Sala>();
		boolean flag = false;
		List<SalaDTO> listaSalaSlob = new ArrayList<SalaDTO>();
		List<Sala> listaZauzete = new ArrayList<Sala>();
//		for (Sala sD : listaSalaKlinike) {
//			System.out.println();
//			System.out.println(sD.getId());
//			flag = false;
//			SalaDTO sdt = new SalaDTO();
//			for (Termin t : terminService.findAll()) {
//
//				System.out.println(t);
//				if (t.getSala().getId() == sD.getId()) {
//					System.out.println(t.getSala().getId() + " " + t.getTermin() + " " + t.getDatumPocetka());
//					if (t.getDatumPocetka().equals(pregled.getDatum()) && t.getTermin().equals(pregled.getTermin())) {
//						System.out.println("Istiiii termin i datum i preskoci salu");
//						//sD.getZauzetiTermini().add(t);
//						listaZauzete.add(sD);
//
//						flag = true;
//						break;
//
//					} else {
//						continue;
//					}
//
//
//				}
//
//			}
//
//			if (flag) {
//				continue;
//			}
//
//			sdt.setNaziv(sD.getNaziv());
//			sdt.setBroj(sD.getBroj());
//			sdt.setId(sD.getId());
//			sdt.setKlinikaID(sD.getKlinika().getId());
//
//			System.out.println();
//	
//			if(!listaZauzete.contains(sdt))
//				listaSalaSlob.add(sdt);
////				
//
//		}
		//	System.out.println("SLOBODNE S: " + listaSalaSlob.size());

		
		for(Sala s:listaSalaKlinike) {
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			for(Termin t: terminService.findAll()) {
				if(t.getSala().getId()==s.getId()) {
					System.out.println(t);
					System.out.println(t.getDatumPocetka());
					System.out.println(pregled.getDatum());
					if( t.getDatumPocetka().equals(pregled.getDatum())) {
						System.out.println(t.getDatumPocetka());
						System.out.println(pregled.getDatum());
						System.out.println("Isti datummmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm**************************");
						if(t.getTermin()==pregled.getTermin()) {
							System.out.println("Isti termin -----------------------");
							if(listaZauzete.contains(s)) {
								continue;
							}else {
								listaZauzete.add(s);
							}
						}
					}
				}
			
			
			}
		}
		
		System.out.println("Lista zauzeteee: " + listaZauzete.size());
		
//		for(Sala ss: listaSalaKlinike) {
//			if(!listaZauzete.contains(ss)) {
//				listaSalaSlob.add(new SalaDTO(ss));
//			}
//		}
//	
		System.out.println("SLOBODNE S: " + listaSalaSlob.size());
		return new ResponseEntity<>(listaSalaSlob, HttpStatus.OK);
	}

}
