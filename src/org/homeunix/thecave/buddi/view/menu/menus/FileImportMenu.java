/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.menus;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.BuddiPluginFactory;
import org.homeunix.thecave.buddi.plugin.api.BuddiImportPlugin;
import org.homeunix.thecave.buddi.view.menu.items.PluginImportEntry;
import org.homeunix.thecave.moss.swing.MossDocumentFrame;
import org.homeunix.thecave.moss.swing.MossFrame;
import org.homeunix.thecave.moss.swing.MossMenu;

public class FileImportMenu extends MossMenu {
	public static final long serialVersionUID = 0;
	
	public FileImportMenu(MossFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_FILE_IMPORT));
	
		updateMenus();
	}
	
	@Override
	public void updateMenus() {
		super.updateMenus();

		this.setEnabled(getFrame() instanceof MossDocumentFrame);
		
		this.removeAll();
		
		for (BuddiImportPlugin plugin : BuddiPluginFactory.getImportPlugins()) {
			if (getFrame() instanceof MossDocumentFrame)
				this.add(new PluginImportEntry((MossDocumentFrame) getFrame(), plugin));
		}
	}
}
