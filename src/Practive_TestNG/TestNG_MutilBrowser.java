package Practive_TestNG;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestNG_MutilBrowser {
	public WebDriver driver;

	@Parameters("browser")

	@BeforeClass
	public void beforeClass() {
		// Passing Browser parameter from TestNG xml
	}

	public void beforeTest(String browser) {
		if(browser.equalsIgnoreCase("firefox")) {
		  driver = new FirefoxDriver();
		}else if (browser.equalsIgnoreCase("ie")) {
		  //System.setProperty("webdriver.ie.driver", "D:\ToolsQA\OnlineStore\drivers\IEDriverServer.exe");
		  //driver = new InternetExplorerDriver();
		}
		driver.get("http://www.store.demoqa.com");
	}
	@Test public void login() throws InterruptedException {

		driver.findElement(By.xpath(".//*[@id='account']/a")).click();

		driver.findElement(By.id("log")).sendKeys("testuser_1");

		driver.findElement(By.id("pwd")).sendKeys("Test@123");

		driver.findElement(By.id("login")).click();

		}  

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
