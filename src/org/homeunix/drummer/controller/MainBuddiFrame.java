/*
 * Created on May 16, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.Component;
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
import org.homeunix.drummer.controller.model.DataInstance;
import org.homeunix.drummer.controller.model.PrefsInstance;
import org.homeunix.drummer.model.Schedule;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.util.BrowserLauncher;
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.util.SwingWorker;
import org.homeunix.drummer.view.AbstractBudgetFrame;
import org.homeunix.drummer.view.ListPanelLayout;
import org.homeunix.drummer.view.MainBuddiFrameLayout;


public class MainBuddiFrame extends MainBuddiFrameLayout {
	public static final long serialVersionUID = 0;
	
	public static MainBuddiFrame getInstance() {
		return SingletonHolder.instance;
	}
	
	private static class SingletonHolder {
		private static MainBuddiFrame instance = new MainBuddiFrame();
	}
	
	private MainBuddiFrame(){
		super();
		
		//Check that there are no scheduled transactions which should be happening...
		updateScheduledTransactions();
		
		DataInstance.getInstance().calculateAllBalances();
		initActions();
		
		startUpdateCheck();
		startVersionCheck();
	}

	@Override
	protected AbstractBudgetFrame initActions() {
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent arg0) {
				Log.debug("Main window resized");
				
				MainBuddiFrame.this.savePosition();
								
				super.componentResized(arg0);
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
				Log.debug("Main Window hidden");
				
				MainBuddiFrame.this.savePosition();
				
				super.componentHidden(arg0);
			}
		});
		
		if (Buddi.isMac()){
			this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			Application.getInstance().addReopenApplicationListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if (!MainBuddiFrame.this.isVisible())
						MainBuddiFrame.this.setVisible(true);
				}
			});
		}
		else{
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		}
		
		return this;
	}

	@Override
	protected AbstractBudgetFrame initContent() {
		return this;
	}

	@Override
	public AbstractBudgetFrame updateButtons() {
		return this;
	}

	@Override
	public AbstractBudgetFrame updateContent() {
		getAccountListPanel().updateContent();
		getCategoryListPanel().updateContent();
		
		return this;
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
		
		PrefsInstance.getInstance().getPrefs().getMainWindow().setHeight(this.getHeight());
		PrefsInstance.getInstance().getPrefs().getMainWindow().setWidth(this.getWidth());
		
		PrefsInstance.getInstance().getPrefs().getMainWindow().setX(this.getX());
		PrefsInstance.getInstance().getPrefs().getMainWindow().setY(this.getY());
		
		PrefsInstance.getInstance().savePrefs();
	}

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
								MainBuddiFrame.this, 
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
							String fileLocation = Const.DOWNLOAD_URL + get();
							
							if (Buddi.isMac())
								fileLocation += Const.DOWNLOAD_URL_DMG;
							else
								fileLocation += Const.DOWNLOAD_URL_ZIP;
							
							try{
								BrowserLauncher.openURL(fileLocation);
							}
							catch (IOException ioe){
								Log.error(ioe);
							}
						}
					}
					
					super.finished();
				}
			};
			
			Log.debug("Starting update checking...");
			updateWorker.start();
		}
	}
	
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
							MainBuddiFrame.this, 
							Translate.getInstance().get(TranslateKeys.DONATE_MESSAGE),
							Translate.getInstance().get(TranslateKeys.DONATE_HEADER),
							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE,
							null,
							buttons,
							buttons[0]);
					
					if (reply == JOptionPane.YES_OPTION){
						try{
							BrowserLauncher.openURL(Const.DONATE_URL);
						}
						catch (IOException ioe){
							Log.error(ioe);
						}
					}
					
					super.finished();
				}
			};
			
			worker.start();
		}
	}
	
	public void updateScheduledTransactions(){
		//Update any scheduled transactions
		final Date today = DateUtil.getEndOfDay(new Date());
		final Calendar tempCal = Calendar.getInstance();
		for (Schedule s : DataInstance.getInstance().getScheduledTransactionsBeforeToday()) {
			Date tempDate = s.getLastDateCreated();
			if (tempDate == null)
				tempDate = s.getStartDate();
			tempDate = DateUtil.getStartOfDay(tempDate);
			
			while(tempDate.before(today)){
				Log.debug("Trying date " + tempDate);				
				tempCal.setTime(tempDate);
				
				//Log, extremely ugly expression to check if tempDay is a day on which we need to make a transaction.
				if ((s.getFrequencyType().equals(TranslateKeys.WEEK.toString())
						&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK))
						|| (s.getFrequencyType().equals(TranslateKeys.MONTH.toString())
								&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_MONTH))){
					Transaction t = DataInstance.getInstance().getDataModelFactory().createTransaction();

					t.setDate(tempDate);
					t.setDescription(s.getDescription());
					t.setAmount(s.getAmount());
					t.setTo(s.getTo());
					t.setFrom(s.getFrom());
					t.setMemo(s.getMemo());
					t.setNumber(s.getNumber());
					t.setScheduled(true);

//					if (s.getLastDateCreated() == null 
//					|| s.getLastDateCreated().before(DateUtil.getStartOfDay(DateUtil.getNextDay(tempDate)))){
					s.setLastDateCreated(DateUtil.getStartOfDay(DateUtil.getNextDay(tempDate)));
//					DataInstance.getInstance().saveDataModel();
//					}

					DataInstance.getInstance().addTransaction(t);
					Log.debug("Added scheduled transaction " + t);
				}

				tempDate = DateUtil.getNextDay(tempDate);
			}
		}
	}
}
