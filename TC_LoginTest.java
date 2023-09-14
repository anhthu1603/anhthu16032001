package com.automation.testcase;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.base.DriverInstance;
import com.automation.pom.LoginPage;
import com.automation.utils.CaptureScreenshot;
import com.automation.utils.PropertiesFileUtils;
import com.github.dockerjava.core.util.FilePathUtil;

public class TC_LoginTest extends DriverInstance {

	@Test(dataProvider = "Excel")
	public void TC01_LoginFirtAccount(String Email, String Password) throws InterruptedException {
		
		System.out.println("START ");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		
		WebElement iconSignIn = driver.findElement(By.xpath("//a[@href='/login']"));
		iconSignIn.click();
		LoginPage loginPage = new LoginPage(driver);
		PageFactory.initElements(driver, loginPage);
		
		loginPage.enterEmail(Email);
		loginPage.enterPassword(Password);
		loginPage.clickSignIn();
		
		WebElement iconSignOut = driver.findElement(By.xpath("//a[@href='/logout']"));
		assertEquals(true, iconSignOut.getText().contains("Logout"), "Đăng nhập thất bại!");
		iconSignOut.click();

		Thread.sleep(2000);

		System.out.println("FINISH ");

	}

	@Test
	@DataProvider(name = "Excel")
	public Object[][] testDataGenerator() throws IOException {
		FileInputStream file = new FileInputStream("./data/data.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet loginSheet = workbook.getSheet("Login");
		int numberOfData = loginSheet.getPhysicalNumberOfRows();
		Object[][] testData = new Object[numberOfData][2];
		for (int i = 0; i < numberOfData; i++) {
			XSSFRow row = loginSheet.getRow(i);
			XSSFCell email = row.getCell(0);
			XSSFCell passwork = row.getCell(1);
			testData[i][0] = email.getStringCellValue();
			testData[i][1] = passwork.getStringCellValue();
		}
		return testData;
	}

	@AfterMethod
	public void takeScreenshot(ITestResult result) throws InterruptedException {
		
		if (ITestResult.FAILURE == result.getStatus()) {
			try {
				
				String email = (String) result.getParameters()[0];
				String password = (String)result.getParameters()[1];
				String accountName = email.substring(0,email.indexOf("@"));
				String filePath = CaptureScreenshot.takeScreenshot(driver, accountName);
			
				CaptureScreenshot.attachScreenshot(filePath);
			} catch (Exception e) {
				System.out.println("Lỗi xảy ra screenshot " + e.getMessage());

			}
		}

	}

}
