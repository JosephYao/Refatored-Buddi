/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.BudgetFrame;
import org.homeunix.thecave.moss.swing.menu.MossMenuItem;

public class EditDeleteBudgetCategory extends MossMenuItem{
	public static final long serialVersionUID = 0;
	
	public EditDeleteBudgetCategory(BudgetFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_DELETE_BUDGET_CATEGORY),
				KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!(getFrame() instanceof BudgetFrame))
			throw new RuntimeException("Calling frame not instance of BudgetFrame");
			
		for (BudgetCategory bc : ((BudgetFrame) getFrame()).getSelectedBudgetCategories()) {
			((BudgetFrame) getFrame()).getDataModel().removeBudgetCategory(bc);
		}

		((BudgetFrame) getFrame()).updateContent();
	}
	
	@Override
	public void updateMenus() {
		super.updateMenus();
		
		this.setEnabled(((BudgetFrame) getFrame()).getSelectedBudgetCategories().size() > 0);
	}
}
