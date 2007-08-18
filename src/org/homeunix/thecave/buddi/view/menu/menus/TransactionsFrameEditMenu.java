/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.menus;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.TransactionFrame;
import org.homeunix.thecave.buddi.view.menu.items.EditClearTransaction;
import org.homeunix.thecave.buddi.view.menu.items.EditDeleteTransaction;
import org.homeunix.thecave.buddi.view.menu.items.EditPreferences;
import org.homeunix.thecave.buddi.view.menu.items.EditRecordTransaction;
import org.homeunix.thecave.moss.swing.menu.MossMenu;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class TransactionsFrameEditMenu extends MossMenu {
	public static final long serialVersionUID = 0;
	
	public TransactionsFrameEditMenu(TransactionFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT));
		
		this.add(new EditRecordTransaction(frame));
		this.add(new EditClearTransaction(frame));
		this.add(new EditDeleteTransaction(frame));
		if (!OperatingSystemUtil.isMac()){
			this.addSeparator();
			this.add(new EditPreferences(frame));
		}
	}
}
