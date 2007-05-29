/*
 * Created on May 29, 2007 by wyatt
 */
package org.homeunix.drummer.util;

import java.io.File;

import org.homeunix.drummer.Buddi;
import org.homeunix.thecave.moss.util.Log;

import com.apple.eawt.Application;
import com.apple.eawt.ApplicationAdapter;
import com.apple.eawt.ApplicationEvent;

public class AppleApplicationWrapper {
	public static void addApplicationListener(){
		Application.getApplication().addApplicationListener(new ApplicationAdapter(){
			@Override
			public void handleOpenFile(ApplicationEvent arg0) {
				File file = new File(arg0.getFilename());
				Log.notice("Opening File: " + file);
				Buddi.setFileToLoad(file.getAbsolutePath());
				super.handleOpenFile(arg0);
			}
		});
	}
}
