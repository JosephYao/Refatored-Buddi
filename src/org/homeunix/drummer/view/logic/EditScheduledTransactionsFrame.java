/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.view.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import org.homeunix.drummer.controller.DataInstance;
import org.homeunix.drummer.model.Schedule;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractBudgetDialog;
import org.homeunix.drummer.view.layout.EditScheduledTransactionsFrameLayout;

public class EditScheduledTransactionsFrame extends EditScheduledTransactionsFrameLayout {
	public static final long serialVersionUID = 0;

	public EditScheduledTransactionsFrame(){
		super(MainBuddiFrame.getInstance());
	}
		
	@Override
	protected AbstractBudgetDialog initActions() {
		doneButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				EditScheduledTransactionsFrame.this.setVisible(false);
				EditScheduledTransactionsFrame.this.dispose();
			}
		});
		
		newButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new ModifyScheduleDialog(null).openWindow();	
			}
		});
		
		editButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Object o = list.getSelectedValue();
				if (o instanceof Schedule){
					Schedule s = (Schedule) o;
					new ModifyScheduleDialog(s).openWindow();
					Log.debug("Done editing.");
				}
			}
		});
		
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return this;
	}

	@Override
	protected AbstractBudgetDialog initContent() {
		updateContent();
		
		return this;
	}

	public AbstractBudgetDialog updateContent(){
		
		Vector<Schedule> scheduledTransactions = DataInstance.getInstance().getScheduledTransactions();
		list.setListData(scheduledTransactions);
		
		return this;
	}


}
