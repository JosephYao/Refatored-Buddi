/*
 * Created on Aug 22, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

public interface MutableAccount extends ImmutableAccount, MutableSource {
		
	public void setStartingBalance(long startingBalance);
	public void setType(MutableType type);
}
