package shadow.monkey;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by bdanglot on 16/08/16.
 */
public class Monkey {

	private static final int sleepTask = 500;

	private static final int sleepInit = 1000;

	private String adr;

	private WebDriver driver;

	private List<String> words;

	private Random random;

	public Monkey(String adr) {
		try {
			this.random = new Random(23);
			System.setProperty("webdriver.chrome.driver", "lib/chromedriver");
			this.driver = new ChromeDriver();
			BufferedReader reader = new BufferedReader(new FileReader("/usr/share/dict/words"));
			String currentLine;
			this.words = new ArrayList<>();
			while ((currentLine = reader.readLine()) != null)
				this.words.add(currentLine);
			reader.close();
			this.adr = adr;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clickOnLink() throws Exception {
		List<WebElement> elements = driver.findElements(By.tagName("a"));
		elements = elements.stream().filter(
				button -> button.isDisplayed() && button.isEnabled()
		).collect(Collectors.toList()
		);
		WebElement element = elements.get(random.nextInt(elements.size()));
		element.click();
		Thread.sleep(sleepTask);
	}

	private boolean clickOnButtons() throws Exception {
		List<WebElement> elements = driver.findElements(By.tagName("input"));
		elements = elements.stream().filter(
				button -> button.isDisplayed() && button.isEnabled() &&
						("button".equals(button.getAttribute("type")) ||
								"submit".equals(button.getAttribute("type")))
		).collect(Collectors.toList()
		);
		if (!elements.isEmpty()) {
			WebElement element = elements.get(random.nextInt(elements.size()));
			element.click();
			Thread.sleep(sleepTask);
			return true;
		} else
			return false;
	}

	private boolean typeOnField() throws Exception {
		List<WebElement> elements = driver.findElements(By.tagName("input"));
		elements = elements.stream().filter(button ->
				button.isDisplayed() && button.isEnabled() &&
						"text".equals(button.getAttribute("type"))
		).collect(Collectors.toList()
		);
		if (!elements.isEmpty()) {
			WebElement element = elements.get(random.nextInt(elements.size()));
			element.sendKeys(words.get(random.nextInt(words.size())));
			element.submit();
			Thread.sleep(sleepTask);
			return true;
		} else
			return false;
	}

	public void doRequest() throws Exception {
		try {
			if (driver.findElement(By.tagName("h1")).getText().startsWith("HTTP Status 500") ||
					driver.findElement(By.tagName("h1")).getText().startsWith("HTTP Status 404"))
				this.init();
			else if (!((ChromeDriver) driver).findElementsByClassName("simplemodal-container").isEmpty()) {
				((ChromeDriver) driver).findElementByClassName("modalCloseImg").click();
			} else {
				int choice = random.nextInt(15);
				boolean action = true;
				if (choice > 13)
					action = typeOnField();
				else if (choice > 8)
					action = clickOnButtons();
				else
					clickOnLink();
				if (!action)
					clickOnLink();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Thread.sleep(sleepTask);
		try {
			if (!driver.getCurrentUrl().startsWith(adr))
				this.init();
		} catch (UnhandledAlertException e) {
			driver.switchTo().alert().dismiss();
		}
	}


	public void init() throws InterruptedException {
		driver.get(adr);
		Thread.sleep(sleepInit);
	}

	public void quit() {
		this.driver.quit();
	}

}
