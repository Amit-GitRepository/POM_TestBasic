package com.automation.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	private WebDriver driver;

	public HomePage(WebDriver driver) {           
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="searchbox")
	private WebElement textBox_searchComputer;

	@FindBy(id="searchsubmit")
	private WebElement button_searchSubmit;

	@FindBy(id="add")
	private WebElement button_addComputer;

	@FindBy(xpath="//div[contains(@class,'alert-message')]")
	private WebElement label_successMessage;

	@FindBy(xpath="//section[@id='main']/h1")
	private WebElement label_totalComputersFound;

	@FindBy(xpath="(//table/tbody//a[1])")
	private WebElement link_firstSearchComputerResult; 

	@FindBys(@FindBy(xpath="//tbody/tr[1]/td"))
	private List<WebElement> listLabel_firstRecordRowItems; 

	@FindBy(xpath="//div[@class='well']/em")
	private WebElement label_NothingToDisplay; 
	
	public void navigateToHomePage(){
		driver.navigate().to("http://computer-database.herokuapp.com/computers");
	}

	public AddComputerPage initaiteComputerCreation(){
		button_addComputer.click();
		return new AddComputerPage(driver);
	}

	public String getConfirmationMessage() {
		return label_successMessage.getText();
	}

	public int getComputerCountInHeader() {
		String totalComputerCount = label_totalComputersFound.getText().substring(0, 3);
		return Integer.valueOf(totalComputerCount);	
	}

	public void searchComputer(String computerName) {
		textBox_searchComputer.sendKeys(computerName);
		button_searchSubmit.click();
	}

	public String getSearchedComputerNameResult() {
		return link_firstSearchComputerResult.getText(); 

	}

	public AddComputerPage editExistingComputer() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		link_firstSearchComputerResult.click();
		return new AddComputerPage(driver);
	}

	public String[] verifyComputerValuesAfterSaving(String computerName, String introducedDate, String discontinuedDate,
			String company) {

		String[] items = new String[4];
		for(int i=0; i<items.length; i++) {
			items[i] = listLabel_firstRecordRowItems.get(i).getText();
		}

		return items;
	}
	
	public String noComputerFound() {
		return label_NothingToDisplay.getText();
	}

	

	
}


