package com.r1rcm.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class configReader {
	
	public static String getPropertyValue(String key) throws FileNotFoundException, IOException {
		Properties property = new Properties();
		property.load(new FileInputStream("C:\\Users\\Amit Kumar-GGN\\eclipse-workspace\\AutomationLearning\\Config.properties"));
		
		return property.getProperty(key);
		
	}
	
	
	
	
	

}
