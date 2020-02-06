package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.example.demo.controller.PacijentController;
import com.example.demo.controller.PregledController;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.model.Authority;
import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.Sala;
import com.example.demo.model.SlobodniTermin;
import com.example.demo.model.TipPregleda;
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

//import static com.example.demo.PacijentKonstante.DB_ID;
//import static com.example.demo.PacijentKonstante.DB_IME;
//import static com.example.demo.PacijentKonstante.DB_PREZIME;
//import static com.example.demo.PacijentKonstante.DB_KOL;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class PacijentControllerJUnit {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PacijentController pacijentController;

	@Autowired
	private PregledController pregledController;

	@Autowired
	private IzvestajOPregleduService IOPService;

	@Autowired
	private PacijentService pacijentService;

	@Autowired
	private PregledService pregledService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SlobodniTerminService STService;

	@Autowired
	private AuthorityRepository authRepository;

	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private SalaService salaService;
	
	@Autowired
	private KlinikaService klinikaService;
	
	@Autowired
	private TipPregledaService TPService;
	

	public static final Long DB_ID = 1L;
	public static final String DB_IME = "Pera";
	public static final String DB_PREZIME = "Peric";
	public static final String DB_NOVO_IME = "PETAR";
	public static final String DB_NOVO_PREZIME = "PETROVIC";
	public static final int DB_KOL = 5;
	private static final String URL_PREFIX = "/api/pacijenti";

	@Test
	public void testFindAll() {
//		Authority a = new Authority();
//		a.setId(new Long(1L));
//		a.setName(new String("PACIJENT"));
//
//		System.out.println("????????????????????auth" + a);
//		
//		String jwt = tokenUtils.tokenPacijent(pacijent, a);
//		assertNotNull(jwt);
//		
//		System.out.println("1111111");
//	    final Authentication authentication = authenticationManager
//
//				.authenticate(new UsernamePasswordAuthenticationToken(pacijent2.getEmail(),
//
//						"pera"));
//		System.out.println("1111111");
//		SecurityContextHolder.getContext().setAuthentication(authentication);

		List<PacijentDTO> pacijenti = (pacijentController.getAll()).getBody();
		assertThat(pacijenti).hasSize(DB_KOL);
//		assertThat(pacijenti)
	}

	@Test
	public void testFindByID() {
		PacijentDTO pacijent = (PacijentDTO) (pacijentController.getPacijentByID(DB_ID)).getBody();
		assertThat(pacijent).hasFieldOrProperty("email");
		assertThat(pacijent).isNotNull();
		assertThat(pacijent.getId()).isEqualTo(DB_ID);
		assertThat(pacijent.getIme()).isEqualTo(DB_IME);
		assertThat(pacijent.getPrezime()).isEqualTo(DB_PREZIME);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdatePacijent() {
		PacijentDTO pacijent2 = (PacijentDTO) (pacijentController.getPacijentByID(DB_ID)).getBody();
		Pacijent pacijent = new Pacijent();
		pacijent.setId(DB_ID);
		pacijent.setIme(DB_NOVO_IME);
		pacijent.setPrezime(DB_NOVO_PREZIME);
		pacijent.setLbo(pacijent2.getLbo());
		pacijent.setEmail(pacijent2.getEmail());
		pacijent.setLozinka(pacijent2.getLozinka());
		pacijent.setAdresa(pacijent2.getAdresa());
		pacijent.setGrad(pacijent2.getGrad());
		pacijent.setDrzava(pacijent2.getDrzava());
		pacijent.setTelefon(pacijent2.getTelefon());
		pacijent.setOdobrenaRegistracija(pacijent2.getOdobrenaRegistracija());

		pacijent = pacijentService.save(pacijent);
		assertThat(pacijent).isNotNull();

		PacijentDTO pacijent3 = (PacijentDTO) (pacijentController.getPacijentByID(DB_ID)).getBody();
		assertThat(pacijent3.getIme()).isEqualTo(DB_NOVO_IME);
		assertThat(pacijent3.getPrezime()).isEqualTo(DB_NOVO_PREZIME);
		assertThat(pacijent3.getId()).isEqualTo(DB_ID);
		assertThat(pacijent3).hasFieldOrProperty("email");
		assertThat(pacijent3).isNotNull();
	}

	@Test
	@Transactional
	@Rollback(true) // it can be omitted because it is true by default
	public void testAddPacijent() {
		Pacijent pacijent = new Pacijent();
		pacijent.setIme(DB_NOVO_IME);
		pacijent.setPrezime(DB_NOVO_PREZIME);
		pacijent.setLbo("101");
		pacijent.setEmail("test@gmail.com");
		pacijent.setLozinka("test");
		pacijent.setAdresa("Temerinska 4");
		pacijent.setGrad("Novi Sad");
		pacijent.setDrzava("Srbija");
		pacijent.setTelefon("060789654");
		pacijent.setJmbg("0303966811711");
		pacijent.setOdobrenaRegistracija(false);
//		
//		Authority a = new Authority();
//		a.setId(new Long(1L));
//		a.setName(new String("PACIJENT"));
//
//		System.out.println("????????????????????auth" + a);
//		
//		String jwt = tokenUtils.tokenPacijent(pacijent, a);
//		assertNotNull(jwt);
//		
//		System.out.println("1111111");
//	    final Authentication authentication = authenticationManager
//
//				.authenticate(new UsernamePasswordAuthenticationToken(pacijent2.getEmail(),
//
//						pacijent2.getLozinka()));
//		System.out.println("1111111");
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		
		int dbSizeBeforeAdd = (pacijentController.getAll()).getBody().size();

		Pacijent dbPacijent = pacijentService.save(pacijent);
		assertThat(dbPacijent).isNotNull();

		// Validate that new student is in the database
		List<PacijentDTO> pacijenti = (pacijentController.getAll()).getBody();
		assertThat(pacijenti).hasSize(dbSizeBeforeAdd + 1);
		PacijentDTO pDTO = new PacijentDTO(dbPacijent);
		pDTO = pacijenti.get(pacijenti.size() - 1); // get last student
		assertThat(pDTO.getIme()).isEqualTo(DB_NOVO_IME);
		assertThat(pDTO.getPrezime()).isEqualTo(DB_NOVO_PREZIME);
	}

	@Test
	@Transactional
//	@Rollback(true)
	public void testAddPregled() {
		Pregled pregled = new Pregled();
		pregled.setDatum(new Date());
		pregled.setCena(1500);
		pregled.setStatus(0);
		pregled.setTermin(9);


		

		Pacijent pacijent = new Pacijent();
		pacijent.setIme(DB_NOVO_IME);
		pacijent.setPrezime(DB_NOVO_PREZIME);
		pacijent.setLbo("101");
		pacijent.setEmail("test@gmail.com");
		pacijent.setLozinka(passwordEncoder.encode("test"));
		pacijent.setAdresa("Temerinska 4");
		pacijent.setGrad("Novi Sad");
		pacijent.setDrzava("Srbija");
		pacijent.setTelefon("060789654");
		pacijent.setJmbg("0303966811711");
		pacijent.setOdobrenaRegistracija(false);

		pregled.setLekar(lekarService.findOne(1L));

		IzvestajOPregledu izvestajOPregledu = IOPService.findById(1L);
		pregled.setKlinika(klinikaService.findOne(1L));
		pregled.setPacijent(pacijent);
		pregled.setSala(salaService.findOne(1L));
		pregled.setIzvestajOPregledu(IOPService.findById(1L));
		pregled.setTipPregleda(TPService.findOne(1L));
		
		Authority a = authRepository.findByUloga("PACIJENT");
//		a.setId(new Long(1L));
//		a.setName(new String("PACIJENT"));
		Set<Authority> auth = new HashSet<Authority>();
		auth.add(a);
		pacijent.setAuthorities(auth);

		Pacijent pacijent2 = pacijentService.save(pacijent);
		System.out.println("????????????????????auth" + a);

		String jwt = tokenUtils.tokenPacijent(pacijent, a);
		assertNotNull(jwt);

		System.out.println("1111111");
		final Authentication authentication = authenticationManager

				.authenticate(new UsernamePasswordAuthenticationToken(pacijent.getEmail(),

						"test"));
		System.out.println("1111111");
		SecurityContextHolder.getContext().setAuthentication(authentication);

		int listaSizeBefore = klinikaService.listaPregledaKlinike(klinikaService.findOne(1L).getId()).size();
		
		int dbSizeBeforeAdd = (pregledController.getAll()).getBody().size();
		ResponseEntity<PregledDTO> pdto = (ResponseEntity<PregledDTO>) pregledController
				.noviPregled(new PregledDTO(pregled));
//		Pregled dbPregled = pregledService.save(pregled);
//		assertThat(pdto.getBody()).isNotNull();
		

		List<PregledDTO> pregledi = (pregledController.getAll()).getBody();
		System.out.println(pregledi.size());
		System.out.println(pdto.getBody());
		List<Pregled>listaPregledaKlinike = klinikaService.listaPregledaKlinike(klinikaService.findOne(1L).getId());
		List<PregledDTO>listaPregledaKlinikeDTO = new ArrayList<PregledDTO>();
		for(Pregled pregledP:listaPregledaKlinike) {
			listaPregledaKlinikeDTO.add(new PregledDTO(pregledP));
		}
		System.out.println(new Long(pregledi.size()));
//		Pregled p = pregledService.findById(new Long(pregledi.size()));
		assertThat(pregledi).hasSize(dbSizeBeforeAdd + 1);
		PregledDTO pDTO = new PregledDTO(pregled);
		pDTO = pregledi.get(pregledi.size() - 1); // get last student
		assertThat(pDTO.getCena()).isEqualTo(1500);
		assertThat(pDTO.getStatus()).isEqualTo(0);
		assertThat(pDTO.getTermin()).isEqualTo(9);
//		assertThat(listaPregledaKlinikeDTO).contains(pregledi.get(pregledi.size() - 1 ));
//		assertThat(listaPregledaKlinikeDTO.get(pregledi.size() - 1 )).isEqualTo(pregledi.get(pregledi.size() - 1 ));
		assertThat(listaSizeBefore).isEqualTo(listaPregledaKlinike.size() - 1 );
		System.out.println(listaPregledaKlinikeDTO.get(listaSizeBefore));
		System.out.println(pregledi.get(pregledi.size()-1));
		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getCena()).isEqualTo(pregledi.get(pregledi.size()-1).getCena());
		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getStatus()).isEqualTo(pregledi.get(pregledi.size()-1).getStatus());
		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getKlinikaID()).isEqualTo(pregledi.get(pregledi.size()-1).getKlinikaID());
		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getDatum()).isEqualTo(pregledi.get(pregledi.size()-1).getDatum());
		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getPacijentEmail()).isEqualTo(pregledi.get(pregledi.size()-1).getPacijentEmail());
		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getLekarID()).isEqualTo(pregledi.get(pregledi.size()-1).getLekarID());
		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getTipPregledaID()).isEqualTo(pregledi.get(pregledi.size()-1).getTipPregledaID());
		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getId()).isEqualTo(pregledi.get(pregledi.size()-1).getId());
	}

	@Test
	@Transactional
//	@Rollback(true)
	public void testAddUDPregled() {
		Pacijent pacijent = new Pacijent();
		pacijent.setIme(DB_NOVO_IME);
		pacijent.setPrezime(DB_NOVO_PREZIME);
		pacijent.setLbo("101");
		pacijent.setEmail("test@gmail.com");
		pacijent.setLozinka(passwordEncoder.encode("test"));
		pacijent.setAdresa("Temerinska 4");
		pacijent.setGrad("Novi Sad");
		pacijent.setDrzava("Srbija");
		pacijent.setTelefon("060789654");
		pacijent.setJmbg("0303966811711");
		pacijent.setOdobrenaRegistracija(false);

		Authority a = authRepository.findByUloga("PACIJENT");
//		a.setId(new Long(1L));
//		a.setName(new String("PACIJENT"));
		Set<Authority> auth = new HashSet<Authority>();
		auth.add(a);
		pacijent.setAuthorities(auth);

		Pacijent pacijent2 = pacijentService.save(pacijent);
		System.out.println("????????????????????auth" + a);

		String jwt = tokenUtils.tokenPacijent(pacijent, a);
		assertNotNull(jwt);

		System.out.println("1111111");
		final Authentication authentication = authenticationManager

				.authenticate(new UsernamePasswordAuthenticationToken(pacijent.getEmail(),

						"test"));
		System.out.println("1111111");
		SecurityContextHolder.getContext().setAuthentication(authentication);

		SlobodniTermin st = STService.findOne(1L);
		double cena = st.getCena();
		Date datum = st.getDatum();
		Lekar lekar = st.getLekar();
		Klinika klinika = st.getKlinika();
		TipPregleda tipPregleda = st.getTipPregleda();
		Sala sala = st.getSala();

		Pregled pregled = new Pregled();
		pregled.setDatum(datum);
		pregled.setCena(cena);
		pregled.setStatus(1);
		pregled.setTermin(9);
		pregled.setLekar(lekar);

		IzvestajOPregledu izvestajOPregledu = IOPService.findById(1L);
		pregled.setKlinika(klinika);
		pregled.setPacijent(pacijent2);
		pregled.setSala(sala);
		pregled.setIzvestajOPregledu(izvestajOPregledu);
		pregled.setTipPregleda(tipPregleda);
		System.out.println();
		System.out.println("//////////////////////////////////////////");

		int listaSizeBefore = klinikaService.listaPregledaKlinike(klinikaService.findOne(1L).getId()).size();
//		int dbSizeBeforeAddST = (STService.findAll()).size();
		ResponseEntity<PregledDTO> pdto = (ResponseEntity<PregledDTO>) pregledController
				.noviPregledST(new PregledDTO(pregled));
//		int dbSizeBeforeAdd = (pregledController.getAll()).getBody().size();

//		Pregled dbPregled = pregledService.save(pregled);
		
//		assertThat(pdto.getBody()).isNotNull();

		// Validate that new student is in the database
		System.out.println("////*********///////////***********/////////");
//		List<PregledDTO> pregledi = (pregledController.getAll()).getBody();

//		List<SlobodniTermin> listaST = STService.findAll();

//		List<PregledDTO> pregledi2 = (pregledController.getAll()).getBody();

//		assertThat(pregledi).hasSize(dbSizeBeforeAdd + 1);
//        assertThat(listaST).hasSize(dbSizeBeforeAddST - 1);
//		List<Pregled> preglediH = (pregledService.findAll());
		
//		List<Pregled>listaPregledaKlinike = klinikaService.listaPregledaKlinike(klinikaService.findOne(1L).getId());
//		List<PregledDTO>listaPregledaKlinikeDTO = new ArrayList<PregledDTO>();
//		for(Pregled pregledP:listaPregledaKlinike) {
//			listaPregledaKlinikeDTO.add(new PregledDTO(pregledP));
//		}
		
		PregledDTO pDTO = new PregledDTO(pregled);
//		pDTO = pregledi.get(pregledi.size() - 1);
		assertThat(pDTO.getDatum()).isEqualTo(datum);
		assertThat(pDTO.getCena()).isEqualTo(cena);
		assertThat(pDTO.getStatus()).isEqualTo(1);
		assertThat(pDTO.getTermin()).isEqualTo(9);
		assertThat(pDTO.getCena()).isEqualTo(st.getCena());
		assertThat(pDTO.getCena()).isEqualTo(st.getCena());
		
//		assertThat(listaSizeBefore).isEqualTo(listaPregledaKlinike.size() - 1 );
//		System.out.println(listaPregledaKlinikeDTO.get(listaSizeBefore));
//		System.out.println(preglediH.get(preglediH.size()-1));
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getCena()).isEqualTo(pregled.getCena());
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getStatus()).isEqualTo(pregled.getStatus());
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getKlinikaID()).isEqualTo(pregled.getKlinika().getId());
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getDatum()).isEqualTo(pregled.getDatum());
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getPacijentEmail()).isEqualTo(pregled.getPacijent().getEmail());
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getLekarID()).isEqualTo(pregled.getLekar().getId());
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getTipPregledaID()).isEqualTo(pregled.getTipPregleda().getId());
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getId()).isEqualTo(pregled.getId());
		
	}

	
}
