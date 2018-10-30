package com.indra.product.test.automation.core.promise;

import java.util.concurrent.CompletableFuture;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * O Objetivo desta classe é prover driver com promessas para as chamadas remotas.
 * 
 * @author msergio
 * @since Setembro de 2018
 * @version {version}
 */
public class WebDriver {

    org.openqa.selenium.WebDriver driver; // Connect to Selenium Driver
    WebElement element; // Connect to dom elements
    CompletableFuture<?> promise;

    public WebDriver(org.openqa.selenium.WebDriver driver) {
        this.driver = driver;
    }

    public CompletableFuture<WebElement> then(String name) {
		return CompletableFuture.supplyAsync(() -> {
            return find(name);
        });
    }

	// Será remodelado.
    public WebElement find(String name) {
		WebElement elemento = null;

		while (true) {
			try {
				elemento = driver.findElement(By.id(name));
				break;
			} catch (Exception e) {

			}
			try {
				elemento = driver.findElement(By.name(name));
				break;
			} catch (Exception e) {

			}
			try {
				elemento = driver.findElement(By.tagName(name));
				break;
			} catch (Exception e) {

			}
			try {
				elemento = driver.findElement(By.cssSelector(name));
				break;
			} catch (Exception e) {

			}
			try {
				elemento = driver.findElement(By.linkText(name));
				break;
			} catch (Exception e) {

			}
			try {
				elemento = driver.findElement(By.partialLinkText(name));
				break;
			} catch (Exception e) {

			}
			try {
				elemento = driver.findElement(By.xpath(name));
				break;
			} catch (Exception e) {

			}
			try {
				elemento = driver.findElement(By.className(name));
				break;
			} catch (Exception e) {

				break;
			}
		}
		return elemento;
	}

    public org.openqa.selenium.WebDriver getDriver() {
		return this.driver;
	}

}
