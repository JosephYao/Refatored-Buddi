/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.event.ActionEvent;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.buddi.view.dialogs.AccountEditorDialog;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.MossMenuItem;

public class EditNewAccount extends MossMenuItem {
	public static final long serialVersionUID = 0;
	
	public EditNewAccount(MainFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_NEW_ACCOUNT));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AccountEditorDialog editor = new AccountEditorDialog((MainFrame) getFrame(), (DataModel) ((MainFrame) getFrame()).getDocument(), null);
		try {
			editor.openWindow();
		}
		catch (WindowOpenException woe){}
		
		((MainFrame) getFrame()).updateContent();
	}
}
