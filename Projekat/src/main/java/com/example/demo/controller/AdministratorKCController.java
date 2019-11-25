package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdministratorKCDTO;
import com.example.demo.dto.AdministratorKlinikeDTO;
import com.example.demo.dto.DijagnozaDTO;
import com.example.demo.dto.KlinickiCentarDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.LekDTO;
import com.example.demo.model.AdministratorKC;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Dijagnoza;
import com.example.demo.model.KlinickiCentar;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lek;
import com.example.demo.service.AdministratorKCService;

/* Za pristupanje svim administratorima KC : http://localhost:8028/api/administratoriKC/svi
 * 
 * Za pronalazenje admina KC pomocu maila : http://localhost:8028/api/administratoriKC/pronadjenAdministratorKC/{email}
 * 
 * Za vracanje liste klinika u klinickom centru
 *  na profilu administratoraKC:   http://localhost:8028/api/administratoriKC/listaKlinika/{administratorKCId}
 *  
 *  Za vracanje liste admina klinika u klinickom centru
 *  na profilu administratoraKC: http://localhost:8028/api/administratoriKC/listaAdministratoraKlinika/{administratorKCId}
 *  
 *  Za vracanje podataka o KC na prof admina: http://localhost:8028/api/administratoriKC/klinickiCentar/{administratorKCId}
 *  
 *  */

@RestController
@RequestMapping(value="/api/administratoriKC", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdministratorKCController {
	
	@Autowired
	private AdministratorKCService administratorKCService;
	
	@GetMapping(value = "/svi")
	public ResponseEntity<List<AdministratorKCDTO>> getAll() {

		List<AdministratorKC> administratoriKC = administratorKCService.findAll();

		System.out.println("ISPISANI SVI ADMINISTRATORI KC IZ BAZE");
		List<AdministratorKCDTO> administratoriKCDTO = new ArrayList<>();
		for (AdministratorKC aKC : administratoriKC) {
			administratoriKCDTO.add(new AdministratorKCDTO(aKC));
		}

		return new ResponseEntity<>(administratoriKCDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pronadjenAdministratorKC/{email}")
	public ResponseEntity<AdministratorKCDTO> getAdministratorKCByEmail(@PathVariable String email){
		
		AdministratorKC administratorKC = administratorKCService.findByEmail(email);
		if (administratorKC == null) {
			System.out.println("NIJE PRONADJEN");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("PRONADJEN: "+ administratorKC.getEmail());
		
		return new ResponseEntity<>(new AdministratorKCDTO(administratorKC), HttpStatus.OK);
	}
	
	//mozda treba preko maila ali nzm 
	//vrati mi listu klinika u klinickom centru
	@GetMapping(value = "/listaKlinika/{email}")
	public ResponseEntity<List<KlinikaDTO>> getListaKlinika(@PathVariable String email) {

		AdministratorKC administratorKC = administratorKCService.findByEmail(email);
		
		KlinickiCentar klinickiCentar = administratorKC.getKlinickiCentar();
		
		List<KlinikaDTO> lista = new ArrayList<>();
		
		for (Klinika k : klinickiCentar.getListaKlinika()) {
			KlinikaDTO kcDTO = new KlinikaDTO();
			kcDTO.setId(k.getId());
			kcDTO.setNaziv(k.getNaziv());
			kcDTO.setAdresa(k.getAdresa());
			kcDTO.setOcena(k.getOcena());
			kcDTO.setOpis(k.getOpis());
			lista.add(kcDTO);
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);

		
	}
	
	//vrati mi listu svih admina klinika u klinickom centru
	@GetMapping(value = "/listaAdministratoraKlinika/{email}")
	public ResponseEntity<List<AdministratorKlinikeDTO>> getListaAdministratoraKlinika(@PathVariable String email) {

		AdministratorKC administratorKC = administratorKCService.findByEmail(email);
		
		KlinickiCentar klinickiCentar = administratorKC.getKlinickiCentar();
		List<AdministratorKlinikeDTO> lista = new ArrayList<>();
		for (Klinika k : klinickiCentar.getListaKlinika() ) {
			for(AdministratorKlinike a : k.getListaAdminKlinike()) {
				AdministratorKlinikeDTO aDTO = new AdministratorKlinikeDTO();
				aDTO.setId(a.getId());
				aDTO.setIme(a.getIme());
				aDTO.setPrezime(a.getPrezime());
				aDTO.setEmail(a.getEmail());
				lista.add(aDTO);
			}
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	//vrati mi podatke o klinickom centru
	@GetMapping(value = "/klinickiCentar/{email}")
	public ResponseEntity<KlinickiCentarDTO> getKlinickiCentar(@PathVariable String email) {
		
		AdministratorKC administratorKC = administratorKCService.findByEmail(email);
		
		KlinickiCentar kc = administratorKC.getKlinickiCentar();
		KlinickiCentarDTO kcDTO = new KlinickiCentarDTO();
		kcDTO.setId(kc.getId());
		kcDTO.setNaziv(kc.getNaziv());
		kcDTO.setAdresa(kc.getAdresa());
		kcDTO.setOpis(kc.getOpis());
		
	    
		return new ResponseEntity<>(kcDTO, HttpStatus.OK);
	}
	
	//vrati mi listu lekova u klinickom centru
	@GetMapping(value = "/listaLekova/{email}")
	public ResponseEntity<List<LekDTO>> getListaLekova(@PathVariable String email) {

		AdministratorKC administratorKC = administratorKCService.findByEmail(email);
		
		KlinickiCentar klinickiCentar = administratorKC.getKlinickiCentar();
		
		List<LekDTO> lista = new ArrayList<>();
		
		for (Lek k : klinickiCentar.getListaLekova()) {
			LekDTO kcDTO = new LekDTO();
			kcDTO.setId(k.getId());
			kcDTO.setNaziv(k.getNaziv());
			kcDTO.setSifra(k.getSifra());
			lista.add(kcDTO);
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);

		
	}
	
	//vrati mi listu dijagnoza u klinickom centru
	@GetMapping(value = "/listaDijagnoza/{email}")
	public ResponseEntity<List<DijagnozaDTO>> getListaDijagnoza(@PathVariable String email) {

		AdministratorKC administratorKC = administratorKCService.findByEmail(email);
			
		KlinickiCentar klinickiCentar = administratorKC.getKlinickiCentar();
			
		List<DijagnozaDTO> lista = new ArrayList<>();
			
		for (Dijagnoza k : klinickiCentar.getListaDijagnoza()) {
			DijagnozaDTO kcDTO = new DijagnozaDTO();
			kcDTO.setId(k.getId());
			kcDTO.setNaziv(k.getNaziv());
			kcDTO.setOpis(k.getOpis());
			lista.add(kcDTO);
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);	
	}

	
	
	
}
