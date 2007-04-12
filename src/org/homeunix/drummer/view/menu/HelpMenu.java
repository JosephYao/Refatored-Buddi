/*
 * Created on Apr 8, 2007 by wyatt
 */
package org.homeunix.drummer.view.menu;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import net.roydesign.app.AboutJMenuItem;
import net.roydesign.app.Application;
import net.roydesign.ui.JScreenMenu;
import net.roydesign.ui.JScreenMenuItem;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.controller.menu.HelpMenuController;
import org.homeunix.drummer.util.DocumentationFactory;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;

public class HelpMenu extends JScreenMenu {
	public static final long serialVersionUID = 0;
	private final HelpMenuController controller;
	
	public HelpMenu(AbstractFrame frame) {
		controller = new HelpMenuController();
		
		// If the menu is not already present because it's provided by
		// the OS (like on Mac OS X), then append it to our menu

		
		this.setText(Translate.getInstance().get(TranslateKeys.MENU_HELP));
		
		final JScreenMenuItem showHelp = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MENU_HELP_HELP));
		final AboutJMenuItem about = Application.getInstance().getAboutJMenuItem();
		
		showHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				KeyEvent.SHIFT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		about.setText(Translate.getInstance().get(TranslateKeys.MENU_HELP_ABOUT));
		
		showHelp.setActionCommand(TranslateKeys.MENU_HELP.toString());
		about.setActionCommand(TranslateKeys.MENU_HELP_ABOUT.toString());

		showHelp.addActionListener(controller);
		about.addActionListener(controller);

		this.add(showHelp);
		if (!AboutJMenuItem.isAutomaticallyPresent()){
			this.add(about);
		}
		this.addSeparator();
		this.add(DocumentationFactory.getDocumentsMenu());
		this.add(DocumentationFactory.getLicensesMenu());

	}
}
