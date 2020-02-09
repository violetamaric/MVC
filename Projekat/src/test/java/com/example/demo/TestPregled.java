package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.PacijentController;
import com.example.demo.controller.PregledController;
import com.example.demo.dto.PregledDTO;
import com.example.demo.model.Authority;
import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.PregledRepository;
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
public class TestPregled {

	public static final Long DB_ID = 1L;
	public static final String DB_IME = "Pera";
	public static final String DB_PREZIME = "Peric";
	public static final String DB_NOVO_IME = "PETAR";
	public static final String DB_NOVO_PREZIME = "PETROVIC";
	public static final int DB_KOL = 5;
	private static final String URL_PREFIX = "/api/pacijenti";

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

	@Autowired
	private PregledRepository pregledRepository;

//	// metode
//	@Test
//	@Transactional
////	@Rollback(true)
//	public void testAddPregled() {
//		Pregled pregled = new Pregled();
//		pregled.setDatum(new Date());
////		pregled.setCena(1500);
//		pregled.setStatus(0);
//		pregled.setTermin(9);
//		Pacijent pacijent = new Pacijent();
//		pacijent.setIme(DB_NOVO_IME);
//		pacijent.setPrezime(DB_NOVO_PREZIME);
//		pacijent.setLbo("101");
//		pacijent.setEmail("test@gmail.com");
//		pacijent.setLozinka(passwordEncoder.encode("test"));
//		pacijent.setAdresa("Temerinska 4");
//		pacijent.setGrad("Novi Sad");
//		pacijent.setDrzava("Srbija");
//		pacijent.setTelefon("060789654");
//		pacijent.setJmbg("0303966811711");
//		pacijent.setOdobrenaRegistracija(2);
//
//		pregled.setLekar(lekarService.findOne(1L));
//
//		IzvestajOPregledu izvestajOPregledu = IOPService.findById(1L);
//		pregled.setKlinika(klinikaService.findOne(1L));
//		pregled.setPacijent(pacijentService.findByID(1L));
//		pregled.setSala(salaService.findOne(1L));
//		pregled.setIzvestajOPregledu(IOPService.findById(1L));
//		pregled.setTipPregleda(TPService.findOne(1L));
//
//		Authority a = authRepository.findByUloga("PACIJENT");
//		Set<Authority> auth = new HashSet<Authority>();
//		auth.add(a);
//		pacijent.setAuthorities(auth);
//
//		Pacijent pacijent2 = pacijentService.save(pacijent);
//		System.out.println("????????????????????auth" + a);
//
//		String jwt = tokenUtils.tokenPacijent(pacijent, a);
//		assertNotNull(jwt);
//
//		System.out.println("1111111");
//		final Authentication authentication = authenticationManager
//
//				.authenticate(new UsernamePasswordAuthenticationToken(pacijent.getEmail(),
//
//						"test"));
//		System.out.println("1111111");
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		int listaSizeBefore = klinikaService.listaPregledaKlinike(klinikaService.findOne(1L).getId()).size();
//
//		int dbSizeBeforeAdd = (pregledController.getAll()).getBody().size();
//		ResponseEntity<?> re = pregledController.noviPregled(new PregledDTO(pregled));
//
//		List<PregledDTO> pregledi = (pregledController.getAll()).getBody();
//
//		List<Pregled> listaPregledaKlinike = klinikaService.listaPregledaKlinike(klinikaService.findOne(1L).getId());
//		List<PregledDTO> listaPregledaKlinikeDTO = new ArrayList<PregledDTO>();
//		for (Pregled pregledP : listaPregledaKlinike) {
//			listaPregledaKlinikeDTO.add(new PregledDTO(pregledP));
//		}
//		assertThat(pregledi).hasSize(dbSizeBeforeAdd + 1);
//		PregledDTO pDTO = new PregledDTO(pregled);
//		pDTO = pregledi.get(pregledi.size() - 1);
//		assertThat(((PregledDTO) re.getBody()).getStatus()).isEqualTo(0);
//		assertThat(((PregledDTO) re.getBody()).getTermin()).isEqualTo(9);
////		assertThat(listaSizeBefore).isEqualTo(listaPregledaKlinike.size() - 1);
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getCena())
//				.isEqualTo(pregledi.get(pregledi.size() - 1).getCena());
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getStatus())
//				.isEqualTo(pregledi.get(pregledi.size() - 1).getStatus());
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getKlinikaID())
//				.isEqualTo(pregledi.get(pregledi.size() - 1).getKlinikaID());
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getDatum())
//				.isEqualTo(pregledi.get(pregledi.size() - 1).getDatum());
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getPacijentEmail())
//				.isEqualTo(pregledi.get(pregledi.size() - 1).getPacijentEmail());
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getLekarID())
//				.isEqualTo(pregledi.get(pregledi.size() - 1).getLekarID());
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getTipPregledaID())
//				.isEqualTo(pregledi.get(pregledi.size() - 1).getTipPregledaID());
//		assertThat(listaPregledaKlinikeDTO.get(listaSizeBefore).getId())
//				.isEqualTo(pregledi.get(pregledi.size() - 1).getId());
//	}

//	@Test
//	@Transactional
////	@Rollback(true)
//	public void testAddUDPregled() {
//		Pacijent pacijent = new Pacijent();
//		pacijent.setIme(DB_NOVO_IME);
//		pacijent.setPrezime(DB_NOVO_PREZIME);
//		pacijent.setLbo("101");
//		pacijent.setEmail("test@gmail.com");
//		pacijent.setLozinka(passwordEncoder.encode("test"));
//		pacijent.setAdresa("Temerinska 4");
//		pacijent.setGrad("Novi Sad");
//		pacijent.setDrzava("Srbija");
//		pacijent.setTelefon("060789654");
//		pacijent.setJmbg("0303966811711");
//		pacijent.setOdobrenaRegistracija(2);
//
//		Authority a = authRepository.findByUloga("PACIJENT");
//		Set<Authority> auth = new HashSet<Authority>();
//		auth.add(a);
//		pacijent.setAuthorities(auth);
//
//		Pacijent pacijent2 = pacijentService.save(pacijent);
//
//		String jwt = tokenUtils.tokenPacijent(pacijent, a);
//		assertNotNull(jwt);
//		final Authentication authentication = authenticationManager
//
//				.authenticate(new UsernamePasswordAuthenticationToken(pacijent.getEmail(),
//
//						"test"));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		SlobodniTermin st = STService.findOne(1L);
//		double cena = st.getCena();
//		Date datum = st.getDatum();
//		Lekar lekar = st.getLekar();
//		Klinika klinika = st.getKlinika();
//		TipPregleda tipPregleda = st.getTipPregleda();
//		Sala sala = st.getSala();
//
//		Pregled pregled = new Pregled();
//		pregled.setDatum(datum);
//		pregled.setCena(cena);
//		pregled.setStatus(1);
//		pregled.setTermin(9);
//		pregled.setLekar(lekar);
//
//		IzvestajOPregledu izvestajOPregledu = IOPService.findById(1L);
//		pregled.setKlinika(klinika);
//		pregled.setPacijent(pacijent2);
//		pregled.setSala(sala);
//		pregled.setIzvestajOPregledu(izvestajOPregledu);
//		pregled.setTipPregleda(tipPregleda);
//
//		int listaSizeBefore = klinikaService.listaPregledaKlinike(klinikaService.findOne(1L).getId()).size();
//
//		ResponseEntity<?> re = pregledController
//				.noviPregledST(new PregledDTO(pregled));
//	
//		PregledDTO pDTO = new PregledDTO(pregled);
//		assertThat(re.getStatusCodeValue()).isEqualTo(200);
//		assertThat(((PregledDTO)re.getBody()).getDatum()).isEqualTo(datum);
//		assertThat(((PregledDTO)re.getBody()).getCena()).isEqualTo(cena);
//		assertThat(((PregledDTO)re.getBody()).getStatus()).isEqualTo(1);
//		assertThat(((PregledDTO)re.getBody()).getTermin()).isEqualTo(9);
//		assertThat(((PregledDTO)re.getBody()).getCena()).isEqualTo(st.getCena());
//		
//	}

	@Test
	@Transactional
//	@Rollback(true)
	public void testPotvrdaPregleda() {

		List<Pregled> listaP = pregledService.findAll();
		for (Pregled p : listaP) {
			if (p.getStatus() == 0 && p.getSala() != null) {
				ResponseEntity<?> re = pregledController.potvrdiPregled(p.getId());

				assertThat(re.getStatusCodeValue()).isEqualTo(200);
				assertThat(((PregledDTO) re.getBody()).getStatus()).isEqualTo(1);
				assertThat(((PregledDTO) re.getBody()).getId()).isEqualTo(p.getId());
				assertThat(((PregledDTO) re.getBody()).getPacijentEmail()).isEqualTo(p.getPacijent().getEmail());
				assertThat(((PregledDTO) re.getBody()).getPacijentID()).isEqualTo(p.getPacijent().getId());
				assertThat(((PregledDTO) re.getBody()).getKlinikaID()).isEqualTo(p.getKlinika().getId());
				assertThat(((PregledDTO) re.getBody()).getLekarID()).isEqualTo(p.getLekar().getId());
				assertThat(((PregledDTO) re.getBody()).getSalaID()).isEqualTo(p.getSala().getId());
				assertThat(((PregledDTO) re.getBody()).getCena()).isEqualTo(p.getCena());
				assertThat(((PregledDTO) re.getBody()).getTermin()).isEqualTo(p.getTermin());
				assertThat(((PregledDTO) re.getBody()).getDatum()).isEqualTo(p.getDatum());
				assertThat(((PregledDTO) re.getBody()).getTipPregledaID()).isEqualTo(p.getTipPregleda().getId());

				break;
			}
		}

	}

	@Test
	@Transactional
//	@Rollback(true)
	public void testOdbijenjePregleda() {

		List<Pregled> listaP = pregledService.findAll();
		for (Pregled p : listaP) {
			if (p.getStatus() == 0 && p.getSala() != null) {
				ResponseEntity<?> re = pregledController.odbijPregled(p.getId());

				assertThat(re.getStatusCodeValue()).isEqualTo(200);
				assertThat(((PregledDTO) re.getBody()).getStatus()).isEqualTo(2);
				assertThat(((PregledDTO) re.getBody()).getId()).isEqualTo(p.getId());
				assertThat(((PregledDTO) re.getBody()).getPacijentEmail()).isEqualTo(p.getPacijent().getEmail());
				assertThat(((PregledDTO) re.getBody()).getPacijentID()).isEqualTo(p.getPacijent().getId());
				assertThat(((PregledDTO) re.getBody()).getKlinikaID()).isEqualTo(p.getKlinika().getId());
				assertThat(((PregledDTO) re.getBody()).getLekarID()).isEqualTo(p.getLekar().getId());
				assertThat(((PregledDTO) re.getBody()).getSalaID()).isEqualTo(p.getSala().getId());
				assertThat(((PregledDTO) re.getBody()).getCena()).isEqualTo(p.getCena());
				assertThat(((PregledDTO) re.getBody()).getTermin()).isEqualTo(p.getTermin());
				assertThat(((PregledDTO) re.getBody()).getDatum()).isEqualTo(p.getDatum());
				assertThat(((PregledDTO) re.getBody()).getTipPregledaID()).isEqualTo(p.getTipPregleda().getId());

				break;
			}
		}

	}

	@Test
	@Transactional
//	@Rollback(true)
	public void testFindOne() {

		Optional<Pregled> p = pregledRepository.findById(1L);
		assertThat(p.getClass()).hasSameClassAs(Pregled.class);

	}
	


}
