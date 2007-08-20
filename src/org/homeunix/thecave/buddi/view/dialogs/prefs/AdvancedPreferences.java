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
import org.homeunix.thecave.buddi.i18n.keys.PreferencesKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.moss.swing.window.MossPanel;

public class AdvancedPreferences extends MossPanel implements PrefsPanel {
	public static final long serialVersionUID = 0;

	private final JCheckBox promptForDataFile;
	private final JComboBox numberOfBackups;
	private final JCheckBox sendCrashReport;

	
	public AdvancedPreferences() {
		super(true);
		
		numberOfBackups = new JComboBox(new Integer[]{3, 5, 10, 25, 50});
		promptForDataFile = new JCheckBox(TextFormatter.getTranslation(PreferencesKeys.PROMPT_FOR_DATA_FILE_AT_STARTUP));
		sendCrashReport = new JCheckBox(TextFormatter.getTranslation(PreferencesKeys.SEND_CRASH_REPORTS));
		
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
		JPanel sendCrashReportPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JLabel numberOfBackupsLabel = new JLabel(TextFormatter.getTranslation(BuddiKeys.NUMBER_OF_BACKUPS));
		JLabel budgetIntervalLabel = new JLabel(TextFormatter.getTranslation(BuddiKeys.BUDGET_INTERVAL));
		
		budgetIntervalPanel.add(budgetIntervalLabel);
		numberOfBackupsPanel.add(numberOfBackupsLabel);
		numberOfBackupsPanel.add(numberOfBackups);
		promptForDataFilePanel.add(promptForDataFile);
		sendCrashReportPanel.add(sendCrashReport);
				
		this.add(budgetIntervalPanel);
		this.add(numberOfBackupsPanel);
		this.add(editLanguagesPanel);
		this.add(editTypesPanel);
		this.add(updatePanel);
		this.add(promptForDataFilePanel);
		this.add(sendCrashReportPanel);
	}
	
	public void load() {
		numberOfBackups.setSelectedItem(PrefsModel.getInstance().getNumberOfBackups());
		promptForDataFile.setSelected(PrefsModel.getInstance().isShowPromptAtStartup());
		sendCrashReport.setSelected(PrefsModel.getInstance().isSendCrashReports());		
	}

	public void save() {
		PrefsModel.getInstance().setNumberOfBackups((Integer) numberOfBackups.getSelectedItem());
		PrefsModel.getInstance().setShowPromptAtStartup(promptForDataFile.isSelected());
		PrefsModel.getInstance().setSendCrashReports(sendCrashReport.isSelected());
	}
}
