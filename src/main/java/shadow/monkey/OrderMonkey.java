package shadow.monkey;

import org.openqa.selenium.By;

/**
 * Created by bdanglot on 22/08/16.
 */
public class OrderMonkey extends MonkeyImpl {

	private String email;

	private String password;

	private boolean signedIn;

	private boolean signedUp;

	public OrderMonkey(String adr) {
		super(adr);
		this.signedUp = false;
		this.email = null;
		this.password = null;
		this.signedIn = false;
	}

	@Override
	public void doRequest() throws Exception {
		if (!this.signedUp)
			this.signedUp = this.signUp();
		else if (!this.signedIn)
			this.signedIn = this.signIn();
		else
			System.out.println(this.driver.getCurrentUrl());
	}

	private boolean signUp() throws InterruptedException {
		boolean done = false;
		if (!this.driver.getCurrentUrl().endsWith("register"))
			this.driver.findElements(By.id("cart_info")).get(0).findElements(By.tagName("a")).get(1).click();
		else {
			this.email = super.getRandomWord() + "@" + this.getRandomWord() + ".com";
			this.driver.findElement(By.id("customer.emailAddress")).sendKeys(this.email);
			Thread.sleep(sleepTask);
			this.driver.findElement(By.id("customer.firstName")).sendKeys(super.getRandomWord());
			Thread.sleep(sleepTask);
			this.driver.findElement(By.id("customer.lastName")).sendKeys(super.getRandomWord());
			Thread.sleep(sleepTask);
			this.password = this.getRandomWord();
			this.driver.findElement(By.id("password")).sendKeys(this.password);
			Thread.sleep(sleepTask);
			this.driver.findElement(By.id("passwordConfirm")).sendKeys(this.password);
			Thread.sleep(sleepTask);
			this.driver.findElements(By.tagName("input")).get(8).click();
			done = true;
		}
		Thread.sleep(sleepTask);
		return done;
	}

	private boolean signIn() throws InterruptedException {
		boolean done = false;
		if (!this.driver.getCurrentUrl().endsWith("login"))
			this.driver.findElements(By.id("cart_info")).get(0).findElements(By.tagName("a")).get(0).click();
		else {
			this.driver.findElement(By.name("j_username")).sendKeys(this.email);
			Thread.sleep(sleepTask);
			this.driver.findElement(By.name("j_password")).sendKeys(this.password);
			Thread.sleep(sleepTask);
			this.driver.findElement(By.tagName("form")).submit();
			done = true;
		}
		Thread.sleep(sleepTask);
		return done;
	}

}
