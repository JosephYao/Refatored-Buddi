/*
 * Created on Apr 1, 2007 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.net.URL;

import net.sourceforge.buddi.api.manager.DataManager;
import net.sourceforge.buddi.api.manager.ImportManager;

import org.homeunix.drummer.view.menu.MainMenu;
import org.homeunix.thecave.moss.gui.abstracts.window.AbstractFrame;

/**
 * @author wyatt
 *
 * A class which sits in between the Moss window framework and 
 * Buddi's implementation of it.  This allows us to do some fancy
 * things (such as include an icon and set a menu bar) by default, 
 * without having to do it for every window.
 */
public abstract class AbstractBuddiFrame extends AbstractFrame implements ActionListener {
	public AbstractBuddiFrame() {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (null == cl) {
			cl = AbstractFrame.class.getClassLoader();
		}

		URL imageResource = cl.getResource("Buddi.gif");
		if (null != imageResource) {
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(imageResource));
		}

		this.setJMenuBar(new MainMenu(this));

	}
	
	public abstract DataManager getDataManager();
	
	public abstract ImportManager getImportManager();
}
