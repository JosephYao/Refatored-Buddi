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

/**
 * @author wyatt
 * Wrapper around the Apple specific classes used to interface with Buddi.  This allows
 * Buddi to run on non Apple systems, as we don't try loading this class (and by 
 * extension, the classes provided by Apple), unless we are on a Mac system.
 */
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
