package com.indra.product.test.automation.client.main;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "json:target/relatorios/resultado.json",
		"junit:target/relatorios/resultado.xml" }, tags = "@apresentacao", monochrome = true, features = "src/test/resources/features", glue = "com.indra.product.test.automation.client.steps")
public class ExecuteTest {
	private static Logger log = Logger.getLogger(ExecuteTest.class.getName());

	public ExecuteTest() {
		log.info("Iniciando teste com cucumber...");
	}

}
