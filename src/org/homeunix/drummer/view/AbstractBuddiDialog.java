/*
 * Created on Apr 1, 2007 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionListener;

import org.homeunix.thecave.moss.gui.abstractwindows.AbstractDialog;

/**
 * @author wyatt
 *
 * A class which sits in between the Moss window framework and 
 * Buddi's implementation of it.  This allows us to do some fancy
 * things (such as include an icon and set a menu bar) by default, 
 * without having to do it for every window.
 */
public abstract class AbstractBuddiDialog extends AbstractDialog implements ActionListener {
	
	public AbstractBuddiDialog(Frame frame) {
		super(frame);
		
		setupAbstractBuddiDialog();
	}
	
	public AbstractBuddiDialog(Dialog dialog) {
		super(dialog);
		
		setupAbstractBuddiDialog();
	}
	
	private void setupAbstractBuddiDialog() {		

	}
}
