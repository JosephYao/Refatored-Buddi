/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.menus;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.menu.items.HelpAbout;
import org.homeunix.thecave.buddi.view.menu.items.HelpDebug;
import org.homeunix.thecave.moss.swing.menu.MossMenu;
import org.homeunix.thecave.moss.swing.window.MossDocumentFrame;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class HelpMenu extends MossMenu {
	public static final long serialVersionUID = 0;

	public HelpMenu(MossDocumentFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_HELP));
	
		//TODO Add help and license files.... 
		if (Const.DEVEL) this.add(new HelpDebug(frame));
		if (!OperatingSystemUtil.isMac()) this.add(new HelpAbout(frame));
	}
}
