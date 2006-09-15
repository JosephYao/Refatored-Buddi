/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.graphs;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.BuddiGraphPlugin;
import org.homeunix.drummer.plugins.BuddiPluginFactory.DateRangeType;
import org.homeunix.drummer.prefs.Interval;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.util.Log;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ExpenseBudgetedVsActual implements BuddiGraphPlugin {
	public DateRangeType getDateRangeType() {
		return DateRangeType.INTERVAL;
	}
	public String getDescription() {
		return TranslateKeys.EXPENSE_ACTUAL_BUDGET_BAR_GRAPH.toString();
	}
	public JPanel getGraphPanel(Date startDate, Date endDate) {
		DefaultCategoryDataset barData = new DefaultCategoryDataset();
		
		Map<Category, Long> categories = getExpensesBetween(startDate, endDate);
		
		Vector<Category> cats = new Vector<Category>(categories.keySet());
		Collections.sort(cats);
		
		double numberOfBudgetPeriods;
		Interval interval = PrefsInstance.getInstance().getSelectedInterval();
		if (!interval.isDays()){
			if (DateUtil.daysBetween(startDate, endDate) <= 25){
				numberOfBudgetPeriods = DateUtil.daysBetween(startDate, endDate) / (30 * interval.getLength());
			}
			else{
				numberOfBudgetPeriods = (DateUtil.monthsBetween(startDate, endDate) + 1) / interval.getLength();
			}
		}
		else{
			numberOfBudgetPeriods = (DateUtil.daysBetween(startDate, endDate) + 1) / interval.getLength();
		}
		
		for (Category c : cats) {
			if (categories.get(c) > 0 || c.getBudgetedAmount() > 0){
				barData.addValue((Number) new Double(categories.get(c) / 100.0), Translate.getInstance().get(TranslateKeys.ACTUAL), Translate.getInstance().get(c.toString()));
				barData.addValue((Number) new Double(c.getBudgetedAmount() * numberOfBudgetPeriods / 100.0), Translate.getInstance().get(TranslateKeys.BUDGETED), Translate.getInstance().get(c.toString()));
			}
		}
		
		JFreeChart chart = ChartFactory.createBarChart(
				Translate.getInstance().get(TranslateKeys.EXPENSE_ACTUAL_BUDGET)
				+ " (" 
				+ Formatter.getInstance().getDateFormat().format(startDate)
				+ " - "
				+ Formatter.getInstance().getDateFormat().format(endDate)
				+ ")",
				"", //Domain axis label
				"", //Range axis label
				barData,             // data
				PlotOrientation.VERTICAL,
				true,               // include legend
				true,
				false
		);
		
		CategoryPlot plot = (CategoryPlot) chart.getCategoryPlot();
		plot.setNoDataMessage("No data available");
		
		return new ChartPanel(chart);
	}
	
	public String getTitle() {
		return "";
	}
	
	private Map<Category, Long> getExpensesBetween(Date startDate, Date endDate){
		Vector<Transaction> transactions = DataInstance.getInstance().getTransactions(startDate, endDate);
		Map<Category, Long> categories = new HashMap<Category, Long>();
		
		//This map is where we store the totals for this time period.
		for (Category category : DataInstance.getInstance().getCategories()) {
			if (!category.isIncome())
				categories.put(category, new Long(0));
		}
		
		for (Transaction transaction : transactions) {
			//Sum up the amounts for each category.
			if (transaction.getFrom() instanceof Category){
				Category c = (Category) transaction.getFrom();
				if (!c.isIncome()){
					Long l = categories.get(c);
					l += transaction.getAmount();
					categories.put(c, l);
					Log.debug("Added a source");
				}
			}
			else if (transaction.getTo() instanceof Category){
				Category c = (Category) transaction.getTo();
				if (!c.isIncome()){
					Long l = categories.get(c);
					l += transaction.getAmount();
					categories.put(c, l);
					Log.debug("Added a destination");
				}
			}
			else
				Log.debug("Didn't add anything...");
		}
				
		return categories;
	}
}
