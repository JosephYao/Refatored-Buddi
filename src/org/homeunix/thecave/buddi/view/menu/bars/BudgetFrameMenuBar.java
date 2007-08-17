/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.bars;

import org.homeunix.thecave.buddi.view.BudgetFrame;
import org.homeunix.thecave.buddi.view.menu.menus.BudgetFrameEditMenu;
import org.homeunix.thecave.buddi.view.menu.menus.FileMenu;
import org.homeunix.thecave.buddi.view.menu.menus.HelpMenu;
import org.homeunix.thecave.buddi.view.menu.menus.WindowMenu;
import org.homeunix.thecave.moss.swing.menu.MossMenuBar;

public class BudgetFrameMenuBar extends MossMenuBar {
	public static final long serialVersionUID = 0;

	public BudgetFrameMenuBar(BudgetFrame frame) {
		super(frame);
		
		this.add(new FileMenu(frame));
		this.add(new BudgetFrameEditMenu(frame));
		this.add(new WindowMenu(frame));
		this.add(new HelpMenu(frame));
	}
}
