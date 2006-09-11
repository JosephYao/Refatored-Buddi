/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.Frame;

import javax.swing.JDialog;

public abstract class AbstractDialog extends JDialog {
		
	private Frame owner;
	
	public AbstractDialog(Frame owner){
		super(owner);
		this.setResizable(false);
		this.owner = owner;
	}
	
	protected abstract AbstractDialog initActions();
	
	protected abstract AbstractDialog initContent();
	
	protected abstract AbstractDialog clearContent();
	
	protected abstract AbstractDialog updateContent();
	
	public abstract AbstractDialog updateButtons();

	public AbstractDialog openWindow(){
		initContent();
		updateButtons();

		this.pack();
		this.setModal(true);
		this.setLocationRelativeTo(owner);
		this.setVisible(true);

		return this;
	}
}