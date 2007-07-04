package org.homeunix.drummer.plugins.imports;

import java.util.Date;
import java.io.File;
import javax.swing.filechooser.FileFilter;

import net.sourceforge.buddi.api.manager.ImportManager;
import net.sourceforge.buddi.api.manager.DataManager;
import net.sourceforge.buddi.api.plugin.BuddiImportPlugin;
import net.sourceforge.buddi.api.exception.ValidationException;

import net.sourceforge.buddi.api.model.MutableAccount;
import net.sourceforge.buddi.api.model.MutableType;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.thecave.moss.util.Version;

public class SimpleImport extends BuddiImportPlugin {

	public static final long serialVersionUID = 0;

	public SimpleImport() {
	}

	public void importData(ImportManager importManager, File file) {
		MutableType myType = importManager.createType();
		myType.setName("My Type");
		try {
			importManager.saveChanges();
		}
		catch (ValidationException e) {
			System.out.println(e);
		}

		MutableAccount account = importManager.createAccount();
		account.setName("My Account");
		account.setCreationDate(new Date());
		account.setAccountType(myType);
		try {
			importManager.saveChanges();
		}
		catch (ValidationException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public String getFileChooserTitle() {
		return "Title";
	}

	public FileFilter getFileFilter() {
		return null;
	}

	public boolean isPromptForFile() {
		return false;
	}

	public String getDescription() {
		return Translate.getInstance().get("Simple Import");
	}

	public Version getAPIVersion() {
		return new Version("2.4");
	}

	public boolean isPluginActive(DataManager dataManager) {
		return true;
	}
}
