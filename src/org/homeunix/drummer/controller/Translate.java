/*
 * Created on May 8, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.io.File;
import java.util.Properties;

import org.homeunix.thecave.moss.util.Log;


public class Translate {
	private final Properties translations = new Properties();
	
	/**
	 * Returns a singleton instance of the Translate class.
	 * @return
	 */
	public static Translate getInstance() {
		return SingletonHolder.instance;
	}
	
	private static class SingletonHolder {
		private static Translate instance = new Translate();		
	}
	
	private Translate(){}
	
	/**
	 * We try to load up to three different language files, in the following order:
	 * <ol>
	 * <li>English.lang - this is the base languauge, and if a term is not 
	 * 		defined elsewhere, we default to the values here.</li>
	 * <li>Specified language, without locale.  For instance, if you try
	 * 		to load Espanol_(MX), we first try to load Espanol.  This
	 * 		is to allow localizations to draw on a base translation in 
	 * 		the same language.</li>
	 * <li>The specified language, with locale.</li>
	 * <p>
	 * This gives us extreme flexibility and maintainability when 
	 * creating locales - to change a single term, you only have to 
	 * create a .lang file with that one term changed.
	 * 
	 * @param language
	 * @return
	 */
	public Translate loadLanguage(String language){
		
		return this;
	}
	
	public Translate loadPluginLanguages(File jarFile, String language){
		
		return this;
	}

	/**
	 * Returns the translation, based on the given string.
	 * @param key The key to translate
	 * @return The translation in currently loaded language
	 */
	public String get(String key){
		if (key == null){
			Log.warning("Null translation key: " + key);
			return key;
		}
		String ret = translations.getProperty(key);
		if (ret == null)
			return key;
		
		return ret;
	}
	
	/**
	 * Returns the translation, based on the given TranslateKey.
	 * @param key The key to translate
	 * @return The translation in currently loaded language
	 */
	public String get(TranslateKeys key){
		String ret = translations.getProperty(key.toString());
		if (ret == null)
			return key.toString();
		return ret;
	}
//	
//	public Set<String> getUnusedKeys() {
//		existingKeys.removeAll(usedKeys);
//		
//		return existingKeys;
//	}
}
