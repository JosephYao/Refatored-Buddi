/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import net.java.dev.SwingWorker;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.BuddiSynchronizePlugin;
import org.homeunix.thecave.buddi.plugin.api.exception.PluginException;
import org.homeunix.thecave.buddi.plugin.api.exception.PluginMessage;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableDocumentImpl;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.buddi.view.TransactionFrame;
import org.homeunix.thecave.moss.swing.MossDocumentFrame;
import org.homeunix.thecave.moss.swing.MossMenuItem;
import org.homeunix.thecave.moss.swing.MossSmartFileChooser;
import org.homeunix.thecave.moss.swing.MossStatusDialog;
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
					PrefsModel.getInstance().getLastDataFiles() != null 
					&& PrefsModel.getInstance().getLastDataFiles().size() > 0 ? 
							PrefsModel.getInstance().getLastDataFiles().get(0) : null, 
					filter, 
					PrefsModel.getInstance().getTranslator().get(BuddiKeys.FILECHOOSER_SYNCHRONIZE_FILE_TITLE), 
					PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK), 
					PrefsModel.getInstance().getTranslator().get(BuddiKeys.MESSAGE_ERROR_CANNOT_WRITE_DATA_FILE), 
					PrefsModel.getInstance().getTranslator().get(BuddiKeys.MESSAGE_ERROR_CANNOT_READ_DATA_FILE), 
					PrefsModel.getInstance().getTranslator().get(BuddiKeys.ERROR));

			if (f == null)
				return;
		}
		
		
		((MossDocumentFrame) getFrame()).getDocument().startBatchChange();
		
		try {
			final File fFinal = f;
			final MossStatusDialog status = new MossStatusDialog(
					getFrame(),
					TextFormatter.getTranslation(plugin.getProcessingMessage()));

			if (plugin.getProcessingMessage() != null)
				status.openWindow();

			SwingWorker worker = new SwingWorker(){
				@Override
				public Object construct() {
					try {
						plugin.synchronizeData(new MutableDocumentImpl((Document) ((MossDocumentFrame) getFrame()).getDocument()), ((MossDocumentFrame) getFrame()), fFinal);
					}
					catch (PluginMessage pm){
						return pm.getMessage();
					}
					catch (PluginException pe){
						Log.error("Error processing data in plugin: ", pe);
						return null;
					}

					return new Object();
				}

				@Override
				public void finished() {
					status.closeWindow();
					
					((Document) ((MossDocumentFrame) getFrame()).getDocument()).updateAllBalances();
					((MossDocumentFrame) getFrame()).getDocument().finishBatchChange();
					
					if (getFrame() instanceof MainFrame)
						((MainFrame) getFrame()).fireStructureChanged();
					if (getFrame() instanceof TransactionFrame)
						((TransactionFrame) getFrame()).fireStructureChanged();

					MainFrame.updateAllContent();
					
					if (get() == null){
						String[] options = new String[1];
						options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK);

						JOptionPane.showOptionDialog(
								getFrame(), 
								TextFormatter.getTranslation(BuddiKeys.MESSAGE_ERROR_CHECK_LOGS_FOR_DETAILS), 
								TextFormatter.getTranslation(BuddiKeys.ERROR), 
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.ERROR_MESSAGE,
								null,
								options,
								options[0]
						);
					}
					else if (get() instanceof String){
						String[] options = new String[1];
						options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK);

						JOptionPane.showOptionDialog(
								getFrame(), 
								get(), 
								"", 
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.PLAIN_MESSAGE,
								null,
								options,
								options[0]
						);
					}
					
					super.finished();
				}
			};

			worker.start();
		}
		catch (Exception ex){
			Log.error("Error encountered in plugin: " + ex);
		}
	}
}
