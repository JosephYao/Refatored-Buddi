/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.menus;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.AccountFrame;
import org.homeunix.thecave.buddi.view.menu.items.EditDeleteAccount;
import org.homeunix.thecave.buddi.view.menu.items.EditModifyAccount;
import org.homeunix.thecave.buddi.view.menu.items.EditNewAccount;
import org.homeunix.thecave.buddi.view.menu.items.EditPreferences;
import org.homeunix.thecave.buddi.view.menu.items.EditViewBudget;
import org.homeunix.thecave.buddi.view.menu.items.EditViewReports;
import org.homeunix.thecave.buddi.view.menu.items.EditViewTransactions;
import org.homeunix.thecave.moss.swing.menu.MossMenu;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class AccountFrameEditMenu extends MossMenu {
	public static final long serialVersionUID = 0;
	
	public AccountFrameEditMenu(AccountFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT));
		
		this.add(new EditNewAccount(frame));
		this.add(new EditModifyAccount(frame));
		this.add(new EditDeleteAccount(frame));
		this.addSeparator();
		this.add(new EditViewTransactions(frame));
		this.addSeparator();
		this.add(new EditViewBudget(frame));
		this.add(new EditViewReports(frame));
		if (!OperatingSystemUtil.isMac()){
			this.addSeparator();
			this.add(new EditPreferences(frame));
		}
	}
}
