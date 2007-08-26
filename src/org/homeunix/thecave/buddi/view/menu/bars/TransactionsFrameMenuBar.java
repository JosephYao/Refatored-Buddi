/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.bars;

import org.homeunix.thecave.buddi.view.TransactionFrame;
import org.homeunix.thecave.buddi.view.menu.menus.FileMenu;
import org.homeunix.thecave.buddi.view.menu.menus.HelpMenu;
import org.homeunix.thecave.buddi.view.menu.menus.TransactionsFrameEditMenu;
import org.homeunix.thecave.buddi.view.menu.menus.WindowMenu;
import org.homeunix.thecave.moss.swing.MossMenuBar;

public class TransactionsFrameMenuBar extends MossMenuBar {
	public static final long serialVersionUID = 0;
	
	public TransactionsFrameMenuBar(TransactionFrame frame) {
		super(frame);
		
		this.add(new FileMenu(frame));
		this.add(new TransactionsFrameEditMenu(frame));
		this.add(new WindowMenu(frame));
		this.add(new HelpMenu(frame));
	}
}
