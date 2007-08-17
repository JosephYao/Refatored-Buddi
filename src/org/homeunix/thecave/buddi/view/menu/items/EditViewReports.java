/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.AccountFrame;
import org.homeunix.thecave.buddi.view.ReportFrame;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.menu.MossMenuItem;
import org.homeunix.thecave.moss.swing.window.MossDocumentFrame;
import org.homeunix.thecave.moss.util.Log;

public class EditViewReports extends MossMenuItem{
	public static final long serialVersionUID = 0;

	public EditViewReports(AccountFrame frame) {
		super(frame, PrefsModel.getInstance().getTranslator().get(MenuKeys.MENU_EDIT_VIEW_REPORTS),
				KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);

		try {
			ReportFrame reports = new ReportFrame((MossDocumentFrame) getFrame());
			reports.openWindow(PrefsModel.getInstance().getReportWindowSize(), PrefsModel.getInstance().getReportWindowLocation());
		}
		catch (WindowOpenException woe){
			Log.error(woe);
		}
	}
}
