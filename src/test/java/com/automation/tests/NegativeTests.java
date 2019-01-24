package com.automation.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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

public class NegativeTests {

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



	//Tests that error is thrown if user tries to save computer without without name
	@Test(priority=1)
	public void Test01_computerNameError() {

		homePage = new HomePage(driver);
		homePage.navigateToHomePage();
		addComputerPage = homePage.initaiteComputerCreation();
		addComputerPage.fillComputerDetails("", introducedDate, discontinuedDate, company);
		addComputerPage.submitComputerDetails();

		boolean errorThrown = addComputerPage.isComputerNameFieldRed();
		assertEquals(errorThrown, true);
	}

	//Tests that error is thrown if user tries to save computer with invalid introduced date
	@Test(priority=2)
	public void Test02_introducedDateError() {

		homePage = new HomePage(driver);
		homePage.navigateToHomePage();
		addComputerPage = homePage.initaiteComputerCreation();
		addComputerPage.fillComputerDetails(computerName, "2019-02-2423", discontinuedDate, company);
		addComputerPage.submitComputerDetails();

		boolean errorThrown = addComputerPage.isIntroducedDateFieldRed();
		assertEquals(errorThrown, true);
	}

	//Tests that error is thrown if user tries to save computer with invalid discontinue date
	@Test(priority=3)
	public void Test03_discontinueDateError() {

		homePage = new HomePage(driver);
		homePage.navigateToHomePage();
		addComputerPage = homePage.initaiteComputerCreation();
		addComputerPage.fillComputerDetails(computerName, introducedDate, "2019-02-2423", company);
		addComputerPage.submitComputerDetails();

		boolean errorThrown = addComputerPage.isDiscontinuedDateFieldRed();
		assertEquals(errorThrown, true);
	}


	//Tests that looks for a computer that doesn't exists
	@Test(priority=4)
	public void Test04_searchComputerThatDoesntExists() {
		
		homePage = new HomePage(driver);
		homePage.navigateToHomePage();
		homePage.searchComputer("kiuytrewsdfghjk");

		assertEquals(homePage.noComputerFound(), "Nothing to display");
	}

	//Tests that multiple computers of the same name can be added
	@Test(priority=5)
	public void Test05_createComputerWithDuplicateName() {

		homePage = new HomePage(driver);
		homePage.navigateToHomePage();
		addComputerPage = homePage.initaiteComputerCreation();
		computerName = "DuplicateComputerName";
		addComputerPage.fillComputerDetails(computerName, introducedDate, discontinuedDate, company);
		homePage = addComputerPage.submitComputerDetails();

		addComputerPage = homePage.initaiteComputerCreation();
		computerName = "DuplicateComputerName";
		addComputerPage.fillComputerDetails(computerName, introducedDate, discontinuedDate, company);
		homePage = addComputerPage.submitComputerDetails();

		assertEquals(homePage.getConfirmationMessage(), "Done! Computer " + computerName + " has been created", 
				"ASSERTION FAILED: New Computer success message not dispalyed");
	}
	
	//Tests that searches computer without giving any name
		@Test(priority=6)
		public void Test06_searchComputerWithoutName() {
			homePage = new HomePage(driver);
			homePage.navigateToHomePage();
			homePage.searchComputer("");

			assertTrue(homePage.getSearchedComputerNameResult().length() > 0);
		}

	@AfterTest
	public void teardown () {
		driver.quit();
	}
}

