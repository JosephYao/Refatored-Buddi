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
import org.homeunix.thecave.moss.swing.menu.MossMenuItem;
import org.homeunix.thecave.moss.swing.window.MossFrame;

public class EditDeleteAccount extends MossMenuItem {
	public static final long serialVersionUID = 0;

	public EditDeleteAccount(MossFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_DELETE_ACCOUNT),
				KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!(getFrame() instanceof AccountFrame))
			throw new RuntimeException("Calling frame not instance of AccountFrame");
			
		for (Account a : ((AccountFrame) getFrame()).getSelectedAccounts()) {
			((AccountFrame) getFrame()).getDataModel().removeAccount(a);
		}

		((AccountFrame) getFrame()).updateContent();
	}
	
	@Override
	public void updateMenus() {
		super.updateMenus();
		
		this.setEnabled(((AccountFrame) getFrame()).getSelectedAccounts().size() > 0);
	}
}
