package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
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
import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.model.Pacijent;
import com.example.demo.model.SlobodniTermin;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.IzvestajOPregleduService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.PacijentService;
import com.example.demo.service.PregledService;
import com.example.demo.service.SalaService;
import com.example.demo.service.SlobodniTerminService;
import com.example.demo.service.TipPregledaService;

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
	private SlobodniTerminService STService;
	
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

	@Autowired
	private KlinikaService klinikaService;
	
	@Autowired
	private IzvestajOPregleduService IOPService;
	
	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private TipPregledaService TPService;
	
	@Autowired
	private SalaService salaService;

	// pregled svih unapred definisanih termina
	@Test
	public void testFindAllUD() {

		// simuliramo ulogovanog pacijenta
		PacijentDTO pacijent2 = new PacijentDTO((pacijentService.findByID(1L)));
		
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

		pacijent.setOdobrenaRegistracija(2);	
		
		Authority a = new Authority();
		a.setId(new Long(1L));
		a.setName("PACIJENT");

		System.out.println("????????????????????auth" + a);

		String jwt = tokenUtils.tokenPacijent(pacijent, a);
		assertNotNull(jwt);

		System.out.println("1111111");
		final Authentication authentication = authenticationManager

				.authenticate(new UsernamePasswordAuthenticationToken(pacijent2.getEmail(),


						"pera"));
		System.out.println("1111111");
		SecurityContextHolder.getContext().setAuthentication(authentication);


//		Authority a = authRepository.findByUloga("PACIJENT");
//		Set<Authority> auth = new HashSet<Authority>();
//		auth.add(a);
//		pacijent.setAuthorities(auth);

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
		a.setName("PACIJENT");

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
		ResponseEntity<?> re = pregledController.noviPregledST(pregledDTO);
//		System.out.println(pregledi.size());
		System.out.println();
		System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
//		List<SlobodniTerminDTO> termini = (STController.getAllUnapredDef()).getBody();
		
		SlobodniTermin st2 = (STService.findOne(1L));
		
		assertThat(termini).hasSize(termini.size());
		assertThat(st2.isStatus()).isEqualTo(true);
		assertThat(st2.getCena()).isEqualTo(((PregledDTO)re.getBody()).getCena());
		assertThat(st2.getDatum()).isEqualTo(((PregledDTO)re.getBody()).getDatum());
		assertThat(st2.getTermin()).isEqualTo(((PregledDTO)re.getBody()).getTermin());
		assertThat(st2.getSala().getId()).isEqualTo(((PregledDTO)re.getBody()).getSalaID());
		assertThat(st2.getLekar().getId()).isEqualTo(((PregledDTO)re.getBody()).getLekarID());
		assertThat(st2.getKlinika().getId()).isEqualTo(((PregledDTO)re.getBody()).getKlinikaID());
		assertThat(st2.getTipPregleda().getId()).isEqualTo(((PregledDTO)re.getBody()).getTipPregledaID());
		assertThat(pacijent2.getEmail()).isEqualTo(((PregledDTO)re.getBody()).getPacijentEmail());
		assertThat(((PregledDTO)re.getBody()).getStatus()).isEqualTo(1);
//		assertThat(st)
//		assertThat(pregledi2).hasSize(pregledi + 1);

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testProveri() {
		Date date = new Date();
		SlobodniTermin st = new SlobodniTermin();
		st.setCena(1500);
		st.setDatum(date);
		st.setKlinika(klinikaService.findOne(1L));
		st.setLekar(lekarService.findOne(1L));
		st.setPopust(50);
		st.setIzvestajOPregledu(IOPService.findById(1L));
		st.setStatus(false);
		st.setTermin(9);
		st.setTipPregleda(TPService.findOne(1L));
		st.setSala(salaService.findById(1L));
		STService.save(st);
		SlobodniTerminDTO stDTO = new SlobodniTerminDTO(st);
		PregledDTO pregledDTO = new PregledDTO(stDTO);
		pregledDTO.setPacijentEmail(pacijentService.findByID(1L).getEmail());
		ResponseEntity<?> re = pregledController.noviPregledST(pregledDTO);
		System.out.println("+++++++++++++++++++++++++++++++++++++");
		System.out.println(re.getStatusCodeValue());
		System.out.println(re.toString());
		System.out.println(re.getBody());
		System.out.println(re.getHeaders());
		System.out.println(re.getStatusCode());
		System.out.println(re.getClass());
		assertThat(re.getBody()).isNotNull();
		assertThat(((PregledDTO)re.getBody()).getTermin()).isEqualTo(st.getTermin());
		assertThat(((PregledDTO)re.getBody()).getCena()).isEqualTo(st.getCena());
		assertThat(((PregledDTO)re.getBody()).getSalaID()).isEqualTo(st.getSala().getId());
		assertThat(((PregledDTO)re.getBody()).getTipPregledaID()).isEqualTo(st.getTipPregleda().getId());
		assertThat(((PregledDTO)re.getBody()).getKlinikaID()).isEqualTo(st.getKlinika().getId());
		assertThat(((PregledDTO)re.getBody()).getLekarID()).isEqualTo(st.getLekar().getId());
		assertThat(((PregledDTO)re.getBody()).getDatum()).isEqualTo(st.getDatum());
		
		System.out.println("+++++++++++++++++++++++++++++++++++++");
		
		
		
	}

	
	
}
