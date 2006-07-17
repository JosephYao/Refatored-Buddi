/*
 * Created on May 16, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import net.roydesign.app.AboutJMenuItem;
import net.roydesign.app.Application;
import net.roydesign.app.PreferencesJMenuItem;
import net.roydesign.app.QuitJMenuItem;
import net.roydesign.ui.JScreenMenu;
import net.roydesign.ui.JScreenMenuBar;
import net.roydesign.ui.JScreenMenuItem;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.controller.DataInstance;
import org.homeunix.drummer.controller.PrefsInstance;
import org.homeunix.drummer.util.BrowserLauncher;
import org.homeunix.drummer.util.FileFunctions;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.util.PrintUtilities;
import org.homeunix.drummer.view.AbstractBudgetFrame;
import org.homeunix.drummer.view.layout.AboutDialog;
import org.homeunix.drummer.view.logic.MainBudgetFrame;
import org.homeunix.drummer.view.logic.PreferencesFrame;
import org.homeunix.drummer.view.logic.TransactionsFrame;

public class BuddiMenu extends JScreenMenuBar {
	public static final long serialVersionUID = 0;
	
	private final JFrame frame;
	
	public BuddiMenu(JFrame frame){
		super();
		
		this.frame = frame;
		
		// Get the application instance
		final Application app = Application.getInstance();
		
		final JScreenMenu file = new JScreenMenu(Translate.getInstance().get(TranslateKeys.FILE));
		final JScreenMenu edit = new JScreenMenu(Translate.getInstance().get(TranslateKeys.EDIT));
		final JScreenMenu window = new JScreenMenu(Translate.getInstance().get(TranslateKeys.WINDOW));
		final JScreenMenu help = new JScreenMenu(Translate.getInstance().get(TranslateKeys.HELP));
		
		this.add(file);
		this.add(edit);
		this.add(window);
		this.add(help);
		
		//File menu items
		final JScreenMenuItem open = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.OPEN_DATA_FILE));
		final JScreenMenuItem newFile = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.NEW_DATA_FILE));
		final JScreenMenuItem backup = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.BACKUP_DATA_FILE));
		final JScreenMenuItem restore = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.RESTORE_DATA_FILE));
		final JScreenMenuItem print = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.PRINT));
		final JScreenMenuItem close = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.CLOSE_WINDOW));
		
		newFile.addUserFrame(MainBudgetFrame.class);
		open.addUserFrame(MainBudgetFrame.class);
		backup.addUserFrame(MainBudgetFrame.class);
		restore.addUserFrame(MainBudgetFrame.class);
		//close.addUserFrame(TransactionsFrame.class);
		
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				KeyEvent.SHIFT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				KeyEvent.SHIFT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		backup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
				KeyEvent.ALT_MASK + KeyEvent.SHIFT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		restore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				KeyEvent.ALT_MASK + KeyEvent.SHIFT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		
		file.add(newFile);
		file.add(open);
		file.addSeparator();
		file.add(backup);
		file.add(restore);
		file.addSeparator();
		file.add(print);
		file.addSeparator();
		file.add(close);
		
		
		//Edit menu items
		final JScreenMenuItem cut = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.CUT));
		final JScreenMenuItem copy = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.COPY));
		final JScreenMenuItem paste = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.PASTE));
		
		cut.addUserFrame(TransactionsFrame.class);
		copy.addUserFrame(TransactionsFrame.class);
		paste.addUserFrame(TransactionsFrame.class);

		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		
		
		//Window menu items
		final JScreenMenuItem minimize = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.MINIMIZE));
		//final JScreenMenuItem zoom = new JScreenMenuItem(Strings.inst().get(Strings.ZOOM));
		
		minimize.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		window.add(minimize);
		//window.add(zoom);
		
		
		//Help menu items
		final JScreenMenuItem showHelp = new JScreenMenuItem(Translate.getInstance().get(TranslateKeys.BUDDI_HELP));
		showHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				KeyEvent.SHIFT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		help.add(showHelp);

		newFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.setDialogTitle(Translate.getInstance().get(TranslateKeys.CHOOSE_DATASTORE_LOCATION));
				if (jfc.showSaveDialog(MainBudgetFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
					if (jfc.getSelectedFile().isDirectory())
						JOptionPane.showMessageDialog(
								null, 
								Translate.getInstance().get(TranslateKeys.CANNOT_SAVE_OVER_DIR),
								Translate.getInstance().get(TranslateKeys.ERROR),
								JOptionPane.ERROR_MESSAGE
						);
					else{
						if (jfc.getSelectedFile().exists()){
							if (JOptionPane.showConfirmDialog(
									null, 
									Translate.getInstance().get(TranslateKeys.OVERWRITE_EXISTING_FILE_MESSAGE) + jfc.getSelectedFile(),
									Translate.getInstance().get(TranslateKeys.OVERWRITE_EXISTING_FILE),
									JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
								return;
							}
						}

						final String location;
						if (jfc.getSelectedFile().getName().endsWith(Const.DATA_FILE_EXTENSION))
							location = jfc.getSelectedFile().getAbsolutePath();
						else
							location = jfc.getSelectedFile().getAbsolutePath() + Const.DATA_FILE_EXTENSION;
						
						DataInstance.getInstance().loadDataModel(new File(location), true);
						PrefsInstance.getInstance().getPrefs().setDataFile(location);
						MainBudgetFrame.getInstance().updateContent();
						JOptionPane.showMessageDialog(
								null,
								Translate.getInstance().get(TranslateKeys.NEW_DATA_FILE_SAVED) + location, 
								Translate.getInstance().get(TranslateKeys.FILE_SAVED), 
								JOptionPane.INFORMATION_MESSAGE
						);
					}
				}
			}
		});
		
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.setFileHidingEnabled(true);
				jfc.setFileFilter(new FileFilter(){
					public boolean accept(File arg0) {
						if (arg0.isDirectory() 
								|| arg0.getName().endsWith(Const.DATA_FILE_EXTENSION))
							return true;
						else
							return false;
					}

					public String getDescription() {
						return Translate.getInstance().get(TranslateKeys.BUDDI_FILE_DESC);
					}
				});
				jfc.setDialogTitle(Translate.getInstance().get(TranslateKeys.OPEN_DATA_FILE_TITLE));
				if (jfc.showOpenDialog(MainBudgetFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
					if (jfc.getSelectedFile().isDirectory())
						Log.debug(Translate.getInstance().get(TranslateKeys.MUST_SELECT_BUDDI_FILE));
					
					else{
						if (JOptionPane.showConfirmDialog(
								null, 
								Translate.getInstance().get(TranslateKeys.CONFIRM_LOAD_BACKUP_FILE), 
								Translate.getInstance().get(TranslateKeys.CLOSE_DATA_FILE),
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
							DataInstance.getInstance().loadDataModel(jfc.getSelectedFile(), false);
							PrefsInstance.getInstance().getPrefs().setDataFile(jfc.getSelectedFile().getAbsolutePath());
							PrefsInstance.getInstance().savePrefs();
							MainBudgetFrame.getInstance().updateContent();
							JOptionPane.showMessageDialog(
									null, 
									Translate.getInstance().get(TranslateKeys.SUCCESSFUL_OPEN_FILE) + jfc.getSelectedFile().getAbsolutePath().toString(),
									Translate.getInstance().get(TranslateKeys.OPENED_FILE), 
									JOptionPane.INFORMATION_MESSAGE
							);
						}
						else
							JOptionPane.showMessageDialog(
									null,
									Translate.getInstance().get(TranslateKeys.CANCELLED_FILE_LOAD_MESSAGE), 
									Translate.getInstance().get(TranslateKeys.CANCELLED_FILE_LOAD),
									JOptionPane.INFORMATION_MESSAGE
							);
					}
				}
			}
		});
		
		//Add the action listeners.  Sure, it's kinda ugly, but it works, and I am too lazy to change it.
		backup.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.setDialogTitle(Translate.getInstance().get(TranslateKeys.CHOOSE_BACKUP_FILE));
				if (jfc.showSaveDialog(MainBudgetFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
					if (jfc.getSelectedFile().isDirectory())
						Log.debug(Translate.getInstance().get(TranslateKeys.CANNOT_SAVE_OVER_DIR));
					
					else{
						final String location;
						if (jfc.getSelectedFile().getName().endsWith(Const.DATA_FILE_EXTENSION))
							location = jfc.getSelectedFile().getAbsolutePath();
						else
							location = jfc.getSelectedFile().getAbsolutePath() + Const.DATA_FILE_EXTENSION;
						
						DataInstance.getInstance().saveDataModel(location);
						JOptionPane.showMessageDialog(null, Translate.getInstance().get(TranslateKeys.SUCCESSFUL_BACKUP) + location, Translate.getInstance().get(TranslateKeys.FILE_SAVED), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		restore.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.setFileHidingEnabled(true);
				jfc.setFileFilter(new FileFilter(){
					public boolean accept(File arg0) {
						if (arg0.isDirectory() 
								|| arg0.getName().endsWith(Const.DATA_FILE_EXTENSION))
							return true;
						else
							return false;
					}

					public String getDescription() {
						return Translate.getInstance().get(TranslateKeys.BUDDI_FILE_DESC);
					}
				});
				jfc.setDialogTitle(Translate.getInstance().get(TranslateKeys.RESTORE_DATA_FILE));
				if (jfc.showOpenDialog(MainBudgetFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
					if (jfc.getSelectedFile().isDirectory())
						Log.debug(Translate.getInstance().get(TranslateKeys.MUST_SELECT_BUDDI_FILE));
					
					else{
						if (JOptionPane.showConfirmDialog(
								null, 
								Translate.getInstance().get(TranslateKeys.CONFIRM_RESTORE_BACKUP_FILE), 
								Translate.getInstance().get(TranslateKeys.RESTORE_DATA_FILE),
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
							File oldFile = new File(PrefsInstance.getInstance().getPrefs().getDataFile());
							try{
								FileFunctions.copyFile(jfc.getSelectedFile(), oldFile);
								DataInstance.getInstance().loadDataModel(oldFile, false);
								MainBudgetFrame.getInstance().updateContent();
								JOptionPane.showMessageDialog(
										null, 
										Translate.getInstance().get(TranslateKeys.SUCCESSFUL_RESTORE_FILE) + jfc.getSelectedFile().getAbsolutePath().toString(), 
										Translate.getInstance().get(TranslateKeys.RESTORED_FILE), 
										JOptionPane.INFORMATION_MESSAGE
								);
							}
							catch (IOException ioe){
								JOptionPane.showMessageDialog(
										null, 
										ioe, 
										"Flagrant System Error",
										JOptionPane.ERROR_MESSAGE
								);
							}
						}
						else
							JOptionPane.showMessageDialog(
									null, 
									Translate.getInstance().get(TranslateKeys.CANCEL_FILE_RESTORE_MESSAGE), 
									Translate.getInstance().get(TranslateKeys.CANCELLED_FILE_RESTORE), 
									JOptionPane.INFORMATION_MESSAGE
							);
					}
				}
			}
		});
		
		print.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (BuddiMenu.this.frame instanceof AbstractBudgetFrame){
					Component toPrint = ((AbstractBudgetFrame) BuddiMenu.this.frame).getPrintedComponent();
					
					if (toPrint != null){
						PrintUtilities pu = new PrintUtilities(toPrint);
						
						pu.print();
					}
					else
						JOptionPane.showMessageDialog(
								null, 
								Translate.getInstance().get(TranslateKeys.NOTHING_TO_PRINT),
								Translate.getInstance().get(TranslateKeys.PRINT_ERROR),
								JOptionPane.INFORMATION_MESSAGE
						);
				}
			}
		});
		
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (BuddiMenu.this.frame != null)
					BuddiMenu.this.frame.setVisible(false);
			}
		});
		
		minimize.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (BuddiMenu.this.frame != null)
					BuddiMenu.this.frame.setExtendedState(JFrame.ICONIFIED);
			}
		});

//		zoom.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent arg0) {
//				if (BudgetMenu.this.frame != null)
//					BudgetMenu.this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//			}
//		});

		
		showHelp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try{
					File localHelp = new File(
							Const.HELP_FOLDER 
							+ File.separator
							+ PrefsInstance.getInstance().getPrefs().getLanguage().replaceAll("-.*$", "")
							+ File.separator
							+ Const.HELP_FILE);
					final String location;
					if (localHelp.exists())
						location = "file://" + localHelp.getAbsolutePath();
					else
						location = "http://buddi.sourceforge.net/" + PrefsInstance.getInstance().getPrefs().getLanguage().replaceAll("-.*$", "");
					
					Log.debug("Trying to open Help at " + location + "...");
					BrowserLauncher.openURL(location);
				}
				catch (IOException ioe){
					Log.error(ioe);
				}
			}
		});
		
		// Get an About item instance. Here it's for Swing but there
		// are also AWT variants like getAboutMenuItem().
		AboutJMenuItem about = app.getAboutJMenuItem();
		about.setText(Translate.getInstance().get(TranslateKeys.ABOUT_MENU_ITEM));
		about.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					new AboutDialog();
				}
			});
				
		// If the menu is not already present because it's provided by
		// the OS (like on Mac OS X), then append it to our menu
		if (!AboutJMenuItem.isAutomaticallyPresent())
			help.add(about);
		
		// Do the same thing for the Preferences and Quit items
		PreferencesJMenuItem preferences = app.getPreferencesJMenuItem();
		preferences.setText(Translate.getInstance().get(TranslateKeys.PREFERENCES_MENU_ITEM));
		preferences.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					new PreferencesFrame().clearContent().openWindow();
				}
			});
		if (!PreferencesJMenuItem.isAutomaticallyPresent())
			edit.add(preferences);
		QuitJMenuItem quit = app.getQuitJMenuItem();
		quit.setText(Translate.getInstance().get(TranslateKeys.QUIT_MENU_ITEM));
		quit.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if (MainBudgetFrame.getInstance() != null)
						MainBudgetFrame.getInstance().savePosition();
					Log.debug("Exiting");
					System.exit(0);
				}
			});
		if (!QuitJMenuItem.isAutomaticallyPresent())
			file.add(quit);
	}
}
