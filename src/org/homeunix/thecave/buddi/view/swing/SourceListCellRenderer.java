/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.swing;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JList;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Source;

public class SourceListCellRenderer extends MaxLengthListCellRenderer {
	public static final long serialVersionUID = 0;
	private final String nullLabel; 
	
	public SourceListCellRenderer(String nullLabel, int maxLength) {
		super(maxLength);
		this.nullLabel = nullLabel;
	}
	
	public SourceListCellRenderer(String nullLabel, JComponent component) {
		super(component);
		this.nullLabel = nullLabel;
	}
	
		
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		//Find correct string
		Object newValue = "";
		if (value instanceof Source)
			newValue = (((Source) value).getFullName());
		else if (value == null)
			newValue = nullLabel;
		else
			newValue = value;
		
		//Use the MaxLength renderer to cut off items which are too long
		super.getListCellRendererComponent(list, newValue, index, isSelected, cellHasFocus);
		
		//Color items correctly
		if (value instanceof Source){
			if ((value instanceof Account && ((Account) value).getAccountType().isCredit())
					|| (value instanceof BudgetCategory && !((BudgetCategory) value).isIncome()))
				this.setText("<font color='red'>" + this.getText() + "</font>");
			if (((Source) value).isDeleted())
				this.setText("<strike>" + this.getText() + "</strike>");
			this.setText("<html>" + this.getText() + "</html>");
		}
		else
			this.setText("<html><font color='gray'>" + this.getText() + "</font></html>");
		
		return this;
	}
}
