package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.PacijentController;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.Pacijent;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.PacijentService;

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
        pDTO = pacijenti.get(pacijenti.size() - 1); //get last student
        assertThat(pDTO.getIme()).isEqualTo(DB_NOVO_IME);
        assertThat(pDTO.getPrezime()).isEqualTo(DB_NOVO_PREZIME);        
	}
	

}
