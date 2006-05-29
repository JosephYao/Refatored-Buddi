/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.Frame;

import javax.swing.JDialog;

public abstract class AbstractBudgetDialog extends JDialog {
		
	private Frame owner;
	
	public AbstractBudgetDialog(Frame owner){
		super(owner);
		this.setResizable(false);
		this.owner = owner;
	}
	
	protected abstract AbstractBudgetDialog initActions();
	
	protected abstract AbstractBudgetDialog initContent();
	
	protected abstract AbstractBudgetDialog clearContent();
	
	protected abstract AbstractBudgetDialog updateContent();
	
	public abstract AbstractBudgetDialog updateButtons();

	public AbstractBudgetDialog openWindow(){
		initContent();
		updateButtons();

		this.pack();
		this.setModal(true);
		this.setLocationRelativeTo(owner);
		this.setVisible(true);

		return this;
	}
}