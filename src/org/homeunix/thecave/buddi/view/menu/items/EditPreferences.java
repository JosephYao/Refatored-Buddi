/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.event.ActionEvent;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.PreferencesFrame;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.menu.MossMenuItem;
import org.homeunix.thecave.moss.swing.window.MossFrame;

public class EditPreferences extends MossMenuItem{
	public static final long serialVersionUID = 0;
	
	public EditPreferences(MossFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_PREFERENCES));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		PreferencesFrame prefs = new PreferencesFrame(getFrame());
		try {
			prefs.openWindow(null, PrefsModel.getInstance().getPreferencesWindowLocation());
		}
		catch (WindowOpenException woe){}
	}
}
