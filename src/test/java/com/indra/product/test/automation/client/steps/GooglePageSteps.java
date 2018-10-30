package com.indra.product.test.automation.client.steps;

import org.openqa.selenium.WebDriver;

import com.indra.product.test.automation.client.pages.GooglePage;
import com.indra.product.test.automation.core.driver.Web;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GooglePageSteps {

	WebDriver driver;
	GooglePage google;	
	String site = "www.google.com.br/?gws_rd=ssl";
	String navegador = "firefox";

	@After
	public void encerra() {
		google.captura("Teste");
		google.encerra();
	}

	@Given("^Internet$")
	public void internet() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
	}

	@When("^Abre o Google$")
	public void abre_o_Google() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		driver = new Web(site).getDriver();
		google = new GooglePage(driver);
	}

	@Then("^Pagina carrega\\.$")
	public void pagina_carrega() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		google.captura("arroz");
		google.validarTituloDoBrowser("Google");
	}

	@When("^Realizar Pesquisa$")
	public void realizar_Pesquisa() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		google.pesquisa("Facebook");
	}

	@Then("^Campo preenchido com texto$")
	public void campo_preenchido_com_texto() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		google.captura("campo pesquisa");
	}

	@When("^Busca e realizada$")
	public void busca_e_realizada() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		google.validarTituloDoBrowser("Facebook - Pesquisa Google");
	}

	@Then("^Pagina carrega trazendo resultado$")
	public void pagina_carrega_trazendo_resultado() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		google.validarTituloDoBrowser("Facebook - Pesquisa Google");
	}
}
