package webdriver;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic10_VerifyAssert {
	WebDriver driver;
	public static String today;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}

	@Test
	public void TC01_ImplicitWait() {
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@id='start']/button")).click();
		Assert.assertEquals("Hello World!", driver.findElement(By.xpath("//div[@id='finish']/h4")).getText());

	}

	@Test
	public void TC02_ExplicitWait() {
		driver.get(
				"http://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@id='ctl00_ContentPlaceholder1_RadCalendar1']")));
		System.out.println(driver.findElement(By.xpath("//span[text()=\"No Selected Dates to display.\"]")).getText());

		today = CommonFunction.getCurrentDay();
		// This is from date picker table
		WebElement dateWidgetFrom = driver
				.findElement(By.xpath("//table[@id='ctl00_ContentPlaceholder1_RadCalendar1_Top']/tbody"));
		List<WebElement> columns = dateWidgetFrom.findElements(By.tagName("td"));

		for (WebElement cell : columns) {
			if (cell.getText().equals(today)) {
				cell.click();
				break;
			}
		}
		new WebDriverWait(driver, 10)
				.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='raDiv']")));
		String dayMonth = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']")).getText();
		if (dayMonth.contains(String.format("Monday, August {0}, 2018", today))) {
			System.out.println("Choose day successful");
		} else {
			System.out.println("Failed");
		}
	}

	@Test
	public void TC03_FluentWait() {
		driver.get("https://stuntcoders.com/snippets/javascript-countdown/");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@id='javascript_countdown_time']")));
		Wait<WebDriver> wait1 = new FluentWait<WebDriver>(driver)
			    .withTimeout(30, SECONDS)
			    .pollingEvery(5, SECONDS)
			    .ignoring(NoSuchElementException.class);
			WebElement foo = wait1.until(new Function<WebDriver, WebElement>() 
			{
			  public WebElement apply(WebDriver driver) {
			  return driver.findElement(By.xpath("//div[@id='javascript_countdown_time']"));
			}
			});
	}

	@Test
	public void TC04_() {
		driver.get("http://toolsqa.wpengine.com/automation-practice-switch-windows/");

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			    .withTimeout(30, SECONDS)
			    .pollingEvery(5, SECONDS)
			    .ignoring(NoSuchElementException.class);
			WebElement foo = wait.until(new Function<WebDriver, WebElement>() 
			{
			  public WebElement apply(WebDriver driver) {
			  return driver.findElement(By.xpath("//div[@id='javascript_countdown_time']"));
			}

	}

	@AfterClass
	public void afterClass() {
		// driver.quit();
	}

}
