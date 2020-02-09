package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.PregledController;
import com.example.demo.controller.SalaController;
import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.SalaDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserTokenState;
import com.example.demo.service.SalaService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class TestSale {

	@Autowired
	private SalaController salaController;

	@Autowired
	private SalaService salaService;

	@Autowired
	private PregledController pregledController;

//	@MockBean
//	private KlinikaService klinikaService;

	@Autowired
	private TestRestTemplate restTemplate;

	// JWT token za pristup REST servisima. Bice dobijen pri logovanju
	private String accessToken;

	// pre izvrsavanja testa, prijava da bismo dobili token
	@Before
	public void login() {
		ResponseEntity<?> responseEntity = restTemplate.postForEntity("/api/korisnici/login",
				new UserDTO("magdalena@gmail.com", "maga"), UserTokenState.class);
//		// preuzmemo token jer ce nam trebati za testiranje REST kontrolera
//		accessToken = ((UserTokenState) responseEntity.getBody()).getAccessToken();
		accessToken = "Bearer " +  ((UserTokenState) responseEntity.getBody()).getAccessToken();
//		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
//		//Add the Jackson Message converter
//		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//
//		// Note: here we are making this converter to process any kind of response, 
//		// not only application/*json, which is the default behaviour
//		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));        
//		messageConverters.add(converter);  
//		restTemplate.getRestTemplate().setMessageConverters(messageConverters);
//		
	
	}

	// Unit testovi
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAll() {

		// postavimo JWT token u zaglavlje zahteva da bi bilo dozvoljeno da pozovemo
		// funkcionalnost
		HttpHeaders headers = new HttpHeaders();
//    	headers.add("X-Auth-Token", accessToken);
		headers.set("Authorization", accessToken);
		// kreiramo objekat koji saljemo u sklopu zahteva
		// objekat nema telo, vec samo zaglavlje, jer je rec o GET zahtevu
//		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
		// posaljemo zahtev koji ima i zaglavlje u kojem je JWT token
		ResponseEntity<?> re = restTemplate.exchange("/api/sale/all", HttpMethod.GET,
				httpEntity, SalaDTO[].class);
//
		SalaDTO[] sale = (SalaDTO[]) re.getBody();
//		ResponseEntity<?> re = salaController.getAll();

		assertEquals(HttpStatus.OK, re.getStatusCode());
//		assertThat(re.getBody()).isNotNull();
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetSaleKlinike() {

		ResponseEntity<?> re = salaController.getSaleKlinike(1L);

		assertThat(re.getStatusCodeValue()).isEqualTo(200);
		assertThat(re.getBody()).isNotNull();
		assertThat((List<SalaDTO>) re.getBody()).hasAtLeastOneElementOfType(SalaDTO.class);

	}

	@Test(expected = NullPointerException.class)
	@Transactional
	@Rollback(true)
	public void testGetSaleKlinikeException() {

		ResponseEntity<?> re = salaController.getSaleKlinike(5L);

		assertThat(re.getStatusCodeValue()).isEqualTo(200);
		assertThat(re.getBody()).isNotNull();

	}

	// integracioni testovi
	@Test
	@Transactional
	@Rollback(true)
	public void getSaleTermini() {

		ResponseEntity<?> re = pregledController.getSaleTermin(1L);

		assertThat(re.getStatusCodeValue()).isEqualTo(200);
		assertThat(re.getBody()).isNotNull();
		assertThat((List<SalaDTO>) re.getBody()).hasAtLeastOneElementOfType(SalaDTO.class);

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSlobodanLekarException() {

		ResponseEntity<?> re = pregledController.slobodanLekar((pregledController.getPregled(1L)).getBody());

		assertThat(re.getStatusCodeValue()).isEqualTo(200);
		assertThat((List<LekarDTO>) re.getBody()).hasOnlyElementsOfType(LekarDTO.class);

	}

//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testRezervisanjeSale() {
//
//		ResponseEntity<?> re = pregledController.rezervisanjeSale((pregledController.getPregled(1L)).getBody());
//
//		assertThat(re.getStatusCodeValue()).isEqualTo(200);
//		assertThat(re.getBody()).isNotNull();
//		assertThat(re.getBody()).isEqualTo("Uspesno rezervisana sala!");
//
//	}

	@Test(expected = NullPointerException.class)
	@Transactional
	@Rollback(true)
	public void testRezervisanjeSaleException() {

		ResponseEntity<?> re = pregledController.rezervisanjeSale((pregledController.getPregled(50L)).getBody());

		assertThat(re.getStatusCodeValue()).isNotEqualTo(200);

	}

}
