/*
 * Created on May 20, 2006 by wyatt
 */
package org.homeunix.drummer.view.reports.layout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.homeunix.drummer.Translate;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.view.AbstractBudgetDialog;

public abstract class CustomDateDialogLayout extends AbstractBudgetDialog {
	
	protected final JButton okButton;
	protected final JButton cancelButton;
	
	protected final JLabel mainLabel;
	protected final JLabel middleLabel;
	
	protected final JFormattedTextField startDateCombo;
	protected final JFormattedTextField endDateCombo;

	
	public CustomDateDialogLayout(Frame owner){
		super(owner);
		
		okButton = new JButton(Translate.inst().get(Translate.OK));
		cancelButton = new JButton(Translate.inst().get(Translate.CANCEL));
		
		Dimension buttonSize = new Dimension(100, okButton.getPreferredSize().height);
		okButton.setPreferredSize(buttonSize);
		cancelButton.setPreferredSize(buttonSize);
		
		startDateCombo = new JFormattedTextField(Formatter.getInstance().getDateFormat());
		endDateCombo = new JFormattedTextField(Formatter.getInstance().getDateFormat());
		
		Dimension textFieldSize = new Dimension(120, startDateCombo.getPreferredSize().height);
		
		startDateCombo.setPreferredSize(textFieldSize);
		endDateCombo.setPreferredSize(textFieldSize);
		
		startDateCombo.setValue(new Date());
		endDateCombo.setValue(new Date());

		JPanel r1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel r2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		mainLabel = new JLabel();
		middleLabel = new JLabel(); 
		
		r1.add(mainLabel);
		
		r2.add(startDateCombo);
		r2.add(middleLabel);
		r2.add(endDateCombo);
		
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
		this.setTitle(Translate.inst().get(Translate.CHOOSE_DATE_INTERVAL));
		
		setVisibility();
		
		//Call the method to add actions to the buttons
		initActions();	
	}
	
	protected abstract void setVisibility();
}
