/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.swing;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JList;

import org.homeunix.thecave.buddi.model.Source;

public class SourceListCellRenderer extends MaxLengthListCellRenderer {
	public static final long serialVersionUID = 0;

	public SourceListCellRenderer(int maxLength) {
		super(maxLength);
	}
	
	public SourceListCellRenderer(JComponent component) {
		super(component);
	}
	
		
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		if (value instanceof Source)
			value = (((Source) value).getFullName());
		else
			value = " ";
		
		return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	}
}
