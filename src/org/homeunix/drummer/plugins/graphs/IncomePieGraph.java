/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.graphs;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;
import org.homeunix.drummer.plugins.interfaces.BuddiGraphPlugin;
import org.homeunix.drummer.view.HTMLExportHelper;
import org.homeunix.drummer.view.HTMLExportHelper.HTMLWrapper;
import org.homeunix.thecave.moss.images.ImageFunctions;
import org.homeunix.thecave.moss.util.Log;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class IncomePieGraph implements BuddiGraphPlugin {

	public HTMLWrapper getGraph(Date startDate, Date endDate) {
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
//				Translate.getInstance().get(TranslateKeys.INCOME)
//				+ " (" 
//				+ Formatter.getInstance().getDateFormat().format(startDate)
//				+ " - "
//				+ Formatter.getInstance().getDateFormat().format(endDate)
//				+ ") "
//				+ Translate.getFormattedCurrency(totalIncome, false),
				"",
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
		
		JPanel graphPanel = new ChartPanel(chart);
		JFrame tempFrame = new JFrame();
		tempFrame.add(graphPanel);
		tempFrame.setBackground(Color.WHITE);
		tempFrame.pack();
		
		StringBuilder sb = HTMLExportHelper.getHtmlHeader(
				Translate.getInstance().get(TranslateKeys.EXPENSE_ACTUAL_BUDGET), 
				null, 
				startDate, 
				endDate);

		sb.append("<img src='graph.png' />");
		sb.append(HTMLExportHelper.getHtmlFooter());
		
		Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
		images.put("graph.png", ImageFunctions.getImageFromComponent(graphPanel));
		
		return new HTMLWrapper(sb.toString(), images);
	}
	
	public String getTitle() {
		return Translate.getInstance().get(TranslateKeys.INCOME_PIE_GRAPH_TITLE);
	}
	
	private Map<Category, Long> getIncomeBetween(Date startDate, Date endDate){
		Vector<Transaction> transactions = TransactionController.getTransactions(startDate, endDate);
		Map<Category, Long> categories = new HashMap<Category, Long>();
		
		//This map is where we store the totals for this time period.
		for (Category category : SourceController.getCategories()) {
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
