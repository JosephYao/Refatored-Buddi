/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import javax.swing.JPanel;

public abstract class AbstractBudgetPanel extends JPanel{	
	protected AbstractBudgetPanel(){}
	
	protected abstract AbstractBudgetPanel initActions();
	
	protected abstract AbstractBudgetPanel initContent();
	
	public abstract AbstractBudgetPanel updateContent();
	
	public abstract AbstractBudgetPanel updateButtons();

	
	/**
	 * Override this to give your window the ability to reset
	 * @return
	 */
	public AbstractBudgetPanel clearContent(){
		
		return this;
	}

	
	public AbstractBudgetPanel load(){
		updateContent();
		initContent();
		return this;
	}	
}