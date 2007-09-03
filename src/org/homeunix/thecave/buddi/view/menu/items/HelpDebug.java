/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.event.ActionEvent;

import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.moss.swing.ApplicationModel;
import org.homeunix.thecave.moss.swing.MossFrame;
import org.homeunix.thecave.moss.swing.MossMenuItem;

public class HelpDebug extends MossMenuItem {
	public static final long serialVersionUID = 0;

	public HelpDebug(MossFrame frame) {
		super(frame, "Dump Data Model");

		this.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		for (MossFrame frame : ApplicationModel.getInstance().getOpenFrames()) {
			if (frame instanceof MainFrame)
				System.out.println(((MainFrame) frame).getDocument());
		}
	}
}
