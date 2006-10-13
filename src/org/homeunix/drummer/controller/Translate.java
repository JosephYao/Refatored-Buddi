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
import org.homeunix.thecave.moss.util.Log;


public class Translate {
	private final Properties translations = new Properties();
	
	public static Translate getInstance() {
		return SingletonHolder.instance;
	}
	
	private static class SingletonHolder {
		private static Translate instance = new Translate();		
	}
	
	private Translate(){}
	
	/**
	 * We try to load up to three different language files, in the following order:
	 * 1) English.lang - this is the base languauge, and if a term is not 
	 * 		defined elsewhere, we default to the values here.
	 * 2) Specified language, without locale.  For instance, if you try
	 * 		to load Espanol_(MX), we first try to load Espanol.  This
	 * 		is to allow localizations to draw on a base translation in 
	 * 		the same language.
	 * 3) The specified language, with locale.
	 * 
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
		String baseFileName = Const.LANGUAGE_FOLDER + File.separator + language.replaceAll("_(.+)$", "") + Const.LANGUAGE_EXTENSION;
		String baseResource = "/" + language.replaceAll("_(.+)$", "") + Const.LANGUAGE_EXTENSION;

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
					"Error loading language file " + localizedFileName + ":\n" + ioe + "\nTrying to load English.lang...\n\nAfter Buddi starts, you need to set the language in Preferences.",
					"Error Loading Language File",
					JOptionPane.ERROR_MESSAGE
			);

			try{
				localizedFileName = Const.LANGUAGE_FOLDER + File.separator + "English" + Const.LANGUAGE_EXTENSION;
				translations.load(new BufferedInputStream(new FileInputStream(localizedFileName)));
			}
			catch (IOException ioe2){
				JOptionPane.showMessageDialog(
						null, 
						"Error loading language file English.lang.  Please check that\nyour Languages directory exists, and contains at least English.lang",
						"Error Loading Language File",
						JOptionPane.ERROR_MESSAGE
				);
			}
		}
		return this;
	}

	public String get(String key){
		if (key == null){
			if (Const.DEVEL) Log.debug("Null translation key");
			return key;
		}
		String ret = translations.getProperty(key);
		if (ret == null)
			return key;
		return ret;
	}
	
	public String get(TranslateKeys key){
		String ret = translations.getProperty(key.toString());
		if (ret == null)
			return key.toString();
		return ret;
	}
}
