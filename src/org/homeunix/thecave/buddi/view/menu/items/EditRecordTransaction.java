/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.moss.swing.menu.MossMenuItem;
import org.homeunix.thecave.moss.swing.window.MossFrame;

public class EditRecordTransaction extends MossMenuItem {
	public static final long serialVersionUID = 0;

	public EditRecordTransaction(MossFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_RECORD_UPDATE));
	}
}
