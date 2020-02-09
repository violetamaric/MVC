package com.example.demo.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class PretragaKlinika {

	private WebDriver driver;

	@FindBy(xpath = "//*[@id=\"pretraziPoljeKlinika\"]")
	private WebElement pretraziPoljeKlinika;

	@FindBy(xpath = "//*[@id=\"odabirDatuma\"]")
	private WebElement odabirDatuma;

	@FindBy(xpath = "//*[@id=\"klinikaOcena9\"]")
	private WebElement klinikaOcena9;

	@FindBy(xpath = "//*[@id=\"btnPretragaKlinika\"]")
	private WebElement btnPretragaKlinika;

	@FindBy(xpath = "//*[@id=\"btnTipPregleda\"]")
	private WebElement btnTipPregleda;

	@FindBy(xpath = "//*[@id=\"btnDatum\"]")
	private WebElement btnDatum;

	@FindBy(xpath = "//*[@id=\"btnOcena\"]")
	private WebElement btnOcena;

	@FindBy(xpath = "//*[@id=\"btnPonistiFilter\"]")
	private WebElement btnPonistiFilter;

	private Select selectTipPregleda;

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getPretraziPoljeKlinika() {
		return pretraziPoljeKlinika;
	}

	public WebElement getOdabirDatuma() {
		return odabirDatuma;
	}

	public WebElement getKlinikaOcena9() {
		return klinikaOcena9;
	}

	public Select getSelectTipPregleda() {
		return selectTipPregleda;
	}

	public WebElement getBtnPretragaKlinika() {
		return btnPretragaKlinika;
	}

	public WebElement getBtnTipPregleda() {
		return btnTipPregleda;
	}

	public WebElement getBtnDatum() {
		return btnDatum;
	}

	public WebElement getBtnOcena() {
		return btnOcena;
	}

	public WebElement getBtnPonistiFilter() {
		return btnPonistiFilter;
	}

}
