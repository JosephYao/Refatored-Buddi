/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Schedule;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.view.AbstractDialog;
import org.homeunix.drummer.view.ScheduleModifyDialogLayout;
import org.homeunix.thecave.moss.util.DateUtil;
import org.homeunix.thecave.moss.util.Log;

public class ScheduleModifyDialog extends ScheduleModifyDialogLayout {
	public static final long serialVersionUID = 0;
	
	private final Schedule schedule;

	public ScheduleModifyDialog(Schedule schedule){
		super(MainBuddiFrame.getInstance());
		
		this.schedule = schedule;
		startDateChooser.setEnabled(schedule == null);
		endDateChooser.setEnabled(schedule == null);
		frequencyPulldown.setEnabled(schedule == null);
		
		
		initContent();
		updateSchedulePulldown();
		transaction.updateContent();
		loadSchedule(schedule);
		schedulePulldown.setEnabled(schedule == null);
		firstWeek_dayButton.setEnabled(false);
		secondWeek_dayButton.setEnabled(false);
		thirdWeek_dayButton.setEnabled(false);
		fourthWeek_dayButton.setEnabled(false);
		monthFreqPulldown.setEnabled(false);
		
	}

	protected String getType(){
		return Translate.getInstance().get(TranslateKeys.ACCOUNT);
	}
		
	@Override
	protected AbstractDialog initActions() {
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println("sch week: "+getScheduleWeek());

				if (ScheduleModifyDialog.this.ensureInfoCorrect()){
					if (!ScheduleModifyDialog.this.startDateChooser.getDate().before(new Date())
							|| schedule != null		//If the schedule has already been defined, we won't bother people again 
							|| JOptionPane.showConfirmDialog(ScheduleModifyDialog.this, 
									Translate.getInstance().get(TranslateKeys.START_DATE_IN_THE_PAST), 
									Translate.getInstance().get(TranslateKeys.START_DATE_IN_THE_PAST_TITLE), 
									JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
						ScheduleModifyDialog.this.saveSchedule();
						MainBuddiFrame.getInstance().updateScheduledTransactions();
						TransactionsFrame.updateAllTransactionWindows();
						ScheduleModifyDialog.this.setVisible(false);
						ScheduleModifyDialog.this.dispose();
					}
					else
						if (Const.DEVEL) Log.debug("Cancelled from either start date in the past, or info not correct");
				}
				else {
					JOptionPane.showMessageDialog(ScheduleModifyDialog.this, 
							Translate.getInstance().get(TranslateKeys.SCHEDULED_NOT_ENOUGH_INFO),
							Translate.getInstance().get(TranslateKeys.SCHEDULED_NOT_ENOUGH_INFO_TITLE),
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ScheduleModifyDialog.this.setVisible(false);
				ScheduleModifyDialog.this.dispose();
			}
		});
		
		frequencyPulldown.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				ScheduleModifyDialog.this.updateSchedulePulldown();
			}
		});
		/*
		firstWeek_dayButton.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				System.out.println("first week status: "+ e.getStateChange());
			}
		});
		
		secondWeek_dayButton.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				System.out.println("second week status: "+ e.getStateChange());
			}
		});
		
		thirdWeek_dayButton.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				System.out.println("third week status: "+ e.getStateChange());
			}
		});
		
		fourthWeek_dayButton.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				System.out.println("fourth week status: "+ e.getStateChange());
			}
		});
		*/
		return this;
	}

	@Override
	protected AbstractDialog initContent() {
		updateContent();
		
		
		return this;
	}

	public AbstractDialog updateContent(){
		
		return this;
	}
	
	private void updateSchedulePulldown(){
		if (getFrequencyType().equals(TranslateKeys.WEEK.toString())){
			scheduleModel.removeAllElements();
			for (TranslateKeys day : Const.DAYS_IN_WEEK) {
				scheduleModel.addElement(day);	
			}
		}
		else if (getFrequencyType().equals(TranslateKeys.MONTH.toString())){
			scheduleModel.removeAllElements();
			Calendar c = Calendar.getInstance();
			c.setTime(DateUtil.getEndOfMonth(new Date(), 0));
			int daysInMonth = c.get(Calendar.DAY_OF_MONTH);
			for(int i = 1; i <= daysInMonth; i++){
				scheduleModel.addElement(i);
			}
		}
		/*Added the following for the four new features added in scheduling*/
		else if (getFrequencyType().equals(TranslateKeys.DAY_MONTH.toString())){
			schedulePulldown.setEnabled(true);
			firstWeek_dayButton.setEnabled(false);
			secondWeek_dayButton.setEnabled(false);
			thirdWeek_dayButton.setEnabled(false);
			fourthWeek_dayButton.setEnabled(false);
			monthFreqPulldown.setEnabled(false);
			
			scheduleModel.removeAllElements();
			for (TranslateKeys day2 : Const.FIRST_DAYS) {
				scheduleModel.addElement(day2);	
			}
		}
		else if (getFrequencyType().equals(TranslateKeys.EVERY_WEEKDAY.toString())){
			//scheduleModel.removeAllElements();
			schedulePulldown.setEnabled(false);
			firstWeek_dayButton.setEnabled(false);
			secondWeek_dayButton.setEnabled(false);
			thirdWeek_dayButton.setEnabled(false);
			fourthWeek_dayButton.setEnabled(false);
			monthFreqPulldown.setEnabled(false);
			
		}else if (getFrequencyType().equals(TranslateKeys.MULTIPLE_WEEKS_EVERY_MONTH.toString())){
			//scheduleModel.removeAllElements();
			schedulePulldown.setEnabled(true);
			firstWeek_dayButton.setEnabled(true);
			secondWeek_dayButton.setEnabled(true);
			thirdWeek_dayButton.setEnabled(true);
			fourthWeek_dayButton.setEnabled(true);
			monthFreqPulldown.setEnabled(false);
			
			scheduleModel.removeAllElements();
			for (TranslateKeys day : Const.DAYS_IN_WEEK) {
				scheduleModel.addElement(day);	
			}
			
		}else if (getFrequencyType().equals(TranslateKeys.MULTIPLE_MONTHS_EVERY_YEAR.toString())){
			//scheduleModel.removeAllElements();
			schedulePulldown.setEnabled(true);
			firstWeek_dayButton.setEnabled(false);
			secondWeek_dayButton.setEnabled(false);
			thirdWeek_dayButton.setEnabled(false);
			fourthWeek_dayButton.setEnabled(false);
			monthFreqPulldown.setEnabled(true);
			
			scheduleModel.removeAllElements();
			Calendar c = Calendar.getInstance();
			c.setTime(DateUtil.getEndOfMonth(new Date(), 0));
			int daysInMonth = c.get(Calendar.DAY_OF_MONTH);
			for(int i = 1; i <= daysInMonth; i++)
			{
				scheduleModel.addElement(i);
			}
			
		}
		/*added by Nicky*/
		else{
			Log.error("Unknown frequency type: " + getFrequencyType());
		}
	}
	
	private boolean ensureInfoCorrect(){
		
		//System.out.println(Calendar.getInstance().get(startDateChooser.getDate()));
		
		if ((scheduleName.getText().length() > 0)
				&& (startDateChooser.getDate() != null)
				&& (transaction.getAmount() != 0)
				&& (transaction.getDescription().length() > 0)
				&& (transaction.getTo() != null)
				&& (transaction.getFrom() != null))
			return true;
		else
			return false;
	}
	
	private void saveSchedule(){
		if (this.schedule == null){
			Transaction t = DataInstance.getInstance().getDataModelFactory().createTransaction();
			t.setAmount(transaction.getAmount());
			t.setDescription(transaction.getDescription());
			t.setNumber(transaction.getNumber());
			t.setMemo(transaction.getMemo());
			t.setTo(transaction.getTo());
			t.setFrom(transaction.getFrom());
			System.out.println("Freq type: "+getFrequencyType()+" sch day: "+getScheduleDay());
			DataInstance.getInstance().addSchedule(scheduleName.getText(), startDateChooser.getDate(), /*null*/endDateChooser.getDate()/*added by Nicky*/, getFrequencyType(), getScheduleDay(), getScheduleWeek(), getScheduleMonth(), t);
		
		}
		else{
			schedule.setScheduleName(scheduleName.getText());
			schedule.setAmount(transaction.getAmount());
			schedule.setDescription(transaction.getDescription());
			schedule.setNumber(transaction.getNumber());
			schedule.setMemo(transaction.getMemo());
			schedule.setTo(transaction.getTo());
			schedule.setFrom(transaction.getFrom());
			schedule.setStartDate(startDateChooser.getDate());
			//added by Nicky
			schedule.setEndDate(endDateChooser.getDate());
			
			schedule.setFrequencyType(getFrequencyType());
			schedule.setScheduleDay(getScheduleDay());
			schedule.setScheduleWeek(getScheduleWeek());
			schedule.setScheduleMonth(getScheduleMonth());
			DataInstance.getInstance().saveDataModel();
		}
	}
	
	private void loadSchedule(Schedule s){
		if (s != null){
			transaction.updateContent();
			updateSchedulePulldown();
			
			scheduleName.setText(s.getScheduleName());
			startDateChooser.setDate(s.getStartDate());
			endDateChooser.setDate(s.getEndDate());//added by Nicky
			frequencyPulldown.setSelectedItem(s.getFrequencyType());
			schedulePulldown.setSelectedIndex(s.getScheduleDay());
			Transaction t = DataInstance.getInstance().getDataModelFactory().createTransaction();
			t.setAmount(s.getAmount());
			t.setDescription(s.getDescription());
			t.setNumber(s.getNumber());
			t.setMemo(s.getMemo());
			t.setTo(s.getTo());
			t.setFrom(s.getFrom());
			if (Const.DEVEL) Log.debug("Transaction to load: " + t);
			transaction.setTransaction(t, true);
		}
	}
	
	private String getFrequencyType(){
		Object o = frequencyPulldown.getSelectedItem();
		
		return o.toString(); 
	}
	
	private Integer getScheduleDay(){
		Object o = schedulePulldown.getSelectedItem();
		
		if (o instanceof Integer){
			return ((Integer) o) - 1;
		}
		else {
		/*added by Nicky*/	
			if(getFrequencyType().equals("DAY_MONTH"))
			{
				for (int i = 0; i < Const.FIRST_DAYS.length; i++) 
				{
					if (Const.FIRST_DAYS[i].equals(o))
						return i;
				}
			}
			else
			{
				for (int i = 0; i < Const.DAYS_IN_WEEK.length; i++) 
				{
					if (Const.DAYS_IN_WEEK[i].equals(o))
						return i;
				}
			}
			if (Const.DEVEL) Log.debug("Unknown object when getting schedule day: " + o);
			return -1;
		}		
		
	}
	
	/**
	 * Reads the checkboxes and set the appropriate weeks that have been selected 
	 * for multiple weeks in a month option
	 */
	
		private Integer getScheduleWeek(){
			int scheduleWeek=0;
			if(firstWeek_dayButton.isSelected())
				scheduleWeek += 1 ;
			if(secondWeek_dayButton.isSelected())
				scheduleWeek += 2 ;
			if(thirdWeek_dayButton.isSelected())
				scheduleWeek += 4 ;
			if(fourthWeek_dayButton.isSelected())
				scheduleWeek += 8 ;
			return scheduleWeek;
			}		
		
	/**
	 * Reads the monthly gap between two transactions as selected from a pulldown 
	 * for multiple months in a year option
	 */
		
		private Integer getScheduleMonth()
		{
			Object o = monthFreqPulldown.getSelectedItem();
			
			if (o instanceof Integer){
				return ((Integer) o);
			}
			return -1;
		}
}