/* 
 * Created on Oct 11, 2006 by wyatt
 * Meant to aid in the creation and upkeep of Buddi language files.
 */

package org.homeunix.drummer.util;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;


public class LanguageEditor2 extends JDialog {
	public static final long serialVersionUID = 0;

	private static final String VERSION = "0.6.0";
	private final JList list = new JList();
	private final JTextArea value = new JTextArea();
	private final SortedPropertyListModel model = new SortedPropertyListModel();

	private final Properties englishProps = new Properties();
	private final Properties baseProps = new Properties();
	private final Properties localizedProps = new Properties();

	private final Vector<TranslationKeyValuePair> translationKeyValuePairs = new Vector<TranslationKeyValuePair>();
	
	final String localeName;
	final String baseLanguage;
	
	private TranslationKeyValuePair selectedTKVP = null;

	public LanguageEditor2(String language) {
		
		if (language.endsWith(")")){
			localeName = language.replaceAll("^.*_\\(", "").replaceAll("\\)$", "");
		}
		else {
			String tempLocaleName = JOptionPane.showInputDialog(null, 
					"Please enter the locale name.  This\n"
					+ "is the two letter extension\n"
					+ "to the language which indicates\n"
					+ "minor changes, e.g. US for English (US).\n"
					+ "To edit the base language itself, hit Cancel.");
			if (tempLocaleName == null)
				localeName = "";
			else
				localeName = tempLocaleName;
		}

		baseLanguage = language.replaceAll("_\\(.*\\)", "");
	}
	
	public String getNewLanguageName(){
		try{
			loadData(baseLanguage, localeName);
			showWindow(baseLanguage, localeName);
			return baseLanguage + (localeName.length() > 0 ? "_(" + localeName + ")" : ""); 
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
		
		return null;
	}

	private void loadData(String baseLanguage, String localeName) throws Exception{
		//Clear out old data
		englishProps.clear();
		baseProps.clear();
		localizedProps.clear();

		//English
		String englishFileName = Const.LANGUAGE_FOLDER + File.separator + "English" + Const.LANGUAGE_EXTENSION;
		String englishResource = "/" + "English" + Const.LANGUAGE_EXTENSION;

		//Base Language (e.g., Espanol)
		String baseFileName = Const.LANGUAGE_FOLDER + File.separator + baseLanguage + Const.LANGUAGE_EXTENSION;
		String baseResource = "/" + baseLanguage + Const.LANGUAGE_EXTENSION;

		String localizedFileName, localizedResource;
		if (localeName.length() > 0){
			//Localized Language (e.g., Espanol_(MX))
			localizedFileName = Const.LANGUAGE_FOLDER + File.separator + baseLanguage + "_(" + localeName + ")" + Const.LANGUAGE_EXTENSION;
			localizedResource = "/" + baseLanguage + "_(" + localeName + ")" + Const.LANGUAGE_EXTENSION;
		}
		else {
			localizedFileName = baseFileName;
			localizedResource = baseResource;
		}

		try{
			//Set up the files
			File englishFile, baseFile, localizedFile;
			englishFile = new File(englishFileName);
			baseFile = new File(baseFileName);
			localizedFile = new File(localizedFileName);

			//Load English
			if (englishFile.exists()){
				if (new BufferedInputStream(new FileInputStream(englishFile)) != null)
					englishProps.load(new BufferedInputStream(new FileInputStream(englishFile)));
			}
			else {
				if (this.getClass().getResourceAsStream(englishResource) != null)
					englishProps.load(this.getClass().getResourceAsStream(englishResource));
			}

			//Load Base Language
			if (baseFile.exists()){
				if (new BufferedInputStream(new FileInputStream(baseFile)) != null)
					baseProps.load(new BufferedInputStream(new FileInputStream(baseFile)));
			}
			else {
				if (this.getClass().getResourceAsStream(baseResource) != null)
					baseProps.load(this.getClass().getResourceAsStream(baseResource));
			}

			//Load Localized Language
			if (localizedFile.exists()){
				if (new BufferedInputStream(new FileInputStream(localizedFile)) != null)
					localizedProps.load(new BufferedInputStream(new FileInputStream(localizedFile)));
			}
			else {
				if (this.getClass().getResourceAsStream(localizedResource) != null)
					localizedProps.load(this.getClass().getResourceAsStream(localizedResource));
			}

		}
		catch(IOException ioe){}		


		Set<Object> languageUnion = new HashSet<Object>();
		languageUnion.addAll(englishProps.keySet());
		languageUnion.addAll(baseProps.keySet());
		languageUnion.addAll(localizedProps.keySet());

		for (Object o : languageUnion) {
			String s = (String) o;

			String englishValue = (englishProps.getProperty(s) == null ? "" : englishProps.getProperty(s));
			String baseValue = (localeName.length() == 0 || baseProps.getProperty(s) == null ? "" : baseProps.getProperty(s));
			String localizedValue = (localizedProps.getProperty(s) == null ? "" : localizedProps.getProperty(s));

			TranslationKeyValuePair tkvp = new TranslationKeyValuePair(s, englishValue, baseValue, localizedValue);
			translationKeyValuePairs.add(tkvp);
		}		
	}

	private void showWindow(final String baseLanguage, final String localeName) throws Exception {

		String title = "Editing " 
			+ (localeName.length() > 0 ? "Locale " + localeName + " for " : "") 
			+ baseLanguage;
		this.setTitle(title);

		list.setModel(model);
		list.setCellRenderer(new TranslationKeyValuePairCellRenderer());

		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){
					if (selectedTKVP != null){
						selectedTKVP.save(value.getText());
					}

					selectedTKVP = (TranslationKeyValuePair) list.getSelectedValue();

					value.setText(selectedTKVP.getLocalizedValue());
				}
			}
		});

		model.addAll(translationKeyValuePairs);		
		JScrollPane listScroller = new JScrollPane(list);
		JScrollPane valueScroller = new JScrollPane(value);

		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		final JButton ok = new JButton(Translate.getInstance().get(TranslateKeys.OK));
		final JButton cancel = new JButton(Translate.getInstance().get(TranslateKeys.CANCEL));
		final JButton help = new JButton(Translate.getInstance().get(TranslateKeys.HELP));
		
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				selectedTKVP.save(value.getText());
				
				File languagesFolder = new File(Buddi.getWorkingDir() + File.separator + Const.LANGUAGE_FOLDER);
				if (!languagesFolder.exists()){
					languagesFolder.mkdirs();
				}

				File saveFile = new File(
						languagesFolder.getAbsolutePath() 
						+ File.separator 
						+ baseLanguage
						+ (localeName.length() > 0 ? "_(" + localeName + ")" : "") 
						+ Const.LANGUAGE_EXTENSION
				);

				Properties newProps = new Properties();

				for (TranslationKeyValuePair tkvp : translationKeyValuePairs) {
					if (tkvp.getLocalizedValue().length() > 0){
						newProps.put(tkvp.getKey(), tkvp.getLocalizedValue());
						tkvp.resetOriginalLocalizedValue();
					}
				}

				try{
					newProps.store(new FileOutputStream(saveFile), null);
				}
				catch (Exception e){
					JOptionPane.showMessageDialog(LanguageEditor2.this, e);
				}
				
				//Close the window.
				LanguageEditor2.this.dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				LanguageEditor2.this.dispose();
			}
		});

		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(LanguageEditor2.this,
						"<html><h2>Buddi Language Editor, version " + VERSION + "</h2>"
						+ "<p><font color=#00D000>Green = Localized Value - All Good</font></p>"
						+ "<p><font color=#000090>Dark Blue = Localized == Base</font></p>"
						+ "<p><font color=#0000FF>Blue = Localized == English</font></p>"
						+ "<p><font color=#A000A0>Purple = Localized missing, value in Base</font></p>"
//						+ "<p><font color=#FFCC00>Yellow = Not in Localized, but in English</font></p>"
						+ "<p><font color=#FF0000>Red = Localized missing, value in English</font></p>"
						+ "<p><font color=#FF9090>Pink = Not in English (Probably a spurious key)</font></p>"
						+ "<p>* = Edited since Last Save</font></p></html>"
				);
			}
		});

		Dimension d = new Dimension(Math.max(100, cancel.getPreferredSize().width), cancel.getPreferredSize().height);
		ok.setPreferredSize(d);
		cancel.setPreferredSize(d);
		help.setPreferredSize(d);
		
		buttons.setBorder(BorderFactory.createEmptyBorder(1, 12, 12, 12));
		buttons.add(help);
		buttons.add(cancel);
		buttons.add(ok);

		JPanel editorPanel = new JPanel(new BorderLayout());

		JPanel listScrollerBorder = new JPanel(new BorderLayout());
		listScrollerBorder.setBorder(BorderFactory.createEmptyBorder(7, 12, 6, 12));
		listScrollerBorder.add(listScroller, BorderLayout.CENTER);

		JPanel valueScrollerBorder = new JPanel(new BorderLayout());
		valueScrollerBorder.setBorder(BorderFactory.createEmptyBorder(1, 12, 5, 12));
		valueScrollerBorder.add(valueScroller, BorderLayout.CENTER);

		editorPanel.add(listScrollerBorder, BorderLayout.CENTER);
		editorPanel.add(valueScrollerBorder, BorderLayout.SOUTH);

		list.setPreferredSize(new Dimension(
				Math.max(list.getPreferredSize().width, 400),
				Math.max(list.getPreferredSize().height, 300)
		));
		value.setPreferredSize(new Dimension(
				Math.max(value.getPreferredSize().width, 200),
				Math.max(value.getPreferredSize().height, 100)
		));

		this.setModal(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(editorPanel, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.SOUTH);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	class TranslationKeyValuePairCellRenderer extends DefaultListCellRenderer {
		public final static long serialVersionUID = 0;
		private Color color; 
		private Color BLUE = new Color(0, 0, 255);
		private Color DARK_BLUE = new Color(0, 0, 170);
		private Color GREEN = new Color(0, 196, 0);
		private Color PINK = new Color(255, 170, 170);
//		private Color YELLOW = new Color(230, 230, 0);
		private Color PURPLE = new Color(196, 0, 196);
		private Color RED = new Color(255, 0, 0);
		private Color OTHER = Color.BLACK;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			TranslationKeyValuePair tkvp = (TranslationKeyValuePair) value;
			//Not in localization or base, but in English - Mark RED
			if (tkvp.getLocalizedValue().length() == 0 
					&& tkvp.getBaseValue().length() == 0
					&& tkvp.getEnglishValue().length() > 0){
				color = RED;
			}
			//Not in localization, but in base && english - Mark PURPLE
			else if (tkvp.getLocalizedValue().length() == 0
					&& tkvp.getBaseValue().length() > 0
					&& tkvp.getEnglishValue().length() > 0){
				color = PURPLE;
			}
//			//Not in localization, but in English - Mark YELLOW
//			else if (tkvp.getLocalizedValue().length() == 0
//					&& tkvp.getEnglishValue().length() > 0){
//				color = YELLOW;
//			}
			//Not in English, but in Base or Localized - Mark PINK
			else if (tkvp.getEnglishValue().length() == 0){
				color = PINK;
			}
			//In Localized == English - Mark BLUE
			else if (tkvp.getLocalizedValue().equals(tkvp.getEnglishValue())){
				color = BLUE;
			}
			//In Localized == Base - Mark Dark Blue
			else if (tkvp.getLocalizedValue().equals(tkvp.getBaseValue())){
				color = DARK_BLUE;
			}
			//In both (Localized and Base) and (Localized and English), and 
			// none are the same - Mark GREEN
			else if (!tkvp.getLocalizedValue().equals(tkvp.getBaseValue())
					&& !tkvp.getLocalizedValue().equals(tkvp.getEnglishValue())){
				color = GREEN;
			}
			//Fallback - we should never get here.
			else {
				color = OTHER;
			}

			this.setForeground(color);

			this.setText(tkvp.getKey() + (tkvp.isChanged() ? " *" : ""));

			return this;
		}


	}

	class TranslationKeyValuePair implements Comparable<TranslationKeyValuePair>{
		public static final long serialVersionUID = 0;

		private String key;
		private String englishValue;
		private String baseValue;
		private String localizedValue;
		private String originalLocalizedValue;

		public TranslationKeyValuePair(String key, String englishValue, String baseValue, String translatedValue) {
			this.key = key;
			this.englishValue = englishValue;
			this.baseValue = baseValue;
			this.localizedValue = translatedValue;
			this.originalLocalizedValue = translatedValue;
		}

		public String getKey() {
			return key;
		}

		public String getLocalizedValue() {
			return localizedValue;
		}

		public String getEnglishValue(){
			return englishValue;
		}

		public String getBaseValue(){
			return baseValue;
		}

		public void setLocalizedValue(String value) {
			this.localizedValue = value;
		}

		public void resetOriginalLocalizedValue(){
			this.originalLocalizedValue = this.localizedValue;
		}

		public int compareTo(TranslationKeyValuePair arg0) {
			return this.getKey().compareTo(arg0.getKey());
		}

		public void save(String newValue){
			this.localizedValue = newValue;
		}

		public boolean isChanged(){
			return !localizedValue.equals(originalLocalizedValue);
		}
	}

	class SortedPropertyListModel extends AbstractListModel {
		public static final long serialVersionUID = 0; 

		private Vector<TranslationKeyValuePair> model = new Vector<TranslationKeyValuePair>();

		public SortedPropertyListModel() {}

		public SortedPropertyListModel(Collection<TranslationKeyValuePair> c){
			model.addAll(c);
			sort();
		}

		public void add(TranslationKeyValuePair cell){
			model.add(cell);
			sort();
		}

		public void addAll(Collection<TranslationKeyValuePair> c){
			model.addAll(c);
			sort();
		}

		public void remove(TranslationKeyValuePair cell){
			model.remove(cell);
			sort();
		}

		public Object getElementAt(int index) {
			return model.get(index);
		}

		public int getSize() {
			return model.size();
		}

		public void update(){
			this.fireContentsChanged(this, 0, getSize() - 1);
		}

		public void sort(){
			Collections.sort(model);
		}
	}
}
