/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.builtin.report;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.PluginReportDateRangeChoices;
import org.homeunix.thecave.buddi.plugin.api.BuddiReportPlugin;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableAccount;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableModel;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableTransaction;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlHelper;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlPage;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.moss.util.DateFunctions;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.Version;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class NetWorthOverTime extends BuddiReportPlugin {

	public static final long serialVersionUID = 0;

	@Override
	public HtmlPage getReport(ImmutableModel model, Date startDate, Date endDate) {
		final int NUM_SAMPLES = 12;

		DefaultCategoryDataset barData = new DefaultCategoryDataset();

		List<Date> dates = new LinkedList<Date>();
		Date date = startDate;

		int numberOfDaysBetween = DateFunctions.getDaysBetween(startDate, new Date(), false);
		int daysBetweenReport = numberOfDaysBetween / NUM_SAMPLES;

		for (int i = 0; i < NUM_SAMPLES; i++){
			date = DateFunctions.addDays(startDate, i * daysBetweenReport);
			if (date.before(new Date()))
				dates.add(date);
			Log.debug("Added date: " + date);			
		}

		dates.add(new Date());

		for (Date d : dates) {
			Map<ImmutableAccount, Long> accounts = getAccountBalance(model, d);

			long total = 0;
			for (ImmutableAccount a : accounts.keySet()) {
//				barData.addValue((Number) new Double(accounts.get(a) / 100.0), Formatter.getInstance().getDateFormat().format(d), a.getName());
				total += accounts.get(a);
			}
			barData.addValue((Number) new Double(total / 100.0), 
					TextFormatter.getTranslation(BuddiKeys.NET_WORTH), 
					Formatter.getDateFormat("MM/dd").format(d));
		}



		JFreeChart chart = ChartFactory.createLineChart(
				"", //Title
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

		StringBuilder sb = HtmlHelper.getHtmlHeader(
				TextFormatter.getTranslation(BuddiKeys.GRAPH_TITLE_NET_WORTH_OVER_TIME), 
				null, 
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

	public String getName() {
		return BuddiKeys.GRAPH_TITLE_NET_WORTH_OVER_TIME.toString();
	}

	private Map<ImmutableAccount, Long> getAccountBalance(ImmutableModel model, Date date){
		Map<ImmutableAccount, Long> map = new HashMap<ImmutableAccount, Long>();

		for (ImmutableAccount a : model.getAccounts()) {
			if (a.getEarliestDate().before(date))
				map.put(a, a.getStartingBalance());
			else
				map.put(a, 0l);
		}

		List<ImmutableTransaction> transactions = model.getTransactions();

		for (ImmutableTransaction transaction : transactions) {
			if (transaction.getDate().before(date)){
				//We are moving money *to* this account
				if (transaction.getTo() instanceof ImmutableAccount){
					ImmutableAccount a = (ImmutableAccount) transaction.getTo();
					map.put(a, map.get(a) + transaction.getAmount());
				}

				//We are moving money *from* this account
				if (transaction.getFrom() instanceof ImmutableAccount){
					ImmutableAccount a = (ImmutableAccount) transaction.getFrom();
					System.out.println(map.get(a));
					map.put(a, map.get(a) - transaction.getAmount());
				}
			}
			else{
				Log.debug("Not including transaction.");
			}
		}

		return map;
	}

	@Override
	public PluginReportDateRangeChoices getDateRangeChoice() {
		return PluginReportDateRangeChoices.START_ONLY;
	}

	public String getDescription() {
		return BuddiKeys.NETWORTH_LINE_GRAPH.toString();
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
