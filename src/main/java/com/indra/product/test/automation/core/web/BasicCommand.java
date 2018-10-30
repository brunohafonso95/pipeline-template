package com.indra.product.test.automation.core.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.indra.product.test.automation.core.exception.CommandException;
// import com.indra.product.test.automation.core.promise.WebDriver;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Paulo Lobo Neto
 * @Descricao Classe responsável por conter comandos que executam interações com
 *            a página da web. Os métodos contidos na classe são todos públicos
 *            e para utilizá-la, é necessário passar o WebDriver como parâmetro
 */
public class BasicCommand {

	private static Logger log = Logger.getLogger(BasicCommand.class.getName());
	private static final String VALUE = "value";
	private static final String SCREENSHOT_REPORT = "./evidences/screenshot/";
	protected WebDriver driver;
	private JavascriptExecutor executor;

	/**
	 * @Descricao Método construtor, define que sempre que a classe for instanciada,
	 *            é necessário passar o driver como parâmetro
	 * @param WebDriver
	 */
	public BasicCommand(WebDriver driver) {
		this.driver = driver;
		this.executor = (JavascriptExecutor) driver;
	}

	public void captura(String string) {
		File shot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destino = new File(SCREENSHOT_REPORT + string + ".png");
		try {
			FileUtils.copyFile(shot, destino);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @Descricao Mudar de aba do navegador
	 * @param numero
	 *            da aba. Ex.: 0, 1, 2 ...
	 */
	public void mudarAba(int numero) {
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[numero]);
		// driver.getDriver().switchTo().window((String)
		// driver.getDriver().getWindowHandles().toArray()[numero]);
	}

	/**
	 * @Descricao Trocar de Janela
	 * @param elemento
	 */
	public void trocarJanela(String elemento) {
		try {
			driver.switchTo().window(elemento);
			// driver.getDriver().switchTo().window(elemento);
		} catch (NoSuchElementException | TimeoutException | ElementNotVisibleException e) {

		}
	}

	public static int getRandomValue() {
		return new Random().nextInt(10000);
	}

	// public WebElement encontra(String name) throws CommandException {
	// WebElement elemento = null;
	// try {
	// driver.then(name).thenAccept(data -> {
	// // log.info("Your total price is: {} ", (price + shippingPrice));
	// return data;
	// System.out.println("Your total price is: $" + data);
	// }).get();
	// // CompletableFuture<WebElement> promise =
	// // CompletableFuture.supplyAsync(find(name))
	// // .thenAccept(el -> {
	// // System.out.println("Your total price is: " + el.getText());
	// // });
	// return elemento;
	// } catch (InterruptedException e) {
	// throw new CommandException(e.getMessage());
	// }

	// // return promise.get();
	// }

	/**
	 * @Descricao Procurar um elemento específico na página. Para utiliza-lo é
	 *            necessário informar um localizador (id, className, cssSelector,
	 *            Xpath, etc) afim de identificá-lo na página.
	 * @param elemento
	 */
	public WebElement encontra(String name) {
		WebElement elemento = null;

		try {
			elemento = driver.findElement(By.id(name));
		} catch (Exception e) {

		}
		try {
			elemento = driver.findElement(By.name(name));
		} catch (Exception e) {

		}
		try {
			elemento = driver.findElement(By.tagName(name));
		} catch (Exception e) {

		}
		try {
			elemento = driver.findElement(By.linkText(name));
		} catch (Exception e) {

		}
		try {
			elemento = driver.findElement(By.partialLinkText(name));
		} catch (Exception e) {

		}
		try {
			elemento = driver.findElement(By.xpath(name));
		} catch (Exception e) {

		}
		try {
			elemento = driver.findElement(By.className(name));
		} catch (Exception e) {
		}
		borda(elemento);
		return elemento;
	}

	public void borda(WebElement elemento) {
		if (elemento != null) {
			executor.executeScript("arguments[0].style.border = 'medium solid red';", elemento);
		}
	}

	public void elementoExiste(String elemento) {
		assertEquals(true, encontra(elemento).isDisplayed());
	}

	public void elementoEstaHabilitado(String elemento) {
		assertEquals(true, encontra(elemento).isEnabled());
	}

	/**
	 * @Descricao Escrever em algum campo
	 * @param elemento
	 * @param texto
	 */
	public void escrever(String elemento, String texto) {
		WebElement e = encontra(elemento);
		e.clear();
		e.sendKeys(texto);
	}

	/**
	 * @Descricao Limpar campo
	 * @param elemento
	 */
	public void limpar(String elemento) throws CommandException {
		try {
			encontra(elemento).clear();
		} catch (Exception e) {
			throw new CommandException(e);
		}
	}

	/**
	 * @Descricao Clicar em um elemento
	 * @param elemento
	 */
	public void clicar(String elemento) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement webElement = wait.until(ExpectedConditions.elementToBeClickable(encontra(elemento)));
		webElement.click();
	}

	/**
	 * @Descricao Clicar em um ou mais elementos
	 * @param elementos
	 */
	public void clicarElementos(List<String> elementos) throws CommandException {
		try {
			for (String elemento : elementos) {
				encontra(elemento).click();
			}
		} catch (Exception e) {
			throw new CommandException(e);
		}
	}

	/**
	 * @Descricao Verificar se o Radio Button está selecionado
	 * @param elemento
	 * @return boolean
	 */
	public void verificarSeRadioEstaMarcado(String elemento) {
		assertTrue(encontra(elemento).isSelected());
	}

	/**
	 * @Descricao Obter texto do elemento. O texto é retornado como uma String
	 * @param elemento
	 * @return String
	 */
	public String obterTexto(String elemento) {
		return encontra(elemento).getText();
	}

	/**
	 * @Descricao limpar valor com backspace
	 * @param elemento
	 */
	public void limparValorComBackspace(WebElement elemento) {
		while (elemento.getAttribute(VALUE).length() > 0) {
			elemento.sendKeys(Keys.BACK_SPACE);
		}
	}

	/**
	 * @Descricao Limpar Campo
	 * @param elemento
	 */
	public void limparCampo(String elemento) {
		encontra(elemento).clear();
	}

	/**
	 * @Descricao Pegar Valor do CSS. elementoCss: nome do elemento CSS a ser obtido
	 *            o valor. Ex. Display
	 * @param elemento
	 * @param elementoCss
	 * @return String
	 */
	public String pegarValorCss(String elemento, String elementoCss) {
		return encontra(elemento).getCssValue(elementoCss);
	}

	/**
	 * @Descricao Obter valor do "Attribute"
	 * @param elemento
	 * @return String
	 */
	public String obterValorDoElementoAttribute(String elemento) {
		return encontra(elemento).getAttribute(VALUE);
	}

	/**
	 * @Descricao Verificar se o check box está marcado
	 * @param elemento
	 * @return boolean
	 */
	public boolean verificarSeOcheckBoxEstaMarcado(String elemento) {
		return encontra(elemento).isSelected();
	}

	/*
	 * ***********************************
	 * 
	 * 
	 * 
	 * **************** Urls ***********
	 * 
	 * 
	 * 
	 ***********************************/

	/**
	 * @Descricao Abrir uma nova URL em um novo browser.
	 * @param URL
	 * 
	 */
	public void abrirUrl(String url) {
		driver.get(url);
	}

	/**
	 * @Descricao Navegar para outra URL, permanecendo na mesma aba do browser
	 * @param URL
	 */
	public void navegarUrl(String url) {
		driver.navigate().to(url);
	}

	/**
	 * @Descricao Valida título da aba do browser
	 * @param tituloDaAba
	 */
	public void validarTituloDoBrowser(String tituloDaAba) {
		assertEquals(tituloDaAba, driver.getTitle());
	}

	public void validaTexto(WebElement e, String texto) {
		System.out.println(e.getText());
		assertTrue(e.getText().contains(texto));
	}

	/**
	 * @Descricao Valida a URL atual
	 * @param URL
	 * 
	 */
	public void validarUrlAtual(String url) {
		assertEquals(url, driver.getCurrentUrl());
	}

	/*
	 * ***********************************
	 * 
	 * 
	 * 
	 * **************** Combos ***********
	 * 
	 * 
	 * 
	 ***********************************/

	/**
	 * @Descricao Selecionar combo por texto visível
	 * @param elemento
	 * @param valor
	 */
	public void selecionarComboPorTextoVisivel(String elemento, String valor) throws NoSuchElementException {
		WebElement webElement = encontra(elemento);
		Select combo = new Select(webElement);
		try {
			combo.selectByValue(valor);
		} catch (Exception e) {
			combo.selectByIndex(Integer.parseInt(valor));
		}
	}

	/**
	 * @Descricao Obter texto da primeira posição do combo
	 * @param elemento
	 * @return String
	 */
	public String obterTextoDaPrimeiraPosicaoDoCombo(String elemento) {
		WebElement element = encontra(elemento);
		Select combo = new Select(element);
		return combo.getFirstSelectedOption().getText();
	}

	/**
	 * @Descricao Obter a quantidade de opções do combo
	 * @param elemento
	 * @return Integer
	 */
	public Integer obterQuantidadeOpcoesCombo(String elemento) {
		WebElement element = encontra(elemento);
		Select combo = new Select(element);
		List<WebElement> options = combo.getOptions();
		return options.size();
	}

	/**
	 * @Descricao Passar um texto e verificar se existe a opção no combo. Ex.: Se
	 *            quiser verificar num combo de cidade a opção "São Paulo", deverá
	 *            ser passado "São Paulo" como parâmetro
	 * @param elemento
	 * @param texto
	 * @return boolean
	 */
	public void verificarSeExisteOpcaoNoSelect(String elemento, String texto) {
		boolean resultado = false;
		WebElement element = encontra(elemento);
		Select combo = new Select(element);
		List<WebElement> options = combo.getOptions();
		for (WebElement option : options) {
			if (option.getText().equals(texto)) {
				resultado = true;
				break;
			}
		}
		assertTrue(resultado);
	}

	/**
	 * @Descricao Desmarcar combo de acordo com o texto
	 * @param elemento
	 * @param valor
	 */
	public void desmarcarComboPorTextoVisivel(String elemento, String valor) {
		WebElement element = encontra(elemento);
		Select combo = new Select(element);
		combo.deselectByVisibleText(valor);
	}

	/**
	 * @Descricao Obter todos os textos do combo
	 * @param elemento
	 * @return List
	 */
	public List<String> obterTextosCombo(String elemento) {
		List<String> listaDeTexto = new ArrayList<String>();
		WebElement element = encontra(elemento);
		Select combo = new Select(element);
		int quantidade = obterQuantidadeOpcoesCombo(elemento);
		for (int i = 0; i <= quantidade; i++) {
			listaDeTexto.add(combo.getOptions().get(i).getText());
		}
		return listaDeTexto;
	}

	public void envia(String elemento) {
		WebElement e = encontra(elemento);
		e.submit();
	}

	/**
	 * @Descricao Obter uma lista das opções que estão selecionadas no combo
	 * @param elemento
	 * @return lista de String
	 */
	public List<String> obterTodasAsOpcoesSelecionadasNoCombo(String elemento) {
		WebElement element = encontra(elemento);
		Select combo = new Select(element);
		List<WebElement> allSelectedOptions = combo.getAllSelectedOptions();
		List<String> valores = new ArrayList<String>();
		for (WebElement opcao : allSelectedOptions) {
			valores.add(opcao.getText());
		}
		return valores;
	}

	/*
	 * ***********************************
	 * 
	 * 
	 * 
	 * **************** Frames ***********
	 * 
	 * 
	 * 
	 ***********************************/

	/**
	 * @Descricao Entrar no frame
	 * @param elemento
	 */
	public void entrarFrame(String elemento) {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(elemento);
	}

	/**
	 * @Descricao Sair do frame atual e voltar pra estrutura HTML padrão
	 */
	public void sairFrame() {
		driver.switchTo().defaultContent();
	}

	/*
	 * ***********************************
	 * 
	 * 
	 * 
	 * **************** Alerts ***********
	 * 
	 * 
	 * 
	 ***********************************/

	/**
	 * @Descricao Aceitar o alerta
	 */
	public void aceitarAlerta() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	/**
	 * @Descricao Obter texto do alerta
	 * @return String
	 */
	public String obterTextoDoAlerta() {
		Alert alert = driver.switchTo().alert();
		String texto = alert.getText();
		return texto;
	}

	/**
	 * @Descricao Negar Alerta
	 */
	public void negarAlerta() {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}

	/**
	 * @Descricao Escrever no Alerta
	 * @param elemento
	 */
	public void escreverNoAlerta(String conteudo) {
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(conteudo);
	}

	/*
	 * ***********************************
	 * 
	 * 
	 * 
	 * **************** Esperas **********
	 * 
	 * 
	 * 
	 ***********************************/

	/**
	 * @param elemento
	 * @param tempo
	 *            limite de espera
	 * @return 
	 */
	public WebElement esperarElemento(By elemento, int time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		return wait.until(ExpectedConditions.presenceOfElementLocated(elemento));
	}

	/**
	 * @param elemento.
	 * @param tempo
	 *            limite de espera
	 */
	public void esperarSerClicavel(String elemento, int time) {
		// WebDriverWait wait = new WebDriverWait(driver, time);
		// wait.until(ExpectedConditions.elementToBeClickable(find(elemento)));
	}

	public void esperarCarregar(String elemento, String texto) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.textToBePresentInElement(encontra(elemento), texto));
	}

	/**
	 * @Descricao
	 * @param elemento
	 * @param tempoLimiteDeEspera
	 */
	public void esperarSerSelecionavel(String elemento, int time) {
		// WebDriverWait wait = new WebDriverWait(driver, time);
		// wait.until(ExpectedConditions.elementToBeSelected(find(elemento)));
	}

	/**
	 * @Descricao Esperar a página estar na URL passada via parâmetro
	 * @param URL
	 * @param tempoLimiteDeEspera
	 */
	public void esperarUrlSerCarregada(String url, int tempoLimiteDeEspera) {
		// WebDriverWait wait = new WebDriverWait(driver, tempoLimiteDeEspera);
		// wait.until(ExpectedConditions.urlToBe(url));
	}

	/**
	 * @Descricao Esperar elemento ser clicável, clicar e escrever
	 * @param elemento
	 * @param texto
	 * @param tempoLimiteDeEspera
	 */
	public void esperarSerClicavelClicarEscrever(String elemento, String valor, int tempoLimiteDeEspera) {
		// WebDriverWait wait = new WebDriverWait(driver, tempoLimiteDeEspera);
		// WebElement e =
		// wait.until(ExpectedConditions.elementToBeClickable(find(elemento)));
		// e.clear();
		// e.sendKeys(valor);
	}

	/**
	 * @Descricao Esperar visibilidade do elemento
	 * @param elemento
	 * @param tempoLimiteDeEspera
	 */
	public void esperarSerInvisivel(String elemento, int tempoLimiteDeEspera) {
		WebDriverWait wait = new WebDriverWait(driver, tempoLimiteDeEspera);
		wait.until(ExpectedConditions.invisibilityOf(encontra(elemento)));
	}

	/**
	 * @Descricao Esperar elemento ser visível
	 * @param elemento
	 * @param tempoLimiteDeEspera
	 * @return 
	 */
	public WebElement esperarElementoSerVisivel(String elemento, int tempoLimiteDeEspera) {
		WebDriverWait wait = new WebDriverWait(driver, tempoLimiteDeEspera);
		return wait.until(ExpectedConditions.visibilityOf(encontra(elemento)));
	}

	/**
	 * @Descricao Esperar elemento ser clicável, após, limpar o campo
	 * @param elemento
	 * @param tempoLimiteDeEspera
	 */
	public void esperarElementoSerClicavelLimparCampo(String elemento, int tempoLimiteDeEspera) {
		// WebDriverWait wait = new WebDriverWait(driver, tempoLimiteDeEspera);
		// WebElement input =
		// wait.until(ExpectedConditions.elementToBeClickable(find(elemento)));
		// while (input.getAttribute(VALUE).length() > 0) {
		// input.sendKeys(Keys.BACK_SPACE);
		// }
	}

	public void navega(String site) {
		// navegar para outros links
		driver.navigate().to(site);
	}

	public void encerra() {
		// Encerra o driver
		try {
			driver.close();
			driver.quit();
		} catch (Exception e) {
			log.error("Nenhum Driver aberto", e);
		}
	}

	public void clicar(By elemento) {
		try {
			WebElement element = driver.findElement(elemento);

			executor.executeScript("arguments[0].click();", element);
		} catch (NoSuchElementException | TimeoutException | ElementNotVisibleException e) {

		}
	}

	public void clicarPorTexto(String elemento, String tag, String texto) {
		try {
			WebElement foco = encontra(elemento);
			List<WebElement> element = foco.findElements(By.tagName(tag));
			for (WebElement e : element) {
				if (e.getText().contentEquals(texto)) {
					borda(e);
					e.click();
					break;
				}
			}
		} catch (ElementNotVisibleException e) {

		}
	}

	/**
	 * @Descricao Rolar para baixo. O parâmetro "quantidade" define a quantidade de
	 *            rolagens
	 * @param quantidade
	 */
	public void rolarParaBaixo(int quantidade) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0, " + quantidade + ")");
		} catch (NoSuchElementException | TimeoutException | ElementNotVisibleException e) {

		}
	}

	/**
	 * @Descricao Rolar para cima. O parâmetro "quantidade" define a quantidade de
	 *            rolagens
	 * @param quantidade
	 */
	public void rolarParaCima(int quantidade) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0, " + quantidade + ")");
		} catch (NoSuchElementException | TimeoutException | ElementNotVisibleException e) {

		}
	}

	/**
	 * @Descricao Pegar valor do CSS. O parâmetro esperar receber o ID do elemento.
	 *            VocÊ DEVE passa o parâmetro como um tipo String
	 * @param idDoElemento
	 * @return String: valor do css
	 */
	public String pegarValorCss(String idDoElemento) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			String value = (String) jse.executeScript(
					"" + "if (document.getElementById('" + idDoElemento + "').style.display == 'none'){   }");
			return value;
		} catch (NoSuchElementException | TimeoutException | ElementNotVisibleException e) {
			return null;
		}
	}

	/**
	 * @Descricao Mover para o elemento
	 * @param elemento
	 */
	public void moverParaOelemento(By elemento) {
		WebElement element = driver.findElement(elemento);
		executor.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/**
	 * @Descricao Escrever
	 * @param elemento
	 * @param texto
	 */
	public void escrever(By elemento, String texto) {
		WebElement element = driver.findElement(elemento);
		executor.executeScript("arguments[0].value=" + texto + ";", element);
	}

}