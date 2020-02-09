package com.example.demo.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OceniKliniku {

	private WebDriver driver;

	@FindBy(xpath = "//*[@id=\"btnOceniKliniku3\"]")
	private WebElement btnOceniKliniku;

	@FindBy(xpath = "//*[@id=\"btn10\"]")
	private WebElement btn10;

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getBtn10() {
		return btn10;
	}

	public WebElement getBtnOceniKliniku() {
		return btnOceniKliniku;
	}

}
