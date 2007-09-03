/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.event.ActionEvent;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.buddi.view.dialogs.AccountEditorDialog;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.MossMenuItem;

public class EditModifyAccount extends MossMenuItem {
	public static final long serialVersionUID = 0;
	
	public EditModifyAccount(MainFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_MODIFY_ACCOUNT));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (Account a : ((MainFrame) getFrame()).getSelectedAccounts()) {
			AccountEditorDialog editor = new AccountEditorDialog((MainFrame) getFrame(), (Document) ((MainFrame) getFrame()).getDocument(), a);
			try {
				editor.openWindow();
			}
			catch (WindowOpenException woe){}
		}

		((MainFrame) getFrame()).updateContent();
	}

	@Override
	public void updateMenus() {
		super.updateMenus();
		
		System.out.println(((MainFrame) getFrame()).getSelectedAccounts().size());
		this.setEnabled(((MainFrame) getFrame()).getSelectedAccounts().size() > 0);
	}
}
