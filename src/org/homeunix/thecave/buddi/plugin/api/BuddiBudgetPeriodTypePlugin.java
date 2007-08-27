/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api;

import org.homeunix.thecave.buddi.model.BudgetPeriodType;
import org.homeunix.thecave.moss.plugin.MossPlugin;
import org.homeunix.thecave.moss.util.Version;

public abstract class BuddiBudgetPeriodTypePlugin implements MossPlugin, BudgetPeriodType {

	public String getDescription() {
		return "Budget Period Type definition";
	}

	public Version getMaximumVersion() {
		return null;
	}

	public Version getMinimumVersion() {
		return null;
	}

	public boolean isPluginActive() {
		return true;
	}
	
	@Override
	public int hashCode() {
		return getName().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BuddiBudgetPeriodTypePlugin)
			return getName().equals(((BuddiBudgetPeriodTypePlugin) obj).getName());
		return false;
	}
	
	@Override
	public String toString() {
		return getName().toString();
	}
}
