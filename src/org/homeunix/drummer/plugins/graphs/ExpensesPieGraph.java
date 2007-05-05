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

import net.sourceforge.buddi.api.manager.DataManager;
import net.sourceforge.buddi.api.plugin.BuddiGraphPlugin;

import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;
import org.homeunix.drummer.view.HTMLExportHelper;
import org.homeunix.drummer.view.HTMLExportHelper.HTMLWrapper;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.Version;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class ExpensesPieGraph extends BuddiGraphPlugin {
	
	public static final long serialVersionUID = 0;
	
	public HTMLWrapper getGraph(Date startDate, Date endDate) {
		DefaultPieDataset pieData = new DefaultPieDataset();
		
		Map<Category, Long> categories = getExpensesBetween(startDate, endDate);
		
		Vector<Category> cats = new Vector<Category>(categories.keySet());
		Collections.sort(cats);
		
		long totalExpenses = 0;
		
		for (Category c : cats) {
			totalExpenses += categories.get(c);
			if (categories.get(c) > 0)
				pieData.setValue(Translate.getInstance().get(c.toString()), new Double((double) categories.get(c) / 100.0));
		}
				
		JFreeChart chart = ChartFactory.createPieChart(
				"",
				pieData,             // data
				true,               // include legend
				true,
				false
		);
		
		chart.setBackgroundPaint(Color.WHITE);
		chart.setBorderStroke(new BasicStroke(0));
				
		StringBuilder sb = HTMLExportHelper.getHtmlHeader(
				Translate.getInstance().get(TranslateKeys.GRAPH_TITLE_EXPENSE_PIE_GRAPH), 
				null, 
				startDate, 
				endDate);

		sb.append("<img class='center_img' src='graph.png' />");
		sb.append(HTMLExportHelper.getHtmlFooter());
		
		Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
		images.put("graph.png", 
				chart.createBufferedImage(
						Toolkit.getDefaultToolkit().getScreenSize().width - 200, 
						Toolkit.getDefaultToolkit().getScreenSize().height - 100));
		
		return new HTMLWrapper(sb.toString(), images);
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
		return TranslateKeys.GRAPH_DESCRIPTION_EXPENSE_PIE_GRAPH.toString();
	}

	public String getTitle() {
		return TranslateKeys.GRAPH_TITLE_EXPENSE_PIE_GRAPH.toString();
	}

	public boolean isPluginActive(DataManager dataManager) {
		return true;
	}
	public Version getAPIVersion() {
//		return new Version("2.3.4");
		return null;
	}
}
