/*
 * Created on May 8, 2006 by wyatt
 * 
 * A utility class which allows editing of transactions.
 */
package org.homeunix.thecave.buddi.view.panels;

import org.homeunix.thecave.buddi.model.TransactionSplit;
import org.homeunix.thecave.moss.swing.MossPanel;

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

	private final TransactionSplit split;
	
	public SplitEditorPanel(TransactionSplit split) {
		this.split = split;
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	@Override
	public void updateContent() {
		super.updateContent();
	}
}
