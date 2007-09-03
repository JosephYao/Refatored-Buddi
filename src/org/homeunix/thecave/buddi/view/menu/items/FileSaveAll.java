/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.event.ActionEvent;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.moss.swing.ApplicationModel;
import org.homeunix.thecave.moss.swing.MossFrame;
import org.homeunix.thecave.moss.swing.MossMenuItem;
import org.homeunix.thecave.moss.util.apple.HiddenMossFrame;

public class FileSaveAll extends MossMenuItem {
	public static final long serialVersionUID = 0;
	
	public FileSaveAll(MossFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_FILE_SAVE_ALL));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (MossFrame frame : ApplicationModel.getInstance().getOpenFrames()) {
			if (frame instanceof MainFrame)
				new FileSave(frame).doClick();
		}
	}
	
	@Override
	public void updateMenus() {
		super.updateMenus();

		this.setEnabled(!(getFrame() instanceof HiddenMossFrame));
	}
}
