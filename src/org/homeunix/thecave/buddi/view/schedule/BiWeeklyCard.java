/*
 * Created on Aug 18, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.schedule;

import java.awt.FlowLayout;

import javax.swing.JLabel;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ScheduleFrequencyDayOfWeek;
import org.homeunix.thecave.buddi.model.ScheduledTransaction;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.view.swing.TranslatorListCellRenderer;
import org.homeunix.thecave.moss.swing.components.JScrollingComboBox;
import org.homeunix.thecave.moss.swing.window.MossPanel;

public class BiWeeklyCard extends MossPanel implements ScheduleCard {
	public static final long serialVersionUID = 0;

	private final JScrollingComboBox biWeeklyDayChooser;
	
	public BiWeeklyCard() {
		super(true);
		biWeeklyDayChooser = new JScrollingComboBox(ScheduleFrequencyDayOfWeek.values());
		open();
	}
	
	@Override
	public void init() {
		super.init();

		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(new JLabel(TextFormatter.getTranslation(BuddiKeys.AND_REPEATING_EVERY)));
		this.add(biWeeklyDayChooser);
		biWeeklyDayChooser.setRenderer(new TranslatorListCellRenderer());
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		
		biWeeklyDayChooser.setEnabled(enabled);
	}
	
	public int getScheduleDay() {
		return biWeeklyDayChooser.getSelectedIndex();
	}
	
	public int getScheduleWeek() {
		return 0;
	}
	
	public int getScheduleMonth() {
		return 0; //TODO This used to be -1.  Check if this change is correct or not.
	}
	
	public void loadSchedule(ScheduledTransaction s) {
		biWeeklyDayChooser.setSelectedIndex(s.getScheduleDay());
	}
}
