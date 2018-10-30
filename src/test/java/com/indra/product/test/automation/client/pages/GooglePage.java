package com.indra.product.test.automation.client.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.indra.product.test.automation.core.web.BasicCommand;

public class GooglePage extends BasicCommand {

	public GooglePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void pesquisa(String busca) {
		// TODO Auto-generated method stub
		escrever("q", busca);
		verificaListaDeSugestoes();
	}

	private void verificaListaDeSugestoes() {
		// TODO Auto-generated method stub
		WebElement e = esperarElemento(By.className("sbsb_b"),2);
		if (e != null) {
			clicar("lsb");
		} else {
			clicar("btnK");
		}
	}

}
