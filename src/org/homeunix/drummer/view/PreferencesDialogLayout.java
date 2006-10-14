/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.LanguageEditor;

public abstract class PreferencesDialogLayout extends AbstractDialog {
	public static final long serialVersionUID = 0;
	
	protected final JButton okButton;
	protected final JButton cancelButton;
	
	protected final JComboBox language;
	protected final JComboBox dateFormat;
	protected final JComboBox currencyFormat;
	protected final JComboBox budgetInterval;
	protected final JCheckBox showDeletedAccounts;
	protected final JCheckBox showDeletedCategories;
	protected final JCheckBox showAccountTypes;
	protected final JCheckBox showAutoComplete;
	protected final JCheckBox showCreditLimit;
	protected final JCheckBox showInterestRate;
	protected final JCheckBox showClearReconcile;
	protected final JComboBox numberOfBackups;
	protected final JButton addButton;
	protected final JButton removeButton;
	protected final JList pluginList;
	protected final DefaultListModel pluginListModel;
	
	protected final JCheckBox enableUpdateNotifications;
	
	protected final DefaultComboBoxModel languageModel;
	
	protected PreferencesDialogLayout(Frame owner){
		super(owner);
		
		//Instantiate buttons
		okButton = new JButton(Translate.getInstance().get(TranslateKeys.OK));
		cancelButton = new JButton(Translate.getInstance().get(TranslateKeys.CANCEL));
		
		//Instantiate final preferences widgets
		showDeletedAccounts = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_DELETED_ACCOUNTS));
		showDeletedCategories = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_DELETED_CATEGORIES));
		showAccountTypes = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_ACCOUNT_TYPES));
		enableUpdateNotifications = new JCheckBox(Translate.getInstance().get(TranslateKeys.ENABLE_UPDATE_NOTIFICATIONS));
		showAutoComplete = new JCheckBox(Translate.getInstance().get(TranslateKeys.AUTO_COMPLETE_TRANSACTION_INFORMATION));
		showCreditLimit = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_CREDIT_LIMIT));
		showInterestRate = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_INTEREST_RATE));
		showClearReconcile = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_CLEAR_RECONCILE));
		languageModel = new DefaultComboBoxModel();
		language = new JComboBox(languageModel);
		currencyFormat = new JComboBox(Const.CURRENCY_FORMATS);
		dateFormat = new JComboBox(Const.DATE_FORMATS);
		budgetInterval = new JComboBox(PrefsInstance.getInstance().getIntervals());		
		numberOfBackups = new JComboBox(new Integer[]{5, 10, 15, 20});
		pluginListModel = new DefaultListModel();
		pluginList = new JList(pluginListModel);
		addButton = new JButton(Translate.getInstance().get(TranslateKeys.ADD));
		removeButton = new JButton(Translate.getInstance().get(TranslateKeys.REMOVE));
		
		//Set up buttons
		Dimension buttonSize = new Dimension(Math.max(100, cancelButton.getPreferredSize().width), okButton.getPreferredSize().height);
		okButton.setPreferredSize(buttonSize);
		cancelButton.setPreferredSize(buttonSize);
		
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
		this.add(mainPanel);
		this.getRootPane().setDefaultButton(okButton);
		this.setPreferredSize(new Dimension(450, 350));
		
		if (Buddi.isMac()) {
			mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 12, 12, 12));
		}
		
		//Call the method to add actions to the buttons
		initActions();		
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
				
				if (value instanceof String){
					String str = (String) value;
					this.setText(new SimpleDateFormat(str).format(new Date()));
				}
				
				return this;
			}
		});
		
		languagePanel.add(languageLabel);
		languagePanel.add(language);
		
		currencyFormatPanel.add(currencyFormatLabel);
		currencyFormatPanel.add(currencyFormat);		
		dateFormatPanel.add(dateFormatLabel);
		dateFormatPanel.add(dateFormat);

		localePanel.add(languagePanel);		
		localePanel.add(currencyFormatPanel);
		localePanel.add(dateFormatPanel);
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
		
		JButton editLanguages = new JButton(Translate.getInstance().get(TranslateKeys.EDIT_LANGUAGES));
		editLanguages.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					LanguageEditor le = new LanguageEditor(language.getSelectedItem().toString());
					String lang = le.getNewLanguageName();
					updateContent();
					language.setSelectedItem(lang);
				}
				catch (Exception ex){}
			}
		});
		
		JLabel numberOfBackupsLabel = new JLabel(Translate.getInstance().get(TranslateKeys.NUMBER_OF_BACKUPS));
		JLabel budgetIntervalLabel = new JLabel(Translate.getInstance().get(TranslateKeys.BUDGET_INTERVAL));
		
		budgetIntervalPanel.add(budgetIntervalLabel);
		budgetIntervalPanel.add(budgetInterval);
		numberOfBackupsPanel.add(numberOfBackupsLabel);
		numberOfBackupsPanel.add(numberOfBackups);
		editLanguagesPanel.add(editLanguages);
		updatePanel.add(enableUpdateNotifications);
				
		advancedPanel.add(budgetIntervalPanel);
		advancedPanel.add(numberOfBackupsPanel);
		advancedPanel.add(editLanguagesPanel);
		advancedPanel.add(updatePanel);
		
		
		return getPanelHolder(advancedPanel);
	}
	
	private JPanel getPluginPanel(){
		JPanel pluginPanel = new JPanel(new BorderLayout());

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		Dimension buttonSize = new Dimension(Math.max(100, removeButton.getPreferredSize().width), removeButton.getPreferredSize().height);
		addButton.setPreferredSize(buttonSize);
		removeButton.setPreferredSize(buttonSize);
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
}
