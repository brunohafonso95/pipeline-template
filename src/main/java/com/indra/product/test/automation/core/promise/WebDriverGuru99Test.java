
package com.indra.product.test.automation.core.promise;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author msergio
 * @since Setembro de 2018
 * @version {version}
 */
public class WebDriverGuru99Test {
	ChromeDriver driver;
	WebDriver webDriver;

	@Test
	public void test() {

		System.setProperty("webdriver.chrome.driver", "bin\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String eTitle = "Demo Guru99 Page";
		String aTitle = driver.getTitle();
		
		driver.get("http://demo.guru99.com/test/guru99home/");
		driver.manage().window().maximize();

		if (aTitle.equals(eTitle)) {
			assertEquals("Demo Guru99 Page", eTitle);
		}

		// close browser
		driver.close();
	}

}