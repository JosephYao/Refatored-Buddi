/*
 * Created on Nov 1, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.exports;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import net.sourceforge.buddi.api.manager.DataManager;
import net.sourceforge.buddi.api.plugin.BuddiExportPlugin;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.thecave.moss.util.Version;

/**
 * This is a simple test plugin which can help to test functionality.  
 * It should not be included in the main release (or at least, not loaded;
 * the class is small enough that I don't care if it is included in the
 * distribution or not).
 * 
 * @author wyatt
 *
 */
public class TestPlugin extends BuddiExportPlugin {

	public static final long serialVersionUID = 0;
	
	public void exportData(DataManager dataManager, File file) {
		System.out.println(Translate.getInstance().get("FOO"));
		
//		ImmutableTransaction transaction = new ImmutableTransactionImpl(null);
//		ImmutableSource s = transaction.getTo();
	}

//	public Class[] getCorrectWindows() {
//		// TODO Auto-generated method stub
//		Class[] c = new Class[1];
//		c[0] = TransactionsFrame.class;
//		return c;
//	}

	public String getFileChooserTitle() {
		return "Title";
	}

	public FileFilter getFileFilter() {
		return null;
	}

	public boolean isPromptForFile() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getDescription() {
		return Translate.getInstance().get("FOO");
	}

	public Version getAPIVersion() {
		return null;
	}
	
	public boolean isPluginActive(DataManager dataManager) {
		return true;
	}
}
