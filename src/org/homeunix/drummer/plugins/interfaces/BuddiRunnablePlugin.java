/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create custom reports.
 */
package org.homeunix.drummer.plugins.interfaces;



/**
 * @author wyatt
 * 
 * A generic Buddi plugin, which will be loaded at the beginning of 
 * the session, just after the init() of MainFrame is completed.
 * You can use this plugin type for extending main Buddi
 * functionality, instead of adding well defined features 
 * such as Reports and Export abilities.
 * 
 * To aid in running this (and to potentially allow it to be run in 
 * a thread, if desired) this plugin implements Runnable.
 */
public interface BuddiRunnablePlugin extends BuddiPlugin, Runnable {

}