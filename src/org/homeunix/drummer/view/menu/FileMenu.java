/*
 * Created on Apr 8, 2007 by wyatt
 */
package org.homeunix.drummer.view.menu;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import net.roydesign.app.Application;
import net.roydesign.app.QuitJMenuItem;
import net.roydesign.ui.JScreenMenu;
import net.roydesign.ui.JScreenMenuItem;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.controller.menu.FileMenuController;
import org.homeunix.drummer.plugins.PluginFactory;
import org.homeunix.drummer.view.AbstractBuddiFrame;
import org.homeunix.drummer.view.HTMLExport;
import org.homeunix.drummer.view.MainFrame;

public class FileMenu extends JScreenMenu {
	public final static long serialVersionUID = 0;
	private final FileMenuController controller;
	
	public FileMenu(AbstractBuddiFrame frame) {
		controller = new FileMenuController(frame);
		
		this.setText(Translate.getInstance().get(TranslateKeys.MENU_FILE));
		
		//File menu items
		final JScreenMenuItem openFile = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MENU_FILE_OPEN));
		final JScreenMenuItem newFile = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MENU_FILE_NEW));
		final JScreenMenuItem saveAsFile = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MENU_FILE_SAVE_AS));
		final JScreenMenuItem backup = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MENU_FILE_BACKUP));
		final JScreenMenuItem restore = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MENU_FILE_RESTORE));
		final JScreenMenuItem encrypt = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MENU_FILE_ENCRYPT));
		final JScreenMenuItem decrypt = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MENU_FILE_DECRYPT));
		final JScreenMenuItem print = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MENU_FILE_PRINT));
		final JScreenMenuItem close = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MENU_FILE_CLOSE_WINDOW));
		final JScreenMenu exports = new JScreenMenu(Translate.getInstance().get(TranslateKeys.MENU_FILE_EXPORT));
		final JScreenMenu imports = new JScreenMenu(Translate.getInstance().get(TranslateKeys.MENU_FILE_IMPORT));
		final QuitJMenuItem quit = Application.getInstance().getQuitJMenuItem();
		quit.setText(Translate.getInstance().get(TranslateKeys.MENU_FILE_QUIT));
		
		openFile.setActionCommand(TranslateKeys.MENU_FILE_OPEN.toString());
		newFile.setActionCommand(TranslateKeys.MENU_FILE_NEW.toString());
		saveAsFile.setActionCommand(TranslateKeys.MENU_FILE_SAVE_AS.toString());
		backup.setActionCommand(TranslateKeys.MENU_FILE_BACKUP.toString());
		restore.setActionCommand(TranslateKeys.MENU_FILE_RESTORE.toString());
		encrypt.setActionCommand(TranslateKeys.MENU_FILE_ENCRYPT.toString());
		decrypt.setActionCommand(TranslateKeys.MENU_FILE_DECRYPT.toString());
		print.setActionCommand(TranslateKeys.MENU_FILE_PRINT.toString());
		close.setActionCommand(TranslateKeys.MENU_FILE_CLOSE_WINDOW.toString());
		quit.setActionCommand(TranslateKeys.MENU_FILE_QUIT.toString());
		
		openFile.addActionListener(controller);
		newFile.addActionListener(controller);
		saveAsFile.addActionListener(controller);
		backup.addActionListener(controller);
		restore.addActionListener(controller);
		encrypt.addActionListener(controller);
		decrypt.addActionListener(controller);
		print.addActionListener(controller);
		close.addActionListener(controller);
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					MainFrame.getInstance().closeWindow();
			}
		});
		
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				KeyEvent.SHIFT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				KeyEvent.SHIFT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		saveAsFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				KeyEvent.SHIFT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		backup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
				KeyEvent.ALT_MASK + KeyEvent.SHIFT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		restore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				KeyEvent.ALT_MASK + KeyEvent.SHIFT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		//Get all the export and import plugins
		for (JScreenMenuItem menuItem : PluginFactory.getExportMenuItems(frame)) {
			exports.add(menuItem);
		}

		for (JScreenMenuItem menuItem : PluginFactory.getImportMenuItems(frame)) {
			imports.add(menuItem);
		}

		// Add the menus to the main menu.
		if (frame instanceof MainFrame){
			this.add(newFile);
			this.add(openFile);
			this.add(saveAsFile);
			this.addSeparator();
			this.add(backup);
			this.add(restore);
			this.addSeparator();
			this.add(encrypt);
			this.add(decrypt);
			this.addSeparator();
		}
		if (frame instanceof HTMLExport){
			this.add(print);
			this.addSeparator();
		}
		this.add(exports);
		this.add(imports);
		//We want to show the close button on all frames 
		// except for the main one.
		if (!(frame instanceof MainFrame)){
			this.addSeparator();
			this.add(close);
		}

		//We add quit if it's not already here.
		if (!QuitJMenuItem.isAutomaticallyPresent())
			this.add(quit);

	}
}
