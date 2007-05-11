/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.ScheduleController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.ModelFactory;
import org.homeunix.drummer.model.Schedule;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.components.EditableTransaction;
import org.homeunix.thecave.moss.gui.JScrollingComboBox;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractDialog;
import org.homeunix.thecave.moss.gui.hint.JHintTextArea;
import org.homeunix.thecave.moss.gui.hint.JHintTextField;
import org.homeunix.thecave.moss.util.DateUtil;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

import com.toedter.calendar.JDateChooser;

public class ScheduleModifyDialog extends AbstractBuddiDialog {
	public static final long serialVersionUID = 0;

	private final Schedule schedule;
	
	private final JButton okButton;
	private final JButton cancelButton;

	private final JHintTextField scheduleName;
	
	private final JHintTextArea message;
	
	private final JScrollingComboBox frequencyPulldown;
	private final JScrollingComboBox weeklyDayChooser;
	private final JScrollingComboBox biWeeklyDayChooser;
	private final JScrollingComboBox monthlyDateChooser;
	private final JScrollingComboBox monthlyFirstDayChooser;
	private final JScrollingComboBox multipleWeeksDayChooser;
	private final JScrollingComboBox multipleMonthsDateChooser;

	private final JDateChooser startDateChooser;

	private final JCheckBox multipleWeeksMonthlyFirstWeek;
	private final JCheckBox multipleWeeksMonthlySecondWeek;
	private final JCheckBox multipleWeeksMonthlyThirdWeek;
	private final JCheckBox multipleWeeksMonthlyFourthWeek;

	private final JCheckBox multipleMonthsYearlyJanuary;
	private final JCheckBox multipleMonthsYearlyFebruary;
	private final JCheckBox multipleMonthsYearlyMarch;
	private final JCheckBox multipleMonthsYearlyApril;
	private final JCheckBox multipleMonthsYearlyMay;
	private final JCheckBox multipleMonthsYearlyJune;
	private final JCheckBox multipleMonthsYearlyJuly;
	private final JCheckBox multipleMonthsYearlyAugust;
	private final JCheckBox multipleMonthsYearlySeptember;
	private final JCheckBox multipleMonthsYearlyOctober;
	private final JCheckBox multipleMonthsYearlyNovember;
	private final JCheckBox multipleMonthsYearlyDecember;
	
	private final Vector<JCheckBox> checkBoxes = new Vector<JCheckBox>();
	
	private final EditableTransaction editableTransaction;
	
	private final CardLayout cardLayout;
	private final JPanel cardHolder;

	public ScheduleModifyDialog(Schedule schedule){
		super(MainFrame.getInstance());

		okButton = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_OK));
		cancelButton = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_CANCEL));

		editableTransaction = new EditableTransaction(null);

		scheduleName = new JHintTextField(Translate.getInstance().get(TranslateKeys.SCHEDULED_ACTION_NAME));
		
		message = new JHintTextArea(Translate.getInstance().get(TranslateKeys.HINT_MESSAGE));
		message.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_SCHEDULED_MESSAGE));
		JScrollPane messageScroller = new JScrollPane(message);
		messageScroller.setPreferredSize(new Dimension(100, 100));
		
		//Create the check boxes for Multiple Weeks each Month.
		multipleWeeksMonthlyFirstWeek = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_WEEK_FIRST.toString()));
		multipleWeeksMonthlySecondWeek = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_WEEK_SECOND.toString()));
		multipleWeeksMonthlyThirdWeek = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_WEEK_THIRD.toString()));
		multipleWeeksMonthlyFourthWeek = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_WEEK_FOURTH.toString()));

		//Create the check boxes for Multiple Months each Year.
		multipleMonthsYearlyJanuary = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_MONTH_JANUARY.toString()));
		multipleMonthsYearlyFebruary = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_MONTH_FEBRUARY.toString()));
		multipleMonthsYearlyMarch = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_MONTH_MARCH.toString()));
		multipleMonthsYearlyApril = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_MONTH_APRIL.toString()));
		multipleMonthsYearlyMay = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_MONTH_MAY.toString()));
		multipleMonthsYearlyJune = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_MONTH_JUNE.toString()));
		multipleMonthsYearlyJuly = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_MONTH_JULY.toString()));
		multipleMonthsYearlyAugust = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_MONTH_AUGUST.toString()));
		multipleMonthsYearlySeptember = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_MONTH_SEPTEMBER.toString()));
		multipleMonthsYearlyOctober = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_MONTH_OCTOBER.toString()));
		multipleMonthsYearlyNovember = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_MONTH_NOVEMBER.toString()));
		multipleMonthsYearlyDecember = new JCheckBox(Translate.getInstance().get(TranslateKeys.SCHEDULE_MONTH_DECEMBER.toString()));

		//Add all the check boxes to a vector for easy iteration 
		// when enabling / disabling them
		checkBoxes.add(multipleWeeksMonthlyFirstWeek);
		checkBoxes.add(multipleWeeksMonthlySecondWeek);
		checkBoxes.add(multipleWeeksMonthlyThirdWeek);
		checkBoxes.add(multipleWeeksMonthlyFourthWeek);
		checkBoxes.add(multipleMonthsYearlyJanuary);
		checkBoxes.add(multipleMonthsYearlyFebruary);
		checkBoxes.add(multipleMonthsYearlyMarch);
		checkBoxes.add(multipleMonthsYearlyApril);
		checkBoxes.add(multipleMonthsYearlyMay);
		checkBoxes.add(multipleMonthsYearlyJune);
		checkBoxes.add(multipleMonthsYearlyJuly);
		checkBoxes.add(multipleMonthsYearlyAugust);
		checkBoxes.add(multipleMonthsYearlySeptember);
		checkBoxes.add(multipleMonthsYearlyOctober);
		checkBoxes.add(multipleMonthsYearlyNovember);
		checkBoxes.add(multipleMonthsYearlyDecember);
		
		
		//This is where we give the Frequency dropdown options
		Vector<String> frequencyPulldownChoices = new Vector<String>();
		frequencyPulldownChoices.add(TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE.toString());
		frequencyPulldownChoices.add(TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DAY_OF_WEEK.toString());
		frequencyPulldownChoices.add(TranslateKeys.SCHEDULE_FREQUENCY_WEEKLY.toString());
		frequencyPulldownChoices.add(TranslateKeys.SCHEDULE_FREQUENCY_BIWEEKLY.toString());
		frequencyPulldownChoices.add(TranslateKeys.SCHEDULE_FREQUENCY_EVERY_DAY.toString());
		frequencyPulldownChoices.add(TranslateKeys.SCHEDULE_FREQUENCY_EVERY_WEEKDAY.toString());		
		frequencyPulldownChoices.add(TranslateKeys.SCHEDULE_FREQUENCY_MULTIPLE_WEEKS_EVERY_MONTH.toString());
		frequencyPulldownChoices.add(TranslateKeys.SCHEDULE_FREQUENCY_MULTIPLE_MONTHS_EVERY_YEAR.toString());
		frequencyPulldown = new JScrollingComboBox(frequencyPulldownChoices);
		
		Vector<String> weeklyDayChooserChoices = new Vector<String>();
		weeklyDayChooserChoices.add(TranslateKeys.SCHEDULE_DAY_SUNDAY.toString());
		weeklyDayChooserChoices.add(TranslateKeys.SCHEDULE_DAY_MONDAY.toString());
		weeklyDayChooserChoices.add(TranslateKeys.SCHEDULE_DAY_TUESDAY.toString());
		weeklyDayChooserChoices.add(TranslateKeys.SCHEDULE_DAY_WEDNESDAY.toString());
		weeklyDayChooserChoices.add(TranslateKeys.SCHEDULE_DAY_THURSDAY.toString());
		weeklyDayChooserChoices.add(TranslateKeys.SCHEDULE_DAY_FRIDAY.toString());
		weeklyDayChooserChoices.add(TranslateKeys.SCHEDULE_DAY_SATURDAY.toString());
		weeklyDayChooser = new JScrollingComboBox(weeklyDayChooserChoices);

		//We use the same one for BiWeekly as Weekly.
		biWeeklyDayChooser = new JScrollingComboBox(weeklyDayChooserChoices);
		
		Vector<String> monthlyDateChooserChoices = new Vector<String>();
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_FIRST.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_SECOND.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_THIRD.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_FOURTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_FIFTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_SIXTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_SEVENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_EIGHTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_NINETH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_TENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_ELEVENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_TWELFTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_THIRTEENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_FOURTEENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_FIFTEENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_SIXTEENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_SEVENTEENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_EIGHTEENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_NINETEENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_TWENTIETH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_TWENTYFIRST.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_TWENTYSECOND.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_TWENTYTHIRD.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_TWENTYFOURTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_TWENTYFIFTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_TWENTYSIXTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_TWENTYSEVENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_TWENTYEIGHTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_TWENTYNINETH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_THIRTIETH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SCHEDULE_DATE_THIRTYFIRST.toString());		

		monthlyDateChooser = new JScrollingComboBox(monthlyDateChooserChoices);
		
		Vector<String> monthlyFirstDayChooserChoices = new Vector<String>();
		monthlyFirstDayChooserChoices.add(TranslateKeys.SCHEDULE_DAY_FIRST_SUNDAY.toString());
		monthlyFirstDayChooserChoices.add(TranslateKeys.SCHEDULE_DAY_FIRST_MONDAY.toString());
		monthlyFirstDayChooserChoices.add(TranslateKeys.SCHEDULE_DAY_FIRST_TUESDAY.toString());
		monthlyFirstDayChooserChoices.add(TranslateKeys.SCHEDULE_DAY_FIRST_WEDNESDAY.toString());
		monthlyFirstDayChooserChoices.add(TranslateKeys.SCHEDULE_DAY_FIRST_THURSDAY.toString());
		monthlyFirstDayChooserChoices.add(TranslateKeys.SCHEDULE_DAY_FIRST_FRIDAY.toString());
		monthlyFirstDayChooserChoices.add(TranslateKeys.SCHEDULE_DAY_FIRST_SATURDAY.toString());
		monthlyFirstDayChooser = new JScrollingComboBox(monthlyFirstDayChooserChoices);
		
		//We already have a vector defined with Sunday, Monday, etc
		multipleWeeksDayChooser = new JScrollingComboBox(weeklyDayChooserChoices);		
		
		//We already have a vector defined with First, Second, ... Thirty First
		multipleMonthsDateChooser = new JScrollingComboBox(monthlyDateChooserChoices);

		startDateChooser = new JDateChooser(DateUtil.getNextDay(new Date()), PrefsInstance.getInstance().getPrefs().getDateFormat());

		Dimension buttonSize = new Dimension(Math.max(100, cancelButton.getPreferredSize().width), cancelButton.getPreferredSize().height);
		okButton.setPreferredSize(buttonSize);
		cancelButton.setPreferredSize(buttonSize);

		Dimension textSize = new Dimension(Math.max(250, scheduleName.getPreferredSize().width), scheduleName.getPreferredSize().height);
		startDateChooser.setPreferredSize(textSize);
		scheduleName.setPreferredSize(textSize);

		//The namePanel is where we keep the Schedule Name.
		JPanel namePanel = new JPanel(new BorderLayout());
		namePanel.add(scheduleName, BorderLayout.EAST);
		namePanel.add(messageScroller, BorderLayout.SOUTH);

		//The schedulePanel is where we keep all the options for scheduling
		JPanel schedulePanel = new JPanel(new BorderLayout());
		schedulePanel.setBorder(BorderFactory.createTitledBorder((String) null));

		//The top part of the schedule information
		JLabel intro = new JLabel(Translate.getInstance().get(TranslateKeys.REPEAT_THIS_ACTION));
		cardLayout = new CardLayout();
		cardHolder = new JPanel(cardLayout);		
		JPanel introHolder = new JPanel(new FlowLayout(FlowLayout.LEFT));
		introHolder.add(intro);
		introHolder.add(frequencyPulldown);
		
		//The middle part of the schedule information
		JPanel startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		startDatePanel.add(new JLabel(Translate.getInstance().get(TranslateKeys.STARTING_ON)));
		startDatePanel.add(startDateChooser);
		
		//Put the schedule panel together properly...
		schedulePanel.add(introHolder, BorderLayout.NORTH);
		schedulePanel.add(startDatePanel, BorderLayout.CENTER);
		schedulePanel.add(cardHolder, BorderLayout.SOUTH);
		
		//We new define each 'card' separately, with the correct
		// options for each frequency.  This is then added to the
		// card holder for quick random access when the frequency 
		// pulldown changes.
		
		
		//Monthly by Date Card
		JPanel monthly = new JPanel(new FlowLayout(FlowLayout.LEFT));
		monthly.add(new JLabel(Translate.getInstance().get(TranslateKeys.AND_REPEATING_ON_THE)));
		monthly.add(monthlyDateChooser);
		monthly.add(new JLabel(Translate.getInstance().get(TranslateKeys.OF_EACH_MONTH)));
		cardHolder.add(monthly, TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE.toString());


		//One Day Every Month Card
		JPanel oneDayMonthly = new JPanel(new FlowLayout(FlowLayout.LEFT));
		oneDayMonthly.add(new JLabel(Translate.getInstance().get(TranslateKeys.AND_REPEATING_ON_THE)));
		oneDayMonthly.add(monthlyFirstDayChooser);
		oneDayMonthly.add(new JLabel(Translate.getInstance().get(TranslateKeys.OF_EACH_MONTH)));
		cardHolder.add(oneDayMonthly, TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DAY_OF_WEEK.toString());

		//Weekly card
		JPanel weekly = new JPanel(new FlowLayout(FlowLayout.LEFT));
		weekly.add(new JLabel(Translate.getInstance().get(TranslateKeys.AND_REPEATING_EVERY)));
		weekly.add(weeklyDayChooser);
		cardHolder.add(weekly, TranslateKeys.SCHEDULE_FREQUENCY_WEEKLY.toString());
		
		//BiWeekly card
		JPanel biweekly = new JPanel(new FlowLayout(FlowLayout.LEFT));
		biweekly.add(new JLabel(Translate.getInstance().get(TranslateKeys.AND_REPEATING_EVERY)));
		biweekly.add(biWeeklyDayChooser);
		cardHolder.add(biweekly, TranslateKeys.SCHEDULE_FREQUENCY_BIWEEKLY.toString());
		
		//Blank Panel - for Every Weekday, and others with no config options
		cardHolder.add(new JPanel(), TranslateKeys.SCHEDULE_FREQUENCY_EVERY_WEEKDAY.toString());
		cardHolder.add(new JPanel(), TranslateKeys.SCHEDULE_FREQUENCY_EVERY_DAY.toString());
		
		//Multiple weeks each month card
		JPanel multipleWeeksMonthly = new JPanel(new BorderLayout());
		JPanel multipleWeeksMonthlyTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
		multipleWeeksMonthlyTop.add(new JLabel(Translate.getInstance().get(TranslateKeys.AND_REPEATING_EVERY)));
		multipleWeeksMonthlyTop.add(multipleWeeksDayChooser);
		multipleWeeksMonthlyTop.add(new JLabel(Translate.getInstance().get(TranslateKeys.ON_EACH_OF_THE_FOLLOWING_WEEKS)));
		multipleWeeksMonthly.add(multipleWeeksMonthlyTop, BorderLayout.NORTH);
		JPanel multipleWeeksMonthlyCheckboxes = new JPanel(new GridLayout(0, 1));
		multipleWeeksMonthlyCheckboxes.add(multipleWeeksMonthlyFirstWeek);
		multipleWeeksMonthlyCheckboxes.add(multipleWeeksMonthlySecondWeek);
		multipleWeeksMonthlyCheckboxes.add(multipleWeeksMonthlyThirdWeek);
		multipleWeeksMonthlyCheckboxes.add(multipleWeeksMonthlyFourthWeek);
		multipleWeeksMonthly.add(multipleWeeksMonthlyCheckboxes, BorderLayout.EAST);
		cardHolder.add(multipleWeeksMonthly, TranslateKeys.SCHEDULE_FREQUENCY_MULTIPLE_WEEKS_EVERY_MONTH.toString());

		//Multiple months each year card
		JPanel multipleMonthsYearly = new JPanel(new BorderLayout());
		JPanel multipleMonthsYearlyTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
		multipleMonthsYearlyTop.add(new JLabel(Translate.getInstance().get(TranslateKeys.AND_REPEATING_ON_THE)));
		multipleMonthsYearlyTop.add(multipleMonthsDateChooser);
		multipleMonthsYearlyTop.add(new JLabel(Translate.getInstance().get(TranslateKeys.ON_EACH_OF_THE_FOLLOWING_MONTHS)));
		multipleMonthsYearly.add(multipleMonthsYearlyTop, BorderLayout.NORTH);
		JPanel multipleMonthsYearlyCheckboxes = new JPanel(new GridLayout(4, 0));
		multipleMonthsYearlyCheckboxes.add(multipleMonthsYearlyJanuary);
		multipleMonthsYearlyCheckboxes.add(multipleMonthsYearlyFebruary);
		multipleMonthsYearlyCheckboxes.add(multipleMonthsYearlyMarch);
		multipleMonthsYearlyCheckboxes.add(multipleMonthsYearlyApril);
		multipleMonthsYearlyCheckboxes.add(multipleMonthsYearlyMay);
		multipleMonthsYearlyCheckboxes.add(multipleMonthsYearlyJune);
		multipleMonthsYearlyCheckboxes.add(multipleMonthsYearlyJuly);
		multipleMonthsYearlyCheckboxes.add(multipleMonthsYearlyAugust);
		multipleMonthsYearlyCheckboxes.add(multipleMonthsYearlySeptember);
		multipleMonthsYearlyCheckboxes.add(multipleMonthsYearlyOctober);
		multipleMonthsYearlyCheckboxes.add(multipleMonthsYearlyNovember);
		multipleMonthsYearlyCheckboxes.add(multipleMonthsYearlyDecember);
		multipleMonthsYearly.add(multipleMonthsYearlyCheckboxes, BorderLayout.EAST);
		cardHolder.add(multipleMonthsYearly, TranslateKeys.SCHEDULE_FREQUENCY_MULTIPLE_MONTHS_EVERY_YEAR.toString());

		
		//Done all the fancy stuff... now just put all the panels together
		JPanel transactionPanel = new JPanel(new BorderLayout());
		transactionPanel.setBorder(BorderFactory.createEmptyBorder(7, 0, 10, 0));
		transactionPanel.add(editableTransaction, BorderLayout.NORTH);
		
		JPanel textPanelSpacer = new JPanel(new BorderLayout());
		
		textPanelSpacer.add(namePanel, BorderLayout.NORTH);
		textPanelSpacer.add(transactionPanel, BorderLayout.CENTER);
		textPanelSpacer.add(schedulePanel, BorderLayout.SOUTH);


		JPanel mainBorderPanel = new JPanel();
		mainBorderPanel.setLayout(new BorderLayout());
		mainBorderPanel.setBorder(BorderFactory.createTitledBorder((String) null));
		mainBorderPanel.add(textPanelSpacer);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);

		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());

		mainPanel.add(mainBorderPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		if (OperatingSystemUtil.isMac()){
			textPanelSpacer.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
			messageScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			messageScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}
		
		this.setLayout(new BorderLayout());
		this.setResizable(true);
		this.setTitle(Translate.getInstance().get(TranslateKeys.SCHEDULED_ACTION));
		this.add(mainPanel);
		this.getRootPane().setDefaultButton(okButton);
		this.schedule = schedule;

		//If we are viewing existing transactions, we cannot 
		// modify the schedule at all.
		startDateChooser.setEnabled(schedule == null);
		frequencyPulldown.setEnabled(schedule == null);
		weeklyDayChooser.setEnabled(schedule == null);
		biWeeklyDayChooser.setEnabled(schedule == null);
		monthlyDateChooser.setEnabled(schedule == null);
		monthlyFirstDayChooser.setEnabled(schedule == null);
		multipleWeeksDayChooser.setEnabled(schedule == null);
		multipleMonthsDateChooser.setEnabled(schedule == null);

		for (JCheckBox checkBox : checkBoxes) {
			checkBox.setEnabled(schedule == null);
		}

		updateSchedulePulldown();
		editableTransaction.updateContent();
		loadSchedule(schedule);
	}

	protected String getType(){
		return Translate.getInstance().get(TranslateKeys.ACCOUNT);
	}

	public AbstractDialog init() {
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

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

		DefaultListCellRenderer pulldownTranslator = new DefaultListCellRenderer(){
			public static final long serialVersionUID = 0;
			public Component getListCellRendererComponent(JList list, Object obj, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, obj, index, isSelected, cellHasFocus);
				if (obj != null)
					this.setText(Translate.getInstance().get(obj.toString()));				
				return this;
			}
		};

		frequencyPulldown.setRenderer(pulldownTranslator);
		weeklyDayChooser.setRenderer(pulldownTranslator);
		biWeeklyDayChooser.setRenderer(pulldownTranslator);
		monthlyDateChooser.setRenderer(pulldownTranslator);
		monthlyFirstDayChooser.setRenderer(pulldownTranslator);
		multipleWeeksDayChooser.setRenderer(pulldownTranslator);
		multipleMonthsDateChooser.setRenderer(pulldownTranslator);
		//Add any other pulldown boxes here...

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


		//We must have filled in at least the name and the date.
		if ((scheduleName.getValue().length() == 0)
				|| (startDateChooser.getDate() == null)){
			return false;
		}

		//If we're just fillinf in the transaction, we need at least
		// amount, description, to, and from.
		if ((editableTransaction.getAmount() != 0)
				&& (editableTransaction.getDescription().length() > 0)
				&& (editableTransaction.getTo() != null)
				&& (editableTransaction.getFrom() != null)){
			return true;
		}

		//If the message is filled in, we can let the action succeed
		// without the transaction being filled out.  However, if any
		// part of the transaction is filled in, it all must be.
		if ((message.getValue().length() > 0)
				&& (editableTransaction.getAmount() == 0)
				&& (editableTransaction.getDescription().length() == 0)
				&& (editableTransaction.getTo() == null)
				&& (editableTransaction.getFrom() == null)){
			return true;
		}

		return false;
	}

	private void saveSchedule(){
		if (this.schedule == null){
			Transaction t = null;
			if (editableTransaction.getDescription() != null
					&& editableTransaction.getTo() != null
					&& editableTransaction.getFrom() != null){
				t = ModelFactory.eINSTANCE.createTransaction();
				t.setAmount(editableTransaction.getAmount());
				t.setDescription(editableTransaction.getDescription());
				t.setNumber(editableTransaction.getNumber());
				t.setMemo(editableTransaction.getMemo());
				t.setTo(editableTransaction.getTo());
				t.setFrom(editableTransaction.getFrom());
				t.setCleared(editableTransaction.isCleared());
				t.setReconciled(editableTransaction.isReconciled());
			}
			if (Const.DEVEL) Log.info("Freq type: "+getFrequencyType()+" sch day: "+getScheduleDay());
			ScheduleController.addSchedule(scheduleName.getValue(), startDateChooser.getDate(), null, getFrequencyType(), getScheduleDay(), getScheduleWeek(), getScheduleMonth(), message.getValue(), t);

		}
		else{
			schedule.setScheduleName(scheduleName.getValue());
			schedule.setMessage(message.getValue());
			schedule.setAmount(editableTransaction.getAmount());
			schedule.setDescription(editableTransaction.getDescription());
			schedule.setNumber(editableTransaction.getNumber());
			schedule.setMemo(editableTransaction.getMemo());
			schedule.setTo(editableTransaction.getTo());
			schedule.setFrom(editableTransaction.getFrom());
			schedule.setCleared(editableTransaction.isCleared());
			schedule.setReconciled(editableTransaction.isReconciled());

			// We should not have to save this, as it cannot be modified.
//			schedule.setStartDate(startDateChooser.getDate());
//			schedule.setFrequencyType(getFrequencyType());
//			schedule.setScheduleDay(getScheduleDay());
//			schedule.setScheduleWeek(getScheduleWeek());
//			schedule.setScheduleMonth(getScheduleMonth());
//			DataInstance.getInstance().saveDataModel();
		}
	}

	private void loadSchedule(Schedule s){
		if (s != null){
			editableTransaction.updateContent();
			updateSchedulePulldown();

			//Load the changeable fields, including Transaction
			scheduleName.setValue(s.getScheduleName());
			if (s.getMessage() != null)
				message.setValue(s.getMessage());

			Transaction t = (Transaction) s;

			if (s.getDescription() != null
					&& s.getTo() != null
					&& s.getFrom() != null){
				editableTransaction.setTransaction(t, true);
			}
			else {
				editableTransaction.setVisible(false);
			}

			//Load the schedule pulldowns, based on which type of 
			// schedule we're following.
			startDateChooser.setDate(s.getStartDate());
			frequencyPulldown.setSelectedItem(s.getFrequencyType());
			if (s.getFrequencyType().equals(TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE.toString()))
				monthlyDateChooser.setSelectedIndex(s.getScheduleDay() - 1);
			if (s.getFrequencyType().equals(TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DAY_OF_WEEK.toString()))			
				monthlyFirstDayChooser.setSelectedIndex(s.getScheduleDay());
			if (s.getFrequencyType().equals(TranslateKeys.SCHEDULE_FREQUENCY_WEEKLY.toString()))
				weeklyDayChooser.setSelectedIndex(s.getScheduleDay());
			if (s.getFrequencyType().equals(TranslateKeys.SCHEDULE_FREQUENCY_BIWEEKLY.toString()))
				biWeeklyDayChooser.setSelectedIndex(s.getScheduleDay());
			if (s.getFrequencyType().equals(TranslateKeys.SCHEDULE_FREQUENCY_MULTIPLE_WEEKS_EVERY_MONTH.toString()))
				multipleWeeksDayChooser.setSelectedIndex(s.getScheduleDay());
			if (s.getFrequencyType().equals(TranslateKeys.SCHEDULE_FREQUENCY_MULTIPLE_MONTHS_EVERY_YEAR.toString()))
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
		if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE.toString())){
			//To make it nicer to read in the data file, 
			// we add 1 to the index.  Don't forget to 
			// subtract one when we load it!
			return monthlyDateChooser.getSelectedIndex() + 1;
		}
		else if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DAY_OF_WEEK.toString())){
			return monthlyFirstDayChooser.getSelectedIndex();
		}
		else if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.SCHEDULE_FREQUENCY_WEEKLY.toString())){
			return weeklyDayChooser.getSelectedIndex();
		}
		else if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.SCHEDULE_FREQUENCY_BIWEEKLY.toString())){
			return biWeeklyDayChooser.getSelectedIndex();
		}
		else if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.SCHEDULE_FREQUENCY_EVERY_DAY.toString())){
			return 0; //We don't use scheduleDay if it is every day
		}
		else if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.SCHEDULE_FREQUENCY_EVERY_WEEKDAY.toString())){
			return 0; //We don't use scheduleDay if it is every weekday
		}
		else if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.SCHEDULE_FREQUENCY_MULTIPLE_WEEKS_EVERY_MONTH.toString())){
			return multipleWeeksDayChooser.getSelectedIndex();
		}
		else if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.SCHEDULE_FREQUENCY_MULTIPLE_MONTHS_EVERY_YEAR.toString())){
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
		if (frequencyPulldown.getSelectedItem().equals(TranslateKeys.SCHEDULE_FREQUENCY_MULTIPLE_MONTHS_EVERY_YEAR.toString())){
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

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(okButton)){
			if (Const.DEVEL) Log.debug("Schedule week: "+ getScheduleWeek());

			if (ScheduleModifyDialog.this.ensureInfoCorrect()){
				String[] options = new String[2];
				options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);
				options[1] = Translate.getInstance().get(TranslateKeys.BUTTON_CANCEL);

				if (!ScheduleModifyDialog.this.startDateChooser.getDate().before(new Date())
						|| schedule != null		//If the schedule has already been defined, we won't bother people again 
						|| JOptionPane.showOptionDialog(ScheduleModifyDialog.this, 
								Translate.getInstance().get(TranslateKeys.START_DATE_IN_THE_PAST), 
								Translate.getInstance().get(TranslateKeys.START_DATE_IN_THE_PAST_TITLE), 
								JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.PLAIN_MESSAGE,
								null,
								options,
								options[0]) == JOptionPane.OK_OPTION){
					ScheduleModifyDialog.this.saveSchedule();
					ScheduleController.checkForScheduledActions();
//					MainFrame.getInstance().updateContent();
//					TransactionsFrame.updateAllTransactionWindows();
					ScheduleModifyDialog.this.closeWindow();
				}
				else
					if (Const.DEVEL) Log.debug("Cancelled from either start date in the past, or info not correct");
			}
			else {
				String[] options = new String[1];
				options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

				JOptionPane.showOptionDialog(ScheduleModifyDialog.this, 
						Translate.getInstance().get(TranslateKeys.SCHEDULED_NOT_ENOUGH_INFO),
						Translate.getInstance().get(TranslateKeys.SCHEDULED_NOT_ENOUGH_INFO_TITLE),
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null,
						options,
						options[0]);
			}
		}
		else if (e.getSource().equals(cancelButton)){
			ScheduleModifyDialog.this.closeWindow();
		}
	}

	public AbstractDialog clear(){
		return this;
	}

	public AbstractDialog updateButtons(){
		return this;
	}
}
