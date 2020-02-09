package com.example.demo.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ZakazivanjePregleda {

	   private WebDriver driver;

	    @FindBy(xpath = "//*[@id=\"zakaziPregled\"]")
	    private WebElement zakaziPregled;

	    @FindBy(xpath = "//*[@id=\"odabranLekar0\"]")
	    private WebElement odabranPregled;
	    
	    @FindBy(xpath = "//*[@id=\"odabranPregled0\"]")
	    private WebElement odabranUDPregled;

	    @FindBy(xpath = "//*[@id=\"vidiTermine0\"]")
	    private WebElement vidiTermine;
	    
	    @FindBy(xpath = "//*[@id=\"btnOdabranLekar\"]")
	    private WebElement odabranLekar;
	    
	    @FindBy(xpath = "//*[@id=\"potvrdiPregled\"]")
	    private WebElement potvrdiPregled;
	  
	    private Select izaberiTermin;
	    
	    public ZakazivanjePregleda() {
//	    	this.izaberiTermin = new Select(driver.findElement(By.id("izaberiTermin")));
	    }

	    public ZakazivanjePregleda(WebDriver driver) {
	        this.driver = driver;
	    }

	    public WebElement getZakaziPregled() {
	        return zakaziPregled;
	    }

	    
	    public WebElement getVidiTermine() {
			return vidiTermine;
		}

		public Select getIzaberiTermin() {
			return izaberiTermin;
		}
		
		
		
		public WebElement getOdabranUDPregled() {
			return odabranUDPregled;
		}

		public WebElement getPotvrdiPregled() {
			return potvrdiPregled;
		}

		public WebElement getOdabranLekar() {
			return odabranLekar;
		}

		public void setOdabranLekar(WebElement odabranLekar) {
			this.odabranLekar = odabranLekar;
		}

		public WebElement getOdabranPregled() {
	        return odabranPregled;
	    }

	    public void vidljivoDugmeZakaziPregled() {
	        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("zakaziPregled")));
	    }
	    

	
	
	
}
