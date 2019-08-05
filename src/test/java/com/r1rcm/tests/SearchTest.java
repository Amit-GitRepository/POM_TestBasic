package com.r1rcm.tests;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.r1rcm.driver.TestSessionInitiator;
import com.r1rcm.pages.AgilePage;
import com.r1rcm.pages.HomePage;
import com.r1rcm.pages.SearchResultsPage;

public class SearchTest extends BaseTest{
	
	WebDriver driver;
	HomePage home;
	SearchResultsPage searchResult;
	AgilePage agile;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest logger;
	
	String searchText = "Selenium";
	String expectedResult = "Search Results For: Selenium";
	
	@BeforeClass
	public void setUp() {
		driver = TestSessionInitiator.getBrowserInstance();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.techbeamers.com/");
		
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "R1RCM Learning Automation");
		extent.setSystemInfo("Environment", "Automation Testing");
		extent.setSystemInfo("User Name", "Altruistic Amit");
		
		htmlReporter.config().setDocumentTitle("Title of the Report Comes here");
		htmlReporter.config().setReportName("Name of the Report Comes here");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
	}
	
	@Test(groups = {"smoke"})
	public void textSearch_Test(){
		home = new HomePage(driver);
		searchResult = home.searchTopic(searchText);
//		searchResult = new SearchResultsPage(driver);
		String actualResult = searchResult.getSearchResultHeading();
		Assert.assertEquals(actualResult, expectedResult);		
	}
	
	@Test(dependsOnMethods = "textSearch_Test")
	public void navigationToAgileTest() {
		home.navigateToAgile();
		agile = new AgilePage(driver);
		agile.goToNextPage();
	}
	
	@Test
	public void testReports() {
		logger = extent.createTest("passTest");
		logger.log(Status.PASS, MarkupHelper.createLabel("Test Case Passed is passTest", ExtentColor.GREEN));
	}

	@Test
	public void failTest() {
		logger = extent.createTest("failTest");
		logger.log(Status.FAIL, "Test Case (failTest) Status is failed");
		logger.log(Status.FAIL, MarkupHelper.createLabel("Test Case (failTest) Status is failed", ExtentColor.RED));
		Assert.assertTrue(false);
	}

	@Test
	public void skipTest() {
		logger = extent.createTest("skipTest");
		logger.log(Status.SKIP, "Test case is skipped intentionally");
		throw new SkipException("Skipping - This is not ready for testing ");
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
		extent.flush();
	}
	

}
