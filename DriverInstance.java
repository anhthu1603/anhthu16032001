package com.automation.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.automation.utils.PropertiesFileUtils;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverInstance {
	 protected WebDriver driver;

	 @BeforeClass
		public void init() {
			System.out.println("ini: Open browser");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			String URL = PropertiesFileUtils.getProperty("base_url");
			driver.get(URL);
			driver.manage().window().maximize();
		}
	 
	 @AfterClass
		public void test() {
			System.out.println("FINISH TEST");
			driver.close();
		}
	
}
