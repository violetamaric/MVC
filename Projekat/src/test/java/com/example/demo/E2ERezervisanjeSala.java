package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.web.Login;
import com.example.demo.web.PocetnaStrana;
import com.example.demo.web.PretragaKlinika;
import com.example.demo.web.ZakazivanjePregleda;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:test.properties")
public class E2ERezervisanjeSala {
	

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
//		pocetna = PageFactory.initElements(browser, PocetnaStrana.class);
//		zakazivanjePregleda = PageFactory.initElements(browser, ZakazivanjePregleda.class);
//		pretragaKlinika = PageFactory.initElements(browser, PretragaKlinika.class);

		login.ensureIsDisplayedTxtEmail();
		login.getTxtEmail().sendKeys("magdalena@gmail.com");
		login.ensureIsDisplayedTxtPass();
		login.getTxtPass().sendKeys("maga");
		login.ensureIsDisplayedBtnLogin();
		login.getBtnLogin().click();
	}
	
	@Test
	public void OdabirSale() throws InterruptedException {

//		pocetna.getStatsCardPregledi().click();
//		Thread.sleep(3000);
//		browser.navigate().to("http://localhost:3000/admink/listaZahtevaPregled");

//		Thread.sleep(1500);
//		zakazivanjePregleda.getOdabranUDPregled().click();
//
//		Thread.sleep(1000);
//		zakazivanjePregleda.getPotvrdiPregled().click();

	}
	
	

}
