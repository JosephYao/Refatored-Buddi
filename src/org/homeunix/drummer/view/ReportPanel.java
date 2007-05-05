/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import net.sourceforge.buddi.api.plugin.BuddiPanelPlugin;

import org.homeunix.drummer.plugins.PluginFactory;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardContainer;

public class ReportPanel extends AbstractInfomationPanel {
	public static final long serialVersionUID = 0;

	@Override
	public StandardContainer init() {
		super.init();
		
		for (BuddiPanelPlugin panel : PluginFactory.getReportPanelLaunchers()) {
			pluginsPanel.add(panel);
			MainFrame.getInstance().getLoadedPlugins().addPlugin(panel);
		}

		return this;
	}
}
