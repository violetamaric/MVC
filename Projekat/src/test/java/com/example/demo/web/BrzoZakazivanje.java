package com.example.demo.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrzoZakazivanje {

	
	   private WebDriver driver;

	    @FindBy(xpath = "//*[@id=\"brzoZakazivanje\"]")
	    private WebElement brzoZakazivanje;



	    public BrzoZakazivanje() {

	    }

	    public BrzoZakazivanje(WebDriver driver) {
	        this.driver = driver;
	    }

	    public WebElement getUnapredDef() {
	        return brzoZakazivanje;
	    }



	    public void vidljivaKarticaBrzoZakazivanje() {
	        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("brzoZakazivanje")));
	    }

	
}
