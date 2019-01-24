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

public class UpdateComputersTest {
 
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
		introducedDate = dateformatter.format(now.plusMonths(1));
		discontinuedDate = dateformatter.format(now.plusMonths(2));
		company = "Nokia";
	}

	//Tests that computer can update all the fields
	@Test(priority=1)
	public void Test01_updateComputerWithAllFields() {

		homePage = new HomePage(driver);
		addComputerPage = homePage.initaiteComputerCreation();
		addComputerPage.fillComputerDetails(computerName, introducedDate, discontinuedDate, company);
		homePage = addComputerPage.submitComputerDetails();

		homePage = new HomePage(driver);
		homePage.searchComputer(computerName);
		addComputerPage = homePage.editExistingComputer();
		addComputerPage.editComputerDetails(computerName +"Edited", "1999-01-02", "2019-01-02", "RCA");
		homePage = addComputerPage.submitComputerDetails();
		homePage.searchComputer(computerName);
		String[] expectedRowValues = homePage.verifyComputerValuesAfterSaving(computerName, introducedDate, discontinuedDate, company);

		assertEquals(expectedRowValues[0], computerName + "Edited");
		assertEquals(expectedRowValues[1], "02 Jan 1999");
		assertEquals(expectedRowValues[2], "02 Jan 2019");
		assertEquals(expectedRowValues[3], "RCA");
	}


	//Tests that computer can update only required fields
	@Test(priority=2)
	public void Test02_updateComputerWithAllFields() {

		homePage = new HomePage(driver);
		addComputerPage = homePage.initaiteComputerCreation();
		addComputerPage.fillComputerDetails(computerName, introducedDate, discontinuedDate, company);
		homePage = addComputerPage.submitComputerDetails();

		homePage = new HomePage(driver);
		homePage.searchComputer(computerName);
		addComputerPage = homePage.editExistingComputer();
		addComputerPage.editComputerDetails(computerName +"Edited", introducedDate, discontinuedDate, company);
		homePage = addComputerPage.submitComputerDetails();
		homePage.searchComputer(computerName);
		String[] expectedRowValues = homePage.verifyComputerValuesAfterSaving(computerName, introducedDate, discontinuedDate, company);

		assertEquals(expectedRowValues[0], computerName + "Edited");
	}

	//Tests that user can update and cancel saving it
	@Test(priority=3)
	public void Test03_updateComputerAndCancelSave() {

		homePage = new HomePage(driver);
		homePage.searchComputer("AutomatedTest");
		addComputerPage = homePage.editExistingComputer();
		addComputerPage.editComputerDetails(computerName + "Edited");
		homePage = addComputerPage.cancelEditComputerDetails();
	}

	//Tests that user updates and verifies successfully updated message
	@Test(priority=4)
	public void Test04_updateComputerAndVerifySucccessMessage() {

		homePage = new HomePage(driver);
		homePage.searchComputer("AutomatedTest");
		addComputerPage = homePage.editExistingComputer();
		addComputerPage.editComputerDetails(computerName);
		homePage = addComputerPage.submitComputerDetails();

		assertEquals(homePage.getConfirmationMessage(), "Done! Computer " + computerName + " has been updated", 
				"ASSERTION FAILED: Successfully updated Computer details message is not dispalyed");
	}


	@AfterTest
	public void teardown () {
		driver.quit();
	}

}
