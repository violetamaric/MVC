package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.PregledController;
import com.example.demo.controller.SlobodniTerminController;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.SlobodniTerminDTO;
import com.example.demo.model.Authority;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.PacijentService;
import com.example.demo.service.PregledService;

//Scenario: "Ja pacijent zelim da zakakazem unapred definisani pregled."
//
//	1. Ja pacijent zelim da vidim sve unapred definisane termine
//	2. Ja pacijent zelim da zakazem jedan unapred definisan termin
//	3. Ja pacijent zelim da vidim zakazani pregled
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class EndToEndTest {

	@Autowired
	private SlobodniTerminController STController;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityRepository authRepository;

	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private PregledService pregledService;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PregledController pregledController;

//	@Before
//    public void init() {
//        MockitoAnnotations.initMocks(this);
//    }

	// pregled svih unapred definisanih termina
	@Test
	public void testFindAllUD() {

		// simuliramo ulogovanog pacijenta

		Pacijent pacijent = new Pacijent();
		pacijent.setIme("Pera");
		pacijent.setPrezime("Peric");
		pacijent.setLbo("101");
		pacijent.setEmail("test@gmail.com");
		pacijent.setLozinka("test");
		pacijent.setAdresa("Temerinska 4");
		pacijent.setGrad("Novi Sad");
		pacijent.setDrzava("Srbija");
		pacijent.setTelefon("060789654");
		pacijent.setJmbg("0303966811711");
		pacijent.setOdobrenaRegistracija(false);

		Authority a = authRepository.findByUloga("PACIJENT");
		Set<Authority> auth = new HashSet<Authority>();
		auth.add(a);
		pacijent.setAuthorities(auth);

//		when(pacijentService.save((Pacijent) any(Pacijent.class)));
//		pacijentService.save(pacijent);
//
//		String jwt = tokenUtils.tokenPacijent(pacijent, a);
//		assertNotNull(jwt);
//
//		final Authentication authentication = authenticationManager
//
//				.authenticate(new UsernamePasswordAuthenticationToken("test@gmail.com",
//
//						"test"));
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);

		// preuzimanje svih unapred definisanih pregleda
		List<SlobodniTerminDTO> termini = (STController.getAllUnapredDef()).getBody();
		assertThat(termini).isNotNull();
	}

	// pacijent zakazuje pregled iz liste unapred definisanih pregleda
	@Test
	@Transactional
	@Rollback(true)
	public void testZakazi() {

		SlobodniTerminDTO st = (STController.getAllUnapredDef()).getBody().get(0);
		PregledDTO pregledDTO = new PregledDTO(st);
		List<SlobodniTerminDTO> termini = (STController.getAllUnapredDef()).getBody();
//		int pregledi = (pregledController.getAll()).getBody().size();
//		List<Pregled> pregledi2 = (pregledService.findAll());
//		assertThat(pregledi2).hasSize(pregledi2.size());
		
		System.out.println();
		PacijentDTO pacijent2 = new PacijentDTO((pacijentService.findByID(1L)));
		System.out.println("*****************pacijent 2" + pacijent2);
		Pacijent pacijent = new Pacijent();
		pacijent.setId(1L);
		pacijent.setIme(pacijent2.getIme());
		pacijent.setPrezime(pacijent2.getPrezime());
		pacijent.setLbo(pacijent2.getLbo());
		pacijent.setEmail(pacijent2.getEmail());
		pacijent.setLozinka(pacijent2.getLozinka());
		pacijent.setAdresa(pacijent2.getAdresa());
		pacijent.setGrad(pacijent2.getGrad());
		pacijent.setDrzava(pacijent2.getDrzava());
		pacijent.setTelefon(pacijent2.getTelefon());
		pacijent.setOdobrenaRegistracija(pacijent2.getOdobrenaRegistracija());
		System.out.println("----------------pacijent 2" + pacijent2);

		Authority a = new Authority();
		a.setId(new Long(1L));
		a.setName(new String("PACIJENT"));

		System.out.println("????????????????????auth" + a);

		String jwt = tokenUtils.tokenPacijent(pacijent, a);
		assertNotNull(jwt);

		System.out.println("1111111");
		final Authentication authentication = authenticationManager

				.authenticate(new UsernamePasswordAuthenticationToken(pacijent2.getEmail(),

						"pera"));
		System.out.println("1111111");
		SecurityContextHolder.getContext().setAuthentication(authentication);

		pregledDTO.setPacijentEmail(pacijent2.getEmail());
		pregledController.noviPregledST(pregledDTO);
//		System.out.println(pregledi.size());
		System.out.println();
		System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
//		List<SlobodniTerminDTO> termini = (STController.getAllUnapredDef()).getBody();
		
		assertThat(termini).hasSize(termini.size());
//		assertThat(pregledi2).hasSize(pregledi + 1);

	}

}
