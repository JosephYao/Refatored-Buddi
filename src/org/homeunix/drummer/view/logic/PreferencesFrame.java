/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.view.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.controller.PrefsInstance;
import org.homeunix.drummer.prefs.Interval;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractBudgetDialog;
import org.homeunix.drummer.view.layout.PreferencesFrameLayout;

public class PreferencesFrame extends PreferencesFrameLayout {
	public static final long serialVersionUID = 0;

	public PreferencesFrame(){
		super(MainBuddiFrame.getInstance());
	}
		
	@Override
	protected AbstractBudgetDialog initActions() {
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (!PrefsInstance.getInstance().getPrefs().getLanguage().equals(language.getSelectedItem().toString())){
					JOptionPane.showMessageDialog(
							PreferencesFrame.this,
							Translate.getInstance().get(TranslateKeys.RESTART),
							Translate.getInstance().get(TranslateKeys.RESTART_NEEDED),
							JOptionPane.INFORMATION_MESSAGE);
				
					Translate.getInstance().loadLanguage(language.getSelectedItem().toString());
				}
				
				PrefsInstance.getInstance().getPrefs().setLanguage(language.getSelectedItem().toString());
				PrefsInstance.getInstance().getPrefs().setDateFormat(dateFormat.getSelectedItem().toString());
				if (budgetInterval.getSelectedItem() instanceof Interval)
					PrefsInstance.getInstance().getPrefs().setSelectedInterval(((Interval) budgetInterval.getSelectedItem()).getName());
				else
					Log.debug("Unknown type (should be Interval): " + budgetInterval.getSelectedItem());
				PrefsInstance.getInstance().getPrefs().setShowDeletedAccounts(showDeletedAccounts.isSelected());
				PrefsInstance.getInstance().getPrefs().setShowDeletedCategories(showDeletedCategories.isSelected());
				PrefsInstance.getInstance().getPrefs().setShowAccountTypes(showAccountTypes.isSelected());
				PrefsInstance.getInstance().getPrefs().setEnableUpdateNotifications(enableUpdateNotifications.isSelected());
				PrefsInstance.getInstance().savePrefs();
												
				Formatter.getInstance().reloadDateFormat();
				PreferencesFrame.this.setVisible(false);
				MainBuddiFrame.getInstance().getAccountListPanel().updateContent();
				MainBuddiFrame.getInstance().getCategoryListPanel().updateContent();
			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreferencesFrame.this.setVisible(false);
				
				MainBuddiFrame.getInstance().getAccountListPanel().updateContent();
				MainBuddiFrame.getInstance().getCategoryListPanel().updateContent();
			}
		});
		
		return this;
	}

	@Override
	protected AbstractBudgetDialog initContent() {
		updateContent();

		language.setSelectedItem(PrefsInstance.getInstance().getPrefs().getLanguage());
		dateFormat.setSelectedItem(PrefsInstance.getInstance().getPrefs().getDateFormat());
		budgetInterval.setSelectedItem(PrefsInstance.getInstance().getSelectedInterval());
		showDeletedAccounts.setSelected(PrefsInstance.getInstance().getPrefs().isShowDeletedAccounts());
		showDeletedCategories.setSelected(PrefsInstance.getInstance().getPrefs().isShowDeletedCategories());
		showAccountTypes.setSelected(PrefsInstance.getInstance().getPrefs().isShowAccountTypes());
		enableUpdateNotifications.setSelected(PrefsInstance.getInstance().getPrefs().isEnableUpdateNotifications());
		
		return this;
	}

	public AbstractBudgetDialog updateContent(){
		languageModel.removeAllElements();

		File languageLocation = new File(Const.LANGUAGE_FOLDER);
		if (languageLocation.exists() && languageLocation.isDirectory()){
			
			
			for (File f: languageLocation.listFiles())
				if (f.getName().endsWith(Const.LANGUAGE_EXTENSION))
					languageModel.addElement(f.getName().replaceAll(Const.LANGUAGE_EXTENSION, ""));
		}
		else{
			Log.critical("Cannot find language directory");
		}
		
		return this;
	}


}
