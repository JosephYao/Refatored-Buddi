/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.ScheduleController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Schedule;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractDialog;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardWindow;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class ScheduledTransactionsListFrame extends AbstractBuddiDialog {
	public static final long serialVersionUID = 0;
	
	private final JButton doneButton;
	private final JButton newButton;
	private final JButton editButton;
	private final JButton deleteButton;
	
	private final JList list;
	
	public ScheduledTransactionsListFrame(){
		super(MainFrame.getInstance());
		
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
			list.putClientProperty("Quaqua.List.style", "striped");
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}
	}

	public StandardWindow clear() {
		return this;
	}
	
	public AbstractDialog updateButtons(){
		
		editButton.setEnabled(list.getSelectedIndices().length > 0);
		deleteButton.setEnabled(list.getSelectedIndices().length > 0);
		
		return this;
	}
	
	public AbstractDialog init() {
		newButton.addActionListener(this);
		editButton.addActionListener(this);
		deleteButton.addActionListener(this);
		doneButton.addActionListener(this);

		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				ScheduledTransactionsListFrame.this.updateButtons();
			}
		});

		return this;
	}

	public AbstractDialog updateContent(){

		Vector<Schedule> scheduledTransactions = ScheduleController.getScheduledTransactions();
		list.setListData(scheduledTransactions);

		return this;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(newButton)){
			new ScheduleModifyDialog(null).openWindow();
			if (Const.DEVEL) Log.debug("Done creating");
			updateContent();
		}
		else if (e.getSource().equals(editButton)){
			Object o = list.getSelectedValue();
			if (o instanceof Schedule){
				Schedule s = (Schedule) o;
				new ScheduleModifyDialog(s).openWindow();
				if (Const.DEVEL) Log.debug("Done editing.");
				updateContent();
			}
		}
		else if (e.getSource().equals(deleteButton)){
			Object o = list.getSelectedValue();
			if (o instanceof Schedule){
				Schedule s = (Schedule) o;
				ScheduleController.removeSchedule(s);
				if (Const.DEVEL) Log.debug("Deleted schedule.");
			}
			else {
				Log.error("Schedule not selected.");
			}
			updateContent();
		}
		else if (e.getSource().equals(doneButton)){
			ScheduledTransactionsListFrame.this.setVisible(false);
			ScheduledTransactionsListFrame.this.dispose();
		}
	}
}
