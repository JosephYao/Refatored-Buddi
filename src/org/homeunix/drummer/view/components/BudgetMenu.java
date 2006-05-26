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
import org.homeunix.drummer.Strings;
import org.homeunix.drummer.controller.DataInstance;
import org.homeunix.drummer.controller.PrefsInstance;
import org.homeunix.drummer.util.BrowserLauncher;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.util.PrintUtilities;
import org.homeunix.drummer.view.AbstractBudgetFrame;
import org.homeunix.drummer.view.layout.AboutDialog;
import org.homeunix.drummer.view.logic.MainBudgetFrame;
import org.homeunix.drummer.view.logic.PreferencesFrame;
import org.homeunix.drummer.view.logic.TransactionsFrame;

public class BudgetMenu extends JScreenMenuBar {
	public static final long serialVersionUID = 0;
	
	private final JFrame frame;
	
	public BudgetMenu(JFrame frame){
		super();
		
		this.frame = frame;
		
		// Get the application instance
		final Application app = Application.getInstance();
		
		final JScreenMenu file = new JScreenMenu(Strings.inst().get(Strings.FILE));
		final JScreenMenu edit = new JScreenMenu(Strings.inst().get(Strings.EDIT));
		final JScreenMenu window = new JScreenMenu(Strings.inst().get(Strings.WINDOW));
		final JScreenMenu help = new JScreenMenu(Strings.inst().get(Strings.HELP));
		
		this.add(file);
		this.add(edit);
		this.add(window);
		this.add(help);
		
		//File menu items
		final JScreenMenuItem backup = new JScreenMenuItem(Strings.inst().get(Strings.BACKUP_DATA_FILE));
		final JScreenMenuItem open = new JScreenMenuItem(Strings.inst().get(Strings.OPEN_DATA_FILE));
		final JScreenMenuItem print = new JScreenMenuItem(Strings.inst().get(Strings.PRINT));
		final JScreenMenuItem close = new JScreenMenuItem(Strings.inst().get(Strings.CLOSE_WINDOW));
		
		backup.addUserFrame(MainBudgetFrame.class);
		open.addUserFrame(MainBudgetFrame.class);
		//close.addUserFrame(TransactionsFrame.class);
		
		backup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
				KeyEvent.SHIFT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                KeyEvent.SHIFT_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		
		file.add(backup);
		file.add(open);
		file.addSeparator();
		file.add(print);
		file.addSeparator();
		file.add(close);
		
		
		//Edit menu items
		final JScreenMenuItem cut = new JScreenMenuItem(Strings.inst().get(Strings.CUT));
		final JScreenMenuItem copy = new JScreenMenuItem(Strings.inst().get(Strings.COPY));
		final JScreenMenuItem paste = new JScreenMenuItem(Strings.inst().get(Strings.PASTE));
		
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
		final JScreenMenuItem minimize = new JScreenMenuItem(Strings.inst().get(Strings.MINIMIZE));
		//final JScreenMenuItem zoom = new JScreenMenuItem(Strings.inst().get(Strings.ZOOM));
		
		minimize.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		window.add(minimize);
		//window.add(zoom);
		
		
		//Help menu items
		final JScreenMenuItem showHelp = new JScreenMenuItem(Strings.inst().get(Strings.BUDDI_HELP));
		showHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		help.add(showHelp);

		//Add the action listeners.  Sure, it's kinda ugly, but it works, and I am too lazy to change it.
		backup.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.setDialogTitle(Strings.inst().get(Strings.CHOOSE_BACKUP_FILE));
				if (jfc.showSaveDialog(MainBudgetFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
					if (jfc.getSelectedFile().isDirectory())
						Log.debug(Strings.inst().get(Strings.CANNOT_SAVE_OVER_DIR));
					
					else{
						final String location;
						if (jfc.getSelectedFile().getName().endsWith(Const.DATA_FILE_EXTENSION))
							location = jfc.getSelectedFile().getAbsolutePath();
						else
							location = jfc.getSelectedFile().getAbsolutePath() + Const.DATA_FILE_EXTENSION;
						
						DataInstance.getInstance().saveDataModel(location);
						JOptionPane.showMessageDialog(null, Strings.inst().get(Strings.SUCCESSFUL_BACKUP) + location, Strings.inst().get(Strings.FILE_SAVED), JOptionPane.INFORMATION_MESSAGE);
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
						return Strings.inst().get(Strings.BUDDI_FILE_DESC);
					}
				});
				jfc.setDialogTitle(Strings.inst().get(Strings.LOAD_BACKUP_FILE));
				if (jfc.showOpenDialog(MainBudgetFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
					if (jfc.getSelectedFile().isDirectory())
						Log.debug(Strings.inst().get(Strings.MUST_SELECT_BUDDI_FILE));
					
					else{
						if (JOptionPane.showConfirmDialog(
								null, 
								Strings.inst().get(Strings.CONFIRM_LOAD_BACKUP_FILE), 
								Strings.inst().get(Strings.CLOSE_DATA_FILE),
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
							DataInstance.getInstance().loadDataModel(jfc.getSelectedFile());
							PrefsInstance.getInstance().getPrefs().setDataFile(jfc.getSelectedFile().getAbsolutePath());
							PrefsInstance.getInstance().savePrefs();
							MainBudgetFrame.getInstance().updateContent();
							JOptionPane.showMessageDialog(null, Strings.inst().get(Strings.SUCCESSFUL_OPEN_FILE) + jfc.getSelectedFile().getAbsolutePath().toString(), Strings.inst().get(Strings.OPENED_FILE), JOptionPane.INFORMATION_MESSAGE);
						}
						else
							JOptionPane.showMessageDialog(null, Strings.inst().get(Strings.KEEP_DATA_FILE_OPEN), Strings.inst().get(Strings.CANCELLED_FILE_LOAD), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		print.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (BudgetMenu.this.frame instanceof AbstractBudgetFrame){
					Component toPrint = ((AbstractBudgetFrame) BudgetMenu.this.frame).getPrintedComponent();
					
					if (toPrint != null){
						PrintUtilities pu = new PrintUtilities(toPrint);
						
						pu.print();
					}
					else
						JOptionPane.showMessageDialog(
								null, 
								Strings.inst().get(Strings.NOTHING_TO_PRINT),
								Strings.inst().get(Strings.PRINT_ERROR),
								JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (BudgetMenu.this.frame != null)
					BudgetMenu.this.frame.setVisible(false);
			}
		});
		
		minimize.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (BudgetMenu.this.frame != null)
					BudgetMenu.this.frame.setExtendedState(JFrame.ICONIFIED);
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
					File localHelp = new File(Const.HELP_FOLDER + File.separator + Const.HELP_FILE);
					final String location;
					if (localHelp.exists())
						location = "file://" + localHelp.getAbsolutePath();
					else
						location = "http://buddi.sourceforge.net/help";
					
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
		
		// Add the action listener for it. Basically, you do what you
		// would normally do. You could also use setAction() for example.
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
		quit.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					Log.debug("Exiting");
					System.exit(0);
				}
			});
		if (!QuitJMenuItem.isAutomaticallyPresent())
			file.add(quit);
	}
}
