package com.example.demo.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RezervisanjeSala {

	
	private WebDriver driver;

	@FindBy(xpath = "//*[@id=\"txtEmail\"]")
	private WebElement txtEmail;

	@FindBy(xpath = "//*[@id=\"txtLoz\"]")
	private WebElement txtPass;

	@FindBy(xpath = "//*[@id=\"btnSignIn\"]")
	private WebElement btnLogin;
	
	
	
	
}
