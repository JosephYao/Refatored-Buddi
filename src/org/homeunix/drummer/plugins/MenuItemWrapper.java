/*
 * Created on Oct 5, 2006 by wyatt
 */
package org.homeunix.drummer.plugins;

import net.roydesign.ui.JScreenMenuItem;

public class MenuItemWrapper {
	private JScreenMenuItem menuItem;
	private BuddiMenuPlugin buddiMenuPlugin;
	
	public MenuItemWrapper(JScreenMenuItem menuItem, BuddiMenuPlugin buddiMenuPlugin){
		this.menuItem = menuItem;
		this.buddiMenuPlugin = buddiMenuPlugin;
	}
	
	public BuddiMenuPlugin getBuddiMenuPlugin() {
		return buddiMenuPlugin;
	}
	public JScreenMenuItem getMenuItem() {
		return menuItem;
	}
}
