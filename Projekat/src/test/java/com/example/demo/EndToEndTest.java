package com.example.demo;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.web.Login;
import com.example.demo.web.PocetnaStrana;
import com.example.demo.web.PretragaKlinika;
import com.example.demo.web.ZakazivanjePregleda;

//Scenario: "Ja pacijent zelim da zakakazem unapred definisani pregled."
//
//	1. Ja pacijent zelim da vidim sve unapred definisane termine
//	2. Ja pacijent zelim da zakazem jedan unapred definisan termin
//	3. Ja pacijent zelim da vidim zakazani pregled
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:test.properties")
public class EndToEndTest {

	private WebDriver browser;

	private Login login;

	private PocetnaStrana pocetna;

	private ZakazivanjePregleda zakazivanjePregleda;

	private PretragaKlinika pretragaKlinika;

	@Before
	public void setUp() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		browser = new ChromeDriver();

		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:3000/login");

		login = PageFactory.initElements(browser, Login.class);
		pocetna = PageFactory.initElements(browser, PocetnaStrana.class);
		zakazivanjePregleda = PageFactory.initElements(browser, ZakazivanjePregleda.class);
		pretragaKlinika = PageFactory.initElements(browser, PretragaKlinika.class);

		login.ensureIsDisplayedTxtEmail();
		login.getTxtEmail().sendKeys("pera@gmail.com");
		login.ensureIsDisplayedTxtPass();
		login.getTxtPass().sendKeys("pera");
		login.ensureIsDisplayedBtnLogin();
		login.getBtnLogin().click();
	}

	@Test
	public void brzoZakazivanje() throws InterruptedException {

		pocetna.vidljivaKarticaBrzoZakazivanje();
		pocetna.getUnapredDef().click();
		Thread.sleep(1500);

		zakazivanjePregleda.vidljivoDugmeZakaziPregled();
		zakazivanjePregleda.getZakaziPregled().click();
		Thread.sleep(1500);

		zakazivanjePregleda.getOdabranPregled().click();
		Thread.sleep(1500);

		zakazivanjePregleda.getVidiTermine().click();
		Thread.sleep(1500);
		Select it = new Select(browser.findElement(By.xpath("//*[@id=\"izaberiTermin\"]")));
		List<WebElement> options = it.getOptions();

		WebElement defaultItem = options.get(1);

		defaultItem.click();
		Thread.sleep(1500);
		zakazivanjePregleda.getOdabranLekar().click();

		Thread.sleep(1000);
		zakazivanjePregleda.getPotvrdiPregled().click();

	}

	@Test
	public void ZakaziPregled() throws InterruptedException {
		pocetna.vidljivaKarticaBrzoZakazivanje();
		pocetna.getUnapredDef().click();
		Thread.sleep(1000);
		browser.navigate().to("http://localhost:3000/pacijent/brzoZakazivanje");

		Thread.sleep(1500);
		zakazivanjePregleda.getOdabranUDPregled().click();

		Thread.sleep(1000);
		zakazivanjePregleda.getPotvrdiPregled().click();

	}

	@Test
	public void SortirajKlinike() throws InterruptedException {
	
		pocetna.vidljivaKarticaBrzoZakazivanje();
		pocetna.getUnapredDef().click();
		Thread.sleep(1500);
		
		pretragaKlinika.getBtnOcena().click();
		Thread.sleep(1000);
		
		pretragaKlinika.getBtnPonistiFilter().click();
		Thread.sleep(1000);
		
		pretragaKlinika.getBtnPretragaKlinika().click();;
		pretragaKlinika.getPretraziPoljeKlinika().sendKeys("Novi Sad");
		Thread.sleep(1000);
		
		pretragaKlinika.getBtnTipPregleda().click();
		Select it = new Select(browser.findElement(By.id("selectTipPregleda")));
		List<WebElement> options = it.getOptions();

		WebElement defaultItem = options.get(1);

		defaultItem.click();
		Thread.sleep(1000);
		
		pretragaKlinika.getBtnPonistiFilter().click();
		Thread.sleep(1000);

//		pretragaKlinika.getOdabirDatuma().clear();
//		pretragaKlinika.getOdabirDatuma().sendKeys("12.03.2020.");
//		pretragaKlinika.getOdabirDatuma().click();
//		Thread.sleep(1000);
	
	}

//    @After
//    public void tearDown() {
//        browser.close();
//    }
//	

//	@Autowired
//	private SlobodniTerminController STController;
//	
//	@Autowired
//	private SlobodniTerminService STService;
//	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	@Autowired
//	private AuthorityRepository authRepository;
//
//	@Autowired
//	private PacijentService pacijentService;
//	
//	@Autowired
//	private PregledService pregledService;
//
//	@Autowired
//	private TokenUtils tokenUtils;
//
//	@Autowired
//	private AuthenticationManager authenticationManager;
//
//	@Autowired
//	private PregledController pregledController;
//
//	@Autowired
//	private KlinikaService klinikaService;
//	
//	@Autowired
//	private IzvestajOPregleduService IOPService;
//	
//	@Autowired
//	private LekarService lekarService;
//	
//	@Autowired
//	private TipPregledaService TPService;
//	
//	@Autowired
//	private SalaService salaService;
//
//	// pregled svih unapred definisanih termina
//	@Test
//	public void testFindAllUD() {
//
//		// preuzimanje svih unapred definisanih pregleda
//		List<SlobodniTerminDTO> termini = (STController.getAllUnapredDef()).getBody();
//		assertThat(termini).isNotNull();
//		
//	}
//
//	// pacijent zakazuje pregled iz liste unapred definisanih pregleda
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testZakazi() {
//
//		SlobodniTerminDTO st = (STController.getAllUnapredDef()).getBody().get(0);
//		PregledDTO pregledDTO = new PregledDTO(st);
//		List<SlobodniTerminDTO> termini = (STController.getAllUnapredDef()).getBody();
////		int pregledi = (pregledController.getAll()).getBody().size();
////		List<Pregled> pregledi2 = (pregledService.findAll());
////		assertThat(pregledi2).hasSize(pregledi2.size());
//		
//		System.out.println();
//		PacijentDTO pacijent2 = new PacijentDTO((pacijentService.findByID(1L)));
//		System.out.println("*****************pacijent 2" + pacijent2);
//		Pacijent pacijent = new Pacijent();
//		pacijent.setId(1L);
//		pacijent.setIme(pacijent2.getIme());
//		pacijent.setPrezime(pacijent2.getPrezime());
//		pacijent.setLbo(pacijent2.getLbo());
//		pacijent.setEmail(pacijent2.getEmail());
//		pacijent.setLozinka(pacijent2.getLozinka());
//		pacijent.setAdresa(pacijent2.getAdresa());
//		pacijent.setGrad(pacijent2.getGrad());
//		pacijent.setDrzava(pacijent2.getDrzava());
//		pacijent.setTelefon(pacijent2.getTelefon());
//		pacijent.setOdobrenaRegistracija(pacijent2.getOdobrenaRegistracija());
//		System.out.println("----------------pacijent 2" + pacijent2);
//
//		Authority a = new Authority();
//		a.setId(new Long(1L));
//		a.setName("PACIJENT");
//
//		System.out.println("????????????????????auth" + a);
//
//		String jwt = tokenUtils.tokenPacijent(pacijent, a);
//		assertNotNull(jwt);
//
//		System.out.println("1111111");
//		final Authentication authentication = authenticationManager
//
//				.authenticate(new UsernamePasswordAuthenticationToken(pacijent2.getEmail(),
//
//						"pera"));
//		System.out.println("1111111");
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		pregledDTO.setPacijentEmail(pacijent2.getEmail());
//		ResponseEntity<?> re = pregledController.noviPregledST(pregledDTO);
////		System.out.println(pregledi.size());
//		System.out.println();
//		System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
////		List<SlobodniTerminDTO> termini = (STController.getAllUnapredDef()).getBody();
//		
//		SlobodniTermin st2 = (STService.findOne(1L));
//		
//		assertThat(termini).hasSize(termini.size());
//		
//		//status kod 200 znaci da je email poslat uspesno
//		assertThat(re.getStatusCodeValue()).isEqualTo(200);
//		
//		assertThat(st2.isStatus()).isEqualTo(true);
//		assertThat(st2.getCena()).isEqualTo(((PregledDTO)re.getBody()).getCena());
//		assertThat(st2.getDatum()).isEqualTo(((PregledDTO)re.getBody()).getDatum());
//		assertThat(st2.getTermin()).isEqualTo(((PregledDTO)re.getBody()).getTermin());
//		assertThat(st2.getSala().getId()).isEqualTo(((PregledDTO)re.getBody()).getSalaID());
//		assertThat(st2.getLekar().getId()).isEqualTo(((PregledDTO)re.getBody()).getLekarID());
//		assertThat(st2.getKlinika().getId()).isEqualTo(((PregledDTO)re.getBody()).getKlinikaID());
//		assertThat(st2.getTipPregleda().getId()).isEqualTo(((PregledDTO)re.getBody()).getTipPregledaID());
//		assertThat(pacijent2.getEmail()).isEqualTo(((PregledDTO)re.getBody()).getPacijentEmail());
//		assertThat(((PregledDTO)re.getBody()).getStatus()).isEqualTo(1);
////		assertThat(st)
////		assertThat(pregledi2).hasSize(pregledi + 1);
//
//	}
//
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testProveri() {
//		Date date = new Date();
//		SlobodniTermin st = new SlobodniTermin();
//		st.setCena(1500);
//		st.setDatum(date);
//		st.setKlinika(klinikaService.findOne(1L));
//		st.setLekar(lekarService.findOne(1L));
//		st.setPopust(50);
//		st.setIzvestajOPregledu(IOPService.findById(1L));
//		st.setStatus(false);
//		st.setTermin(9);
//		st.setTipPregleda(TPService.findOne(1L));
//		st.setSala(salaService.findById(1L));
//		STService.save(st);
//		SlobodniTerminDTO stDTO = new SlobodniTerminDTO(st);
//		PregledDTO pregledDTO = new PregledDTO(stDTO);
//		pregledDTO.setPacijentEmail(pacijentService.findByID(1L).getEmail());
//		ResponseEntity<?> re = pregledController.noviPregledST(pregledDTO);
//		System.out.println("+++++++++++++++++++++++++++++++++++++");
//		System.out.println(re.getStatusCodeValue());
//		System.out.println(re.toString());
//		System.out.println(re.getBody());
//		System.out.println(re.getHeaders());
//		System.out.println(re.getStatusCode());
//		System.out.println(re.getClass());
//		assertThat(re.getBody()).isNotNull();
//		assertThat(((PregledDTO)re.getBody()).getTermin()).isEqualTo(st.getTermin());
//		assertThat(((PregledDTO)re.getBody()).getCena()).isEqualTo(st.getCena());
//		assertThat(((PregledDTO)re.getBody()).getSalaID()).isEqualTo(st.getSala().getId());
//		assertThat(((PregledDTO)re.getBody()).getTipPregledaID()).isEqualTo(st.getTipPregleda().getId());
//		assertThat(((PregledDTO)re.getBody()).getKlinikaID()).isEqualTo(st.getKlinika().getId());
//		assertThat(((PregledDTO)re.getBody()).getLekarID()).isEqualTo(st.getLekar().getId());
//		assertThat(((PregledDTO)re.getBody()).getDatum()).isEqualTo(st.getDatum());
//		
//		System.out.println("+++++++++++++++++++++++++++++++++++++");
//		
//		
//		
//	}

}
