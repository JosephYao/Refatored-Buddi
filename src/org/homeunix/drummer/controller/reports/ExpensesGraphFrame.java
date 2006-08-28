/*
 * Created on May 19, 2006 by wyatt
 */
package org.homeunix.drummer.controller.reports;

import java.awt.Font;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.view.AbstractBudgetFrame;
import org.homeunix.drummer.view.GraphFrameLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class ExpensesGraphFrame extends GraphFrameLayout {
	public static final long serialVersionUID = 0;
	
	public ExpensesGraphFrame(Date startDate, Date endDate){
		super(startDate, endDate);
	}
	
	@Override
	protected JPanel buildReport(Date startDate, Date endDate) {
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
				Translate.getInstance().get(TranslateKeys.EXPENSES)
				+ " (" 
				+ Formatter.getInstance().getDateFormat().format(startDate)
				+ " - "
				+ Formatter.getInstance().getDateFormat().format(endDate)
				+ ") "
				+ PrefsInstance.getInstance().getPrefs().getCurrencySymbol()
				+ Formatter.getInstance().getDecimalFormat().format((double) totalExpenses / 100.0),
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
