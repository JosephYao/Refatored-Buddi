/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.menus;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.menu.items.EditPreferences;
import org.homeunix.thecave.moss.swing.menu.MossMenu;
import org.homeunix.thecave.moss.swing.window.MossDocumentFrame;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class ReportFrameEditMenu extends MossMenu {
	public static final long serialVersionUID = 0;
	
	public ReportFrameEditMenu(MossDocumentFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT));
		
		if (!OperatingSystemUtil.isMac()){
			this.addSeparator();
			this.add(new EditPreferences(frame));
		}
	}
}
