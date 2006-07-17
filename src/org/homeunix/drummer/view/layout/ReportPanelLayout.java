/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view.layout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.view.AbstractBudgetPanel;

public abstract class ReportPanelLayout extends AbstractBudgetPanel {
	public static final long serialVersionUID = 0;
	
	protected final Map<JComboBox, ReportType> jComboBoxes; 
	
	public enum ReportType {
		//Reports
		INCOME_EXPENSE_BY_CATEGORY,
		INCOME_EXPENSE_BY_DESCRIPTION,
		
		//Graphs
		EXPENSES,
		INCOME,
		NETWORTH,
		REVENUE_EXPENSE,
		NETWORTH_OVER_TIME
	};
	
	protected class DateChoice {
		private Date startDate;
		private Date endDate;
		private String name;
		private boolean custom = false;
		
		public DateChoice(String name){
			this.name = name;
			custom = true;
		}
		
		public DateChoice(Date startDate, Date endDate, String name){
			this.startDate = startDate;
			this.endDate = endDate;
			this.name = name;
		}
		
		public String toString(){
			return name;
		}
		
		public Date getStartDate(){
			return startDate;
		}

		public Date getEndDate(){
			return endDate;
		}
		
		public boolean isCustom(){
			return custom;
		}
	}
	
	
	protected ReportPanelLayout(){
		
		Vector<DateChoice> intervals = getIntervals();
		Vector<DateChoice> endDates = getEndDates();
		Vector<DateChoice> startDates = getStartDates();
				
		//Reports
		final JComboBox incomeExpenseByCategoryReportIntervalChooser = new JComboBox(intervals);
		final JComboBox incomeExpenseByDescriptionReportIntervalChooser = new JComboBox(intervals);
		
		//Graphs
		final JComboBox expensePieGraphIntervalChooser = new JComboBox(intervals);
		final JComboBox incomePieGraphIntervalChooser = new JComboBox(intervals);
		final JComboBox netWorthPieGraphIntervalChooser = new JComboBox(endDates);
		final JComboBox revenueExpenseBarGraphIntervalChooser = new JComboBox(intervals);
		final JComboBox netWorthOverTimeLineGraphIntervalChooser = new JComboBox(startDates);
		
		jComboBoxes = new HashMap<JComboBox, ReportType>();
		jComboBoxes.put(expensePieGraphIntervalChooser, ReportType.EXPENSES);
		jComboBoxes.put(incomePieGraphIntervalChooser, ReportType.INCOME);
		jComboBoxes.put(netWorthPieGraphIntervalChooser, ReportType.NETWORTH);
		jComboBoxes.put(revenueExpenseBarGraphIntervalChooser, ReportType.REVENUE_EXPENSE);
		jComboBoxes.put(netWorthOverTimeLineGraphIntervalChooser, ReportType.NETWORTH_OVER_TIME);
		
		jComboBoxes.put(incomeExpenseByCategoryReportIntervalChooser, ReportType.INCOME_EXPENSE_BY_CATEGORY);
		jComboBoxes.put(incomeExpenseByDescriptionReportIntervalChooser, ReportType.INCOME_EXPENSE_BY_DESCRIPTION);
		
		JPanel reportsPanel = new JPanel();
		reportsPanel.setLayout(new BoxLayout(reportsPanel, BoxLayout.Y_AXIS));
		reportsPanel.setBorder(BorderFactory.createTitledBorder(Translate.getInstance().get(TranslateKeys.REPORTS)));
		JPanel graphsPanel = new JPanel();
		graphsPanel.setLayout(new BoxLayout(graphsPanel, BoxLayout.Y_AXIS));
		graphsPanel.setBorder(BorderFactory.createTitledBorder(Translate.getInstance().get(TranslateKeys.GRAPHS)));
		
		JPanel r1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel r2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		r1.add(new JLabel(Translate.getInstance().get(TranslateKeys.REPORT_INCOME_EXPENSES_BY_CATEGORY)));		
		r1.add(incomeExpenseByCategoryReportIntervalChooser);

		r2.add(new JLabel(Translate.getInstance().get(TranslateKeys.REPORT_INCOME_EXPENSES_BY_DESCRIPTION)));
		r2.add(incomeExpenseByDescriptionReportIntervalChooser);
		
		JPanel g1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel g2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel g3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel g4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel g5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		g1.add(new JLabel(Translate.getInstance().get(TranslateKeys.EXPENSE_PIE_GRAPH)));
		g1.add(expensePieGraphIntervalChooser);

		g2.add(new JLabel(Translate.getInstance().get(TranslateKeys.INCOME_PIE_GRAPH)));
		g2.add(incomePieGraphIntervalChooser);
		
		g3.add(new JLabel(Translate.getInstance().get(TranslateKeys.EXPENSE_ACTUAL_BUDGET_BAR_GRAPH)));
		g3.add(revenueExpenseBarGraphIntervalChooser);

		g4.add(new JLabel(Translate.getInstance().get(TranslateKeys.NETWORTH_PIE_GRAPH)));
		g4.add(netWorthPieGraphIntervalChooser);

		g5.add(new JLabel(Translate.getInstance().get(TranslateKeys.NETWORTH_LINE_GRAPH)));
		g5.add(netWorthOverTimeLineGraphIntervalChooser);

		
		reportsPanel.add(r1);
		reportsPanel.add(r2);
		
		graphsPanel.add(g1);
		graphsPanel.add(g2);
		graphsPanel.add(g3);
		graphsPanel.add(g4);
		graphsPanel.add(g5);
		
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 7, 17));
		
		mainPanel.add(reportsPanel);
		mainPanel.add(graphsPanel);
		
		Dimension comboBoxSize = incomeExpenseByCategoryReportIntervalChooser.getPreferredSize();
		for (JComboBox box : jComboBoxes.keySet()) {
			box.setPreferredSize(comboBoxSize);
		}
		
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
				
		//Call the method to add actions to the buttons
		initActions();
		updateContent();
		updateButtons();
	}
	
	public AbstractBudgetPanel updateButtons(){

		return this;
	}
	
	protected abstract Vector<DateChoice> getIntervals();
	protected abstract Vector<DateChoice> getEndDates();
	protected abstract Vector<DateChoice> getStartDates();
}
