package com.example.demo.controller;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.AdministratorKC;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Authority;
import com.example.demo.model.Lekar;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Pacijent;
import com.example.demo.model.UserTokenState;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.AdministratorKCService;
import com.example.demo.service.AdministratorKlinikeService;
import com.example.demo.service.LekarService;
import com.example.demo.service.MedicinskaSestraService;
import com.example.demo.service.PacijentService;

@RestController
@RequestMapping(value = "/api/korisnici", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
//	@Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
	private PacijentService pacijentService;

	@Autowired
	private LekarService lekarService;

	@Autowired
	private MedicinskaSestraService medicinskaSestraService;

	@Autowired
	private AdministratorKCService administratorKCService;

	@Autowired
	private AdministratorKlinikeService administratorKlinikeService;

	@Autowired
	private TokenUtils tokenUtils;
	
    

//	@Autowired
//	private AuthenticationManager authenticationManager;

//	@PostMapping(path = "/login", consumes = "application/json")
//	@CrossOrigin(origins = "http://localhost:3000")
//	public ResponseEntity<UserDTO> login(@RequestBody PacijentDTO pacijentDTO) {
//
//		System.out.println("LOGIN");
//		
//		AdministratorKC administratorKC = administratorKCService.findByEmailAndLozinka(pacijentDTO.getEmail(),
//				pacijentDTO.getLozinka());
//		if(administratorKC == null) {
//			AdministratorKlinike administratorKlinike = administratorKlinikeService.findByEmailAndLozinka(pacijentDTO.getEmail(),
//					pacijentDTO.getLozinka());
//			if(administratorKlinike == null) {
//				Lekar lekar = lekarService.findByEmailAndLozinka(pacijentDTO.getEmail(),
//						pacijentDTO.getLozinka());
//				if (lekar == null) {
//					MedicinskaSestra medicinskaSestra = medicinskaSestraService.findByEmailAndLozinka(pacijentDTO.getEmail(),
//							pacijentDTO.getLozinka());
//					if (medicinskaSestra == null) {
//						Pacijent pacijent = pacijentService.findByEmailAndLozinka(pacijentDTO.getEmail(),
//								pacijentDTO.getLozinka());
//						if (pacijent == null) {
//							return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//						}else if(pacijent.getOdobrenaRegistracija() == false){
//							System.out.println("NIJE ODOBRENA REGISTRACIJA OD STRANE ADMINA KC");
//							return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//						}else {
//							UserDTO userDTO = new UserDTO();
//							userDTO.setEmail(pacijent.getEmail());
//							userDTO.setLozinka(pacijent.getLozinka());
//							userDTO.setUloga(Uloga.PACIJENT);
//							System.out.println(userDTO);
//							return new ResponseEntity<>(userDTO, HttpStatus.OK);							
//						}
//					}else {
//						UserDTO userDTO = new UserDTO();
//						userDTO.setEmail(medicinskaSestra.getEmail());
//						userDTO.setLozinka(medicinskaSestra.getLozinka());
//						userDTO.setUloga(Uloga.MEDICINSKASESTRA);
//						System.out.println(userDTO);
//						return new ResponseEntity<>(userDTO, HttpStatus.OK);
//						
//					}
//				}else {
//					UserDTO userDTO = new UserDTO();
//					userDTO.setEmail(lekar.getEmail());
//					userDTO.setLozinka(lekar.getLozinka());
//					userDTO.setUloga(Uloga.LEKAR);
//					System.out.println(userDTO);
//					return new ResponseEntity<>(userDTO, HttpStatus.OK);
//				}
//			}else {
//				UserDTO userDTO = new UserDTO();
//				userDTO.setEmail(administratorKlinike.getEmail());
//				userDTO.setLozinka(administratorKlinike.getLozinka());
//				userDTO.setUloga(Uloga.ADMINISTRATORK);
//				System.out.println(userDTO);
//				return new ResponseEntity<>(userDTO, HttpStatus.OK);
//			}
//		}else {
//			UserDTO userDTO = new UserDTO();
//			userDTO.setEmail(administratorKC.getEmail());
//			userDTO.setLozinka(administratorKC.getLozinka());
//			userDTO.setUloga(Uloga.ADMINISTRATORKC);
//			System.out.println(userDTO);
//			return new ResponseEntity<>(userDTO, HttpStatus.OK);
//		}
//		
//	}
//	@PostMapping(path = "/login", consumes = "application/json")
//	@CrossOrigin(origins = "http://localhost:3000")
//	public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDTO userDTO,
//
//			HttpServletResponse response) throws AuthenticationException, IOException {
//
//		final Authentication authentication = authenticationManager
//
//				.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(),
//
//						userDTO.getLozinka()));
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		Object nekiKorisnik = authentication.getPrincipal();
//
//		if (nekiKorisnik instanceof Pacijent) {
//
//			Pacijent pacijent = (Pacijent) nekiKorisnik;
//
//			Collection<?> roles = pacijent.getAuthorities();
//
//			String jwt = tokenUtils.tokenPacijent(pacijent, (Authority) roles.iterator().next());
//
//			int expiresIn = tokenUtils.getExpiredIn();
//
//			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
//
//		} else if (nekiKorisnik instanceof Lekar) {
//
//			Lekar lekar = (Lekar) nekiKorisnik;
//
//			Collection<?> roles = lekar.getAuthorities();
//
//			String jwt = tokenUtils.tokenLekar(lekar, (Authority) roles.iterator().next());
//
//			int expiresIn = tokenUtils.getExpiredIn();
//
//			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
//
//		} else if (nekiKorisnik instanceof MedicinskaSestra) {
//
//			MedicinskaSestra medicinskaSestra = (MedicinskaSestra) nekiKorisnik;
//
//			Collection<?> roles = medicinskaSestra.getAuthorities();
//
//			String jwt = tokenUtils.tokenMedicinskaSestra(medicinskaSestra, (Authority) roles.iterator().next());
//
//			int expiresIn = tokenUtils.getExpiredIn();
//
//			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
//
//		} else if (nekiKorisnik instanceof AdministratorKlinike) {
//
//			AdministratorKlinike administratorKlinike = (AdministratorKlinike) nekiKorisnik;
//
//			Collection<?> roles = administratorKlinike.getAuthorities();
//
//			String jwt = tokenUtils.tokenAKC(administratorKlinike, (Authority) roles.iterator().next());
//
//			int expiresIn = tokenUtils.getExpiredIn();
//
//			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
//
//		} else if (nekiKorisnik instanceof AdministratorKC) {
//
//			AdministratorKC administratorKlinickogCentra = (AdministratorKC) nekiKorisnik;
//
//			Collection<?> roles = administratorKlinickogCentra.getAuthorities();
//
//			String jwt = tokenUtils.tokenAK(administratorKlinickogCentra, (Authority) roles.iterator().next());
//
//			int expiresIn = tokenUtils.getExpiredIn();
//
//			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
//
//		} else {
//
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//
//		}
//
//	}
}
