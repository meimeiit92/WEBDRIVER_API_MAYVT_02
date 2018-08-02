package webdriver;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic09_UploadFile {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void TC01_UploadFileBySendKeys() {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
		WebElement parent = driver.findElement(By.xpath("//tbody[@class='files']"));
		List<WebElement> listItemsBefore = parent.findElements(By.xpath(".//*"));

		fileInput.sendKeys("/Users/meimei/Downloads/IMG_2023.JPGIMG_2023.JPG");

		List<WebElement> listItemsAfter = parent.findElements(By.xpath(".//*"));
		Assert.assertTrue(listItemsBefore.size() == 0 && listItemsAfter.size() > 0);
	}

	@Test
	public void TC02_UploadFileByAutoIT() throws IOException {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		WebElement parent = driver.findElement(By.xpath("//tbody[@class='files']"));
		List<WebElement> listItemsBefore = parent.findElements(By.xpath(".//*"));
		driver.findElement(By.xpath("//input[@type='file']")).click();
		String filePath = "E:\\PROJECT ROOT\\ROOT_WEBDRIVER_API\\Selenium_Training_Official\\upload\\image.png";
		Runtime.getRuntime().exec(new String[] { ".\\upload\\ie.exe", filePath });

		List<WebElement> listItemsAfter = parent.findElements(By.xpath(".//*"));
		Assert.assertTrue(listItemsBefore.size() == 0 && listItemsAfter.size() > 0);
	}

	@Test
	public void TC03_UploadFileByRobotClass() throws IOException, AWTException, InterruptedException {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		WebElement parent = driver.findElement(By.xpath("//tbody[@class='files']"));
		List<WebElement> listItemsBefore = parent.findElements(By.xpath(".//*"));
		driver.findElement(By.xpath("//input[@type='file']")).click();
		StringSelection select = new StringSelection("E:\\PROJECT TRAINING\\Selenium_Training\\upload\\image.png");

		// Copy to clipboard
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);

		// Click
		driver.findElement(By.className("fileinput-button")).click();

		Robot robot = new Robot();
		Thread.sleep(1000);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		Thread.sleep(1000);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		List<WebElement> listItemsAfter = parent.findElements(By.xpath(".//*"));
		Assert.assertTrue(listItemsBefore.size() == 0 && listItemsAfter.size() > 0);
	}

	@Test
	public void TC04_UploadFile() throws IOException {
		driver.get("https://encodable.com/uploaddemo/");
		WebElement fileInput = driver.findElement(By.xpath("//input[@id='uploadname1']"));
		fileInput.sendKeys("/Users/meimei/Downloads/IMG_2023.JPGIMG_2023.JPG");
		
		Select select = new Select(driver.findElement(By.xpath("//select[@class='upform_field picksubdir_field']")));
		select.selectByVisibleText("/uploaddemo/files/");
		
		String email = CommonFunction.RandomValidEmailAddress(CommonFunction.RandomString());
		String firstName = CommonFunction.RandomString();
		
		driver.findElement(By.xpath("//input[@id='newsubdir1']")).sendKeys(CommonFunction.RandomString());
		driver.findElement(By.xpath("//input[@id='formfield-email_address']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@id='formfield-first_name']")).sendKeys(firstName);
		
		driver.findElement(By.xpath("//input[@id='uploadbutton']")).click();
		
		WebElement Email = driver.findElement(By.xpath("//dl[@id='fcuploadsummary']"));
		List<WebElement> emailList = Email.findElements(By.xpath(".//*"));
		
		WebElement name = driver.findElement(By.xpath("//dl[@id='fcuploadsummary']"));
		List<WebElement> firstNameList = name.findElements(By.xpath(".//*"));
		boolean isTrue = false;
		boolean isNameTrue = false;
		for (WebElement e : emailList) {
			if (e.getText().equals(email))
			{
				isTrue = true;
				Assert.assertTrue(isTrue);
				break;
			}
		}
		if (isTrue!=true)
		{
			Assert.assertTrue(isTrue);
		}
		for (WebElement e : firstNameList) {
			if (e.getText().equals(email))
			{
				isNameTrue = true;
				Assert.assertTrue(isNameTrue);
				break;
			}
		}
		if (isNameTrue!=true)
		{
			Assert.assertTrue(isNameTrue);
		}

		driver.findElement(By.xpath("//a[text()='View Uploaded Files']")).click();
		driver.findElement(By.xpath("//tbody/following-sibling::tr[@class='kutty']")).click();
		
	}

	@AfterClass
	public void afterClass() {
	}

}
