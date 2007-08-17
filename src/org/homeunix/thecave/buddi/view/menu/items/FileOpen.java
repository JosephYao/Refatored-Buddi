/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.KeyStroke;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.i18n.keys.MessageKeys;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.exception.DocumentLoadException;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.AccountFrame;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.file.SmartFileChooser;
import org.homeunix.thecave.moss.swing.menu.MossMenuItem;
import org.homeunix.thecave.moss.swing.window.MossFrame;

public class FileOpen extends MossMenuItem {
	public static final long serialVersionUID = 0;
	
	public FileOpen(MossFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_FILE_OPEN),
				KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		File f = SmartFileChooser.showSmartOpenDialog(
				getFrame(), 
				PrefsModel.getInstance().getLastDataFile(), 
				Const.FILE_FILTER_DATA, 
				PrefsModel.getInstance().getTranslator().get(BuddiKeys.FILECHOOSER_OPEN_DATA_FILE_TITLE), 
				PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK), 
				PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_ERROR_CANNOT_WRITE_DATA_FILE), 
				PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_ERROR_CANNOT_READ_DATA_FILE), 
				PrefsModel.getInstance().getTranslator().get(BuddiKeys.ERROR));
		
		if (f == null)
			return;
		
		try {
			AccountFrame accountFrame = new AccountFrame(new DataModel(f));
			accountFrame.openWindow(PrefsModel.getInstance().getAccountWindowSize(), null);
		}
		catch (DocumentLoadException lme){
			throw new RuntimeException("Error loading model: " + lme);
		}
		catch (WindowOpenException foe){}
	}
}
