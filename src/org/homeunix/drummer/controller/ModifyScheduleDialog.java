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
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractDialog;
import org.homeunix.drummer.view.ModifyScheduleDialogLayout;

public class ModifyScheduleDialog extends ModifyScheduleDialogLayout {
	public static final long serialVersionUID = 0;
	
	private final Schedule schedule;

	public ModifyScheduleDialog(Schedule schedule){
		super(MainBuddiFrame.getInstance());
		
		this.schedule = schedule;
		startDateChooser.setEnabled(schedule == null);
		frequencyPulldown.setEnabled(schedule == null);
		schedulePulldown.setEnabled(schedule == null);
		initContent();
		updateSchedulePulldown();
		transaction.updateContent();
		
		loadSchedule(schedule);
	}

	protected String getType(){
		return Translate.getInstance().get(TranslateKeys.ACCOUNT);
	}
		
	@Override
	protected AbstractDialog initActions() {
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (ModifyScheduleDialog.this.ensureInfoCorrect()){
					if (!ModifyScheduleDialog.this.startDateChooser.getDate().before(new Date())
							|| schedule != null		//If the schedule has already been defined, we won't bother people again 
							|| JOptionPane.showConfirmDialog(ModifyScheduleDialog.this, 
									Translate.getInstance().get(TranslateKeys.START_DATE_IN_THE_PAST), 
									Translate.getInstance().get(TranslateKeys.START_DATE_IN_THE_PAST_TITLE), 
									JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
						ModifyScheduleDialog.this.saveSchedule();
						MainBuddiFrame.getInstance().updateScheduledTransactions();
						TransactionsFrame.updateAllTransactionWindows();
						ModifyScheduleDialog.this.setVisible(false);
						ModifyScheduleDialog.this.dispose();
					}
					else
						if (Const.DEVEL) Log.debug("Cancelled from either start date in the past, or info not correct");
				}
				else {
					JOptionPane.showMessageDialog(ModifyScheduleDialog.this, 
							Translate.getInstance().get(TranslateKeys.SCHEDULED_NOT_ENOUGH_INFO),
							Translate.getInstance().get(TranslateKeys.SCHEDULED_NOT_ENOUGH_INFO_TITLE),
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ModifyScheduleDialog.this.setVisible(false);
				ModifyScheduleDialog.this.dispose();
			}
		});
		
		frequencyPulldown.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				ModifyScheduleDialog.this.updateSchedulePulldown();
			}
		});
		
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
			for (String day : Const.DAYS_IN_WEEK) {
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
		else{
			Log.error("Unknown frequency type: " + getFrequencyType());
		}
	}
	
	private boolean ensureInfoCorrect(){
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

			DataInstance.getInstance().addSchedule(scheduleName.getText(), startDateChooser.getDate(), null, getFrequencyType(), getScheduleDay(), t);
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
			schedule.setFrequencyType(getFrequencyType());
			schedule.setScheduleDay(getScheduleDay());
			
			DataInstance.getInstance().saveDataModel();
		}
	}
	
	private void loadSchedule(Schedule s){
		if (s != null){
			transaction.updateContent();
			updateSchedulePulldown();
			
			scheduleName.setText(s.getScheduleName());
			startDateChooser.setDate(s.getStartDate());
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
			for (int i = 0; i < Const.DAYS_IN_WEEK.length; i++) {
				if (Const.DAYS_IN_WEEK[i].equals(o))
					return i;
			}
			
			if (Const.DEVEL) Log.debug("Unknown object when getting schedule day: " + o);
			return -1;
		}		
	}
}
