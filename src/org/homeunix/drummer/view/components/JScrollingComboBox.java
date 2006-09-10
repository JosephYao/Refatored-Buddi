package org.homeunix.drummer.view.components;

import java.util.Vector;

import javax.swing.JComboBox;

public class JScrollingComboBox extends JComboBox {
	public static final long serialVersionUID = 0;

	public JScrollingComboBox(){
		this(15);
	}
	
	public JScrollingComboBox(int maxRowCount){
		super();
		this.setMaximumRowCount(maxRowCount);
	}
	
	public JScrollingComboBox(Vector elements){
		super(elements);
		this.setMaximumRowCount(10);
	}
}

