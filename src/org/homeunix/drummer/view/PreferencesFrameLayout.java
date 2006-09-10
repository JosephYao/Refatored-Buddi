/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.prefs.PrefsInstance;

public abstract class PreferencesFrameLayout extends AbstractBudgetDialog {
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

	protected final JCheckBox enableUpdateNotifications;
	
	protected final DefaultComboBoxModel languageModel;
	
	protected PreferencesFrameLayout(Frame owner){
		super(owner);
		
		okButton = new JButton(Translate.getInstance().get(TranslateKeys.OK));
		cancelButton = new JButton(Translate.getInstance().get(TranslateKeys.CANCEL));
		
		Dimension buttonSize = new Dimension(100, okButton.getPreferredSize().height);
		okButton.setPreferredSize(buttonSize);
		cancelButton.setPreferredSize(buttonSize);
				
		JLabel languageLabel = new JLabel(Translate.getInstance().get(TranslateKeys.LANGUAGE));
		languageModel = new DefaultComboBoxModel();
		language = new JComboBox(languageModel);
		
		JLabel dateFormatLabel = new JLabel(Translate.getInstance().get(TranslateKeys.DATE_FORMAT));
		JLabel currencyFormatLabel = new JLabel(Translate.getInstance().get(TranslateKeys.CURRENCY));
		
		//Add the date formats defined in Const.
		currencyFormat = new JComboBox(Const.CURRENCY_FORMATS);
		dateFormat = new JComboBox(Const.DATE_FORMATS);
		dateFormat.setRenderer(new DefaultListCellRenderer(){
			public static final long serialVersionUID = 0;
			public Component getListCellRendererComponent(JList list, Object obj, int index, boolean isSelected, boolean cellHasFocus) {
				if (obj instanceof String){
					String str = (String) obj;
					this.setText(new SimpleDateFormat(str).format(new Date()));
				}
				
				return this;
			}
		});
		
//		Dimension dropdownSize = dateFormat.getPreferredSize();
//		language.setPreferredSize(dropdownSize);
//		currencyFormat.setPreferredSize(dropdownSize);
		
		JLabel budgetIntervalLabel = new JLabel(Translate.getInstance().get(TranslateKeys.BUDGET_INTERVAL));
		budgetInterval = new JComboBox(PrefsInstance.getInstance().getIntervals());
		
		showDeletedAccounts = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_DELETED_ACCOUNTS));
		showDeletedCategories = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_DELETED_CATEGORIES));
		showAccountTypes = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_ACCOUNT_TYPES));
		enableUpdateNotifications = new JCheckBox(Translate.getInstance().get(TranslateKeys.ENABLE_UPDATE_NOTIFICATIONS));
		showAutoComplete = new JCheckBox(Translate.getInstance().get(TranslateKeys.AUTO_COMPLETE_TRANSACTION_INFORMATION));
		showCreditLimit = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_CREDIT_LIMIT));
		showInterestRate = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_INTEREST_RATE));
		showClearReconcile = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHOW_CLEAR_RECONCILE));
		
		showAccountTypes.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_SHOW_ACCOUNT_TYPES));
		showCreditLimit.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_SHOW_CREDIT_LIMIT));
		showInterestRate.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_SHOW_INTEREST_RATE));
		showClearReconcile.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_SHOW_CLEAR_RECONCILE));
		
		JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel dateFormatPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel currencyFormatPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel budgetIntervalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel deletePanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel deletePanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel updatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel autoCompletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel creditLimitPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel interestRatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel clearReconcilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		languagePanel.add(languageLabel);
		languagePanel.add(language);
		
		currencyFormatPanel.add(currencyFormatLabel);
		currencyFormatPanel.add(currencyFormat);
		
		dateFormatPanel.add(dateFormatLabel);
		dateFormatPanel.add(dateFormat);
		
		budgetIntervalPanel.add(budgetIntervalLabel);
		budgetIntervalPanel.add(budgetInterval);
		
		deletePanel1.add(showDeletedAccounts);
		
		deletePanel2.add(showDeletedCategories);
		
		accountPanel.add(showAccountTypes);
		
		autoCompletePanel.add(showAutoComplete);
		
		updatePanel.add(enableUpdateNotifications);
		
		creditLimitPanel.add(showCreditLimit);
		
		interestRatePanel.add(showInterestRate);
		
		clearReconcilePanel.add(showClearReconcile);
		
		JPanel localePanel = new JPanel(new GridLayout(0, 1));
		JPanel viewPanel = new JPanel(new GridLayout(0, 1));
//		JPanel otherPanel = new JPanel(new GridLayout(0, 1));
		
//		localePanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
//		viewPanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
//		otherPanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		
		localePanel.setBorder(BorderFactory.createTitledBorder(""));
		viewPanel.setBorder(BorderFactory.createTitledBorder(""));
//		otherPanel.setBorder(BorderFactory.createTitledBorder(""));
		
//		JPanel textPanel = new JPanel(new GridLayout(0, 1));
		localePanel.add(languagePanel);		
		localePanel.add(currencyFormatPanel);
		localePanel.add(dateFormatPanel);
		localePanel.add(new JLabel());
		localePanel.add(new JLabel());
		localePanel.add(new JLabel());
		localePanel.add(new JLabel());
		localePanel.add(new JLabel());
		localePanel.add(updatePanel);		
				
		viewPanel.add(deletePanel1);
		viewPanel.add(deletePanel2);
		viewPanel.add(accountPanel);
		viewPanel.add(interestRatePanel);
		viewPanel.add(creditLimitPanel);
		viewPanel.add(clearReconcilePanel);
		viewPanel.add(autoCompletePanel);
		viewPanel.add(budgetIntervalPanel);
		viewPanel.add(new JLabel());  // Line up view and locale panels

		
//		JPanel textPanelSpacer = new JPanel();
//		textPanelSpacer.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
//		textPanelSpacer.add(textPanel);
		
		JPanel mainBorderPanel = new JPanel(new GridLayout(1, 0));
//		mainBorderPanel.setLayout(new BorderLayout());
//		mainBorderPanel.setBorder(BorderFactory.createTitledBorder(""));
		mainBorderPanel.add(localePanel);
		mainBorderPanel.add(viewPanel);
//		mainBorderPanel.add(otherPanel);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);
		
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 12, 12, 12));
		
		mainPanel.add(mainBorderPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setTitle(Translate.getInstance().get(TranslateKeys.PREFERENCES));
		this.setLayout(new BorderLayout());
		this.add(mainPanel);
		this.getRootPane().setDefaultButton(okButton);
		
		//Call the method to add actions to the buttons
		initActions();		
	}
	
	public AbstractBudgetDialog clearContent(){
		return this;
	}
		
	public AbstractBudgetDialog updateButtons(){
		
		return this;
	}

}
