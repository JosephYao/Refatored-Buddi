/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.plugins;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.roydesign.ui.JScreenMenuItem;

import org.homeunix.drummer.controller.MainBuddiFrame;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractFrame;
import org.homeunix.drummer.view.GraphFrameLayout;
import org.homeunix.drummer.view.ReportFrameLayout;
import org.homeunix.drummer.view.components.CustomDateDialog;


public class BuddiPluginFactory {

	public static enum DateRangeType {
		INTERVAL,
		START_ONLY,
		END_ONLY
	};
	
	public static JScreenMenuItem getPluginMenuItem(String className, final AbstractFrame frame){
		JScreenMenuItem menuItem = null;
		final BuddiMenuPlugin plugin;
		
		// Make an instance of this plug-in
		try {
			Object o = Class.forName(className).newInstance();

			// Check for correct interface 
			if (!(o instanceof BuddiMenuPlugin)){
				throw new Exception("Not of type BuddiMenuPlugin: " + o.getClass().getName());
			}
			
			plugin = (BuddiMenuPlugin) o;
			
			menuItem = new JScreenMenuItem(plugin.getDescription());
			
//			for (Class c : plugin.getCorrectWindows()) {
//				if (c != null){
//					Log.warning("Added class " + c);
////					menuItem.addUserFrame(c);
//				}
//				else{
//					Log.warning("null deteccted in UserFrame");
//				}
//			}
			
			//This is where the user's custom code is actually run
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if (plugin instanceof BuddiExportPlugin)
						((BuddiExportPlugin) plugin).exportData(frame);
					else if (plugin instanceof BuddiImportPlugin)
						((BuddiImportPlugin) plugin).importData(frame);
				}
			});
		}
		catch (Exception ie){
			Log.error("Error loading plugin.  Ensure that it is referenced from your classpath.  Complete error: " + ie);
		}

		return menuItem;

	}
	
	public static JPanel getPanelPluginLauncher(String className){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		final BuddiPanelPlugin plugin;
		
		// Make an instance of this plug-in
		try {
			Object o = Class.forName(className).newInstance();

			// Check for correct interface 
			if (!(o instanceof BuddiPanelPlugin)){
				throw new Exception("Not of type BuddiPanelPlugin: " + o.getClass().getName());
			}
			
			plugin = (BuddiPanelPlugin) o;
			
			//Select the correct options for the dropdown, based on the plugin
			Vector<DateChoice> dateChoices;
			if (plugin.getDateRangeType().equals(DateRangeType.INTERVAL))
				dateChoices = getInterval();
			else if (plugin.getDateRangeType().equals(DateRangeType.START_ONLY))
				dateChoices = getStartOnly();
			else if (plugin.getDateRangeType().equals(DateRangeType.END_ONLY))
				dateChoices = getEndOnly();
			else
				dateChoices = new Vector<DateChoice>();
 
			//Get the combobox 
			final JComboBox dateSelect = new JComboBox(dateChoices);
			dateSelect.setPreferredSize(new Dimension(Math.max(150, dateSelect.getPreferredSize().width), dateSelect.getPreferredSize().height));
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
							BuddiPluginFactory.openNewPanelPluginWindow(
									plugin, 
									choice.getStartDate(), 
									choice.getEndDate()
							);
						}
						//If they want a custom window, it's a little harder...
						else{
							new CustomDateDialog(
									MainBuddiFrame.getInstance(),
									plugin
							).openWindow();
						}
					}
					
					dateSelect.setSelectedItem(null);
				}
			});
			
			panel.add(new JLabel(Translate.getInstance().get(plugin.getDescription())));
			panel.add(dateSelect);
			return panel;
		}
		catch (Exception ie){
			Log.error("Error loading plugin.  Ensure that it is referenced from your classpath.  Complete error: " + ie);
		}
		
		return null;
	}


	public static void openNewPanelPluginWindow(BuddiPanelPlugin plugin, final Date startDate, final Date endDate){
		if (plugin instanceof BuddiReportPlugin)
			new ReportFrameLayout((BuddiReportPlugin) plugin, startDate, endDate).openWindow();
		else if (plugin instanceof BuddiGraphPlugin)
			new GraphFrameLayout((BuddiGraphPlugin) plugin, startDate, endDate).openWindow();
		else
			Log.error("Incorrect plugin type: " + plugin.getClass().getName());
		
	}
	
	public static StringBuffer getHtmlHeader(TranslateKeys reportTitle, Date startDate, Date endDate){
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"); 
		sb.append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<title>");
		sb.append(Translate.getInstance().get(reportTitle));
		sb.append("</title>\n");
		
		sb.append("<style type=\"text/css\">\n");
		sb.append(".red { color: red; }");
		sb.append("h1 { font-size: large; }");
		sb.append("table.main { background-color: black; width: 100%; }\n");
		sb.append("table.transactions { background-color: white; width: 100%; padding-left: 3em; }\n");
		sb.append("table.main tr { padding-bottom: 1em; }\n");
		sb.append("table.main th { width: 20%; background-color: #DDE}\n");
		sb.append("table.main td { width: 20%; background-color: #EEF}\n");
		sb.append("table.transactions td { width: 30%; background-color: white}\n");
		sb.append("</style>\n");
		
		sb.append("</head>\n");
		sb.append("<body>\n");
		
		sb.append("<h1>");
		sb.append(Translate.getInstance().get(reportTitle));
		sb.append(" ("); 
		sb.append(Formatter.getInstance().getDateFormat().format(startDate));
		sb.append(" - ");
		sb.append(Formatter.getInstance().getDateFormat().format(endDate));
		sb.append(") ");
		sb.append("</h1>\n");
		
		return sb;
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
