/*
 * Created on Oct 3, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.prefs.PluginEntry;
import org.homeunix.drummer.prefs.PrefsInstance;

public class PluginEntryArrayEditor extends JPanel {
	public static final long serialVersionUID = 0;
	
	private final DefaultListModel model;
	private final JList list;
	private final JButton newButton;
	private final JButton deleteButton;
		
	public PluginEntryArrayEditor() {
		list = new JList();
		
		model = new DefaultListModel();
		
		list.setModel(model);
		
		JScrollPane scroller = new JScrollPane(list);
		
		newButton = new JButton(Translate.getInstance().get(TranslateKeys.NEW));
		deleteButton = new JButton(Translate.getInstance().get(TranslateKeys.DELETE));
		
		deleteButton.setEnabled(false);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(newButton);
		buttonPanel.add(deleteButton);		
		
		this.setLayout(new BorderLayout());
		this.add(scroller, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		//Add the actions
		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				deleteButton.setEnabled(list.getSelectedIndices().length > 0);
			}
		});
		
		newButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				model.addElement(JOptionPane.showInputDialog(
						null, 
						"Enter Plugin Class Name:",
						"Plugin Class",
						JOptionPane.PLAIN_MESSAGE
				));
			}
		});
		
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				model.remove(list.getSelectedIndex());
			}
		});
		
	}
	
	public PluginEntryArrayEditor(Collection<PluginEntry> entries){
		this();
		
		setStrings(entries);
	}
	
	public void setStrings(Collection<PluginEntry> entries){
		for (PluginEntry entry : entries) {
			model.addElement(entry.getClassName());	
		}		
	}

	public Collection<PluginEntry> getPluginEntries(){
		Collection<PluginEntry> entries = new Vector<PluginEntry>();
		
		for (int i = 0; i < model.size(); i++){
			PluginEntry entry = PrefsInstance.getInstance().getPrefsFactory().createPluginEntry();
			entry.setClassName(model.get(i).toString());
			entries.add(entry);
		}
		
		return entries;
	}
}
