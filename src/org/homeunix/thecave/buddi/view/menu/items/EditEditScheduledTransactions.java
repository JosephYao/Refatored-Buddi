/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.event.ActionEvent;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.buddi.view.ScheduledTransactionListFrame;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.MossMenuItem;

public class EditEditScheduledTransactions extends MossMenuItem{
	public static final long serialVersionUID = 0;

	//This has to be a AccountFrame, as we need to get selected accounts.
	public EditEditScheduledTransactions(MainFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_EDIT_SCHEDULED_TRANSACTIONS));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			ScheduledTransactionListFrame scheduledTransactionsFrame = new ScheduledTransactionListFrame((MainFrame) getFrame());
			scheduledTransactionsFrame.openWindow(null, PrefsModel.getInstance().getScheduledTransactionWindowLocation());
		}
		catch (WindowOpenException foe){
			foe.printStackTrace();
		}
	}
}
