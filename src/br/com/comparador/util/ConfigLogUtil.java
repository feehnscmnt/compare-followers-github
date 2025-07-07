package br.com.comparador.util;

import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;
import java.io.InputStream;

/**
 * Classe util responsável pela configuração do log.
 * 
 * @author Felipe Nascimento
 * 
 */

public class ConfigLogUtil {
	private static final Logger LOG = Logger.getLogger(ConfigLogUtil.class.getName());
	
	/**
	 * Construtor da classe neutro.
	 */
	private ConfigLogUtil() {}
	
	/**
	 * Método responsável pela configuração do log.
	 */
	public static void configLog() {
		
		try (InputStream ins = ConfigLogUtil.class.getClassLoader().getResourceAsStream("logging.properties")) {
			
			LogManager.getLogManager().readConfiguration(ins);
			
		} catch (IOException e) {
			
			LOG.log(Level.SEVERE, LogConsoleColorsUtil.ANSI_RED_LOG, String.format("Exception in %s -- Message: %s", ConfigLogUtil.class.getSimpleName(), e.getMessage()));
			
		}
		
	}
	
}