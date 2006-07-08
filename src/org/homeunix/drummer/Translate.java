/*
 * Created on May 8, 2006 by wyatt
 */
package org.homeunix.drummer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.homeunix.drummer.util.Log;


public class Translate {
	private final Properties translations = new Properties();
	
	public static Translate inst() {
		return SingletonHolder.instance;
	}
	
	private static class SingletonHolder {
		private static Translate instance = new Translate();		
	}
	
	private Translate(){}
	
	public Translate loadLanguage(String language){
		String languageFile = Const.LANGUAGE_FOLDER + File.separator + language + Const.LANGUAGE_EXTENSION;
		try{
			translations.load(new BufferedInputStream(new FileInputStream(languageFile)));
		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(
					null, 
					"Error loading language file " + languageFile + ": " + ioe,
					"Error Loading Language File",
					JOptionPane.ERROR_MESSAGE
			);
		}
		return this;
	}

	public String get(String key){
		if (key == null){
			Log.debug("Null translation key");
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
