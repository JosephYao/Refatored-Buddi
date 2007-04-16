/*
 * Created on Apr 6, 2007 by wyatt
 */
package org.homeunix.drummer.view;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.DocumentController;
import org.homeunix.drummer.controller.ReturnCodes;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.util.FileFunctions;
import org.homeunix.thecave.moss.util.Log;


public class DocumentManager {

	/**
	 * Get an instance of the DocumentManager.
	 * @return DocumentManager
	 */
	public static DocumentManager getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static DocumentManager instance = new DocumentManager();

		public static void restartProgram(){
			instance = new DocumentManager();
		}
	}

	private DocumentManager(){}

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
			return Translate.getInstance().get(TranslateKeys.FILE_DESCRIPTION_BUDDI);
		}
	};

	public static final FileFilter backupFilter = new FileFilter(){
		@Override
		public boolean accept(File f) {
			if (f.isDirectory() || f.getName().endsWith(Const.BACKUP_FILE_EXTENSION)){
				return true;
			}
			return false;
		}

		@Override
		public String getDescription() {
			return Translate.getInstance().get(TranslateKeys.FILE_DESCRIPTION_BUDDI);
		}
	};

	/**
	 * Prompts the user to open an existing file, or create a new one.
	 */
	public ReturnCodes chooseFile(){
		String[] options = {
				Translate.getInstance().get(TranslateKeys.BUTTON_NEW_DATA_FILE),
				Translate.getInstance().get(TranslateKeys.BUTTON_OPEN_DATA_FILE),
				Translate.getInstance().get(TranslateKeys.BUTTON_CANCEL),
		};
		ReturnCodes returnCode = ReturnCodes.INITIAL;

		while (!returnCode.equals(ReturnCodes.SUCCESS)
				&& !returnCode.equals(ReturnCodes.CANCEL)){
			int ret = JOptionPane.showOptionDialog(
					MainFrame.getInstance(), 
					Translate.getInstance().get(TranslateKeys.MESSAGE_CHOOSE_NEW_OR_EXISTING_DATA_FILE),
					Translate.getInstance().get(TranslateKeys.MESSAGE_CHOOSE_NEW_OR_EXISTING_DATA_FILE_TITLE),
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[0]);

			if (ret == JOptionPane.YES_OPTION){
				returnCode = DocumentManager.getInstance().newDataFile(null);
			}
			else if (ret == JOptionPane.NO_OPTION){
				returnCode = DocumentManager.getInstance().loadDataFile(null);
			}
			else {
				returnCode = ReturnCodes.CANCEL;
			}
		}

		if (Const.DEVEL) Log.debug("Return code from DocumentManage.chooseFile() is " + returnCode);
		return returnCode;
	}

	public ReturnCodes newDataFile(File file){
		while (file == null){
			final JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setFileFilter(fileFilter);
			if (PrefsInstance.getInstance().getPrefs().getDataFile() != null)
				jfc.setCurrentDirectory(new File(PrefsInstance.getInstance().getPrefs().getDataFile()));
			jfc.setDialogTitle(Translate.getInstance().get(TranslateKeys.FILECHOOSER_NEW_DATA_FILE_TITLE));
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
				//We no longer allow overwriting existing files.  If
				// the user selects an existing file, we show an
				// error and prompt for another file.
				if (jfc.getSelectedFile().exists()){
					JOptionPane.showMessageDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_WRITE_OVER_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.ERROR_MESSAGE);
				}
				//Check that we have write permission to the folder where this
				// file was selected.
				else if (!jfc.getSelectedFile().getParentFile().canWrite()){
					JOptionPane.showMessageDialog(
							null,
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_WRITE_DATA_FILE),
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
				return ReturnCodes.CANCEL;
			}
		}

		if (file != null){
			DocumentController.newFile(file);

			return ReturnCodes.SUCCESS;
		}
		else {
			Log.error("Error creating file.");
		}

		return ReturnCodes.ERROR;
//		DataInstance.getInstance().newDataFile(file);
//		PrefsInstance.getInstance().getPrefs().setDataFile(file.getAbsolutePath());
//		MainBuddiFrame.getInstance().updateContent();

//		return file;
	}

	public ReturnCodes loadDataFile(File file){
		File f = showLoadFileDialog(file, Translate.getInstance().get(TranslateKeys.FILECHOOSER_OPEN_DATA_FILE_TITLE), fileFilter);

		ReturnCodes returnCode = ReturnCodes.INITIAL;
		if (f != null){
			returnCode = DocumentController.loadFile(f);
		}
		else {
			returnCode = ReturnCodes.ERROR;
		}

		if (Const.DEVEL) Log.debug("Return code from DocumentManage.loadDataFile() is " + returnCode);
		return returnCode;
	}

	public ReturnCodes loadBackupFile(File backupFile, File restoreTo){
		backupFile = showLoadFileDialog(backupFile, Translate.getInstance().get(TranslateKeys.FILECHOOSER_OPEN_DATA_FILE_TITLE), backupFilter);

		if (restoreTo == null){
			restoreTo = new File(PrefsInstance.getInstance().getPrefs().getDataFile());
		}

		if (backupFile != null && restoreTo != null) {
			try {
				FileFunctions.copyFile(backupFile, restoreTo);
				DocumentController.loadFile(restoreTo);
				JOptionPane.showMessageDialog(
						null, 
						Translate.getInstance().get(TranslateKeys.SUCCESSFUL_RESTORE_FILE) 
						+ backupFile.getAbsolutePath().toString(), 
						Translate.getInstance().get(TranslateKeys.RESTORED_FILE), 
						JOptionPane.INFORMATION_MESSAGE
				);

				return ReturnCodes.SUCCESS;
			}
			catch (IOException ioe){
				JOptionPane.showMessageDialog(
						null, 
						Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_READING_FILE) 
						+ "\n" 
						+ backupFile.getAbsolutePath().toString(), 
						Translate.getInstance().get(TranslateKeys.ERROR), 
						JOptionPane.ERROR_MESSAGE);					
			}
		}

		return ReturnCodes.ERROR;
	}

	public File showLoadFileDialog(File file, String title, FileFilter filter){
		while (file == null){
			final JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setFileFilter(filter);
			if (PrefsInstance.getInstance().getPrefs().getDataFile() != null)
				jfc.setCurrentDirectory(new File(PrefsInstance.getInstance().getPrefs().getDataFile()));
			jfc.setDialogTitle(title);
			if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				if (!jfc.getSelectedFile().exists()){

				}
				//Check that we have write permission to the folder where this
				// file was selected.
				else if (!jfc.getSelectedFile().getParentFile().canWrite()){
					JOptionPane.showMessageDialog(
							null,
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_WRITE_DATA_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.ERROR_MESSAGE);
				}
				else if (!jfc.getSelectedFile().canRead()){
					JOptionPane.showMessageDialog(
							null,
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_READ_DATA_FILE),
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
//		null, 
//		Translate.getInstance().get(TranslateKeys.CONFIRM_LOAD_BACKUP_FILE), 
//		Translate.getInstance().get(TranslateKeys.CLOSE_DATA_FILE),
//		JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
//		DataInstance.getInstance().loadDataFile(file);
//		PrefsInstance.getInstance().getPrefs().setDataFile(file.getAbsolutePath());
//		PrefsInstance.getInstance().savePrefs();
//		MainBuddiFrame.getInstance().updateContent();
//		}

		return file;
	}

	public ReturnCodes saveDataFile(File file){
		File f = showSaveFileDialog(file, Translate.getInstance().get(TranslateKeys.FILECHOOSER_SAVE_DATA_FILE_TITLE), fileFilter);

		ReturnCodes returnCode = ReturnCodes.INITIAL;
		if (f != null){
			returnCode = DocumentController.saveFile(f);
			JOptionPane.showMessageDialog(
					null, 
					Translate.getInstance().get(TranslateKeys.SUCCESSFUL_SAVE_FILE) 
					+ f, 
					Translate.getInstance().get(TranslateKeys.FILE_SAVED), 
					JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			returnCode = ReturnCodes.ERROR;
			JOptionPane.showMessageDialog(
					null, 
					Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_SAVING_FILE) 
					+ " " 
					+ f
					+ "\n\n"
					+ Translate.getInstance().get(TranslateKeys.CHECK_CONSOLE), 
					Translate.getInstance().get(TranslateKeys.ERROR), 
					JOptionPane.ERROR_MESSAGE);
		}
		
		return returnCode;
	}


	public ReturnCodes saveBackupFile(File file){
		File f = showSaveFileDialog(file, Translate.getInstance().get(TranslateKeys.FILECHOOSER_SAVE_DATA_FILE_TITLE), backupFilter);

		ReturnCodes returnCode = ReturnCodes.INITIAL;
		if (f != null){
			returnCode = DocumentController.saveFile(f);
			
			if (returnCode.equals(ReturnCodes.SUCCESS)){
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
						Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_SAVING_FILE) 
						+ " " 
						+ f
						+ "\n\n"
						+ Translate.getInstance().get(TranslateKeys.CHECK_CONSOLE), 
						Translate.getInstance().get(TranslateKeys.ERROR), 
						JOptionPane.ERROR_MESSAGE);
			}
			
			return returnCode;
		}
		
		return ReturnCodes.ERROR;
	}

	public File showSaveFileDialog(File file, String title, FileFilter filter){
		while (file == null){
			final JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setFileFilter(filter);
			if (PrefsInstance.getInstance().getPrefs().getDataFile() != null)
				jfc.setCurrentDirectory(new File(PrefsInstance.getInstance().getPrefs().getDataFile()));
			jfc.setDialogTitle(title);
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
				//We no longer allow overwriting existing files.  If
				// the user selects an existing file, we show an
				// error and prompt for another file.
				if (jfc.getSelectedFile().exists()){
					JOptionPane.showMessageDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_WRITE_OVER_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.ERROR_MESSAGE);
				}
				//Check that we have write permission to the folder where this
				// file was selected.
				else if (!jfc.getSelectedFile().getParentFile().canWrite()){
					JOptionPane.showMessageDialog(
							null,
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_WRITE_DATA_FILE),
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
//		null, 
//		Translate.getInstance().get(TranslateKeys.SUCCESSFUL_BACKUP) + file, 
//		Translate.getInstance().get(TranslateKeys.FILE_SAVED), 
//		JOptionPane.INFORMATION_MESSAGE);

		return file;
	}
	
	/**
	 * Pick a data file to use for initial startup.  This is based on 
	 * a number of different variables: if the data file is set in
	 * PrefsInstance, if it exists, etc.
	 *   
	 * We use the following steps when deciding if we create a new
	 * file or open an existing one:
	 * 
	 *  1) If the user wants to be prompted, prompt them.  If they hit 
	 *  cancel here, exit.
	 *  2) If there is a file in Preferences, try loading that one.
	 *  3) If there is any problem with the above, prompt the user.
	 *  4) If the user hits cancel from here, return cancel (most
	 *  likely will result in a quit from the calling code).
	 */
	public ReturnCodes selectDesiredDataFile(){
		//Load the data model.  Depending on different options and 
		// user choices, this may be a new one, or load an existing one.
		File dataFile = null;
		
		ReturnCodes returnCode = ReturnCodes.INITIAL;
		while (!returnCode.equals(ReturnCodes.SUCCESS)
				&& !returnCode.equals(ReturnCodes.CANCEL)){

			//This option allows us to prompt for a new data file at startup every time
			if (PrefsInstance.getInstance().getPrefs().isPromptForFileAtStartup()
					|| returnCode.equals(ReturnCodes.ERROR)){
				if (Const.DEVEL) Log.debug("User requested prompt for data file at start time, or there was an error.");
				returnCode = DocumentManager.getInstance().chooseFile();
			}
			//If the data file is not null, we try to use it.
			else if (PrefsInstance.getInstance().getPrefs().getDataFile() != null){
				dataFile = new File(PrefsInstance.getInstance().getPrefs().getDataFile());

				//Does the file exist?
				if (!dataFile.exists()){
					if (Const.DEVEL) Log.debug("Data file does not exist.  Prompting to choose.");
					returnCode = DocumentManager.getInstance().chooseFile();
				}
				//Before we open the file, we check that we have read / write 
				// permission to it.  This is in response to bug #1626996. 
				else if (!dataFile.canWrite()){
					JOptionPane.showMessageDialog(
							null,
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_WRITE_DATA_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.ERROR_MESSAGE);
					returnCode = DocumentManager.getInstance().chooseFile();
				}
				else if (!dataFile.canRead()){
					JOptionPane.showMessageDialog(
							null,
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_READ_DATA_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.ERROR_MESSAGE);
					returnCode = DocumentManager.getInstance().chooseFile();
				}
				//If all looks well, we try to load the file.
				else {
					if (Const.DEVEL) Log.debug("All looks to be OK.  Trying to load data file.");
					returnCode = DocumentController.loadFile(dataFile);
				}
			}
			//If the data file was null, we need to have the user pick a different one.
			else {
				returnCode = DocumentManager.getInstance().chooseFile();
			}
			
			if (Const.DEVEL) Log.debug("Return Code for initial document choice is " + returnCode);
		}
		
		return returnCode;
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
