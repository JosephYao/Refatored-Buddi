/*
 * Created on May 20, 2006 by wyatt
 */
package org.homeunix.drummer.view.reports.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.homeunix.drummer.Strings;
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractBudgetDialog;
import org.homeunix.drummer.view.layout.ReportPanelLayout.ReportType;
import org.homeunix.drummer.view.reports.layout.CustomDateDialogLayout;

public class CustomDateIntervalDialog extends CustomDateDialogLayout {
	public static final long serialVersionUID = 0;
	
	private ReportType reportType;

	public CustomDateIntervalDialog(JFrame parent, ReportType reportType){
		super(parent);
		
		this.reportType = reportType;
	}
	
	@Override
	protected AbstractBudgetDialog clearContent() {
		return this;
	}

	@Override
	protected AbstractBudgetDialog initActions() {
		okButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				Date startDate;
				Date endDate;
				if (startDateCombo.getValue() instanceof Date
						&& endDateCombo.getValue() instanceof Date){
					startDate = DateUtil.getStartDate((Date) startDateCombo.getValue());
					endDate = DateUtil.getEndDate((Date) endDateCombo.getValue());
				}
				else{
					Log.debug("Start and End interval not of type date");
					return;
				}
				
				if (endDate.before(startDate)){
					JOptionPane.showMessageDialog(
							null, 
							Strings.inst().get(Strings.START_DATE_AFTER_END_DATE), 
							Strings.inst().get(Strings.REPORT_DATE_ERROR), 
							JOptionPane.ERROR_MESSAGE
					);
					return;
				}
				
				Log.debug("Getting transactions between " + startDate + " and " + endDate);
				
				if (reportType.equals(ReportType.INCOME_EXPENSE))
					new IncomeExpenseReportFrame(startDate, endDate);
				else if (reportType.equals(ReportType.REVENUE_EXPENSE))
					new ExpenseBudgetedActualGraphFrame(startDate, endDate);
				else
					Log.debug("Don't know what to do with type " + reportType);
				//[TODO] Add more types as needed...
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
	protected AbstractBudgetDialog initContent() {
		return this;
	}

	@Override
	public AbstractBudgetDialog updateButtons() {
		return this;
	}

	@Override
	protected AbstractBudgetDialog updateContent() {
		return this;
	}
	
	protected void setVisibility(){
		mainLabel.setText(Strings.inst().get(Strings.REPORT_BETWEEN));
		middleLabel.setText(Strings.inst().get(Strings.AND));		
	}	
}
