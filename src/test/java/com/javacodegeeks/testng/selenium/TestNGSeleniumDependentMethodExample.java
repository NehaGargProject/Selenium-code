package com.javacodegeeks.testng.selenium;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
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
import org.testng.annotations.Test;


@ContextConfiguration("driver_context.xml")
public class TestNGSeleniumDependentMethodExample extends AbstractTestNGSpringContextTests {
	@Autowired
	private WebDriver driver;

	@BeforeClass
	public void printBrowserUsed() {
		System.out.println("Driver used is: " + driver);
	}
	
	@Test(dependsOnMethods="searchTestNGInJCG")
	public void clickOnJCGTestNGArticle() {
		System.out.println("You are in page " + driver.getTitle());
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				WebElement link = driver.findElement(By.linkText("TestNG Maven Project Example"));
				if (link != null) {
					System.out.println("Going to click on '" + link.getText() + "'");
					link.click();
				}
				return link != null;
			}
		});
		assertPageTitle("TestNG Maven Project Example");
	}

	@Test
	public void searchTestNGInJCG() {
		final String searchKey = "TestNG";
		System.out.println("Search " + searchKey + " in JCG");
		driver.navigate().to("http://examples.javacodegeeks.com/");
		(new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				//System.out.println(d.getPageSource());
				WebElement popup = null;
				//popup = d.findElement(By.cssSelector("a.fancybox-item fancybox-close"));
				//System.out.println("popup 1" + popup );
				//popup = d.findElement(By.cssSelector("a.fancybox-item fancybox-close"));
				//System.out.println("popup 2" + popup );
				popup = d.findElement(By.xpath("//*[@class='fancybox-item fancybox-close']"));
				System.out.println("popup 3" + popup );

				
				if (popup != null) {
					System.out.println("Found popup, close it");
					popup.click();
				}
				return popup != null;
			}
		});
		WebElement element = driver.findElement(By.name("s"));
		System.out.println("Enter " + searchKey);
		element.sendKeys(searchKey);
		System.out.println("submit");		
		element.submit();
		assertPageTitle(searchKey);
		System.out.println("Got " + searchKey + " results");
	}
	
	private void assertPageTitle(final String title) {
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				System.out.println("TITLE: " + d.getTitle());
				return d.getTitle().toLowerCase().contains(title.toLowerCase());
			}
		});
	}

	@AfterSuite
	public void quitDriver() throws Exception {
		driver.quit();
	}
}
