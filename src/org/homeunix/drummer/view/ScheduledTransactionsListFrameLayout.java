/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public abstract class ScheduledTransactionsListFrameLayout extends AbstractDialog {
	public static final long serialVersionUID = 0;
	
	protected final JButton doneButton;
	protected final JButton newButton;
	protected final JButton editButton;
	protected final JButton deleteButton;
	
	protected final JList list;
	
	protected ScheduledTransactionsListFrameLayout(Frame owner){
		super(owner);
		
		doneButton = new JButton(Translate.getInstance().get(TranslateKeys.DONE));
		newButton = new JButton(Translate.getInstance().get(TranslateKeys.NEW));
		editButton = new JButton(Translate.getInstance().get(TranslateKeys.EDIT));
		deleteButton = new JButton(Translate.getInstance().get(TranslateKeys.DELETE));
		
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		Dimension buttonSize = new Dimension(Math.max(100, deleteButton.getPreferredSize().width), deleteButton.getPreferredSize().height);
		doneButton.setPreferredSize(buttonSize);
		newButton.setPreferredSize(buttonSize);
		editButton.setPreferredSize(buttonSize);
		deleteButton.setPreferredSize(buttonSize);
		
		JScrollPane listScroller = new JScrollPane(list);
		
		JPanel scrollBorderPanel = new JPanel(new BorderLayout());
		scrollBorderPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		scrollBorderPanel.add(listScroller, BorderLayout.CENTER);
		
		JPanel scrollPanel = new JPanel(new BorderLayout());
		scrollPanel.setBorder(BorderFactory.createTitledBorder(Translate.getInstance().get(TranslateKeys.SCHEDULED_ACTIONS)));
		scrollPanel.add(scrollBorderPanel, BorderLayout.CENTER);
		
		JPanel editTransactionsButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		editTransactionsButtonPanel.add(newButton);
		editTransactionsButtonPanel.add(editButton);
		editTransactionsButtonPanel.add(deleteButton);
		
		scrollPanel.add(editTransactionsButtonPanel, BorderLayout.SOUTH);
		
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(doneButton);
		
		mainPanel.add(scrollPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setTitle(Translate.getInstance().get(TranslateKeys.EDIT_SCHEDULED_ACTIONS));
		this.setLayout(new BorderLayout());
		this.add(mainPanel);
		this.getRootPane().setDefaultButton(doneButton);
		
		if (OperatingSystemUtil.isMac()){
			mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 12, 12, 12));
			list.putClientProperty("Quaqua.List.style", "striped");
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}

		
		//Call the method to add actions to the buttons
		initActions();		
	}
	
	public AbstractDialog clearContent(){
		return this;
	}
		
	public AbstractDialog updateButtons(){
		
		editButton.setEnabled(list.getSelectedIndices().length > 0);
		deleteButton.setEnabled(list.getSelectedIndices().length > 0);
		
		return this;
	}
}
