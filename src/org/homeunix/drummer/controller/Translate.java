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
import org.homeunix.drummer.util.Log;


public class Translate {
	private final Properties translations = new Properties();
	
	public static Translate getInstance() {
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
					"Error loading language file " + languageFile + ":\n" + ioe + "\nTrying to load en.lang...\n\nAfter Buddi starts, you need to set the language in Preferences.",
					"Error Loading Language File",
					JOptionPane.ERROR_MESSAGE
			);

			try{
				languageFile = Const.LANGUAGE_FOLDER + File.separator + "en" + Const.LANGUAGE_EXTENSION;
				translations.load(new BufferedInputStream(new FileInputStream(languageFile)));
			}
			catch (IOException ioe2){
				JOptionPane.showMessageDialog(
						null, 
						"Error loading language file en.lang.  Please check that\nyour Languages directory exists, and contains at least en.lang",
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