package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.example.demo.dto.SalaDTO;
import com.example.demo.dto.TerminDTO;
import com.example.demo.model.Klinika;
import com.example.demo.model.Pregled;
import com.example.demo.model.Sala;
import com.example.demo.model.Termin;
import com.example.demo.service.EmailService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.PacijentService;
import com.example.demo.service.PregledService;
import com.example.demo.service.SalaService;
import com.example.demo.service.TerminService;

@RestController
@RequestMapping(value = "/api/sale", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class SalaController {
	@Autowired
	private SalaService salaService;

	@Autowired
	private EmailService emailService;
	@Autowired
	private TerminService terminService;
	@Autowired
	private PacijentService pacijentService;
	@Autowired
	private KlinikaService klinikaService;
	@Autowired
	private PregledService pregledService;
	@Autowired
	private LekarService lekarService;

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping(value = "/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<SalaDTO> findById(@PathVariable Long id) {

		Sala sala = salaService.findOne(id);

		// studen must exist
		if (sala == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new SalaDTO(sala), HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> getAll() {

		List<Sala> sale = salaService.findAll();

		// convert students to DTOs
		List<SalaDTO> salaDTO = new ArrayList<>();
		for (Sala p : sale) {
			salaDTO.add(new SalaDTO(p));
		}

		return new ResponseEntity<>(salaDTO, HttpStatus.OK);
	}
	@GetMapping(value = "/allIB/{idKlinike}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> getAllBI(@PathVariable Long idKlinike) {

//		List<Sala> ret = new ArrayList<Sala>();
		Klinika klinika = klinikaService.findById(idKlinike);
		List<Sala> sale = salaService.findAll();
		List<Pregled> pregledi = pregledService.findAll();
		// convert students to DTOs
		System.out.println("****************************************************************");
		Date datumDanasnji = new Date();
		List<SalaDTO> salaDTO = new ArrayList<>();
		
		
			for (Sala p : sale) {
				if(p.getKlinika().getId()==klinika.getId()) {
					
						for(Termin t: p.getZauzetiTermini()) {
							if(t.getDatumPocetka().before(datumDanasnji)) {
								System.out.println("Datum: " + t.getDatumPocetka()) ;
								if(p.equals(t.getSala())) {
									if(!salaDTO.contains(p)) {
//										ret.add(p);
										System.out.println(p);
										salaDTO.add(new SalaDTO(p));
									}
								}
								
							}else {
								continue;
							}
								
								
							
						}
						if(p.getZauzetiTermini().size()==0) {
							System.out.println(p);
							salaDTO.add(new SalaDTO(p));
						}
						
					}
					
				
				
			}
		
		
		System.out.println("****************************************************************");
		return new ResponseEntity<>(salaDTO, HttpStatus.OK);
	}

	@GetMapping(value = "preuzmiSaleKlinike/{id}")
//	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> getSaleKlinike(@PathVariable Long id) {

		Klinika klinika = klinikaService.findOne(id);
		List<Sala> sale = salaService.findAll();
		List<SalaDTO> lista = new ArrayList<SalaDTO>();
		for (Sala s : sale) {
			if (s.getKlinika().getId() == klinika.getId()) {
				SalaDTO salaDTO = new SalaDTO(s);
				lista.add(salaDTO);
			}
		}

		System.out.println("Lista sala u klinici:" + klinika.getNaziv() + " ID: " + id);
		for (SalaDTO ss : lista) {
			System.out.println(ss.getNaziv() + ss.getBroj());
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	// brisanje sale
	@PostMapping(path = "/brisanjeSale", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<String> brisanjeSale(@RequestBody SalaDTO salaDTO) {
		System.out.println("------------------------------------------------------");
		System.out.println("pocinje");
		System.out.println(salaDTO);
//		Long  id = salaDTO.getId();
		Sala sala = salaService.findById(salaDTO.getId());
		System.out.println(sala);
		Long idK = (Long) salaDTO.getKlinikaID();
		Klinika klinika = klinikaService.findById(idK);
		System.out.println("OK");

		if (klinika.getListaSala().contains(sala)) {

			Set<Sala> lista = klinika.getListaSala();
			System.out.println("--prije---");
			for (Sala s : lista) {
				System.out.println(s.getNaziv() + s.getBroj());
			}
			System.out.println("-----------");
			lista.remove(sala);

			klinika.getListaSala().clear();
			klinika.setListaSala(lista);
			salaService.delete(sala);
			System.out.println("--psole---");
			for (Sala s : lista) {
				System.out.println(s.getNaziv() + s.getBroj());
			}
			System.out.println("-----------");
			klinika = klinikaService.save(klinika);
			System.out.println("obrisano");
		}

		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("uspesno obrisana sala", HttpStatus.OK);
	}

	@PostMapping(path = "/dodajSalu", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<SalaDTO> novaSala(@RequestBody SalaDTO sDTO) {
		System.out.println("dodavanje nove sale");
		System.out.println(sDTO);
		Sala s = new Sala();
		s.setNaziv(sDTO.getNaziv());
		s.setBroj(sDTO.getBroj());
		s.setTipSale(sDTO.getTipSale());
		Klinika k = klinikaService.findById(sDTO.getKlinikaID());
		s.setKlinika(k);
		System.out.println("SALE BROJ: " + salaService.findAll().size());
		s = salaService.save(s);
		System.out.println("SALE BROJ: " + salaService.findAll().size());
		return new ResponseEntity<>(new SalaDTO(s), HttpStatus.OK);
	}

	// izmena sale
	@PutMapping(path = "/izmenaSale", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<SalaDTO> izmenaSale(@RequestBody SalaDTO salaDTO) {
		System.out.println("------------------------------------------------------");
		Sala sala = salaService.findById(salaDTO.getId());
		
		Klinika klinika = klinikaService.findById(salaDTO.getKlinikaID());

		if (salaDTO.getNaziv() != null && salaDTO.getNaziv() != "") {
			System.out.println("izmenjen naziv sale");
			sala.setNaziv(salaDTO.getNaziv());
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		sala.setBroj(salaDTO.getBroj());
		sala.setTipSale(salaDTO.getTipSale());
		sala.setId(salaDTO.getId());
		sala.setKlinika(klinika);
		sala = salaService.save(sala);

		System.out.println("------------------------------------------------------");

		return new ResponseEntity<>(new SalaDTO(sala), HttpStatus.CREATED);
	}

	@GetMapping(value = "/allTermini")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<TerminDTO>> getAllTermini() {

		List<Termin> termini = terminService.findAll();

		// convert students to DTOs
		List<TerminDTO> terminDTO = new ArrayList<>();
		for (Termin t : termini) {
			if (t.getSala() != null) {
				terminDTO.add(new TerminDTO(t));
			}

		}

		return new ResponseEntity<>(terminDTO, HttpStatus.OK);
	}


	@GetMapping(value = "/getSaleZaBrisanjeIzm")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> getSaleBI1() {

		List<Sala> sale = salaService.findAll();

		// convert students to DTOs
		List<SalaDTO> salaDTO = new ArrayList<>();
		for (Sala p : sale) {
//			for(Pregled p)
			salaDTO.add(new SalaDTO(p));
		}

		return new ResponseEntity<>(salaDTO, HttpStatus.OK);
	}

//	@Scheduled(cron = "59 59 23 * * ?")
//    public void automaticSchedule() {
//        System.out.println("Automatska fja");
//        List<MedicalExaminationRequest> allRequests = medicalExaminationService.getAllExamsRequests();
//
//        for (MedicalExaminationRequest r : allRequests) {
//            List<MedicalExaminationRoom> availableRooms = medicalExaminationRoomService.getAvailableRooms(r.getClinic().getId(), r.getDate());
//            try {
//                medicalExaminationService.saveExamination(r.getDate(), r.getPrice(), r.getDuration(), r.getDiscount(), availableRooms.get(0).getId(),
//                        r.getClinic().getId(), r.getDoctor().getId(), r.getPatient().getId(), r.getType().getId(), r.getId(), false);
//
//            } catch (IndexOutOfBoundsException ioobe) {
//                List<MedicalExaminationRoom> availableRooms2;
//                int addDays = 1;
//                Date newDate;
//                do {
//                    Calendar c = Calendar.getInstance();
//                    c.setTime(r.getDate());
//                    c.add(Calendar.DATE, addDays);
//                    newDate = c.getTime();
//                    availableRooms2 = medicalExaminationRoomService.getAvailableRooms(r.getClinic().getId(), newDate);
//                    addDays++;
//                } while (availableRooms2.size() == 0);
//                medicalExaminationService.saveExamination(newDate, r.getPrice(), r.getDuration(), r.getDiscount(), availableRooms2.get(0).getId(),
//                        r.getClinic().getId(), r.getDoctor().getId(), r.getPatient().getId(), r.getType().getId(), r.getId(), false);
//
//            }
//        }
//    }
}
