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
					String[] options = new String[1];
					options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

					JOptionPane.showOptionDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_WRITE_OVER_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE,
							null,
							options,
							options[0]);
				}
				//Check that we have write permission to the folder where this
				// file was selected.
				else if (!isFolderWritable(jfc.getSelectedFile().getParentFile())){
					if (Const.DEVEL) Log.debug("DocumentManager.newDataFile(): !jfc.getSelectedFile().getParentFile().canWrite().  jfc.getSelectedFile() = " + jfc.getSelectedFile() + ", jfc.getSelectedFile().getParent() = " + jfc.getSelectedFile().getParentFile());
					String[] options = new String[1];
					options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

					JOptionPane.showOptionDialog(
							null,
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_WRITE_DATA_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE,
							null,
							options,
							options[0]);
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

		String[] options = new String[1];
		options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

		JOptionPane.showOptionDialog(
				MainFrame.getInstance(),
				Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CREATING_FILE),
				Translate.getInstance().get(TranslateKeys.ERROR),
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.ERROR_MESSAGE,
				null,
				options,
				options[0]);

		return ReturnCodes.ERROR;
	}

	public ReturnCodes loadDataFile(File file){
		DocumentController.saveFile();
		
		File f = showLoadFileDialog(file, Translate.getInstance().get(TranslateKeys.FILECHOOSER_OPEN_DATA_FILE_TITLE), fileFilter);

		if (f == null){
			return ReturnCodes.CANCEL;
		}

		ReturnCodes returnCode = DocumentController.loadFile(f);

		if (!returnCode.equals(ReturnCodes.SUCCESS) 
				&& !returnCode.equals(ReturnCodes.CANCEL)){
			String[] options = new String[1];
			options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

			JOptionPane.showOptionDialog(
					MainFrame.getInstance(),
					Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_OPENING_FILE),
					Translate.getInstance().get(TranslateKeys.ERROR),
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE,
					null,
					options,
					options[0]);
		}

		return returnCode;
	}

	public ReturnCodes loadBackupFile(File backupFile, File restoreTo){
		backupFile = showLoadFileDialog(backupFile, Translate.getInstance().get(TranslateKeys.FILECHOOSER_OPEN_DATA_FILE_TITLE), backupFilter);

		if (backupFile == null){
			return ReturnCodes.CANCEL;
		}

		if (restoreTo == null){
			restoreTo = new File(PrefsInstance.getInstance().getPrefs().getDataFile());
		}

		if (backupFile != null && restoreTo != null) {
			try {
				FileFunctions.copyFile(backupFile, restoreTo);
				DocumentController.loadFile(restoreTo);
				String[] options = new String[1];
				options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

				JOptionPane.showOptionDialog(
						null, 
						Translate.getInstance().get(TranslateKeys.SUCCESSFUL_RESTORE_FILE) 
						+ backupFile.getAbsolutePath().toString(), 
						Translate.getInstance().get(TranslateKeys.RESTORED_FILE),
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						null,
						options,
						options[0]
				);

				return ReturnCodes.SUCCESS;
			}
			catch (IOException ioe){
				String[] options = new String[1];
				options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

				JOptionPane.showOptionDialog(
						null, 
						Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_READING_FILE) 
						+ "\n" 
						+ backupFile.getAbsolutePath().toString(), 
						Translate.getInstance().get(TranslateKeys.ERROR), 
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null,
						options,
						options[0]);					
			}
		}

		String[] options = new String[1];
		options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

		JOptionPane.showOptionDialog(
				MainFrame.getInstance(),
				Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_OPENING_FILE),
				Translate.getInstance().get(TranslateKeys.ERROR),
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.ERROR_MESSAGE,
				null,
				options,
				options[0]);

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
				else if (!isFolderWritable(jfc.getSelectedFile().getParentFile())){
					String[] options = new String[1];
					options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

					JOptionPane.showOptionDialog(
							null,
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_WRITE_DATA_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE,
							null,
							options,
							options[0]);
				}
				else if (!jfc.getSelectedFile().canRead()){
					String[] options = new String[1];
					options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

					JOptionPane.showOptionDialog(
							null,
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_READ_DATA_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE,
							null,
							options,
							options[0]);
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

		return file;
	}
	
	public File showImportFileDialog(File file, String title, FileFilter filter){
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
				else if (!jfc.getSelectedFile().canRead()){
					String[] options = new String[1];
					options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

					JOptionPane.showOptionDialog(
							null,
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_READ_DATA_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE,
							null,
							options,
							options[0]);
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

		return file;
	}

	public ReturnCodes saveDataFile(File file){
		File f = showSaveFileDialog(file, Translate.getInstance().get(TranslateKeys.FILECHOOSER_SAVE_DATA_FILE_TITLE), fileFilter);

		if (f == null){
			return ReturnCodes.CANCEL;
		}

		ReturnCodes returnCode = DocumentController.saveFile(f);

		if (returnCode.equals(ReturnCodes.SUCCESS)){
			String[] options = new String[1];
			options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

			JOptionPane.showOptionDialog(
					null, 
					Translate.getInstance().get(TranslateKeys.SUCCESSFUL_SAVE_FILE) 
					+ f, 
					Translate.getInstance().get(TranslateKeys.FILE_SAVED), 
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					null,
					options,
					options[0]);
		}
		else {
			String[] options = new String[1];
			options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

			JOptionPane.showOptionDialog(
					null, 
					Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_SAVING_FILE) 
					+ " " 
					+ f
					+ "\n\n"
					+ Translate.getInstance().get(TranslateKeys.CHECK_CONSOLE), 
					Translate.getInstance().get(TranslateKeys.ERROR),
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE,
					null,
					options,
					options[0]);
		}

		return returnCode;
	}


	public ReturnCodes saveBackupFile(File file){
		File f = showSaveFileDialog(file, Translate.getInstance().get(TranslateKeys.FILECHOOSER_SAVE_DATA_FILE_TITLE), backupFilter);

		if (f == null){
			return ReturnCodes.CANCEL;
		}


		ReturnCodes returnCode = ReturnCodes.INITIAL;
		if (f != null){
			returnCode = DocumentController.saveFile(f);

			if (returnCode.equals(ReturnCodes.SUCCESS)){
				String[] options = new String[1];
				options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

				JOptionPane.showOptionDialog(
						null, 
						Translate.getInstance().get(TranslateKeys.SUCCESSFUL_BACKUP) 
						+ f, 
						Translate.getInstance().get(TranslateKeys.FILE_SAVED),
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						null,
						options,
						options[0]);
			}
			else {
				String[] options = new String[1];
				options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

				JOptionPane.showOptionDialog(
						null, 
						Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_SAVING_FILE) 
						+ " " 
						+ f
						+ "\n\n"
						+ Translate.getInstance().get(TranslateKeys.CHECK_CONSOLE), 
						Translate.getInstance().get(TranslateKeys.ERROR), 
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null,
						options,
						options[0]);
			}
		}
		else {
			returnCode = ReturnCodes.ERROR;
		}

		if (!returnCode.equals(ReturnCodes.SUCCESS)){
			String[] options = new String[1];
			options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

			JOptionPane.showOptionDialog(
					MainFrame.getInstance(),
					Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_SAVING_FILE),
					Translate.getInstance().get(TranslateKeys.ERROR),
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE,
					null,
					options,
					options[0]);
		}

		return returnCode;
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
					String[] options = new String[1];
					options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

					JOptionPane.showOptionDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_WRITE_OVER_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE,
							null,
							options,
							options[0]);
				}
				//Check that we have write permission to the folder where this
				// file was selected.
				else if (!isFolderWritable(jfc.getSelectedFile().getParentFile())){
					String[] options = new String[1];
					options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

					JOptionPane.showOptionDialog(
							null,
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_WRITE_DATA_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE,
							null,
							options,
							options[0]);
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

		return file;
	}
	
	public File showExportFileDialog(File file, String title, FileFilter filter){
		while (file == null){
			final JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setFileFilter(filter);
			if (PrefsInstance.getInstance().getPrefs().getDataFile() != null)
				jfc.setCurrentDirectory(new File(PrefsInstance.getInstance().getPrefs().getDataFile()));
			jfc.setDialogTitle(title);
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
				//Check that we have write permission to the folder where this
				// file was selected.
				if (!isFolderWritable(jfc.getSelectedFile().getParentFile())){
					String[] options = new String[1];
					options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

					JOptionPane.showOptionDialog(
							null,
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_WRITE_DATA_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE,
							null,
							options,
							options[0]);
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
	 *  1) If there is a file passed in, load it.  This will allow
	 *  you to open files initially at load time.
	 *  2) If the user wants to be prompted, prompt them.  If they hit 
	 *  cancel here, exit.
	 *  3) If there is a file in Preferences, try loading that one.
	 *  4) If there is any problem with the above, prompt the user.
	 *  5) If the user hits cancel from here, return cancel (most
	 *  likely will result in a quit from the calling code).
	 */
	public ReturnCodes selectDesiredDataFile(String fileToLoad){
		//Load the data model.  Depending on different options and 
		// user choices, this may be a new one, or load an existing one.
		File dataFile = null;

		ReturnCodes returnCode = ReturnCodes.INITIAL;
		while (!returnCode.equals(ReturnCodes.SUCCESS)
				&& !returnCode.equals(ReturnCodes.CANCEL)){

			//If we passed in a data file to load, try to load it.
			if (fileToLoad != null){
				returnCode = DocumentController.loadFile(new File(fileToLoad));
				fileToLoad = null;
			}
			//This option allows us to prompt for a new data file at startup every time
			else if (PrefsInstance.getInstance().getPrefs().isPromptForFileAtStartup()
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
					if (Const.DEVEL) Log.debug("DocumentManager.selectDesiredFile(): !dataFile.canWrite()");
					String[] options = new String[1];
					options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

					JOptionPane.showOptionDialog(
							null,
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_WRITE_DATA_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE,
							null,
							options,
							options[0]);
					returnCode = DocumentManager.getInstance().chooseFile();
				}
				else if (!dataFile.canRead()){
					String[] options = new String[1];
					options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

					JOptionPane.showOptionDialog(
							null,
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_READ_DATA_FILE),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE,
							null,
							options,
							options[0]);
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

	/**
	 * Simple test to see if a folder is writable.  File.canWrite()
	 * does not work on all Windows folders, as the folder may
	 * be set RO but permissions allow it to be written.
	 * This is in response to bug #1716654, "Error creating
	 * a new file in My Documents folder".
	 * @param folder
	 * @return
	 */
	private boolean isFolderWritable(File folder){
		if (!folder.isDirectory())
			return false;
		if (folder.canWrite())
			return true;
		File testFile = null;
		final int MAX_CHECKS = 25;
		for (int i = 0; i <= MAX_CHECKS && (testFile == null || testFile.exists()); i++){
			testFile = new File(folder.getAbsolutePath() + File.separator + "." + Math.random());
			if (i == MAX_CHECKS)
				return false; //Could not test file - all tests already existed
		}

		try {
			testFile.createNewFile();
			if (testFile.exists()){
				testFile.delete();
				return true;
			}
		}
		catch (IOException ioe){}

		return false;
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
