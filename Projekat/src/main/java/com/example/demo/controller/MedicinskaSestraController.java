package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MedicinskaSestraDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.RadniDanDTO;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Pacijent;
import com.example.demo.model.RadniDan;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.MedicinskaSestraService;
import com.example.demo.service.PacijentService;



@RestController
@RequestMapping(value="/api/medicinskaSestra", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicinskaSestraController {
	@Autowired
	private MedicinskaSestraService medicinskaSestraService;
	
	@Autowired
	private PacijentService pacijenti;
	
	@Autowired
	private KlinikaService klinikaService;
	
	//vrati sve medicinske sestre
	@GetMapping(value = "/sve")
	public ResponseEntity<List<MedicinskaSestraDTO>> getAll() {

		List<MedicinskaSestra> medSes = medicinskaSestraService.findAll();

		List<MedicinskaSestraDTO> medSesDTO = new ArrayList<>();
		for (MedicinskaSestra ms : medSes) {
			medSesDTO.add(new MedicinskaSestraDTO(ms) );
		}

		return new ResponseEntity<>(medSesDTO, HttpStatus.OK);
	}

	//vrati odredjenu med sestru
	@GetMapping(value = "/medicinskaSestra/{email}")
	public ResponseEntity<MedicinskaSestraDTO> getMedicinskaSestraByEmail(@PathVariable String email){
		
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(email);
		if (ms == null) {
			System.out.println("NIJE PRONADJENA");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("PRONADJENA: "+ ms.getEmail());
		
		return new ResponseEntity<>(new MedicinskaSestraDTO(ms), HttpStatus.OK);
	}

	//vrati listu pacijenata
	@GetMapping(value = "/listaPacijenata/{email}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<PacijentDTO>> getListaPacijenata(@PathVariable String email) {
		System.out.println("//////////////////// MED SESTRA LISTA PACIJENATA ////////////////////////");
		
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(email);
		
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

	@GetMapping(value = "/listaRadnihDana/{email}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<RadniDanDTO>> getListaRadnihDana(@PathVariable String email) {
		System.out.println("//////////////////// MED SESTRA LISTA Radnih dana ////////////////////////");
		
		MedicinskaSestra ms = medicinskaSestraService.findByEmail(email);
		Set<RadniDan> listaRD = ms.getListaRadnihDana();
		List<RadniDanDTO> lista = new ArrayList<RadniDanDTO>();
		for(RadniDan rd: listaRD) {
			System.out.println(rd.getDatumPocetka());
			lista.add(new RadniDanDTO(rd));
		}
		

		System.out.println("*************");
		return new ResponseEntity<>(lista, HttpStatus.OK);

		
	}
}
