package br.com.comparador.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * Classe model que representa o objeto com as configs da API do Github obtidas no XML.
 * 
 * @author Felipe Nascimento
 * 
 */

@Data
@XmlRootElement(name="config")
public class ConfigModel {
	private String token;
	private String urlGetFollowers;
	private String urlGetFollowing;
	private String urlSetFollowUnfollow;
}