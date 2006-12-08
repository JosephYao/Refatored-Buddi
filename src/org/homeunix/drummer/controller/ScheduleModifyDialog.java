/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JOptionPane;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Schedule;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.view.AbstractDialog;
import org.homeunix.drummer.view.ScheduleModifyDialogLayout;
import org.homeunix.thecave.moss.util.Log;

public class ScheduleModifyDialog extends ScheduleModifyDialogLayout {
	public static final long serialVersionUID = 0;

	private final Schedule schedule;

	public ScheduleModifyDialog(Schedule schedule){
		super(MainBuddiFrame.getInstance());

		this.schedule = schedule;
		
		//If we are viewing existing transactions, we cannot 
		// modify the schedule at all.
		startDateChooser.setEnabled(schedule == null);
		frequencyPulldown.setEnabled(schedule == null);
		weeklyDayChooser.setEnabled(schedule == null);
		monthlyDateChooser.setEnabled(schedule == null);
		monthlyFirstDayChooser.setEnabled(schedule == null);
		multipleWeeksDayChooser.setEnabled(schedule == null);
		multipleMonthsDateChooser.setEnabled(schedule == null);

		for (JCheckBox checkBox : checkBoxes) {
			checkBox.setEnabled(schedule == null);
		}

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
				if (Const.DEVEL) Log.debug("Schedule week: "+ getScheduleWeek());

				if (ScheduleModifyDialog.this.ensureInfoCorrect()){
					if (!ScheduleModifyDialog.this.startDateChooser.getDate().before(new Date())
							|| schedule != null		//If the schedule has already been defined, we won't bother people again 
							|| JOptionPane.showConfirmDialog(ScheduleModifyDialog.this, 
									Translate.getInstance().get(TranslateKeys.START_DATE_IN_THE_PAST), 
									Translate.getInstance().get(TranslateKeys.START_DATE_IN_THE_PAST_TITLE), 
									JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
						ScheduleModifyDialog.this.saveSchedule();
						MainBuddiFrame.getInstance().updateScheduledTransactions();
						MainBuddiFrame.getInstance().updateContent();
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
		DefaultListCellRenderer pulldownTranslator = new DefaultListCellRenderer(){
			public static final long serialVersionUID = 0;
			public Component getListCellRendererComponent(JList list, Object obj, int index, boolean isSelected, boolean cellHasFocus) {
				this.setText(Translate.getInstance().get(obj.toString()));				
				return this;
			}
		};

		frequencyPulldown.setRenderer(pulldownTranslator);
		weeklyDayChooser.setRenderer(pulldownTranslator);
		monthlyDateChooser.setRenderer(pulldownTranslator);
		monthlyFirstDayChooser.setRenderer(pulldownTranslator);
		multipleWeeksDayChooser.setRenderer(pulldownTranslator);
		multipleMonthsDateChooser.setRenderer(pulldownTranslator);
		//Add any other pulldown boxes here...

		updateContent();

		return this;
	}

	public AbstractDialog updateContent(){

		return this;
	}

	private void updateSchedulePulldown(){

//		cardLayout.invalidateLayout(cardHolder);
//		cardLayout.layoutContainer(cardHolder);
		
		cardLayout.show(cardHolder, frequencyPulldown.getSelectedItem().toString());
//		this.pack();
		
		if (Const.DEVEL) Log.info("Showing card " + frequencyPulldown.getSelectedItem().toString());

//		if (getFrequencyType().equals(TranslateKeys.WEEK.toString())){
//		scheduleModel.removeAllElements();
//		for (TranslateKeys day : Const.DAYS_IN_WEEK) {
//		scheduleModel.addElement(day);	
//		}
//		}
//		else if (getFrequencyType().equals(TranslateKeys.MONTH.toString())){
//		scheduleModel.removeAllElements();
//		Calendar c = Calendar.getInstance();
//		c.setTime(DateUtil.getEndOfMonth(new Date(), 0));
//		int daysInMonth = c.get(Calendar.DAY_OF_MONTH);
//		for(int i = 1; i <= daysInMonth; i++){
//		scheduleModel.addElement(i);
//		}
//		}
//		/*Added the following for the four new features added in scheduling*/
//		else if (getFrequencyType().equals(TranslateKeys.ONE_DAY_EVERY_MONTH.toString())){
//		schedulePulldown.setEnabled(true);
//		firstWeek_dayButton.setEnabled(false);
//		secondWeek_dayButton.setEnabled(false);
//		thirdWeek_dayButton.setEnabled(false);
//		fourthWeek_dayButton.setEnabled(false);
//		monthFreqPulldown.setEnabled(false);

//		scheduleModel.removeAllElements();
//		for (TranslateKeys day2 : Const.FIRST_DAYS) {
//		scheduleModel.addElement(day2);	
//		}
//		}
//		else if (getFrequencyType().equals(TranslateKeys.EVERY_WEEKDAY.toString())){
//		//scheduleModel.removeAllElements();
//		schedulePulldown.setEnabled(false);
//		firstWeek_dayButton.setEnabled(false);
//		secondWeek_dayButton.setEnabled(false);
//		thirdWeek_dayButton.setEnabled(false);
//		fourthWeek_dayButton.setEnabled(false);
//		monthFreqPulldown.setEnabled(false);

//		}else if (getFrequencyType().equals(TranslateKeys.MULTIPLE_WEEKS_EVERY_MONTH.toString())){
//		//scheduleModel.removeAllElements();
//		schedulePulldown.setEnabled(true);
//		firstWeek_dayButton.setEnabled(true);
//		secondWeek_dayButton.setEnabled(true);
//		thirdWeek_dayButton.setEnabled(true);
//		fourthWeek_dayButton.setEnabled(true);
//		monthFreqPulldown.setEnabled(false);

//		scheduleModel.removeAllElements();
//		for (TranslateKeys day : Const.DAYS_IN_WEEK) {
//		scheduleModel.addElement(day);	
//		}

//		}else if (getFrequencyType().equals(TranslateKeys.MULTIPLE_MONTHS_EVERY_YEAR.toString())){
//		//scheduleModel.removeAllElements();
//		schedulePulldown.setEnabled(true);
//		firstWeek_dayButton.setEnabled(false);
//		secondWeek_dayButton.setEnabled(false);
//		thirdWeek_dayButton.setEnabled(false);
//		fourthWeek_dayButton.setEnabled(false);
//		monthFreqPulldown.setEnabled(true);

//		scheduleModel.removeAllElements();
//		Calendar c = Calendar.getInstance();
//		c.setTime(DateUtil.getEndOfMonth(new Date(), 0));
//		int daysInMonth = c.get(Calendar.DAY_OF_MONTH);
//		for(int i = 1; i <= daysInMonth; i++)
//		{
//		scheduleModel.addElement(i);
//		}

//		}
//		/*added by Nicky*/
//		else{
//		Log.error("Unknown frequency type: " + getFrequencyType());
//		}
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
			if (Const.DEVEL) Log.info("Freq type: "+getFrequencyType()+" sch day: "+getScheduleDay());
			DataInstance.getInstance().addSchedule(scheduleName.getText(), startDateChooser.getDate(), null, getFrequencyType(), getScheduleDay(), getScheduleWeek(), getScheduleMonth(), t);

		}
		else{
			schedule.setScheduleName(scheduleName.getText());
			schedule.setAmount(transaction.getAmount());
			schedule.setDescription(transaction.getDescription());
			schedule.setNumber(transaction.getNumber());
			schedule.setMemo(transaction.getMemo());
			schedule.setTo(transaction.getTo());
			schedule.setFrom(transaction.getFrom());

			// We should not have to save this, as it cannot be modified.
//			schedule.setStartDate(startDateChooser.getDate());
//			schedule.setFrequencyType(getFrequencyType());
//			schedule.setScheduleDay(getScheduleDay());
//			schedule.setScheduleWeek(getScheduleWeek());
//			schedule.setScheduleMonth(getScheduleMonth());
			DataInstance.getInstance().saveDataModel();
		}
	}

	private void loadSchedule(Schedule s){
		if (s != null){
			transaction.updateContent();
			updateSchedulePulldown();

			//Load the changeable fields, including Transaction
			scheduleName.setText(s.getScheduleName());
			Transaction t = DataInstance.getInstance().getDataModelFactory().createTransaction();
			t.setAmount(s.getAmount());
			t.setDescription(s.getDescription());
			t.setNumber(s.getNumber());
			t.setMemo(s.getMemo());
			t.setTo(s.getTo());
			t.setFrom(s.getFrom());
			if (Const.DEVEL) Log.debug("Transaction to load: " + t);
			transaction.setTransaction(t, true);
			
			//Load the schedule pulldowns
			startDateChooser.setDate(s.getStartDate());
			frequencyPulldown.setSelectedItem(s.getFrequencyType());
			monthlyDateChooser.setSelectedIndex(s.getScheduleDay() - 1);
			monthlyFirstDayChooser.setSelectedIndex(s.getScheduleDay());
			weeklyDayChooser.setSelectedIndex(s.getScheduleDay());
			multipleWeeksDayChooser.setSelectedIndex(s.getScheduleDay());
			multipleMonthsDateChooser.setSelectedIndex(s.getScheduleDay() - 1);
			
			//Load the checkmarks, using bit bashing logic
			multipleWeeksMonthlyFirstWeek.setSelected((s.getScheduleWeek() & 1) != 0);
			multipleWeeksMonthlySecondWeek.setSelected((s.getScheduleWeek() & 2) != 0);
			multipleWeeksMonthlyThirdWeek.setSelected((s.getScheduleWeek() & 4) != 0);
			multipleWeeksMonthlyFourthWeek.setSelected((s.getScheduleWeek() & 8) != 0);
			
			multipleMonthsYearlyJanuary.setSelected((s.getScheduleMonth() & 1) != 0);
			multipleMonthsYearlyFebruary.setSelected((s.getScheduleMonth() & 2) != 0);
			multipleMonthsYearlyMarch.setSelected((s.getScheduleMonth() & 4) != 0);
			multipleMonthsYearlyApril.setSelected((s.getScheduleMonth() & 8) != 0);
			multipleMonthsYearlyMay.setSelected((s.getScheduleMonth() & 16) != 0);
			multipleMonthsYearlyJune.setSelected((s.getScheduleMonth() & 32) != 0);
			multipleMonthsYearlyJuly.setSelected((s.getScheduleMonth() & 64) != 0);
			multipleMonthsYearlyAugust.setSelected((s.getScheduleMonth() & 128) != 0);
			multipleMonthsYearlySeptember.setSelected((s.getScheduleMonth() & 256) != 0);
			multipleMonthsYearlyOctober.setSelected((s.getScheduleMonth() & 512) != 0);
			multipleMonthsYearlyNovember.setSelected((s.getScheduleMonth() & 1024) != 0);
			multipleMonthsYearlyDecember.setSelected((s.getScheduleMonth() & 2048) != 0);
		}
	}

	private String getFrequencyType(){
		String frequencyType = frequencyPulldown.getSelectedItem().toString();
				
		return frequencyType; 
	}

	private Integer getScheduleDay(){
		if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.MONTHLY_BY_DATE.toString())){
			//To make it nicer to read in the data file, as well
			// as backwards compatible, we add 1 to the index.
			// Don't forget to subtract one when we load it!
			return monthlyDateChooser.getSelectedIndex() + 1;
		}
		else if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.MONTHLY_BY_DAY_OF_WEEK.toString())){
			return monthlyFirstDayChooser.getSelectedIndex();
		}
		else if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.WEEKLY.toString())){
			return weeklyDayChooser.getSelectedIndex();
		}
		else if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.EVERY_DAY.toString())){
			return 0; //We don't use scheduleDay if it is every day
		}
		else if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.EVERY_WEEKDAY.toString())){
			return 0; //We don't use scheduleDay if it is every weekday
		}
		else if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.MULTIPLE_WEEKS_EVERY_MONTH.toString())){
			return multipleWeeksDayChooser.getSelectedIndex();
		}
		else if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.MULTIPLE_MONTHS_EVERY_YEAR.toString())){
			//To make it nicer to read in the data file, we add 1 
			// to the index.  Don't forget to subtract one when we load it!
			return multipleMonthsDateChooser.getSelectedIndex() + 1;
		}
		else {
			Log.error("Unknown frequency in getScheduleDay(): " + frequencyPulldown.getSelectedItem());
			return 0;
		}



//		Object o = schedulePulldown.getSelectedItem();

//		if (o instanceof Integer){
//		return ((Integer) o) - 1;
//		}
//		else {
//		/*added by Nicky*/	
//		if (getFrequencyType().equals(TranslateKeys.ONE_DAY_EVERY_MONTH.toString())){
//		for (int i = 0; i < Const.FIRST_DAYS.length; i++) 
//		{
//		if (Const.FIRST_DAYS[i].equals(o))
//		return i;
//		}
//		}
//		else
//		{
//		for (int i = 0; i < Const.DAYS_IN_WEEK.length; i++) 
//		{
//		if (Const.DAYS_IN_WEEK[i].equals(o))
//		return i;
//		}
//		}
//		if (Const.DEVEL) Log.debug("Unknown object when getting schedule day: " + o);
//		return -1;
//		}		

	}

	/**
	 * Reads the checkboxes and set the appropriate weeks that have been selected 
	 * for multiple weeks in a month option
	 */

	private Integer getScheduleWeek(){
		int scheduleWeek = 0;
		
		scheduleWeek += (multipleWeeksMonthlyFirstWeek.isSelected() ? 1 : 0);
		scheduleWeek += (multipleWeeksMonthlySecondWeek.isSelected() ? 2 : 0);
		scheduleWeek += (multipleWeeksMonthlyThirdWeek.isSelected() ? 4 : 0);
		scheduleWeek += (multipleWeeksMonthlyFourthWeek.isSelected() ? 8 : 0);
		
		return scheduleWeek;
	}		

	/**
	 * Returns a value representing all selected months.  We store these
	 * in the bit values: January = 1, February = 2, March = 4, April = 8,
	 * etc up to December = 2048.  To extract these again, you just have
	 * to AND the value with a bitmask of January = 1, etc; if the
	 * resulting value is 0, that month is not selected, if != 0, it is.
	 */
	private Integer getScheduleMonth()
	{
		if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.MULTIPLE_MONTHS_EVERY_YEAR.toString())){
			int value = 0;
			
			value += (multipleMonthsYearlyJanuary.isSelected() ? 1 : 0 );
			value += (multipleMonthsYearlyFebruary.isSelected() ? 2 : 0 );
			value += (multipleMonthsYearlyMarch.isSelected() ? 4 : 0 );
			value += (multipleMonthsYearlyApril.isSelected() ? 8 : 0 );
			value += (multipleMonthsYearlyMay.isSelected() ? 16 : 0 );
			value += (multipleMonthsYearlyJune.isSelected() ? 32 : 0 );
			value += (multipleMonthsYearlyJuly.isSelected() ? 64 : 0 );
			value += (multipleMonthsYearlyAugust.isSelected() ? 128 : 0 );
			value += (multipleMonthsYearlySeptember.isSelected() ? 256 : 0 );
			value += (multipleMonthsYearlyOctober.isSelected() ? 512 : 0 );
			value += (multipleMonthsYearlyNovember.isSelected() ? 1024 : 0 );
			value += (multipleMonthsYearlyDecember.isSelected() ? 2048 : 0 );
			
			return value;
		}
		else {
			return -1;
		}
	}
}