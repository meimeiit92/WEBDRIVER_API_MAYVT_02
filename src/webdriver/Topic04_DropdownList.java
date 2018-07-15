package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic04_DropdownList {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {

		driver = new FirefoxDriver();
		// System.setProperty("webdriver.chrome.driver",".\\driver\\chromedriver.exe");
		// driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void DropdownList() {
		driver.get("http://daominhdam.890m.com/");
		Select select = new Select(driver.findElement(By.xpath(".//select[@id='job1']")));
		// Verify Job multiple select 	
		Assert.assertFalse(select.isMultiple());
		
		select.selectByVisibleText("Automation Tester" );
		Assert.assertEquals("Automation Tester",select.getFirstSelectedOption().getText()) ;
		
		select.selectByIndex(3);
		Assert.assertEquals("Mobile Tester",select.getFirstSelectedOption().getText()) ;
		Assert.assertEquals(5, select.getOptions().size());
		
	}
	@Test
	public void TextBoxTextArea() throws InterruptedException 
	{
		// mngr142949  arYnemA
		String email1 = CommonFunction.RandomPassword();
	String email = CommonFunction.RandomValidEmailAddress(CommonFunction.RandomString()+email1) ;
	String name = CommonFunction.RandomString();
	String oldAddress =  CommonFunction.RandomString();
	String newAddress =  CommonFunction.RandomString();
	String oldCity = CommonFunction.RandomString();
	String newCity = CommonFunction.RandomString();
	
		driver.get("http://demo.guru99.com/v4");
		driver.findElement(By.xpath(".//input[@name='uid']")).sendKeys("mngr142949"); 
		driver.findElement(By.xpath(".//input[@name='password']")).sendKeys("arYnemA");
		driver.findElement(By.xpath("//input[@type='submit']")).click() ;
		driver.findElement(By.xpath("//a[contains(.,'New Customer')]")).click() ;
		driver.findElement(By.xpath("//input[@name='name']")).sendKeys(name) ;
		driver.findElement(By.xpath("//input[@value='f']")).click() ;
		driver.findElement(By.xpath(".//*[@id='dob']")).sendKeys("08/09/1992") ;
		driver.findElement(By.xpath("//textarea[@name='addr']")).sendKeys(oldAddress);
		driver.findElement(By.xpath("//input[@name='city']")).sendKeys(oldCity) ;
		driver.findElement(By.xpath("//input[@name='state']")).sendKeys( "viet nam " ) ;
		driver.findElement(By.xpath("//input[@name='pinno']")).sendKeys( "100000" ) ;
		driver.findElement(By.xpath("//input[@name='telephoneno']")).sendKeys( "01838426992" ) ;
		driver.findElement(By.xpath("//input[@name='emailid']")).sendKeys(email ) ;
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys( "123456789" ) ;
		
		driver.findElement(By.xpath("//input[@type='submit']")).click() ;
		
		
		String id = driver.findElement(By.xpath(".//table[@id='customer']//td[contains(.,'Customer ID')]/following-sibling::td")).getText();
		driver.findElement(By.xpath("//a[contains(.,'Edit Customer')]")).click() ;
		driver.findElement(By.xpath("//input[@name='cusid']")).sendKeys(id ) ;
		driver.findElement(By.xpath("//input[@type='submit']")).click() ;
		
		Assert.assertEquals(name, driver.findElement(By.xpath("//input[@name='name']")).getText());
		
		Assert.assertEquals(oldAddress, driver.findElement(By.xpath("//textarea[@name='addr']")).getText());
		
		
		driver.findElement(By.xpath("//textarea[@name='addr']")).sendKeys(newAddress);
		driver.findElement(By.xpath("//input[@name='city']")).sendKeys(newCity) ;
		driver.findElement(By.xpath("//input[@type='submit']")).click() ;
		
		Assert.assertEquals(newCity, driver.findElement(By.xpath("//input[@name='city']")).getText());
		Assert.assertEquals(newAddress, driver.findElement(By.xpath("//textarea[@name='addr']")).getText());

	}

	@AfterClass
	public void afterClass() {
		driver.quit();

	}

}
