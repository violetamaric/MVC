package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.controller.PacijentController;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.Pacijent;
import com.example.demo.service.PacijentService;


//import static com.example.demo.PacijentKonstante.DB_ID;
//import static com.example.demo.PacijentKonstante.DB_IME;
//import static com.example.demo.PacijentKonstante.DB_PREZIME;
//import static com.example.demo.PacijentKonstante.DB_KOL;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class PacijentControllerTest {
	
	@Autowired
	PacijentController pacijentController;
	

	@Autowired
	private PacijentService pacijentService;
	

	
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

	@Test
	public void testFindAll() {
		List<PacijentDTO> pacijenti = (pacijentController.getAll()).getBody();
		assertThat(pacijenti).hasSize(DB_KOL);
//		assertThat(pacijenti)
	}
	
	@Test
	public void testFindByLbo() {
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
    @Rollback(true) //it can be omitted because it is true by default
	public void testAdd() {
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
		pacijent.setOdobrenaRegistracija(false);
		
		int dbSizeBeforeAdd = (pacijentController.getAll()).getBody().size();
		
		Pacijent dbPacijent = pacijentService.save(pacijent);
		assertThat(dbPacijent).isNotNull();
				
		// Validate that new student is in the database
        List<PacijentDTO> pacijenti = (pacijentController.getAll()).getBody();
        assertThat(pacijenti).hasSize(dbSizeBeforeAdd + 1);
        PacijentDTO pDTO = new PacijentDTO(dbPacijent);
        pDTO = pacijenti.get(pacijenti.size() - 1); //get last student
        assertThat(pDTO.getIme()).isEqualTo(DB_NOVO_IME);
        assertThat(pDTO.getPrezime()).isEqualTo(DB_NOVO_PREZIME);        
	}
	
//	@Test
//	public void testGetAllStudents() throws Exception {
//		mockMvc.perform(get(URL_PREFIX + "/all")).andExpect(status().isOk())
//				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(DB_KOL)))
//				.andExpect(jsonPath("$.[*].id").value(hasItem(DB_ID.intValue())))
//				.andExpect(jsonPath("$.[*].ime").value(hasItem(DB_IME)))
//				.andExpect(jsonPath("$.[*].prezime").value(hasItem(DB_PREZIME)));
//	}

}
