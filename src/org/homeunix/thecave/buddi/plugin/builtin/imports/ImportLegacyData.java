/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.builtin.imports;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.i18n.keys.MessageKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.BuddiImportPlugin;
import org.homeunix.thecave.buddi.plugin.api.model.MutableModel;
import org.homeunix.thecave.moss.exception.DocumentLoadException;
import org.homeunix.thecave.moss.swing.MossSmartFileChooser;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.Version;

public class ImportLegacyData extends BuddiImportPlugin {

	@Override
	public void importData(MutableModel model, File file) {
		try {
			File f = MossSmartFileChooser.showSmartOpenDialog(
					null, 
					PrefsModel.getInstance().getLastDataFile(), 
					new FileFilter(){
						@Override
						public boolean accept(File f) {
							if (f.isDirectory() 
									|| f.getName().endsWith(Const.DATA_FILE_EXTENSION_OLD)){
								return true;
							}
							return false;
						}

						@Override
						public String getDescription() {
							return PrefsModel.getInstance().getTranslator().get(BuddiKeys.FILE_DESCRIPTION_BUDDI_DATA_FILES);
						}
					}, 
					PrefsModel.getInstance().getTranslator().get(BuddiKeys.FILECHOOSER_OPEN_DATA_FILE_TITLE), 
					PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK), 
					PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_ERROR_CANNOT_WRITE_DATA_FILE), 
					PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_ERROR_CANNOT_READ_DATA_FILE), 
					PrefsModel.getInstance().getTranslator().get(BuddiKeys.ERROR));
			
			if (f == null)
				return;

			LegacyModelConverter.convert(model, file);
		}
		catch (DocumentLoadException dle){
			Log.error("Error converting data file: ", dle);
		}
	}

	public Version getMaximumVersion() {
		return null;
	}

	public Version getMinimumVersion() {
		return null;
	}

	public String getName() {
		return BuddiKeys.IMPORT_LEGACY_BUDDI_FORMAT.toString();
	}
}
