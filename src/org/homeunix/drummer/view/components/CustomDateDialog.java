/*
 * Created on May 20, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.sourceforge.buddi.api.plugin.BuddiPanelPlugin;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.plugins.BuddiPluginHelper;
import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;
import org.homeunix.drummer.prefs.Interval;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.AbstractBuddiDialog;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractDialog;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardWindow;
import org.homeunix.thecave.moss.util.DateUtil;
import org.homeunix.thecave.moss.util.Log;

import com.toedter.calendar.JDateChooser;

/**
 * The dialog which allows users to choose a custom date range, or
 * start / end date only.  Designed to launch a plugin when the user 
 * has chosen the date.
 * 
 * @author wyatt
 *
 */
public class CustomDateDialog extends AbstractBuddiDialog {
	public static final long serialVersionUID = 0;

	protected final JButton okButton;
	protected final JButton cancelButton;

	protected final JLabel mainLabel;
	protected final JLabel middleLabel;

	protected final JDateChooser startDateChooser;
	protected final JDateChooser endDateChooser;

	protected final BuddiPanelPlugin plugin;


	public CustomDateDialog(Frame owner, BuddiPanelPlugin plugin){
		super(owner);

		this.plugin = plugin;

		okButton = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_OK));
		cancelButton = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_CANCEL));

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

		//Set the start date to be (by default) a time in the past
		// back as far as the current interval.  For instance, if the 
		// interval is set to 1 month, and the day is Feb 15, set the 
		// date to Jan 1.
		// This is not guaranteed to give perfect results every time,
		// but if we are giving the user a choice in the date interva;,
		// we may as well start at a time in the past rather than just
		// give them the current date, as we have done before now.
		// Added to address feature request #1649972.
		Interval i = PrefsInstance.getInstance().getSelectedInterval();
		if (i.isDays()){
			startDateChooser.setDate(
					DateUtil.getNextNDay(new Date(), (int) i.getLength() * -1));
		}
		else {
			startDateChooser.setDate(
					DateUtil.getBeginOfMonth(new Date(), (int) i.getLength() * -1));								
		}			

		
		setVisibility();
	}

	private void setVisibility(){
		if (plugin.getDateRangeType().equals(DateRangeType.INTERVAL)){
			mainLabel.setText(Translate.getInstance().get(TranslateKeys.REPORT_BETWEEN));
//			middleLabel.setText(Translate.getInstance().get(TranslateKeys.AND));					
			middleLabel.setText(" - ");
		}
		else{
			mainLabel.setText(Translate.getInstance().get(TranslateKeys.REPORT_AS_OF_DATE));
			middleLabel.setVisible(false);

			if (plugin.getDateRangeType().equals(DateRangeType.START_ONLY)){
				endDateChooser.setVisible(false);
			}
			else if (plugin.getDateRangeType().equals(DateRangeType.END_ONLY)){
				startDateChooser.setVisible(false);
			}
		}
	}


	public StandardWindow clear() {
		// TODO Auto-generated method stub
		return null;
	}

	public AbstractDialog init() {
		okButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				Date startDate, endDate;

//				if (plugin.getDateRangeType().equals(DateRangeType.INTERVAL)){
				startDate = DateUtil.getStartOfDay(startDateChooser.getDate());
				endDate = DateUtil.getEndOfDay(endDateChooser.getDate());
//				}
//				else if (plugin.getDateRangeType().equals(DateRangeType.START_ONLY)){
//				startDate = DateUtil.getStartOfDay(startDateChooser.getDate());
//				}
//				else if (plugin.getDateRangeType().equals(DateRangeType.END_ONLY)){
//				endDate = DateUtil.getEndOfDay(endDateChooser.getDate());
//				}


				if (plugin.getDateRangeType().equals(DateRangeType.INTERVAL)
						&& endDate.before(startDate)){
					JOptionPane.showMessageDialog(
							null, 
							Translate.getInstance().get(TranslateKeys.START_DATE_AFTER_END_DATE), 
							Translate.getInstance().get(TranslateKeys.REPORT_DATE_ERROR), 
							JOptionPane.ERROR_MESSAGE
					);
					return;
				}

				if (Const.DEVEL) Log.debug("Getting transactions between " + startDate + " and " + endDate);

				CustomDateDialog.this.setVisible(false);

				BuddiPluginHelper.openNewPanelPluginWindow(plugin, startDate, endDate);
			}
		});

		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				CustomDateDialog.this.setVisible(false);
			}
		});

		return this;
	}

	public void actionPerformed(ActionEvent e) {
	}
	public StandardWindow updateButtons() {
		return null;
	}
	public StandardWindow updateContent() {
		return null;
	}
}
