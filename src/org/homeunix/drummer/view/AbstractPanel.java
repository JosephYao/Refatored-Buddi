/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import javax.swing.JPanel;

public abstract class AbstractPanel extends JPanel{	
	protected AbstractPanel(){}
	
	protected abstract AbstractPanel initActions();
	
	protected abstract AbstractPanel initContent();
	
	public abstract AbstractPanel updateContent();
	
	public abstract AbstractPanel updateButtons();

	
	/**
	 * Override this to give your window the ability to reset
	 * @return
	 */
	public AbstractPanel clearContent(){
		
		return this;
	}

	
	public AbstractPanel load(){
		updateContent();
		initContent();
		return this;
	}	
}