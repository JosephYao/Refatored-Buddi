/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.event.ActionEvent;
import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.exception.InvalidValueException;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.moss.swing.MossMenuItem;
import org.homeunix.thecave.moss.util.Log;

public class EditCopyBudgetCategoryValuesForward extends MossMenuItem{
	public static final long serialVersionUID = 0;

	public EditCopyBudgetCategoryValuesForward(MainFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_COPY_VALUES_TO_PREVIOUS_BUDGET_PERIOD));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (BudgetCategory bc : ((MainFrame) getFrame()).getSelectedBudgetCategories()) {
			long amount = bc.getAmount(new Date());
			try {
				bc.setAmount(bc.getBudgetPeriodType().getBudgetPeriodOffset(new Date(), -1), amount);
			}
			catch (InvalidValueException ive){
				Log.error(ive);
			}
		}

		((MainFrame) getFrame()).updateContent();
	}
	
	@Override
	public void updateMenus() {
		super.updateMenus();
		
		this.setEnabled(((MainFrame) getFrame()).getSelectedBudgetCategories().size() > 0);
	}
}
