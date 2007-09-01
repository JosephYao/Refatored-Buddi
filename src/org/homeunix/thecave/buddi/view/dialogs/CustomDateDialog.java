/*
 * Created on May 20, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.BudgetCategoryTypes;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.i18n.keys.PluginReportDateRangeChoices;
import org.homeunix.thecave.buddi.model.BudgetCategoryType;
import org.homeunix.thecave.buddi.model.impl.ModelFactory;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.BuddiPluginHelper;
import org.homeunix.thecave.buddi.plugin.api.BuddiReportPlugin;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.moss.swing.MossDialog;
import org.homeunix.thecave.moss.util.DateFunctions;
import org.homeunix.thecave.moss.util.Log;
import org.jdesktop.swingx.JXDatePicker;

/**
 * The dialog which allows users to choose a custom date range, or
 * start / end date only.  Designed to launch a plugin when the user 
 * has chosen the date.
 * 
 * @author wyatt
 *
 */
public class CustomDateDialog extends MossDialog implements ActionListener {
	public static final long serialVersionUID = 0;

	protected final JButton okButton;
	protected final JButton cancelButton;

	protected final JLabel mainLabel;
	protected final JLabel middleLabel;

	protected final JXDatePicker startDateChooser;
	protected final JXDatePicker endDateChooser;

	protected final BuddiReportPlugin report;
	private final MainFrame reportFrame;

	public CustomDateDialog(MainFrame reportFrame, BuddiReportPlugin plugin){
		super(reportFrame);
		
		this.reportFrame = reportFrame;
		this.report = plugin;

		okButton = new JButton(TextFormatter.getTranslation(ButtonKeys.BUTTON_OK));
		cancelButton = new JButton(TextFormatter.getTranslation(ButtonKeys.BUTTON_CANCEL));

		Dimension buttonSize = new Dimension(100, okButton.getPreferredSize().height);
		okButton.setPreferredSize(buttonSize);
		cancelButton.setPreferredSize(buttonSize);

		startDateChooser = new JXDatePicker();
		endDateChooser = new JXDatePicker();

		startDateChooser.setEditor(new JFormattedTextField(new SimpleDateFormat(PrefsModel.getInstance().getDateFormat())));
		startDateChooser.setDate(new Date());
		endDateChooser.setEditor(new JFormattedTextField(new SimpleDateFormat(PrefsModel.getInstance().getDateFormat())));
		endDateChooser.setDate(new Date());

		Dimension textFieldSize = new Dimension(180, startDateChooser.getPreferredSize().height);

		startDateChooser.setPreferredSize(textFieldSize);
		endDateChooser.setPreferredSize(textFieldSize);

		startDateChooser.setDate(new Date());
		endDateChooser.setDate(new Date());

		JPanel r1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel r2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		mainLabel = new JLabel();
		middleLabel = new JLabel(); 

		r1.add(mainLabel);

		r2.add(startDateChooser);
		r2.add(middleLabel);
		r2.add(endDateChooser);

		JPanel textPanelSpacer = new JPanel();
		textPanelSpacer.setLayout(new BoxLayout(textPanelSpacer, BoxLayout.Y_AXIS));
		textPanelSpacer.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		textPanelSpacer.add(r1);
		textPanelSpacer.add(r2);

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
		this.setTitle(TextFormatter.getTranslation(BuddiKeys.CHOOSE_DATE_INTERVAL));

		//Set the start date to be (by default) a time in the past
		// back as far as the current interval.  For instance, if the 
		// interval is set to 1 month, and the day is Feb 15, set the 
		// date to Jan 1.
		// This is not guaranteed to give perfect results every time,
		// but if we are giving the user a choice in the date interva;,
		// we may as well start at a time in the past rather than just
		// give them the current date, as we have done before now.
		// Added to address feature request #1649972.
		BudgetCategoryType period = ModelFactory.getBudgetCategoryType(BudgetCategoryTypes.BUDGET_CATEGORY_TYPE_MONTH);
		startDateChooser.setDate(period.getStartOfBudgetPeriod(new Date()));			

		setVisibility(plugin.getDateRangeChoice());
	}

	private void setVisibility(PluginReportDateRangeChoices rangeChoiceType){
		if (rangeChoiceType.equals(PluginReportDateRangeChoices.INTERVAL)){
			mainLabel.setText(TextFormatter.getTranslation(BuddiKeys.REPORT_BETWEEN));
//			middleLabel.setText(TextFormatter.getTranslation(TranslateKeys.AND));					
			middleLabel.setText(" - ");
		}
		else{
			mainLabel.setText(TextFormatter.getTranslation(BuddiKeys.REPORT_AS_OF_DATE));
			middleLabel.setVisible(false);

			if (rangeChoiceType.equals(PluginReportDateRangeChoices.START_ONLY)){
				endDateChooser.setVisible(false);
			}
			else if (rangeChoiceType.equals(PluginReportDateRangeChoices.END_ONLY)){
				startDateChooser.setVisible(false);
			}
		}
	}


	public void init() {
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(okButton)){
			Date startDate, endDate;

			startDate = DateFunctions.getStartOfDay(startDateChooser.getDate());
			endDate = DateFunctions.getEndOfDay(endDateChooser.getDate());

			if (report.getDateRangeChoice().equals(PluginReportDateRangeChoices.INTERVAL)
					&& endDate.before(startDate)){
				String[] options = new String[1];
				options[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_OK);

				JOptionPane.showOptionDialog(
						null, 
						TextFormatter.getTranslation(BuddiKeys.START_DATE_AFTER_END_DATE), 
						TextFormatter.getTranslation(BuddiKeys.REPORT_DATE_ERROR),
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null,
						options,
						options[0]
				);
				return;
			}

			if (Const.DEVEL) Log.debug("Getting transactions between " + startDate + " and " + endDate);

			CustomDateDialog.this.setVisible(false);

			BuddiPluginHelper.openReport(reportFrame, report, startDate, endDate);
		}
		else if (e.getSource().equals(cancelButton)){
			this.closeWindow();
		}
	}

}
