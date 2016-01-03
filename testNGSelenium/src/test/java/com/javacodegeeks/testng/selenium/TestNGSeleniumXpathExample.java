package com.javacodegeeks.testng.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.javacodegeeks.testing.config.PageXpathConstant;

public class TestNGSeleniumXpathExample {

	private WebDriver driver;
	
	@BeforeClass
	public void initDriver() throws Exception
	{
		System.out.println("Working in firefox driver");
		driver = new FirefoxDriver();
	}
	
	
	@Test
	public void searchStringUsingXPATH()
	{
		final String searchKey = "TestNG";
		//final String GOOGLESEARCHBOX = "//*[@id='sb_ifc0']";
		System.out.println("Opening google.com");
		driver.navigate().to("http://www.google.com");
		System.out.println("Web element as XPATH");
		WebElement element = driver.findElement(By.xpath(PageXpathConstant.GOOGLESEARCHBOX));
		System.out.println("Searching search string");
		element.sendKeys(searchKey);
		element.submit();
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase()
						.startsWith(searchKey.toLowerCase());
			}
		});
	
	}

		@AfterSuite
		public void quitDriver() throws Exception {
			driver.quit();
		}
	
}
