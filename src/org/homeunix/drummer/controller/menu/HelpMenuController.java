/*
 * Created on Apr 8, 2007 by wyatt
 */
package org.homeunix.drummer.controller.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.AboutDialog;
import org.homeunix.thecave.moss.util.Log;

import edu.stanford.ejalbert.BrowserLauncher;

public class HelpMenuController implements ActionListener {
	public final static long serialVersionUID = 0;

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(TranslateKeys.MENU_HELP.toString())){
			try{
				File localHelp = new File(
						Const.HELP_FOLDER 
						+ File.separator
						+ "en"	//Until I get more translations of the documentation, I will keep it hardcoded to English 
//						+ PrefsInstance.getInstance().getPrefs().getLanguage().replaceAll("-.*$", "")
						+ File.separator
						+ Const.HELP_FILE);
				BrowserLauncher bl = new BrowserLauncher(null);
				if (localHelp.exists()) {
//					location = "file://" + localHelp.getAbsolutePath().replaceAll(" ", "%20");
					if (Const.DEVEL) Log.debug("Trying to open Help at " + localHelp.getAbsolutePath() + "...");
					bl.openURLinBrowser(localHelp.toURI().toURL().toString());
				}
				else {
					final String location = Const.PROJECT_URL + PrefsInstance.getInstance().getPrefs().getLanguage().replaceAll("-.*$", "") + "/index.php";
					bl.openURLinBrowser(location);
				}


			}
			catch (Exception ex){
				Log.error(ex);
			}
		}
		else if (e.getActionCommand().equals(TranslateKeys.MENU_HELP_ABOUT.toString())){
			new AboutDialog().openWindow();
		}
		else if (e.getActionCommand().equals(TranslateKeys.MENU_HELP_CHECK_FOR_UPDATES.toString())){
			Buddi.startUpdateCheck(true);
		}
		else {
			if (Const.DEVEL) Log.debug("Clicked " + e.getActionCommand());
		}
	}
}
