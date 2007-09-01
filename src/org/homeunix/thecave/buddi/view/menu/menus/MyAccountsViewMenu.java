/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.menus;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.buddi.view.menu.items.ViewRollAllAccounts;
import org.homeunix.thecave.buddi.view.menu.items.ViewUnrollAllAccounts;
import org.homeunix.thecave.moss.swing.MossMenu;

public class MyAccountsViewMenu extends MossMenu {
	public static final long serialVersionUID = 0;
	
	public MyAccountsViewMenu(MainFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_VIEW));
		
		this.add(new ViewUnrollAllAccounts(frame));
		this.add(new ViewRollAllAccounts(frame));
	}
}
