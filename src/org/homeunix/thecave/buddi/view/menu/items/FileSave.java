/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.KeyStroke;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.moss.exception.DocumentSaveException;
import org.homeunix.thecave.moss.model.AbstractDocument;
import org.homeunix.thecave.moss.swing.menu.MossMenuItem;
import org.homeunix.thecave.moss.swing.window.MossDocumentFrame;

public class FileSave extends MossMenuItem {
	public static final long serialVersionUID = 0;
	
	public FileSave(MossDocumentFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_FILE_SAVE),
				KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AbstractDocument model = ((MossDocumentFrame) getFrame()).getDocument(); 
		File f = model.getFile();
		try {
			if (f == null) {
				new FileSaveAs((MossDocumentFrame) getFrame()).doClick();
			}
			else {
				model.save();
				getFrame().updateContent();
			}
		}
		catch (DocumentSaveException dse){
			//TODO Do something less drastic here.  For debugging, we will
			// throw a runtime exception to ensure that we catch all error
			// conditions.
			throw new RuntimeException("Error saving file: " + dse, dse);
		}
	}
}
