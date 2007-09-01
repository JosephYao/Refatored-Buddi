/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.menus;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.buddi.view.menu.items.EditRollAllBudgetCategories;
import org.homeunix.thecave.buddi.view.menu.items.EditUnrollAllBudgetCategories;
import org.homeunix.thecave.moss.swing.MossMenu;

public class MyBudgetViewMenu extends MossMenu {
	public static final long serialVersionUID = 0;

	public MyBudgetViewMenu(MainFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_VIEW));
		
		this.add(new EditUnrollAllBudgetCategories(frame));
		this.add(new EditRollAllBudgetCategories(frame));
	}
}
