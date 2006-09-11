/*
 * Created on May 20, 2006 by wyatt
 */
package org.homeunix.drummer.controller.reports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractDialog;
import org.homeunix.drummer.view.CustomDateDialogLayout;
import org.homeunix.drummer.view.ReportPanelLayout.ReportType;

public class CustomDateIntervalDialog extends CustomDateDialogLayout {
	public static final long serialVersionUID = 0;
	
	private ReportType reportType;

	public CustomDateIntervalDialog(JFrame parent, ReportType reportType){
		super(parent);
		
		this.reportType = reportType;
	}
	
	@Override
	protected AbstractDialog clearContent() {
		return this;
	}

	@Override
	protected AbstractDialog initActions() {
		okButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				Date startDate = DateUtil.getStartOfDay(startDateChooser.getDate());
				Date endDate = DateUtil.getEndOfDay(endDateChooser.getDate());
				
				if (endDate.before(startDate)){
					JOptionPane.showMessageDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.START_DATE_AFTER_END_DATE), 
							Translate.getInstance().get(TranslateKeys.REPORT_DATE_ERROR), 
							JOptionPane.ERROR_MESSAGE
					);
					return;
				}
				
				Log.debug("Getting transactions between " + startDate + " and " + endDate);
				
				if (reportType.equals(ReportType.INCOME_EXPENSE_BY_CATEGORY))
					new IncomeExpenseByCategoryReportFrame(startDate, endDate);
				else if (reportType.equals(ReportType.INCOME_EXPENSE_BY_DESCRIPTION))
					new IncomeExpenseByDescriptionReportFrame(startDate, endDate);
				else if (reportType.equals(ReportType.EXPENSES))
					new ExpensesGraphFrame(startDate, endDate);
				else if (reportType.equals(ReportType.INCOME))
					new IncomeGraphFrame(startDate, endDate);
				else if (reportType.equals(ReportType.REVENUE_EXPENSE))
					new ExpenseBudgetedActualGraphFrame(startDate, endDate);
				else
					Log.debug("Don't know what to do with type " + reportType);
				//TODO Add more types as needed...
				//else if (reportType.equals(ReportType.meetBudget))
		
				CustomDateIntervalDialog.this.setVisible(false);
			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				CustomDateIntervalDialog.this.setVisible(false);
			}
		});
		
		return this;
	}

	@Override
	protected AbstractDialog initContent() {
		return this;
	}

	@Override
	public AbstractDialog updateButtons() {
		return this;
	}

	@Override
	protected AbstractDialog updateContent() {
		return this;
	}
	
	protected void setVisibility(){
		mainLabel.setText(Translate.getInstance().get(TranslateKeys.REPORT_BETWEEN));
		middleLabel.setText(Translate.getInstance().get(TranslateKeys.AND));		
	}	
}
