/*
 * Created on Oct 11, 2006 by wyatt
 * 
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LanguageEditor2 extends JFrame {
	public static final long serialVersionUID = 0;

	private static final String VERSION = "0.6.0";
	private final JList list = new JList();
	private final JTextArea value = new JTextArea();
	private final SortedPropertyListModel model = new SortedPropertyListModel();

	private final Properties englishProps = new Properties();
	private final Properties translatedProps = new Properties();

	private final Vector<TranslationKeyValuePair> translationKeyValuePairs = new Vector<TranslationKeyValuePair>();

	private TranslationKeyValuePair selectedTKVP = null;

	public LanguageEditor2() throws Exception {
		loadData();
		showWindow();
	}

	private void loadData() throws Exception{
		englishProps.clear();
		
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Open Base File, or press Cancel to use English.lang");
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			File loadFile = jfc.getSelectedFile();
			englishProps.load(new FileInputStream(loadFile));
			
		}
		else {
			URL english = new URL("https://svn.sourceforge.net/svnroot/buddi/trunk/Languages/English.lang");
			englishProps.load(english.openStream());			
		}


		translatedProps.clear();
		jfc = new JFileChooser();
		jfc.setDialogTitle("Open Translation File, or press Cancel for a new file");
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			File loadFile = jfc.getSelectedFile();
			translatedProps.load(new FileInputStream(loadFile));
		}

		Set<Object> languageUnion = new HashSet<Object>();
		languageUnion.addAll(englishProps.keySet());
		languageUnion.addAll(translatedProps.keySet());
		
		for (Object o : languageUnion) {
			String s = (String) o;

			String englishValue = (englishProps.getProperty(s) == null ? "" : englishProps.getProperty(s));
			String translationValue = (translatedProps.getProperty(s) == null ? "" : translatedProps.getProperty(s));

			TranslationKeyValuePair tkvp = new TranslationKeyValuePair(s, englishValue, translationValue);
			translationKeyValuePairs.add(tkvp);
		}		
	}

	private void showWindow() throws Exception {
		list.setModel(model);
		list.setCellRenderer(new TranslationKeyValuePairCellRenderer());

		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){
					if (selectedTKVP != null){
						selectedTKVP.save(value.getText());
					}

					selectedTKVP = (TranslationKeyValuePair) list.getSelectedValue();

					value.setText(selectedTKVP.getTranslatedValue());
				}
			}
		});

		model.addAll(translationKeyValuePairs);		
		JScrollPane listScroller = new JScrollPane(list);
		JScrollPane valueScroller = new JScrollPane(value);

		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		final JButton save = new JButton("Save");
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
					File saveFile = jfc.getSelectedFile();

					Properties newProps = new Properties();

					for (TranslationKeyValuePair tkvp : translationKeyValuePairs) {
						if (tkvp.getTranslatedValue().length() > 0){
							newProps.put(tkvp.getKey(), tkvp.getTranslatedValue());
							tkvp.resetOriginalTranslatedValue();
						}
					}

					try{
						newProps.store(new FileOutputStream(saveFile), null);
					}
					catch (Exception e){
						JOptionPane.showMessageDialog(LanguageEditor2.this, e);
					}
				}
			}
		});

		final JButton help = new JButton("Help");
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(LanguageEditor2.this,
						"<html><h2>Buddi Language Editor, version " + VERSION + "</h2>"
						+ "<p><font color='green'>Green = In both, and Values are Different</font></p>"
						+ "<p><font color='blue'>Blue = In both, but Values are the Same</font></p>"
						+ "<p><font color=#F0C000>Orange = Not in Translation, but in Base</font></p>"
						+ "<p><font color='red'>Red = Not in Base, but in Translation</font></p>"
						+ "<p>* = Edited since Last Save</font></p></html>"
				);
			}
		});

		buttons.setBorder(BorderFactory.createEmptyBorder(1, 12, 12, 12));
		buttons.add(help);
		buttons.add(save);

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

		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				int r = JOptionPane.showConfirmDialog(
						null, 
						"Do you want to save before exiting?"
				);
				
				if (r == JOptionPane.YES_OPTION){
					save.doClick();
					LanguageEditor2.this.dispose();
					System.exit(0);
				}
				else if (r == JOptionPane.NO_OPTION){
					LanguageEditor2.this.dispose();
					System.exit(0);
				}
				
				super.windowClosing(e);
			}
		});
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(editorPanel, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.SOUTH);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) throws Exception {
		new LanguageEditor2();

	}

	class TranslationKeyValuePairCellRenderer extends DefaultListCellRenderer {
		public final static long serialVersionUID = 0;
		private Color color; 
		private Color BLUE = new Color(0, 0, 196);
		private Color GREEN = new Color(0, 128, 0);
		private Color ORANGE = new Color(230, 180, 0);
		private Color RED = new Color(196, 0, 0);

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			TranslationKeyValuePair tkvp = (TranslationKeyValuePair) value;
			//Not in translation - Mark ORANGE
			if (tkvp.getTranslatedValue().length() == 0){
				color = ORANGE;
			}
			//Not in English - Mark RED
			else if (tkvp.getEnglishValue().length() == 0){
				color = RED;
			}
			//In both, but they are the same - Mark BLUE
			else if (tkvp.getTranslatedValue().equals(tkvp.getEnglishValue())){
				color = BLUE;
			}
			//In both, and not the same - Mark GREEN
			else {
				color = GREEN;
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
		private String translatedValue;
		private String originalTranslatedValue;

		public TranslationKeyValuePair(String key, String englishValue, String translatedValue) {
			this.key = key;
			this.englishValue = englishValue;
			this.translatedValue = translatedValue;
			this.originalTranslatedValue = translatedValue;
		}

		public String getKey() {
			return key;
		}

		public String getTranslatedValue() {
			return translatedValue;
		}

		public String getEnglishValue(){
			return englishValue;
		}

		public void setTranslatedValue(String value) {
			this.translatedValue = value;
		}

		public void resetOriginalTranslatedValue(){
			this.originalTranslatedValue = this.translatedValue;
		}

		public int compareTo(TranslationKeyValuePair arg0) {
			return this.getKey().compareTo(arg0.getKey());
		}

		public void save(String newValue){
			this.translatedValue = newValue;
		}

		public boolean isChanged(){
			return !translatedValue.equals(originalTranslatedValue);
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
