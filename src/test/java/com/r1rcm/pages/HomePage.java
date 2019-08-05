package com.r1rcm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage{

	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "s")
	private WebElement searchItem_textBox;

	@FindBy(id = "search-image")
	private WebElement searchIcon_button;

	@FindBy(linkText = "Agile")
	private WebElement agileHeader_link;

	public SearchResultsPage searchTopic(String searchText) {
		searchItem_textBox.sendKeys(searchText);
		searchIcon_button.click();
		return new SearchResultsPage(driver);
	}

	public void navigateToAgile() {
		agileHeader_link.click();
	}

}
