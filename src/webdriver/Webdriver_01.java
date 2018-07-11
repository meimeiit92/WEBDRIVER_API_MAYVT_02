package webdriver;

import org.testng.annotations.Test;

import bsh.Console;

import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Webdriver_01 {
	WebDriver driver;
  @BeforeClass
  public void beforeClass() {
		//Khoi tao trinh duyet
		driver = new FirefoxDriver();
		// System.setProperty("webdriver.chrome.driver",".\\driver\\chromedriver.exe");
		// driver = new ChromeDriver();
		//Wait cho page duoc load thanh cong
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//maximize browser len
		driver.manage().window().maximize();
		//get URL cua web
		driver.get("http://live.guru99.com");

  }
  @Test
  public void TC01_VerifyURLAndTitle(){
	  String homePageTitle = driver.getTitle();
	  String urlPage;
	  Assert.assertEquals("Home page",homePageTitle);
	  
	driver.findElement(By.xpath(".//div[@class='footer-container']//a[contains(@title,'My Account')]") ).click() ;
	driver.findElement(By.xpath("//a[@title='Create an Account']//span[contains(.,'Create an Account')]")).click();
	driver.findElement(By.xpath(".//div[@class=\"account-cart-wrapper\"]//span[text()='Account']")).click();
	driver.findElement(By.xpath(".//div[@id=\"header-account\"]//a[@title=\"Log In\"]")).click();
	
	urlPage = driver.getCurrentUrl();
	Assert.assertEquals("http://live.guru99.com/index.php/customer/account/login/",urlPage);
	
	driver.findElement(By.xpath("//a[@title='Create an Account']//span[contains(.,'Create an Account')]")).click();
	urlPage = driver.getCurrentUrl();
	Assert.assertEquals("http://live.guru99.com/index.php/customer/account/create/",urlPage);
	driver.close();
  }
  public void TC02_LoginEmpty()
  {
	  driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//maximize browser len
		driver.manage().window().maximize();

	  driver.get("http://live.guru99.com");
	  
	  driver.findElement(By.xpath(".//div[@class='footer-container']//a[contains(@title,'My Account')]") ).click();
	  driver.findElement(By.xpath(".//button[@id='send2'][@type=\"submit\"]")).submit();
	  
	  Assert.assertTrue(driver.findElement(By.xpath(".//div[@id='advice-required-entry-email']")).isDisplayed());
	  Assert.assertTrue(driver.findElement(By.xpath(".//div[@id='advice-required-entry-pass']")).isDisplayed());
  }
  @AfterClass
  public void afterClass() {
	  //quit driver
	  //driver.quit();

  }

}
