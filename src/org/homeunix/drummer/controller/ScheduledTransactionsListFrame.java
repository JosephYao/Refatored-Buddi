/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Schedule;
import org.homeunix.drummer.view.AbstractDialog;
import org.homeunix.drummer.view.ScheduledTransactionsListFrameLayout;
import org.homeunix.thecave.moss.util.Log;

public class ScheduledTransactionsListFrame extends ScheduledTransactionsListFrameLayout {
	public static final long serialVersionUID = 0;

	public ScheduledTransactionsListFrame(){
		super(MainBuddiFrame.getInstance());
	}
		
	@Override
	protected AbstractDialog initActions() {
		doneButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ScheduledTransactionsListFrame.this.setVisible(false);
				ScheduledTransactionsListFrame.this.dispose();
			}
		});
		
		newButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new ScheduleModifyDialog(null).openWindow();
				if (Const.DEVEL) Log.debug("Done creating");
				updateContent();
			}
		});
		
		editButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Object o = list.getSelectedValue();
				if (o instanceof Schedule){
					Schedule s = (Schedule) o;
					new ScheduleModifyDialog(s).openWindow();
					if (Const.DEVEL) Log.debug("Done editing.");
					updateContent();
				}
			}
		});
		
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
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
		});
		
		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				ScheduledTransactionsListFrame.this.updateButtons();
			}
		});
		
		return this;
	}

	@Override
	protected AbstractDialog initContent() {
		updateContent();
		
		return this;
	}

	public AbstractDialog updateContent(){
		
		Vector<Schedule> scheduledTransactions = DataInstance.getInstance().getScheduledTransactions();
		list.setListData(scheduledTransactions);
		
		return this;
	}


}
