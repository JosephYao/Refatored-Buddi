/*
 * Created on Oct 7, 2006 by wyatt
 */
package org.homeunix.drummer.plugins;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.plugins.interfaces.BuddiGraphPlugin;
import org.homeunix.drummer.plugins.interfaces.BuddiPanelPlugin;
import org.homeunix.drummer.plugins.interfaces.BuddiReportPlugin;
import org.homeunix.drummer.view.HTMLExportHelper;
import org.homeunix.thecave.moss.util.DateUtil;
import org.homeunix.thecave.moss.util.Log;

import edu.stanford.ejalbert.BrowserLauncher;

public class BuddiPluginHelper {
	public static void openNewPanelPluginWindow(BuddiPanelPlugin plugin, final Date startDate, final Date endDate){
		if (plugin instanceof BuddiReportPlugin){
			try {
				File index = HTMLExportHelper.createHTML("graph", ((BuddiReportPlugin) plugin).getReport(startDate, endDate));
				BrowserLauncher bl = new BrowserLauncher(null);
				bl.openURLinBrowser(index.toURI().toURL().toString());
			}
			catch (Exception e){
				Log.error("Error making HTML: " + e);
			}

//			WindowAttributes wa = PrefsInstance.getInstance().getPrefs().getWindows().getReportsWindow();
//			Dimension dimension = new Dimension(wa.getWidth(), wa.getHeight());
//			Point point = new Point(wa.getX(), wa.getY());
//			
//			ReportFrame rfl = new ReportFrame((BuddiReportPlugin) plugin, startDate, endDate);
//			rfl.openWindow(dimension, point);
//			rfl.requestFocusInWindow();
		}
		else if (plugin instanceof BuddiGraphPlugin){
			try {
				File index = HTMLExportHelper.createHTML("graph", ((BuddiGraphPlugin) plugin).getGraph(startDate, endDate));
				BrowserLauncher bl = new BrowserLauncher(null);
				bl.openURLinBrowser(index.toURI().toURL().toString());
			}
			catch (Exception e){
				Log.error("Error making HTML: " + e);
			}

//			WindowAttributes wa = PrefsInstance.getInstance().getPrefs().getWindows().getGraphsWindow();
//			Dimension dimension = new Dimension(wa.getWidth(), wa.getHeight());
//			Point point = new Point(wa.getX(), wa.getY());

//			GraphFrame gfl = new GraphFrame((BuddiGraphPlugin) plugin, startDate, endDate);
//			gfl.openWindow(dimension, point);
//			gfl.requestFocusInWindow();
		}
		else {
			Log.error("Incorrect plugin type: " + plugin.getClass().getName());
		}

	}

	protected static Vector<DateChoice> getInterval() {
		Vector<DateChoice> intervals = new Vector<DateChoice>();

		intervals.add(null);
		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getNextNDay(new Date(), -7)),
				DateUtil.getEndOfDay(new Date()),
				Translate.getInstance().get(TranslateKeys.PAST_WEEK)
		));
		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getNextNDay(new Date(), -14)),
				DateUtil.getStartOfDay(DateUtil.getNextNDay(new Date(), 0)),
				Translate.getInstance().get(TranslateKeys.PAST_FORTNIGHT)
		));
		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getBeginOfMonth(new Date(), 0)),
				DateUtil.getEndOfDay(DateUtil.getEndOfMonth(new Date(), 0)),
				Translate.getInstance().get(TranslateKeys.THIS_MONTH)
		));
		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getBeginOfMonth(new Date(), -1)),
				DateUtil.getEndOfDay(DateUtil.getEndOfMonth(new Date(), -1)),
				Translate.getInstance().get(TranslateKeys.LAST_MONTH)
		));
		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getBeginOfQuarter(new Date(), 0)),
				DateUtil.getStartOfDay(DateUtil.getEndOfQuarter(new Date(), 0)),
				Translate.getInstance().get(TranslateKeys.THIS_QUARTER)
		));
		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getBeginOfQuarter(new Date(), -1)),
				DateUtil.getStartOfDay(DateUtil.getEndOfQuarter(new Date(), -1)),
				Translate.getInstance().get(TranslateKeys.LAST_QUARTER)
		));
		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getBeginOfYear(Calendar.getInstance().get(Calendar.YEAR))),
				DateUtil.getEndOfDay(DateUtil.getEndOfYear(Calendar.getInstance().get(Calendar.YEAR))),
				Translate.getInstance().get(TranslateKeys.THIS_YEAR)
		));

		intervals.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getBeginOfYear(Calendar.getInstance().get(Calendar.YEAR) - 1)),
				DateUtil.getEndOfDay(DateUtil.getEndOfYear(Calendar.getInstance().get(Calendar.YEAR) - 1)),
				Translate.getInstance().get(TranslateKeys.LAST_YEAR)
		));
		intervals.add(new DateChoice(
				Translate.getInstance().get(TranslateKeys.OTHER)
		));

		return intervals;
	}

	protected static Vector<DateChoice> getEndOnly() {
		Vector<DateChoice> endDates = new Vector<DateChoice>();

		endDates.add(null);
		endDates.add(new DateChoice(
				null,
				DateUtil.getEndOfDay(new Date()),
				Translate.getInstance().get(TranslateKeys.TODAY)
		));
		endDates.add(new DateChoice(
				null,
				DateUtil.getEndOfDay(DateUtil.getNextNDay(new Date(), -1)),
				Translate.getInstance().get(TranslateKeys.YESTERDAY)
		));
		endDates.add(new DateChoice(
				null,
				DateUtil.getEndOfDay(DateUtil.getNextNDay(new Date(), -8)),
				Translate.getInstance().get(TranslateKeys.LAST_WEEK)
		));
		endDates.add(new DateChoice(
				null,
				DateUtil.getEndOfDay(DateUtil.getNextNDay(new Date(), -30)),
				Translate.getInstance().get(TranslateKeys.LAST_MONTH)
		));
		endDates.add(new DateChoice(
				null,
				DateUtil.getEndOfDay(DateUtil.getNextNDay(new Date(), -365)),
				Translate.getInstance().get(TranslateKeys.LAST_YEAR)
		));
		endDates.add(new DateChoice(
				Translate.getInstance().get(TranslateKeys.OTHER)
		));


		return endDates;
	}

	protected static Vector<DateChoice> getStartOnly() {
		Vector<DateChoice> startDates = new Vector<DateChoice>();


		startDates.add(null);
		startDates.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getNextNDay(new Date(), -30)),
				null,
				Translate.getInstance().get(TranslateKeys.ONE_MONTH)
		));
		startDates.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getNextNDay(new Date(), -60)),
				null,
				Translate.getInstance().get(TranslateKeys.TWO_MONTHS)
		));
		startDates.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getEndOfMonth(DateUtil.getStartOfDay(new Date()), -6)),
				null,
				Translate.getInstance().get(TranslateKeys.SIX_MONTHS)
		));

		startDates.add(new DateChoice(
				DateUtil.getStartOfDay(DateUtil.getEndOfMonth(DateUtil.getStartOfDay(new Date()), -12)),
				null,
				Translate.getInstance().get(TranslateKeys.YEAR)
		));

		startDates.add(new DateChoice(
				Translate.getInstance().get(TranslateKeys.OTHER)
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

	public static enum DateRangeType {
		INTERVAL,
		START_ONLY,
		END_ONLY
	}	
}
