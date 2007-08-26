/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.menus;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.buddi.view.menu.items.EditDeleteBudgetCategory;
import org.homeunix.thecave.buddi.view.menu.items.EditModifyBudgetCategory;
import org.homeunix.thecave.buddi.view.menu.items.EditNewBudgetCategory;
import org.homeunix.thecave.buddi.view.menu.items.EditPreferences;
import org.homeunix.thecave.buddi.view.menu.items.EditViewScheduledTransactions;
import org.homeunix.thecave.moss.swing.MossMenu;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class BudgetFrameEditMenu extends MossMenu {
	public static final long serialVersionUID = 0;

	public BudgetFrameEditMenu(MainFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT));
		
		this.add(new EditNewBudgetCategory(frame));
		this.add(new EditModifyBudgetCategory(frame));
		this.add(new EditDeleteBudgetCategory(frame));
		this.addSeparator();
		this.add(new EditViewScheduledTransactions(frame));
		if (!OperatingSystemUtil.isMac()){
			this.addSeparator();
			this.add(new EditPreferences(frame));
		}
	}
}
