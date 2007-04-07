/*
 * Created on Oct 3, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.exports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.filechooser.FileFilter;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.plugins.interfaces.BuddiExportPlugin;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.ReportFrame;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;
import org.homeunix.thecave.moss.util.Log;

import edu.stanford.ejalbert.BrowserLauncher;

public class ExportHTML implements BuddiExportPlugin {

	public void exportData(AbstractFrame frame, File file) {
		if (Const.DEVEL) Log.debug("Exporting HTML");
		if (frame instanceof ReportFrame){
			String htmlReport = ((ReportFrame) frame).getHTMLPage();

			File tempFile = new File(
//					(!reportPath.matches("^\\S*[ ]\\S*$") ? reportPath : "")
					new File(
							PrefsInstance.getInstance().getPrefs().getDataFile()
					).getParent() 
					+ File.separatorChar
					+ "report_" 
					+ (int) (Math.random() * 1000) 
					+ ".html"
			);
			if (Const.DEVEL) Log.debug("Tempfile: " + tempFile.getAbsolutePath());

			try{
				PrintStream out = new PrintStream(new FileOutputStream(tempFile));
				out.println(htmlReport);
				out.close();

				tempFile.deleteOnExit();

				if (Const.DEVEL) Log.debug("Opening file...");
				BrowserLauncher bl = new BrowserLauncher(null);
				bl.openURLinBrowser(tempFile.toURI().toURL().toString());
				if (Const.DEVEL) Log.debug("Finished opening file...");
			}
			catch (Exception ex){
				Log.error(ex);
			}
		}
		else {
			Log.error("This is not an instance of ReportFrameLayout");
		}
	}
	
	public Class[] getCorrectWindows() {
		try{
			Class[] windows = new Class[1];
			windows[0] = Class.forName("org.homeunix.drummer.view.ReportFrameLayout");
			return windows;
		}
		catch (ClassNotFoundException cnfe){
			Log.error(cnfe);
			return new Class[0];
		}
	}

	public String getDescription() {
		return Translate.getInstance().get(TranslateKeys.EXPORT_TO_HTML);
	}

	public String getFileChooserTitle() {
		return null;
	}

	public FileFilter getFileFilter() {
		return null;
	}

	public boolean isPromptForFile() {
		return false;
	}
}
