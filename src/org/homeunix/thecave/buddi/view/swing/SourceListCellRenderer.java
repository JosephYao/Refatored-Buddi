/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.swing;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JList;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.swing.IndentedSourceWrapper;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;

public class SourceListCellRenderer extends MaxLengthListCellRenderer {
	public static final long serialVersionUID = 0;
	private final String nullLabel; 
	
	/**
	 * Creates a new Source list renderer, using the given string
	 * as the label when a source is not selected, with each item having
	 * a maximum length conputed from the specified component.
	 * @param nullLabel
	 * @param component
	 */
	public SourceListCellRenderer(String nullLabel, JComponent component) {
		super(component);
		this.nullLabel = nullLabel;
	}
	
		
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		//Find correct string
		Object newValue = "";
		if (value instanceof IndentedSourceWrapper)
			newValue = ((((IndentedSourceWrapper) value).getSource()).getFullName());
		else if (BuddiKeys.SPLITS.toString().equals(value))
			newValue = TextFormatter.getTranslation(value.toString());
		else if (index == -1)
			newValue = nullLabel;
		else
			newValue = value;
		
		//Use the MaxLength renderer to cut off items which are too long
		super.getListCellRendererComponent(list, newValue, index, isSelected, cellHasFocus);
		
		//Color items correctly
		if (value instanceof IndentedSourceWrapper){
			//This will make the credit / expense sources red.  I don't really like this...
//			if ((value instanceof Account && ((Account) value).getAccountType().isCredit())
//					|| (value instanceof BudgetCategory && !((BudgetCategory) value).isIncome()))
//				this.setText("<font color='red'>" + this.getText() + "</font>");
			IndentedSourceWrapper indentedSourceWrapper = (IndentedSourceWrapper) value;
			if (indentedSourceWrapper.getSource().isDeleted()){
				this.setText("<strike>" + this.getText() + "</strike>");
			}
			if (indentedSourceWrapper.getIndent() > 0){
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < indentedSourceWrapper.getIndent(); i++){
					sb.append("&nbsp;&nbsp;");
				}
				this.setText(sb.toString() + this.getText());
			}
			this.setText("<html>" + this.getText() + "</html>");
		}
		else if (!BuddiKeys.SPLITS.toString().equals(value))
			this.setText("<html><font color='gray'>" + this.getText() + "</font></html>");
		
		return this;
	}
}
