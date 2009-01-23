/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.TransactionSplit;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.exception.InvalidValueException;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.buddi.view.panels.SplitEditorPanel;
import org.homeunix.thecave.moss.swing.MossDialog;
import org.homeunix.thecave.moss.util.Log;

public class SplitTransactionDialog extends MossDialog implements ActionListener {

	public static final long serialVersionUID = 0;

	private final JButton save;
	private final JButton cancel;

	private final Document model;
	private final List<TransactionSplit> splits;
	private final long amount; //The sum of all splits must equal this number.
	private final boolean from; //Is this for a To or From?  This will affect what types of BCs can be used.
	
	//This keeps track of the relationship between TransactionSplits and JPanels, for displaying splits.
	private final Map<TransactionSplit, SplitEditorPanel> splitsToPanelsMap = new HashMap<TransactionSplit, SplitEditorPanel>();
	
	private final JPanel splitPanels;

	@SuppressWarnings("unchecked")
	public SplitTransactionDialog(MainFrame frame, Document model, List<TransactionSplit> splits, long amount, boolean from) {
		super(frame);

		this.model = model;
		this.splits = splits;
		this.amount = amount;
		this.from = from;

		splitPanels = new JPanel();
		
		save = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_SAVE));
		cancel = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_CANCEL));
	}

	public void init() {
		super.init();
		
		this.setLayout(new BorderLayout());
		
		splitPanels.setLayout(new GridBagLayout());
		this.add(splitPanels, BorderLayout.NORTH);

		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(cancel);
		buttonsPanel.add(save);
		this.add(buttonsPanel, BorderLayout.SOUTH);
	}

	public void updateButtons() {
		super.updateButtons();

		if (splits != null){
			long amount = 0;
			for (TransactionSplit split : splits) {
				amount += split.getAmount();
			}
			
			save.setEnabled(amount == this.amount);
		}
		else {
			save.setEnabled(false);
		}
	}

	public void updateContent() {
		List<TransactionSplit> splits = null;
		
		if (splits == null)
			splits = new ArrayList<TransactionSplit>();
		
		//First we remove splits which are not in the transaction
		for (TransactionSplit split : splitsToPanelsMap.keySet()) {
//			if (!splits.contains(split)){
//				splitPanels.remove(splitsToPanelsMap.get(split));
//				splitsToPanelsMap.remove(split);
//			}
		}

		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.2;
		
		//Next we add in new ones
		for (TransactionSplit split : splits) {
			if (!splitsToPanelsMap.keySet().contains(split)){
				try {
					SplitEditorPanel splitPanel;
					if (splitsToPanelsMap.get(split) != null)
						splitPanel = splitsToPanelsMap.get(split);
					else
						splitPanel = new SplitEditorPanel(split, from);
					
					final TransactionSplit finalSplit = split;
					c.gridx = 0;
					splitPanels.add(splitPanel, c);
					splitsToPanelsMap.put(split, splitPanel);
										
					c.gridx = 2;
					JButton removeButton = new JButton("-");
					removeButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
//							splitPanels.remove(splitPanel);
							splitsToPanelsMap.remove(finalSplit);
							updateContent();
						}
					});
					splitPanel.add(removeButton);
					
					c.gridy++;
				}
				catch (InvalidValueException ive){
					Log.warning("Error creating split editor", ive);
				}
			}
		}

		//Include a new line, for adding more
		try {
			SplitEditorPanel splitPanel = new SplitEditorPanel(model, from);
			c.gridx = 0;
			splitPanels.add(splitPanel, c);
			splitsToPanelsMap.put(null, splitPanel);
			
			c.gridx = 1;
			JButton addButton = new JButton("+");
			addButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					try {
						SplitEditorPanel editor = (SplitEditorPanel) splitsToPanelsMap.get(null); //Get the unsaved split editor
						if (editor != null){
							splitsToPanelsMap.put(editor.getNewSplit(), editor);
							splitsToPanelsMap.remove(null);
						}
						updateContent();
					}
					catch (InvalidValueException ive){
						Log.warning("You must fill in both amount and source");
					}
				}
			});
			splitPanel.add(addButton);
			
			c.gridx = 2;
			JButton removeButton = new JButton("-");
			splitPanel.add(removeButton);
		}
		catch (InvalidValueException ive){
			Log.warning("Error creating split editor", ive);
		}
		
		//Force a refresh
		splitPanels.invalidate();
		
		this.pack();
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
}
