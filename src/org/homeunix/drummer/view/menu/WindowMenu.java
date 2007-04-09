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
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class WindowMenu extends JScreenMenu {
	public static final long serialVersionUID = 0;
	private final WindowMenuController controller;
	
	public WindowMenu(AbstractFrame frame) {
		controller = new WindowMenuController(frame);
		
		this.setText(Translate.getInstance().get(TranslateKeys.WINDOW));
		
		final JScreenMenuItem minimize = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MINIMIZE));
		final JScreenMenuItem openMainWindow = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MAIN_BUDDI_WINDOW));

		minimize.setActionCommand(TranslateKeys.MINIMIZE.toString());
		openMainWindow.setActionCommand(TranslateKeys.MAIN_BUDDI_WINDOW.toString());

		minimize.addActionListener(controller);
		openMainWindow.addActionListener(controller);

		
		minimize.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		if (OperatingSystemUtil.isMac() && frame == null){
			this.add(openMainWindow);
			this.addSeparator();
		}
		this.add(minimize);		
	}
}
