/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.components.EditableTransaction;
import org.homeunix.thecave.moss.util.DateUtil;

import com.toedter.calendar.JDateChooser;

public abstract class ScheduleModifyDialogLayout extends AbstractDialog {
	public static final long serialVersionUID = 0;

	protected final JButton okButton;
	protected final JButton cancelButton;

	protected final JTextField scheduleName;
	protected final JComboBox frequencyPulldown;
	protected final JComboBox schedulePulldown;
	protected final JComboBox monthFreqPulldown;

	protected final JDateChooser startDateChooser;
	protected final JDateChooser endDateChooser;  //added by Nicky

	protected JCheckBox firstWeek_dayButton;
	protected JCheckBox secondWeek_dayButton;
	protected JCheckBox thirdWeek_dayButton;
	protected JCheckBox fourthWeek_dayButton;

	protected final EditableTransaction transaction;

	protected final DefaultComboBoxModel scheduleModel;

	protected ScheduleModifyDialogLayout(Frame owner){
		super(owner);

		okButton = new JButton(Translate.getInstance().get(TranslateKeys.OK));
		cancelButton = new JButton(Translate.getInstance().get(TranslateKeys.CANCEL));

		transaction = new EditableTransaction(null);

		JLabel frequencyPulldownLabel = new JLabel(Translate.getInstance().get(TranslateKeys.FREQUENCY));
		JLabel schedulePulldownLabel = new JLabel(Translate.getInstance().get(TranslateKeys.EVERY));
		JLabel monthFreqPulldownLabel = new JLabel("Every month");
		JLabel startDateChooserLabel = new JLabel(Translate.getInstance().get(TranslateKeys.START_DATE));
		JLabel scheduleNameLabel = new JLabel(Translate.getInstance().get(TranslateKeys.NAME));

		JLabel endDateChooserLabel = new JLabel(Translate.getInstance().get(TranslateKeys.END_DATE));

//		Create the check boxes.
		firstWeek_dayButton = new JCheckBox("First Week Day");
		//firstWeek_dayButton.setSelected(true);

		secondWeek_dayButton = new JCheckBox("Second Week Day");
		//secondWeek_dayButton.setSelected(true);

		thirdWeek_dayButton = new JCheckBox("Third Week Day");
		//thirdWeek_dayButton.setSelected(true);

		fourthWeek_dayButton = new JCheckBox("Fourth Week Day");
		//fourthWeek_dayButton.setSelected(true);

		Vector<String> frequencyChoices = new Vector<String>();

		frequencyChoices.add(TranslateKeys.WEEK.toString());
		frequencyChoices.add(TranslateKeys.MONTH.toString());
		//The new options have been added here
		frequencyChoices.add(TranslateKeys.DAY_MONTH.toString());
		frequencyChoices.add(TranslateKeys.EVERY_WEEKDAY.toString());
		frequencyChoices.add(TranslateKeys.MULTIPLE_WEEKS_EVERY_MONTH.toString());
		frequencyChoices.add(TranslateKeys.MULTIPLE_MONTHS_EVERY_YEAR.toString());

		scheduleName = new JTextField();
		frequencyPulldown = new JComboBox(frequencyChoices);
		//frequencyPulldown.setSelectedIndex(3);
		schedulePulldown = new JComboBox();

		Vector<Integer> monthChoices = new Vector<Integer>();

		for(int i=1;i<=12;i++)
		{
			monthChoices.add(new Integer(i));
		}

		monthFreqPulldown = new JComboBox(monthChoices);

		startDateChooser = new JDateChooser(DateUtil.getNextDay(new Date()), PrefsInstance.getInstance().getPrefs().getDateFormat());
		endDateChooser = new JDateChooser(DateUtil.getNextDay(new Date()), PrefsInstance.getInstance().getPrefs().getDateFormat());

		scheduleModel = new DefaultComboBoxModel();
		schedulePulldown.setModel(scheduleModel);


		frequencyPulldown.setRenderer(new DefaultListCellRenderer(){
			public static final long serialVersionUID = 0;
			public Component getListCellRendererComponent(JList list, Object obj, int index, boolean isSelected, boolean cellHasFocus) {
				this.setText(Translate.getInstance().get(obj.toString()));				
				return this;
			}
		});

		schedulePulldown.setRenderer(new DefaultListCellRenderer(){
			public static final long serialVersionUID = 0;
			public Component getListCellRendererComponent(JList list, Object obj, int index, boolean isSelected, boolean cellHasFocus) {
				this.setText(Translate.getInstance().get(obj.toString()));				
				return this;
			}
		});

		Dimension buttonSize = new Dimension(Math.max(100, cancelButton.getPreferredSize().width), cancelButton.getPreferredSize().height);
		okButton.setPreferredSize(buttonSize);
		cancelButton.setPreferredSize(buttonSize);

		Dimension pulldownSize = new Dimension(Math.max(150, frequencyPulldown.getPreferredSize().width), frequencyPulldown.getPreferredSize().height);
		frequencyPulldown.setPreferredSize(pulldownSize);
		schedulePulldown.setPreferredSize(pulldownSize);

		Dimension textSize = new Dimension(pulldownSize.width, startDateChooser.getPreferredSize().height);
		startDateChooser.setPreferredSize(textSize);
		scheduleName.setPreferredSize(textSize);

		/*
		JPanel textPanel = new JPanel(new GridLayout(0, 2));
		textPanel.add(scheduleNameLabel);
		textPanel.add(scheduleName);
		textPanel.add(frequencyPulldownLabel);
		textPanel.add(frequencyPulldown);
		textPanel.add(schedulePulldownLabel);
		textPanel.add(schedulePulldown);
		textPanel.add(startDateChooserLabel);
		textPanel.add(startDateChooser);

		JPanel textPanelHolder = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		textPanelHolder.add(textPanel);
		 */

		/* added by Nicky*/
		JPanel textPanelHolder = new JPanel(new FlowLayout(FlowLayout.LEFT));
		textPanelHolder.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		textPanelHolder.add(scheduleNameLabel);
		textPanelHolder.add(scheduleName);
		//End of edit by Nicky
		/* added by Nicky*/

		JPanel repeatPanel = new JPanel(new GridLayout(0,2));

		repeatPanel.add(startDateChooserLabel);
		repeatPanel.add(startDateChooser);
		repeatPanel.add(endDateChooserLabel);
		repeatPanel.add(endDateChooser);
		repeatPanel.add(frequencyPulldownLabel);
		repeatPanel.add(frequencyPulldown);
		repeatPanel.add(schedulePulldownLabel);
		repeatPanel.add(schedulePulldown);
		repeatPanel.add(monthFreqPulldownLabel);
		repeatPanel.add(monthFreqPulldown);

		JPanel repeatPanelHolder = new JPanel(new FlowLayout(FlowLayout.LEFT));
		repeatPanelHolder.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		repeatPanelHolder.add(repeatPanel);


		JPanel checkPanel = new JPanel(new GridLayout(0, 1));
		checkPanel.add(firstWeek_dayButton);
		checkPanel.add(secondWeek_dayButton);
		checkPanel.add(thirdWeek_dayButton);
		checkPanel.add(fourthWeek_dayButton);

		JPanel checkPanelHolder = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		checkPanelHolder.setBorder(BorderFactory.createEmptyBorder(0, 17, 0, 0));
		checkPanelHolder.add(checkPanel);

		JPanel textPanelSpacer = new JPanel(new FlowLayout(FlowLayout.LEFT)/*GridLayout(0,1)*/);
		textPanelSpacer.setBorder(BorderFactory.createEmptyBorder(7, 17, 0, 17));

		textPanelSpacer.add(textPanelHolder);
		textPanelSpacer.add(transaction); //Changed by Nicky from South to North
		textPanelSpacer.add(repeatPanelHolder);

		textPanelSpacer.add(checkPanelHolder);

		//end edit	
		/*
		JPanel textPanelSpacer = new JPanel(new BorderLayout());
		textPanelSpacer.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		textPanelSpacer.add(textPanelHolder, BorderLayout.NORTH);
		textPanelSpacer.add(transaction); //Changed by Nicky from South to North
		textPanelSpacer.add(repeatPanelHolder, BorderLayout.CENTER);

        textPanelSpacer.add(checkPanelHolder, BorderLayout.SOUTH);
		 */





		JPanel mainBorderPanel = new JPanel();
		mainBorderPanel.setLayout(new BorderLayout());
		mainBorderPanel.setBorder(BorderFactory.createTitledBorder((String) null));
		mainBorderPanel.add(textPanelSpacer);
		//mainBorderPanel.add(checkPanel);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);

		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 12, 12, 12));

		mainPanel.add(mainBorderPanel, BorderLayout.CENTER);
		//	mainPanel.add(checkPanel, BorderLayout.CENTER);

		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		this.setResizable(true);
		this.setTitle(Translate.getInstance().get(TranslateKeys.SCHEDULED_TRANSACTION));
		this.add(mainPanel);
		this.getRootPane().setDefaultButton(okButton);
		this.setPreferredSize(new Dimension(630, 400));

		//Call the method to add actions to the buttons
		initActions();		
	}

	protected abstract String getType();

	public AbstractDialog clearContent(){

		return this;
	}

	public AbstractDialog updateButtons(){

		return this;
	}

}
