/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.swing;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import org.homeunix.thecave.moss.util.Formatter;

public class MaxLengthListCellRenderer extends DefaultListCellRenderer {
	public static final long serialVersionUID = 0;
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		if (value != null){
			String s = value.toString();
			int maxLength = TransactionCellRenderer.getCurrentDescriptionLength(); 
			if (s.length() > maxLength)
				this.setText(Formatter.getLengthFormat(maxLength).format(s));
		}
		
		return this;
	}
}
