package selenium;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class HW01 {
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
	  String homePageURL = driver.getCurrentUrl();
	  
	  Assert.assertEquals("Home page",homePageTitle);
	  Assert.assertEquals("http://live.guru99.com", homePageURL);

  }

  @AfterClass
  public void afterClass() {
	  //quit driver
	  driver.quit();

  }

}
