/*
 * Created on May 8, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.i18n;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.homeunix.thecave.buddi.Buddi;
import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.BuddiPluginFactory;

import ca.digitalcave.moss.i18n.Translator;

public class BuddiTranslator extends Translator {

	public BuddiTranslator() {
		super(Const.LANGUAGE_EXTENSION);
		
		reloadLanguages();
	}
	
	public void reloadLanguages(){
		
//		try {
//			throw new RuntimeException();
//		}
//		catch (Throwable t){
//			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Path", t);
//		}
		
		translations.clear();
		loadLanguages("/" + Const.LANGUAGE_FOLDER, this.getLanguageList(PrefsModel.getInstance().getLanguage()));
		loadLanguages(Buddi.getLanguagesFolder(), this.getLanguageList(PrefsModel.getInstance().getLanguage()));
		
		for (File pluginFile : BuddiPluginFactory.getPluginFiles()) {
			loadLanguages(pluginFile, "/" + Const.LANGUAGE_FOLDER, this.getLanguageList(PrefsModel.getInstance().getLanguage()));
		}
	}
}
