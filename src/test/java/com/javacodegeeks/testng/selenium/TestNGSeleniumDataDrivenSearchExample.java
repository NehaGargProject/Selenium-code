package com.javacodegeeks.testng.selenium;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@ContextConfiguration("driver_context.xml")
public class TestNGSeleniumDataDrivenSearchExample extends
		AbstractTestNGSpringContextTests {
	@Autowired
	private WebDriver driver;

	@BeforeClass
	public void printBrowserUsed() {
		System.out.println("Driver used is: " + driver);
	}

	@Test(dataProvider = "searchStringsExcelFile")
	public void searchGoogle(final String searchKey) {
		System.out.println("Search " + searchKey + " in google");
		driver.navigate().to("http://www.google.com");
		WebElement element = driver.findElement(By.name("q"));
		System.out.println("Enter " + searchKey);
		element.sendKeys(searchKey);
		System.out.println("submit");
		element.submit();
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase()
						.startsWith(searchKey.toLowerCase());
			}
		});
		System.out.println("Got " + searchKey + " results");
	}

	@DataProvider
	private Object[][] searchStringsExcelFile() {
		Object[][] arrayObject = getExcelData("c:/Excel/SampleBook.xls","Sheet1");
		return arrayObject;
	}

	public String[][] getExcelData(String fileName, String sheetName)
	{
		String[][] arrayExcelData = null;
		
		try
		{
			
			FileInputStream fs = new FileInputStream(fileName);
			Workbook wb = Workbook.getWorkbook(fs);
			Sheet sh = wb.getSheet(sheetName);

			int totalNoOfCols = sh.getColumns();
			int totalNoOfRows = sh.getRows();
			
			arrayExcelData = new String[totalNoOfRows-1][totalNoOfCols];
			
			for (int i= 1 ; i < totalNoOfRows; i++) {

				for (int j=0; j < totalNoOfCols; j++) {
					arrayExcelData[i-1][j] = sh.getCell(j, i).getContents();
				}

			}
			
	
		}catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
		e.printStackTrace();
	} catch (BiffException e) {
		e.printStackTrace();
	}
		return arrayExcelData;
	}
	
	@AfterSuite
	public void quitDriver() throws Exception {
		if(driver != null)
			driver.quit();
	}
}
