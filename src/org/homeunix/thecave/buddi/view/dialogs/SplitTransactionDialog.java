/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.TransactionSplit;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.moss.swing.MossDialog;

public class SplitTransactionDialog extends MossDialog implements ActionListener {

	public static final long serialVersionUID = 0;

	private final JButton save;
	private final JButton cancel;

	private final Document model;
	private final Transaction transaction;
	private final boolean from; //Is this for a To or From?  This will affect what types of BCs can be used.
	
	private final Map<TransactionSplit, JPanel> panels = new HashMap<TransactionSplit, JPanel>();
	
	private final JPanel splitsPanel;

	@SuppressWarnings("unchecked")
	public SplitTransactionDialog(MainFrame frame, Document model, Transaction transaction, boolean from) {
		super(frame);

		this.model = model;
		this.transaction = transaction;
		this.from = from;

		splitsPanel = new JPanel();
		
		save = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_SAVE));
		cancel = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_CANCEL));
	}

	public void init() {
		super.init();
		
		this.setLayout(new BorderLayout());
		
		splitsPanel.setLayout(new GridLayout(0, 1));
		this.add(splitsPanel, BorderLayout.NORTH);

		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(cancel);
		buttonsPanel.add(save);
		this.add(buttonsPanel, BorderLayout.SOUTH);
	}

	public void updateButtons() {
		super.updateButtons();

		List<TransactionSplit> splits;
		if (from)
			splits = transaction.getFromSplits();
		else
			splits = transaction.getToSplits();
		
		if (splits != null){
			long amount = 0;
			for (TransactionSplit split : splits) {
				amount += split.getAmount();
			}
			
			save.setEnabled(amount == transaction.getAmount());
		}
		else {
			save.setEnabled(false);
		}
	}

	public void updateContent() {
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
}
