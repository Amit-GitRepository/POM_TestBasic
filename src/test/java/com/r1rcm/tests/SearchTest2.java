package com.r1rcm.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.r1rcm.pages.AgilePage;
import com.r1rcm.pages.HomePage;

public class SearchTest2 extends BaseTest{
	
	@Test
	public void textSearch_Test(){
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Amit Kumar-GGN\\Downloads\\chromedriver_win32_2\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.techbeamers.com/");
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		HomePage home = new HomePage(driver);
		home.searchTopic("Selenium");
		
		
		AgilePage agile = new AgilePage(driver);
//		agile.navigateToAgile();
		
		driver.quit();
		
	}
	

}
