/*
 * Created on Aug 19, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.i18n;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.KeyStroke;

import org.homeunix.thecave.buddi.Buddi;
import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.keys.AboutFrameKeys;
import org.homeunix.thecave.buddi.i18n.keys.AccountFrameKeys;
import org.homeunix.thecave.buddi.i18n.keys.BudgetExpenseDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.BudgetFrameKeys;
import org.homeunix.thecave.buddi.i18n.keys.BudgetIncomeDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.i18n.keys.MessageKeys;
import org.homeunix.thecave.buddi.i18n.keys.MonthKeys;
import org.homeunix.thecave.buddi.i18n.keys.PluginRangeFilters;
import org.homeunix.thecave.buddi.i18n.keys.PreferencesKeys;
import org.homeunix.thecave.buddi.i18n.keys.ScheduleFrequency;
import org.homeunix.thecave.buddi.i18n.keys.ScheduleFrequencyDayOfMonth;
import org.homeunix.thecave.buddi.i18n.keys.ScheduleFrequencyDayOfWeek;
import org.homeunix.thecave.buddi.i18n.keys.ScheduleFrequencyFirstWeekOfMonth;
import org.homeunix.thecave.buddi.i18n.keys.ScheduleFrequencyMonth;
import org.homeunix.thecave.buddi.i18n.keys.ScheduleFrequencyWeek;
import org.homeunix.thecave.buddi.i18n.keys.TransactionDateFilterKeys;
import org.homeunix.thecave.buddi.i18n.keys.TypeCreditDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.TypeDebitDefaultKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.moss.i18n.LanguageEditor;
import org.homeunix.thecave.moss.swing.menu.MossMenu;
import org.homeunix.thecave.moss.swing.menu.MossMenuBar;
import org.homeunix.thecave.moss.swing.menu.MossMenuItem;

public class BuddiLanguageEditor extends LanguageEditor {
	public static final long serialVersionUID = 0;
	
	private final File languageLocation = new File(Buddi.getWorkingDir() + File.separator + Const.LANGUAGE_FOLDER);
	
	public BuddiLanguageEditor(String selectedLanguage) {
		super(Const.LANGUAGE_EXTENSION, selectedLanguage, true);

		//TODO Make sure all translation keys are loaded here.  It's annoying that this is
		// not automated, but the convenience of having multiple smaler key files
		// makes up for it.
		this.loadKeys((Enum[]) BuddiKeys.values());
		this.loadKeys((Enum[]) AboutFrameKeys.values());
		this.loadKeys((Enum[]) AccountFrameKeys.values());
		this.loadKeys((Enum[]) BudgetExpenseDefaultKeys.values());
		this.loadKeys((Enum[]) BudgetFrameKeys.values());
		this.loadKeys((Enum[]) BudgetIncomeDefaultKeys.values());
		this.loadKeys((Enum[]) BudgetPeriodKeys.values());
		this.loadKeys((Enum[]) ButtonKeys.values());
		this.loadKeys((Enum[]) MenuKeys.values());
		this.loadKeys((Enum[]) MessageKeys.values());
		this.loadKeys((Enum[]) MonthKeys.values());		
		this.loadKeys((Enum[]) PluginRangeFilters.values());
		this.loadKeys((Enum[]) PreferencesKeys.values());
		this.loadKeys((Enum[]) ScheduleFrequency.values());
		this.loadKeys((Enum[]) ScheduleFrequencyDayOfMonth.values());
		this.loadKeys((Enum[]) ScheduleFrequencyDayOfWeek.values());
		this.loadKeys((Enum[]) ScheduleFrequencyFirstWeekOfMonth.values());
		this.loadKeys((Enum[]) ScheduleFrequencyMonth.values());
		this.loadKeys((Enum[]) ScheduleFrequencyWeek.values());
		this.loadKeys((Enum[]) TransactionDateFilterKeys.values());
		this.loadKeys((Enum[]) TypeCreditDefaultKeys.values());
		this.loadKeys((Enum[]) TypeDebitDefaultKeys.values());

		//TODO Before we load the language, we should prompt (so that the user
		// can enter a new one if desired).
		this.loadLanguages(Const.LANGUAGE_RESOURCE_PATH, PrefsModel.getInstance().getTranslator().getLanguageList(selectedLanguage).toArray(new String[1]));
		this.loadLanguages(new File(Const.LANGUAGE_FOLDER), PrefsModel.getInstance().getTranslator().getLanguageList(selectedLanguage).toArray(new String[1]));
	}
	
	@Override
	public void init() {
		super.init();

		MossMenuBar menuBar = new MossMenuBar(this);
		MossMenu fileMenu = new MossMenu(this, "File");
		MossMenuItem save = new MossMenuItem(this, "Save Translation", KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				BuddiLanguageEditor.this.saveLanguages(languageLocation);
			}
		});
		
		fileMenu.add(save);
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);		
	}
	
	
}
