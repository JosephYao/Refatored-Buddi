/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view.layout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.view.AbstractBudgetDialog;

public abstract class PreferencesFrameLayout extends AbstractBudgetDialog {
	public static final long serialVersionUID = 0;
	
	protected final JButton okButton;
	protected final JButton cancelButton;
	
	protected final JComboBox language;
	protected final JComboBox dateFormat;
	protected final JCheckBox showDeletedAccounts;
	protected final JCheckBox showDeletedCategories;
	
	protected final DefaultComboBoxModel languageModel;
	
	protected PreferencesFrameLayout(Frame owner){
		super(owner);
		
		okButton = new JButton(Translate.inst().get(TranslateKeys.OK));
		cancelButton = new JButton(Translate.inst().get(TranslateKeys.CANCEL));
		
		Dimension buttonSize = new Dimension(100, okButton.getPreferredSize().height);
		okButton.setPreferredSize(buttonSize);
		cancelButton.setPreferredSize(buttonSize);
				
		JLabel languageLabel = new JLabel(Translate.inst().get(TranslateKeys.LANGUAGE));
		languageModel = new DefaultComboBoxModel();
		language = new JComboBox(languageModel);
		
		JLabel dateFormatLabel = new JLabel(Translate.inst().get(TranslateKeys.DATE_FORMAT));
		
		//Add the date formats defined in Const.
		dateFormat = new JComboBox(Const.dateFormats);
		
		showDeletedAccounts = new JCheckBox(Translate.inst().get(TranslateKeys.SHOW_DELETED_ACCOUNTS));
		showDeletedCategories = new JCheckBox(Translate.inst().get(TranslateKeys.SHOW_DELETED_CATEGORIES));
		
		JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel dateFormatPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel deletePanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel deletePanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		languagePanel.add(languageLabel);
		languagePanel.add(language);
		
		dateFormatPanel.add(dateFormatLabel);
		dateFormatPanel.add(dateFormat);
		
		deletePanel1.add(showDeletedAccounts);
		
		deletePanel2.add(showDeletedCategories);
		
		JPanel textPanel = new JPanel(new GridLayout(0, 1));
		textPanel.add(languagePanel);
		textPanel.add(dateFormatPanel);
		textPanel.add(deletePanel1);
		textPanel.add(deletePanel2);
		
		JPanel textPanelSpacer = new JPanel();
		textPanelSpacer.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		textPanelSpacer.add(textPanel);
		
		JPanel mainBorderPanel = new JPanel();
		mainBorderPanel.setLayout(new BorderLayout());
		mainBorderPanel.setBorder(BorderFactory.createTitledBorder(""));
		mainBorderPanel.add(textPanelSpacer);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);
		
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 12, 12, 12));
		
		mainPanel.add(mainBorderPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setTitle(Translate.inst().get(TranslateKeys.PREFERENCES));
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
