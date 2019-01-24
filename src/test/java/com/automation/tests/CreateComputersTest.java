package com.automation.tests;

import static org.testng.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.automation.pageObjects.AddComputerPage;
import com.automation.pageObjects.HomePage;

public class CreateComputersTest { 

	public WebDriver driver;
	HomePage homePage;
	AddComputerPage addComputerPage;

	// Test Data variables
	String computerName;
	String introducedDate;
	String discontinuedDate;
	String company;

	@BeforeTest
	public void setup () {
		System.setProperty("webdriver.chrome.driver","/usr/local/bin/chromedriver");  
		ChromeOptions chromeOptions = new ChromeOptions();
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		driver.navigate().to("http://computer-database.herokuapp.com/computers"); 

		// Dynamic Test Data Creation
		DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();

		computerName = "AutomatedTest_"+ now;
		introducedDate = dateformatter.format(now.plusMonths(1)).toString();
		discontinuedDate = dateformatter.format(now.plusMonths(2)).toString();
		company = "Canon";
	}
	
	//Tests that computer is added by filling all the required fields
	@Test(priority=1)
	public void Test01_createComputerWithAllFields() {

		homePage = new HomePage(driver);
		addComputerPage = homePage.initaiteComputerCreation();
		addComputerPage.fillComputerDetails(computerName, introducedDate, discontinuedDate, company);
		homePage = addComputerPage.submitComputerDetails();

		assertEquals(homePage.getConfirmationMessage(), "Done! Computer " + computerName + " has been created", 
				"ASSERTION FAILED: New Computer success message not dispalyed");
	}

	//Tests that add computer only filling mandatory fields
	@Test(priority=2)
	public void Test02_createComputerWithOnlyMandatoryFields() {

		homePage = new HomePage(driver);
		addComputerPage = homePage.initaiteComputerCreation();
		addComputerPage.fillComputerDetails(computerName);
		homePage = addComputerPage.submitComputerDetails();

		assertEquals(homePage.getConfirmationMessage(), "Done! Computer " + computerName + " has been created", 
				"ASSERTION FAILED: New Computer success message not dispalyed");
	}

	//Tests that user can add a computer with Computer name with longer Computer Name
	@Test(priority=3)
	public void Test03_createComputerWithComputerNameLonger() {

		homePage = new HomePage(driver);
		addComputerPage = homePage.initaiteComputerCreation();
		addComputerPage.fillComputerDetails(computerName + "AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_", introducedDate, discontinuedDate, company);
		homePage = addComputerPage.submitComputerDetails();

		assertEquals(homePage.getConfirmationMessage(), "Done! Computer " + computerName + "AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_AutomatedTest_LongName_" + " has been created", 
				"ASSERTION FAILED: New Computer success message not dispalyed");	
	}

	//Tests that user can add a computer with Computer name containing Special Characters
	@Test(priority=4)
	public void Test04_createComputerWithComputerNameContainingSpecialCharacters() {

		homePage = new HomePage(driver);
		addComputerPage = homePage.initaiteComputerCreation();
		addComputerPage.fillComputerDetails(computerName + "ARRA!@#$%^&*()_++_{:L>?\":><>?!@#$%^&*(1234567890", introducedDate, discontinuedDate, company);
		homePage = addComputerPage.submitComputerDetails();

		assertEquals(homePage.getConfirmationMessage(), "Done! Computer " + computerName + "ARRA!@#$%^&*()_++_{:L>?\":><>?!@#$%^&*(1234567890" + " has been created", 
				"ASSERTION FAILED: New Computer success message not dispalyed");	
	}

	//Tests that computer count in header is incremented by 1 after successful computer addition
	@Test(priority=5)
	public void Test05_createComputerCheckCountIncrease() {

		homePage = new HomePage(driver);
		int computerCountInitially = homePage.getComputerCountInHeader();

		addComputerPage = homePage.initaiteComputerCreation();
		addComputerPage.fillComputerDetails(computerName, introducedDate, discontinuedDate, company);
		homePage = addComputerPage.submitComputerDetails();

		assertEquals(homePage.getConfirmationMessage(), "Done! Computer " + computerName + " has been created", 
				"ASSERTION FAILED: New Computer success message not dispalyed");
		int computerCountAfterNewComputerCreation = homePage.getComputerCountInHeader();

		assertEquals(computerCountInitially + 1, computerCountAfterNewComputerCreation);
	}

	//Tests that newly added computer appears in search results
	@Test(priority=6)
	public void Test06_createComputerCheckNewlyCreatedComputer() {

		homePage = new HomePage(driver);			
		addComputerPage = homePage.initaiteComputerCreation();
		addComputerPage.fillComputerDetails(computerName, introducedDate, discontinuedDate, company);
		homePage = addComputerPage.submitComputerDetails();

		homePage.searchComputer(computerName);
		assertEquals(homePage.getSearchedComputerNameResult(), computerName, "ASSERTION FAILED: Searched computer is not the same as newly added");
	}


	@AfterTest
	public void teardown () {
		driver.quit();
	}


}
