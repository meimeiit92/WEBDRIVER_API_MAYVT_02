package webdriver;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic05_Button_CheckBox_Alert {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void TC01_Button() {
		String urlPage;
		driver.get("http://live.guru99.com");
		driver.findElement(By.xpath(".//div[@class='footer-container']//a[contains(@title,'My Account')]")).click();

		urlPage = driver.getCurrentUrl();
		Assert.assertEquals("http://live.guru99.com/index.php/customer/account/login/", urlPage);

		WebElement element = driver
				.findElement(By.xpath("//a[@title='Create an Account']//span[contains(.,'Create an Account')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		urlPage = driver.getCurrentUrl();
		Assert.assertEquals("http://live.guru99.com/index.php/customer/account/create/", urlPage);
	}

	@Test
	public void TC02_CheckBox() {
		driver.get("http://demos.telerik.com/kendo-ui/styling/checkboxes");
		driver.findElement(By.xpath(".//label[text() = 'Dual-zone air conditioning']")).click();
		WebElement checkbox = driver.findElement(
				By.xpath(".//label[text() = 'Dual-zone air conditioning']/preceding-sibling::input[@type='checkbox']"));

		Assert.assertTrue(checkbox.isSelected());

		if (checkbox.isSelected()) {
			driver.findElement(By.xpath(".//label[text() = 'Dual-zone air conditioning']")).click();
			Assert.assertFalse(checkbox.isSelected());
		} else {
			System.out.println("Checkbox is deselected");
		}

	}

	@Test
	public void TC03_RadioButton() {
		driver.get("http://demos.telerik.com/kendo-ui/styling/radios");
		driver.findElement(By.xpath(".//label[text() = '2.0 Petrol, 147kW']")).click();

		WebElement radiobutton = driver.findElement(By.xpath(".//label[text() = '2.0 Petrol, 147kW']"));
		if (!radiobutton.isSelected()) {
			driver.findElement(By.xpath(".//label[text() = '2.0 Petrol, 147kW']")).click();
		} else {
			System.out.println("Radio button is selected");
		}

	}

	@Test
	public void TC04_JSAlert() {
		driver.get("http://daominhdam.890m.com/");
		driver.findElement(By.xpath(".//button[text()='Click for JS Alert']")).click();
		Alert alert = driver.switchTo().alert();
		String textOnAlert = alert.getText();

		Assert.assertEquals(textOnAlert, "I am a JS Alert");

		alert.accept();
		Assert.assertEquals("You clicked an alert successfully",
				driver.findElement(By.xpath(".//*[@id='result']")).getText());
	}

	@Test
	public void TC05_JSConfirm() {
		driver.get("http://daominhdam.890m.com/");
		driver.findElement(By.xpath(".//button[text()='Click for JS Confirm']")).click();
		Alert alert = driver.switchTo().alert();
		String textOnAlert = alert.getText();

		Assert.assertEquals(textOnAlert, "I am a JS Confirm");

		alert.dismiss();
		Assert.assertEquals("You clicked: Cancel", driver.findElement(By.xpath(".//*[@id='result']")).getText());

	}

	@Test
	public void TC06_JSPrompt() {
		driver.get("http://daominhdam.890m.com/");
		driver.findElement(By.xpath(".//button[text()='Click for JS Prompt']")).click();
		Alert alert = driver.switchTo().alert();
		String textOnAlert = alert.getText();

		Assert.assertEquals(textOnAlert, "I am a JS prompt");
		String string = "MeiMei";
		alert.sendKeys(string);
		alert.accept();
		Assert.assertEquals("You entered: " + string + "",
				driver.findElement(By.xpath(".//*[@id='result']")).getText());

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
