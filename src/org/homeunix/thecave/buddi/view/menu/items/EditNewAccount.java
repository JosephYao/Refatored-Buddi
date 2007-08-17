/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.AccountFrame;
import org.homeunix.thecave.buddi.view.dialogs.AccountEditorDialog;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.menu.MossMenuItem;
import org.homeunix.thecave.moss.swing.window.MossFrame;

public class EditNewAccount extends MossMenuItem {
	public static final long serialVersionUID = 0;
	
	public EditNewAccount(MossFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_NEW_ACCOUNT),
				KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() + KeyEvent.SHIFT_MASK));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!(getFrame() instanceof AccountFrame))
			throw new RuntimeException("Calling frame not instance of AccountFrame");
			
		AccountEditorDialog editor = new AccountEditorDialog((AccountFrame) getFrame(), ((AccountFrame) getFrame()).getDataModel(), null);
		try {
			editor.openWindow();
		}
		catch (WindowOpenException woe){}
		
		((AccountFrame) getFrame()).updateContent();
	}
}
