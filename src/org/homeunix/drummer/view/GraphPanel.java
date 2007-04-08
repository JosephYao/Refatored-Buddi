/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import javax.swing.JPanel;

import org.homeunix.drummer.plugins.PluginFactory;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardContainer;

public class GraphPanel extends AbstractInfomationPanel {
	public static final long serialVersionUID = 0;

	@Override
	public StandardContainer init() {
		super.init();
		
		for (JPanel panel : PluginFactory.getGraphPanelLaunchers()) {
			pluginsPanel.add(panel);
		}
		
		return this;
	}
}
