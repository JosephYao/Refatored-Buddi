/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JComboBox;

import org.homeunix.drummer.Translate;
import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.controller.model.DataInstance;
import org.homeunix.drummer.controller.reports.CustomDateIntervalDialog;
import org.homeunix.drummer.controller.reports.CustomEndDateDialog;
import org.homeunix.drummer.controller.reports.CustomStartDateDialog;
import org.homeunix.drummer.controller.reports.ExpenseBudgetedActualGraphFrame;
import org.homeunix.drummer.controller.reports.ExpensesGraphFrame;
import org.homeunix.drummer.controller.reports.IncomeExpenseByCategoryReportFrame;
import org.homeunix.drummer.controller.reports.IncomeExpenseByDescriptionReportFrame;
import org.homeunix.drummer.controller.reports.IncomeGraphFrame;
import org.homeunix.drummer.controller.reports.NetWorthGraphFrame;
import org.homeunix.drummer.controller.reports.NetWorthOverTimeGraphFrame;
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.view.AbstractBudgetPanel;
import org.homeunix.drummer.view.ReportPanelLayout;

public class ReportPanel extends ReportPanelLayout {
	public static final long serialVersionUID = 0;
		
	public ReportPanel(){
		super();
	}
	
	protected AbstractBudgetPanel initActions(){
		for (JComboBox box : jComboBoxes.keySet()) {
			box.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if (arg0.getSource() != null){
						if (arg0.getSource() instanceof JComboBox) {
							JComboBox box = (JComboBox) arg0.getSource();
							if (box.getSelectedItem() instanceof DateChoice) {
								DateChoice choice = (DateChoice) box.getSelectedItem();
								if (jComboBoxes.get(box) instanceof ReportType) {
									ReportType type = (ReportType) jComboBoxes.get(box);
									if (choice.isCustom()){
										if (type.equals(ReportType.REVENUE_EXPENSE)
												|| type.equals(ReportType.INCOME_EXPENSE_BY_CATEGORY)
												|| type.equals(ReportType.INCOME_EXPENSE_BY_DESCRIPTION)
												|| type.equals(ReportType.EXPENSES)
												|| type.equals(ReportType.INCOME))
											new CustomDateIntervalDialog(
													MainBuddiFrame.getInstance(),
													type
											).openWindow();
										else if (type.equals(ReportType.NETWORTH_OVER_TIME))
											new CustomStartDateDialog(
													MainBuddiFrame.getInstance(),
													type
											).openWindow();
										else if (type.equals(ReportType.NETWORTH))
											new CustomEndDateDialog(
													MainBuddiFrame.getInstance(),
													type
											).openWindow();

											
									}
									else{
										// TODO Add more report / graph types here as needed.
										if (type.equals(ReportType.EXPENSES))
											new ExpensesGraphFrame(choice.getStartDate(), choice.getEndDate());
										else if (type.equals(ReportType.INCOME))
											new IncomeGraphFrame(choice.getStartDate(), choice.getEndDate());
										else if(type.equals(ReportType.NETWORTH))
											new NetWorthGraphFrame(choice.getEndDate());
										else if(type.equals(ReportType.REVENUE_EXPENSE))
											new ExpenseBudgetedActualGraphFrame(choice.getStartDate(), choice.getEndDate());
										else if(type.equals(ReportType.NETWORTH_OVER_TIME))
											new NetWorthOverTimeGraphFrame(choice.getStartDate());
										else if(type.equals(ReportType.INCOME_EXPENSE_BY_CATEGORY))
											new IncomeExpenseByCategoryReportFrame(choice.getStartDate(), choice.getEndDate());
										else if(type.equals(ReportType.INCOME_EXPENSE_BY_DESCRIPTION))
											new IncomeExpenseByDescriptionReportFrame(choice.getStartDate(), choice.getEndDate());
									}
								}
							}
							box.setSelectedItem(null);
						}
					}
				}
			});
		}
		return this;
	}
	
	protected AbstractBudgetPanel initContent(){
		DataInstance.getInstance().calculateAllBalances();
		
		return this;
	}
	
	public AbstractBudgetPanel updateContent(){

		return this;
	}

	@Override
	protected Vector<DateChoice> getIntervals() {
		Vector<DateChoice> intervals = new Vector<DateChoice>();
		
		intervals.add(null);
		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getNextNDay(new Date(), -7)),
				DateUtil.getEndOfDay(new Date()),
				Translate.getInstance().get(TranslateKeys.PAST_WEEK)
		));
		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getNextNDay(new Date(), -14)),
				DateUtil.getStartOfDay(DateUtil.getNextNDay(new Date(), 0)),
				Translate.getInstance().get(TranslateKeys.PAST_FORTNIGHT)
		));
		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getBeginOfMonth(new Date(), 0)),
				DateUtil.getEndOfDay(DateUtil.getEndOfMonth(new Date(), 0)),
				Translate.getInstance().get(TranslateKeys.THIS_MONTH)
		));
		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getBeginOfMonth(new Date(), -1)),
				DateUtil.getEndOfDay(DateUtil.getEndOfMonth(new Date(), -1)),
				Translate.getInstance().get(TranslateKeys.LAST_MONTH)
		));
		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getBeginOfQuarter(new Date(), 0)),
				DateUtil.getStartOfDay(DateUtil.getEndOfQuarter(new Date(), 0)),
				Translate.getInstance().get(TranslateKeys.THIS_QUARTER)
		));
		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getBeginOfQuarter(new Date(), -1)),
				DateUtil.getStartOfDay(DateUtil.getEndOfQuarter(new Date(), -1)),
				Translate.getInstance().get(TranslateKeys.LAST_QUARTER)
		));
		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getBeginOfYear(Calendar.getInstance().get(Calendar.YEAR))),
				DateUtil.getEndOfDay(DateUtil.getEndOfYear(Calendar.getInstance().get(Calendar.YEAR))),
				Translate.getInstance().get(TranslateKeys.THIS_YEAR)
		));
		
		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getBeginOfYear(Calendar.getInstance().get(Calendar.YEAR) - 1)),
				DateUtil.getEndOfDay(DateUtil.getEndOfYear(Calendar.getInstance().get(Calendar.YEAR) - 1)),
				Translate.getInstance().get(TranslateKeys.LAST_YEAR)
		));
		intervals.add(new DateChoice(
				Translate.getInstance().get(TranslateKeys.OTHER)
		));
		
		return intervals;
	}

	@Override
	protected Vector<DateChoice> getEndDates() {
		Vector<DateChoice> endDates = new Vector<DateChoice>();
		
		endDates.add(null);
		endDates.add(new DateChoice(
				null,
				DateUtil.getEndOfDay(new Date()),
				Translate.getInstance().get(TranslateKeys.TODAY)
		));
		endDates.add(new DateChoice(
				null,
				DateUtil.getEndOfDay(DateUtil.getNextNDay(new Date(), -1)),
				Translate.getInstance().get(TranslateKeys.YESTERDAY)
		));
		endDates.add(new DateChoice(
				null,
				DateUtil.getEndOfDay(DateUtil.getNextNDay(new Date(), -8)),
				Translate.getInstance().get(TranslateKeys.LAST_WEEK)
		));
		endDates.add(new DateChoice(
				null,
				DateUtil.getEndOfDay(DateUtil.getNextNDay(new Date(), -30)),
				Translate.getInstance().get(TranslateKeys.LAST_MONTH)
		));
		endDates.add(new DateChoice(
				null,
				DateUtil.getEndOfDay(DateUtil.getNextNDay(new Date(), -365)),
				Translate.getInstance().get(TranslateKeys.LAST_YEAR)
		));
		endDates.add(new DateChoice(
				Translate.getInstance().get(TranslateKeys.OTHER)
		));
		

		return endDates;
	}

	@Override
	protected Vector<DateChoice> getStartDates() {
		Vector<DateChoice> startDates = new Vector<DateChoice>();

		
		startDates.add(null);
		startDates.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getNextNDay(new Date(), -30)),
				null,
				Translate.getInstance().get(TranslateKeys.ONE_MONTH)
		));
		startDates.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getNextNDay(new Date(), -60)),
				null,
				Translate.getInstance().get(TranslateKeys.TWO_MONTHS)
		));
		startDates.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getEndOfMonth(DateUtil.getStartOfDay(new Date()), -6)),
				null,
				Translate.getInstance().get(TranslateKeys.SIX_MONTHS)
		));
		
		startDates.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getEndOfMonth(DateUtil.getStartOfDay(new Date()), -12)),
				null,
				Translate.getInstance().get(TranslateKeys.YEAR)
		));
		
		startDates.add(new DateChoice(
				Translate.getInstance().get(TranslateKeys.OTHER)
		));
		
		return startDates;
	}
	
	
}
