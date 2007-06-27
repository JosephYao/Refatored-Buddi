/*
 * Created on Jun 26, 2007 by wyatt
 */
package org.homeunix.drummer.view;

import javax.swing.JFrame;

import org.homeunix.drummer.view.components.EditableTransaction;

public class Test {
	public Test() {
		JFrame frame = new JFrame();

		frame.add(new EditableTransaction(null));
		
		frame.pack();
		frame.setVisible(true);	}

}
