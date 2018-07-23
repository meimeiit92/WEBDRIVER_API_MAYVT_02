package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic06_UserInteraction {
	WebDriver driver;
	
  @BeforeClass
  public void beforeClass() {
		driver = new FirefoxDriver();
  }
  
  @Test
  public void TC01_MouseHover() {
	  driver.get("http://daominhdam.890m.com/");
	  
	  Actions action = new Actions (driver); 
	  WebElement we = driver.findElement(By.xpath("//a[text()='Hover over me']"));
	  action.moveToElement(we).build().perform();
	  
  }

  @AfterClass
  public void afterClass() {
		//driver.quit();
  }

}
