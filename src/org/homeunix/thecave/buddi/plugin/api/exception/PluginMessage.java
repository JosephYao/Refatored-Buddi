/*
 * Created on Aug 3, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.exception;


/**
 * The exception thrown when you need to notify the user of something.  This is generally
 * an error, but can be good as well ("Plugin Completed Successfully"). 
 * 
 * @author wyatt
 *
 */
public class PluginMessage extends Exception {
	public static final long serialVersionUID = 0;
	
	public PluginMessage(String message) {
		super(message);
	}
}
