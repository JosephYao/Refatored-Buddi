/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.graphs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;
import org.homeunix.drummer.plugins.interfaces.BuddiGraphPlugin;
import org.homeunix.drummer.view.HTMLExportHelper;
import org.homeunix.drummer.view.HTMLExportHelper.HTMLWrapper;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.Version;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class NetWorthBreakdown implements BuddiGraphPlugin {

	public HTMLWrapper getGraph(Date startDate, Date endDate) {
		DefaultCategoryDataset barData = new DefaultCategoryDataset();
		
		Map<Account, Long> accounts = getAccountBalance(endDate);
						
		for (Account a : accounts.keySet()) {
			barData.addValue((Number) new Double(accounts.get(a) / 100.0), a.getName(), "");
		}
				
		JFreeChart chart = ChartFactory.createBarChart(
//				Translate.getInstance().get(TranslateKeys.NET_WORTH)
//				+ " (" 
//				+ Formatter.getInstance().getDateFormat().format(endDate)
//				+ ")",
				"",
				"", //Domain axis label
				"", //Range axis label
				barData,             // data
				PlotOrientation.VERTICAL,
				true,               // include legend
				true,
				false
		);
		
		chart.setBackgroundPaint(Color.WHITE);
		chart.setBorderStroke(new BasicStroke(0));
				
		StringBuilder sb = HTMLExportHelper.getHtmlHeader(
				Translate.getInstance().get(TranslateKeys.NET_WORTH), 
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
	
	public String getTitle() {
		return Translate.getInstance().get(TranslateKeys.GRAPH_TITLE_NET_WORTH_BREAKDOWN);
	}
	
	private Map<Account, Long> getAccountBalance(Date date){
		Map<Account, Long> map = new HashMap<Account, Long>();
		
		for (Account a : SourceController.getAccounts()) {
			if (a.getCreationDate().before(date))
				map.put(a, a.getStartingBalance());
			else
				map.put(a, 0l);
		}
		
		Vector<Transaction> transactions = TransactionController.getTransactions();
		
		for (Transaction transaction : transactions) {
			if (transaction.getDate().before(date)){
				//We are moving money *to* this account
				if (transaction.getTo() instanceof Account){
					Account a = (Account) transaction.getTo();
					map.put(a, map.get(a) + transaction.getAmount());
				}
				
				//We are moving money *from* this account
				if (transaction.getFrom() instanceof Account){
					Account a = (Account) transaction.getFrom();
					map.put(a, map.get(a) - transaction.getAmount());
				}
			}
			else{
				Log.debug("Not including transaction.");
			}
		}
		
		return map;
	}
	
	public DateRangeType getDateRangeType() {
		return DateRangeType.END_ONLY;
	}
	
	public String getDescription() {
		return TranslateKeys.GRAPH_DESCRIPTION_NET_WORTH_BREAKDOWN.toString();
	}
	
	public boolean isPluginActive() {
		return true;
	}
	public Version getMinimumVersion() {
		return new Version("2.3.4");
	}
}
