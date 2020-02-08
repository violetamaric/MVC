package com.example.demo.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.MedicinskaSestraDTO;
import com.example.demo.dto.OdmorOdsustvoLDTO;
import com.example.demo.dto.OdmorOdsustvoMSDTO;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.OdmorOdsustvoLekar;
import com.example.demo.model.OdmorOdsustvoMedicinskaSestra;
import com.example.demo.model.Operacija;
import com.example.demo.model.Termin;
import com.example.demo.model.TipOdmorOdsustvo;
import com.example.demo.service.EmailService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.MedicinskaSestraService;
import com.example.demo.service.OdmorOdsustvoLekarService;
import com.example.demo.service.OdmorOdsustvoMedicinskaSestraService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value="/api/odmorodsustvo", produces = MediaType.APPLICATION_JSON_VALUE)
public class OdmorOdsustvoController {
	@Autowired
	private MedicinskaSestraService medicinskaSestraService;
	@Autowired
	private LekarService lekarService; 
	
	@Autowired
	private KlinikaService klinikaService;
	@Autowired
	private EmailService emailService;
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired 
	private OdmorOdsustvoMedicinskaSestraService oomsService;
	
	@Autowired 
	private OdmorOdsustvoLekarService oolService;
	
	//vrati sve zahteve med sestre
	@GetMapping(value = "/listaZahtevaOdmorOdsustvoMS")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<OdmorOdsustvoMSDTO>> getZahteviMedSestre() {

		List<OdmorOdsustvoMedicinskaSestra> ooms = oomsService.findAll();

		List<OdmorOdsustvoMSDTO> oomsDTO = new ArrayList<>();
		for (OdmorOdsustvoMedicinskaSestra o : ooms) {
			if(o.getStatus() == 0) {
				oomsDTO.add(new OdmorOdsustvoMSDTO(o) );
			}
			
		}

		return new ResponseEntity<>(oomsDTO, HttpStatus.OK);
	}
	
	//vrati sve zahteve lekara 
	@GetMapping(value = "/listaZahtevaOdmorOdsustvoLekara")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<OdmorOdsustvoLDTO>> getZahteviLekara() {

		//List<OdmorOdsustvoLekar> ool = oolService.findAll();

		List<OdmorOdsustvoLDTO> oolDTO = new ArrayList<>();
		for (OdmorOdsustvoLekar o : oolService.findAll()) {
			if(o.getStatus() == 0) {
				oolDTO.add(new OdmorOdsustvoLDTO(o) );
			}
		}

		return new ResponseEntity<>(oolDTO, HttpStatus.OK);
	}

	//zahtev med sest
	@GetMapping(value = "/zahtev/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<OdmorOdsustvoMSDTO> getZahtevMedSestre(@PathVariable Long id) {

		OdmorOdsustvoMedicinskaSestra ooms = oomsService.findById(id);
		
		if(ooms != null) {
			return new ResponseEntity<>(new OdmorOdsustvoMSDTO(ooms), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	//posalji zahtev med sestra
	@PostMapping(path = "/posaljiZahtev", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('MED_SESTRA')")
	public ResponseEntity<?> slanjeZahteva(@RequestBody OdmorOdsustvoMSDTO ooDTO) {
		System.out.println("------------------------------------------------------");
		
	
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(ooDTO.getEmailMS());
		
		Set<OdmorOdsustvoMedicinskaSestra> listaool = ms.getListaOdmorOdsustvo();
		for(OdmorOdsustvoMedicinskaSestra ool: listaool) {
			if(ool.getStatus() ==  1) {
				if( ooDTO.getDatumOd().compareTo(ool.getDatumOd()) * ool.getDatumOd().compareTo(ooDTO.getDatumDo()) >= 0) {
					System.out.println("-------------nalazi se odsustvo i odmor-------------");
					return new ResponseEntity<>("Datum je zauzet.", HttpStatus.OK);
				}
				if( ooDTO.getDatumOd().compareTo(ool.getDatumDo()) * ool.getDatumDo().compareTo(ooDTO.getDatumDo()) >= 0) {
					System.out.println("-------------nalazi se odsustvo i odmor-------------");
					return new ResponseEntity<>("Datum je zauzet.", HttpStatus.OK);
				}
				
			}
		}
		
		//TODO 2 : I ZA OPERACIJE DODATI
		
//		if(ms != null) {
			Klinika k = klinikaService.findById(ms.getKlinika().getId());
			
//			if(k != null) {
				OdmorOdsustvoMedicinskaSestra ooms = new OdmorOdsustvoMedicinskaSestra();
				ooms.setDatumOd(ooDTO.getDatumOd());
				ooms.setDatumDo(ooDTO.getDatumDo());
				ooms.setOpis(ooDTO.getOpis());
				System.out.println(ooDTO.getTip());
				if(ooDTO.getTip().equals("ODMOR")) {
					System.out.println("ispis odmora");
					ooms.setTip(TipOdmorOdsustvo.ODMOR);
				}else {
					ooms.setTip(TipOdmorOdsustvo.ODSUSTVO);
				}
				
				ooms.setStatus(0);
				ooms.setMedicinskaSestra(ms);
				ooms.setKlinika(k);
				
				ooms = oomsService.save(ooms);
				
				k.getZahteviZaOdmorOdsustvoMedestre().add(ooms);
				k = klinikaService.save(k);
				
				ms.getListaOdmorOdsustvo().add(ooms);
				ms = medicinskaSestraService.save(ms);
				
				System.out.println("------------------------------------------------------");
				return new ResponseEntity<>(new OdmorOdsustvoMSDTO(ooms), HttpStatus.CREATED);
//			}
//		}
//		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	//posalji zahtev lekar
	@PostMapping(path = "/posaljiZahtevLekar", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> slanjeZahtevaLekar(@RequestBody OdmorOdsustvoLDTO ooDTO) {
		System.out.println("------------------------------------------------------");
		
		Lekar lekar = lekarService.findByEmail(ooDTO.getEmailL());
		
		Set<Termin> pregledi = lekar.getListaZauzetihTermina();
		for(Termin p : pregledi) {
			if(ooDTO.getDatumOd().compareTo(p.getDatumPocetka()) * p.getDatumPocetka().compareTo(ooDTO.getDatumDo()) >= 0) {
				System.out.println("----------------nalazi se pregled---------------");
				return new ResponseEntity<>("Datum je zauzet.", HttpStatus.OK);
			}
		}
		//ali i operacija se nalazi u listi zauzetih termina lekara
//		Set<Operacija> operacije = lekar.getListaOperacija();
//		for(Operacija o : operacije) {
//			if(o.getStatus() == 1) {
//				if(ooDTO.getDatumOd().compareTo(o.getDatum()) * o.getDatum().compareTo(ooDTO.getDatumDo()) >= 0) {
//					System.out.println("----------------nalazi se pregled---------------");
//					return new ResponseEntity<>("Datum je zauzet.", HttpStatus.OK);
//				}
//			}
//			
//		}
		
		
		Set<OdmorOdsustvoLekar> listaool = lekar.getListaOdmorOdsustvo();
		for(OdmorOdsustvoLekar ool: listaool) {
			if(ool.getStatus() ==  1) {
				if( ooDTO.getDatumOd().compareTo(ool.getDatumOd()) * ool.getDatumOd().compareTo(ooDTO.getDatumDo()) >= 0) {
					System.out.println("-------------nalazi se odsustvo i odmor-------------");
					return new ResponseEntity<>("Datum je zauzet.", HttpStatus.OK);
				}
				if( ooDTO.getDatumOd().compareTo(ool.getDatumDo()) * ool.getDatumDo().compareTo(ooDTO.getDatumDo()) >= 0) {
					System.out.println("-------------nalazi se odsustvo i odmor-------------");
					return new ResponseEntity<>("Datum je zauzet.", HttpStatus.OK);
				}
				
			}
		}
		
		//TODO 1 : I ZA OPERACIJE DODATI
		
		
			

			Klinika k = klinikaService.findById(lekar.getKlinika().getId());
			

				OdmorOdsustvoLekar ooms = new OdmorOdsustvoLekar();
				ooms.setDatumOd(ooDTO.getDatumOd());
				ooms.setDatumDo(ooDTO.getDatumDo());
				ooms.setOpis(ooDTO.getOpis());
				System.out.println(ooDTO.getTip());
				if(ooDTO.getTip().equals("ODMOR")) {
					System.out.println("ispis odmora");
					ooms.setTip(TipOdmorOdsustvo.ODMOR);
				}else {
					ooms.setTip(TipOdmorOdsustvo.ODSUSTVO);
				}
				
				ooms.setStatus(0);
				ooms.setLekar(lekar);
				ooms.setKlinika(k);
				
				ooms = oolService.save(ooms);
				
				k.getZahteviZaOdmorOdsustvoLekara().add(ooms);
				k = klinikaService.save(k);
				
				lekar.getListaOdmorOdsustvo().add(ooms);
				lekar = lekarService.save(lekar);
				
				System.out.println("------------------------------------------------------");
				return new ResponseEntity<>(new OdmorOdsustvoLDTO(ooms), HttpStatus.CREATED);


	}
	
	
	//ODOBRI zahtev med sestri
	@PostMapping(path = "/potvrdaMS", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<String> potvrdaZahtevaMedSes(@RequestBody OdmorOdsustvoMSDTO ooDTO) {
		System.out.println("------------------------------------------------------");
		
	
		MedicinskaSestra ms = medicinskaSestraService.findById(ooDTO.getIdMedSestre());
		MedicinskaSestraDTO msDTO = new MedicinskaSestraDTO(ms);
		
		Klinika k = klinikaService.findById(ms.getKlinika().getId());
		KlinikaDTO kDTO = new KlinikaDTO(k);
		
		OdmorOdsustvoMedicinskaSestra ooms = oomsService.findById(ooDTO.getId());
		OdmorOdsustvoMSDTO oomsDTO = new OdmorOdsustvoMSDTO(ooms);
			
		String subject ="Odobren zahtev za odmor/odsustvo";
		String text = "Postovani " + ms.getIme() + " " + ms.getPrezime() 
					+ ",\n\nOdobren vam je zahtev za godisnji odmor/odsustvo u trajanju od " 
					+ ooms.getDatumOd() + " do " + ooms.getDatumDo()
					+ ".\n\nS postovanjem,\n" + k.getNaziv() ;

		System.out.println(subject);
		System.out.println(text);
		
		
		//slanje emaila
		try {
			emailService.poslatiOdgovorMedSestri(msDTO, subject, text);
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
		}
		
		System.out.println("status pre: "+ooms.getStatus());
		ooms.setStatus(1);
		ooms = oomsService.save(ooms);
		System.out.println("status posle: "+ooms.getStatus());
		
	
		return new ResponseEntity<>("odobreno", HttpStatus.CREATED);

	}
	
	//ODBIJ zahtev med sestri
	@PostMapping(path = "/odbijanjeMS/{razlog}", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<String> odbjanjeZahtevaMedSes(@RequestBody OdmorOdsustvoMSDTO ooDTO, @PathVariable String razlog) {
		System.out.println("------------------------------------------------------");
		
	
		MedicinskaSestra ms = medicinskaSestraService.findById(ooDTO.getIdMedSestre());
		MedicinskaSestraDTO msDTO = new MedicinskaSestraDTO(ms);
		
		Klinika k = klinikaService.findById(ms.getKlinika().getId());
//		KlinikaDTO kDTO = new KlinikaDTO(k);
		
		OdmorOdsustvoMedicinskaSestra ooms = oomsService.findById(ooDTO.getId());
//		OdmorOdsustvoMSDTO oomsDTO = new OdmorOdsustvoMSDTO(ooms);
			
		String subject ="Odbijen zahtev za odmor/odsustvo";
		String text = "Postovani " + ms.getIme() + " " + ms.getPrezime() 
					+ ",\n\nOdbijen vam je zahtev za godisnji odmor/odsustvo.\nRazlog: " + razlog  
					+ ".\n\nS postovanjem,\n" + k.getNaziv() ;

		System.out.println(subject);
		System.out.println(text);
		
		
		//slanje emaila
		try {
			emailService.poslatiOdgovorMedSestri(msDTO, subject, text);
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
		}
		
		
		ooms.setStatus(2);
		ooms = oomsService.save(ooms);
		
//		k.getZahteviZaOdmorOdsustvoMedestre().remove(ooms);
		k = klinikaService.save(k);
		
//		ms.getListaOdmorOdsustvo().remove(ooms);
		ms = medicinskaSestraService.save(ms);
		
		
		
		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("odbijeno", HttpStatus.OK);

	}
	
	//ODOBRI zahtev lekaru
	@PostMapping(path = "/potvrdaL", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<String> potvrdaZahtevaLekaru(@RequestBody OdmorOdsustvoLDTO ooDTO) {
		System.out.println("------------------------------------------------------");
		
	
		Lekar l = lekarService.findById(ooDTO.getIdLekara());
		LekarDTO lDTO = new LekarDTO(l);
		
		Klinika k = klinikaService.findById(l.getKlinika().getId());
		KlinikaDTO kDTO = new KlinikaDTO(k);
		
		OdmorOdsustvoLekar ooms = oolService.findById(ooDTO.getId());
		OdmorOdsustvoLDTO oomsDTO = new OdmorOdsustvoLDTO(ooms);
			
		String subject ="Odobren zahtev za odmor/odsustvo";
		String text = "Postovani " + l.getIme() + " " + l.getPrezime() 
					+ ",\n\nOdobren vam je zahtev za godisnji odmor/odsustvo u trajanju od " 
					+ ooms.getDatumOd() + " do " + ooms.getDatumDo()
					+ ".\n\nS postovanjem,\n" + k.getNaziv() ;

		System.out.println(subject);
		System.out.println(text);
		
		
		//slanje emaila
		try {
			emailService.poslatiOdgovorLekaru(lDTO, subject, text);
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
		}
		
		System.out.println("status pre: "+ooms.getStatus());
		ooms.setStatus(1);
		ooms = oolService.save(ooms);
		System.out.println("status posle: "+ooms.getStatus());
		
	
		return new ResponseEntity<>("odobreno", HttpStatus.CREATED);

	}
	
	//ODBIJ zahtev lekaru
	@PostMapping(path = "/odbijanjeL/{razlog}", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<String> odbijanjeZahtevaLekaru(@RequestBody OdmorOdsustvoLDTO ooDTO, @PathVariable String razlog) {
		System.out.println("------------------------------------------------------");
		
	
		Lekar l = lekarService.findById(ooDTO.getIdLekara());
		LekarDTO lDTO = new LekarDTO(l);
		
		Klinika k = klinikaService.findById(l.getKlinika().getId());
		KlinikaDTO kDTO = new KlinikaDTO(k);
		
		OdmorOdsustvoLekar ooms = oolService.findById(ooDTO.getId());
		OdmorOdsustvoLDTO oomsDTO = new OdmorOdsustvoLDTO(ooms);
			
		String subject ="Odbijen zahtev za odmor/odsustvo";
		String text = "Postovani " + l.getIme() + " " + l.getPrezime() 
					+ ",\n\nOdbijen vam je zahtev za godisnji odmor/odsustvo.\nRazlog: " + razlog 

					+ ".\n\nS postovanjem,\n" + k.getNaziv() ;

		System.out.println(subject);
		System.out.println(text);
		
		
		//slanje emaila
		try {
			emailService.poslatiOdgovorLekaru(lDTO, subject, text);
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
		}
		
		ooms.setStatus(2);
		ooms = oolService.save(ooms);
		
//		k.getZahteviZaOdmorOdsustvoLekara().remove(ooms);
		k = klinikaService.save(k);
		
//		l.getListaOdmorOdsustvo().remove(ooms);
		l = lekarService.save(l);
		
		
		
		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("odobreno", HttpStatus.OK);

	}
	
}