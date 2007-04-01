/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.components.EditableTransaction;
import org.homeunix.thecave.moss.gui.JScrollingComboBox;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractDialog;
import org.homeunix.thecave.moss.gui.hint.JHintTextArea;
import org.homeunix.thecave.moss.gui.hint.JHintTextField;
import org.homeunix.thecave.moss.util.DateUtil;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

import com.toedter.calendar.JDateChooser;

public abstract class ScheduleModifyDialogLayout extends AbstractBuddiDialog {
	public static final long serialVersionUID = 0;

	protected final JButton okButton;
	protected final JButton cancelButton;

	protected final JHintTextField scheduleName;
	
	protected final JHintTextArea message;
	
	protected final JScrollingComboBox frequencyPulldown;
	protected final JScrollingComboBox weeklyDayChooser;
	protected final JScrollingComboBox biWeeklyDayChooser;
	protected final JScrollingComboBox monthlyDateChooser;
	protected final JScrollingComboBox monthlyFirstDayChooser;
	protected final JScrollingComboBox multipleWeeksDayChooser;
	protected final JScrollingComboBox multipleMonthsDateChooser;

	protected final JDateChooser startDateChooser;

	protected final JCheckBox multipleWeeksMonthlyFirstWeek;
	protected final JCheckBox multipleWeeksMonthlySecondWeek;
	protected final JCheckBox multipleWeeksMonthlyThirdWeek;
	protected final JCheckBox multipleWeeksMonthlyFourthWeek;

	protected final JCheckBox multipleMonthsYearlyJanuary;
	protected final JCheckBox multipleMonthsYearlyFebruary;
	protected final JCheckBox multipleMonthsYearlyMarch;
	protected final JCheckBox multipleMonthsYearlyApril;
	protected final JCheckBox multipleMonthsYearlyMay;
	protected final JCheckBox multipleMonthsYearlyJune;
	protected final JCheckBox multipleMonthsYearlyJuly;
	protected final JCheckBox multipleMonthsYearlyAugust;
	protected final JCheckBox multipleMonthsYearlySeptember;
	protected final JCheckBox multipleMonthsYearlyOctober;
	protected final JCheckBox multipleMonthsYearlyNovember;
	protected final JCheckBox multipleMonthsYearlyDecember;
	
	protected final Vector<JCheckBox> checkBoxes = new Vector<JCheckBox>();
	
	protected final EditableTransaction transaction;
	
	protected final CardLayout cardLayout;
	protected final JPanel cardHolder;

	protected ScheduleModifyDialogLayout(Frame owner){
		super(owner);

		okButton = new JButton(Translate.getInstance().get(TranslateKeys.OK));
		cancelButton = new JButton(Translate.getInstance().get(TranslateKeys.CANCEL));

		transaction = new EditableTransaction(null);

		scheduleName = new JHintTextField(Translate.getInstance().get(TranslateKeys.SCHEDULED_ACTION_NAME));
		
		message = new JHintTextArea(Translate.getInstance().get(TranslateKeys.MESSAGE_HINT));
		message.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_SCHEDULED_MESSAGE));
		JScrollPane messageScroller = new JScrollPane(message);
		messageScroller.setPreferredSize(new Dimension(100, 100));
		
		//Create the check boxes for Multiple Weeks each Month.
		multipleWeeksMonthlyFirstWeek = new JCheckBox(Translate.getInstance().get(TranslateKeys.FIRST_WEEK.toString()));
		multipleWeeksMonthlySecondWeek = new JCheckBox(Translate.getInstance().get(TranslateKeys.SECOND_WEEK.toString()));
		multipleWeeksMonthlyThirdWeek = new JCheckBox(Translate.getInstance().get(TranslateKeys.THIRD_WEEK.toString()));
		multipleWeeksMonthlyFourthWeek = new JCheckBox(Translate.getInstance().get(TranslateKeys.FOURTH_WEEK.toString()));

		//Create the check boxes for Multiple Months each Year.
		multipleMonthsYearlyJanuary = new JCheckBox(Translate.getInstance().get(TranslateKeys.JANUARY.toString()));
		multipleMonthsYearlyFebruary = new JCheckBox(Translate.getInstance().get(TranslateKeys.FEBRUARY.toString()));
		multipleMonthsYearlyMarch = new JCheckBox(Translate.getInstance().get(TranslateKeys.MARCH.toString()));
		multipleMonthsYearlyApril = new JCheckBox(Translate.getInstance().get(TranslateKeys.APRIL.toString()));
		multipleMonthsYearlyMay = new JCheckBox(Translate.getInstance().get(TranslateKeys.MAY.toString()));
		multipleMonthsYearlyJune = new JCheckBox(Translate.getInstance().get(TranslateKeys.JUNE.toString()));
		multipleMonthsYearlyJuly = new JCheckBox(Translate.getInstance().get(TranslateKeys.JULY.toString()));
		multipleMonthsYearlyAugust = new JCheckBox(Translate.getInstance().get(TranslateKeys.AUGUST.toString()));
		multipleMonthsYearlySeptember = new JCheckBox(Translate.getInstance().get(TranslateKeys.SEPTEMBER.toString()));
		multipleMonthsYearlyOctober = new JCheckBox(Translate.getInstance().get(TranslateKeys.OCTOBER.toString()));
		multipleMonthsYearlyNovember = new JCheckBox(Translate.getInstance().get(TranslateKeys.NOVEMEBER.toString()));
		multipleMonthsYearlyDecember = new JCheckBox(Translate.getInstance().get(TranslateKeys.DECEMBER.toString()));

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
		frequencyPulldownChoices.add(TranslateKeys.MONTHLY_BY_DATE.toString());
		frequencyPulldownChoices.add(TranslateKeys.MONTHLY_BY_DAY_OF_WEEK.toString());
		frequencyPulldownChoices.add(TranslateKeys.WEEKLY.toString());
		frequencyPulldownChoices.add(TranslateKeys.BIWEEKLY.toString());
		frequencyPulldownChoices.add(TranslateKeys.EVERY_DAY.toString());
		frequencyPulldownChoices.add(TranslateKeys.EVERY_WEEKDAY.toString());		
		frequencyPulldownChoices.add(TranslateKeys.MULTIPLE_WEEKS_EVERY_MONTH.toString());
		frequencyPulldownChoices.add(TranslateKeys.MULTIPLE_MONTHS_EVERY_YEAR.toString());
		frequencyPulldown = new JScrollingComboBox(frequencyPulldownChoices);
		
		Vector<String> weeklyDayChooserChoices = new Vector<String>();
		weeklyDayChooserChoices.add(TranslateKeys.SUNDAY.toString());
		weeklyDayChooserChoices.add(TranslateKeys.MONDAY.toString());
		weeklyDayChooserChoices.add(TranslateKeys.TUESDAY.toString());
		weeklyDayChooserChoices.add(TranslateKeys.WEDNESDAY.toString());
		weeklyDayChooserChoices.add(TranslateKeys.THURSDAY.toString());
		weeklyDayChooserChoices.add(TranslateKeys.FRIDAY.toString());
		weeklyDayChooserChoices.add(TranslateKeys.SATURDAY.toString());
		weeklyDayChooser = new JScrollingComboBox(weeklyDayChooserChoices);

		//We use the same one for BiWeekly as Weekly.
		biWeeklyDayChooser = new JScrollingComboBox(weeklyDayChooserChoices);
		
		Vector<String> monthlyDateChooserChoices = new Vector<String>();
		monthlyDateChooserChoices.add(TranslateKeys.FIRST.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SECOND.toString());
		monthlyDateChooserChoices.add(TranslateKeys.THIRD.toString());
		monthlyDateChooserChoices.add(TranslateKeys.FOURTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.FIFTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SIXTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SEVENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.EIGHTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.NINETH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.TENTHS.toString());
		monthlyDateChooserChoices.add(TranslateKeys.ELEVENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.TWELFTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.THIRTEENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.FOURTEENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.FIFTEENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SIXTEENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.SEVENTEENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.EIGHTEENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.NINETEENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.TWENTIETH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.TWENTYFIRST.toString());
		monthlyDateChooserChoices.add(TranslateKeys.TWENTYSECOND.toString());
		monthlyDateChooserChoices.add(TranslateKeys.TWENTYTHIRD.toString());
		monthlyDateChooserChoices.add(TranslateKeys.TWENTYFOURTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.TWENTYFIFTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.TWENTYSIXTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.TWENTYSEVENTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.TWENTYEIGHTH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.TWENTYNINETH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.THIRTIETH.toString());
		monthlyDateChooserChoices.add(TranslateKeys.THIRTYFIRST.toString());		

		monthlyDateChooser = new JScrollingComboBox(monthlyDateChooserChoices);
		
		Vector<String> monthlyFirstDayChooserChoices = new Vector<String>();
		monthlyFirstDayChooserChoices.add(TranslateKeys.FIRST_SUNDAY.toString());
		monthlyFirstDayChooserChoices.add(TranslateKeys.FIRST_MONDAY.toString());
		monthlyFirstDayChooserChoices.add(TranslateKeys.FIRST_TUESDAY.toString());
		monthlyFirstDayChooserChoices.add(TranslateKeys.FIRST_WEDNESDAY.toString());
		monthlyFirstDayChooserChoices.add(TranslateKeys.FIRST_THURSDAY.toString());
		monthlyFirstDayChooserChoices.add(TranslateKeys.FIRST_FRIDAY.toString());
		monthlyFirstDayChooserChoices.add(TranslateKeys.FIRST_SATURDAY.toString());
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
		cardHolder.add(monthly, TranslateKeys.MONTHLY_BY_DATE.toString());


		//One Day Every Month Card
		JPanel oneDayMonthly = new JPanel(new FlowLayout(FlowLayout.LEFT));
		oneDayMonthly.add(new JLabel(Translate.getInstance().get(TranslateKeys.AND_REPEATING_ON_THE)));
		oneDayMonthly.add(monthlyFirstDayChooser);
		oneDayMonthly.add(new JLabel(Translate.getInstance().get(TranslateKeys.OF_EACH_MONTH)));
		cardHolder.add(oneDayMonthly, TranslateKeys.MONTHLY_BY_DAY_OF_WEEK.toString());

		//Weekly card
		JPanel weekly = new JPanel(new FlowLayout(FlowLayout.LEFT));
		weekly.add(new JLabel(Translate.getInstance().get(TranslateKeys.AND_REPEATING_EVERY)));
		weekly.add(weeklyDayChooser);
		cardHolder.add(weekly, TranslateKeys.WEEKLY.toString());
		
		//BiWeekly card
		JPanel biweekly = new JPanel(new FlowLayout(FlowLayout.LEFT));
		biweekly.add(new JLabel(Translate.getInstance().get(TranslateKeys.AND_REPEATING_EVERY)));
		biweekly.add(biWeeklyDayChooser);
		cardHolder.add(biweekly, TranslateKeys.BIWEEKLY.toString());
		
		//Blank Panel - for Every Weekday, and others with no config options
		cardHolder.add(new JPanel(), TranslateKeys.EVERY_WEEKDAY.toString());
		cardHolder.add(new JPanel(), TranslateKeys.EVERY_DAY.toString());
		
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
		cardHolder.add(multipleWeeksMonthly, TranslateKeys.MULTIPLE_WEEKS_EVERY_MONTH.toString());

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
		cardHolder.add(multipleMonthsYearly, TranslateKeys.MULTIPLE_MONTHS_EVERY_YEAR.toString());

		
		//Done all the fancy stuff... now just put all the panels together
		JPanel transactionPanel = new JPanel(new BorderLayout());
		transactionPanel.setBorder(BorderFactory.createEmptyBorder(7, 0, 10, 0));
		transactionPanel.add(transaction, BorderLayout.NORTH);
		
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
			mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 12, 12, 12));
			messageScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			messageScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}
		
		this.setLayout(new BorderLayout());
		this.setResizable(true);
		this.setTitle(Translate.getInstance().get(TranslateKeys.SCHEDULED_ACTION));
		this.add(mainPanel);
		this.getRootPane().setDefaultButton(okButton);
	}

	protected abstract String getType();

	public AbstractDialog clear(){
		return this;
	}

	public AbstractDialog updateButtons(){
		return this;
	}
}
