package br.com.comparador.controller;

import br.com.comparador.util.LogConsoleColorsUtil;
import java.nio.file.StandardCopyOption;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.net.URI;

public class ZipDownloader {
	private static final Logger LOG = Logger.getLogger(ZipDownloader.class.getName());

    public static void main(String[] args) throws Exception {
    	
    	UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    	
        var fileURL = JOptionPane.showInputDialog(
        	
        	null,
        	"Informe a URL do arquivo para download",
        	"URL do arquivo",
        	JOptionPane.INFORMATION_MESSAGE
        	
        );
        
        if (Objects.isNull(fileURL) || fileURL.isEmpty()) { System.exit(0); }
        
        try {
        	
        	LOG.log(Level.INFO, LogConsoleColorsUtil.ANSI_BLACK_LOG, "Realizando download.. Aguarde!");
        	
            downloadFile(fileURL, String.format("%s/Downloads", System.getProperty("user.home")), getFileNameFromUrl(fileURL));
            
            LOG.info("Download concluído com sucesso!");
            
        } catch (IOException e) {
        	
            LOG.log(Level.SEVERE, LogConsoleColorsUtil.ANSI_RED_LOG, String.format("Houve erro ao baixar o arquivo. Exception: %s", e.getMessage()));
            
        }
        
    }

    private static void downloadFile(String fileURL, String saveDirectory, String fileName) throws IOException {
    	
        var target = Paths.get(saveDirectory, fileName);
        
        Files.createDirectories(target.getParent());

        try (var in = new URI(fileURL).toURL().openStream()) {
        	
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            
        } catch (URISyntaxException e) {
        	
        	LOG.log(Level.SEVERE, LogConsoleColorsUtil.ANSI_RED_LOG, String.format("Houve erro ao obter o arquivo. Exception: %s", e.getMessage()));
        	
        }
        
    }
    
    private static String getFileNameFromUrl(String fileURL) throws URISyntaxException, MalformedURLException {
    	
    	var path = new URI(fileURL).toURL().getPath();
        
        var lastSlashIndex = path.lastIndexOf('/');
        
        if (lastSlashIndex != -1 && lastSlashIndex < path.length() - 1) {
        	
            return path.substring(lastSlashIndex + 1);
            
        } else {
        	
            return null;
            
        }
        
    }
    
}