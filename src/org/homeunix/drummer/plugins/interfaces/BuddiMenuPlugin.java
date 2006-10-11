/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create custom reports.
 */
package org.homeunix.drummer.plugins.interfaces;



public interface BuddiMenuPlugin extends BuddiFilePlugin {

	/**
	 * Returns the classes of the windows to appear in.  Should
	 * be an array such as MainBuddiFrame.class, TransactionsFrame.class,
	 * and so forth.
	 * @return
	 */
	public Class[] getCorrectWindows();
}
