/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.view.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.Strings;
import org.homeunix.drummer.controller.PrefsInstance;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractBudgetDialog;
import org.homeunix.drummer.view.layout.PreferencesDialogLayout;

public class PreferencesFrame extends PreferencesDialogLayout {
	public static final long serialVersionUID = 0;

	public PreferencesFrame(){
		super(MainBudgetFrame.getInstance());
	}
		
	@Override
	protected AbstractBudgetDialog initActions() {
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (!PrefsInstance.getInstance().getPrefs().getLanguage().equals(language.getSelectedItem().toString()) 
						|| !PrefsInstance.getInstance().getPrefs().getDateFormat().equals(dateFormat.getSelectedItem().toString()))
					JOptionPane.showMessageDialog(
							PreferencesFrame.this,
							Strings.inst().get(Strings.RESTART),
							Strings.inst().get(Strings.RESTART_NEEDED),
							JOptionPane.INFORMATION_MESSAGE);
				
				PrefsInstance.getInstance().getPrefs().setLanguage(language.getSelectedItem().toString());
				PrefsInstance.getInstance().getPrefs().setDateFormat(dateFormat.getSelectedItem().toString());
				PrefsInstance.getInstance().getPrefs().setShowDeletedAccounts(showDeletedAccounts.isSelected());
				PrefsInstance.getInstance().getPrefs().setShowDeletedCategories(showDeletedCategories.isSelected());
				PrefsInstance.getInstance().savePrefs();
								
				PreferencesFrame.this.setVisible(false);
				MainBudgetFrame.getInstance().getAccountListPanel().updateContent();
				MainBudgetFrame.getInstance().getCategoryListPanel().updateContent();
			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreferencesFrame.this.setVisible(false);
				
				MainBudgetFrame.getInstance().getAccountListPanel().updateContent();
				MainBudgetFrame.getInstance().getCategoryListPanel().updateContent();
			}
		});
		
		return this;
	}

	@Override
	protected AbstractBudgetDialog initContent() {
		updateContent();

		language.setSelectedItem(PrefsInstance.getInstance().getPrefs().getLanguage());
		dateFormat.setSelectedItem(PrefsInstance.getInstance().getPrefs().getDateFormat());
		showDeletedAccounts.setSelected(PrefsInstance.getInstance().getPrefs().isShowDeletedAccounts());
		showDeletedCategories.setSelected(PrefsInstance.getInstance().getPrefs().isShowDeletedCategories());
		
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
