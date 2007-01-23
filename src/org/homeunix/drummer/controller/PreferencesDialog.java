/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.plugins.PluginFactory;
import org.homeunix.drummer.prefs.Interval;
import org.homeunix.drummer.prefs.Plugin;
import org.homeunix.drummer.prefs.Prefs;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.AbstractDialog;
import org.homeunix.drummer.view.PreferencesDialogLayout;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;

public class PreferencesDialog extends PreferencesDialogLayout {
	public static final long serialVersionUID = 0;

	public PreferencesDialog(){
		super(MainBuddiFrame.getInstance());
	}

	@Override
	protected AbstractDialog initActions() {
		okButton.addActionListener(new ActionListener(){
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				final Prefs prefs = PrefsInstance.getInstance().getPrefs();
				boolean needRestart = false;
				if (forceRestart
						|| !prefs.getLanguage().equals(language.getSelectedItem().toString())
						|| (prefs.isShowAdvanced() != showClearReconcile.isSelected())
						|| pluginsChanged()){

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
				prefs.setPromptForFileAtStartup(promptForDataFile.isSelected());

				prefs.getLists().getPlugins().clear();
				for (int i = 0; i < pluginList.getModel().getSize(); i++) {
					Object o = pluginList.getModel().getElementAt(i);
					if (o instanceof Plugin){
						Plugin plugin = (Plugin) o;
						if (plugin.getClassName().endsWith(".class"))
							PrefsInstance.getInstance().getPrefs().getLists().getPlugins().add(plugin);
					}
				}

				PrefsInstance.getInstance().savePrefs();

				Formatter.getInstance().setDateFormat(PrefsInstance.getInstance().getPrefs().getDateFormat());				

				if (needRestart){
					int retValue = JOptionPane.showConfirmDialog(
							PreferencesDialog.this,
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

				PreferencesDialog.this.setVisible(false);
				PreferencesDialog.this.dispose();
			}
		});

		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreferencesDialog.this.setVisible(false);

				MainBuddiFrame.getInstance().getAccountListPanel().updateContent();
				MainBuddiFrame.getInstance().getCategoryListPanel().updateContent();
			}
		});

		addButton.addActionListener(new ActionListener(){
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.setFileFilter(new FileFilter(){
					public boolean accept(File arg0) {
						if (arg0.getAbsolutePath().endsWith(".jar") ||
								arg0.isDirectory())
							return true;
						else
							return false;
					}

					@Override
					public String getDescription() {
						return Translate.getInstance().get(TranslateKeys.JAR_FILES);
					}
				});
				jfc.setDialogTitle(Translate.getInstance().get(TranslateKeys.CHOOSE_PLUGIN_JAR));
				if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					Vector<String> classNames = PluginFactory.getAllPluginsFromJar(jfc.getSelectedFile());
					if (classNames.size() == 0){
						JOptionPane.showMessageDialog(PreferencesDialog.this, 
								Translate.getInstance().get(TranslateKeys.NO_PLUGINS_IN_JAR), 
								Translate.getInstance().get(TranslateKeys.NO_PLUGINS_IN_JAR_TITLE), 
								JOptionPane.WARNING_MESSAGE
						);
					}
					for (String className : classNames) {
						Plugin plugin = PrefsInstance.getInstance().getPrefsFactory().createPlugin();
						plugin.setJarFile(jfc.getSelectedFile().getAbsolutePath());
						plugin.setClassName(className);
						if (plugin.getClassName().endsWith(".class"))
							pluginListModel.addElement(plugin);
					}
				}

			}
		});

		removeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (pluginList.getSelectedValues().length > 0) {
					for (Object o : pluginList.getSelectedValues()) {
						pluginListModel.removeElement(o);	
					}
				}
			}
		});
		
		pluginList.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){
					removeButton.setEnabled(pluginList.getSelectedIndex() != -1);
				}
			}
		});
		
		pluginList.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){
					removeButton.setEnabled(pluginList.getSelectedIndex() != -1);
				}
			}
		});
		
		otherCurrencyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String newCurrency = JOptionPane.showInputDialog(
						PreferencesDialog.this, 
						Translate.getInstance().get(TranslateKeys.ENTER_CURRENCY_SYMBOL), 
						Translate.getInstance().get(TranslateKeys.ENTER_CURRENCY_SYMBOL_TITLE), 
						JOptionPane.PLAIN_MESSAGE);
				
				if (newCurrency != null && newCurrency.length() > 0){
					currencyModel.removeAllElements();
					
					//Set up currency lists
					boolean customCurrency = true; //Assume custom until proved otherwise, below
					for (String s : Const.CURRENCY_FORMATS) {
						currencyModel.addElement(s);
						if (s.equals(newCurrency)){
							customCurrency = false;
							Log.debug("Currency " + newCurrency + " already in list...");
						}
					}
					if (customCurrency){
						currencyModel.addElement(newCurrency);
					}
					
					currencyFormat.setSelectedItem(newCurrency);
				}
				else {
					Log.debug("Invalid currency: '" + newCurrency + "'");
				}
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
		promptForDataFile.setSelected(prefs.isPromptForFileAtStartup());
		
		pluginListModel.clear();
		for (Object o : PrefsInstance.getInstance().getPrefs().getLists().getPlugins()) {
			Plugin plugin = (Plugin) o;
			if (plugin.getClassName().endsWith(".class")){
				pluginListModel.addElement(plugin);
				pluginList.setSelectedValue(plugin, false);
			}
		}

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
		
		language.setSelectedItem(PrefsInstance.getInstance().getPrefs().getLanguage());

		return this;
	}

	private boolean pluginsChanged(){
		if (pluginListModel.size() != PrefsInstance.getInstance().getPrefs().getLists().getPlugins().size())
			return true;
		Map<Object, Boolean> currentPlugins = new HashMap<Object, Boolean>();

		for(int i = 0; i < pluginListModel.size(); i++){
			currentPlugins.put(pluginListModel.get(i), true);
		}

		for (Object o : PrefsInstance.getInstance().getPrefs().getLists().getPlugins()) {
			if (currentPlugins.get(o) == null)
				return true;
		}

		return false;
	}
}
