package com.example.demo.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.AdministratorKC;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Authority;
import com.example.demo.model.KlinickiCentar;
import com.example.demo.model.Lekar;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Pacijent;
import com.example.demo.model.UserTokenState;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.AdministratorKCService;
import com.example.demo.service.AdministratorKlinikeService;
import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.service.KlinickiCentarService;
import com.example.demo.service.LekarService;
import com.example.demo.service.MedicinskaSestraService;
import com.example.demo.service.PacijentService;

@RestController
@RequestMapping(value = "/api/korisnici", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	private KlinickiCentarService KCService;

	@Autowired
	private AuthorityRepository authorityRepository;
    

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
//	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
//	@CrossOrigin(origins = "http://localhost:3000")
//	public ResponseEntity<UserDTO> getUser(){
//		System.out.println("GET USER");
//		
//		UserDTO userDTO = new UserDTO();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		System.out.println(SecurityContextHolder.getContext().getAuthentication());
//		userDTO.setEmail((String) auth.getPrincipal());
//		System.out.println(userDTO.getEmail());
//		System.out.println(auth.getAuthorities());
//		return new ResponseEntity<>(userDTO, HttpStatus.OK);
//	}
	
	@PostMapping(path = "/register", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<PacijentDTO> registerPacijent(@RequestBody PacijentDTO pacijentDTO) {

		Pacijent pacijent = new Pacijent();
		
		List<Pacijent> pacijenti = pacijentService.findAll();
		for (Pacijent p :pacijenti) {
			if(p.getLbo().equals(pacijentDTO.getLbo()) ||
					p.getJmbg().equals(pacijentDTO.getJmbg()) ||
					p.getEmail().equals(pacijentDTO.getEmail())) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		}

		pacijent.setLbo(pacijentDTO.getLbo());
		pacijent.setIme(pacijentDTO.getIme());
		pacijent.setPrezime(pacijentDTO.getPrezime());
		pacijent.setEmail(pacijentDTO.getEmail());
		pacijent.setLozinka(passwordEncoder.encode(pacijentDTO.getLozinka()));
		pacijent.setAdresa(pacijentDTO.getAdresa());
		pacijent.setGrad(pacijentDTO.getGrad());
		pacijent.setDrzava(pacijentDTO.getDrzava());
		pacijent.setTelefon(pacijentDTO.getTelefon());
		pacijent.setOdobrenaRegistracija(false);
		pacijent.setJmbg(pacijentDTO.getJmbg());
		Set<Authority> authorities = new HashSet<Authority>();
		authorities.add(authorityRepository.findByUloga("PACIJENT"));
		pacijent.setAuthorities(authorities);

		List<KlinickiCentar> listaKC = KCService.find();
		KlinickiCentar kc = listaKC.get(0);
		pacijent.setKlinickiCentar(kc);

		pacijent = pacijentService.save(pacijent);
		kc.getZahteviZaRegistraciju().add(pacijent);
		kc = KCService.save(kc);

//		KlinickiCentar kc = pacijent.getKlinickiCentar();
//		
//		System.out.println("dodat u zahteve za registraciju");
//		kc.getZahteviZaRegistraciju().add(pacijent);

		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.CREATED);
	}

	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> createAuthenticationToken(HttpServletRequest req,@RequestBody UserDTO userDTO,

			HttpServletResponse response) throws AuthenticationException, IOException {
		System.out.println("LOGIN");
		System.out.println(userDTO.getEmail());
		System.out.println(userDTO.getLozinka());
		UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken(userDTO.getEmail(),

				userDTO.getLozinka());
		System.out.println("prosao auth");
		System.out.println(u);
		final Authentication authentication = authenticationManager

				.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(),

						userDTO.getLozinka()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("***" + SecurityContextHolder.getContext().getAuthentication());
//		SecurityContext sc = SecurityContextHolder.getContext();
//	    sc.setAuthentication(authentication);
//	    HttpSession session = req.getSession(true);
//	    session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

		Object nekiKorisnik = authentication.getPrincipal();

		if (nekiKorisnik instanceof Pacijent) {

			Pacijent pacijent = (Pacijent) nekiKorisnik;

			Collection<?> roles = pacijent.getAuthorities();

			String jwt = tokenUtils.tokenPacijent(pacijent, (Authority) roles.iterator().next());

			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, ((Authority) roles.iterator().next()).getUloga(),((Pacijent)authentication.getPrincipal()).getEmail()));

		} else if (nekiKorisnik instanceof Lekar) {

			Lekar lekar = (Lekar) nekiKorisnik;

			Collection<?> roles = lekar.getAuthorities();

			String jwt = tokenUtils.tokenLekar(lekar, (Authority) roles.iterator().next());

			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, ((Authority) roles.iterator().next()).getUloga(),((Lekar)authentication.getPrincipal()).getEmail()));

		} else if (nekiKorisnik instanceof MedicinskaSestra) {

			MedicinskaSestra medicinskaSestra = (MedicinskaSestra) nekiKorisnik;

			Collection<?> roles = medicinskaSestra.getAuthorities();

			String jwt = tokenUtils.tokenMedicinskaSestra(medicinskaSestra, (Authority) roles.iterator().next());

			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, ((Authority) roles.iterator().next()).getUloga(),((MedicinskaSestra)authentication.getPrincipal()).getEmail()));

		} else if (nekiKorisnik instanceof AdministratorKlinike) {

			AdministratorKlinike administratorKlinike = (AdministratorKlinike) nekiKorisnik;

			Collection<?> roles = administratorKlinike.getAuthorities();

			String jwt = tokenUtils.tokenAKC(administratorKlinike, (Authority) roles.iterator().next());

			int expiresIn = tokenUtils.getExpiredIn();
			
			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, ((Authority) roles.iterator().next()).getUloga(),((AdministratorKlinike)authentication.getPrincipal()).getEmail()));

		} else if (nekiKorisnik instanceof AdministratorKC) {

			AdministratorKC administratorKlinickogCentra = (AdministratorKC) nekiKorisnik;

			Collection<?> roles = administratorKlinickogCentra.getAuthorities();

			String jwt = tokenUtils.tokenAK(administratorKlinickogCentra, (Authority) roles.iterator().next());
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, ((Authority) roles.iterator().next()).getUloga(),((AdministratorKC)authentication.getPrincipal()).getEmail()));

		} else {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

	}

	@PutMapping(path = "/potvrdaRegistracije/{id}", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> aktivirajPacijenta(@PathVariable Long id) {

		// a student must exist
		System.out.println("AKTIVIRAJ PACIJENTA");
		Pacijent pacijent = pacijentService.findByID(id);

		pacijent.setOdobrenaRegistracija(true);

		pacijent = pacijentService.save(pacijent);
		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
	}
}
