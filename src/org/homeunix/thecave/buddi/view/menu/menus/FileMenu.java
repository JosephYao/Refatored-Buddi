/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.menus;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.menu.items.FileCloseWindow;
import org.homeunix.thecave.buddi.view.menu.items.FileNew;
import org.homeunix.thecave.buddi.view.menu.items.FileOpen;
import org.homeunix.thecave.buddi.view.menu.items.FileQuit;
import org.homeunix.thecave.buddi.view.menu.items.FileSave;
import org.homeunix.thecave.buddi.view.menu.items.FileSaveAs;
import org.homeunix.thecave.moss.swing.MossDocumentFrame;
import org.homeunix.thecave.moss.swing.MossMenu;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class FileMenu extends MossMenu {
	public static final long serialVersionUID = 0;
	
	public FileMenu(MossDocumentFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_FILE));
		
		this.add(new FileNew(frame));
		this.add(new FileOpen(frame));
		this.add(new FileSave(frame));
		this.add(new FileSaveAs(frame));
		this.addSeparator();
		this.add(new FileImportMenu(frame));
		this.add(new FileExportMenu(frame));
		this.addSeparator();
		this.add(new FileCloseWindow(frame));
		if (!OperatingSystemUtil.isMac()){
			this.addSeparator();
			this.add(new FileQuit(frame));
		}
	}
}
