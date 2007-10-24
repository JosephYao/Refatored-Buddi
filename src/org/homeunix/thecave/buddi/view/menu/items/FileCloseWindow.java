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
import org.homeunix.thecave.moss.swing.MossFrame;
import org.homeunix.thecave.moss.swing.MossMenuItem;
import org.homeunix.thecave.moss.util.apple.HiddenMossFrame;

public class FileCloseWindow extends MossMenuItem {
	public static final long serialVersionUID = 0;
	
	public FileCloseWindow(MossFrame frame) {
		super(frame, 
				PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_FILE_CLOSE_WINDOW),
				KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	
	public void actionPerformed(ActionEvent e) {
		getFrame().closeWindow();
	}
	
	@Override
	public void updateMenus() {
		super.updateMenus();

		this.setEnabled(!(getFrame() instanceof HiddenMossFrame));
	}
}
