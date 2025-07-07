package br.com.comparador.util;

import br.com.comparador.model.MessageModel;
import br.com.comparador.model.FollowModel;
import com.google.gson.reflect.TypeToken;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.regex.Pattern;
import com.google.gson.Gson;
import java.util.Comparator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
import java.net.URI;

/**
 * Classe responsável pela comunicação com a API do Github.
 * 
 * @author Felipe Nascimento
 *
 */

public class ConnectUtil {
	private static final Logger LOG = Logger.getLogger(ConnectUtil.class.getName());
	private static Integer statusCode = 0;
	
	/**
	 * Construtor da classe neutro.
	 */
	private ConnectUtil() {}
	
	/**
	 * Método responsável pela abertura da requisição passando as informações necessárias à API do Github.
	 * 
	 * @param urlApi - URL da API
	 * @param token - token de autorização
	 * 
	 * @return JSON com as informações solicitadas
	 * 
	 */
	public static List<FollowModel> getResponseApi(String urlApi, String token) {
		
		var followList = new TypeToken<List<FollowModel>>(){}.getType();
		var follow = new ArrayList<FollowModel>();
		var url = urlApi;
		
		try (var client = HttpClient.newHttpClient()) {
			
			while (Objects.nonNull(url)) {
				
				var request = HttpRequest
					.newBuilder()
					.uri(new URI(url))
					.header("Authorization", String.format("token %s", token))
					.header("Accept", "application/vnd.github+json")
					.GET()
					.build();
				
				var response = client.send(request, HttpResponse.BodyHandlers.ofString());
				
				if (response.body().contains("message")) {
					
					var message = new Gson().fromJson(response.body(), MessageModel.class);
					
					LOG.info(message.getMessage());
					LOG.info(message.getDocumentationUrl());
					
					System.exit(0);
					
				} else {
					
					List<FollowModel> follows = new Gson().fromJson(response.body(), followList);
					
					follow.addAll(follows);
					
					url = getNextPageUrl(response.headers().firstValue("Link").orElse(null));
					
				}
				
			}
			
		} catch (URISyntaxException | IOException | InterruptedException e) {
			
			Thread.currentThread().interrupt();
			LOG.log(Level.SEVERE, LogConsoleColorsUtil.ANSI_RED_LOG, String.format("Houve um erro inesperado no método getResponseApi. Exception: %s", e.getMessage()));
			
		}
		
		follow.sort(Comparator.comparing(FollowModel::getLogin));
		
		return follow;
		
	}
	
	/**
	 * Método responsável por seguir ou parar de seguir os perfis.
	 * 
	 * @param urlApi - URL da API
	 * @param token - token de autorização
	 * @param followUnfollow - true para seguir, false para deixar de seguir
	 * 
	 * @return código de status da requisição
	 * 
	 */
	public static Integer setFollowUnfollow(String urlApi, String token, boolean followUnfollow) {
		
		try (var client = HttpClient.newHttpClient()) {
			
			var requestBuilder = HttpRequest
				.newBuilder()
				.uri(new URI(urlApi))
				.header("Authorization", String.format("Bearer %s", token))
				.header("Accept", "application/vnd.github+json");
			
			HttpRequest request;
			
			if (followUnfollow) {
				
				request = requestBuilder.PUT(HttpRequest.BodyPublishers.noBody()).build();
				
			} else {
				
				request = requestBuilder.DELETE().build();
				
			}
			
			client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(response -> statusCode = response.statusCode()).join();
			
		} catch (URISyntaxException e) {
			
			LOG.log(Level.SEVERE, LogConsoleColorsUtil.ANSI_RED_LOG, String.format("Houve um erro inesperado no método setFollowUnfollow. Exception: %s", e.getMessage()));
			
		}
		
		return statusCode;
		
	}
	
	/**
	 * Método responsável por obter o link da próxima página de perfis, caso tenha.
	 * 
	 * @param linkHeader - link da próxima página de perfis
	 * 
	 * @return link da próxima página de perfis tratado
	 * 
	 */
	private static String getNextPageUrl(String linkHeader) {
		
        if (Objects.nonNull(linkHeader)) {
        	
            var matcher = Pattern.compile("<([^>]+)>; rel=\"next\"").matcher(linkHeader);
            
            if (matcher.find()) {
            	
                return matcher.group(1);
                
            }
            
        }
        
        return null;
        
    }
	
}