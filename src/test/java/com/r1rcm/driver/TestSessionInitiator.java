package com.r1rcm.driver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.r1rcm.utils.configReader;

public class TestSessionInitiator {
	
	public static WebDriver getBrowserInstance(){
		
		try {
			if(configReader.getPropertyValue("browser").equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver", configReader.getPropertyValue("chromeDriverPath"));
				WebDriver driver = new ChromeDriver();
				return driver;

			}else if(configReader.getPropertyValue("browser").equalsIgnoreCase("Firefox")) {
				System.setProperty("webdriver.gecko.driver", configReader.getPropertyValue("firefoxDriverPath"));
				WebDriver driver = new FirefoxDriver();
				return driver;

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
//		driver.get(configReader.getPropertyValue("baseURL"));
//		
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		driver.manage().window().maximize();
	}

}
