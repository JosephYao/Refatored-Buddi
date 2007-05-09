/*
 * Created on May 8, 2007 by wyatt
 */
package net.sourceforge.buddi.api.plugin;

public interface AutoRefreshPlugin {
	/**
	 * @return How often the plugin should check its active / inactive 
	 * status, in seconds.  Defaults to 2 seconds if not set.  There is
	 * a timer thread which updates the plugin, if it is of the correct
	 * type (Panel or Menu) once each period specified here.  Panel 
	 * plugins will be set visible / invisible; menu plugins will
	 * be set enabled / disabled.
	 */
	public int getRefreshInterval();
}
