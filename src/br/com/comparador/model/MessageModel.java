package br.com.comparador.model;

import com.google.gson.annotations.SerializedName;
import lombok.ToString;
import lombok.Data;

/**
 * Classe model para apresentação das mensagens de erro da API do Github.
 * 
 * @author Felipe Nascimento
 *
 */

@Data
@ToString
public class MessageModel {
	private String message;
	
	@SerializedName("documentation_url")
    private String documentationUrl;
}