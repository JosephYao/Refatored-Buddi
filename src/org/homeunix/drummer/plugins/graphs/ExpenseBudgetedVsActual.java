/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.graphs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;
import org.homeunix.drummer.plugins.interfaces.BuddiGraphPlugin;
import org.homeunix.drummer.prefs.Interval;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.HTMLExportHelper;
import org.homeunix.drummer.view.HTMLExportHelper.HTMLWrapper;
import org.homeunix.thecave.moss.util.DateUtil;
import org.homeunix.thecave.moss.util.Log;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ExpenseBudgetedVsActual implements BuddiGraphPlugin {

	public HTMLWrapper getGraph(Date startDate, Date endDate) {
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
				"", //Title - we do this as a JLabel for more flexibility
				"", //Domain axis label
				"", //Range axis label
				barData,             // data
				PlotOrientation.HORIZONTAL,
				true,               // include legend
				true,
				false
		);
		chart.setBackgroundPaint(Color.WHITE);
		chart.setBorderStroke(new BasicStroke(0));
				
		StringBuilder sb = HTMLExportHelper.getHtmlHeader(
				Translate.getInstance().get(TranslateKeys.GRAPH_TITLE_EXPENSE_ACTUAL_BUDGET), 
				null, 
				startDate, 
				endDate);

		sb.append("<img class='center_img' src='graph.png' />");
		sb.append(HTMLExportHelper.getHtmlFooter());
		
		Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
		images.put("graph.png", 
				chart.createBufferedImage(
						Toolkit.getDefaultToolkit().getScreenSize().width - 200, 
						cats.size() * 50));
		
		return new HTMLWrapper(sb.toString(), images);
	}
	
	public String getTitle() {
		return Translate.getInstance().get(TranslateKeys.ACTUAL_VS_BUDGETED_EXPENSES_TITLE);
	}
	
	private Map<Category, Long> getExpensesBetween(Date startDate, Date endDate){
		Vector<Transaction> transactions = TransactionController.getTransactions(startDate, endDate);
		Map<Category, Long> categories = new HashMap<Category, Long>();
		
		//This map is where we store the totals for this time period.
		for (Category category : SourceController.getCategories()) {
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
	
	public DateRangeType getDateRangeType() {
		return DateRangeType.INTERVAL;
	}

	public String getDescription() {
		return TranslateKeys.GRAPH_DESCRIPTION_EXPENSE_ACTUAL_BUDGET.toString();
	}
}
