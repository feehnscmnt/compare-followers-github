package br.com.comparador.util;

/**
 * Classe util responsável pelo código ANSI dos logs.
 * 
 * @author Felipe Nascimento
 * 
 */

public class LogConsoleColorsUtil {
	
	/**
	 * Construtor da classe neutro.
	 */
	private LogConsoleColorsUtil() {}
	
	/**
	 * O parâmetro '{0}' em cada código ANSI refere-se ao texto do log.
	 */
	public static final String ANSI_YELLOW_LOG = "\033[1;33m{0}";
	public static final String ANSI_PURPLE_LOG = "\033[1;35m{0}";
	public static final String ANSI_WHITE_LOG = "\033[1;37m{0}";
	public static final String ANSI_GREEN_LOG = "\033[1;32m{0}";
	public static final String ANSI_BLACK_LOG = "\033[1;30m{0}";
	public static final String ANSI_BLUE_LOG = "\033[1;34m{0}";
	public static final String ANSI_CYAN_LOG = "\033[1;36m{0}";
	public static final String ANSI_RED_LOG = "\033[1;31m{0}";
	
}