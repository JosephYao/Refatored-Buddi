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
import org.homeunix.thecave.moss.swing.ApplicationModel;
import org.homeunix.thecave.moss.swing.MossFrame;
import org.homeunix.thecave.moss.swing.MossMenuItem;
import org.homeunix.thecave.moss.util.Log;

public class FileQuit extends MossMenuItem {
	public static final long serialVersionUID = 0;
	
	public FileQuit(MossFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_FILE_QUIT),
				KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	
	public void actionPerformed(ActionEvent e) {
		for (MossFrame frame : ApplicationModel.getInstance().getOpenFrames()) {
			if (!frame.canClose()){
				Log.debug("Frame " + frame.getTitle() + " refused to quit; cancelling quit request.");
				return;
			}
		}
		
		//We have already checked the windows; if none of them cancelled the request, we can just close.
		while (ApplicationModel.getInstance().getOpenFrames().size() > 0) {
			ApplicationModel.getInstance().getOpenFrames().get(0).closeWindowWithoutPrompting();
		}
		
		System.exit(0);
	}
}
