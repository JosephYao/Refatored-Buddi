/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.event.ActionEvent;

import org.homeunix.thecave.moss.swing.menu.MossMenuItem;
import org.homeunix.thecave.moss.swing.window.MossFrame;

public class WindowEntry extends MossMenuItem {
	public static final long serialVersionUID = 0;
	
	private final MossFrame targetFrame;
	
	public WindowEntry(MossFrame parentFrame, MossFrame targetFrame) {
		super(parentFrame, targetFrame.getTitle());
		
		this.targetFrame = targetFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		targetFrame.setVisible(true);
		targetFrame.requestFocusInWindow();		
	}
}
