/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.builtin.report;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.PluginReportDateRangeChoices;
import org.homeunix.thecave.buddi.plugin.api.BuddiReportPlugin;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableBudgetCategory;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableDocument;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableTransaction;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlHelper;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlPage;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.Version;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class ExpensesPieGraph extends BuddiReportPlugin {
	
	public static final long serialVersionUID = 0;
	
	@Override
	public HtmlPage getReport(ImmutableDocument model, Date startDate, Date endDate) {
		DefaultPieDataset pieData = new DefaultPieDataset();
		
		Map<ImmutableBudgetCategory, Long> categories = getExpensesBetween(model, startDate, endDate);
		
		List<ImmutableBudgetCategory> cats = new LinkedList<ImmutableBudgetCategory>(categories.keySet());
		Collections.sort(cats);
		
		long totalExpenses = 0;
		
		for (ImmutableBudgetCategory c : cats) {
			totalExpenses += categories.get(c);
			if (categories.get(c) > 0)
				pieData.setValue(TextFormatter.getTranslation(c.toString()), new Double((double) categories.get(c) / 100.0));
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
				
		StringBuilder sb = HtmlHelper.getHtmlHeader(
				TextFormatter.getTranslation(BuddiKeys.GRAPH_TITLE_EXPENSE_PIE_GRAPH), 
				TextFormatter.getFormattedCurrency(totalExpenses), 
				startDate, 
				endDate);

		sb.append("<img class='center_img' src='graph.png' />");
		sb.append(HtmlHelper.getHtmlFooter());
		
		Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
		images.put("graph.png", 
				chart.createBufferedImage(
						Math.min(Toolkit.getDefaultToolkit().getScreenSize().width - 200, 1000), 
						Toolkit.getDefaultToolkit().getScreenSize().height - 100));
		
		return new HtmlPage(sb.toString(), images);
	}
		
	private Map<ImmutableBudgetCategory, Long> getExpensesBetween(ImmutableDocument model, Date startDate, Date endDate){
		List<ImmutableTransaction> transactions = model.getTransactions(startDate, endDate);
		Map<ImmutableBudgetCategory, Long> categories = new HashMap<ImmutableBudgetCategory, Long>();
		
		//This map is where we store the totals for this time period.
		for (ImmutableBudgetCategory category : model.getBudgetCategories()) {
			if (!category.isIncome())
				categories.put(category, new Long(0));
		}
		
		for (ImmutableTransaction transaction : transactions) {
			//Sum up the amounts for each category.
			if (transaction.getFrom() instanceof ImmutableBudgetCategory){
				ImmutableBudgetCategory c = (ImmutableBudgetCategory) transaction.getFrom();
				if (!c.isIncome()){
					Long l = categories.get(c);
					l += transaction.getAmount();
					categories.put(c, l);
					Log.debug("Added a source");
				}
			}
			else if (transaction.getTo() instanceof ImmutableBudgetCategory){
				ImmutableBudgetCategory c = (ImmutableBudgetCategory) transaction.getTo();
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

	@Override
	public PluginReportDateRangeChoices getDateRangeChoice() {
		return PluginReportDateRangeChoices.INTERVAL;
	}
	
	public String getDescription() {
		return BuddiKeys.GRAPH_DESCRIPTION_EXPENSE_PIE_GRAPH.toString();
	}

	public String getName() {
		return BuddiKeys.GRAPH_TITLE_EXPENSE_PIE_GRAPH.toString();
	}

	public boolean isPluginActive() {
		return true;
	}

	public Version getMaximumVersion() {
		return null;
	}
	
	public Version getMinimumVersion() {
		return null;
	}
}
