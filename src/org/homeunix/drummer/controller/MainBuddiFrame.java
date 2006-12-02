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
		final Calendar tempCal = Calendar.getInstance();
		final Calendar startCal = Calendar.getInstance();

		//		System.out.println(new Date(97,10,1));
		for (Schedule s : DataInstance.getInstance().getScheduledTransactionsBeforeToday()) {
			System.out.println(s.getScheduleName());

			Date tempDate = s.getLastDateCreated();
			Date startDate = s.getStartDate();

			if (tempDate == null)
				tempDate = s.getStartDate();
			tempDate = DateUtil.getStartOfDay(tempDate);
			startDate = DateUtil.getStartOfDay(startDate);//added by Nicky



			startCal.setTime(startDate);

			System.out.println("Start Date: "+startCal.get(Calendar.MONTH));

			//The EndDate of the transaction is been tested if the transaction is even valid anymore
			if(s.getEndDate()!=null)
			{
				//The transaction is scheduled for a date before today and before the EndDate 
				while (tempDate.before(today) && tempDate.before(s.getEndDate())) 
				{
					if (Const.DEVEL)
						Log.debug("Trying date " + tempDate);
					tempCal.setTime(tempDate);
					// If multiple weeks in a day has been selected, The weeks are set and unset according to the 
					// selection
					int tempscheduleweek = s.getScheduleWeek();
					int isFirstWeekSet = 0, isSecondWeekSet = 0, isThirdWeekSet = 0, isFourthWeekSet = 0;
					isFirstWeekSet = tempscheduleweek % 2;
					isSecondWeekSet = (tempscheduleweek / 2) % 2;
					isThirdWeekSet = (tempscheduleweek / 4) % 2;
					isFourthWeekSet = (tempscheduleweek / 8) % 2;

					//To find the gaps of multiple months between the transaction
					//the start dates and the current dates data were extracted
					int current_date_year = tempCal.get(Calendar.YEAR);
					int start_date_year = startCal.get(Calendar.YEAR);
					int current_date_month = tempCal.get(Calendar.MONTH);
					int start_date_month = startCal.get(Calendar.MONTH);

					int year_offset, month_offset;
					//The year offset and month offset were calculated to determine 
					//the monthly difference between the current dates and start of
					//scheduled transaction
					year_offset = current_date_year - start_date_year;

					month_offset = 12 * (year_offset - 1) + 12
					- (start_date_month - current_date_month);
					//////////////////////////////////////////////
					System.out.println("Years: " + "curr: " + current_date_year
							+ "year offset: " + year_offset + "month offset:"
							+ month_offset);
					System.out.println("strmon: " + start_date_month
							+ "curr mon: " + current_date_month
							+ "get sch month: " + s.getScheduleMonth());
					System.out.println("bits set are" + isFirstWeekSet + " "
							+ isSecondWeekSet + " " + isThirdWeekSet + " "
							+ isFourthWeekSet);
					//tempCal.get(Calendar.MONTH)
					System.out.println("DEKH "+s.getScheduleDay()+" "+tempCal.get(Calendar.DAY_OF_WEEK)+" month date:"+tempCal.get(Calendar.DAY_OF_MONTH));

					/*
					 *Checks : Frequency type = Week then check the day if matching with the scheduled day
					 *		   Frequency type = Month then check for date if matching with the scheduled date
					 *         Frequency type = Day of a month then check for day if matching with the scheduled day
					 *		   Frequency type = Every Weekday then check if the day is not a sunday or a saturday
					 *		   Frequency type = Multiple Weeks every Month then check for the day as well as the week in the month 
					 *							when it is scheduled
					 *		   Frequency type = Multiple Months every year then check if the transaction is scheduled 
					 *							for the month and the date
					 */

					if ((s.getFrequencyType().equals(TranslateKeys.WEEK.toString())
							&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK))
							|| (s.getFrequencyType().equals(TranslateKeys.MONTH.toString())
									&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_MONTH))
									|| (s.getFrequencyType().equals(TranslateKeys.DAY_MONTH.toString())
											&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)
											&& tempCal.get(Calendar.DAY_OF_MONTH) <= 7)
											||(s.getFrequencyType().equals(TranslateKeys.EVERY_WEEKDAY.toString())
													&& (tempCal.get(Calendar.DAY_OF_WEEK) < 7)
													&& (tempCal.get(Calendar.DAY_OF_WEEK) > 1))
													||(s.getFrequencyType().equals(TranslateKeys.MULTIPLE_WEEKS_EVERY_MONTH.toString())
															&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)
															&& (((isFirstWeekSet==1)&&(tempCal.get(Calendar.DAY_OF_MONTH) <= 7))
																	||((isSecondWeekSet==1)&&(tempCal.get(Calendar.DAY_OF_MONTH) > 7)&&(tempCal.get(Calendar.DAY_OF_MONTH) <=14))
																	||((isThirdWeekSet==1)&&(tempCal.get(Calendar.DAY_OF_MONTH) >14)&&(tempCal.get(Calendar.DAY_OF_MONTH) <=21))
																	||((isFourthWeekSet==1)&&(tempCal.get(Calendar.DAY_OF_MONTH) >21)&&(tempCal.get(Calendar.DAY_OF_MONTH) <=28))))
																	||(s.getFrequencyType().equals(TranslateKeys.MULTIPLE_MONTHS_EVERY_YEAR.toString())
																			&&(month_offset%s.getScheduleMonth()==0)
																			&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_MONTH) 
																	)){ 

						Transaction t = DataInstance.getInstance().getDataModelFactory().createTransaction();

						t.setDate(tempDate);
						t.setDescription(s.getDescription());
						t.setAmount(s.getAmount());
						t.setTo(s.getTo());
						t.setFrom(s.getFrom());
						t.setMemo(s.getMemo());
						t.setNumber(s.getNumber());
						t.setScheduled(true);

						//						if (s.getLastDateCreated() == null 
						//						|| s.getLastDateCreated().before(DateUtil.getStartOfDay(DateUtil.getNextDay(tempDate)))){
						s.setLastDateCreated(DateUtil.getStartOfDay(DateUtil.getNextDay(tempDate)));
						//						DataInstance.getInstance().saveDataModel();
						//						}

						DataInstance.getInstance().addTransaction(t);
						if (Const.DEVEL) Log.debug("Added scheduled transaction " + t);
					}

					tempDate = DateUtil.getNextDay(tempDate);
				}
			}
		}
	}
}