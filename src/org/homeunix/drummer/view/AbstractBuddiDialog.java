/*
 * Created on Apr 1, 2007 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.net.URL;

import org.homeunix.thecave.moss.gui.abstracts.window.AbstractDialog;
import org.homeunix.thecave.moss.gui.abstracts.window.AbstractFrame;

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
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (null == cl) {
			cl = AbstractFrame.class.getClassLoader();
		}

		URL imageResource = cl.getResource("Buddi.gif");
		if (null != imageResource) {
			((java.awt.Frame) this.getOwner()).setIconImage(Toolkit.getDefaultToolkit().getImage(imageResource));
		}
	}
}
