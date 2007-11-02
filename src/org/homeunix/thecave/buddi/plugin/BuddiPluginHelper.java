/*
 * Created on Oct 7, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.plugin;

import java.io.File;
import java.util.Date;
import java.util.Vector;

import net.java.dev.SwingWorker;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.PluginRangeFilters;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.plugin.api.BuddiReportPlugin;
import org.homeunix.thecave.buddi.plugin.api.model.impl.ImmutableDocumentImpl;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.MossStatusDialog;
import org.homeunix.thecave.moss.util.DateFunctions;
import org.homeunix.thecave.moss.util.Log;

import edu.stanford.ejalbert.BrowserLauncher;

public class BuddiPluginHelper {
	public static void openReport(final MainFrame frame, final BuddiReportPlugin report, final Date startDate, final Date endDate){
		final MossStatusDialog status = new MossStatusDialog(
				frame,
				TextFormatter.getTranslation(BuddiKeys.MESSAGE_GENERATING_REPORT));

		try {
			status.openWindow();
		}
		catch (WindowOpenException woe){}

		SwingWorker worker = new SwingWorker(){
			@Override
			public Object construct() {
				try {
					File index = report.getReport(
									new ImmutableDocumentImpl((Document) frame.getDocument()),
									frame,
									startDate, 
									endDate).createHTML("report");
					BrowserLauncher bl = new BrowserLauncher(null);
					bl.openURLinBrowser(index.toURI().toURL().toString());
				}
				catch (Exception e){
					Log.error("Error making HTML: " + e);
					e.printStackTrace(Log.getPrintStream());
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

	public static Vector<DateChoice> getInterval() {
		Vector<DateChoice> intervals = new Vector<DateChoice>();

//		intervals.add(null);
		intervals.add(new DateChoice(
				DateFunctions.getStartOfWeek(new Date()),
				DateFunctions.getEndOfWeek(new Date()),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_THIS_WEEK)
		));
		intervals.add(new DateChoice(
				DateFunctions.getStartOfWeek(DateFunctions.addDays(new Date(), -7)),
				DateFunctions.getEndOfWeek(DateFunctions.addDays(new Date(), -7)),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_LAST_WEEK)
		));
		intervals.add(new DateChoice(
				DateFunctions.getStartOfDay(DateFunctions.getStartOfMonth(new Date())),
				DateFunctions.getEndOfDay(DateFunctions.getEndOfMonth(new Date())),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_THIS_MONTH)
		));
		intervals.add(new DateChoice(
				DateFunctions.getStartOfMonth(DateFunctions.addMonths(new Date(), -1)),
				DateFunctions.getEndOfMonth(DateFunctions.addMonths(new Date(), -1)),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_LAST_MONTH)
		));
		intervals.add(new DateChoice(
				DateFunctions.getStartOfQuarter(new Date()),
				DateFunctions.getEndOfQuarter(new Date()),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_THIS_QUARTER)
		));
		intervals.add(new DateChoice(
				DateFunctions.addQuarters(DateFunctions.getStartOfQuarter(new Date()), -1),
				DateFunctions.addQuarters(DateFunctions.getEndOfQuarter(new Date()), -1),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_LAST_QUARTER)
		));
		intervals.add(new DateChoice(
				DateFunctions.getStartOfDay(DateFunctions.getStartOfYear(new Date())),
				DateFunctions.getEndOfDay(DateFunctions.getEndOfYear(new Date())),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_THIS_YEAR)
		));
		intervals.add(new DateChoice(
				DateFunctions.getStartOfDay(DateFunctions.getStartOfYear(new Date())),
				DateFunctions.getEndOfDay(new Date()),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_THIS_YEAR_TO_DATE)
		));
		intervals.add(new DateChoice(
				DateFunctions.addYears(DateFunctions.getStartOfYear(new Date()), -1),
				DateFunctions.addYears(DateFunctions.getEndOfYear(new Date()), -1),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_LAST_YEAR)
		));
		intervals.add(new DateChoice(
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_OTHER)
		));

		return intervals;
	}

	public static Vector<DateChoice> getEndOnly() {
		Vector<DateChoice> endDates = new Vector<DateChoice>();

//		endDates.add(null);
		endDates.add(new DateChoice(
				null,
				DateFunctions.getEndOfDay(new Date()),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_TODAY)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.getEndOfDay(DateFunctions.addDays(new Date(), -1)),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_YESTERDAY)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.getEndOfDay(DateFunctions.addDays(new Date(), -7)),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_ONE_WEEK_AGO)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.addMonths(new Date(), -1),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_ONE_MONTH_AGO)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.addYears(new Date(), -1),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_ONE_YEAR_AGO)
		));
		endDates.add(new DateChoice(
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_OTHER)
		));


		return endDates;
	}

	public static Vector<DateChoice> getStartOnly() {
		Vector<DateChoice> startDates = new Vector<DateChoice>();


//		startDates.add(null);
		startDates.add(new DateChoice(
				DateFunctions.addMonths(DateFunctions.getStartOfMonth(new Date()), -1),
				null,
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_ONE_MONTH)
		));
		startDates.add(new DateChoice(
				DateFunctions.addMonths(DateFunctions.getStartOfMonth(new Date()), -2),
				null,
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_TWO_MONTHS)
		));
		startDates.add(new DateChoice(
				DateFunctions.addMonths(DateFunctions.getStartOfMonth(new Date()), -6),
				null,
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_SIX_MONTHS)
		));

		startDates.add(new DateChoice(
				DateFunctions.addMonths(DateFunctions.getStartOfMonth(new Date()), -12),
				null,
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_YEAR)
		));

		startDates.add(new DateChoice(
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_OTHER)
		));

		return startDates;
	}

	public static class DateChoice {
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
