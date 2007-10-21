/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.swing;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;

import org.homeunix.thecave.moss.util.Formatter;

public class MaxLengthListCellRenderer extends DefaultListCellRenderer {
	public static final long serialVersionUID = 0;

	private final JComponent component;

	private int computedLength = -1;

	public MaxLengthListCellRenderer(JComponent component) {
		this.component = component;
	}

//	public MaxLengthListCellRenderer(int maxLength) {
//		this.maxLength = maxLength;
//		this.component = null;
//	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		if (value != null){
			String s = value.toString();
			if (s.length() > computedLength)
				this.setText(Formatter.getStringToLength(s, component.getWidth(), component.getGraphics().getFontMetrics()));
			else
				this.setText(s);
		}
		else {
			this.setText(" ");
		}

		return this;
	}
}
