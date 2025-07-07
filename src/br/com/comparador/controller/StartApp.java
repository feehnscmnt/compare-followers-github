package br.com.comparador.controller;

import br.com.comparador.util.LogConsoleColorsUtil;
import br.com.comparador.util.ConfigLogUtil;
import br.com.comparador.model.FollowModel;
import br.com.comparador.util.ConnectUtil;
import br.com.comparador.util.ConfigUtil;
import jakarta.xml.bind.JAXBException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe responsável pela inicialização do programa.
 * 
 * @author Felipe Nascimento
 * 
 */

public class StartApp {
	private static final Logger LOG = Logger.getLogger(StartApp.class.getName());

	public static void main(String[] args) throws JAXBException {
		
		var configs = ConfigUtil.getConfigs();
		ConfigLogUtil.configLog();
		
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
			
			LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "=====================================================================================");
			LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "=====================================================================================");
			LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "================================ INÍCIO DO PROCESSO =================================");
			LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "=====================================================================================");
			
			LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "Realizando comunicação com a API.. Pode demorar um pouco.. Aguarde!");
			
			var followers = ConnectUtil.getResponseApi(configs.getUrlGetFollowers(), configs.getToken());
			var following = ConnectUtil.getResponseApi(configs.getUrlGetFollowing(), configs.getToken());
			
			LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "Comunicação realizada com sucesso.");
			LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "=====================================================================================");
			
			LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, String.format("Você tem %s seguidores.", followers.size()));
			LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, String.format("Você segue %s perfis.", following.size()));
			
			LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "=====================================================================================");
			
			if (Objects.equals(followers, following)) {
				
				LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "Você segue quem te segue também.");
				
			} else {
				
				var extraFollowers = new ArrayList<>(followers);
				extraFollowers.removeAll(following);
				
				if (!extraFollowers.isEmpty()) {
					
					LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "Os perfis abaixo te seguem e você não os segue de volta:");
					LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "=====================================================================================");
					
					extraFollowers.sort(Comparator.comparing(FollowModel::getLogin));
					extraFollowers.forEach(follow -> LOG.info(follow.getLogin()));
					
					LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "=====================================================================================");
					
					extraFollowers.forEach(follow -> {
						
						if (!follow.getLogin().isEmpty() && Objects.nonNull(follow.getLogin())) {
							
							LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, String.format("Tentando seguir o perfil %s..", follow.getLogin()));
							
							var statusCode = ConnectUtil.setFollowUnfollow(String.format("%s/%s", configs.getUrlSetFollowUnfollow(), follow.getLogin()), configs.getToken(), true);
							
							switch (statusCode) {
							
								case 204 -> LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, String.format("Agora você segue o perfil %s.", follow.getLogin()));
								case 404 -> LOG.log(Level.SEVERE, LogConsoleColorsUtil.ANSI_RED_LOG, String.format("O perfil %s não foi encontrado.", follow.getLogin()));
								case 401 -> LOG.log(Level.SEVERE, LogConsoleColorsUtil.ANSI_RED_LOG, "Você não tem autorização para realizar esta requisição.");
								default -> LOG.log(Level.SEVERE, LogConsoleColorsUtil.ANSI_RED_LOG, String.format("Houve erro ao tentar seguir o perfil %s.", follow.getLogin()));
							
							}
							
							LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "=====================================================================================");
							
						}
						
					});
					
				}
				
				LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "=====================================================================================");
				
				var extraFollowing = new ArrayList<>(following);
				extraFollowing.removeAll(followers);
				
				if (!extraFollowing.isEmpty()) {
					
					LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "Você segue os perfis abaixo e eles não te seguem de volta:");
					LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "=====================================================================================");
					
					extraFollowing.sort(Comparator.comparing(FollowModel::getLogin));
					extraFollowing.forEach(follow -> LOG.info(follow.getLogin()));
					
					LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "=====================================================================================");
					
					extraFollowing.forEach(follow -> {
						
						if (!follow.getLogin().isEmpty() && Objects.nonNull(follow.getLogin())) {
							
							LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, String.format("Tentando parar de seguir o perfil %s..", follow.getLogin()));
							
							var statusCode = ConnectUtil.setFollowUnfollow(String.format("%s/%s", configs.getUrlSetFollowUnfollow(), follow.getLogin()), configs.getToken(), false);
							
							switch (statusCode) {
								
								case 204 -> LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, String.format("Agora você não segue mais o perfil %s.", follow.getLogin()));
								case 404 -> LOG.log(Level.SEVERE, LogConsoleColorsUtil.ANSI_RED_LOG, String.format("O perfil %s não foi encontrado.", follow.getLogin()));
								case 401 -> LOG.log(Level.SEVERE, LogConsoleColorsUtil.ANSI_RED_LOG, "Você não tem autorização para realizar esta requisição.");
								default -> LOG.log(Level.SEVERE, LogConsoleColorsUtil.ANSI_RED_LOG, String.format("Houve erro ao tentar parar de seguir o perfil %s.", follow.getLogin()));

							}
							
							LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "=====================================================================================");
							
						}
						
					});
					
				}
				
			}
			
			LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "=====================================================================================");
			LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "================================== FIM DO PROCESSO ==================================");
			LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "=====================================================================================");
			
		}, 0, TimeUnit.MINUTES.toMillis(2), TimeUnit.MILLISECONDS);
		
	}

}