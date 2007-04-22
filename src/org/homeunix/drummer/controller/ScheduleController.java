/*
 * Created on Apr 7, 2007 by wyatt
 */
package org.homeunix.drummer.controller;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.ModelFactory;
import org.homeunix.drummer.model.Schedule;
import org.homeunix.drummer.model.Source;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.view.TransactionsFrame;
import org.homeunix.thecave.moss.util.DateUtil;
import org.homeunix.thecave.moss.util.Log;

public class ScheduleController {
	/**
	 * Returns all scheduled transactions
	 * @return A vector of all scheduled transactions
	 */
	@SuppressWarnings("unchecked")
	public static Vector<Schedule> getScheduledTransactions(){
		Vector<Schedule> v = new Vector<Schedule>(DataInstance.getInstance().getDataModel().getAllTransactions().getScheduledTransactions());
		Collections.sort(v);
		return v;
	}

	/**
	 * Returns scheduled transactions with to or from set to given source
	 * @return A vector of scheduled transactions
	 */
	@SuppressWarnings("unchecked")
	public static Vector<Schedule> getScheduledTransactions(Source source){
		Vector<Schedule> v = new Vector<Schedule>();
		for (Object o : DataInstance.getInstance().getDataModel().getAllTransactions().getScheduledTransactions()) {
			Schedule s = (Schedule) o;
			if (s.getTo().equals(source) || s.getFrom().equals(source)){
				v.add(s);
			}
		}
		Collections.sort(v);
		return v;
	}

	
	/**
	 * Adds a new schedule to the model.  Since there are a number of fields
	 * which MUST be set, we prompt for all of them, instead of relying on the
	 * calling code to set them all.
	 * 
	 * The required objects for this are not obvious at first, and may
	 * require some interpretation.  Plugin developers: if you need to
	 * use this method, check the offical Buddi code which calls it
	 * to see the correct methods and values. 
	 * 
	 * @param name Human readable name (which will show up in the list)
	 * @param startDate Date which the schedule is effective
	 * @param endDate Date which the schedule expires (not currently used)
	 * @param frequencyType The type of frequency.  The options for this are 
	 * in TranslateKeys; unfortunately, there is no good way of checking which is 
	 * used for FrequencyType and which is not.  You can look in MainBuddiFrame.updateScheduledTransactions()
	 * for a list of which ones we check for.
	 * @param scheduleDay The day the schedule is one.  The meanings of the values 
	 * for this will differ depending on frequencyType.
	 * @param scheduleWeek Used for multiple weeks in a month option
	 * @param scheduleMonth Used for multiple months in a yeah option
	 * @param transaction The transaction to base new scheduled transactions off of
	 */
	@SuppressWarnings("unchecked")
	public static void addSchedule(String name, Date startDate, Date endDate, String frequencyType, Integer scheduleDay, Integer scheduleWeek, Integer scheduleMonth, String message, Transaction transaction){
		Schedule s = ModelFactory.eINSTANCE.createSchedule();
		s.setScheduleName(name);
		s.setStartDate(startDate);
		s.setEndDate(endDate);
		s.setFrequencyType(frequencyType);
		s.setScheduleDay(scheduleDay);
		s.setScheduleWeek(scheduleWeek);
		s.setScheduleMonth(scheduleMonth);
		s.setMessage(message);
		if (transaction != null){
			s.setAmount(transaction.getAmount());
			s.setDescription(transaction.getDescription());
			s.setNumber(transaction.getNumber());
			s.setMemo(transaction.getMemo());
			s.setTo(transaction.getTo());
			s.setFrom(transaction.getFrom());
			s.setCleared(transaction.isCleared());
			s.setReconciled(transaction.isReconciled());
		}

		DataInstance.getInstance().getDataModel().getAllTransactions().getScheduledTransactions().add(s);
	}

	/**
	 * Removes a schedule from the data model.
	 * @param s
	 */
	public static void removeSchedule(Schedule s){
		DataInstance.getInstance().getDataModel().getAllTransactions().getScheduledTransactions().remove(s);
	}

	/**
	 * Returns all scheduled transactions who meet the following criteria: 
	 * 
	 * 1) Start date is before today
	 * 2) Last date created is before today (or is not yet set).
	 * 
	 * This is used at startup when determining which scheduled transactions
	 * we need to check and possibly add.
	 * @return
	 */
	public static Vector<Schedule> getScheduledTransactionsBeforeToday(){
		Vector<Schedule> v = getScheduledTransactions();
		Vector<Schedule> newV = new Vector<Schedule>();

		for (Schedule schedule : v) {
			if (schedule.getStartDate().before(DateUtil.getStartOfDay(new Date()))
					&& (schedule.getLastDateCreated() == null 
							|| schedule.getLastDateCreated().before(DateUtil.getStartOfDay(new Date()))))
				newV.add(schedule);
		}

		return newV;
	}
	
	/**
	 * Runs through the list of scheduled transactions, and adds any which
	 * show be executed to the apropriate transacactions list.
+ 	 * Checks for the frequency type and based on it finds if a transaction is scheduled for a date
+ 	 * that has gone past.
	 */
	public static void checkForScheduledActions(){
		//Update any scheduled transactions
		final Date today = DateUtil.getEndOfDay(new Date());
		//We specify a GregorianCalendar because we make some assumptions
		// about numbering of months, etc that may break if we 
		// use the default calendar for the locale.  It's not the
		// prettiest code, but it works.  Perhaps we can change
		// it to be cleaner later on...
		final GregorianCalendar tempCal = new GregorianCalendar();

		for (Schedule s : getScheduledTransactionsBeforeToday()) {
			if (Const.DEVEL) Log.info("Looking at scheduled transaction " + s.getScheduleName());

			Date tempDate = s.getLastDateCreated();

			//Temp date is where we will start looping from.
			if (tempDate == null){
				//If it is null, we need to init it to a sane value.
				tempDate = s.getStartDate();
			}
			else {
				//We start one day after the last day, to avoid repeats.  
				// See bug #1641937 for more details.
				tempDate = DateUtil.getNextDay(tempDate);
			}
			
			Date lastDayCreated = (Date) tempDate.clone();

			tempDate = DateUtil.getStartOfDay(tempDate);

			if (Const.DEVEL){
				Log.debug("tempDate = " + tempDate);
				Log.debug("startDate = " + s.getStartDate());
			}

			//The transaction is scheduled for a date before today and before the EndDate 
			while (tempDate.before(today)) {
				if (Const.DEVEL) Log.debug("Trying date " + tempDate);

				//We use a Calendar instead of a Date object for comparisons
				// because the Calendar interface is much nicer.
				tempCal.setTime(tempDate);

				boolean todayIsTheDay = false;

				//We check each type of schedule, and if it matches,
				// we set todayIsTheDay to true.  We could do it 
				// all in one huge if statement, but that is very
				// hard to read and maintain.

				//If we are using the Monthly by Date frequency, 
				// we only check if the given day is equal to the
				// scheduled day.
				if (s.getFrequencyType().equals(TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE.toString())
						&& s.getScheduleDay() == tempCal.get(Calendar.DAY_OF_MONTH)){
					todayIsTheDay = true;
				}
				//If we are using the Monthly by Day of Week,
				// we check if the given day (Sunday, Monday, etc) is equal to the
				// scheduleDay, and if the given day is within the first week.
				// FYI, we store Sunday == 0, even though Calendar.SUNDAY == 1.  Thus,
				// we add 1 to our stored day before comparing it.
				else if (s.getFrequencyType().equals(TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DAY_OF_WEEK.toString())
						&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)
						&& tempCal.get(Calendar.DAY_OF_MONTH) <= 7){
					todayIsTheDay = true;
				}
				//If we are using Weekly frequency, we only need to compare
				// the number of the day.
				// FYI, we store Sunday == 0, even though Calendar.SUNDAY == 1.  Thus,
				// we add 1 to our stored day before comparing it.
				else if (s.getFrequencyType().equals(TranslateKeys.SCHEDULE_FREQUENCY_WEEKLY.toString())
						&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)){
					todayIsTheDay = true;
				}
				//If we are using BiWeekly frequency, we need to compare
				// the number of the day as well as ensure that there is one
				// week between each scheduled transaction.
				// FYI, we store Sunday == 0, even though Calendar.SUNDAY == 1.  Thus,
				// we add 1 to our stored day before comparing it.
				else if (s.getFrequencyType().equals(TranslateKeys.SCHEDULE_FREQUENCY_BIWEEKLY.toString())
						&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)
						&& (DateUtil.daysBetween(lastDayCreated, tempDate) > 13)){
					todayIsTheDay = true;
					lastDayCreated = (Date) tempDate.clone();
				}
				//Every day - it's obvious enough even for a monkey!
				else if (s.getFrequencyType().equals(TranslateKeys.SCHEDULE_FREQUENCY_EVERY_DAY.toString())){
					todayIsTheDay = true;
				}
				//Every weekday - all days but Saturday and Sunday.
				else if (s.getFrequencyType().equals(TranslateKeys.SCHEDULE_FREQUENCY_EVERY_WEEKDAY.toString())
						&& (tempCal.get(Calendar.DAY_OF_WEEK) < Calendar.SATURDAY)
						&& (tempCal.get(Calendar.DAY_OF_WEEK) > Calendar.SUNDAY)){
					todayIsTheDay = true;
				}
				//To make this one clearer, we do it in two passes.
				// First, we check the frequency type and the day.
				// If these match, we do our bit bashing to determine
				// if the week is correct.
				else if (s.getFrequencyType().equals(TranslateKeys.SCHEDULE_FREQUENCY_MULTIPLE_WEEKS_EVERY_MONTH.toString())
						&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)){
					if (Const.DEVEL) {
						Log.debug("We are looking at day " + tempCal.get(Calendar.DAY_OF_WEEK) + ", which matches s.getScheduleDay() which == " + s.getScheduleDay());
						Log.debug("s.getScheduleWeek() == " + s.getScheduleWeek());
					}
					int week = s.getScheduleWeek();
					//The week mask should return 1 for the first week (day 1 - 7), 
					// 2 for the second week (day 8 - 14), 4 for the third week (day 15 - 21),
					// and 8 for the fourth week (day 22 - 28).  We then AND it with 
					// the scheduleWeek to determine if this week matches the criteria
					// or not.
					int weekNumber = tempCal.get(Calendar.DAY_OF_WEEK_IN_MONTH) - 1;
					int weekMask = (int) Math.pow(2, weekNumber);
					if (Const.DEVEL){
						Log.debug("The week number is " + weekNumber + ", the week mask is " + weekMask + ", and the day of week in month is " + tempCal.get(Calendar.DAY_OF_WEEK_IN_MONTH));
					}
					if ((week & weekMask) != 0){
						if (Const.DEVEL) Log.info("The date " + tempCal.getTime() + " matches the requirements.");
						todayIsTheDay = true;
					}
				}
				//To make this one clearer, we do it in two passes.
				// First, we check the frequency type and the day.
				// If these match, we do our bit bashing to determine
				// if the month is correct.
				else if (s.getFrequencyType().equals(TranslateKeys.SCHEDULE_FREQUENCY_MULTIPLE_MONTHS_EVERY_YEAR.toString())
						&& s.getScheduleDay() == tempCal.get(Calendar.DAY_OF_MONTH)){
					int months = s.getScheduleMonth();
					//The month mask should be 2 ^ MONTH NUMBER,
					// where January == 0.
					// i.e. 1 for January, 4 for March, 2048 for December.
					int monthMask = (int) Math.pow(2, tempCal.get(Calendar.MONTH));
					if ((months & monthMask) != 0){
						if (Const.DEVEL) Log.info("The date " + tempCal.getTime() + " matches the requirements.");
						todayIsTheDay = true;
					}
				}

				//If one of the above rules matches, we will copy the
				// scheduled transaction into the transactions list
				// at the given day.
				if (todayIsTheDay){
					if (Const.DEVEL) Log.debug("Setting last created date to " + tempDate);
					s.setLastDateCreated(DateUtil.getEndOfDay(tempDate));
					if (Const.DEVEL) Log.debug("Last created date to " + s.getLastDateCreated());

					if (s.getMessage() != null && s.getMessage().length() > 0){
						JOptionPane.showMessageDialog(
								null, 
								s.getMessage(), 
								Translate.getInstance().get(TranslateKeys.SCHEDULED_MESSAGE), 
								JOptionPane.INFORMATION_MESSAGE);
					}

					if (tempDate != null
							&& s.getDescription() != null) {
						Transaction t = ModelFactory.eINSTANCE.createTransaction();

						t.setDate(tempDate);
						t.setDescription(s.getDescription());
						t.setAmount(s.getAmount());
						t.setTo(s.getTo());
						t.setFrom(s.getFrom());
						t.setMemo(s.getMemo());
						t.setNumber(s.getNumber());
						t.setCleared(s.isCleared());
						t.setReconciled(s.isReconciled());
						t.setScheduled(true);

						TransactionsFrame.addToTransactionListModel(t);
						if (Const.DEVEL) Log.info("Added scheduled transaction " + t + " to transaction list on date " + t.getDate());
					}
					else {
						Log.info("There was an error adding a scheduled transaction:\n\tDate = " 
								+ tempDate
								+ "\n\tDescription = " 
								+ s.getDescription());
					}
					//We need to save to store the lastCreatedDate
					DataInstance.getInstance().saveDataFile();
				}

				tempDate = DateUtil.getNextDay(tempDate);
			}
		}
	}
}
