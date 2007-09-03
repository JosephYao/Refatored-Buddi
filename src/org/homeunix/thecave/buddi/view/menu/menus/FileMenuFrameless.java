/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.menus;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.menu.items.FileNew;
import org.homeunix.thecave.buddi.view.menu.items.FileOpen;
import org.homeunix.thecave.buddi.view.menu.items.FileQuit;
import org.homeunix.thecave.moss.swing.MossFrame;
import org.homeunix.thecave.moss.swing.MossMenu;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class FileMenuFrameless extends MossMenu {
	public static final long serialVersionUID = 0;
	
	public FileMenuFrameless(MossFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_FILE));
		
		this.add(new FileNew(frame));
		this.add(new FileOpen(frame));
		this.addSeparator();
		if (!OperatingSystemUtil.isMac()){
			this.addSeparator();
			this.add(new FileQuit(frame));
		}
	}
}
