/*
 * Created on May 20, 2006 by wyatt
 */
package org.homeunix.drummer.controller.reports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractBudgetDialog;
import org.homeunix.drummer.view.CustomDateDialogLayout;
import org.homeunix.drummer.view.ReportPanelLayout.ReportType;

public class CustomEndDateDialog extends CustomDateDialogLayout {
	public static final long serialVersionUID = 0;
	
	private ReportType reportType;

	public CustomEndDateDialog(JFrame parent, ReportType reportType){
		super(parent);
		
		this.reportType = reportType;
	}
	
	@Override
	protected AbstractBudgetDialog clearContent() {
		return this;
	}

	@Override
	protected AbstractBudgetDialog initActions() {
		okButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				Date endDate;
				endDate = DateUtil.getEndOfDay(endDateChooser.getDate());
				
				if (endDate.after(DateUtil.getEndOfDay(new Date()))){
					JOptionPane.showMessageDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.DATE_AFTER_TODAY), 
							Translate.getInstance().get(TranslateKeys.REPORT_DATE_ERROR), 
							JOptionPane.ERROR_MESSAGE
					);
					return;
				}
				
				if (reportType.equals(ReportType.NETWORTH))
					new NetWorthGraphFrame(endDate);
				else
					Log.debug("Don't know what to do with type " + reportType);
				//TODO Add more types as needed...
		
				CustomEndDateDialog.this.setVisible(false);
			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				CustomEndDateDialog.this.setVisible(false);
			}
		});
		
		return this;
	}

	@Override
	protected AbstractBudgetDialog initContent() {
		return this;
	}

	@Override
	public AbstractBudgetDialog updateButtons() {
		return this;
	}

	@Override
	protected AbstractBudgetDialog updateContent() {
		return this;
	}
	
	protected void setVisibility(){
		mainLabel.setText(Translate.getInstance().get(TranslateKeys.REPORT_AS_OF_DATE));
		middleLabel.setVisible(false);
		
		startDateChooser.setVisible(false);
	}
}
