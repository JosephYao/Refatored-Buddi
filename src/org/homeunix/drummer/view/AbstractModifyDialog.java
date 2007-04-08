/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

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
import javax.swing.JTextField;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Type;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractDialog;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardWindow;
import org.homeunix.thecave.moss.gui.formatted.JCurrencyField;
import org.homeunix.thecave.moss.util.Formatter;

public abstract class AbstractModifyDialog<SourceType> extends AbstractBuddiDialog {
	public static final long serialVersionUID = 0;
	
	protected final JButton okButton;
	protected final JButton cancelButton;
	
	protected final JTextField name;
	protected final JCurrencyField amount;
	protected final JComboBox pulldown;
	protected final JCheckBox check;
	protected final JCurrencyField creditLimit;
	protected final JCurrencyField interestRate;
	
	protected final JLabel nameLabel;
	protected final JLabel amountLabel;
	protected final JLabel pulldownLabel;
	protected final JLabel creditLimitLabel;
	protected final JLabel interestRateLabel;
	protected final JLabel gap = new JLabel();
	
	
	protected final DefaultComboBoxModel pulldownModel;
	
	protected SourceType source;
	protected Type type;
	
	protected AbstractModifyDialog(Frame owner){
		super(owner);
		
		okButton = new JButton(Translate.getInstance().get(TranslateKeys.OK));
		cancelButton = new JButton(Translate.getInstance().get(TranslateKeys.CANCEL));
				
		nameLabel = new JLabel(Translate.getInstance().get(TranslateKeys.NAME));
		name = new JTextField();
		amountLabel = new JLabel();
		amount = new JCurrencyField(0, 5, Formatter.getInstance().getDecimalFormat());
		creditLimit = new JCurrencyField(0, 5, Formatter.getInstance().getDecimalFormat());
		interestRate = new JCurrencyField(0, 5, Formatter.getInstance().getDecimalFormat());
		
		pulldownLabel = new JLabel();
		pulldownModel = new DefaultComboBoxModel();
		pulldown = new JComboBox(pulldownModel);
		
		creditLimitLabel = new JLabel(Translate.getInstance().get(TranslateKeys.OVERDRAFT_LIMIT) + " " + Translate.getInstance().get(TranslateKeys.OPTIONAL_TAG));
		interestRateLabel = new JLabel(Translate.getInstance().get(TranslateKeys.INTEREST_RATE) + " " + Translate.getInstance().get(TranslateKeys.OPTIONAL_TAG));
		
		Dimension buttonSize = new Dimension(Math.max(100, cancelButton.getPreferredSize().width), cancelButton.getPreferredSize().height);
		okButton.setPreferredSize(buttonSize);
		cancelButton.setPreferredSize(buttonSize);
		
		check = new JCheckBox(Translate.getInstance().get(TranslateKeys.INCOME));
		
		JPanel textPanel = new JPanel(new BorderLayout());
		JPanel textPanelLeft = new JPanel(new GridLayout(0, 1));
		JPanel textPanelRight = new JPanel(new GridLayout(0, 1));
		textPanel.add(textPanelLeft, BorderLayout.WEST);
		textPanel.add(textPanelRight, BorderLayout.EAST);
		
		textPanelLeft.add(nameLabel);
		textPanelRight.add(name);
		if (!this.getClass().equals(TypeModifyDialog.class)){
			textPanelLeft.add(pulldownLabel);
			textPanelRight.add(pulldown);
			textPanelLeft.add(amountLabel);
			textPanelRight.add(amount);
			if (this.getClass().equals(AccountModifyDialog.class)){
				if (PrefsInstance.getInstance().getPrefs().isShowCreditLimit()){
					textPanelLeft.add(creditLimitLabel);
					textPanelRight.add(creditLimit);
				}
				if (PrefsInstance.getInstance().getPrefs().isShowInterestRate()){
					textPanelLeft.add(interestRateLabel);
					textPanelRight.add(interestRate);
				}
			}
		}
		if (!this.getClass().equals(AccountModifyDialog.class)){
			textPanelLeft.add(gap);
			textPanelRight.add(check);
		}
		
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
		
		this.setLayout(new BorderLayout());
		this.add(mainPanel);
		this.getRootPane().setDefaultButton(okButton);		
	}
	
	protected abstract String getType();
	
	public StandardWindow clear(){
		name.setText("");
		amount.setValue(0);
		pulldown.setSelectedItem(null);
		check.setSelected(false);
		this.source = null;
		this.setTitle(Translate.getInstance().get(TranslateKeys.NEW) + " " + getType());
		return this;
	}
	
	public AbstractDialog loadSource(SourceType source){
		this.source = source;
		this.setTitle(Translate.getInstance().get(TranslateKeys.EDIT) + " " + getType());
		return this;
	}
	
	public AbstractDialog loadType(Type type){
		this.type = type;
		return this;
	}
	
	public StandardWindow updateButtons() {
		return this;
	}
}
