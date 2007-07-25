/*
 * Created on Oct 7, 2006 by wyatt
 */
package org.homeunix.drummer.plugins;

import java.io.File;
import java.util.Date;
import java.util.Vector;

import net.java.dev.SwingWorker;
import net.sourceforge.buddi.api.manager.APICommonHTMLHelper;
import net.sourceforge.buddi.api.plugin.BuddiGraphPlugin;
import net.sourceforge.buddi.api.plugin.BuddiPanelPlugin;
import net.sourceforge.buddi.api.plugin.BuddiReportPlugin;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.view.MainFrame;
import org.homeunix.thecave.moss.gui.JStatusDialog;
import org.homeunix.thecave.moss.util.DateFunctions;
import org.homeunix.thecave.moss.util.Log;

import edu.stanford.ejalbert.BrowserLauncher;

public class BuddiPluginHelper {
	public static void openNewPanelPluginWindow(final BuddiPanelPlugin plugin, final Date startDate, final Date endDate){
		final JStatusDialog status = new JStatusDialog(
				MainFrame.getInstance(),
				Translate.getInstance().get(plugin instanceof BuddiReportPlugin ? TranslateKeys.MESSAGE_GENERATING_REPORT : TranslateKeys.MESSAGE_GENERATING_GRAPH));

		status.openWindow();
		
		SwingWorker worker = new SwingWorker(){
			@Override
			public Object construct() {
				if (plugin instanceof BuddiReportPlugin){
					try {
						File index = APICommonHTMLHelper.createHTML(
								"report", 
								((BuddiReportPlugin) plugin).getReport(
										MainFrame.getInstance().getDataManager(),
										startDate, 
										endDate));
						BrowserLauncher bl = new BrowserLauncher(null);
						bl.openURLinBrowser(index.toURI().toURL().toString());
					}
					catch (Exception e){
						Log.error("Error making HTML: " + e);
						e.printStackTrace(Log.getPrintStream());
					}
				}
				else if (plugin instanceof BuddiGraphPlugin){
					try {
						File index = APICommonHTMLHelper.createHTML(
								"graph", 
								((BuddiGraphPlugin) plugin).getGraph(
										MainFrame.getInstance().getDataManager(),
										startDate, 
										endDate));
						BrowserLauncher bl = new BrowserLauncher(null);
						bl.openURLinBrowser(index.toURI().toURL().toString());
					}
					catch (Exception e){
						Log.error("Error making HTML: " + e);
						e.printStackTrace(Log.getPrintStream());
					}
				}
				else {
					Log.error("Incorrect plugin type: " + plugin.getClass().getName());
				}
				
				return null;
			}
			
			@Override
			public void finished() {
				status.closeWindow();
				super.finished();
			}
		};

		worker.start();
	}

	protected static Vector<DateChoice> getInterval() {
		Vector<DateChoice> intervals = new Vector<DateChoice>();

		intervals.add(null);
		intervals.add(new DateChoice(
				DateFunctions.getStartOfDay(DateFunctions.addDays(new Date(), -7)),
				DateFunctions.getEndOfDay(new Date()),
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_PAST_WEEK)
		));
		intervals.add(new DateChoice(
				DateFunctions.getStartOfDay(DateFunctions.addDays(new Date(), -14)),
				DateFunctions.getStartOfDay(DateFunctions.addDays(new Date(), 0)),
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_PAST_FORTNIGHT)
		));
		intervals.add(new DateChoice(
				DateFunctions.getStartOfDay(DateFunctions.getStartOfMonth(new Date())),
				DateFunctions.getEndOfDay(DateFunctions.getEndOfMonth(new Date())),
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_THIS_MONTH)
		));
		intervals.add(new DateChoice(
				DateFunctions.addMonths(DateFunctions.getStartOfMonth(new Date()), -1),
				DateFunctions.addMonths(DateFunctions.getEndOfMonth(new Date()), -1),
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_LAST_MONTH)
		));
		intervals.add(new DateChoice(
				DateFunctions.getStartOfDay(DateFunctions.getStartOfQuarter(new Date(), 0)),
				DateFunctions.getStartOfDay(DateFunctions.getEndOfQuarter(new Date(), 0)),
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_THIS_QUARTER)
		));
		intervals.add(new DateChoice(
				DateFunctions.getStartOfDay(DateFunctions.getStartOfQuarter(new Date(), -1)),
				DateFunctions.getStartOfDay(DateFunctions.getEndOfQuarter(new Date(), -1)),
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_LAST_QUARTER)
		));
		intervals.add(new DateChoice(
				DateFunctions.getStartOfDay(DateFunctions.getStartOfYear(new Date())),
				DateFunctions.getEndOfDay(DateFunctions.getEndOfYear(new Date())),
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_THIS_YEAR)
		));

		intervals.add(new DateChoice(
				DateFunctions.addYears(DateFunctions.getStartOfYear(new Date()), -1),
				DateFunctions.addYears(DateFunctions.getEndOfYear(new Date()), -1),
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_LAST_YEAR)
		));
		intervals.add(new DateChoice(
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_OTHER)
		));

		return intervals;
	}

	protected static Vector<DateChoice> getEndOnly() {
		Vector<DateChoice> endDates = new Vector<DateChoice>();

		endDates.add(null);
		endDates.add(new DateChoice(
				null,
				DateFunctions.getEndOfDay(new Date()),
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_TODAY)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.getEndOfDay(DateFunctions.addDays(new Date(), -1)),
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_YESTERDAY)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.getEndOfDay(DateFunctions.addDays(new Date(), -8)),
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_LAST_WEEK)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.getEndOfDay(DateFunctions.addDays(new Date(), -30)),
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_LAST_MONTH)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.getEndOfDay(DateFunctions.addDays(new Date(), -365)),
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_LAST_YEAR)
		));
		endDates.add(new DateChoice(
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_OTHER)
		));


		return endDates;
	}

	protected static Vector<DateChoice> getStartOnly() {
		Vector<DateChoice> startDates = new Vector<DateChoice>();


		startDates.add(null);
		startDates.add(new DateChoice(
				DateFunctions.addMonths(DateFunctions.getStartOfMonth(new Date()), -1),
				null,
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_ONE_MONTH)
		));
		startDates.add(new DateChoice(
				DateFunctions.addMonths(DateFunctions.getStartOfMonth(new Date()), -2),
				null,
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_TWO_MONTHS)
		));
		startDates.add(new DateChoice(
				DateFunctions.addMonths(DateFunctions.getStartOfMonth(new Date()), -6),
				null,
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_SIX_MONTHS)
		));

		startDates.add(new DateChoice(
				DateFunctions.getStartOfYear(new Date()),
				null,
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_YEAR)
		));

		startDates.add(new DateChoice(
				Translate.getInstance().get(TranslateKeys.PLUGIN_FILTER_OTHER)
		));

		return startDates;
	}

	protected static class DateChoice {
		private Date startDate;
		private Date endDate;
		private String name;
		private boolean custom = false;

		public DateChoice(String name){
			this.name = name;
			custom = true;
		}

		public DateChoice(Date startDate, Date endDate, String name){
			this.startDate = startDate;
			this.endDate = endDate;
			this.name = name;
		}

		public String toString(){
			return name;
		}

		public Date getStartDate(){
			return startDate;
		}

		public Date getEndDate(){
			return endDate;
		}

		public boolean isCustom(){
			return custom;
		}
	}	
}
