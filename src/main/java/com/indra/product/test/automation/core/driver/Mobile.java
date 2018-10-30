package com.indra.product.test.automation.core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class Mobile {

	private AppiumDriverLocalService servico;
	private WebDriver webDriver;

	public void iniciaServidor() {
		// Appium Instalado via NPM (npm install -g appium)
		AppiumServiceBuilder builder;
		final String IP_ADRESS = "127.0.0.1";
		builder = new AppiumServiceBuilder();
		builder.withIPAddress(IP_ADRESS);
		builder.usingPort(4723);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");

		servico = AppiumDriverLocalService.buildService(builder);
		servico.start();
	}

	public void encerraServidor() {
		servico.stop();
	}

	public void preparaEmulador(String pacote, String atividade) {
		DesiredCapabilities desiredCapabilities;
		desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("deviceName", "emulator-5554");
		desiredCapabilities.setCapability("platformVersion", "5.1");
		desiredCapabilities.setCapability("noReset", "True");
		desiredCapabilities.setCapability("appPackage", pacote);
		desiredCapabilities.setCapability("appActivity", atividade);
		desiredCapabilities.setCapability("appWaitActivity", atividade);
		webDriver = new AndroidDriver<WebElement>(servico, desiredCapabilities);
	}

	public void finalizaEmulador() {
		webDriver.quit();
	}
}
