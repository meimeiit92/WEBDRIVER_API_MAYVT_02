package webdriver;

import org.testng.annotations.Test;

import java.util.Random;
import java.util.UUID;

import bsh.Console;

import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
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
  @Test 
  public void TC02_LoginEmpty()
  {
	  driver.get("http://live.guru99.com");
	  
	  driver.findElement(By.xpath(".//div[@class='footer-container']//a[contains(@title,'My Account')]") ).click();
	  driver.findElement(By.xpath(".//button[@id='send2'][@type=\"submit\"]")).submit();
	  
	  Assert.assertTrue(driver.findElement(By.xpath(".//div[@id='advice-required-entry-email']")).isDisplayed());
	  Assert.assertTrue(driver.findElement(By.xpath(".//div[@id='advice-required-entry-pass']")).isDisplayed());
  }
  
  @Test  
  public void TC03_LoginWithEmailIncorrect ()
  {
	  String email; 
	  driver.get("http://live.guru99.com");
	  driver.findElement(By.xpath(".//div[@class='footer-container']//a[contains(@title,'My Account')]") ).click() ;
	  
	  email = RandomString(); 
	  WebElement field  = driver.findElement(By.xpath(".//input[@id='email']")); 
	  field.clear();
	  field.sendKeys(email);
	  driver.findElement(By.xpath(".//button[@id='send2'][@type=\"submit\"]")).submit(); 
	  //  Verify error message xuất hiện:  Please enter a valid email address. For example johndoe@domain.com.
	  Assert.assertTrue(driver.findElement(By.xpath(".//div[@id=\"advice-validate-email-email\"]")).isDisplayed());
  }
  @Test  
  public void TC04_LoginWithPasswordIncorrect() 
  {
	  String email; 
	  String passWord ; 
	  driver.get("http://live.guru99.com");
	  driver.findElement(By.xpath(".//div[@class='footer-container']//a[contains(@title,'My Account')]") ).click() ;
	  
	  email = RandomString();
	  passWord = RandomPassword();
	  WebElement field  = driver.findElement(By.xpath(".//input[@id='email']")); 
	  field.clear();
	  field.sendKeys(email);
	  field  = driver.findElement(By.xpath(".//input[@id='pass']")); 
	  field.clear();
	  field.sendKeys(passWord);
	  driver.findElement(By.xpath(".//button[@id='send2'][@type=\"submit\"]")).submit(); 
	  
	  Assert.assertTrue(driver.findElement(By.xpath(".//div[@id='advice-validate-password-pass']")).isDisplayed());
  }
  
  @Test 
  public void TC05_CreateAnAccount()
  {
	  driver.get("http://live.guru99.com");
	  String url ="http://live.guru99.com/index.php/";
	  
	  String email = RandomString();
	  String emailValid = RandomValidEmailAddress(email);
	  String firstName = RandomString();
	  String middleName = RandomString();
	  String lastName = RandomString();
	  String password = RandomString();
	  WebElement field;
		driver.findElement(By.xpath(".//div[@class='footer-container']//a[contains(@title,'My Account')]") ).click() ;
		driver.findElement(By.xpath("//a[@title='Create an Account']//span[contains(.,'Create an Account')]")).click();
		field = driver.findElement(By.xpath(".//input[@id='firstname']"));
		field.clear();
		field.sendKeys(firstName);
		
		field = driver.findElement(By.xpath(".//input[@id='middlename']"));
		field.clear();
		field.sendKeys(middleName);
		
		field = driver.findElement(By.xpath(".//input[@id='lastname']"));
		field.clear();
		field.sendKeys(lastName);
		
		field = driver.findElement(By.xpath(".//input[@id='email_address']"));
		field.clear();
		field.sendKeys(emailValid);
		
		field = driver.findElement(By.xpath(".//input[@id='password']"));
		field.clear();
		field.sendKeys(password);
		
		field = driver.findElement(By.xpath(".//input[@id='confirmation']"));
		field.clear();
		field.sendKeys(password);
		
		driver.findElement(By.xpath(".//form[@id=\"form-validate\"]//div[@class=\"buttons-set\"]//button[@title=\"Register\"]")).click();
		Assert.assertTrue(driver.findElement(By.xpath(".//div[@class = \"my-account\"]//span[contains(.,'Thank you for registering with Main Website Store')]")).isDisplayed());
		
		driver.findElement(By.xpath(".//div[@class = \"account-cart-wrapper\"]//span[contains(.,'Account')]")).click();
		driver.findElement(By.xpath(".//div[@class=\"links\"]//a[@title=\"Log Out\"]")).click();
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String urlPage = driver.getCurrentUrl();
		Assert.assertEquals(url,urlPage);
  }
  public String RandomString()
  {
	  String uuid = UUID.randomUUID().toString();
	    return uuid;
  }
  public String RandomPassword()
  {
	  int randomInt;
	  String random;
	  Random rg = new Random();
	  randomInt = rg.nextInt(4 );
	  random= Integer.toString(randomInt);
		  return random;
  }
  public static String RandomValidEmailAddress(String email) {
	  String emailValid = email+"@gmail.com";
	  return emailValid;
	}
  
  @AfterClass
  public void afterClass() {
	  //quit driver
	  //driver.quit();

  }

}
