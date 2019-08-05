package com.r1rcm.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
	
	@BeforeSuite
	public void makeDBConnection() {
		System.out.println("Here we are making DB connection");
	}
	
	@AfterSuite
	public void closeDBConnection() {
		System.out.println("Here we close DB connection");
	}
}
