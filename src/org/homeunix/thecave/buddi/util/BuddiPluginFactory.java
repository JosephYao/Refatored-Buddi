/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.util;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.model.prefs.PluginInfo;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.BuddiReportPlugin;
import org.homeunix.thecave.moss.plugin.MossPlugin;
import org.homeunix.thecave.moss.plugin.factory.PluginFactory;

public class BuddiPluginFactory extends PluginFactory {

	public static List<BuddiReportPlugin> getReportPlugins(){
		List<BuddiReportPlugin> reports = new LinkedList<BuddiReportPlugin>();
		
		//Load built in plugins
		for (String className : Const.BUILT_IN_PLUGINS){
			MossPlugin plugin = BuddiPluginFactory.getValidPluginFromClasspath(className);
			if (plugin instanceof BuddiReportPlugin){
				reports.add((BuddiReportPlugin) plugin);
			}
		}
		
		//Load user defined plugins
		for (PluginInfo info : PrefsModel.getInstance().getPluginInfo()) {
			File jarFile = new File(info.getJarFile());
			
			for (MossPlugin plugin : getValidPluginsFromJar(jarFile, Const.VERSION)) {
				if (plugin instanceof BuddiReportPlugin){
					reports.add((BuddiReportPlugin) plugin);
				}
			}
		}
		
		return reports;
	}
}
