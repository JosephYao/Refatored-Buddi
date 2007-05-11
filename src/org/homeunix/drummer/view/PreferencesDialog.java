/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.plugins.PluginFactory;
import org.homeunix.drummer.prefs.Interval;
import org.homeunix.drummer.prefs.Plugin;
import org.homeunix.drummer.prefs.Prefs;
import org.homeunix.drummer.prefs.PrefsFactory;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.LanguageEditor;
import org.homeunix.thecave.moss.gui.JScrollingComboBox;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractDialog;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardWindow;
import org.homeunix.thecave.moss.util.Log;

public class PreferencesDialog extends AbstractBuddiDialog {
	public static final long serialVersionUID = 0;
	
	private final JButton okButton;
	private final JButton cancelButton;
	
	private final JScrollingComboBox language;
	private final JScrollingComboBox dateFormat;
	private final JScrollingComboBox currencyFormat;
	private final JCheckBox currencySymbolAfterAmount;
	private final JComboBox budgetInterval;
	private final JCheckBox showDeletedAccounts;
	private final JCheckBox showDeletedCategories;
	private final JCheckBox showAccountTypes;
	private final JCheckBox showAutoComplete;
	private final JCheckBox showCreditLimit;
	private final JCheckBox showInterestRate;
	private final JCheckBox showClearReconcile;
	private final JCheckBox promptForDataFile;
	private final JComboBox numberOfBackups;
	private final JButton addButton;
	private final JButton removeButton;
	private final JButton otherCurrencyButton;
	private final JButton otherDateFormatButton;
	private final JList pluginList;
	private final DefaultListModel pluginListModel;
	
	private final JCheckBox enableUpdateNotifications;
	
	private final DefaultComboBoxModel languageModel;
	private final DefaultComboBoxModel currencyModel;
	private final DefaultComboBoxModel dateFormatModel;
	
	private boolean forceRestart = false;
	
	public PreferencesDialog(){
		super(MainFrame.getInstance());
		
		//Instantiate buttons
		okButton = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_OK));
		cancelButton = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_CANCEL));
		
		//Instantiate final preferences widgets
		showDeletedAccounts = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_DELETED_ACCOUNTS));
		showDeletedCategories = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_DELETED_CATEGORIES));
		showAccountTypes = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_ACCOUNT_TYPES));
		enableUpdateNotifications = new JCheckBox(Translate.getInstance().get(TranslateKeys.ENABLE_UPDATE_NOTIFICATIONS));
		showAutoComplete = new JCheckBox(Translate.getInstance().get(TranslateKeys.AUTO_COMPLETE_TRANSACTION_INFORMATION));
		showCreditLimit = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_CREDIT_LIMIT));
		showInterestRate = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_INTEREST_RATE));
		showClearReconcile = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_CLEAR_RECONCILE));
		promptForDataFile = new JCheckBox(Translate.getInstance().get(TranslateKeys.PROMPT_FOR_DATA_FILE_AT_STARTUP));
		languageModel = new DefaultComboBoxModel();
		language = new JScrollingComboBox(languageModel);
		currencyModel = new DefaultComboBoxModel();
		currencyFormat = new JScrollingComboBox(currencyModel);
		currencySymbolAfterAmount = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_CURRENCY_SYMBOL_AFTER_AMOUNT));
		dateFormatModel = new DefaultComboBoxModel();
		dateFormat = new JScrollingComboBox(dateFormatModel);
		budgetInterval = new JComboBox(PrefsInstance.getInstance().getIntervals());		
		numberOfBackups = new JComboBox(new Integer[]{5, 10, 15, 20, 50});
		pluginListModel = new DefaultListModel();
		pluginList = new JList(pluginListModel);
		addButton = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_ADD));
		removeButton = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_REMOVE));
		otherCurrencyButton = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_OTHER));
		otherDateFormatButton = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_OTHER));
		
		//Set up buttons
		Dimension buttonSize = new Dimension(Math.max(100, cancelButton.getPreferredSize().width), okButton.getPreferredSize().height);
		okButton.setPreferredSize(buttonSize);
		cancelButton.setPreferredSize(buttonSize);
		otherCurrencyButton.setPreferredSize(buttonSize);
		otherDateFormatButton.setPreferredSize(buttonSize);
		
		//Set up currency lists
		boolean customCurrency = true; //Assume custom until proved otherwise, below
		String currency = PrefsInstance.getInstance().getPrefs().getCurrencySymbol();
		for (String s : Const.CURRENCY_FORMATS) {
			currencyModel.addElement(s);
			if (s.equals(currency))
				customCurrency = false;
		}
		if (customCurrency){
			currencyModel.addElement(currency);
		}
		
		//Set up Date Format list
		boolean customDateFormat = true; //Assume custom until proved otherwise, below
		String dateFormat = PrefsInstance.getInstance().getPrefs().getDateFormat();
		for (String s : Const.DATE_FORMATS) {
			dateFormatModel.addElement(s);
			if (s.equals(dateFormat))
				customDateFormat = false;
		}
		if (customDateFormat){
			dateFormatModel.addElement(dateFormat);
		}
		
		//Set up tooltips
		showAccountTypes.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_SHOW_ACCOUNT_TYPES));
		showCreditLimit.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_SHOW_CREDIT_LIMIT));
		showInterestRate.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_SHOW_INTEREST_RATE));
		showClearReconcile.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_SHOW_CLEAR_RECONCILE));
		
		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab(Translate.getInstance().get(TranslateKeys.VIEW), getViewPanel());
		tabs.addTab(Translate.getInstance().get(TranslateKeys.LOCALE), getLocalePanel());
		tabs.addTab(Translate.getInstance().get(TranslateKeys.PLUGINS), getPluginPanel());
		tabs.addTab(Translate.getInstance().get(TranslateKeys.ADVANCED), getAdvancedPanel());
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);
		
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(tabs, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setTitle(Translate.getInstance().get(TranslateKeys.PREFERENCES));
		this.setLayout(new BorderLayout());
		this.setResizable(true);
		this.add(mainPanel);
		this.getRootPane().setDefaultButton(okButton);
		this.setPreferredSize(new Dimension(450, 350));
//		if (OperatingSystemUtil.isMac()) {
//			mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 12, 12, 12));
//		}		
	}
	
	public AbstractDialog clearContent(){
		return this;
	}
		
	public AbstractDialog updateButtons(){
		return this;
	}

	private JPanel getLocalePanel(){
		JPanel localePanel = new JPanel();
		localePanel.setLayout(new BoxLayout(localePanel, BoxLayout.Y_AXIS));

		JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel dateFormatPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel currencyFormatPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JLabel dateFormatLabel = new JLabel(Translate.getInstance().get(TranslateKeys.DATE_FORMAT));
		JLabel currencyFormatLabel = new JLabel(Translate.getInstance().get(TranslateKeys.CURRENCY));
		JLabel languageLabel = new JLabel(Translate.getInstance().get(TranslateKeys.LANGUAGE));

		//Set up the language pulldown
		language.setRenderer(new DefaultListCellRenderer(){
			public static final long serialVersionUID = 0;
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof String){
					String str = (String) value;
					this.setText(str.replaceAll("_", " "));
				}
				return this;
			}
		});

		//Set up the date format pulldown
		dateFormat.setRenderer(new DefaultListCellRenderer(){
			public static final long serialVersionUID = 0;
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				
				if (value != null){
					this.setText(new SimpleDateFormat(value.toString()).format(new Date()));
				}
				
				return this;
			}
		});
		
//		dateFormat.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent e) {
//				if (dateFormat.getSelectedItem() == null){
//					if (e.getItem().equals(dateFormat.getItemAt(0))){
//						dateFormat.setSelectedIndex(1);
//					}
//					Log.debug("null; e.getItem == " + e.getItem());
//					dateFormat.setSelectedIndex(0);
//				}
//			}			
//		});
		
		languagePanel.add(languageLabel);
		languagePanel.add(language);
		
		dateFormatPanel.add(dateFormatLabel);
		dateFormatPanel.add(dateFormat);
		dateFormatPanel.add(otherDateFormatButton);
		currencyFormatPanel.add(currencyFormatLabel);
		currencyFormatPanel.add(currencyFormat);
		currencyFormatPanel.add(otherCurrencyButton);

		localePanel.add(languagePanel);		
		localePanel.add(dateFormatPanel);
		localePanel.add(currencyFormatPanel);
		localePanel.add(currencySymbolAfterAmount);
		localePanel.add(Box.createVerticalGlue());
		
		return getPanelHolder(localePanel);
	}
	
	private JPanel getViewPanel(){
		JPanel viewPanel = new JPanel();
		viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
		
		JPanel deletePanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel deletePanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		JPanel autoCompletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel creditLimitPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel interestRatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel clearReconcilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		deletePanel1.add(showDeletedAccounts);
		deletePanel2.add(showDeletedCategories);
		accountPanel.add(showAccountTypes);
		autoCompletePanel.add(showAutoComplete);
		creditLimitPanel.add(showCreditLimit);
		interestRatePanel.add(showInterestRate);
		clearReconcilePanel.add(showClearReconcile);
		
		viewPanel.add(deletePanel1);
		viewPanel.add(deletePanel2);
		viewPanel.add(accountPanel);
		viewPanel.add(interestRatePanel);
		viewPanel.add(creditLimitPanel);
		viewPanel.add(clearReconcilePanel);
		viewPanel.add(autoCompletePanel);
		viewPanel.add(Box.createVerticalGlue());
				
		return getPanelHolder(viewPanel);

	}
	
	private JPanel getAdvancedPanel(){
		JPanel advancedPanel = new JPanel();
		advancedPanel.setLayout(new BoxLayout(advancedPanel, BoxLayout.Y_AXIS));
		
		JPanel budgetIntervalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel numberOfBackupsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel updatePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel editLanguagesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel editTypesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel promptForDataFilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton editLanguages = new JButton(Translate.getInstance().get(TranslateKeys.EDIT_LANGUAGES));
		editLanguages.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					LanguageEditor le = new LanguageEditor(language.getSelectedItem().toString());
					String lang = le.getNewLanguageName();
					updateContent();
					language.setSelectedItem(lang);
					forceRestart = true; //We need to restart for language to take effect.
				}
				catch (Exception ex){}
			}
		});
		
		JButton editTypes = new JButton(Translate.getInstance().get(TranslateKeys.EDIT_ACCOUNT_TYPES));
		editTypes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new TypeListDialog().openWindow();
			}
		});
		
		Dimension d = editTypes.getPreferredSize();
		editLanguages.setPreferredSize(d);
		
		JLabel numberOfBackupsLabel = new JLabel(Translate.getInstance().get(TranslateKeys.NUMBER_OF_BACKUPS));
		JLabel budgetIntervalLabel = new JLabel(Translate.getInstance().get(TranslateKeys.BUDGET_INTERVAL));
		
		budgetIntervalPanel.add(budgetIntervalLabel);
		budgetIntervalPanel.add(budgetInterval);
		numberOfBackupsPanel.add(numberOfBackupsLabel);
		numberOfBackupsPanel.add(numberOfBackups);
		editLanguagesPanel.add(editLanguages);
		editTypesPanel.add(editTypes);
		updatePanel.add(enableUpdateNotifications);
		promptForDataFilePanel.add(promptForDataFile);
				
		advancedPanel.add(budgetIntervalPanel);
		advancedPanel.add(numberOfBackupsPanel);
		advancedPanel.add(editLanguagesPanel);
		advancedPanel.add(editTypesPanel);
		advancedPanel.add(updatePanel);
		advancedPanel.add(promptForDataFilePanel);
		
		return getPanelHolder(advancedPanel);
	}
	
	private JPanel getPluginPanel(){
		JPanel pluginPanel = new JPanel(new BorderLayout());

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		Dimension buttonSize = new Dimension(Math.max(100, removeButton.getPreferredSize().width), removeButton.getPreferredSize().height);
		addButton.setPreferredSize(buttonSize);
		removeButton.setPreferredSize(buttonSize);
		removeButton.setEnabled(false);
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		
		JScrollPane pluginListScroller = new JScrollPane(pluginList);
		
		pluginPanel.add(pluginListScroller, BorderLayout.CENTER);
		pluginPanel.add(buttonPanel, BorderLayout.SOUTH);
		pluginPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		return pluginPanel;
	}
	
	private JPanel getPanelHolder(JPanel panel){
		JPanel panelHolder = new JPanel(new BorderLayout());
		panelHolder.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		panelHolder.add(panel, BorderLayout.NORTH);
		panelHolder.add(new JPanel(), BorderLayout.CENTER);

		return panelHolder;
	}
	
	public AbstractDialog init() {
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		addButton.addActionListener(this);
		removeButton.addActionListener(this);
		otherCurrencyButton.addActionListener(this);
		otherDateFormatButton.addActionListener(this);

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

		final Prefs prefs = PrefsInstance.getInstance().getPrefs();

		language.setSelectedItem(prefs.getLanguage());
		dateFormat.setSelectedItem(prefs.getDateFormat());
		currencyFormat.setSelectedItem(prefs.getCurrencySymbol());
		currencySymbolAfterAmount.setSelected(prefs.isCurrencySymbolAfterAmount());

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

		File languageLocation = new File(Buddi.getWorkingDir() + File.separator + Const.LANGUAGE_FOLDER);
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

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(okButton)){
			final Prefs prefs = PrefsInstance.getInstance().getPrefs();
			boolean needRestart = false;
			if (forceRestart
					|| !prefs.getLanguage().equals(language.getSelectedItem().toString())
					|| (prefs.isShowAdvanced() != showClearReconcile.isSelected())
					|| (prefs.isCurrencySymbolAfterAmount() != currencySymbolAfterAmount.isSelected())
					|| (prefs.isEnableUpdateNotifications() != enableUpdateNotifications.isSelected())
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
			prefs.setCurrencySymbolAfterAmount(currencySymbolAfterAmount.isSelected());
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

			if (needRestart){
				String[] options = new String[2];
				options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_YES);
				options[1] = Translate.getInstance().get(TranslateKeys.BUTTON_NO);

				int retValue = JOptionPane.showOptionDialog(
						PreferencesDialog.this,
						Translate.getInstance().get(TranslateKeys.RESTART_NEEDED),
						Translate.getInstance().get(TranslateKeys.RESTART_NEEDED_TITLE),
						JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						null,
						options,
						options[0]
				);

				if (retValue == JOptionPane.YES_OPTION){
					if (MainFrame.getInstance() != null)
						MainFrame.getInstance().savePosition();

					MainFrame.restartProgram();
				}
			}
//			else {
//				MainFrame.getInstance().getAccountListPanel().updateContent();
//				MainFrame.getInstance().getCategoryListPanel().updateContent();
//			}
			TransactionsFrame.updateAllTransactionWindows();

			PreferencesDialog.this.setVisible(false);
			PreferencesDialog.this.dispose();
		}
		else if (e.getSource().equals(cancelButton)){
			PreferencesDialog.this.setVisible(false);

//			MainFrame.getInstance().getAccountListPanel().updateContent();
//			MainFrame.getInstance().getCategoryListPanel().updateContent();
		}
		else if (e.getSource().equals(addButton)){
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
					String[] options = new String[1];
					options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

					JOptionPane.showOptionDialog(
							PreferencesDialog.this, 
							Translate.getInstance().get(TranslateKeys.NO_PLUGINS_IN_JAR), 
							Translate.getInstance().get(TranslateKeys.NO_PLUGINS_IN_JAR_TITLE), 
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE,
							null,
							options,
							options[0]
					);
				}
				for (String className : classNames) {
					Plugin plugin = PrefsFactory.eINSTANCE.createPlugin();
					plugin.setJarFile(jfc.getSelectedFile().getAbsolutePath());
					plugin.setClassName(className);
					if (plugin.getClassName().endsWith(".class"))
						pluginListModel.addElement(plugin);
				}
			}

		}
		else if (e.getSource().equals(removeButton)){
			if (pluginList.getSelectedValues().length > 0) {
				for (Object o : pluginList.getSelectedValues()) {
					pluginListModel.removeElement(o);	
				}
			}
		}
		else if (e.getSource().equals(otherCurrencyButton)){
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
		else if (e.getSource().equals(otherDateFormatButton)){
			String newDateFormat = JOptionPane.showInputDialog(
						this, 
						Translate.getInstance().get(TranslateKeys.MESSAGE_ENTER_DATE_FORMAT), 
						Translate.getInstance().get(TranslateKeys.MESSAGE_ENTER_DATE_FORMAT_TITLE), 
						JOptionPane.PLAIN_MESSAGE);

			if (newDateFormat != null 
					&& newDateFormat.length() > 0){
				
				//Test out the new format, to see if it complies with
				// the format rules.
				try {
					new SimpleDateFormat(newDateFormat);
				}
				catch (IllegalArgumentException iae){
					String[] options = new String[1];
					options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

					JOptionPane.showOptionDialog(
							this, 
							Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_INCORRECT_FORMAT), 
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE,
							null,
							options,
							options[0]);
					return;
				}

				
				dateFormatModel.removeAllElements();

				//Set up currency lists
				boolean customDateFormat = true; //Assume custom until proved otherwise, below
				for (String s : Const.DATE_FORMATS) {
					dateFormatModel.addElement(s);
					if (s.equals(newDateFormat)){
						customDateFormat = false;
						Log.debug("Date Format " + newDateFormat + " already in list...");
					}
				}
				if (customDateFormat){
					dateFormatModel.addElement(newDateFormat);
				}

				dateFormatModel.setSelectedItem(newDateFormat);
			}
			else {
				Log.debug("Invalid Date Format: '" + newDateFormat + "'");
			}
		}
	}

	public StandardWindow clear() {
		return null;
	}

}
