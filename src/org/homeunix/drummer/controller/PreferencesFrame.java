/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.prefs.CustomPlugins;
import org.homeunix.drummer.prefs.Interval;
import org.homeunix.drummer.prefs.Prefs;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractDialog;
import org.homeunix.drummer.view.PreferencesDialogLayout;

public class PreferencesFrame extends PreferencesDialogLayout {
	public static final long serialVersionUID = 0;

	public PreferencesFrame(){
		super(MainBuddiFrame.getInstance());
	}
		
	@Override
	protected AbstractDialog initActions() {
		okButton.addActionListener(new ActionListener(){
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				final Prefs prefs = PrefsInstance.getInstance().getPrefs();
				boolean needRestart = false;
				if (!prefs.getLanguage().equals(language.getSelectedItem().toString())){
				
					Translate.getInstance().loadLanguage(language.getSelectedItem().toString());
					needRestart = true;
				}

				if (numberOfBackups.getSelectedItem() instanceof Integer)
					prefs.setNumberOfBackups((Integer) numberOfBackups.getSelectedItem());
				else //If there is some problem, at least make sure user has some backups
					PrefsInstance.getInstance().getPrefs().setNumberOfBackups(10);
				prefs.setLanguage(language.getSelectedItem().toString());
				prefs.setDateFormat(dateFormat.getSelectedItem().toString());
				prefs.setCurrencySymbol(currencyFormat.getSelectedItem().toString());
				if (budgetInterval.getSelectedItem() instanceof Interval)
					prefs.setSelectedInterval(((Interval) budgetInterval.getSelectedItem()).getName());
				else
					if (Const.DEVEL) Log.debug("Unknown type (should be Interval): " + budgetInterval.getSelectedItem());
				prefs.setShowDeletedAccounts(showDeletedAccounts.isSelected());
				prefs.setShowDeletedCategories(showDeletedCategories.isSelected());
				prefs.setShowAccountTypes(showAccountTypes.isSelected());
				prefs.setShowAutoComplete(showAutoComplete.isSelected());
				prefs.setShowCreditLimit(showCreditLimit.isSelected());
				prefs.setShowInterestRate(showInterestRate.isSelected());
				prefs.setEnableUpdateNotifications(enableUpdateNotifications.isSelected());
				prefs.setShowAdvanced(showClearReconcile.isSelected());
				
				CustomPlugins cp = prefs.getCustomPlugins();
				if (cp == null){
					cp = PrefsInstance.getInstance().getPrefsFactory().createCustomPlugins();
					prefs.setCustomPlugins(cp);
				}
				
				cp.getExportPlugins().clear();
				cp.getImportPlugins().clear();
				cp.getPanelPlugins().clear();
				cp.getExportPlugins().addAll(exportPlugins.getPluginEntries());
				cp.getImportPlugins().addAll(importPlugins.getPluginEntries());
				cp.getPanelPlugins().addAll(panelPlugins.getPluginEntries());
				
				PrefsInstance.getInstance().savePrefs();
												
				Formatter.getInstance().reloadDateFormat();				
				
				if (needRestart){
					int retValue = JOptionPane.showConfirmDialog(
							PreferencesFrame.this,
							Translate.getInstance().get(TranslateKeys.RESTART_NEEDED),
							Translate.getInstance().get(TranslateKeys.RESTART_NEEDED_TITLE),
							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE
					);
					
					if (retValue == JOptionPane.YES_OPTION){
						if (MainBuddiFrame.getInstance() != null)
							MainBuddiFrame.getInstance().savePosition();

						MainBuddiFrame.restartProgram();
					}
				}
				else {
					MainBuddiFrame.getInstance().getAccountListPanel().updateContent();
					MainBuddiFrame.getInstance().getCategoryListPanel().updateContent();
				}
				TransactionsFrame.updateAllTransactionWindows();
				
				PreferencesFrame.this.setVisible(false);
				PreferencesFrame.this.dispose();
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

	@SuppressWarnings("unchecked")
	@Override
	protected AbstractDialog initContent() {
		updateContent();

		final Prefs prefs = PrefsInstance.getInstance().getPrefs();
		
		language.setSelectedItem(prefs.getLanguage());
		dateFormat.setSelectedItem(prefs.getDateFormat());
		currencyFormat.setSelectedItem(prefs.getCurrencySymbol());

		
		showDeletedAccounts.setSelected(prefs.isShowDeletedAccounts());
		showDeletedCategories.setSelected(prefs.isShowDeletedCategories());
		showAccountTypes.setSelected(prefs.isShowAccountTypes());
		showAutoComplete.setSelected(prefs.isShowAutoComplete());
		showCreditLimit.setSelected(prefs.isShowCreditLimit());
		showInterestRate.setSelected(prefs.isShowInterestRate());
		showClearReconcile.setSelected(prefs.isShowAdvanced());

		budgetInterval.setSelectedItem(PrefsInstance.getInstance().getSelectedInterval());
		numberOfBackups.setSelectedItem(prefs.getNumberOfBackups());
		enableUpdateNotifications.setSelected(prefs.isEnableUpdateNotifications());
		
		if (prefs.getCustomPlugins() == null){
			prefs.setCustomPlugins(PrefsInstance.getInstance().getPrefsFactory().createCustomPlugins());
		}
		
		exportPlugins.setStrings(prefs.getCustomPlugins().getExportPlugins());
		importPlugins.setStrings(prefs.getCustomPlugins().getImportPlugins());
		panelPlugins.setStrings(prefs.getCustomPlugins().getPanelPlugins());
		
		return this;
	}

	public AbstractDialog updateContent(){
		languageModel.removeAllElements();

		Set<String> languages = new HashSet<String>();
		
		// Load all available languages into Prefs.  Start with 
		// the bundled languages, and if there are more, load them too.
		for (String language : Const.BUNDLED_LANGUAGES) {
			languages.add(language);			
		}
		
		File languageLocation = new File(Const.LANGUAGE_FOLDER);
		if (languageLocation.exists() && languageLocation.isDirectory()){
			for (File f: languageLocation.listFiles())
				if (f.getName().endsWith(Const.LANGUAGE_EXTENSION))
					languages.add(f.getName().replaceAll(Const.LANGUAGE_EXTENSION, ""));
		}
		else{
			Log.critical("Cannot find language directory");
		}

		Vector<String> languagesVector = new Vector<String>(languages);
		Collections.sort(languagesVector);
		for (String string : languagesVector) {
			languageModel.addElement(string);
		}
		
		return this;
	}


}
