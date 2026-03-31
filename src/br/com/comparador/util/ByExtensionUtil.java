package br.com.comparador.util;

import java.util.Objects;

/**
 * Classe responsável pela rotina de conversão de valores por extenso.
 * 
 * @author Felipe Nascimento
 *
 */

public class ByExtensionUtil {
	
	/**
	 * Construtor da classe neutro.
	 */
	private ByExtensionUtil() {}
	
	/**
	 * Método responsável pela conversão de valores por extenso.
	 * 
	 * @param value - valor que será convertido
	 * 
	 * @return valor por extenso
	 * 
	 */
	public static String byExtension(Integer value) {
		
		String[] units = { "zero", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove",
				           "dez", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete", "dezoito", "dezenove" };
		
		if (Objects.equals(value, 0)) return units[0];
		if (value < 0) return String.format("menos %s", byExtension(Math.abs(value)));
		
		return recursiveProcess(value, 0).trim().replaceAll("\\s+", " ");
		
	}
	
	/**
	 * Método responsável pelo gerenciamento das grandes escalas.
	 * 
	 * @param value - valor que será processado
	 * @param scale - escala de valores
	 * 
	 * @return escala por extenso
	 * 
	 */
	private static String recursiveProcess(Integer value, Integer scale) {
		
		String[] singularSuffix = { "", "mil", "milhão", "bilhão", "trilhão" };
		
		String[] pluralSuffix = { "", "mil", "milhões", "bilhões", "trilhões" };
		
		if (Objects.equals(value, 0)) return "";
		
		var lowerPart = Long.valueOf(value) % 1000;
        var highPart = Long.valueOf(value) / 1000;
        
        var extensiveLowerPart = convertBlockOfThree(lowerPart);
        var suffix = "";
        
        if (lowerPart > 0 && scale > 0) {
        	
        	suffix = (lowerPart == 1) ? singularSuffix[scale] : pluralSuffix[scale];
            
        }
        
        var remainingResult = recursiveProcess((int) highPart, scale + 1);
        
        var conector = (lowerPart > 0 && lowerPart < 100 && highPart > 0) ? " e " : " ";
        
        return String.format("%s%s%s%s", remainingResult, (suffix.isEmpty() ? "" : String.format(" %s", suffix)), conector, extensiveLowerPart);
		
	}
	
	/**
	 * Método responsável pela conversão de um número de '0' a '999' para texto.
	 * 
	 * @param value - valor que será convertido
	 * 
	 * @return valor convertido em texto
	 * 
	 */
	private static String convertBlockOfThree(Long value) {
		
		String[] units = { "zero", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove",
				           "dez", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete", "dezoito", "dezenove" };
		
		String[] tens = { "", "", "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa" };
		
		String[] hundreds = { "", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos", "oitocentos", "novecentos" };
        
		if (Objects.equals(value, Long.valueOf(0))) return "";
		if (Objects.equals(value, Long.valueOf(100))) return "cem";

		StringBuilder sb = new StringBuilder();
        
        var c = (int) (value / 100);
        var rest = (int) (value % 100);

        if (c > 0) {
        	
            sb.append(hundreds[c]);
            if (rest > 0) sb.append(" e ");
            
        }

        if (rest > 0) {
        	
            if (rest < 20) {
            	
                sb.append(units[rest]);
                
            } else {
            	
                var d = rest / 10;
                var u = rest % 10;
                
                sb.append(tens[d]);
                if (u > 0) sb.append(" e ").append(units[u]);
                
            }
            
        }
        
        return String.valueOf(sb);
        
    }
	
}