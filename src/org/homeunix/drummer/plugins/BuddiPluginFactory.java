/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.plugins;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.homeunix.drummer.controller.MainBuddiFrame;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.components.CustomDateDialogLayout;


public class BuddiPluginFactory {

	public static enum DateRangeType {
		INTERVAL,
		START_ONLY,
		END_ONLY
	};

	public static JPanel getPluginLaunchPane(String className){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		final BuddiReportPlugin reportPlugin;
		
		// Make an instance of this plug-in
		try {
			Object o = Class.forName(className).newInstance();

			// Check for correct interface 
			if (!(o instanceof BuddiReportPlugin)){
				throw new Exception("Not of type BuddiReportPlugin: " + o.getClass().getName());
			}
			
			reportPlugin = (BuddiReportPlugin) o;
			
			Vector<DateChoice> dateChoices;
			if (reportPlugin.getDateRangeType().equals(DateRangeType.INTERVAL))
				dateChoices = getInterval();
			else if (reportPlugin.getDateRangeType().equals(DateRangeType.START_ONLY))
				dateChoices = getStartOnly();
			else if (reportPlugin.getDateRangeType().equals(DateRangeType.END_ONLY))
				dateChoices = getEndOnly();
			else
				dateChoices = new Vector<DateChoice>();
 
			final JComboBox dateSelect = new JComboBox(dateChoices);
			dateSelect.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					//Find out which item was clicked on
					Object o = dateSelect.getSelectedItem();
					
					//If the object was a date choice, access the encoded dates
					if (o instanceof DateChoice){
						DateChoice choice = (DateChoice) o;
						
						//As long as the choice is not custom, our job is easy
						if (!choice.isCustom()){
							//Launch a new reports window with given parameters
							BuddiPluginFactory.openNewPluginWindow(
									reportPlugin, 
									choice.getStartDate(), 
									choice.getEndDate());
						}
						//If they want a custom window, it's a little harder...
						else{
							new CustomDateDialogLayout(
									MainBuddiFrame.getInstance(),
									reportPlugin
							).openWindow();
						}
					}
				}
			});
			
			panel.add(new JLabel(reportPlugin.getDescription()));
			panel.add(dateSelect);
			return panel;
		}
		catch (Exception ie){
			Log.error("Error loading plugin.  Ensure that it is referenced from your classpath.  Complete error: " + ie);
		}
		
		return null;
	}


	public static void openNewPluginWindow(BuddiPlugin plugin, final Date startDate, final Date endDate){
		if (plugin instanceof BuddiReportPlugin)
			new BuddiReportFrame((BuddiReportPlugin) plugin, startDate, endDate);
		
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
}
