/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import javax.swing.JPanel;

import org.homeunix.drummer.plugins.PluginFactory;

public class ReportPanel extends AbstractInfomationPanel {
	public static final long serialVersionUID = 0;

	public ReportPanel(){
		super();

		for (JPanel panel : PluginFactory.getReportPanelLaunchers()) {
			pluginsPanel.add(panel);
		}
	}
}
