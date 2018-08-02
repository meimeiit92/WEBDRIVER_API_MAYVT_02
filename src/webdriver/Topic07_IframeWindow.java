package webdriver;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic07_IframeWindow {

	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void TC01_Iframe() {
		String idFrame1 = "vizury-notification-template";
		String idFrame2 = "viz_iframe36255b59cea37f5753cdca9acb0ONR";
		String idFrame3 = "viz_iframe36255b59cea45d140d313030bb3ONR";
		driver.get("http://www.hdfcbank.com/");
		List<WebElement> iframeElements = driver.findElements(By.tagName("iframe"));
		if (iframeElements.size() > 0) {
			for (WebElement e : iframeElements) {
				if (e.getAttribute("id").equals(idFrame1)) {
					driver.switchTo().frame(idFrame1);
					driver.findElement(By.xpath("//div[@id='div-close']")).click();
					driver.switchTo().defaultContent();
				}
				if (e.getAttribute("id").equals(idFrame2)) {
					driver.switchTo().frame(idFrame2);
					Assert.assertEquals(" What are you looking for?",
							driver.findElement(By.xpath("//div[@id='div-close']")).getText());
					driver.switchTo().defaultContent();
				}
				if (e.getAttribute("id").equals(idFrame3)) {
					driver.switchTo().frame(idFrame3);
					List<WebElement> imageElements = driver.findElements(By.xpath("//div[@class='item-list']"));
					Assert.assertTrue(imageElements.size() == 6);
					driver.switchTo().defaultContent();
				}
			}
		}
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='flipBanner']")).isDisplayed());
		List<WebElement> imageElements = driver.findElements(By.xpath("//img[@class='front icon']"));
		Assert.assertTrue(imageElements.size() == 8);

	}

	@Test
	public void TC02_Window() {
		driver.get("http://daominhdam.890m.com/");
		driver.findElement(By.xpath("//a[@href='http://google.com.vn']")).click();
		String parentWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindow : allWindows) {
			if (!runWindow.equals(parentWindow)) {
				driver.switchTo().window(runWindow);
				break;
			}
		}
		String title = driver.getTitle();
		Assert.assertEquals("Google", title);
		driver.close();
		driver.switchTo().window(parentWindow);
	}

	@Test
	public void TC03_MutilWindows() {
		driver.get("http://www.hdfcbank.com/");
		try {
			if (driver.findElement(By.xpath("//img[@class='notimage clickable-element']")).isDisplayed()) {
				driver.findElement(By.xpath("//div[@id='div-close']")).click();
				System.out.println("Element is Visible");
			}
		} catch (NoSuchElementException e) {

			System.out.println("Element is InVisible");
		}
		driver.findElement(By.xpath("//a[@href='/htdocs/common/agri/index.html']")).click();
		String parentWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindow : allWindows) {
			if (!runWindow.equals(parentWindow)) {
				String parent1 = driver.getWindowHandle();
				driver.switchTo().window(runWindow);
				driver.findElement(By.xpath("//div[p='Account Details']")).click();
				closeAllWithoutParentWindows(parent1);
				break;
			}
		}
		String parent2 = driver.getWindowHandle();
		driver.findElement(By.xpath("//a[text()='Privacy Policy']")).click();
		closeAllWithoutParentWindows(parent2);

		driver.findElement(By.xpath("//a[@title='Corporate Social Responsibility']")).click();
		closeAllWithoutParentWindows(parent2);

	}

	public void closeAllWithoutParentWindows(String parentWindow) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			if (!runWindows.equals(parentWindow)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
