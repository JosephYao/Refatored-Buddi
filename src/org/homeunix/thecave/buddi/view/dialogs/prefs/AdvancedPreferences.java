/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.dialogs.prefs;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.swing.TranslatorListCellRenderer;
import org.homeunix.thecave.moss.swing.window.MossPanel;

public class AdvancedPreferences extends MossPanel implements PrefsPanel {
	public static final long serialVersionUID = 0;

	private final JComboBox budgetInterval;
	private final JCheckBox promptForDataFile;
	private final JComboBox numberOfBackups;

	
	public AdvancedPreferences() {
		super(true);
		
		budgetInterval = new JComboBox(BudgetPeriodKeys.values());//TODOPrefsInstance.getInstance().getIntervals());
		numberOfBackups = new JComboBox(new Integer[]{5, 10, 15, 20, 50});
		promptForDataFile = new JCheckBox(PrefsModel.getInstance().getTranslator().get(BuddiKeys.PROMPT_FOR_DATA_FILE_AT_STARTUP));
		
		open();
	}

	@Override
	public void init() {
		super.init();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel budgetIntervalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel numberOfBackupsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel updatePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel editLanguagesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel editTypesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel promptForDataFilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		budgetInterval.setRenderer(new TranslatorListCellRenderer());
		
//		JButton editTypes = new JButton(PrefsModel.getInstance().getTranslator().get(BuddiKeys.EDIT_ACCOUNT_TYPES));
//		editTypes.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent arg0) {
//				new TypeListDialog().openWindow();
//			}
//		});
		
		JLabel numberOfBackupsLabel = new JLabel(PrefsModel.getInstance().getTranslator().get(BuddiKeys.NUMBER_OF_BACKUPS));
		JLabel budgetIntervalLabel = new JLabel(PrefsModel.getInstance().getTranslator().get(BuddiKeys.BUDGET_INTERVAL));
		
		budgetIntervalPanel.add(budgetIntervalLabel);
		budgetIntervalPanel.add(budgetInterval);
		numberOfBackupsPanel.add(numberOfBackupsLabel);
		numberOfBackupsPanel.add(numberOfBackups);
//		editTypesPanel.add(editTypes);
		promptForDataFilePanel.add(promptForDataFile);
				
		this.add(budgetIntervalPanel);
		this.add(numberOfBackupsPanel);
		this.add(editLanguagesPanel);
		this.add(editTypesPanel);
		this.add(updatePanel);
		this.add(promptForDataFilePanel);
	}

	public void save() {
		// TODO Auto-generated method stub

	}
}
