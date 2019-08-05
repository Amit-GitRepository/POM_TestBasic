package com.r1rcm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AgilePage{

	WebDriver driver;

	public AgilePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(linkText = "Next")
	private WebElement nextNavigation_button;
	
	public void goToNextPage() {
		nextNavigation_button.click();
	}

}
