/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.dialogs.preferences;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.BuddiPreferencePlugin;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.moss.util.Version;

public class ViewPreferences extends BuddiPreferencePlugin {
	public static final long serialVersionUID = 0;
	
	private final JCheckBox showDeleted;
	private final JCheckBox showClear;
	private final JCheckBox showAutoComplete;
	private final JCheckBox showReconcile;
	private final JCheckBox showFlatAccounts;
	private final JCheckBox showFlatBudget;

	public ViewPreferences() {
		showDeleted = new JCheckBox(TextFormatter.getTranslation(BuddiKeys.PREFERENCE_SHOW_DELETED));
		showAutoComplete = new JCheckBox(TextFormatter.getTranslation(BuddiKeys.PREFERENCE_AUTO_COMPLETE_TRANSACTION_INFORMATION));
		showClear = new JCheckBox(TextFormatter.getTranslation(BuddiKeys.PREFERENCE_SHOW_CLEAR));
		showReconcile = new JCheckBox(TextFormatter.getTranslation(BuddiKeys.PREFERENCE_SHOW_RECONCILE));
		showFlatAccounts = new JCheckBox(TextFormatter.getTranslation(BuddiKeys.PREFERENCE_SHOW_FLAT_ACCOUNTS));
		showFlatBudget = new JCheckBox(TextFormatter.getTranslation(BuddiKeys.PREFERENCE_SHOW_FLAT_BUDGET));
	}

	@Override
	public JPanel getPreferencesPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel autoCompletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel clearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel reconcilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel flatAccountsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel flatBudgetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		deletePanel.add(showDeleted);
		autoCompletePanel.add(showAutoComplete);
		clearPanel.add(showClear);
		reconcilePanel.add(showReconcile);
		flatAccountsPanel.add(showFlatAccounts);
		flatBudgetPanel.add(showFlatBudget);
		
		panel.add(autoCompletePanel);
		panel.add(deletePanel);
		panel.add(clearPanel);
		panel.add(reconcilePanel);
		panel.add(flatAccountsPanel);
		panel.add(flatBudgetPanel);
		panel.add(Box.createVerticalGlue());
		
		return panel;
	}
	
	public void load() {
		showDeleted.setSelected(PrefsModel.getInstance().isShowDeleted());
		showAutoComplete.setSelected(PrefsModel.getInstance().isShowAutoComplete());
		showClear.setSelected(PrefsModel.getInstance().isShowCleared());
		showReconcile.setSelected(PrefsModel.getInstance().isShowReconciled());
		showFlatAccounts.setSelected(PrefsModel.getInstance().isShowFlatAccounts());
		showFlatBudget.setSelected(PrefsModel.getInstance().isShowFlatBudget());
	}
	
	public boolean save() {
		System.out.println("Saving");
		
		boolean restart = false;
		
		PrefsModel.getInstance().setShowDeleted(showDeleted.isSelected());
		PrefsModel.getInstance().setShowAutoComplete(showAutoComplete.isSelected());
		PrefsModel.getInstance().setShowCleared(showClear.isSelected());
		PrefsModel.getInstance().setShowReconciled(showReconcile.isSelected());
		System.out.println("Checkbox Accounts  = " + showFlatAccounts.isSelected());
		PrefsModel.getInstance().setShowFlatAccounts(showFlatAccounts.isSelected());
		System.out.println("Checkbox Budget  = " + showFlatBudget.isSelected());
		PrefsModel.getInstance().setShowFlatBudget(showFlatBudget.isSelected());
		
		return restart;
	}
	
	public String getName() {
		return BuddiKeys.VIEW.toString();
	}
	
	public Version getMaximumVersion() {
		return null;
	}
	
	public Version getMinimumVersion() {
		return null;
	}
}
