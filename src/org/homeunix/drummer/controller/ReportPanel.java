/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import javax.swing.JPanel;

import org.homeunix.drummer.plugins.PluginFactory;
import org.homeunix.drummer.view.ReportPanelLayout;

public class ReportPanel extends ReportPanelLayout {
	public static final long serialVersionUID = 0;

	public ReportPanel(){
		super();

		for (JPanel panel : PluginFactory.getPanelLaunchers()) {
			pluginsPanel.add(panel);
		}
	}
}
