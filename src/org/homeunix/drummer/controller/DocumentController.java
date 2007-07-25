/*
 * Created on Apr 7, 2007 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.List;

import net.java.dev.swingworker.SwingWorker;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.MainFrame;
import org.homeunix.drummer.view.TransactionsFrame;
import org.homeunix.thecave.moss.gui.JStatusDialog;
import org.homeunix.thecave.moss.util.Log;

public class DocumentController {

	public static ReturnCodes loadFile(final File f){
		if (f != null){
			
			final JStatusDialog progress = new JStatusDialog(
					MainFrame.getInstance(), 
					Translate.getInstance().get(TranslateKeys.MESSAGE_READING_FILE));
			progress.openWindow(new Dimension(150, 50), null);
			
			ReturnCodes returnCode = ReturnCodes.INITIAL;
			
			//We need to synchronize this because in some cases, 
			// a saveFile() call would happen between the time 
			// that we loaded the new data file and the time
			// that we set the new file in the Prefs.  This
			// would result in the old data being saved over the
			// new file, which is a Bad Thing.
			//
			//We do not put the code to set the data file in the
			// document loading code, as that would make problems
			// trying to recover from a failed load, as well as
			// make problems for using the loadDataFile() method
			// for restoring backups, etc.
			synchronized (new Object()) {
				
				//Save the existing location, in case the load
				// fails for some reason.
				String oldLocation = PrefsInstance.getInstance().getPrefs().getDataFile();
				PrefsInstance.getInstance().getPrefs().setDataFile(f.getAbsolutePath());
				
				returnCode = 
					DataInstance.getInstance().loadDataFile(f);

				//If there was a problem loading file (bad file,
				// password incorrect, etc), we return that
				// error code.  We also must be sure to reset the
				// Prefs location to the last known good location.
				if (returnCode != ReturnCodes.SUCCESS){
					if (Const.DEVEL) Log.debug("DocumentController.loadFile() failure: return code == " + returnCode);
					PrefsInstance.getInstance().getPrefs().setDataFile(oldLocation);
					progress.closeWindow();
					return returnCode;	
				}

							
			}

			SwingWorker<ReturnCodes, String> worker = new SwingWorker<ReturnCodes, String>(){

				@Override
				protected ReturnCodes doInBackground() throws Exception {
					publish(Translate.getInstance().get(TranslateKeys.MESSAGE_LOADING_DATA));
										
					TransactionsFrame.reloadModel();
					DataInstance.getInstance().saveDataFile();

					return ReturnCodes.SUCCESS;
				}

				@Override
				protected void process(List<String> chunks) {
					if (chunks.size() > 0 && progress != null){
						progress.setMessage(chunks.get(chunks.size() - 1));
					}

					super.process(chunks);
				}

				@Override
				protected void done() {
					try {
						if (get().equals(ReturnCodes.SUCCESS)){
							MainFrame.getInstance().updateContent();
						}
					}
					catch (Exception e){

					}

					if (progress != null)
						progress.closeWindow();
					super.done();
				}
			};

//			PrefsInstance.getInstance().getPrefs().setDataFile(f.getAbsolutePath());
//			TransactionsFrame.reloadModel();
//			DataInstance.getInstance().saveDataFile();
//			MainFrame.getInstance().updateContent();

			worker.execute();
			
			try {
				worker.get();
			}
			catch (Exception e){
				
			}
			
			return returnCode;
		} 

		return ReturnCodes.ERROR;
	}

	public static ReturnCodes newFile(File f){
		ReturnCodes returnCode = ReturnCodes.INITIAL;

		if (f != null){
			PrefsInstance.getInstance().getPrefs().setDataFile(f.getAbsolutePath());
			returnCode = DataInstance.getInstance().newDataFile(f);
			TransactionsFrame.reloadModel();
			MainFrame.getInstance().updateContent();
			DataInstance.getInstance().saveDataFile();

			return returnCode;
		}

		return ReturnCodes.ERROR;
	}

	public static ReturnCodes saveFile(){
		return DataInstance.getInstance().saveDataFile();
	}

	/**
	 * Saves the data file in a separate thread.  This can be used
	 * to schedule a save, but not take up GUI time.
	 * 
	 * Note that since this is executed in a different thread,
	 * you will not get a return code, and the file may not 
	 * have been saved properly.
	 */
	public static void saveFileSoon(){
		Thread thread = new Thread(new Runnable() {
			public void run() {
				saveFile();
			}
		});

		thread.run();
	}


	public static ReturnCodes saveFile(File f){
		ReturnCodes returnCode = ReturnCodes.INITIAL;
		try {
			returnCode = DataInstance.getInstance().saveDataFile(f.getAbsolutePath());
		}
		catch (IOException ioe){
			Log.error("Error saving data file: " + ioe);
			returnCode = ReturnCodes.ERROR;
		}

		return returnCode;
	}

//	private class LoadWorker extends SwingWorker<ReturnCodes, String> {
//	private final File f;
//	private final JProgressDialog progress;

//	public LoadWorker(File f, JProgressDialog progress){
//	this.f = f;
//	this.progress = progress;
//	}

//	@Override
//	protected ReturnCodes doInBackground() throws Exception {
//	publish("Loading File");
//	ReturnCodes ret = DataInstance.getInstance().loadDataFile(f);

//	//If there was a problem loading file (bad file,
//	// password incorrect, etc), we return that
//	// error code.
//	if (ret != ReturnCodes.SUCCESS)
//	return ret;

//	publish("Setting Prefs");
//	PrefsInstance.getInstance().getPrefs().setDataFile(f.getAbsolutePath());

//	publish("Reading Data");
//	TransactionsFrame.reloadModel();
//	DataInstance.getInstance().saveDataFile();

//	return ReturnCodes.SUCCESS;
//	}

//	@Override
//	protected void process(List<String> chunks) {
//	if (chunks.size() > 0 && progress != null){
//	progress.setMessage(chunks.get(chunks.size() - 1));
//	}

//	super.process(chunks);
//	}

//	@Override
//	protected void done() {
//	try {
//	if (get().equals(ReturnCodes.SUCCESS)){
//	publish("Displaying Data");
//	MainFrame.getInstance().updateContent();
//	}
//	}
//	catch (Exception e){

//	}

//	if (progress != null)
//	progress.closeWindow();
//	super.done();
//	}
//	}
}
