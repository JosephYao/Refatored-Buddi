/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import javax.swing.JPanel;

import org.homeunix.drummer.plugins.PluginFactory;
import org.homeunix.drummer.view.InfomationPanelLayout;

public class GraphPanel extends InfomationPanelLayout {
	public static final long serialVersionUID = 0;

	public GraphPanel(){
		super();

		for (JPanel panel : PluginFactory.getGraphPanelLaunchers()) {
			pluginsPanel.add(panel);
		}
	}
}
