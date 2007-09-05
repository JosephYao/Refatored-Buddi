/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.menus;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.buddi.view.TransactionFrame;
import org.homeunix.thecave.buddi.view.menu.items.EditClearTransaction;
import org.homeunix.thecave.buddi.view.menu.items.EditCopyBudgetCategoryValuesBackwards;
import org.homeunix.thecave.buddi.view.menu.items.EditCopyBudgetCategoryValuesForward;
import org.homeunix.thecave.buddi.view.menu.items.EditDeleteAccount;
import org.homeunix.thecave.buddi.view.menu.items.EditDeleteBudgetCategory;
import org.homeunix.thecave.buddi.view.menu.items.EditDeleteTransaction;
import org.homeunix.thecave.buddi.view.menu.items.EditModifyAccount;
import org.homeunix.thecave.buddi.view.menu.items.EditModifyBudgetCategory;
import org.homeunix.thecave.buddi.view.menu.items.EditNewAccount;
import org.homeunix.thecave.buddi.view.menu.items.EditNewBudgetCategory;
import org.homeunix.thecave.buddi.view.menu.items.EditPreferences;
import org.homeunix.thecave.buddi.view.menu.items.EditRecordTransaction;
import org.homeunix.thecave.buddi.view.menu.items.EditUndeleteAccount;
import org.homeunix.thecave.buddi.view.menu.items.EditUndeleteBudgetCategory;
import org.homeunix.thecave.buddi.view.menu.items.EditViewScheduledTransactions;
import org.homeunix.thecave.buddi.view.menu.items.EditViewTransactions;
import org.homeunix.thecave.moss.swing.MossFrame;
import org.homeunix.thecave.moss.swing.MossMenu;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class EditMenu extends MossMenu {
	public static final long serialVersionUID = 0;
	
	public EditMenu(MossFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT));
		
	}
	
	@Override
	public void updateMenus() {
		this.removeAll();
		
		if (getFrame() instanceof MainFrame){
			this.setEnabled(true);
			MainFrame frame = (MainFrame) getFrame();
			
			if (frame.isMyAccountsTabSelected()){
				this.add(new EditNewAccount(frame));
				this.add(new EditModifyAccount(frame));
				this.add(new EditDeleteAccount(frame));
				this.add(new EditUndeleteAccount(frame));
				this.addSeparator();
				this.add(new EditViewTransactions(frame));
			}
			else if (frame.isMyBudgetTabSelected()){
				this.add(new EditNewBudgetCategory(frame));
				this.add(new EditModifyBudgetCategory(frame));
				this.add(new EditDeleteBudgetCategory(frame));
				this.add(new EditUndeleteBudgetCategory(frame));
				this.addSeparator();
				this.add(new EditCopyBudgetCategoryValuesBackwards(frame));
				this.add(new EditCopyBudgetCategoryValuesForward(frame));
				this.addSeparator();
			}

			this.add(new EditViewScheduledTransactions(frame));
			if (!OperatingSystemUtil.isMac()){
				this.addSeparator();
				this.add(new EditPreferences(frame));
			}
		}
		else if (getFrame() instanceof TransactionFrame){
			this.setEnabled(true);
			TransactionFrame frame = (TransactionFrame) getFrame();
			this.add(new EditRecordTransaction(frame));
			this.add(new EditClearTransaction(frame));
			this.add(new EditDeleteTransaction(frame));
			if (!OperatingSystemUtil.isMac()){
				this.addSeparator();
				this.add(new EditPreferences(frame));
			}
		}
		else {
			this.setVisible(false);
		}
		
		super.updateMenus();
	}
}
