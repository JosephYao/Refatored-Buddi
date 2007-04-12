/*
 * Created on Apr 8, 2007 by wyatt
 */
package org.homeunix.drummer.controller.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.view.PreferencesDialog;
import org.homeunix.drummer.view.ScheduledTransactionsListFrame;
import org.homeunix.drummer.view.TransactionsFrame;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;
import org.homeunix.thecave.moss.util.Log;

public class EditMenuController implements ActionListener {
	public final static long serialVersionUID = 0;
	private AbstractFrame frame;

	public EditMenuController(AbstractFrame frame){
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(TranslateKeys.MENU_EDIT_TOGGLE_RECONCILED.toString())){
			((TransactionsFrame) frame).toggleReconciled();
		}
		else if (e.getActionCommand().equals(TranslateKeys.MENU_EDIT_TOGGLE_CLEARED.toString())){
			((TransactionsFrame) frame).toggleCleared();
		}
		else if (e.getActionCommand().equals(TranslateKeys.MENU_EDIT_SCHEDULED_ACTIONS.toString())){
			new ScheduledTransactionsListFrame().openWindow();
		}
		else if (e.getActionCommand().equals(TranslateKeys.CLEAR.toString())){
			((TransactionsFrame) frame).clickClear();
		}
		else if (e.getActionCommand().equals(TranslateKeys.RECORD.toString())){
			((TransactionsFrame) frame).clickRecord();
		}
		else if (e.getActionCommand().equals(TranslateKeys.MENU_EDIT_PREFERENCES.toString())){
			new PreferencesDialog().openWindow();
		}
		else {
			Log.debug("Clicked " + e.getActionCommand());
		}
	}
}
