/*
 * Created on May 19, 2006 by wyatt
 */
package org.homeunix.drummer.controller.layout.reports;

import java.util.Date;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;

import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractBudgetFrame;
import org.homeunix.drummer.view.reports.layout.GraphFrameLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class NetWorthOverTimeGraphFrame extends GraphFrameLayout {
	public static final long serialVersionUID = 0;
	
	public NetWorthOverTimeGraphFrame(Date startDate){
		super(startDate, null);
	}
	
	@Override
	protected JPanel buildReport(Date startDate, Date endDate) {
		
		final int NUM_SAMPLES = 12;
		
		DefaultCategoryDataset barData = new DefaultCategoryDataset();
		
		Vector<Date> dates = new Vector<Date>();
		Date date = startDate;
		
		int numberOfDaysBetween = DateUtil.daysBetween(startDate, new Date());
		int daysBetweenReport = numberOfDaysBetween / NUM_SAMPLES;
		
		for (int i = 0; i < NUM_SAMPLES; i++){
			date = DateUtil.getNextNDay(startDate, i * daysBetweenReport);
			if (date.before(new Date()))
				dates.add(date);
			Log.debug("Added date: " + date);			
		}
		
//		for (int i = 0; date.before(new Date()); i++){
//			date = DateUtil.getEndOfMonth(startDate, i);
//			if (date.before(new Date()))
//				dates.add(date);
//			Log.debug("Added date: " + date);
//		}

		dates.add(new Date());
		
		for (Date d : dates) {
			Map<Account, Long> accounts = getAccountBalance(d);
			
			long total = 0;
			for (Account a : accounts.keySet()) {
//				barData.addValue((Number) new Double(accounts.get(a) / 100.0), Formatter.getInstance().getDateFormat().format(d), a.getName());
				total += accounts.get(a);
			}
			barData.addValue((Number) new Double(total / 100.0), Translate.getInstance().get(TranslateKeys.NET_WORTH), Formatter.getInstance().getShortDateFormat().format(d));
		}
		
		
		
		JFreeChart chart = ChartFactory.createLineChart(
				Translate.getInstance().get(TranslateKeys.NET_WORTH)				
				+ " (" 
				+ Formatter.getInstance().getDateFormat().format(startDate)
				+ " - "
				+ Formatter.getInstance().getDateFormat().format(new Date())
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
