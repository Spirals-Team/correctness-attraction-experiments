package shadow.monkey;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bdanglot on 16/08/16.
 */
public class RandomMonkey extends MonkeyImpl {

	public RandomMonkey(String adr) {
		super(adr);
	}

	public RandomMonkey(String adr, int seed) {
		super(adr, seed);
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
			element.sendKeys(super.getRandomWord());
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
		} catch (org.openqa.selenium.remote.SessionNotFoundException sessionNotFound) {
			this.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Thread.sleep(sleepTask);
		try {
			if (!driver.getCurrentUrl().startsWith(adr))
				this.init();
		} catch (UnhandledAlertException e) {
			driver.switchTo().alert().dismiss();
		} catch (org.openqa.selenium.remote.SessionNotFoundException sessionNotFound) {
			this.quit();
		}
	}

}
