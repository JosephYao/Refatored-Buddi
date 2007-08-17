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
import org.homeunix.thecave.buddi.view.AccountFrame;
import org.homeunix.thecave.buddi.view.BudgetFrame;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.menu.MossMenuItem;
import org.homeunix.thecave.moss.swing.window.MossDocumentFrame;

public class EditViewBudget extends MossMenuItem {
	public static final long serialVersionUID = 0;
	
	public EditViewBudget(MossDocumentFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_VIEW_BUDGET),
				KeyStroke.getKeyStroke(KeyEvent.VK_B, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			((AccountFrame) getFrame()).getDataModel().startBatchChange();
			BudgetFrame budgetFrame = new BudgetFrame((AccountFrame) getFrame());
			budgetFrame.openWindow(PrefsModel.getInstance().getBudgetWindowSize(), PrefsModel.getInstance().getBudgetWindowLocation());
		}
		catch (WindowOpenException foe){}
		
		((AccountFrame) getFrame()).getDataModel().finishBatchChange();
	}
}
