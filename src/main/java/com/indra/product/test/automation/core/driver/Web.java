package com.indra.product.test.automation.core.driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Class Web
 */
public class Web {

	private WebDriver driver;
	private Logger log = Logger.getLogger(Web.class.getName());
	private String proxy, user, pass, site;

	public Web(String site) {
		try {
			// Driver Padrão Chrome
			driver = DriverBuilder("");
			driver.navigate().to("http://" + site);
			log.info("Carregando arquivo de WebDriver: " + driver);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public Web(String navegador, String site) {
		// Escolher um navegador
		this.site = site;
		try {
			driver = DriverBuilder("");
			driver.get("http://" + site);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public WebDriver DriverBuilder(String navegador) {
		loadProperty();
		WebDriver driver;
		if (navegador.contains("firefox")) {
			WebDriverManager.firefoxdriver().proxy(proxy).proxyUser(user).proxyPass(pass).setup();
			driver = new FirefoxDriver();
		} else if (navegador.contains("ie")) {
			WebDriverManager.iedriver().proxy(proxy).proxyUser(user).proxyPass(pass).setup();
			InternetExplorerOptions capability = new InternetExplorerOptions();
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			capability.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, site);
			driver = new InternetExplorerDriver(capability);
		} else if (navegador.contains("headless")) {
			driver = null;
		} else {
			//WebDriverManager.chromedriver().setup();
			WebDriverManager.chromedriver()
			.proxy(proxy)
			.proxyUser(user)
			.proxyPass(pass).setup();
			//System.setProperty("webdriver.chrome.driver", System.getenv("ChromeWebDriver") + "/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--lang=pt");
			driver = new ChromeDriver();
		}
		return driver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void loadProperty() {
		Properties prop = new Properties();
		InputStream input;
		try {
			input = new FileInputStream("src/main/resources/configuration.properties");
			prop.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		proxy = prop.getProperty("proxy");
		user = prop.getProperty("user");
		pass = prop.getProperty("senha");
	}
}
