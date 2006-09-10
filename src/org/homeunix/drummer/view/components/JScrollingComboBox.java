package org.homeunix.drummer.view.components;

import java.util.Vector;

import javax.swing.JComboBox;

public class JScrollingComboBox extends JComboBox {
	public static final long serialVersionUID = 0;
	
	private static int DEFAULT_MAX_ROW_COUNT = 15;

	public JScrollingComboBox(){
		this(DEFAULT_MAX_ROW_COUNT);
	}
	
	public JScrollingComboBox(int maxRowCount){
		super();
		this.setMaximumRowCount(maxRowCount);
	}
	
	public JScrollingComboBox(Vector elements){
		super(elements);
		this.setMaximumRowCount(DEFAULT_MAX_ROW_COUNT);
	}
	
	public static void setDefaultMaxRowCount(int maxRowCount){
		DEFAULT_MAX_ROW_COUNT = maxRowCount;
	}
	
	public static int getDefaultMaxRowCount(){
		return DEFAULT_MAX_ROW_COUNT;
	}
}

