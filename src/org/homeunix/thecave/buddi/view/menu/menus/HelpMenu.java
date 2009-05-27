/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.menus;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.menu.items.HelpAbout;
import org.homeunix.thecave.buddi.view.menu.items.HelpCheckForUpdates;
import org.homeunix.thecave.buddi.view.menu.items.HelpDownloadPlugins;
import org.homeunix.thecave.buddi.view.menu.items.HelpHelp;
import org.homeunix.thecave.buddi.view.menu.items.HelpTutorial;
import org.homeunix.thecave.moss.common.OperatingSystemUtil;
import org.homeunix.thecave.moss.swing.MossFrame;
import org.homeunix.thecave.moss.swing.MossMenu;

public class HelpMenu extends MossMenu {
	public static final long serialVersionUID = 0;

	public HelpMenu(MossFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_HELP));
	
		if (!OperatingSystemUtil.isMac()){
			this.add(new HelpAbout(frame));
			this.addSeparator();
		}
		this.add(new HelpHelp(frame));
		this.add(new HelpTutorial(frame));
		this.addSeparator();
		this.add(new HelpDownloadPlugins(frame));
		this.addSeparator();
		this.add(new HelpCheckForUpdates(frame));
		
	}
}
