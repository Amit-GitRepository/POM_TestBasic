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

public class DeleteComputersTest {

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
	
	//Tests that deletes a computer
	@Test(priority=1)
	public void Test01_deleteComputer() {

		homePage = new HomePage(driver);
		addComputerPage = homePage.initaiteComputerCreation();
		addComputerPage.fillComputerDetails(computerName, introducedDate, discontinuedDate, company);
		homePage = addComputerPage.submitComputerDetails();
		
		homePage.searchComputer(computerName);
		addComputerPage = homePage.editExistingComputer();
		addComputerPage.deleteComputer();
		

		assertEquals(homePage.getConfirmationMessage(), "Done! Computer has been deleted", 
				"ASSERTION FAILED: New Computer success message not dispalyed");
	}

	//Tests that computer count in header is decremented by 1 after successful computer addition
	@Test(priority=2)
	public void Test02_deleteComputerAndCheckCountDecrease() {

		homePage = new HomePage(driver);
		int computerCountInitially = homePage.getComputerCountInHeader();
		homePage = new HomePage(driver);
		addComputerPage = homePage.initaiteComputerCreation();
		addComputerPage.fillComputerDetails(computerName, introducedDate, discontinuedDate, company);
		homePage = addComputerPage.submitComputerDetails();
		
		homePage.searchComputer(computerName);
		addComputerPage = homePage.editExistingComputer();
		addComputerPage.deleteComputer();
		
		int computerCountAfterNewComputerCreation = homePage.getComputerCountInHeader();
		assertEquals(computerCountInitially, computerCountAfterNewComputerCreation);
	}

	//Tests that deletes a computer search for deleted computer again
	@Test(priority=3)
	public void Test03_deleteComputerAndSearchForDeletedComputer() {
		homePage = new HomePage(driver);
		addComputerPage = homePage.initaiteComputerCreation();
		addComputerPage.fillComputerDetails(computerName, introducedDate, discontinuedDate, company);
		homePage = addComputerPage.submitComputerDetails();
		
		homePage.searchComputer(computerName);
		addComputerPage = homePage.editExistingComputer();
		homePage = addComputerPage.deleteComputer();
		
		homePage.searchComputer(computerName);
		
		assertEquals(homePage.noComputerFound(), "Nothing to display");
	}


	@AfterTest
	public void teardown () {
		driver.quit();
	}


}
