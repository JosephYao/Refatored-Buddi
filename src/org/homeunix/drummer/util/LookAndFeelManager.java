package org.homeunix.drummer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.view.MainFrame;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public final class LookAndFeelManager {
	public static final long serialVersionUID = 0;
	public static final String QUAQUA_LOOK_AND_FEEL = "ch.randelshofer.quaqua.QuaquaLookAndFeel";
	
	public static LookAndFeelManager getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static LookAndFeelManager instance = new LookAndFeelManager();
	}
	
	private LookAndFeelManager() {
		installThirdPartyLookAndFeels();
	}
	
	private static void installThirdPartyLookAndFeels() {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (null == cl) {
			cl = Buddi.class.getClassLoader();
		}
		installLookAndFeelsFromResources("META-INF/services/javax.swing.LookAndFeel", cl);
		installLookAndFeelsFromResources("meta-inf/services/javax.swing.LookAndFeel", cl);
		if (OperatingSystemUtil.isMac()){
			installLookAndFeelsFromResources(QUAQUA_LOOK_AND_FEEL, cl);
		}
	}

	private static void installLookAndFeelsFromResources(String name, ClassLoader cl) {
		try {
			Enumeration<URL> resources = cl.getResources(name);
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()));
				String className = null;
				while ((className = reader.readLine()) != null) {
					LookAndFeel laf = (LookAndFeel) cl.loadClass(className).newInstance();
					if (laf.isSupportedLookAndFeel()) {
						UIManager.installLookAndFeel(laf.getName(), className);
					}
				}
			}
		} catch (IOException ex) {
			Log.error("Error installing pluggable look and feels", ex);
		} catch (Exception ex) {
			Log.error("Error loading class", ex);
		}	
	}
	
	public void setLookAndFeel(String className) {		
		boolean installed = false;
		
		if (null != className) {
			installed = setLookAndFeel0(className);
		}
		if (!installed && OperatingSystemUtil.isMac()) {
			installed = setLookAndFeel0(QUAQUA_LOOK_AND_FEEL);
		}
		if (!installed) {
			installed = setLookAndFeel0(UIManager.getSystemLookAndFeelClassName());
		}
		if (!installed) {
			installed = setLookAndFeel0(UIManager.getCrossPlatformLookAndFeelClassName());
		}
		if (!installed) {
			Log.warning("Unable to install any LookAndFeel.");
		}		
	}
	
	public void updateLookAndFeel(LookAndFeelInfo info) {
		updateLookAndFeel(info, false);
	}
	
	public void updateLookAndFeel(LookAndFeelInfo info, boolean savePrefs) {
		if (null == info) {
			setLookAndFeel(null);
		} else {
			setLookAndFeel(info.getClassName());
		}
				
		// refresh the frame
		SwingUtilities.updateComponentTreeUI(MainFrame.getInstance());
	}
	
	private boolean setLookAndFeel0(String className) {
		if (Const.DEVEL) Log.debug("Setting LNF: " + className);
		if (QUAQUA_LOOK_AND_FEEL.equals(className)) {
			// TODO: generalize this so user can set properties for any LAF
			// set system properties here that affect Quaqua
			// for example the default layout policy for tabbed
			// panes:
			System.setProperty("Quaqua.tabLayoutPolicy", "scroll");
			System.setProperty("Quaqua.selectionStyle", "bright");
		}
		
		try {
			UIManager.setLookAndFeel(className);
			return true;
		} catch (Exception ex) {
			Log.error("Error setting look and feel; trying system default", ex);
			return false;
		}	
	}	
}