/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.BudgetFrame;
import org.homeunix.thecave.buddi.view.dialogs.BudgetCategoryEditorDialog;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.menu.MossMenuItem;

public class EditNewBudgetCategory extends MossMenuItem{
	public static final long serialVersionUID = 0;

	public EditNewBudgetCategory(BudgetFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_NEW_BUDGET_CATEGORY),
				KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() + KeyEvent.SHIFT_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!(getFrame() instanceof BudgetFrame))
			throw new RuntimeException("Calling frame not instance of BudgetFrame");
			
		BudgetCategoryEditorDialog editor = new BudgetCategoryEditorDialog((BudgetFrame) getFrame(), ((BudgetFrame) getFrame()).getDataModel(), null);
		try {
			editor.openWindow();
		}
		catch (WindowOpenException woe){}
		
		((BudgetFrame) getFrame()).updateContent();
	}
}
