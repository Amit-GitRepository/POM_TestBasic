package com.r1rcm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchResultsPage{

	WebDriver driver;

	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(tagName = "h1")
	private WebElement searchResult_heading;
	
	public String getSearchResultHeading() {
		return searchResult_heading.getText();
	}

}
