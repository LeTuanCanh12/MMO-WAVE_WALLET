package BE;

import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.poi.ss.formula.BaseFormulaEvaluator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WaveOcean {
	static WebDriver driver;

	public WaveOcean() {
		// Khởi tạo WebDriver

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://web.telegram.org/k/");
	}

	public void importWallet(String address) {
		// TODO Auto-generated method stub
		try {
			switchToParticleWallet();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		try {
			Thread.sleep(2000);
			WebElement openMenu = driver.findElement(By.cssSelector("button[class='relative p-4']"));
			openMenu.click();
			Thread.sleep(2000);
			WebElement buttonImport = driver.findElement(By.cssSelector("button[class='btn btn-add-account']"));
			buttonImport.click();
			Thread.sleep(2000);
			WebElement pasteWallet = driver.findElement(By.cssSelector("textarea"));
			pasteWallet.sendKeys(address);
			Thread.sleep(2000);
			WebElement confirm = driver.findElement(By.cssSelector("button[class='btn-continue btn-common w-full']"));
			confirm.click();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

	}

	public void action(String time) throws InterruptedException {
		Thread.sleep(1000);
	
		try {
			try {
				switchToParticleWallet();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			claimOcean();
			Thread.sleep(1000);
			// mo menu ban dau
			try {
				WebElement openMenu = driver.findElement(By.cssSelector("button[class='relative p-4']"));
				openMenu.click();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			// lay danh sach vi
			getListWallet(time);
		} catch (Exception e) {
			// TODO: handle exception
			restart(time);
		}
	}

	private void restart(String setTime) throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(20));
		reset();
		driver.navigate().refresh();

		WebElement open = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("div[class='new-message-bot-commands is-view'")));

		open.click();
		Thread.sleep(1000);
		try {
			WebElement launch = driver.findElement(By.xpath("/html/body/div[7]/div/div[2]/button[1]"));
			launch.click();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Thread.sleep(6000);
		action(setTime);
	}

	private void getListWallet(String setTime) throws InterruptedException {
		// TODO Auto-generated method stub
		List<WebElement> listTotalWallet = new ArrayList();
		List<WebElement> listToRun = new ArrayList();
		listTotalWallet = driver.findElements(By.cssSelector("div[class='account-item']"));
		int totalCountWallet = listTotalWallet.size() - 1;
		int count = 0;
		// lap lai thao tac moi 2h
		for (int time = 0; time < 10000000; time++) {
			if (count != 0) {
				totalCountWallet = listTotalWallet.size();
			}
			for (int i = 0; i < totalCountWallet; i++) {

				if (count != 0 && i != 0) {

					listToRun = driver.findElements(By.cssSelector("div[class='account-item']"));
					System.out.println("index : " + i);
					System.out.println("Vi hien tai: " + listToRun.get(i - 1).getText());
					WebElement choseWallet = listToRun.get(i - 1);

					choseWallet.click();
					Thread.sleep(1000);
					claimOcean();
					if (i == 6) {
						Actions actions = new Actions(driver);
						actions.sendKeys(Keys.DOWN).perform();
					}
				} else {

					listToRun = driver.findElements(By.cssSelector("div[class='account-item']"));
					System.out.println("index : " + i);
					System.out.println("Vi hien tai: " + listToRun.get(i).getText());
					WebElement choseWallet = listToRun.get(i);

					choseWallet.click();
					Thread.sleep(1000);
					claimOcean();
					if (i == 5) {
						Actions actions = new Actions(driver);
						actions.sendKeys(Keys.DOWN).perform();
					}
				}

			}

			count++;
			System.out.println("done 1 lan claim 2h");
			Thread.sleep(60000 * Integer.valueOf(setTime));
		}
	}

	private void claimOcean() throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		Thread.sleep(2000);
		WebElement claimNow = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("div[class='item-1'")));
		claimNow.click();
		Thread.sleep(1000);
		try {
			WebElement claimOcean = driver.findElement(By.cssSelector("div[class='claim cursor-pointer']"));
			claimOcean.click();
			System.out.println("claim xong");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Chua toi gio claim");
		}
		reset();
		Thread.sleep(1500);
		WebElement back = driver.findElement(By.cssSelector("button[class='btn-icon popup-close']"));
		back.click();
		switchToParticleWallet();
		Thread.sleep(1500);
		WebElement openMenu = driver.findElement(By.cssSelector("button[class='relative p-4']"));
		openMenu.click();
		Thread.sleep(1500);
	}

	public void reset() {
		driver.switchTo().defaultContent();
	}

	private void switchToParticleWallet() {
		// TODO Auto-generated method stub
		driver.switchTo().frame(driver.findElement(By.cssSelector("iframe")));
	}

}