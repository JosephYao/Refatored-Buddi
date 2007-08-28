/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.menus;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.BuddiPluginFactory;
import org.homeunix.thecave.buddi.plugin.api.BuddiExportPlugin;
import org.homeunix.thecave.buddi.view.menu.items.PluginExportEntry;
import org.homeunix.thecave.moss.swing.MossDocumentFrame;
import org.homeunix.thecave.moss.swing.MossMenu;

public class FileExportMenu extends MossMenu {
	public static final long serialVersionUID = 0;
	
	public FileExportMenu(MossDocumentFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_FILE_EXPORT));
		
	}
	
	@Override
	public void updateMenus() {
		super.updateMenus();
		
		this.removeAll();
		
		for (BuddiExportPlugin plugin : BuddiPluginFactory.getExportPlugins()) {
			this.add(new PluginExportEntry((MossDocumentFrame) getFrame(), plugin));
		}
	}
}
