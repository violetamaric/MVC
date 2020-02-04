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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MedicinskaSestraDTO;
import com.example.demo.dto.OdmorOdsustvoMSDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.OdmorOdsustvoMedicinskaSestra;
import com.example.demo.model.Pacijent;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.MedicinskaSestraService;



@RestController
@RequestMapping(value="/api/medicinskaSestra", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicinskaSestraController {
	@Autowired
	private MedicinskaSestraService medicinskaSestraService;
	
	
	@Autowired
	private KlinikaService klinikaService;
	
	
	//vrati sve medicinske sestre
	@GetMapping(value = "/sve")
	@PreAuthorize("hasAuthority('MED_SESTRA')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<MedicinskaSestraDTO>> getAll() {

		List<MedicinskaSestra> medSes = medicinskaSestraService.findAll();

		List<MedicinskaSestraDTO> medSesDTO = new ArrayList<>();
		for (MedicinskaSestra ms : medSes) {
			medSesDTO.add(new MedicinskaSestraDTO(ms) );
		}

		return new ResponseEntity<>(medSesDTO, HttpStatus.OK);
	}

	//vrati odredjenu med sestru
	@GetMapping(value = "/medicinskaSestra")
	@PreAuthorize("hasAuthority('MED_SESTRA')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<MedicinskaSestraDTO> getMedicinskaSestraByEmail(Principal p){
		
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(p.getName());
		if (ms == null) {
			System.out.println("NIJE PRONADJENA");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("PRONADJENA: "+ ms.getEmail());
		
		return new ResponseEntity<>(new MedicinskaSestraDTO(ms), HttpStatus.OK);
	}
	
	@GetMapping(value = "/medSestra/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<MedicinskaSestraDTO> getMedicinskaSestraById(@PathVariable Long id){
		
		MedicinskaSestra ms = medicinskaSestraService.findById(id);
		if (ms == null) {
			System.out.println("NIJE PRONADJENA");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("PRONADJENA: "+ ms.getEmail());
		
		return new ResponseEntity<>(new MedicinskaSestraDTO(ms), HttpStatus.OK);
	}

	//vrati listu pacijenata
	@GetMapping(value = "/listaPacijenata")
	@PreAuthorize("hasAuthority('MED_SESTRA')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<PacijentDTO>> getListaPacijenata(Principal pr) {
		System.out.println("//////////////////// MED SESTRA LISTA PACIJENATA ////////////////////////");
		
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(pr.getName());
		
		List<Pacijent> listaPacijenataKlinike = klinikaService.findByIdKlinike(ms.getKlinika().getId());
		
		//List<Pacijent> listaSvihP =  pacijenti.findAll();
		System.out.println("Lista pacijenata od MED SESTRE: " + ms.getEmail());
		List<PacijentDTO> lista = new ArrayList<>();
			
		for (Pacijent p : listaPacijenataKlinike ) {
			
				System.out.println(p);
				
				if(p.getOdobrenaRegistracija() == true ) {
					PacijentDTO pDTO = new PacijentDTO(p);
					System.out.println("Pacijent dodat");
					lista.add(pDTO);
				}
						
		}
		
		System.out.println("*************");
//		for(PacijentDTO pd  : lista) {
//			System.out.println(pd);
//		}
		System.out.println("*************");
		return new ResponseEntity<>(lista, HttpStatus.OK);

		
	}
	
	//izmeni medicinsku sestru
	@PutMapping(path="/izmena", consumes = "application/json")
	@PreAuthorize("hasAuthority('MED_SESTRA')")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<MedicinskaSestraDTO> izmeniMedicinskuSestru(@RequestBody MedicinskaSestraDTO msDTO) {

		// a student must exist
		System.out.println("MED SESTRA IZMENA");
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(msDTO.getEmail());


		if(msDTO.getIme() != null && msDTO.getIme() != "") {
			System.out.println("izmenjeno ime admina");
			ms.setIme(msDTO.getIme());
			
		}
		if(msDTO.getPrezime()!= null && msDTO.getPrezime() != "") {
			System.out.println("izmenjeno prezime admina");
			ms.setPrezime(msDTO.getPrezime());
			
		}
		if(msDTO.getLozinka()!= null && msDTO.getLozinka() != "") {
			System.out.println("izmenjena lozinka admina");
			ms.setLozinka(msDTO.getLozinka());
			
		}
		if(msDTO.getBrTelefona()!= null && msDTO.getBrTelefona() != "") {
			System.out.println("izmenjena lozinka admina");
			ms.setBrTelefona(msDTO.getBrTelefona());
			
		}
		
		ms = medicinskaSestraService.save(ms);
		return new ResponseEntity<>(new MedicinskaSestraDTO(ms), HttpStatus.OK);
	}

//	//vrati odredjenog pacijenta
////	@GetMapping(value = "/findPacijentEmail/{email:.+}")
//	@GetMapping(value = "/findPacijentEmail/{email}")
//	@CrossOrigin(origins = "http://localhost:3000")
//	@PreAuthorize("hasAuthority('MED_SESTRA')")
//	public ResponseEntity<?> getPacijentByEmail(@PathVariable String email) {
//		System.out.println("find pacijent");
//		Pacijent pacijent = pacijentService.findByEmail(email);
//		System.out.println("pacijent " + pacijent);
//		if (pacijent == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		System.out.println(pacijent.getEmail() + "++++");
//		return ResponseEntity.ok(new PacijentDTO(pacijent));
//	}

//	//zdravstveni karton
//	@GetMapping(value = "/findZK/{email}")
//	@CrossOrigin(origins = "http://localhost:3000")
//	@PreAuthorize("hasAuthority('MED_SESTRA')")
//	public ResponseEntity<ZdravstveniKarton> getZK(@PathVariable String email) {
//
//		System.out.println("find pacijent");
//		System.out.println("zk");
//
//		Pacijent pacijent = pacijentService.findByEmail(email);
//		System.out.println("Pacijent: " + pacijent);
//		if (pacijent == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//
//		ZdravstveniKarton zk = pacijent.getZdravstveniKarton();
//		System.out.println(pacijent.getEmail() + "++++");
//		Pacijent p = new Pacijent();
//		p.setEmail(pacijent.getEmail());
//		zk.setPacijent(p);
//		return new ResponseEntity<>(new ZdravstveniKarton(zk), HttpStatus.OK);
//	}
//	
	
	
	//vraca listu odmora i odsustva
	@GetMapping(value = "/listaOdmor")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('MED_SESTRA')")
	public ResponseEntity<List<OdmorOdsustvoMSDTO>> getOdmor(Principal p) {

		System.out.println("ODMOR ");
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(p.getName());
		
		if (ms == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<OdmorOdsustvoMSDTO> omsDTO = new ArrayList<>();
		for(OdmorOdsustvoMedicinskaSestra oms : ms.getListaOdmorOdsustvo()) {
			System.out.println("Jedan zahtev: " + oms.getTip()+ " " + oms.getStatus() );
			if(oms.getStatus() == 1) {
				System.out.println("Jedan zahtev: " + oms.getTip()+ " " + oms.getStatus() );
				System.out.println(oms.getDatumOd() + " " + oms.getDatumDo());
				omsDTO.add(new OdmorOdsustvoMSDTO(oms));
			}
			
			
		}
		
		return new ResponseEntity<>(omsDTO, HttpStatus.OK);
	}
	
	
//	//vraca listu pregleda
//	@GetMapping(value = "/listaPregleda/{email}")
//	@CrossOrigin(origins = "http://localhost:3000")
//	public ResponseEntity<List<PregledDTO>> getListaPregleda(@PathVariable String email) {
//		System.out.println("//////////////////// MED SESTRA LISTA Radnih dana ////////////////////////");
//		
//		MedicinskaSestra ms = medicinskaSestraService.findByEmail(email);
////		Set<Pregled> listaRD = ms.getListaPregleda();
//		List<PregledDTO> lista = new ArrayList<PregledDTO>();
////		for(Pregled rd: listaRD) {
////			System.out.println(rd.getDatumPocetka());
////			lista.add(new PregledDTO(rd));
////		}
//		
//
//		System.out.println("*************");
//		return new ResponseEntity<>(lista, HttpStatus.OK);
//
//		
//	}
	

}
