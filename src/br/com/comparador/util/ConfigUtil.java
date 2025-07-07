package br.com.comparador.util;

import br.com.comparador.model.ConfigModel;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.JAXBContext;

/**
 * Classe util responsável pela leitura do XML com as configs da API do Github.
 * 
 * @author Felipe Nascimento
 * 
 */

public class ConfigUtil {
	
	/**
	 * Construtor da classe neutro.
	 */
	private ConfigUtil() {}
	
	/**
	 * Método responsável pela leitura e espelhamento do XML.
	 * 
	 * @return objeto Java com as configs da API do Github
	 * 
	 */
	public static ConfigModel getConfigs() throws JAXBException {
		
		return (ConfigModel) JAXBContext.newInstance(ConfigModel.class).createUnmarshaller()
			.unmarshal(ConfigUtil.class.getClassLoader().getResourceAsStream("config.xml"));
		
	}
	
}