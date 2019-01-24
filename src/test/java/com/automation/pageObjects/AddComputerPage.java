package com.automation.pageObjects;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddComputerPage {

	private WebDriver driver;

	public AddComputerPage(WebDriver driver) {           
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="name")
	private WebElement textBox_computerName;
	
	@FindBy(id="introduced")
	private WebElement textBox_introducedDate;
	
	@FindBy(id="discontinued")
	private WebElement textBox_discontinuedDate;
	
	@FindBy(id="company")
	private WebElement dropdown_company;
	
	@FindBy(xpath="//input[@type=\"submit\"]")
	private WebElement button_createComputer;

	@FindBy(xpath="//a[@class='btn']")
	private WebElement button_cancelCreateComputer;
	
	@FindBy(xpath="//input[contains(@value,'Delete')]")
	private WebElement button_deleteComputer;
	
	@FindBy(xpath="//div[contains(@class,'error')]")
	private WebElement panel_redHighlighted;
	
	public void fillComputerDetails(String computerName){
		textBox_computerName.sendKeys(computerName);
	}
	
	
	public void fillComputerDetails(String computerName, String introducedDate, String discontinuedDate, String company){
		textBox_computerName.sendKeys(computerName);
		textBox_introducedDate.sendKeys(introducedDate);
		textBox_discontinuedDate.sendKeys(discontinuedDate);
		Select country = new Select(dropdown_company);
		country.selectByVisibleText(company);
	}
	
	public void editComputerDetails(String computerName, String introducedDate, String discontinuedDate, String company){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		textBox_computerName.clear();
		textBox_computerName.sendKeys(computerName); 
		textBox_introducedDate.clear();
		textBox_introducedDate.sendKeys(introducedDate);
		textBox_discontinuedDate.clear();
		textBox_discontinuedDate.sendKeys(discontinuedDate);
		Select country = new Select(dropdown_company);
		country.selectByVisibleText(company);
	}
	
	public void editComputerDetails(String computerName){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		textBox_computerName.clear();
		textBox_computerName.sendKeys(computerName);
	}
	
	public HomePage submitComputerDetails(){
		button_createComputer.click();
		return new HomePage(driver);
	}
	
	public HomePage cancelEditComputerDetails(){
		button_cancelCreateComputer.click();
		return new HomePage(driver);
	}
	
	public HomePage deleteComputer() {
		button_deleteComputer.click();
		return new HomePage(driver);
	}
	
	public boolean isComputerNameFieldRed() {
		assertEquals(panel_redHighlighted.getCssValue("color"), "rgba(64, 64, 64, 1)");
		return panel_redHighlighted.isDisplayed();
	}
	
	public boolean isIntroducedDateFieldRed() {
		assertEquals(panel_redHighlighted.getCssValue("color"), "rgba(64, 64, 64, 1)");
		return panel_redHighlighted.isDisplayed();
	}
	
	public boolean isDiscontinuedDateFieldRed() {
		assertEquals(panel_redHighlighted.getCssValue("color"), "rgba(64, 64, 64, 1)");
		return panel_redHighlighted.isDisplayed();
	}
	
}
