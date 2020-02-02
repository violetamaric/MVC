package com.example.demo;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.controller.PacijentController;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.Authority;
import com.example.demo.model.Klinika;
import com.example.demo.model.Pacijent;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.PacijentService;
import static org.junit.Assert.assertEquals;
//import static com.example.demo.PacijentKonstante.DB_ID;
//import static com.example.demo.PacijentKonstante.DB_IME;
//import static com.example.demo.PacijentKonstante.DB_PREZIME;
//import static com.example.demo.PacijentKonstante.DB_KOL;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class PacijentControllerTest {
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	PacijentController pacijentController;
	

	@Autowired
	private PacijentService pacijentService;
	
	
	@Autowired
	private KlinikaService klinikaService;

	
	public static final Long DB_ID = 1L;
	public static final String DB_IME = "Pera";
	public static final String DB_PREZIME = "Peric";
	public static final String DB_NOVO_IME = "PETAR";
	public static final String DB_NOVO_PREZIME = "PETROVIC";
	public static final int DB_KOL = 5;

	private static final String URL_PREFIX = "/api/pacijenti";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	

	@Autowired
	private WebApplicationContext webApplicationContext;

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
//
//	@Before
//	public void setUp() throws Exception {
//		klinikaService.save(new Klinika("P1", "O1", "GG",1));
//		klinikaService.save(new Klinika("P2","O2", "CC",2));
//		klinikaService.save(new Klinika("P3","O3", "FF",3));
//		klinikaService.save(new Klinika("P4","O4", "DD",4));
//		klinikaService.save(new Klinika("P5","O4", "HH",5));
//	}
	@Test(expected = ObjectOptimisticLockingFailureException.class)
	public void testOptimisticLockingScenario() {

		Klinika klinikaForUserOne = klinikaService.findById(1L);
		Klinika klinikaForUserTwo = klinikaService.findById(1L);

		//modifikovanje istog objekta
		klinikaForUserOne.setOcena(10);
		klinikaForUserTwo.setOcena(9);

		//verzija oba objekta je 0
		assertEquals(0, klinikaForUserOne.getVersion().intValue());
		assertEquals(0, klinikaForUserTwo.getVersion().intValue());

		//pokusaj cuvanja prvog objekta
		klinikaService.save(klinikaForUserOne);

		//pokusaj cuvanja drugog objekta - Exception!
		klinikaService.save(klinikaForUserTwo);
	}
	@Test
    @Transactional
    @Rollback(true)
	public void testGetAllPacijente() throws Exception {
		
//		UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken("mmvvcc",
//
//				"mmvvcc");
		System.out.println("test get all pacijente");
		PacijentDTO pacijent2 = (PacijentDTO) (pacijentController.getPacijentByID(DB_ID)).getBody();
		System.out.println("*****************pacijent 2" + pacijent2);
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
		
		mockMvc.perform(get(URL_PREFIX + "/all").header("Authorization", jwt)).andExpect(status().isOk())
//		mockMvc.perform(get(URL_PREFIX + "/all")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(DB_KOL)))
				.andExpect(jsonPath("$.[*].id").value(hasItem(DB_ID.intValue())))
				.andExpect(jsonPath("$.[*].ime").value(hasItem(DB_IME)))
				.andExpect(jsonPath("$.[*].prezime").value(hasItem(DB_PREZIME)));
	}

	
	
}
