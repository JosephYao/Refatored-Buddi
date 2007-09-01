/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.menus;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.buddi.view.menu.items.EditDeleteAccount;
import org.homeunix.thecave.buddi.view.menu.items.EditModifyAccount;
import org.homeunix.thecave.buddi.view.menu.items.EditNewAccount;
import org.homeunix.thecave.buddi.view.menu.items.EditPreferences;
import org.homeunix.thecave.buddi.view.menu.items.EditViewScheduledTransactions;
import org.homeunix.thecave.buddi.view.menu.items.EditViewTransactions;
import org.homeunix.thecave.moss.swing.MossMenu;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class MyAccountsEditMenu extends MossMenu {
	public static final long serialVersionUID = 0;
	
	public MyAccountsEditMenu(MainFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT));
		
		this.add(new EditNewAccount(frame));
		this.add(new EditModifyAccount(frame));
		this.add(new EditDeleteAccount(frame));
		this.addSeparator();
		this.add(new EditViewTransactions(frame));
		this.add(new EditViewScheduledTransactions(frame));
		if (!OperatingSystemUtil.isMac()){
			this.addSeparator();
			this.add(new EditPreferences(frame));
		}
	}
}
