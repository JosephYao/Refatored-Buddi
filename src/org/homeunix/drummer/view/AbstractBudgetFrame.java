/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.Component;

import javax.swing.JFrame;

import org.homeunix.drummer.view.components.BudgetMenu;

public abstract class AbstractBudgetFrame extends JFrame{	
	protected AbstractBudgetFrame(){
		
		this.setJMenuBar(new BudgetMenu(this));
	}
	
	protected abstract AbstractBudgetFrame initActions();
	
	protected abstract AbstractBudgetFrame initContent();
	
	public abstract AbstractBudgetFrame updateContent();
	
	public abstract AbstractBudgetFrame updateButtons();

	
	/**
	 * Override this to give your window the ability to reset
	 * @return
	 */
	public AbstractBudgetFrame clearContent(){
		
		return this;
	}

	
	public AbstractBudgetFrame load(){
		updateContent();
		return this;
	}
	
	public AbstractBudgetFrame openWindow(){
		load();
		this.pack();
		this.setVisible(true);
		return this;
	}
	
	public abstract Component getPrintedComponent();
}