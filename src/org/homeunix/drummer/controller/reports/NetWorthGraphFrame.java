/*
 * Created on May 19, 2006 by wyatt
 */
package org.homeunix.drummer.controller.reports;

import java.util.Date;
import java.util.Map;

import javax.swing.JPanel;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.view.AbstractFrame;
import org.homeunix.drummer.view.GraphFrameLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class NetWorthGraphFrame extends GraphFrameLayout {
	public static final long serialVersionUID = 0;
	
	public NetWorthGraphFrame(Date endDate){
		super(null, endDate);
	}
	
	@Override
	protected JPanel buildReport(Date startDate, Date endDate) {
		DefaultCategoryDataset barData = new DefaultCategoryDataset();
		
		Map<Account, Long> accounts = getAccountBalance(endDate);
						
		for (Account a : accounts.keySet()) {
			barData.addValue((Number) new Double(accounts.get(a) / 100.0), a.getName(), "");
		}
				
		JFreeChart chart = ChartFactory.createBarChart(
				Translate.getInstance().get(TranslateKeys.NET_WORTH)
				+ " (" 
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
	protected AbstractFrame initContent() {
		return this;
	}

	@Override
	public AbstractFrame updateButtons() {
		return this;
	}

	@Override
	public AbstractFrame updateContent() {
		return this;
	}
}
