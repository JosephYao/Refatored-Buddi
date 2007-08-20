/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.i18n.keys.MessageKeys;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.moss.exception.DocumentSaveException;
import org.homeunix.thecave.moss.model.AbstractDocument;
import org.homeunix.thecave.moss.swing.file.SmartFileChooser;
import org.homeunix.thecave.moss.swing.menu.MossMenuItem;
import org.homeunix.thecave.moss.swing.window.MossDocumentFrame;

public class FileSaveAs extends MossMenuItem {
	public static final long serialVersionUID = 0;
	
	public FileSaveAs(MossDocumentFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_FILE_SAVE_AS),
				KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() + KeyEvent.SHIFT_MASK));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AbstractDocument model = ((MossDocumentFrame) getFrame()).getDocument();
		File f = SmartFileChooser.showSaveFileDialog(
				getFrame(), 
				PrefsModel.getInstance().getLastDataFile(), 
				Const.FILE_FILTER_DATA, 
				Const.DATA_FILE_EXTENSION, 
				PrefsModel.getInstance().getTranslator().get(BuddiKeys.FILECHOOSER_SAVE_DATA_FILE_TITLE), 
				PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK), 
				PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_CANCEL), 
				PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_REPLACE), 
				PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_ERROR_CANNOT_WRITE_DATA_FILE), 
				PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_PROMPT_OVERWRITE_FILE), 
				PrefsModel.getInstance().getTranslator().get(BuddiKeys.ERROR));
		
		//User hit cancel
		if (f == null)
			return;
		
		try {
			//Ask if we should encrypt this file
			String[] options = new String[2];
			options[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_YES);
			options[1] = TextFormatter.getTranslation(ButtonKeys.BUTTON_NO);
			((DataModel) model).setEncrypted(JOptionPane.showOptionDialog(
					getFrame(), 
					TextFormatter.getTranslation(MessageKeys.MESSAGE_ASK_FOR_DATA_FILE_ENCRYPTION),
					TextFormatter.getTranslation(MessageKeys.MESSAGE_ASK_FOR_DATA_FILE_ENCRYPTION_TITLE),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					null,
					options,
					options[0]
			) == JOptionPane.YES_OPTION);
			
			((MossDocumentFrame) getFrame()).getDocument().saveAs(f);
			getFrame().updateContent();
		}
		catch (DocumentSaveException dse){
			throw new RuntimeException("Error saving file: " + dse);
		}
	}
	
	
}
