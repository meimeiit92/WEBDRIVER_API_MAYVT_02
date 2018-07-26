package webdriver;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
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
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(By.xpath("//a[text()='Hover over me']"));
		action.moveToElement(we).perform();
		Assert.assertEquals("Hooray!", driver.findElement(By.xpath("//div[@class='tooltip-inner']")).getText());

		driver.get("http://www.myntra.com/");
		WebElement we1 = driver
				.findElement(By.xpath("//span[@class='myntraweb-sprite desktop-iconUser sprites-user']"));
		action.moveToElement(we1).perform();
		driver.findElement(By.xpath("//a[@href='/login?referer=https://www.myntra.com/']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='login-box']")).isDisplayed());
	}

	@Test
	public void TC02_SelectMutilpleItem() {
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");
		List<WebElement> listItems = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		Actions moveItem = new Actions(driver);
		moveItem.clickAndHold(listItems.get(0)).moveToElement(listItems.get(3)).release().perform();
		List<WebElement> listItems1 = driver
				.findElements(By.xpath("//li[@class='ui-state-default ui-selectee ui-selected']"));
		Assert.assertTrue(listItems1.size() == 4);

	}

	@Test
	public void TC03_DoubleClick() {
		driver.get("http://www.seleniumlearn.com/double-click");
		WebElement doublicClickMeBtn = driver.findElement(By.xpath("//button[text()='Double-Click Me!']"));
		Actions action = new Actions(driver);
		action.doubleClick(doublicClickMeBtn).perform();
		Alert alert = driver.switchTo().alert();
		String textOnAlert = alert.getText();
		Assert.assertEquals("The Button was double-clicked.", textOnAlert);
		alert.accept();
	}

	@Test
	public void TC04_RightClick() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		WebElement rightClickMeBtn = driver.findElement(By.xpath("//span[text()='right click me']"));
		Actions action = new Actions(driver);
		action.contextClick(rightClickMeBtn).perform();
		WebElement hoverText = driver.findElement(By.xpath(
				"//li[contains(@class,'context-menu-visible') and contains(@class,'context-menu-hover')]/span[text()='Quit']"));
		action.moveToElement(hoverText).perform();
		driver.findElement(By.xpath(
				"//li[contains(@class,'context-menu-visible') and contains(@class,'context-menu-hover')]/span[text()='Quit']"))
				.click();
		;

		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	@Test
	public void TC05_DragDropElement() throws InterruptedException {
		driver.get("http://demos.telerik.com/kendo-ui/dragdrop/angular");
		driver.findElement(By.xpath("//a[@class='optanon-allow-all']")).click();
		Thread.sleep(3000);
		WebElement sourceButton = driver.findElement(By.xpath("//div[@id='draggable']"));
		WebElement targetButton = driver.findElement(By.xpath("//div[@id='droptarget']"));
		Actions action = new Actions(driver);
		action.dragAndDrop(sourceButton, targetButton).build().perform();
		Assert.assertEquals("You did great!", driver.findElement(By.xpath("///div[@id='droptarget']")).getText());

		driver.get("http://jqueryui.com/resources/demos/droppable/default.html");
		WebElement sourceButton2 = driver.findElement(By.xpath("//div[@id='draggable']"));
		WebElement targetButton2 = driver.findElement(By.xpath("//div[@id='droppable']"));

		action.dragAndDrop(sourceButton2, targetButton2).build().perform();
		Assert.assertEquals("Dropped!", driver.findElement(By.xpath("///div[@id='droppable']")).getText());

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
