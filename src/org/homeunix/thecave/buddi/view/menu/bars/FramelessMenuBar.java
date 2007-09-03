/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.bars;

import org.homeunix.thecave.buddi.view.menu.menus.FileMenu;
import org.homeunix.thecave.buddi.view.menu.menus.HelpMenu;
import org.homeunix.thecave.buddi.view.menu.menus.WindowMenu;
import org.homeunix.thecave.moss.swing.MossMenuBar;
import org.homeunix.thecave.moss.util.apple.Application;

public class FramelessMenuBar extends MossMenuBar {
	public static final long serialVersionUID = 0;

	public FramelessMenuBar() {
		super(Application.getApplication().getHiddenFrame());

		this.add(new FileMenu(getFrame()));
		this.add(new WindowMenu(getFrame()));
		this.add(new HelpMenu(getFrame()));
	}
}
