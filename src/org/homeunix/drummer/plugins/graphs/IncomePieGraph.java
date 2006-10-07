/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.graphs;

import java.awt.Font;
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
import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;
import org.homeunix.drummer.plugins.interfaces.BuddiGraphPlugin;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class IncomePieGraph implements BuddiGraphPlugin {

	public JPanel getGraphPanel(Date startDate, Date endDate) {
		DefaultPieDataset pieData = new DefaultPieDataset();
		
		Map<Category, Long> categories = getIncomeBetween(startDate, endDate);
		
		Vector<Category> cats = new Vector<Category>(categories.keySet());
		Collections.sort(cats);
		
		long totalIncome = 0;
		
		for (Category c : cats) {
			totalIncome += categories.get(c);
			
			if (categories.get(c) > 0)
				pieData.setValue(Translate.getInstance().get(c.toString()), new Double((double) categories.get(c) / 100.0));
		}
				
		JFreeChart chart = ChartFactory.createPieChart(
				Translate.getInstance().get(TranslateKeys.INCOME)
				+ " (" 
				+ Formatter.getInstance().getDateFormat().format(startDate)
				+ " - "
				+ Formatter.getInstance().getDateFormat().format(endDate)
				+ ") "
				+ PrefsInstance.getInstance().getPrefs().getCurrencySymbol()
				+ Formatter.getInstance().getDecimalFormat().format((double) totalIncome / 100.0),
				pieData,             // data
				true,               // include legend
				true,
				false
		);
		
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionOutlinesVisible(false);
		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		plot.setNoDataMessage("No data available");
		plot.setCircular(false);
		plot.setLabelGap(0.02);
		
		return new ChartPanel(chart);
	}
	
	public String getTitle() {
		return "";
	}
	
	private Map<Category, Long> getIncomeBetween(Date startDate, Date endDate){
		Vector<Transaction> transactions = DataInstance.getInstance().getTransactions(startDate, endDate);
		Map<Category, Long> categories = new HashMap<Category, Long>();
		
		//This map is where we store the totals for this time period.
		for (Category category : DataInstance.getInstance().getCategories()) {
			if (category.isIncome())
				categories.put(category, new Long(0));
		}
		
		for (Transaction transaction : transactions) {
			//Sum up the amounts for each category.
			if (transaction.getFrom() instanceof Category){
				Category c = (Category) transaction.getFrom();
				if (c.isIncome()){
					Long l = categories.get(c);
					l += transaction.getAmount();
					categories.put(c, l);
					Log.debug("Added a source");
				}
			}
			else if (transaction.getTo() instanceof Category){
				Category c = (Category) transaction.getTo();
				if (c.isIncome()){
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
		return TranslateKeys.INCOME_PIE_GRAPH.toString();
	}
}
