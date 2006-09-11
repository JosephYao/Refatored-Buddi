/*
 * Created on May 20, 2006 by wyatt
 */
package org.homeunix.drummer.controller.reports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractDialog;
import org.homeunix.drummer.view.CustomDateDialogLayout;
import org.homeunix.drummer.view.ReportPanelLayout.ReportType;

public class CustomStartDateDialog extends CustomDateDialogLayout {
	public static final long serialVersionUID = 0;
	
	private ReportType reportType;

	public CustomStartDateDialog(JFrame parent, ReportType reportType){
		super(parent);
		
		this.reportType = reportType;
	}
	
	@Override
	protected AbstractDialog clearContent() {
		return this;
	}

	@Override
	protected AbstractDialog initActions() {
		okButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				Date startDate;
				startDate = DateUtil.getStartOfDay(startDateChooser.getDate());
				
				if (startDate.after(new Date())){
					JOptionPane.showMessageDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.DATE_AFTER_TODAY), 
							Translate.getInstance().get(TranslateKeys.REPORT_DATE_ERROR), 
							JOptionPane.ERROR_MESSAGE
					);
					return;
				}
								
				if (reportType.equals(ReportType.NETWORTH_OVER_TIME))
					new NetWorthOverTimeGraphFrame(startDate);
				else
					Log.debug("Don't know what to do with type " + reportType);
				//TODO Add more types as needed...
				//else if (reportType.equals(ReportType.meetBudget))
		
				CustomStartDateDialog.this.setVisible(false);
			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				CustomStartDateDialog.this.setVisible(false);
			}
		});
		
		return this;
	}

	@Override
	protected AbstractDialog initContent() {
		return this;
	}

	@Override
	public AbstractDialog updateButtons() {
		return this;
	}

	@Override
	protected AbstractDialog updateContent() {
		return this;
	}
	
	protected void setVisibility(){
		mainLabel.setText(Translate.getInstance().get(TranslateKeys.REPORT_AS_OF_DATE));
		middleLabel.setVisible(false);
		
		endDateChooser.setVisible(false);
	}
}
