/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view.layout;

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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.homeunix.drummer.Translate;
import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.view.AbstractBudgetDialog;
import org.homeunix.drummer.view.components.EditableTransaction;

import com.toedter.calendar.JDateChooser;

public abstract class ModifyScheduleDialogLayout extends AbstractBudgetDialog {
	public static final long serialVersionUID = 0;
	
	protected final JButton okButton;
	protected final JButton cancelButton;
	
	protected final JTextField scheduleName;
	protected final JComboBox frequencyPulldown;
	protected final JComboBox schedulePulldown;
	protected final JDateChooser startDateChooser;
	
	protected final EditableTransaction transaction;
	
	protected final DefaultComboBoxModel scheduleModel;
	
	protected ModifyScheduleDialogLayout(Frame owner){
		super(owner);
		
		okButton = new JButton(Translate.getInstance().get(TranslateKeys.OK));
		cancelButton = new JButton(Translate.getInstance().get(TranslateKeys.CANCEL));

		transaction = new EditableTransaction(null);
		
		JLabel frequencyPulldownLabel = new JLabel(Translate.getInstance().get(TranslateKeys.FREQUENCY));
		JLabel schedulePulldownLabel = new JLabel(Translate.getInstance().get(TranslateKeys.EVERY));
		JLabel startDateChooserLabel = new JLabel(Translate.getInstance().get(TranslateKeys.START_DATE));
		JLabel scheduleNameLabel = new JLabel(Translate.getInstance().get(TranslateKeys.NAME));
		
		Vector<String> frequencyChoices = new Vector<String>();
		frequencyChoices.add(TranslateKeys.WEEK.toString());
		frequencyChoices.add(TranslateKeys.MONTH.toString());
		
		scheduleName = new JTextField();
		frequencyPulldown = new JComboBox(frequencyChoices);
		schedulePulldown = new JComboBox();
		startDateChooser = new JDateChooser(DateUtil.getNextDay(new Date()));

		scheduleModel = new DefaultComboBoxModel();
		schedulePulldown.setModel(scheduleModel);
		
		frequencyPulldown.setRenderer(new DefaultListCellRenderer(){
			public static final long serialVersionUID = 0;
			public Component getListCellRendererComponent(JList list, Object obj, int index, boolean isSelected, boolean cellHasFocus) {
				if (obj instanceof String){
					String str = (String) obj;
					this.setText(Translate.getInstance().get(str));
				}
				else
					this.setText(obj.toString());
				
				return this;
			}
		});
		
		schedulePulldown.setRenderer(new DefaultListCellRenderer(){
			public static final long serialVersionUID = 0;
			public Component getListCellRendererComponent(JList list, Object obj, int index, boolean isSelected, boolean cellHasFocus) {
				if (obj instanceof String){
					String str = (String) obj;
					this.setText(Translate.getInstance().get(str));
				}
				else
					this.setText(obj.toString());
				
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
		
		JPanel textPanel = new JPanel(new GridLayout(0, 2));
		textPanel.add(scheduleNameLabel);
		textPanel.add(scheduleName);
		textPanel.add(frequencyPulldownLabel);
		textPanel.add(frequencyPulldown);
		textPanel.add(schedulePulldownLabel);
		textPanel.add(schedulePulldown);
		textPanel.add(startDateChooserLabel);
		textPanel.add(startDateChooser);
		
		JPanel textPanelSpacer = new JPanel(new BorderLayout());
		textPanelSpacer.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		textPanelSpacer.add(textPanel, BorderLayout.NORTH);
		textPanelSpacer.add(transaction, BorderLayout.SOUTH);
		
		JPanel mainBorderPanel = new JPanel();
		mainBorderPanel.setLayout(new BorderLayout());
		mainBorderPanel.setBorder(BorderFactory.createTitledBorder((String) null));
		mainBorderPanel.add(textPanelSpacer);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);
		
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 12, 12, 12));
		
		mainPanel.add(mainBorderPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		this.add(mainPanel);
		this.getRootPane().setDefaultButton(okButton);
		this.setPreferredSize(new Dimension(700, 300));
		
		//Call the method to add actions to the buttons
		initActions();		
	}
	
	protected abstract String getType();
	
	public AbstractBudgetDialog clearContent(){

		return this;
	}
	
	public AbstractBudgetDialog updateButtons(){
		
		return this;
	}

}
