/*
 * Created on May 8, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.jar.JarLoader;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;


public class Translate {
	private final Properties translations = new Properties();
//	private final Set<String> existingKeys = new HashSet<String>();
//	private final Set<String> usedKeys = new HashSet<String>();
	
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
		//English
		String englishFileName = Const.LANGUAGE_FOLDER + File.separator + "English" + Const.LANGUAGE_EXTENSION;
		String englishResource = "/" + "English" + Const.LANGUAGE_EXTENSION;
		
		//Base Language (e.g., Espanol)
		String baseFileName = Const.LANGUAGE_FOLDER + File.separator + language.replaceAll("_\\(.*\\)$", "") + Const.LANGUAGE_EXTENSION;
		String baseResource = "/" + language.replaceAll("_\\(.*\\)$", "") + Const.LANGUAGE_EXTENSION;

		//Localized Language (e.g., Espanol_(MX))
		String localizedFileName = Const.LANGUAGE_FOLDER + File.separator + language + Const.LANGUAGE_EXTENSION;
		String localizedResource = "/" + language + Const.LANGUAGE_EXTENSION;
		
		try{
			if (Const.DEVEL) Log.info("Trying to load language: " + localizedFileName);

			//Set up the files
			File englishFile, baseFile, localizedFile;
			englishFile = new File(englishFileName);
			baseFile = new File(baseFileName);
			localizedFile = new File(localizedFileName);
			
			//Load English
			if (englishFile.exists()){
				if (new BufferedInputStream(new FileInputStream(englishFile)) != null)
					translations.load(new BufferedInputStream(new FileInputStream(englishFile)));
			}
			else {
				if (this.getClass().getResourceAsStream(englishResource) != null)
					translations.load(this.getClass().getResourceAsStream(englishResource));
			}
			
			//Load Base Language
			if (baseFile.exists()){
				if (new BufferedInputStream(new FileInputStream(baseFile)) != null)
					translations.load(new BufferedInputStream(new FileInputStream(baseFile)));
			}
			else {
				if (this.getClass().getResourceAsStream(baseResource) != null)
					translations.load(this.getClass().getResourceAsStream(baseResource));
			}

			//Load Localized Language
			if (localizedFile.exists()){
				if (new BufferedInputStream(new FileInputStream(localizedFile)) != null)
					translations.load(new BufferedInputStream(new FileInputStream(localizedFile)));
			}
			else {
				if (this.getClass().getResourceAsStream(localizedResource) != null)
					translations.load(this.getClass().getResourceAsStream(localizedResource));
			}

		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(
					null, 
					"Error loading language file.  Please check that\nyour Languages directory exists, and contains at least one language file.",
					"Error Loading Language File",
					JOptionPane.ERROR_MESSAGE
			);
		}
		
//		for (Object o : translations.keySet()) {
//			existingKeys.add(o.toString());
//		}
		
		return this;
	}
	
	public Translate loadPluginLanguages(File jarFile, String language){
		//English
		String englishResource = "/" + "English" + Const.LANGUAGE_EXTENSION;
		
		//Base Language (e.g., Espanol)
		String baseResource = "/" + language.replaceAll("_\\(.*\\)$", "") + Const.LANGUAGE_EXTENSION;

		//Localized Language (e.g., Espanol_(MX))
		String localizedResource = "/" + language + Const.LANGUAGE_EXTENSION;
		
		try{
			if (Const.DEVEL) Log.info("Trying to load language: " + localizedResource);

			//Load English
			if (JarLoader.getResourceAsStream(jarFile, englishResource) != null)
				translations.load(JarLoader.getResourceAsStream(jarFile, englishResource));
			
			//Load Base Language
			if (JarLoader.getResourceAsStream(jarFile, baseResource) != null)
				translations.load(JarLoader.getResourceAsStream(jarFile, baseResource));

			//Load Localized Language
			if (JarLoader.getResourceAsStream(jarFile, localizedResource) != null)
				translations.load(JarLoader.getResourceAsStream(jarFile, localizedResource));
		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(
					null, 
					"Error loading language from Jar.",
					"Error Loading Language File",
					JOptionPane.ERROR_MESSAGE
			);
		}
		
		//Yes, I know that this is slow, but we want the user-defined 
		// languages to override the plugin languages, to give users 
		// the ability to change the strings for the plugins as well.  
		// If we always load the base languages (which includes the 
		// user-modified languages from Languages folder as well), 
		// then we are guaranteed to always have this happen.
		loadLanguage(language);
		
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

	/**
	 * Converts a long value (in cents: 10000 == $100.00) to a string
	 * with proper decimal values, with the user's desired currency
	 * sign in the user's specified position (whether behind or in front
	 * of the amount).  It is highly recommended that you use this method
	 * to output monetary values, as it presents the user with a constant
	 * look for currency.
	 * 
	 * Technically, this is not a translation method, so it could be argued 
	 * that it should not be here.  I put it here to avoid the creation of
	 * another class to do just one function.
	 * @param value The currency amount, in cents (as per Buddi's internal 
	 * representation of currency).  For instance, to represent the value
	 * $123.45, you would pass in 12345. 
	 * @return A string with proper decimal places, plus the user's defined 
	 * currency symbol in the correct position (whether before or after the
	 * amount).
	 */
	public static String getFormattedCurrency(long value, boolean isCredit){
		return (PrefsInstance.getInstance().getPrefs().isCurrencySymbolAfterAmount() ? "" : PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
		+ (value != 0 && (isCredit ^ value < 0) ? "-" : "")
		+ Formatter.getInstance().getDecimalFormat().format(Math.abs((double) value / 100.0))
		+ (PrefsInstance.getInstance().getPrefs().isCurrencySymbolAfterAmount() ? " " + PrefsInstance.getInstance().getPrefs().getCurrencySymbol() : "");
	}
}
