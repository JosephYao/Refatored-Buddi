/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.event.ActionEvent;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.buddi.view.dialogs.BudgetCategoryEditorDialog;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.MossMenuItem;

public class EditModifyBudgetCategory extends MossMenuItem{
	public static final long serialVersionUID = 0;

	public EditModifyBudgetCategory(MainFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_MODIFY_BUDGET_CATEGORY));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (BudgetCategory bc : ((MainFrame) getFrame()).getSelectedBudgetCategories()) {
			BudgetCategoryEditorDialog editor = new BudgetCategoryEditorDialog((MainFrame) getFrame(), (DataModel) ((MainFrame) getFrame()).getDocument(), bc);
			try {
				editor.openWindow();
			}
			catch (WindowOpenException woe){}
		}

		((MainFrame) getFrame()).updateContent();
	}
	
	@Override
	public void updateMenus() {
		super.updateMenus();
		
		this.setEnabled(((MainFrame) getFrame()).getSelectedBudgetCategories().size() > 0);
	}
}
