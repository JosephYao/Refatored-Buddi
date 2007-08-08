/*
 * Created on Apr 8, 2007 by wyatt
 */
package org.homeunix.drummer.view.menu;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import net.roydesign.ui.JScreenMenu;
import net.roydesign.ui.JScreenMenuItem;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.controller.menu.WindowMenuController;
import org.homeunix.drummer.view.MainFrame;
import org.homeunix.thecave.moss.gui.abstracts.window.AbstractFrame;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class WindowMenu extends JScreenMenu {
	public static final long serialVersionUID = 0;
	private final WindowMenuController controller;
	
	public WindowMenu(AbstractFrame frame) {
		controller = new WindowMenuController(frame);
		
		this.setText(Translate.getInstance().get(TranslateKeys.MENU_WINDOW));
		
		final JScreenMenuItem minimize = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MENU_WINDOW_MINIMIZE));
		final JScreenMenuItem openMainWindow = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MENU_WINDOW_MAIN_WINDOW));
		final JScreenMenuItem leftTab = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MENU_WINDOW_LEFT_TAB));
		final JScreenMenuItem rightTab = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MENU_WINDOW_RIGHT_TAB));

		minimize.setActionCommand(TranslateKeys.MENU_WINDOW_MINIMIZE.toString());
		openMainWindow.setActionCommand(TranslateKeys.MENU_WINDOW_MAIN_WINDOW.toString());
		leftTab.setActionCommand(TranslateKeys.MENU_WINDOW_LEFT_TAB.toString());
		rightTab.setActionCommand(TranslateKeys.MENU_WINDOW_RIGHT_TAB.toString());		

		minimize.addActionListener(controller);
		openMainWindow.addActionListener(controller);
		leftTab.addActionListener(controller);
		rightTab.addActionListener(controller);
		
		minimize.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		leftTab.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,
				KeyEvent.ALT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		rightTab.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,
				KeyEvent.ALT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		if (OperatingSystemUtil.isMac() && frame == null){
			this.add(openMainWindow);
			this.addSeparator();
		}
		if (frame instanceof MainFrame){
			this.add(leftTab);
			this.add(rightTab);
			this.addSeparator();
		}
		this.add(minimize);		
	}
}
