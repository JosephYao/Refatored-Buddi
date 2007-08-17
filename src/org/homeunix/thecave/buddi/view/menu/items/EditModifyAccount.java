/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.AccountFrame;
import org.homeunix.thecave.buddi.view.dialogs.AccountEditorDialog;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.menu.MossMenuItem;
import org.homeunix.thecave.moss.swing.window.MossFrame;

public class EditModifyAccount extends MossMenuItem {
	public static final long serialVersionUID = 0;
	
	public EditModifyAccount(MossFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_MODIFY_ACCOUNT),
				KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!(getFrame() instanceof AccountFrame))
			throw new RuntimeException("Calling frame not instance of AccountFrame");
			
		for (Account a : ((AccountFrame) getFrame()).getSelectedAccounts()) {
			AccountEditorDialog editor = new AccountEditorDialog((AccountFrame) getFrame(), ((AccountFrame) getFrame()).getDataModel(), a);
			try {
				editor.openWindow();
			}
			catch (WindowOpenException woe){}
		}

		((AccountFrame) getFrame()).updateContent();
	}

	@Override
	public void updateMenus() {
		super.updateMenus();
		
		this.setEnabled(((AccountFrame) getFrame()).getSelectedAccounts().size() > 0);
	}
}
