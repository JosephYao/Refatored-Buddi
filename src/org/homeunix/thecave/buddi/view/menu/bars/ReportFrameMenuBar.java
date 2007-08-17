/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.bars;

import org.homeunix.thecave.buddi.view.ReportFrame;
import org.homeunix.thecave.buddi.view.menu.menus.FileMenu;
import org.homeunix.thecave.buddi.view.menu.menus.HelpMenu;
import org.homeunix.thecave.buddi.view.menu.menus.ReportFrameEditMenu;
import org.homeunix.thecave.buddi.view.menu.menus.WindowMenu;
import org.homeunix.thecave.moss.swing.menu.MossMenuBar;

public class ReportFrameMenuBar extends MossMenuBar {
	public static final long serialVersionUID = 0;

	public ReportFrameMenuBar(ReportFrame frame) {
		super(frame);
		
		this.add(new FileMenu(frame));
		this.add(new ReportFrameEditMenu(frame));
		this.add(new WindowMenu(frame));
		this.add(new HelpMenu(frame));
	}
}
