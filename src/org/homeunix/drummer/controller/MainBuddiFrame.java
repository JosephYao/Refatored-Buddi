/*
 * Created on May 16, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.roydesign.app.Application;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Schedule;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.AbstractFrame;
import org.homeunix.drummer.view.ListPanelLayout;
import org.homeunix.drummer.view.MainBuddiFrameLayout;
import org.homeunix.thecave.moss.util.DateUtil;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.SwingWorker;

import edu.stanford.ejalbert.BrowserLauncher;


public class MainBuddiFrame extends MainBuddiFrameLayout {
	public static final long serialVersionUID = 0;

	/**
	 * Get an instance of the main Buddi window.
	 * @return MainBuddiFrame - Main Buddi window instance
	 */
	public static MainBuddiFrame getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static MainBuddiFrame instance = new MainBuddiFrame();

		public static void restartProgram(){
			instance = new MainBuddiFrame();
		}
	}

	/**
	 * Forces a restart of the program.
	 */
	public static void restartProgram(){
		for(Frame frame : TransactionsFrame.getFrames()){
			frame.dispose();
		}

		getInstance().dispose();

		SingletonHolder.instance = null;
		SingletonHolder.restartProgram();
		getInstance().openWindow();
	}

	/**
	 * Constructor for the main Buddi window.
	 */
	private MainBuddiFrame(){
		super();

		//Check that there are no scheduled transactions which should be happening...
		updateScheduledTransactions();
		updateContent();

		DataInstance.getInstance().calculateAllBalances();
	}

	@Override
	public AbstractFrame openWindow() {
		initActions();

		startUpdateCheck();
		startVersionCheck();

		return super.openWindow();
	}

	@Override
	protected AbstractFrame initActions() {
		getInstance().addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent arg0) {
				if (Const.DEVEL) Log.debug("Main window resized");

				MainBuddiFrame.getInstance().savePosition();

				super.componentResized(arg0);
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
				if (Const.DEVEL) Log.debug("Main Window hidden");

				MainBuddiFrame.getInstance().savePosition();

				super.componentHidden(arg0);
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				if (Const.DEVEL) Log.debug("Main Window moved");

				MainBuddiFrame.getInstance().savePosition();

				super.componentMoved(e);
			}

		});

		if (Buddi.isMac()){
			getInstance().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			Application.getInstance().addReopenApplicationListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if (!MainBuddiFrame.getInstance().isVisible())
						MainBuddiFrame.getInstance().setVisible(true);
				}
			});
		}
		else{
			getInstance().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		}

		return getInstance();
	}

	@Override
	protected AbstractFrame initContent() {
		return getInstance();
	}

	@Override
	public AbstractFrame updateButtons() {
		return getInstance();
	}

	@Override
	public AbstractFrame updateContent() {
		//Update the title to reflect current data file...
		this.setTitle(Translate.getInstance().get(TranslateKeys.BUDDI) + " - " + PrefsInstance.getInstance().getPrefs().getDataFile());

		getAccountListPanel().updateContent();
		getCategoryListPanel().updateContent();

		return getInstance();
	}

	@Override
	public Component getPrintedComponent() {
		if (getSelectedPanel() instanceof ListPanelLayout){
			ListPanelLayout listPanel = (ListPanelLayout) getSelectedPanel();
			return listPanel.getTree();
		}
		else
			return null;
	}

	public void savePosition(){
		PrefsInstance.getInstance().checkWindowSanity();

		PrefsInstance.getInstance().getPrefs().getWindows().getMainWindow().setHeight(getInstance().getHeight());
		PrefsInstance.getInstance().getPrefs().getWindows().getMainWindow().setWidth(getInstance().getWidth());

		PrefsInstance.getInstance().getPrefs().getWindows().getMainWindow().setX(getInstance().getX());
		PrefsInstance.getInstance().getPrefs().getWindows().getMainWindow().setY(getInstance().getY());

		PrefsInstance.getInstance().savePrefs();
	}

	/**
	 * Starts a thread which checks the Internet for any new versions.
	 */
	public void startUpdateCheck(){
		if (PrefsInstance.getInstance().getPrefs().isEnableUpdateNotifications()){
			SwingWorker updateWorker = new SwingWorker(){

				@Override
				public Object construct() {
					try{
						Properties versions = new Properties();
						URL mostRecentVersion = new URL(Const.PROJECT_URL + Const.VERSION_FILE);

						versions.load(mostRecentVersion.openStream());

						if (Const.VERSION.compareTo(versions.get(Const.BRANCH).toString()) < 0){
							return versions.get(Const.BRANCH);
						}
					}
					catch (MalformedURLException mue){
						Log.error(mue);
					}
					catch (IOException ioe){
						Log.error(ioe);
					}

					return null;
				}

				@Override
				public void finished() {
					if (get() != null){
						String[] buttons = new String[2];
						buttons[0] = Translate.getInstance().get(TranslateKeys.DOWNLOAD);
						buttons[1] = Translate.getInstance().get(TranslateKeys.CANCEL);

						int reply = JOptionPane.showOptionDialog(
								MainBuddiFrame.getInstance(), 
								Translate.getInstance().get(TranslateKeys.NEW_VERSION_MESSAGE)
								+ " " + get() + "\n"
								+ Translate.getInstance().get(TranslateKeys.NEW_VERSION_MESSAGE_2),
								Translate.getInstance().get(TranslateKeys.NEW_VERSION),
								JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE,
								null,
								buttons,
								buttons[0]);

						if (reply == JOptionPane.YES_OPTION){
							String fileLocation;
							if (Const.BRANCH.equals(Const.STABLE))
								fileLocation = Const.DOWNLOAD_URL_STABLE;
							else
								fileLocation = Const.DOWNLOAD_URL_UNSTABLE;

							if (Buddi.isMac())
								fileLocation += Const.DOWNLOAD_URL_DMG;
							else if (Buddi.isWindows())
								fileLocation += Const.DOWNLOAD_URL_ZIP;
							else
								fileLocation += Const.DOWNLOAD_URL_TGZ;

							try{
								BrowserLauncher bl = new BrowserLauncher(null);
								bl.openURLinBrowser(fileLocation);
							}
							catch (Exception e){
								Log.error(e);
							}
						}
					}

					super.finished();
				}
			};

			if (Const.DEVEL) Log.debug("Starting update checking...");
			updateWorker.start();
		}
	}

	/**
	 * Checks if getInstance() version has been run before, and if not, displays any messages that show on first run. 
	 */
	private void startVersionCheck(){
		if (!PrefsInstance.getInstance().getLastVersionRun().equals(Const.VERSION)){
			PrefsInstance.getInstance().updateVersion();

			SwingWorker worker = new SwingWorker() {

				@Override
				public Object construct() {
					return null;
				}

				@Override
				public void finished() {
					String[] buttons = new String[2];
					buttons[0] = Translate.getInstance().get(TranslateKeys.DONATE);
					buttons[1] = Translate.getInstance().get(TranslateKeys.NOT_NOW);

					int reply = JOptionPane.showOptionDialog(
							MainBuddiFrame.getInstance(), 
							Translate.getInstance().get(TranslateKeys.DONATE_MESSAGE),
							Translate.getInstance().get(TranslateKeys.DONATE_HEADER),
							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE,
							null,
							buttons,
							buttons[0]);

					if (reply == JOptionPane.YES_OPTION){
						try{
							BrowserLauncher bl = new BrowserLauncher(null);
							bl.openURLinBrowser(Const.DONATE_URL);
						}
						catch (Exception e){
							Log.error(e);
						}
					}

					super.finished();
				}
			};

			worker.start();
		}
	}

	/**
	 * Runs through the list of scheduled transactions, and adds any which
	 * show be executed to the apropriate transacactions list.
+ 	 * Checks for the frequency type and based on it finds if a transaction is scheduled for a date
+ 	 * that has gone past.
	 */
	public void updateScheduledTransactions(){
		//Update any scheduled transactions
		final Date today = DateUtil.getEndOfDay(new Date());
		//We specify a GregorianCalendar because we make some assumptions
		// about numbering of months, etc that may break if we 
		// use the default calendar for the locale.  It's not the
		// prettiest code, but it works.  Perhaps we can change
		// it to be cleaner later on...
		final GregorianCalendar tempCal = new GregorianCalendar();

		for (Schedule s : DataInstance.getInstance().getScheduledTransactionsBeforeToday()) {
			if (Const.DEVEL) Log.info("Looking at scheduled transaction " + s.getScheduleName());

			Date tempDate = s.getLastDateCreated();

			//Temp date is where we will start looping from.  If it is
			// null, we need to init it to a sane value.
			if (tempDate == null)
				tempDate = s.getStartDate();


			tempDate = DateUtil.getStartOfDay(tempDate);

			//The transaction is scheduled for a date before today and before the EndDate 
			while (tempDate.before(today) && tempDate.before(s.getEndDate())) {
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
				if (s.getFrequencyType().equals(TranslateKeys.MONTHLY_BY_DATE.toString())
						&& s.getScheduleDay() == tempCal.get(Calendar.DAY_OF_MONTH)){
					todayIsTheDay = true;
				}
				//If we are using the Monthly by Day of Week,
				// we check if the given day (Sunday, Monday, etc) is equal to the
				// scheduleDay, and if the given day is within the first week.
				// FYI, we store Sunday == 0, even though Calendar.SUNDAY == 1.  Thus,
				// we add 1 to our stored day before comparing it.
				else if (s.getFrequencyType().equals(TranslateKeys.MONTHLY_BY_DAY_OF_WEEK.toString())
						&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)
						&& tempCal.get(Calendar.DAY_OF_MONTH) <= 7){
					todayIsTheDay = true;
				}
				//If we are using Weekly frequency, we only need to compare
				// the number of the day.
				// FYI, we store Sunday == 0, even though Calendar.SUNDAY == 1.  Thus,
				// we add 1 to our stored day before comparing it.
				else if (s.getFrequencyType().equals(TranslateKeys.WEEKLY.toString())
						&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)){
					todayIsTheDay = true;
				}
				//Every day - it's obvious enough even for a monkey!
				else if (s.getFrequencyType().equals(TranslateKeys.EVERY_DAY.toString())){
					todayIsTheDay = true;
				}
				//Every weekday - all days but Saturday and Sunday.
				else if (s.getFrequencyType().equals(TranslateKeys.EVERY_WEEKDAY.toString())
						&& (tempCal.get(Calendar.DAY_OF_WEEK) < Calendar.SATURDAY)
						&& (tempCal.get(Calendar.DAY_OF_WEEK) > Calendar.SUNDAY)){
					todayIsTheDay = true;
				}
				//To make this one clearer, we do it in two passes.
				// First, we check the frequency type and the day.
				// If these match, we do out bit bashing to determine
				// if the week is correct.
				else if (s.getFrequencyType().equals(TranslateKeys.MULTIPLE_WEEKS_EVERY_MONTH.toString())
						&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)){
					int week = s.getScheduleWeek();
					//The week mask should return 1 for the first week (day 1 - 7), 
					// 2 for the second week (day 8 - 14), 4 for the third week (day 15 - 21),
					// and 8 for the fourth week (day 22 - 28).  We then AND it with 
					// the scheduleWeek to determine if this week matches the criteria
					// or not.  
					int weekMask = 2 ^ ((tempCal.get(Calendar.DAY_OF_MONTH) + 1) / 7) + 1;
					if ((week & weekMask) != 0){
						todayIsTheDay = true;
					}
				}
				//To make this one clearer, we do it in two passes.
				// First, we check the frequency type and the day.
				// If these match, we do out bit bashing to determine
				// if the month is correct.
				else if (s.getFrequencyType().equals(TranslateKeys.MULTIPLE_MONTHS_EVERY_YEAR.toString())
						&& s.getScheduleDay() == tempCal.get(Calendar.DAY_OF_MONTH)){
					int months = s.getScheduleMonth();
					//The month mask should be 2 ^ MONTH NUMBER,
					// where January == 0.
					// i.e. 1 for January, 4 for March, 2048 for December.
					int monthMask = 2 ^ tempCal.get(Calendar.MONTH);
					if ((months & monthMask) != 0){
						todayIsTheDay = true;
					}
				}

				//If one of the above rules matches, we will copy the
				// scheduled transaction into the transactions list
				// at the given day.
				if (todayIsTheDay){
					Transaction t = DataInstance.getInstance().getDataModelFactory().createTransaction();

					t.setDate(tempDate);
					t.setDescription(s.getDescription());
					t.setAmount(s.getAmount());
					t.setTo(s.getTo());
					t.setFrom(s.getFrom());
					t.setMemo(s.getMemo());
					t.setNumber(s.getNumber());
					t.setScheduled(true);

					s.setLastDateCreated(DateUtil.getEndOfDay(tempDate));

					DataInstance.getInstance().addTransaction(t);
					if (Const.DEVEL) Log.info("Added scheduled transaction " + t + " to transaction list on date " + t.getDate());
				}

				tempDate = DateUtil.getNextDay(tempDate);
			}
		}
	}
}