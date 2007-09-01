/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.event.ActionEvent;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.exception.InvalidValueException;
import org.homeunix.thecave.buddi.plugin.api.exception.ModelException;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.moss.swing.MossMenuItem;
import org.homeunix.thecave.moss.util.Log;

public class EditDeleteBudgetCategory extends MossMenuItem{
	public static final long serialVersionUID = 0;
	
	public EditDeleteBudgetCategory(MainFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_DELETE_BUDGET_CATEGORY));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!(getFrame() instanceof MainFrame))
			throw new RuntimeException("Calling frame not instance of BudgetFrame");
			
		for (BudgetCategory bc : ((MainFrame) getFrame()).getSelectedBudgetCategories()) {
			try {
				((Document) ((MainFrame) getFrame()).getDocument()).removeBudgetCategory(bc);
			}
			catch (ModelException me){
				try {
					bc.setDeleted(true);
				}
				catch (InvalidValueException ive){
					Log.error("Error setting deleted flag on budget category");
				}
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
