package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.example.demo.dto.TipPregledaDTO;
import com.example.demo.model.Klinika;
import com.example.demo.model.Pregled;
import com.example.demo.model.SlobodniTermin;
import com.example.demo.model.TipPregleda;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.PregledService;
import com.example.demo.service.SlobodniTerminService;
import com.example.demo.service.TipPregledaService;

@RestController
@RequestMapping(value = "/api/tipPregleda", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class TipPregledaController {

	@Autowired
	private TipPregledaService TPService;
	@Autowired
	private KlinikaService klinikaService;
	@Autowired
	private SlobodniTerminService STService;
	@Autowired
	private PregledService pregledService;
	
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
	
	@GetMapping(value = "/allKlinike/{idk}")
	@PreAuthorize("hasAuthority('PACIJENT') or hasAuthority('ADMIN_KLINIKE')" )
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<TipPregledaDTO>> findAllKlinike(@PathVariable Long idk) {

		List<TipPregleda> tp = TPService.findAll();
		List<TipPregledaDTO> listaTP = new ArrayList<>();

		for (TipPregleda tipP : tp) { 
			for(Klinika k: tipP.getListaKlinika()) {
				if(idk == k.getId()) {
					listaTP.add(new TipPregledaDTO(tipP));
				}
			}
			
		}

		return new ResponseEntity<>(listaTP, HttpStatus.OK);

	}
	
	@GetMapping(value = "/all")
	@PreAuthorize("hasAuthority('PACIJENT') or hasAuthority('ADMIN_KLINIKE')or hasAuthority('LEKAR')" )
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
		return new ResponseEntity<>(tp, HttpStatus.OK);
	}

	@GetMapping(value = "/allTerminiIB/{idKlinike}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<TipPregledaDTO>> getAllTerminiIB(@PathVariable Long idKlinike) {

//		List<Sala> ret = new ArrayList<Sala>();
		Klinika klinika = klinikaService.findById(idKlinike);
	
	//	List<Pregled> pregledi = pregledService.findAll();
		// convert students to DTOs
		System.out.println("****************************************************************");
		Date datumDanasnji = new Date();
		List<TipPregledaDTO> tpDTO = new ArrayList<>();
		
		
		List<Pregled> listaPRegleda = new ArrayList<Pregled>();
		//zakazani pregledi  na nekoj klinici
		for(Pregled p:klinika.getListaPregleda()) {
			if(p.getStatus()==1) {
				listaPRegleda.add(p);
			}
		}
		Date datumD = new Date();
		
		List<TipPregleda> listatp = TPService.findAll();
		//tipovi pregleda na toj klinici
		boolean flag = false;
	
		
//		if(klinika.getListaTipovaPregleda().size()==0) {
//			
//			System.out.println("AAAAAAAAAAAAAAAAAAAAA");
//
//		//	flag = true;
//			System.out.println(tt);
//			tpDTO.add(new TipPregledaDTO(tt));
//		}
//		
		for(TipPregleda tt :klinika.getListaTipovaPregleda()) {
			
				System.out.println("|| " +  tt.getId() + " " + tt.getNaziv());
				for(Pregled pp : listaPRegleda) {
					if(pp.getTipPregleda().getId()==(tt.getId())) {
					
						if(pp.getDatum().after(datumD)) {
							continue;
						}else{
							if(!tpDTO.contains(tt)) {
//								ret.add(p);
								System.out.println("AAAAAAAAAAAAAAAAAAAAA");
								System.out.println(pp.getDatum());
							//	flag = true;
								System.out.println(tt);
								tpDTO.add(new TipPregledaDTO(tt));
							}
						}
				
				
					}
				}
			
		
		}
		
			List<TipPregleda>lista = new ArrayList<TipPregleda>();
			for(Pregled pr : listaPRegleda) {
				TipPregleda tp = new TipPregleda(pr.getTipPregleda());
				if(!lista.contains(tp)) {
					lista.add(tp);
					System.out.println("---- " + tp.getNaziv());
				}
			}
		
		for(TipPregleda tip : klinika.getListaTipovaPregleda()) {	
			if(!lista.contains(tip)) {
				System.out.println(tip.getNaziv());
				tpDTO.add(new TipPregledaDTO(tip));
			}
		
		}
		System.out.println("****************************************************************");
		return new ResponseEntity<>(tpDTO, HttpStatus.OK);
	}
	
	// brisanje tp 
	@GetMapping(value = "/brisanjeTP/{idtp}/{idk}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<TipPregledaDTO>> brisanjeTP(@PathVariable Long idtp, @PathVariable Long idk) {
		System.out.println("------------------------------------------------------");
		System.out.println("pocinje");
		// tp koji se brise
		TipPregleda tp = TPService.findOne(idtp);
		Klinika klinika = klinikaService.findById(idk);
		List<TipPregledaDTO> listaTP = new ArrayList<TipPregledaDTO>();
		for(TipPregleda t:TPService.findAll()) {
			if(klinika.getListaTipovaPregleda().contains(t)) {
				if(t.getId()==tp.getId()) {
					continue;
				}else {
					for(SlobodniTermin st : STService.findAll()) {
						if(st.getKlinika().getId()==idk) {
							if(st.getTipPregleda().getId()==t.getId()) 
								STService.delete(st);
								continue;

						}
						
					}
					for(Pregled st : pregledService.findAll()) {
						if(st.getKlinika().getId()==idk) {
							if(st.getTipPregleda().getId()==t.getId()) 
								pregledService.delete(st);
								continue;

						}
						
					}
					TipPregledaDTO tD = new TipPregledaDTO(t);
					listaTP.add(tD);
					klinika.getListaTipovaPregleda().remove(t);
					TPService.delete(t);
					klinikaService.save(klinika);
				
				}
			}
		}

		

////		System.out.println("Klinika id ------------- : " + klinika.getId());
//		boolean flag = false;
//		if (listaTP.contains(tp)) {
//			List<SlobodniTermin> listaST = STService.findAll();
//			for(SlobodniTermin s: listaST) {
//				if(s.getTipPregleda().getId().equals(tp.getId())) {
//					flag = true;
//					s.setStatus(true);
//					break;
////					listaST.remove(s);
////					STService.delete(s);
//				}
//			}
//			System.out.println("TP =============== " + tp.getNaziv());
////			Set<Lekar> lista = klinika.getListaLekara();
////			listaTP.remove(tp);
////			klinika.getListaLekara().clear();
////			klinika.setListaLekara(lista);
//			if(!flag) {
//				TPService.delete(tp);
//			}
//			
//
////			tp = TPService.save(tp);
			System.out.println("obrisano");
//		}
		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>(listaTP, HttpStatus.OK);
	}

	
	@PostMapping(path="/dodajNoviTP/{idk}", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<TipPregledaDTO> noviTP(@RequestBody TipPregledaDTO tpDTO, @PathVariable Long idk) {
		System.out.println("dodavanje novog tp");
		System.out.println(tpDTO);
		TipPregleda tp = new TipPregleda();
		tp.setNaziv(tpDTO.getNaziv());
		tp.setCena(tpDTO.getCena());
		
		
		Klinika klinika = klinikaService.findById(idk);
		klinika.getListaTipovaPregleda().add(tp);
		tp.getListaKlinika().add(klinika);
		tp = TPService.save(tp);

		return new ResponseEntity<>(new TipPregledaDTO(tp), HttpStatus.OK);
	}
	
	@GetMapping(value = "/klinikeTP/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
//	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<?> klinikeTP(@PathVariable Long id) {

		List<Klinika> klinike = TPService.findKlinike(id);
		return new ResponseEntity<>(klinike, HttpStatus.OK);
	}
	
	
}

