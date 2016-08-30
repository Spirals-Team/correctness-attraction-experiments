package shadow.monkey;

import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by bdanglot on 22/08/16.
 */
public abstract class MonkeyImpl implements Monkey {

	protected static final int sleepTask = 500;

	protected static final int sleepInit = 1000;

	protected WebDriver driver;

	protected Random random;

	protected String adr;

	private List<String> words;

	public MonkeyImpl(String adr, int seed) {
		try {
			this.random = new Random(seed);
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

	public MonkeyImpl(String adr) {
		this(adr, 23);
	}

	public void init() throws InterruptedException {
		try {
			this.driver.get(adr);
			Thread.sleep(sleepInit);
		} catch (UnhandledAlertException e) {
			this.driver.switchTo().alert().dismiss();
			this.init();
		}
	}

	public void quit() {
		this.driver.quit();
	}

	protected String getRandomWord() {
		String a;
		while ((a = this.words.get(this.random.nextInt(this.words.size()))).contains("'")) ;
		return a;
	}


}
