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
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableDocument;
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
		
		final ImmutableDocument model = new ImmutableDocumentImpl((Document) frame.getDocument());
		
		//We want to run the GUI in the EventDispatch thread.  If the user has not
		// cancelled, then we will proceed.
		if (!report.getReportGUI(model,
				frame,
				startDate, 
				endDate))
			return;
		
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
									model,
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
		return getInterval(null);
	}
	
	public static Vector<DateChoice> getInterval(Document model) {
		Vector<DateChoice> intervals = new Vector<DateChoice>();

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
				//Note that this is called when the data file is first opened, and it will not change until the main window is re-opened (i.e., 
				// we start Buddi again).  To try to ensure that we don't miss anything, including new entries, we go to the beginning of the 
				// year of the first transaction, and go to one week after today.  If people comment on this, we may expand the range,
				// or possibly update the ranges at plugin runtime.
				DateFunctions.getStartOfYear((model != null && model.getTransactions() != null && model.getTransactions().size() > 0 ? model.getTransactions().get(0).getDate() : new Date())),
				DateFunctions.getEndOfDay(DateFunctions.addDays(new Date(), 7)),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_ALL_TIME)
		));		
		intervals.add(new DateChoice(
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_OTHER)
		));

		return intervals;
	}

	public static Vector<DateChoice> getEndOnly() {
		return getEndOnly(null);
	}
	
	public static Vector<DateChoice> getEndOnly(Document model) {
		Vector<DateChoice> endDates = new Vector<DateChoice>();

		endDates.add(new DateChoice(
				null,
				DateFunctions.addYears(new Date(), -1),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_ONE_YEAR_AGO)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.addMonths(new Date(), -1),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_ONE_MONTH_AGO)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.getEndOfDay(DateFunctions.addDays(new Date(), -7)),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_ONE_WEEK_AGO)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.getEndOfDay(DateFunctions.addDays(new Date(), -1)),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_YESTERDAY)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.getEndOfDay(new Date()),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_TODAY)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.getEndOfDay(DateFunctions.addDays(new Date(), 7)),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_ONE_WEEK_FROM_NOW)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.getEndOfMonth(DateFunctions.addMonths(new Date(), 1)),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_END_OF_NEXT_MONTH)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.getEndOfYear(new Date()),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_END_OF_THIS_YEAR)
		));
		endDates.add(new DateChoice(
				null,
				DateFunctions.getEndOfYear(DateFunctions.addYears(new Date(), 1)),
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_END_OF_NEXT_YEAR)
		));
		endDates.add(new DateChoice(
				TextFormatter.getTranslation(PluginRangeFilters.PLUGIN_FILTER_OTHER)
		));


		return endDates;
	}

	public static Vector<DateChoice> getStartOnly() {
		return getStartOnly(null);
	}
	
	public static Vector<DateChoice> getStartOnly(Document model) {
		Vector<DateChoice> startDates = new Vector<DateChoice>();

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
