/*
 * Created on May 20, 2006 by wyatt
 */
package org.homeunix.drummer.view.layout.reports;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.homeunix.drummer.Translate;
import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.controller.model.PrefsInstance;
import org.homeunix.drummer.view.AbstractBudgetDialog;

import com.toedter.calendar.JDateChooser;

public abstract class CustomDateDialogLayout extends AbstractBudgetDialog {
	
	protected final JButton okButton;
	protected final JButton cancelButton;
	
	protected final JLabel mainLabel;
	protected final JLabel middleLabel;
	
	protected final JDateChooser startDateChooser;
	protected final JDateChooser endDateChooser;

	
	public CustomDateDialogLayout(Frame owner){
		super(owner);
		
		okButton = new JButton(Translate.getInstance().get(TranslateKeys.OK));
		cancelButton = new JButton(Translate.getInstance().get(TranslateKeys.CANCEL));
		
		Dimension buttonSize = new Dimension(100, okButton.getPreferredSize().height);
		okButton.setPreferredSize(buttonSize);
		cancelButton.setPreferredSize(buttonSize);
		
		startDateChooser = new JDateChooser();
		endDateChooser = new JDateChooser();
		
		startDateChooser.setDateFormatString(PrefsInstance.getInstance().getPrefs().getDateFormat());
		endDateChooser.setDateFormatString(PrefsInstance.getInstance().getPrefs().getDateFormat());
		
		Dimension textFieldSize = new Dimension(180, startDateChooser.getPreferredSize().height);
		
		startDateChooser.setPreferredSize(textFieldSize);
		endDateChooser.setPreferredSize(textFieldSize);
		
		startDateChooser.setDate(new Date());
		endDateChooser.setDate(new Date());

		JPanel r1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel r2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		mainLabel = new JLabel();
		middleLabel = new JLabel(); 
		
		r1.add(mainLabel);
		
		r2.add(startDateChooser);
		r2.add(middleLabel);
		r2.add(endDateChooser);
		
		JPanel textPanelSpacer = new JPanel();
		textPanelSpacer.setLayout(new BoxLayout(textPanelSpacer, BoxLayout.Y_AXIS));
		textPanelSpacer.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		textPanelSpacer.add(r1);
		textPanelSpacer.add(r2);
		
		JPanel mainBorderPanel = new JPanel();
		mainBorderPanel.setLayout(new BorderLayout());
		mainBorderPanel.setBorder(BorderFactory.createTitledBorder(""));
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
		this.setTitle(Translate.getInstance().get(TranslateKeys.CHOOSE_DATE_INTERVAL));
		
		setVisibility();
		
		//Call the method to add actions to the buttons
		initActions();	
	}
	
	protected abstract void setVisibility();
}
