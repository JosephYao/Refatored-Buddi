/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.event.ActionEvent;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.moss.swing.MossMenuItem;

public class EditUnrollAllBudgetCategories extends MossMenuItem{
	public static final long serialVersionUID = 0;
	
	public EditUnrollAllBudgetCategories(MainFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_UNROLL_ALL_BUDGET_CATEGORIES));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!(getFrame() instanceof MainFrame))
			throw new RuntimeException("Calling frame not instance of BudgetFrame");
			
		for (BudgetCategory bc : ((Document) ((MainFrame) getFrame()).getDocument()).getBudgetCategories()) {
			bc.setExpanded(true);
		}
		
		((MainFrame) getFrame()).updateContent();
	}
}
