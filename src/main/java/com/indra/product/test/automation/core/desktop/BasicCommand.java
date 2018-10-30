package com.indra.product.test.automation.core.desktop;

import java.awt.Rectangle;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.sikuli.basics.Settings;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

public class BasicCommand {

	private static Logger log = Logger.getLogger(BasicCommand.class.getName());
	public Screen s = null;
	private static final String IMAGE_PATH = "./bin/imagens/";
	private Process pb;
	private String File;

	public BasicCommand() {
		Settings.OcrTextRead = true;
		Settings.OcrTextSearch = true;
		s = new Screen();
		s.delayClick(200);
		s.delayType(200);
		
	}

	public void Escreve(String conteudo) {
		s.type(conteudo);
		s.type(Key.TAB);
	}
	
	public void captura() {
		Rectangle reg = s.getRect();
		s.capture(reg).getImage();
	}
	
	public void encerraPrograma() {
		System.out.println(pb);
		pb.destroy();
		try {
			pb = Runtime.getRuntime().exec("TASKKILL /F /IM " + File);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pb.destroy();
	}

	public void Escreve(String conteudo, int vezes) {
		int rodadas = 0;
		while (rodadas < vezes) {
			s.type(conteudo);
			rodadas++;
		}
	}

	public void Ctrl(String comando) {
		s.type(comando, KeyModifier.CTRL);
	}

	public void Ctrl(String comando, int vezes) {
		int rodadas = 0;
		while (rodadas < vezes) {
			s.type(comando, KeyModifier.CTRL);
			rodadas++;
		}
	}

	public void Alt(String comando) {
		s.type(comando, KeyModifier.ALT);
	}

	public void Alt(String comando, int vezes) {
		int rodadas = 0;
		while (rodadas < vezes) {
			s.type(comando, KeyModifier.ALT);
			rodadas++;
		}
	}

	public void Clica(String imagem) {
		try {
			Match e = s.find(IMAGE_PATH + imagem + ".png");
			e.click(IMAGE_PATH + imagem + ".png");
		} catch (Exception e) {
			log.error(e);
		}
	}

	public void CliqueDuplo(String imagem) throws FindFailed {
		try {
			s.doubleClick(IMAGE_PATH + imagem + ".png");
		} catch (Exception e) {
			log.error(e);
		}
	}

	public void IniciaApplicacao(String app) {
		this.File = app; 
		String pathToFile= Paths.get(IMAGE_PATH + app).toString();
		try {
			pb = Runtime.getRuntime().exec("cmd /c " + pathToFile);
			System.out.println(pb);
		} catch (Exception e) {
			log.error(e);
		}
	}

	public Match AguardeTela(String imagem) throws InterruptedException, FindFailed {
		boolean resultado = false;
		Match tela = null;
		while (!resultado) {
			try {
				tela = s.find(IMAGE_PATH + imagem);
				resultado = true;
			} catch (Exception e) {
				log.error(e);
			} finally {
				resultado = false;
			}
		}
		return tela;
	}
}
