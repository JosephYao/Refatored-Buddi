/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.event.ActionEvent;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.dialogs.BackupManagerDialog;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.MossDocumentFrame;
import org.homeunix.thecave.moss.swing.MossFrame;
import org.homeunix.thecave.moss.swing.MossMenuItem;
import org.homeunix.thecave.moss.util.Log;

public class FileRevertToBackup extends MossMenuItem {
	public static final long serialVersionUID = 0;
	
	public FileRevertToBackup(MossFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_FILE_REVERT_TO_BACKUP));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			BackupManagerDialog bmd = new BackupManagerDialog((MossDocumentFrame) getFrame());
			bmd.openWindow();
		}
		catch (WindowOpenException woe){
			Log.error(woe);
		}
	}
	
	@Override
	public void updateMenus() {
		super.updateMenus();

		this.setEnabled(getFrame() instanceof MossDocumentFrame 
				&& ((MossDocumentFrame) getFrame()).getDocument().getFile() != null);
	}
}
