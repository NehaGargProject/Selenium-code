package com.javacodegeeks.testng.selenium;

import java.io.File;

import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class TestNGSeleniumBrowserCfgExample {
	private WebDriver driver;	

	@Parameters({"browser"})
	@BeforeTest
	public void initDriver(@Optional("firefox") String browser) throws Exception {
		
		System.out.println("You are testing on browser " + browser);
		browser = browser.toLowerCase();

		if (browser.equals("chrome")) {	
			String chromeBinaryPath = "";
		    String osName = System.getProperty("os.name").toUpperCase();

		    if (osName.contains("WINDOWS")) {
		      chromeBinaryPath = "/chromedriver_win32/chromedriver.exe";
		    } else if (osName.contains("MAC")) {
		      chromeBinaryPath = "/chromedriver_mac32/chromedriver";

		      File chromedriver =
		          new File(ClassLoader.getSystemResource("ChromeDriver" + chromeBinaryPath).getPath());

		      // set application user permissions to 455
		      chromedriver.setExecutable(true);
		    } else if (osName.contains("LINUX")) {
		      chromeBinaryPath = "/chromedriver_linux64/chromedriver";

		      File chromedriver =
		          new File(ClassLoader.getSystemResource("ChromeDriver" + chromeBinaryPath).getPath());

		      // set application user permissions to 455
		      chromedriver.setExecutable(true);
		    }

		    System.setProperty("webdriver.chrome.driver",
		                       new File(ClassLoader.getSystemResource("ChromeDriver" + chromeBinaryPath)
		                                    .getPath())
		                           .getPath());

			driver = new ChromeDriver();
		} else if (browser.equals("firefox")) {
			driver = new FirefoxDriver();
		} else {
			throw new RuntimeException("Please create a driver for " + browser);
		}
	}

	@Test(dataProvider = "searchStrings")
	public void searchGoogleMultiBrowser(final String searchKey) {
		System.out.println("Search " + searchKey + " in google");
		driver.navigate().to("http://www.google.com");		
		WebElement element = driver.findElement(By.name("q"));
		System.out.println("Enter " + searchKey);
		element.sendKeys(searchKey);
		System.out.println("submit");
		element.submit();
		 (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver d) {
	                return d.getTitle().toLowerCase().startsWith(searchKey.toLowerCase());
	            }
	        });
		System.out.println("Got " + searchKey + " results");
	}

	@DataProvider
	private Object[][] searchStrings() {
		return new Object[][] { { "TestNG" }, { "Selenium" } };
	}

	@AfterTest
	public void quitDriver() throws Exception {
		driver.quit();
	}	
}
