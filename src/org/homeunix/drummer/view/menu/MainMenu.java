/*
 * Created on May 16, 2006 by wyatt
 */
package org.homeunix.drummer.view.menu;

import net.roydesign.ui.JScreenMenuBar;

import org.homeunix.drummer.view.AbstractBuddiFrame;

/**
 * The main menu, from which we call all the others.
 * 
 * @author wyatt
 */
public class MainMenu extends JScreenMenuBar {
	public static final long serialVersionUID = 0;

	public MainMenu(AbstractBuddiFrame frame){
		FileMenu file = new FileMenu(frame);
		EditMenu edit = new EditMenu(frame);
		WindowMenu window = new WindowMenu(frame);
		HelpMenu help = new HelpMenu(frame);
		
		this.add(file);
		this.add(edit);
		this.add(window);
		this.add(help);
	}
}
