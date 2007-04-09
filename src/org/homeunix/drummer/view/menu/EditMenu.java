/*
 * Created on Apr 8, 2007 by wyatt
 */
package org.homeunix.drummer.view.menu;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import net.roydesign.app.Application;
import net.roydesign.app.PreferencesJMenuItem;
import net.roydesign.ui.JScreenMenu;
import net.roydesign.ui.JScreenMenuItem;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.controller.menu.EditMenuController;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.MainFrame;
import org.homeunix.drummer.view.TransactionsFrame;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;

public class EditMenu extends JScreenMenu {
	public static final long serialVersionUID = 0;
	private final EditMenuController controller;
	
	public EditMenu(AbstractFrame frame) {
		controller = new EditMenuController(frame);
		
		this.setText(Translate.getInstance().get(TranslateKeys.EDIT));
		
		final JScreenMenuItem toggleReconciled = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.TOGGLE_RECONCILED));
		final JScreenMenuItem toggleCleared = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.TOGGLE_CLEARED));
		final JScreenMenuItem editAutomaticTransactions = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.EDIT_SCHEDULED_ACTIONS));
		final JScreenMenuItem clearTransaction = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.CLEAR) + " / " + Translate.getInstance().get(TranslateKeys.NEW));
		final JScreenMenuItem recordTransaction = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.RECORD) + " / " + Translate.getInstance().get(TranslateKeys.UPDATE));
		final PreferencesJMenuItem preferences = Application.getInstance().getPreferencesJMenuItem();
		preferences.setText(Translate.getInstance().get(TranslateKeys.PREFERENCES_MENU_ITEM));

		toggleReconciled.setActionCommand(TranslateKeys.TOGGLE_RECONCILED.toString());
		toggleCleared.setActionCommand(TranslateKeys.TOGGLE_CLEARED.toString());
		editAutomaticTransactions.setActionCommand(TranslateKeys.EDIT_SCHEDULED_ACTIONS.toString());
		clearTransaction.setActionCommand(TranslateKeys.CLEAR.toString());
		recordTransaction.setActionCommand(TranslateKeys.RECORD.toString());
		preferences.setActionCommand(TranslateKeys.PREFERENCES_MENU_ITEM.toString());

		toggleReconciled.addActionListener(controller);
		toggleCleared.addActionListener(controller);
		editAutomaticTransactions.addActionListener(controller);
		clearTransaction.addActionListener(controller);
		recordTransaction.addActionListener(controller);
		preferences.addActionListener(controller);
		
		toggleReconciled.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				KeyEvent.SHIFT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		toggleCleared.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				KeyEvent.SHIFT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		clearTransaction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		recordTransaction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		
		if (frame instanceof TransactionsFrame){
			this.add(recordTransaction);
			this.add(clearTransaction);
			this.addSeparator();
			if (PrefsInstance.getInstance().getPrefs().isShowAdvanced()){
				this.add(toggleCleared);
				this.add(toggleReconciled);
				this.addSeparator();
			}
		}
		if (frame instanceof MainFrame){
			this.add(editAutomaticTransactions);
		}
		if (!PreferencesJMenuItem.isAutomaticallyPresent()){
			this.add(preferences);
		}
	}
}
