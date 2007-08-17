/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.dialogs.prefs;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.homeunix.thecave.buddi.Buddi;
import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
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
import org.homeunix.thecave.buddi.i18n.keys.TransactionDateFilterKeys;
import org.homeunix.thecave.buddi.i18n.keys.TypeCreditDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.TypeDebitDefaultKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.moss.i18n.LanguageEditor;
import org.homeunix.thecave.moss.swing.components.JScrollingComboBox;
import org.homeunix.thecave.moss.swing.window.MossPanel;
import org.homeunix.thecave.moss.util.Log;

public class LocalePreferences extends MossPanel implements PrefsPanel {
	public static final long serialVersionUID = 0; 

	private final JScrollingComboBox language;
	private final JScrollingComboBox dateFormat;
	private final JScrollingComboBox currencyFormat;
	private final JCheckBox currencySymbolAfterAmount;
	private final JButton otherCurrencyButton;
	private final JButton otherDateFormatButton;

	private final DefaultComboBoxModel languageModel;
	private final DefaultComboBoxModel currencyModel;
	private final DefaultComboBoxModel dateFormatModel;


	public LocalePreferences() {
		languageModel = new DefaultComboBoxModel();
		language = new JScrollingComboBox(languageModel);
		currencyModel = new DefaultComboBoxModel();
		currencyFormat = new JScrollingComboBox(currencyModel);
		currencySymbolAfterAmount = new JCheckBox(PrefsModel.getInstance().getTranslator().get(BuddiKeys.SHOW_CURRENCY_SYMBOL_AFTER_AMOUNT));
		dateFormatModel = new DefaultComboBoxModel();
		dateFormat = new JScrollingComboBox(dateFormatModel);
		otherCurrencyButton = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OTHER));
		otherDateFormatButton = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OTHER));

	}

	public void init() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel dateFormatPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel currencyFormatPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JLabel dateFormatLabel = new JLabel(PrefsModel.getInstance().getTranslator().get(BuddiKeys.DATE_FORMAT));
		JLabel currencyFormatLabel = new JLabel(PrefsModel.getInstance().getTranslator().get(BuddiKeys.CURRENCY));
		JLabel languageLabel = new JLabel(PrefsModel.getInstance().getTranslator().get(BuddiKeys.LANGUAGE));

		//Set up currency lists
		boolean customCurrency = true; //Assume custom until proved otherwise, below
		String currency = PrefsModel.getInstance().getCurrencySign();
		for (String s : Const.CURRENCY_FORMATS) {
			currencyModel.addElement(s);
			if (s.equals(currency))
				customCurrency = false;
		}
		if (customCurrency){
			currencyModel.addElement(currency);
		}

		//Set up Date Format list
		boolean customDateFormat = true; //Assume custom until proved otherwise, below
		for (String s : Const.DATE_FORMATS) {
			dateFormatModel.addElement(s);
			if (s.equals(PrefsModel.getInstance().getDateFormat()))
				customDateFormat = false;
		}
		if (customDateFormat){
			dateFormatModel.addElement(PrefsModel.getInstance().getDateFormat());
		}

		//Set up the language pulldown
		language.setRenderer(new DefaultListCellRenderer(){
			public static final long serialVersionUID = 0;
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof String){
					String str = (String) value;
					this.setText(str.replaceAll("_", " "));
				}
				return this;
			}
		});

		//Set up the date format pulldown
		dateFormat.setRenderer(new DefaultListCellRenderer(){
			public static final long serialVersionUID = 0;
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

				if (value != null){
					this.setText(new SimpleDateFormat(value.toString()).format(new Date()));
				}

				return this;
			}
		});


		otherCurrencyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String newCurrency = JOptionPane.showInputDialog(
						LocalePreferences.this, 
						PrefsModel.getInstance().getTranslator().get(BuddiKeys.ENTER_CURRENCY_SYMBOL), 
						PrefsModel.getInstance().getTranslator().get(BuddiKeys.ENTER_CURRENCY_SYMBOL_TITLE), 
						JOptionPane.PLAIN_MESSAGE);

				if (newCurrency != null && newCurrency.length() > 0){
					currencyModel.removeAllElements();

					//Set up currency lists
					boolean customCurrency = true; //Assume custom until proved otherwise, below
					for (String s : Const.CURRENCY_FORMATS) {
						currencyModel.addElement(s);
						if (s.equals(newCurrency)){
							customCurrency = false;
							Log.debug("Currency " + newCurrency + " already in list...");
						}
					}
					if (customCurrency){
						currencyModel.addElement(newCurrency);
					}

					currencyFormat.setSelectedItem(newCurrency);
				}
				else {
					Log.debug("Invalid currency: '" + newCurrency + "'");
				}	
			}
		});

		otherDateFormatButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String newDateFormat = JOptionPane.showInputDialog(
						LocalePreferences.this, 
						PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_ENTER_DATE_FORMAT), 
						PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_ENTER_DATE_FORMAT_TITLE), 
						JOptionPane.PLAIN_MESSAGE);

				if (newDateFormat != null 
						&& newDateFormat.length() > 0){

					//Test out the new format, to see if it complies with
					// the format rules.
					try {
						new SimpleDateFormat(newDateFormat);
					}
					catch (IllegalArgumentException iae){
						String[] options = new String[1];
						options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK);

						JOptionPane.showOptionDialog(
								LocalePreferences.this, 
								PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_ERROR_INCORRECT_FORMAT), 
								PrefsModel.getInstance().getTranslator().get(BuddiKeys.ERROR),
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.ERROR_MESSAGE,
								null,
								options,
								options[0]);
						return;
					}


					dateFormatModel.removeAllElements();

					//Set up currency lists
					boolean customDateFormat = true; //Assume custom until proved otherwise, below
					for (String s : Const.DATE_FORMATS) {
						dateFormatModel.addElement(s);
						if (s.equals(newDateFormat)){
							customDateFormat = false;
							Log.debug("Date Format " + newDateFormat + " already in list...");
						}
					}
					if (customDateFormat){
						dateFormatModel.addElement(newDateFormat);
					}

					dateFormatModel.setSelectedItem(newDateFormat);
				}
				else {
					Log.debug("Invalid Date Format: '" + newDateFormat + "'");
				}
			}
		});

		JButton editLanguages = new JButton(PrefsModel.getInstance().getTranslator().get(BuddiKeys.EDIT_LANGUAGES));
		editLanguages.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					LanguageEditor le = new LanguageEditor(Const.LANGUAGE_EXTENSION, language.getSelectedItem().toString());

					//TODO Make sure all translation keys are loaded here.  It's annoying that this is
					// not automated, but the convenience of having multiple smaler key files
					// makes up for it.
					le.loadKeys((Enum[]) BuddiKeys.values());
					le.loadKeys((Enum[]) AboutFrameKeys.values());
					le.loadKeys((Enum[]) AccountFrameKeys.values());
					le.loadKeys((Enum[]) BudgetExpenseDefaultKeys.values());
					le.loadKeys((Enum[]) BudgetFrameKeys.values());
					le.loadKeys((Enum[]) BudgetIncomeDefaultKeys.values());
					le.loadKeys((Enum[]) BudgetPeriodKeys.values());
					le.loadKeys((Enum[]) ButtonKeys.values());
					le.loadKeys((Enum[]) MenuKeys.values());
					le.loadKeys((Enum[]) MessageKeys.values());
					le.loadKeys((Enum[]) MonthKeys.values());
					le.loadKeys((Enum[]) TransactionDateFilterKeys.values());
					le.loadKeys((Enum[]) TypeCreditDefaultKeys.values());
					le.loadKeys((Enum[]) TypeDebitDefaultKeys.values());

					le.loadLanguages(Const.LANGUAGE_RESOURCE_PATH, PrefsModel.getInstance().getTranslator().getLanguageList(language.getSelectedItem().toString()).toArray(new String[1]));
					le.loadLanguages(new File(Const.LANGUAGE_FOLDER), PrefsModel.getInstance().getTranslator().getLanguageList(language.getSelectedItem().toString()).toArray(new String[1]));

					le.openWindow();

					updateContent();
//					language.setSelectedItem(lang);
//					forceRestart = true; //We need to restart for language to take effect.
				}
				catch (Exception ex){}
			}
		});

//		dateFormat.addItemListener(new ItemListener() {
//		public void itemStateChanged(ItemEvent e) {
//		if (dateFormat.getSelectedItem() == null){
//		if (e.getItem().equals(dateFormat.getItemAt(0))){
//		dateFormat.setSelectedIndex(1);
//		}
//		Log.debug("null; e.getItem == " + e.getItem());
//		dateFormat.setSelectedIndex(0);
//		}
//		}			
//		});

		//Set up language model
		languageModel.removeAllElements();

		Set<String> languages = new HashSet<String>();

		// Load all available languages into Prefs.  Start with 
		// the bundled languages, and if there are more, load them too.
		for (String language : Const.BUNDLED_LANGUAGES) {
			languages.add(language);			
		}

		File languageLocation = new File(Buddi.getWorkingDir() + File.separator + Const.LANGUAGE_FOLDER);
		if (languageLocation.exists() && languageLocation.isDirectory()){
			for (File f: languageLocation.listFiles())
				if (f.getName().endsWith(Const.LANGUAGE_EXTENSION))
					languages.add(f.getName().replaceAll(Const.LANGUAGE_EXTENSION, ""));
		}
		else{
			Log.critical("Cannot find language directory");
		}

		Vector<String> languagesVector = new Vector<String>(languages);
		Collections.sort(languagesVector);
		for (String string : languagesVector) {
			languageModel.addElement(string);
		}

		dateFormat.setSelectedItem(PrefsModel.getInstance().getDateFormat());
		currencyFormat.setSelectedItem(PrefsModel.getInstance().getCurrencySign());
		currencySymbolAfterAmount.setSelected(PrefsModel.getInstance().isShowCurrencyAfterAmount());
		language.setSelectedItem(PrefsModel.getInstance().getLanguage());


		languagePanel.add(languageLabel);
		languagePanel.add(language);
		languagePanel.add(editLanguages);

		dateFormatPanel.add(dateFormatLabel);
		dateFormatPanel.add(dateFormat);
		dateFormatPanel.add(otherDateFormatButton);
		currencyFormatPanel.add(currencyFormatLabel);
		currencyFormatPanel.add(currencyFormat);
		currencyFormatPanel.add(otherCurrencyButton);

		this.add(languagePanel);		
		this.add(dateFormatPanel);
		this.add(currencyFormatPanel);
		this.add(currencySymbolAfterAmount);
		this.add(Box.createVerticalGlue());

//		return getPanelHolder(localePanel);
	}

	public void save() {
		PrefsModel.getInstance().setDateFormat(dateFormat.getSelectedItem().toString());
		PrefsModel.getInstance().setCurrencySign(currencyFormat.getSelectedItem().toString());
		PrefsModel.getInstance().setShowCurrencyAfterAmount(currencySymbolAfterAmount.isSelected());
		PrefsModel.getInstance().setLanguage(language.getSelectedItem().toString());
		
		PrefsModel.getInstance().getTranslator().reloadLanguages();
	}
}
