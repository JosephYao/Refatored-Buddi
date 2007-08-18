/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.AccountFrame;
import org.homeunix.thecave.buddi.view.TransactionFrame;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.menu.MossMenuItem;

public class EditViewTransactions extends MossMenuItem{
	public static final long serialVersionUID = 0;
	
	//This has to be a AccountFrame, as we need to get selected accounts.
	public EditViewTransactions(AccountFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_VIEW_TRANSACTIONS),
				KeyStroke.getKeyStroke(KeyEvent.VK_T, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	
	@Override
	public void updateMenus() {
		super.updateMenus();
		
		this.setEnabled(((AccountFrame) getFrame()).getSelectedAccounts().size() > 0);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (Account a : ((AccountFrame) getFrame()).getSelectedAccounts()) {
			try {
				TransactionFrame transactionsFrame = new TransactionFrame(((AccountFrame) getFrame()).getDataModel(), (AccountFrame) getFrame(), a);
				transactionsFrame.openWindow(PrefsModel.getInstance().getTransactionWindowSize(), PrefsModel.getInstance().getTransactionWindowLocation());
			}
			catch (WindowOpenException foe){
				foe.printStackTrace();
			}
		}
	}
}
