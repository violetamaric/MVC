package com.example.demo.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Login {

	private WebDriver driver;

	@FindBy(xpath = "//*[@id=\"txtEmail\"]")
	private WebElement txtEmail;

	@FindBy(xpath = "//*[@id=\"txtLoz\"]")
	private WebElement txtPass;

	@FindBy(xpath = "//*[@id=\"btnSignIn\"]")
	private WebElement btnLogin;

	public Login() {
	}

	public Login(WebDriver driver) {
		this.driver = driver;
	}

	public void ensureIsDisplayedBtnLogin() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(btnLogin));
	}

	public void ensureIsDisplayedTxtEmail() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(txtEmail));
	}

	public void ensureIsDisplayedTxtPass() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(txtPass));
	}

}
