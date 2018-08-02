package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic08_Javascripts {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC01_JavascriptExcecutor() {
		driver.get("http://live.guru99.com/");
		JavascriptExecutor js = (JavascriptExecutor) driver;

		Assert.assertEquals("live.guru99.com", js.executeScript("return document.domain").toString());
		Assert.assertEquals("http://live.guru99.com/", js.executeScript("return document.URL").toString());

		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[text()='Mobile']")));
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[text()='Samsung Galaxy']")));
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[@class='button btn-cart']")));

		String sText = js.executeScript("return document.documentElement.innerText;").toString();
		Assert.assertTrue(sText.contains("Samsung Galaxy was added to your shopping cart."));

		driver.get("http://live.guru99.com/");
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[text()='Privacy Policy']")));
		Assert.assertEquals("Privacy Policy", js.executeScript("return document.title").toString());

		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		List<WebElement> we = driver.findElements(By.xpath("//tbody//following-sibling::tr/th[text()='WISHLIST_CNT']"));
		Assert.assertTrue(we.size() == 1);

		js.executeScript("window.location = 'http://demo.guru99.com/v4/'");
		Assert.assertEquals("demo.guru99.com", (String) js.executeScript("return document.domain"));
	}

	@Test
	public void TC02_RemoveAttribute() {
		driver.get("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_input_disabled");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.switchTo().frame("iframeResult");
		WebElement element = driver.findElement(By.xpath("//input[@name='lname']"));
		js.executeScript("arguments[0].removeAttribute('disabled');", element);
		String lastName = CommonFunction.RandomString();
		driver.findElement(By.xpath("//input[@name='lname']")).sendKeys(lastName);

		driver.findElement(By.xpath("//input[@type='submit']")).click();
		String stringAfter = driver.findElement(By.xpath("//div[@class='w3-container w3-large w3-border']")).getText();
		Assert.assertTrue(stringAfter.contains(lastName));

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
