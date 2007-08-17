/*
 * Created on May 8, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.i18n;

import java.io.File;

import org.homeunix.thecave.buddi.Buddi;
import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;

public class BuddiTranslator extends org.homeunix.thecave.moss.i18n.Translator {

	public BuddiTranslator() {
		super(Const.LANGUAGE_EXTENSION);
		
		reloadLanguages();
	}
	
	public void reloadLanguages(){
		translations.clear();
		loadLanguages("/Languages", this.getLanguageList(PrefsModel.getInstance().getLanguage()));
		loadLanguages(new File(Buddi.getWorkingDir() + "Languages"), this.getLanguageList(PrefsModel.getInstance().getLanguage()));
	}
}
