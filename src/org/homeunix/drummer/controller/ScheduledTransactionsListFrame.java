/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Schedule;
import org.homeunix.drummer.view.ScheduledTransactionsListFrameLayout;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractDialog;
import org.homeunix.thecave.moss.util.Log;

public class ScheduledTransactionsListFrame extends ScheduledTransactionsListFrameLayout {
	public static final long serialVersionUID = 0;

	public ScheduledTransactionsListFrame(){
		super(MainBuddiFrame.getInstance());
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

		Vector<Schedule> scheduledTransactions = DataInstance.getInstance().getScheduledTransactions();
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
				DataInstance.getInstance().removeSchedule(s);
				DataInstance.getInstance().saveDataModel();
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
