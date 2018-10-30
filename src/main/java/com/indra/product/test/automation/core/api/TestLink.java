package com.indra.product.test.automation.core.api;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestCaseStep;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.model.TestProject;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;

public class TestLink {

	private String url = "https://sltestlink.indra.es/lib/api/xmlrpc/v1/xmlrpc.php";
	private String devKey = "c9adfd96874f983ea7f70d503d0ff42c";
	private TestLinkAPI api = null;

	private ArrayList<String> features = new ArrayList<String>();
	private ArrayList<String> scenarios = new ArrayList<String>();

	private String conteudoGherkin = "";

	private static String tratarString(String texto) {
		// TODO Auto-generated method stub
		String dirty = texto.replace("<p>", "").replace("</p>", "").replace("\n", "").replace("\t", "");
		String clean = StringEscapeUtils.unescapeHtml4(dirty);
		return clean;
	}

	/// - methodDescription:
	/// title: Construtor
	/// text_description:
	/// - Responsável por criar um objeto que se conecta ao TestLink
	/// parameters:
	/// ex:
	/// - TestLink testLink = new TestLink();
	/// return_description: Método não tem retorno.
	/// return: void
	public TestLink() {

		// Inicio da criação de um objeto de conexão com o TestLink utilizando a API
		// disponível
		// Necessário passar a URL do testlink adicionando a parte:
		// "/lib/api/xmlrpc/v1/xmlrpc.php"
		// Gerar token de conexão através do site do TestLink
		try {
			try {
				api = new TestLinkAPI(new URL(url), devKey);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (TestLinkAPIException te) {
			te.printStackTrace(System.err);
			System.exit(-1);
		}
	}

	/// - methodDescription:
	/// title: obtemProjetoPorNome
	/// text_description:
	/// - Responsável por obter o projeto com base no nome
	/// parameters:
	/// - String: nomeProjeto
	/// ex:
	/// - testLink.obtemProjetoPorNome("TESTES INDRA 2018");
	/// return_description: Método retorna um objeto do tipo TestProject.
	/// return: TestProject
	public TestProject obtemProjetoPorNome(String nomeProjeto) {
		return api.getTestProjectByName(nomeProjeto);
	}

	/// - methodDescription:
	/// title: obtemSuitesDeTestes
	/// text_description:
	/// - Obtem as suites de testes com base no Id de um projeto encontrado.
	/// parameters:
	/// - TestProject: projeto
	/// ex:
	/// - testLink.obtemSuitesDeTestes(projeto);
	/// return_description: Retorna um array de suites de testes.
	/// return: TestSuite[].
	public TestSuite[] obtemSuitesDeTestes(TestProject projeto) {
		return api.getFirstLevelTestSuitesForTestProject(projeto.getId());
	}

	/// - methodDescription:
	/// title: obtemTestesCases
	/// text_description:
	/// - Obtem os Test Cases a partir de uma determinada Suite de Testes
	/// parameters:
	/// - TestSuite: t
	/// ex:
	/// - testLink.obtemTestesCases(testSuite);
	/// return_description: Um array de Test Cases.
	/// return: TestCase[]
	private TestCase[] obtemTestesCases(TestSuite t) {
		// TODO Auto-generated method stub
		return api.getTestCasesForTestSuite(t.getId(), true, null);
	}

	/// - methodDescription:
	/// title: obtemStepsTratarParaconteudoGherkin
	/// text_description:
	/// - Apos receber os steps este método trata os
	/// - textos para que possam ser escritos no formato scenarios
	/// parameters:
	/// - List<TestCaseStep>: steps
	/// ex:
	/// - testLink.obtemStepsTratarParaconteudoGherkin(listaSteps);
	/// return_description: Não possui retorno.
	/// return: void.
	public void obtemStepsTratarParaconteudoGherkin(List<TestCaseStep> steps) {
		for (TestCaseStep s : steps) {

			String stepTratado = s.getActions().replace("\n", "").replace("</p>", "").replace("<p>\t", "")
					.replace("\r\n" + "\r\n", "");
			String anotacaoscenarios;
			String expectTratado = s.getExpectedResults().replace("\n", "").replace("</p>", "").replace("<p>\t", "")
					.replace("\r\n" + "\r\n", "");

			if (steps.indexOf(s) == 0) {
				anotacaoscenarios = "Given ";
			} else {
				anotacaoscenarios = "When ";
			}

			conteudoGherkin = conteudoGherkin.concat("\t" + anotacaoscenarios + stepTratado + "\n");
			conteudoGherkin = conteudoGherkin.concat("\tThen " + expectTratado + "\n");

		}
	}

	/// - methodDescription:
	/// title: escreveFeatures
	/// text_description:
	/// - Este método criará os arquivos ".feature" já na pasta
	/// "src/test/resources".
	/// - Não deve ser usado de forma direta.
	/// - Deve ser usado dentro de outro método capaz de buscar os casos de Testes
	/// do TestLink.
	/// parameters:
	/// ex:
	/// - escreveFeature();
	/// return_description: Método não tem retorno.
	/// return: void
	public void escreveFeatures() {

		for (String i : features) {
			System.out.println(i);
			String path = System.getProperty("user.dir") + "/src/test/resources/features/" + i + ".feature";
			System.out.println(path);
			try {
				FileWriter arq = new FileWriter(path);
				PrintWriter print = new PrintWriter(arq);
				print.printf(scenarios.get(features.indexOf(i)));
				arq.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/// - methodDescription:
	/// title: gerarTodosOsscenariossDoProjeto
	/// text_description:
	/// - Método responsável por acessar um projeto e obter todos os casos de
	/// testes,
	/// - passando por todos os nível (suites, test cases, etc).
	/// parameters:
	/// - String: nomeDoProjeto
	/// ex:
	/// - testLink.gerarTodosOsscenariossDoProjeto(projeto);
	/// return_description: Embora não possua retorno, este método gerará os
	/// arquivos ".feature" nas suas respectivas pastas.
	/// return: void.
	public void gerarTodosOsScenariosDoProjeto(String nomeDoProjeto) {
		// Acesso ao Projeto
		TestProject projeto = obtemProjetoPorNome(nomeDoProjeto);

		// Utilizando o ID o projeto encontrado é possível obter uma lista de suites de
		// testes

		TestSuite[] suite = obtemSuitesDeTestes(projeto);

		for (TestSuite t : suite) {

			conteudoGherkin = conteudoGherkin.concat("Feature: " + t.getName() + "\n\n");
			features.add(t.getName());

			// utilizando o id das suites encontradas é possível onter os cases
			TestCase[] tc = obtemTestesCases(t);

			for (TestCase tcase : tc) {
				conteudoGherkin = conteudoGherkin.concat("Scenario: " + tcase.getName() + "\n");

				// Depois de encontrar os test cases é preciso usar os seus id + o prefix para
				// ter um acesso preciso

				// O Prefix será usado para obter os Test Cases mais adiante.
				TestCase tc2 = api.getTestCase(tcase.getId(), Integer.parseInt(projeto.getPrefix()), null);

				// Após obter os cases de forma precisa é possivel obter os steps e gerar os
				// scenarioss
				obtemStepsTratarParaconteudoGherkin(tc2.getSteps());

				conteudoGherkin = conteudoGherkin.concat("\n");
				scenarios.add(conteudoGherkin);
				conteudoGherkin = "";
			}
		}
		escreveFeatures();
	}

	/// - methodDescription:
	/// title: gerarTodosOsscenariossDoProjeto
	/// text_description:
	/// - Método responsável por acessar um projeto e
	/// - obter todos os casos de testes com base numa suite especifica, passando
	/// - por todos os nível (suites, test cases, etc).
	/// parameters:
	/// - String: nomeDoProjeto
	/// - String: suiteEspecificada
	/// ex:
	/// - testLink.gerarscenariosAPartirDeUmaSuite(projeto);
	/// return_description: Embora não possua retorno, este método gerará os
	/// arquivos ".feature" nas suas respectivas pastas.
	/// return: void.
	public void gerarscenariosAPartirDeUmaSuite(String Suite) {
		TestProject projeto = obtemProjetoPorNome("DEVOPSCORE");
		TestSuite[] suites = obtemSuitesDeTestes(projeto);
		List<TestSuite> suiteSelecionada = new ArrayList<TestSuite>();

		for (TestSuite ts : suites) {
			System.out.println(">>>>>>>>>" + ts.getName());
			if (ts.getName().contains(Suite)) {
				suiteSelecionada.add(ts);
			}
		}

		for (TestSuite t : suiteSelecionada) {
			conteudoGherkin = conteudoGherkin.concat("Feature: " + t.getName() + "\n\n");
			features.add(t.getName());

			// utilizando o id das suites encontradas é possível onter os cases
			TestCase[] tc = obtemTestesCases(t);

			for (TestCase tcase : tc) {
				conteudoGherkin = conteudoGherkin.concat("scenarios: " + tcase.getName() + "\n");

				// Depois de encontrar os test cases é preciso usar os seus id + o prefix para
				// ter um acesso preciso

				// O Prefix será usado para obter os Test Cases mais adiante.
				TestCase tc2 = api.getTestCase(tcase.getId(), Integer.parseInt(projeto.getPrefix()), null);

				// Após obter os cases de forma precisa é possivel obter os steps e gerar os
				// scenarioss
				obtemStepsTratarParaconteudoGherkin(tc2.getSteps());

				conteudoGherkin = conteudoGherkin.concat("\n");
				scenarios.add(conteudoGherkin);
				conteudoGherkin = "";
			}
		}
		escreveFeatures();
	}

	public void gerarFeaturesDeUmTestPlan() throws TestLinkAPIException, MalformedURLException {

		TestProject projeto = api.getTestProjectByName("DEVOPSCORE");

		TestPlan[] planoDeTestes = api.getProjectTestPlans(projeto.getId());

		for (TestPlan tp : planoDeTestes) {

			TestSuite[] suitesDeTestes = api.getTestSuitesForTestPlan(tp.getId());

			for (TestSuite st : suitesDeTestes) {

				if (st.getName().contains("o")) {

					features.add(st.getName());

					conteudoGherkin = conteudoGherkin.concat("Feature: " + st.getName() + "\n\n");

					TestCase[] casosDeTestes = api.getTestCasesForTestSuite(st.getId(), true, null);

					for (TestCase tc : casosDeTestes) {

						conteudoGherkin = conteudoGherkin.concat("Scenario: " + tc.getName() + "\n");

						TestCase testCaseDetalhado = api.getTestCase(tc.getId(), Integer.parseInt(projeto.getPrefix()),
								null);

						conteudoGherkin = conteudoGherkin
								.concat("\tGiven " + tratarString(testCaseDetalhado.getPreconditions()) + "\n");

						List<TestCaseStep> passos = testCaseDetalhado.getSteps();

						for (TestCaseStep p : passos) {

							String acoes = p.getActions();
							String resultados = p.getExpectedResults();

							conteudoGherkin = conteudoGherkin.concat("\tWhen " + tratarString(acoes) + "\n");
							conteudoGherkin = conteudoGherkin.concat("\tThen " + tratarString(resultados) + "\n");
						}

						conteudoGherkin = conteudoGherkin.concat("\n");
					}
					scenarios.add(conteudoGherkin);
				}
				conteudoGherkin = "";
			}
		}
		escreveFeatures();
	}
}
