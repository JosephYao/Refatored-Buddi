/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.dialogs.prefs;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.moss.swing.window.MossPanel;

public class ViewPreferences extends MossPanel implements PrefsPanel {
	public static final long serialVersionUID = 0;
	
	private final JCheckBox showDeleted;
	private final JCheckBox showClear;
//	private final JCheckBox showAccountTypes;
	private final JCheckBox showAutoComplete;
//	private final JCheckBox showCreditLimit;
//	private final JCheckBox showInterestRate;
	private final JCheckBox showReconcile;

	public ViewPreferences() {
		super(true);
		
		showDeleted = new JCheckBox(PrefsModel.getInstance().getTranslator().get(BuddiKeys.SHOW_DELETED));
//		showAccountTypes = new JCheckBox(PrefsModel.getInstance().getTranslator().get(BuddiKeys.SHOW_ACCOUNT_TYPES));
		showAutoComplete = new JCheckBox(PrefsModel.getInstance().getTranslator().get(BuddiKeys.AUTO_COMPLETE_TRANSACTION_INFORMATION));
//		showCreditLimit = new JCheckBox(PrefsModel.getInstance().getTranslator().get(BuddiKeys.SHOW_CREDIT_LIMIT));
//		showInterestRate = new JCheckBox(PrefsModel.getInstance().getTranslator().get(BuddiKeys.SHOW_INTEREST_RATE));
		showClear = new JCheckBox(PrefsModel.getInstance().getTranslator().get(BuddiKeys.SHOW_CLEAR));
		showReconcile = new JCheckBox(PrefsModel.getInstance().getTranslator().get(BuddiKeys.SHOW_RECONCILE));
		
		open();
	}
	
	@Override
	public void init() {
		super.init();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		JPanel autoCompletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		JPanel creditLimitPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		JPanel interestRatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel clearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel reconcilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		deletePanel.add(showDeleted);
//		accountPanel.add(showAccountTypes);
		autoCompletePanel.add(showAutoComplete);
//		creditLimitPanel.add(showCreditLimit);
//		interestRatePanel.add(showInterestRate);
		clearPanel.add(showClear);
		reconcilePanel.add(showReconcile);
		
		showDeleted.setSelected(PrefsModel.getInstance().isShowDeleted());
		showAutoComplete.setSelected(PrefsModel.getInstance().isShowAutoComplete());
		showClear.setSelected(PrefsModel.getInstance().isShowCleared());
		showReconcile.setSelected(PrefsModel.getInstance().isShowReconciled());

		
		this.add(deletePanel);
//		this.add(accountPanel);
//		this.add(interestRatePanel);
//		this.add(creditLimitPanel);
		this.add(clearPanel);
		this.add(reconcilePanel);
		this.add(autoCompletePanel);
		this.add(Box.createVerticalGlue());
	}
	
	public void save() {
		PrefsModel.getInstance().setShowDeleted(showDeleted.isSelected());
		PrefsModel.getInstance().setShowAutoComplete(showAutoComplete.isSelected());
		PrefsModel.getInstance().setShowCleared(showClear.isSelected());
		PrefsModel.getInstance().setShowReconciled(showReconcile.isSelected());
	}
}
