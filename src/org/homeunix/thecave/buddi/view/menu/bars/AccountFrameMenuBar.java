/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.bars;

import org.homeunix.thecave.buddi.view.AccountFrame;
import org.homeunix.thecave.buddi.view.menu.menus.AccountFrameEditMenu;
import org.homeunix.thecave.buddi.view.menu.menus.FileMenu;
import org.homeunix.thecave.buddi.view.menu.menus.HelpMenu;
import org.homeunix.thecave.buddi.view.menu.menus.WindowMenu;
import org.homeunix.thecave.moss.swing.menu.MossMenuBar;

public class AccountFrameMenuBar extends MossMenuBar {
	public static final long serialVersionUID = 0;
	
	public AccountFrameMenuBar(AccountFrame frame) {
		super(frame);
		
		this.add(new FileMenu(frame));
		this.add(new AccountFrameEditMenu(frame));
		this.add(new WindowMenu(frame));
		this.add(new HelpMenu(frame));
	}
}
