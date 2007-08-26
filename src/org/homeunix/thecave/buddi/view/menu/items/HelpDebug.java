/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.items;

import java.awt.event.ActionEvent;

import org.homeunix.thecave.moss.swing.MossDocumentFrame;
import org.homeunix.thecave.moss.swing.MossMenuItem;

public class HelpDebug extends MossMenuItem {
	public static final long serialVersionUID = 0;

	public HelpDebug(MossDocumentFrame frame) {
		super(frame, "Dump Data Model");

		this.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println(((MossDocumentFrame) getFrame()).getDocument());
	}
}
