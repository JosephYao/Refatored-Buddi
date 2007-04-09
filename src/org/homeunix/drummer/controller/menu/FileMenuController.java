/*
 * Created on Apr 8, 2007 by wyatt
 */
package org.homeunix.drummer.controller.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.homeunix.drummer.controller.DocumentController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.DocumentManager;
import org.homeunix.drummer.view.HTMLExport;
import org.homeunix.drummer.view.MainFrame;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;
import org.homeunix.thecave.moss.util.FileFunctions;
import org.homeunix.thecave.moss.util.Log;

import edu.stanford.ejalbert.BrowserLauncher;

public class FileMenuController implements ActionListener {
	private AbstractFrame frame;

	public FileMenuController(AbstractFrame frame){
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(TranslateKeys.NEW_FILE_MENU.toString())){
			File f = DocumentManager.getInstance().newFile(null);
			if (f != null){
				DocumentController.newFile(f);
			}
		}
		else if (e.getActionCommand().equals(TranslateKeys.OPEN_FILE_MENU.toString())){
			File f = DocumentManager.getInstance().loadFile(null);
			if (f != null){
				DocumentController.loadFile(f);
			}
		}
		else if (e.getActionCommand().equals(TranslateKeys.SAVE_AS_FILE_MENU.toString())){
			File f = DocumentManager.getInstance().saveFile(null);
			if (f != null){
				if (DocumentController.saveFile(f)){
					JOptionPane.showMessageDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.SUCCESSFUL_SAVE_FILE) 
							+ f, 
							Translate.getInstance().get(TranslateKeys.FILE_SAVED), 
							JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.PROBLEM_SAVING_FILE) 
							+ " " 
							+ f
							+ "\n\n"
							+ Translate.getInstance().get(TranslateKeys.CHECK_CONSOLE), 
							Translate.getInstance().get(TranslateKeys.ERROR), 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if (e.getActionCommand().equals(TranslateKeys.BACKUP_FILE_MENU.toString())){
			File f = DocumentManager.getInstance().saveBackupFile(null);
			if (f != null){
				if (DocumentController.saveFile(f)){
					JOptionPane.showMessageDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.SUCCESSFUL_BACKUP) 
							+ f, 
							Translate.getInstance().get(TranslateKeys.FILE_SAVED), 
							JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.PROBLEM_SAVING_FILE) 
							+ " " 
							+ f
							+ "\n\n"
							+ Translate.getInstance().get(TranslateKeys.CHECK_CONSOLE), 
							Translate.getInstance().get(TranslateKeys.ERROR), 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if (e.getActionCommand().equals(TranslateKeys.RESTORE_FILE_MENU.toString())){
			File oldFile = new File(PrefsInstance.getInstance().getPrefs().getDataFile());
			File newFile = DocumentManager.getInstance().loadBackupFile(null);

			if (newFile != null) {
				try {
					FileFunctions.copyFile(newFile, oldFile);
					DocumentController.loadFile(oldFile);
					JOptionPane.showMessageDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.SUCCESSFUL_RESTORE_FILE) 
							+ newFile.getAbsolutePath().toString(), 
							Translate.getInstance().get(TranslateKeys.RESTORED_FILE), 
							JOptionPane.INFORMATION_MESSAGE
					);
				}
				catch (IOException ioe){
					JOptionPane.showMessageDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.PROBLEM_READING_DATA_FILE_INTRO) 
							+ newFile.getAbsolutePath().toString(), 
							Translate.getInstance().get(TranslateKeys.ERROR), 
							JOptionPane.ERROR_MESSAGE);					
				}
			}
		}

		else if (e.getActionCommand().equals(TranslateKeys.ENCRYPT_DATA_FILE.toString())){
			if (JOptionPane.showConfirmDialog(
					MainFrame.getInstance(), 
					Translate.getInstance().get(TranslateKeys.ENCRYPT_DATA_FILE_WARNING),
					Translate.getInstance().get(TranslateKeys.CHANGE_ENCRYPTION),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE
			) == JOptionPane.YES_OPTION){
				DataInstance.getInstance().createNewCipher(true);
				DataInstance.getInstance().saveDataFile();
			}
		}
		else if (e.getActionCommand().equals(TranslateKeys.DECRYPT_DATA_FILE.toString())){
			if (JOptionPane.showConfirmDialog(
					MainFrame.getInstance(), 
					Translate.getInstance().get(TranslateKeys.DECRYPT_DATA_FILE_WARNING),
					Translate.getInstance().get(TranslateKeys.CHANGE_ENCRYPTION),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE
			) == JOptionPane.YES_OPTION){
				DataInstance.getInstance().createNewCipher(false);
				DataInstance.getInstance().saveDataFile();
			}
		}
		else if (e.getActionCommand().equals(TranslateKeys.PRINT.toString())){
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
		else if (e.getActionCommand().equals(TranslateKeys.CLOSE_WINDOW.toString())){
			if (frame != null)
				frame.closeWindow();
		}
		else {
			Log.debug("Clicked " + e.getActionCommand());
		}
	}
}