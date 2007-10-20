/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.swing;

import java.awt.Component;
import java.awt.FontMetrics;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;

import org.homeunix.thecave.moss.util.Formatter;

public class MaxLengthListCellRenderer extends DefaultListCellRenderer {
	public static final long serialVersionUID = 0;

	private final int maxLength;
	private final JComponent component;

	private int computedLength = -1;

	public MaxLengthListCellRenderer(JComponent component) {
		this.component = component;
		this.maxLength = -1;
	}

	public MaxLengthListCellRenderer(int maxLength) {
		this.maxLength = maxLength;
		this.component = null;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

//		if (computedLength == -1)
			computeLength();

		if (value != null){
			String s = value.toString();
			if (s.length() > computedLength)
				this.setText(Formatter.getStringLengthFormat(computedLength).format(s));
			else
				this.setText(s);
		}
		else {
			this.setText(" ");
		}

		return this;
	}

	public void computeLength(){
		if (component != null){
			if (component.getGraphics() != null){
				//We test strings until the size is greater than the component.
				FontMetrics fm = component.getGraphics().getFontMetrics();
				String test = "XXXXX";
				for(; fm.stringWidth(test) < component.getWidth(); test += "X"){
					computedLength = test.length();
				}
			}
		}
		else {
			computedLength = maxLength;
		}

		if (computedLength <= 0)
			computedLength = 3;
	}
}
