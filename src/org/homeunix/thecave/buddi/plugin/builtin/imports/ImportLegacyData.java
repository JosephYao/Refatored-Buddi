/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.builtin.imports;

import java.io.File;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.plugin.api.BuddiImportPlugin;
import org.homeunix.thecave.buddi.plugin.api.model.MutableModel;
import org.homeunix.thecave.moss.exception.DocumentLoadException;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.Version;

public class ImportLegacyData extends BuddiImportPlugin {

	@Override
	public void importData(MutableModel model, File file) {
		try {
			if (file == null)
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
