/*
 * Created on May 8, 2006 by wyatt
 * 
 * A utility class which allows editing of transactions.
 */
package org.homeunix.thecave.buddi.view.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.TransactionSplit;
import org.homeunix.thecave.buddi.model.impl.ModelFactory;
import org.homeunix.thecave.buddi.model.swing.SourceComboBoxModel;
import org.homeunix.thecave.buddi.plugin.api.exception.InvalidValueException;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.view.swing.SourceListCellRenderer;
import org.homeunix.thecave.moss.swing.MossDecimalField;
import org.homeunix.thecave.moss.swing.MossPanel;
import org.homeunix.thecave.moss.swing.MossScrollingComboBox;

/**
 * This panel represents each TransactionSplit object.  A list of these panels
 * will be shown in the SplitTransactionDialog, and users can add / remove these
 * at will.
 * 
 * @author wyatt
 *
 */
public class SplitEditorPanel extends MossPanel {
	public static final long serialVersionUID = 0;

	private final boolean from;
	private final Document model;
	private final TransactionSplit split;
	private final MossScrollingComboBox source;
	private final MossDecimalField amount;
	
	public SplitEditorPanel(Document model, boolean from) throws InvalidValueException {
		super(true);
		if (model == null)
			throw new InvalidValueException("Model must not be null");
		this.model = model;
		this.split = ModelFactory.createTransactionSplit(null, 0l);
		this.from = from;
		this.source = new MossScrollingComboBox();
		this.amount = new MossDecimalField();
		
		open();
	}
	
	public SplitEditorPanel(TransactionSplit split, boolean from) throws InvalidValueException {
		super(true);
		if (split.getDocument() == null)
			throw new InvalidValueException("Model assigned to Split must not be null");
		this.model = split.getDocument();
		this.split = split;
		this.from = from;
		source = new MossScrollingComboBox();
		amount = new MossDecimalField();
		
		open();
	}
	
	@Override
	public void init() {
		super.init();

		source.setPreferredSize(new Dimension(150, source.getPreferredSize().height));
		amount.setPreferredSize(new Dimension(100, amount.getPreferredSize().height));
		
		source.setModel(new SourceComboBoxModel(model, from, false));
		
		source.setRenderer(new SourceListCellRenderer(TextFormatter.getTranslation(BuddiKeys.HINT_SOURCE), source));
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.weighty = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.weightx = 0.2;
		c.gridx = 0;
		this.add(source, c);
		
		c.weightx = 0.1;
		c.gridx = 1;
		this.add(amount, c);
	}
	
	@Override
	public void updateContent() {
		super.updateContent();
		
		if (split != null){
			source.setSelectedItem(split.getSource());
			amount.setValue(split.getAmount());
		}
	}
	
	public TransactionSplit getNewSplit() throws InvalidValueException {
		if (!(source.getSelectedItem() instanceof Source) || amount.getValue() == 0){
			throw new InvalidValueException("You must fill in a non-zero amount, and select a source for the split.");
		}
		
		return ModelFactory.createTransactionSplit((Source) source.getSelectedItem(), amount.getValue()); 
	}
}
