/*
 * Created on May 19, 2006 by wyatt
 */
package org.homeunix.drummer.view.reports.logic;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;

import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.view.AbstractBudgetFrame;
import org.homeunix.drummer.view.reports.layout.GraphFrameLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ExpenseBudgetedActualGraphFrame extends GraphFrameLayout {
	public static final long serialVersionUID = 0;
	
	public ExpenseBudgetedActualGraphFrame(Date startDate, Date endDate){
		super(startDate, endDate);
	}
	
	@Override
	protected JPanel buildReport(Date startDate, Date endDate) {
		DefaultCategoryDataset barData = new DefaultCategoryDataset();
		
		Map<Category, Long> categories = getExpensesBetween(startDate, endDate);
		
		Vector<Category> cats = new Vector<Category>(categories.keySet());
		Collections.sort(cats);
		
		double numberOfMonths;
		if (DateUtil.daysBetween(startDate, endDate) <= 25){
			numberOfMonths = DateUtil.daysBetween(startDate, endDate) / 30;
		}
		else{
			numberOfMonths = DateUtil.monthsBetween(startDate, endDate) + 1;
		}
		
		for (Category c : cats) {
			barData.addValue((Number) new Double(categories.get(c) / 100.0), Translate.inst().get(TranslateKeys.ACTUAL), Translate.inst().get(c.getName()));
			barData.addValue((Number) new Double(c.getBudgetedAmount() * numberOfMonths / 100.0), Translate.inst().get(TranslateKeys.BUDGETED), Translate.inst().get(c.getName()));
		}
		
		JFreeChart chart = ChartFactory.createBarChart(
				Translate.inst().get(TranslateKeys.EXPENSE_ACTUAL_BUDGET)
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

	@Override
	protected AbstractBudgetFrame initContent() {
		return this;
	}

	@Override
	public AbstractBudgetFrame updateButtons() {
		return this;
	}

	@Override
	public AbstractBudgetFrame updateContent() {
		return this;
	}
}
