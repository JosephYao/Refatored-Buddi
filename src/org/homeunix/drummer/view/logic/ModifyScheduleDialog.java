/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.view.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.homeunix.drummer.Translate;
import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.model.Schedule;
import org.homeunix.drummer.view.AbstractBudgetDialog;
import org.homeunix.drummer.view.layout.ModifyScheduleDialogLayout;

public class ModifyScheduleDialog extends ModifyScheduleDialogLayout {
	public static final long serialVersionUID = 0;

	public ModifyScheduleDialog(Schedule schedule){
		super(MainBuddiFrame.getInstance());
		
		loadSchedule(schedule);
	}

	protected String getType(){
		return Translate.getInstance().get(TranslateKeys.ACCOUNT);
	}
		
	@Override
	protected AbstractBudgetDialog initActions() {
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ModifyScheduleDialog.this.saveSchedule();
				ModifyScheduleDialog.this.setVisible(false);
				ModifyScheduleDialog.this.dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ModifyScheduleDialog.this.setVisible(false);
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
		transaction.updateContent();
		
		return this;
	}
	
	private void saveSchedule(){
		//DataInstance.getInstance().
	}
	
	private void loadSchedule(Schedule s){
		
	}
}
