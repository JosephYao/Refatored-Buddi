/*
 * Created on Oct 11, 2006 by wyatt
 * 
 * Meant to aid in the creation and upkeep of Buddi language files.
 */

package org.homeunix.drummer.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.homeunix.thecave.moss.util.SwingWorker;

public class LanguageEditor extends JFrame {
	public static final long serialVersionUID = 0;
	
	private static final String VERSION = "0.5.0";

	public LanguageEditor() throws Exception {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		final Properties englishProps = new Properties();
		final Properties translatedProps = new Properties();

		SwingWorker worker = new SwingWorker(){
			@Override
			public Object construct() {
				try {
					URL english = new URL("https://svn.sourceforge.net/svnroot/buddi/trunk/Languages/English.lang"); 
					englishProps.load(english.openStream());
				}
				catch (Exception e){
					return e;
				}
				return null;
			}
		};
		
		//Start the worker going early, to make it look like it's faster...
		worker.start();

		JFileChooser jfc = new JFileChooser();
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			File loadFile = jfc.getSelectedFile();
			translatedProps.load(new FileInputStream(loadFile));
		}

		//Wait until worker returns.
		if (worker.get() != null){
			throw (Exception) worker.get();
		}
			

		final Map<String, EditorCell> englishCells = new HashMap<String, EditorCell>();
		final Map<String, EditorCell> translatedCells = new HashMap<String, EditorCell>();
		final Map<String, EditorCell> allCells = new HashMap<String, EditorCell>();

		//First, load all English terms.
		for (Object o : englishProps.keySet()) {
			if (o instanceof String){
				String s = (String) o;
				EditorCell ec = new EditorCell(s, englishProps.getProperty(o.toString()), translatedCells);
				ec.setFlagColor(Color.RED);
				englishCells.put(s, ec);
				allCells.put(s, ec);
			}
		}


		for (Object o : translatedProps.keySet()) {
			if (o instanceof String){
				String s = (String) o;
				EditorCell ec = englishCells.get(s);
				//There is not an English version.  Mark it Green. (Why green?  I don't know...)
				if (ec == null){
					ec = new EditorCell(s, translatedProps.getProperty(s), translatedCells);
					ec.setFlagColor(Color.GREEN);
					translatedCells.put(s, ec);
					allCells.put(s, ec);
					ec.setRemoveButtonEnabled(true);
				}
				//There is a translated version, but it is the same as English.  
				// This may be a mistake in the translation - 
				// mark it purple, but still put it in Translated Cells map.
				else if (ec.getValue().equals(translatedProps.getProperty(s))){
					ec.setValue((String) translatedProps.get(s));
					ec.setFlagColor(new Color(200, 0, 200));
					translatedCells.put(s, ec);
					ec.setRemoveButtonEnabled(true);
				}
				//There is a translated version, different from 
				// the English version.  Mark it black.  
				else if (translatedProps.getProperty(s) != null){
					ec.setValue((String) translatedProps.get(s));
					ec.setFlagColor(Color.BLACK);
					translatedCells.put(s, ec);
					ec.setRemoveButtonEnabled(true);
				}
				//There is an English version, but no translated version.
				// Mark it red.
				else {
					ec.setFlagColor(Color.RED);
				}
			}
		}

		//Get ready to display the cells
		Vector<EditorCell> cellsToDisplay = new Vector<EditorCell>(allCells.values());		
		Collections.sort(cellsToDisplay);

		for (EditorCell ec : cellsToDisplay) {
			panel.add(ec);
		}
		JScrollPane scroller = new JScrollPane(panel);

		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		final JButton save = new JButton("Save");
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
					File saveFile = jfc.getSelectedFile();

					Properties newProps = new Properties();

					for (String s : translatedCells.keySet()) {
						if (translatedCells.get(s) != null){
							newProps.put(s, translatedCells.get(s).getValue());
						}
					}

					try{
						newProps.store(new FileOutputStream(saveFile), null);
					}
					catch (Exception e){
						JOptionPane.showMessageDialog(LanguageEditor.this, e);
					}
				}
			}
		});
		
		final JButton help = new JButton("Help");
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(LanguageEditor.this,
						"Buddi Language Editor, version " + VERSION + "\n\n"
						+ "Black = in both English and Translated (and Translated is different than English)\n"
						+ "Red = not in Translated file (but in English)\n"
						+ "Green = not in English file (but in Translated)\n"
						+ "Purple = in both English and Translated (but Translated is same as English)\n"
						+ "Blue = value changed this session\n"
						+ "Crossed out = removed from translation (re-edit to put back in)"
				);
			}
		});
		
		final JButton quit = new JButton("Save and Quit");
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				save.doClick();
				System.exit(0);
			}
		});
		
		
		buttons.add(help);
		buttons.add(save);
		buttons.add(quit);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(scroller, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);
	}

//	/**
//	 * @param args
//	 */
//	public static void main(final String[] args) throws Exception {
//		new LanguageEditor();
//
//	}

	public static class EditorCell extends JPanel implements Comparable<EditorCell>{
		public static final long serialVersionUID = 0;

		private String key;
		private String value;

		private final JLabel keyLabel;
		private final JTextArea valueField;
		private final JButton removeKey;

		public EditorCell(String key, String value, final Map<String, EditorCell> translatedCells) {
			super(new BorderLayout());
			this.key = key;
			this.value = value;

			keyLabel = new JLabel(key);
			valueField = new JTextArea(value);
			removeKey = new JButton("Remove");

			JScrollPane valueScroller = new JScrollPane(valueField);
			
			keyLabel.setPreferredSize(new Dimension(300, keyLabel.getPreferredSize().height));
			valueScroller.setPreferredSize(new Dimension(400, 50));

			valueField.getDocument().addDocumentListener(new DocumentListener(){
				public void changedUpdate(DocumentEvent arg0) {
					changeCell();
				}
				public void insertUpdate(DocumentEvent arg0) {
					changeCell();
				}
				public void removeUpdate(DocumentEvent arg0) {
					changeCell();
				}

				private void changeCell(){
					keyLabel.setForeground(Color.BLUE);
					keyLabel.setText(EditorCell.this.key);
					removeKey.setEnabled(true);
					EditorCell.this.value = valueField.getText();
					translatedCells.put(EditorCell.this.key, EditorCell.this);
				}
			});
			
			removeKey.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					keyLabel.setText("<html><s>" + keyLabel.getText() + "</s></html>");
					translatedCells.remove(EditorCell.this.key);
					removeKey.setEnabled(false);
				}
			});
			
			removeKey.setEnabled(false);
			
			JPanel keyPanel = new JPanel();
			keyPanel.setLayout(new BoxLayout(keyPanel, BoxLayout.Y_AXIS));
			keyPanel.add(keyLabel);
			keyPanel.add(removeKey);
			this.add(keyPanel, BorderLayout.WEST);
			this.add(valueScroller, BorderLayout.EAST);
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
			valueField.setText(value);
		}

		public void setFlagColor(Color c){
			keyLabel.setForeground(c);
		}

		public int compareTo(EditorCell arg0) {
			return this.getKey().compareTo(arg0.getKey());
		}
		
		public void setRemoveButtonEnabled(boolean enabled){
			removeKey.setEnabled(enabled);
		}
	}
}
