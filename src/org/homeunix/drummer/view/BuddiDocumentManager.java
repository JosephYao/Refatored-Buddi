/*
 * Created on Apr 6, 2007 by wyatt
 */
package org.homeunix.drummer.view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.gui.abstractwindows.DocumentManager;


public class BuddiDocumentManager implements DocumentManager{
	
	/**
	 * Get an instance of the DocumentManager.
	 * @return DocumentManager
	 */
	public static BuddiDocumentManager getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static BuddiDocumentManager instance = new BuddiDocumentManager();

		public static void restartProgram(){
			instance = new BuddiDocumentManager();
		}
	}
	
	private BuddiDocumentManager(){
		
	}
	
	public static final FileFilter fileFilter = new FileFilter(){
		@Override
		public boolean accept(File f) {
			if (f.isDirectory() || f.getName().endsWith(Const.DATA_FILE_EXTENSION)){
				return true;
			}
			return false;
		}

		@Override
		public String getDescription() {
			return Translate.getInstance().get(TranslateKeys.BUDDI_FILE_DESC);
		}
	};
	
	/**
	 * Prompts the user to open an existing file, or create a new one.
	 */
	public DataFileWrapper chooseNewOrExistingDataFile(){
		String[] options = {
				Translate.getInstance().get(TranslateKeys.NEW_DATA_FILE_OPTION),
				Translate.getInstance().get(TranslateKeys.OPEN_DATA_FILE_OPTION),
				Translate.getInstance().get(TranslateKeys.CANCEL),
		};
		File f = null;
		while (f == null){
			int ret = JOptionPane.showOptionDialog(
					null, 
					Translate.getInstance().get(TranslateKeys.NEW_OR_EXISTING_DATA_FILE),
					Translate.getInstance().get(TranslateKeys.CHOOSE_DATASTORE_LOCATION),
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[0]);

			if (ret == JOptionPane.YES_OPTION){
				f = BuddiDocumentManager.getInstance().newFile(null);
				if (f != null){
					PrefsInstance.getInstance().getPrefs().setDataFile(f.getAbsolutePath());
					PrefsInstance.getInstance().savePrefs();
					return new DataFileWrapper(f, false);
				}
			}
			else if (ret == JOptionPane.NO_OPTION){
				f = BuddiDocumentManager.getInstance().loadFile(null);
				if (f != null){
					PrefsInstance.getInstance().getPrefs().setDataFile(f.getAbsolutePath());
					PrefsInstance.getInstance().savePrefs();
					return new DataFileWrapper(f, true);
				}
			}
			else{
				return null;
			}
		}
		
		return null;
	}
	
	public File newFile(File file){
		while (file == null){
			final JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setFileFilter(fileFilter);
			if (PrefsInstance.getInstance().getPrefs().getDataFile() != null)
				jfc.setCurrentDirectory(new File(PrefsInstance.getInstance().getPrefs().getDataFile()));
			jfc.setDialogTitle(Translate.getInstance().get(TranslateKeys.NEW_DATA_FILE));
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
				//We no longer allow overwriting existing files.  If
				// the user selects an existing file, we show an
				// error and prompt for another file.
				if (jfc.getSelectedFile().exists()){
					JOptionPane.showMessageDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.CANNOT_WRITE_OVER_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.ERROR_MESSAGE);
				}
				//Check that we have write permission to the folder where this
				// file was selected.
				else if (!jfc.getSelectedFile().getParentFile().canWrite()){
					JOptionPane.showMessageDialog(
							null,
							Translate.getInstance().get(TranslateKeys.CANNOT_WRITE_DATA_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.ERROR_MESSAGE);
				}
				//User has selected a new file name.  Make sure the extension
				// is correct, and use this file.
				else {
					final String location;
					if (jfc.getSelectedFile().getName().endsWith(Const.DATA_FILE_EXTENSION))
						location = jfc.getSelectedFile().getAbsolutePath();
					else
						location = jfc.getSelectedFile().getAbsolutePath() + Const.DATA_FILE_EXTENSION;

					file = new File(location);
				}
			}
			//The user hit cancel.  We return to the calling code with false. 
			else {
				return null;
			}
		}
		
//		DataInstance.getInstance().newDataFile(file);
//		PrefsInstance.getInstance().getPrefs().setDataFile(file.getAbsolutePath());
//		MainBuddiFrame.getInstance().updateContent();

		return file;
	}

	public File loadFile(File file){
		while (file == null){
			final JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setFileFilter(fileFilter);
			if (PrefsInstance.getInstance().getPrefs().getDataFile() != null)
				jfc.setCurrentDirectory(new File(PrefsInstance.getInstance().getPrefs().getDataFile()));
			jfc.setDialogTitle(Translate.getInstance().get(TranslateKeys.OPEN_DATA_FILE));
			if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				if (!jfc.getSelectedFile().exists()){
					
				}
				//Check that we have write permission to the folder where this
				// file was selected.
				else if (!jfc.getSelectedFile().getParentFile().canWrite()){
					JOptionPane.showMessageDialog(
							null,
							Translate.getInstance().get(TranslateKeys.CANNOT_WRITE_DATA_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.ERROR_MESSAGE);
				}
				else if (!jfc.getSelectedFile().canRead()){
					JOptionPane.showMessageDialog(
							null,
							Translate.getInstance().get(TranslateKeys.CANNOT_READ_DATA_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					file = jfc.getSelectedFile();
				}
			}
			//The user hit cancel.  We return to the calling code with false. 
			else {
				return null;
			}
		}

		//We now have a file; let's try to load it.
//		if (JOptionPane.showConfirmDialog(
//				null, 
//				Translate.getInstance().get(TranslateKeys.CONFIRM_LOAD_BACKUP_FILE), 
//				Translate.getInstance().get(TranslateKeys.CLOSE_DATA_FILE),
//				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
//		DataInstance.getInstance().loadDataFile(file);
//		PrefsInstance.getInstance().getPrefs().setDataFile(file.getAbsolutePath());
//		PrefsInstance.getInstance().savePrefs();
//		MainBuddiFrame.getInstance().updateContent();
//		}
		
		return file;
	}

	public File saveFile(File file){
		while (file == null){
			final JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setFileFilter(fileFilter);
			if (PrefsInstance.getInstance().getPrefs().getDataFile() != null)
				jfc.setCurrentDirectory(new File(PrefsInstance.getInstance().getPrefs().getDataFile()));
			jfc.setDialogTitle(Translate.getInstance().get(TranslateKeys.SAVE_DATA_FILE));
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
				//We no longer allow overwriting existing files.  If
				// the user selects an existing file, we show an
				// error and prompt for another file.
				if (jfc.getSelectedFile().exists()){
					JOptionPane.showMessageDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.CANNOT_WRITE_OVER_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.ERROR_MESSAGE);
				}
				//Check that we have write permission to the folder where this
				// file was selected.
				else if (!jfc.getSelectedFile().getParentFile().canWrite()){
					JOptionPane.showMessageDialog(
							null,
							Translate.getInstance().get(TranslateKeys.CANNOT_WRITE_DATA_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.ERROR_MESSAGE);
				}
				//User has selected a new file name.  Make sure the extension
				// is correct, and use this file.
				else {
					final String location;
					if (jfc.getSelectedFile().getName().endsWith(Const.DATA_FILE_EXTENSION))
						location = jfc.getSelectedFile().getAbsolutePath();
					else
						location = jfc.getSelectedFile().getAbsolutePath() + Const.DATA_FILE_EXTENSION;
					
					file = new File(location);
				}
			}
			//The user hit cancel.  We return to the calling code with false. 
			else {
				return null;
			}
		}
		
//		DataInstance.getInstance().saveDataModel(file.getAbsolutePath());
//		JOptionPane.showMessageDialog(
//				null, 
//				Translate.getInstance().get(TranslateKeys.SUCCESSFUL_BACKUP) + file, 
//				Translate.getInstance().get(TranslateKeys.FILE_SAVED), 
//				JOptionPane.INFORMATION_MESSAGE);

		return file;
	}
	
	public class DataFileWrapper {
		private File dataFile;
		private boolean existing;

		public DataFileWrapper(File dataFile, boolean existing){
			this.dataFile = dataFile;
			this.existing = existing;
		}
		
		public File getDataFile() {
			return dataFile;
		}
		public boolean isExisting() {
			return existing;
		}
	}
}
