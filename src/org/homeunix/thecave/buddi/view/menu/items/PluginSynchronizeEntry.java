/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.i18n.keys.MessageKeys;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.BuddiSynchronizePlugin;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableDocumentImpl;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.moss.swing.MossDocumentFrame;
import org.homeunix.thecave.moss.swing.MossMenuItem;
import org.homeunix.thecave.moss.swing.MossSmartFileChooser;
import org.homeunix.thecave.moss.util.Log;

public class PluginSynchronizeEntry extends MossMenuItem {
	public static final long serialVersionUID = 0;

	private final BuddiSynchronizePlugin plugin;

	public PluginSynchronizeEntry(MossDocumentFrame parentFrame, BuddiSynchronizePlugin plugin) {
		super(parentFrame, TextFormatter.getTranslation(plugin.getName()));

		this.plugin = plugin;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File f = null;

		if (plugin.isPromptForFile()){
			FileFilter filter = new FileFilter(){
				@Override
				public boolean accept(File f) {
					if (f.isDirectory())
						return true;
					if (plugin.getFileExtensions() != null){
						for (String s : plugin.getFileExtensions()) {
							if (f.getName().endsWith(s))
								return true;
						}
					}
					else 
						return true;
					return false;
				}

				@Override
				public String getDescription() {
					return TextFormatter.getTranslation(plugin.getDescription());
				}
			};
			f = MossSmartFileChooser.showSmartOpenDialog(
					getFrame(), 
					PrefsModel.getInstance().getLastDataFile(), 
					filter, 
					PrefsModel.getInstance().getTranslator().get(BuddiKeys.FILECHOOSER_SYNCHRONIZE_FILE_TITLE), 
					PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK), 
					PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_ERROR_CANNOT_WRITE_DATA_FILE), 
					PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_ERROR_CANNOT_READ_DATA_FILE), 
					PrefsModel.getInstance().getTranslator().get(BuddiKeys.ERROR));

			if (f == null)
				return;
		}

		((MossDocumentFrame) getFrame()).getDocument().startBatchChange();
		
		Log.debug("Calling synchronizeData()");
		plugin.synchronizeData(new MutableDocumentImpl((Document) ((MossDocumentFrame) getFrame()).getDocument()), ((MossDocumentFrame) getFrame()), f);
		Log.debug("Finished synchronizeData(); updating balances");
		((Document) ((MossDocumentFrame) getFrame()).getDocument()).updateAllBalances();
		Log.debug("Finished updating balances");
		
		((MossDocumentFrame) getFrame()).getDocument().finishBatchChange();
		
		//Update all windows when done
		MainFrame.updateAllContent();
	}
}
