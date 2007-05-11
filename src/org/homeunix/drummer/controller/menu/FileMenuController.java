/*
 * Created on Apr 8, 2007 by wyatt
 */
package org.homeunix.drummer.controller.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.view.DocumentManager;
import org.homeunix.drummer.view.HTMLExport;
import org.homeunix.drummer.view.MainFrame;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;
import org.homeunix.thecave.moss.util.Log;

import edu.stanford.ejalbert.BrowserLauncher;

public class FileMenuController implements ActionListener {
	private AbstractFrame frame;

	public FileMenuController(AbstractFrame frame){
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(TranslateKeys.MENU_FILE_NEW.toString())){
			DocumentManager.getInstance().newDataFile(null);
		}
		else if (e.getActionCommand().equals(TranslateKeys.MENU_FILE_OPEN.toString())){
			DocumentManager.getInstance().loadDataFile(null);
		}
		else if (e.getActionCommand().equals(TranslateKeys.MENU_FILE_SAVE_AS.toString())){
			DocumentManager.getInstance().saveDataFile(null);
		}
		else if (e.getActionCommand().equals(TranslateKeys.MENU_FILE_BACKUP.toString())){
			DocumentManager.getInstance().saveBackupFile(null);
		}
		else if (e.getActionCommand().equals(TranslateKeys.MENU_FILE_RESTORE.toString())){ 
			DocumentManager.getInstance().loadBackupFile(null, null);
		}
		else if (e.getActionCommand().equals(TranslateKeys.MENU_FILE_ENCRYPT.toString())){
			String[] options = new String[2];
			options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_YES);
			options[1] = Translate.getInstance().get(TranslateKeys.BUTTON_NO);
			if (JOptionPane.showOptionDialog(
					MainFrame.getInstance(), 
					Translate.getInstance().get(TranslateKeys.MESSAGE_ENCRYPT_DATA_FILE_WARNING),
					Translate.getInstance().get(TranslateKeys.MESSAGE_CHANGE_ENCRYPTION_TITLE),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					null,
					options,
					options[0]
			) == JOptionPane.YES_OPTION){
				DataInstance.getInstance().createNewCipher(true);
				DataInstance.getInstance().saveDataFile();
			}
		}
		else if (e.getActionCommand().equals(TranslateKeys.MENU_FILE_DECRYPT.toString())){
			String[] options = new String[2];
			options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_YES);
			options[1] = Translate.getInstance().get(TranslateKeys.BUTTON_NO);
			if (JOptionPane.showOptionDialog(
					MainFrame.getInstance(), 
					Translate.getInstance().get(TranslateKeys.MESSAGE_DECRYPT_DATA_FILE_WARNING),
					Translate.getInstance().get(TranslateKeys.MESSAGE_CHANGE_ENCRYPTION_TITLE),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					null,
					options,
					options[0]
			) == JOptionPane.YES_OPTION){
				DataInstance.getInstance().createNewCipher(false);
				DataInstance.getInstance().saveDataFile();
			}
		}
		else if (e.getActionCommand().equals(TranslateKeys.MENU_FILE_PRINT.toString())){
			if (frame instanceof HTMLExport){
				try {
					File index = ((HTMLExport) frame).exportToHTML();
					BrowserLauncher bl = new BrowserLauncher(null);
					bl.openURLinBrowser(index.toURI().toURL().toString());
				}
				catch (Exception ex){
					Log.error("Error making HTML: " + ex);
				}
			}
		}
		else if (e.getActionCommand().equals(TranslateKeys.MENU_FILE_CLOSE_WINDOW.toString())){
			if (frame != null)
				frame.closeWindow();
		}
		else {
			Log.debug("Clicked " + e.getActionCommand());
		}
	}
}