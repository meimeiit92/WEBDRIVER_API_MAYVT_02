package Practive_TestNG;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestNG_DataProvider {
	WebDriver driver;

	@Test(dataProvider = "dp")
	public void f(String n, String s) {
		driver.get("http://demo.guru99.com/v4/");
		driver.findElement(By.xpath("//input[@name='uid']")).sendKeys(n);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(s);
		driver.findElement(By.name("btnLogin")).click();

	}

	@DataProvider
	public Object[][] dp(Method method) {
		Object[][] result = null;
		if (method.getName().equals("f"))
		{
			result = new Object[][]
					{{"01","Nhds"},{"02","Mdhfiush"}};
		}
		else if (method.getName().equals("f"))
		{	
			result = new Object[][]
					{{"03","Nhds"},{"04","Mdhfiush"}};
		}
		return result;
	}

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterClass
	public void afterClass() {
	}

}
