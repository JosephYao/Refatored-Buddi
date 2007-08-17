/*
 * Created on Aug 4, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.swing;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.FilteredLists.BudgetCategoryListFilteredByParent;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.moss.util.DateFunctions;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

public class BudgetTreeTableModel extends AbstractTreeTableModel {

	private final DataModel model;
	private final Object root;
//	private int year;
//	private int month;
	private Date selectedDate;
	private int monthOffset; //Where the selected month is in relation to the 
							 // edge of the table.
	
	public BudgetTreeTableModel(DataModel model) {
		super(new Object());
		this.model = model;
		this.root = getRoot();
		this.selectedDate = new Date();
//		this.year = DateFunctions.getYear(new Date());
//		this.month = DateFunctions.getMonth(new Date());
		this.monthOffset = 2; //This puts the current month in the middle of three columns.
	}
	
	
	
//	public int getYear() {
//		return year;
//	}
//	public void setYear(int year) {
//		this.year = year;
//		fireStructureChanged();
//	}
//	
//	public int getMonth() {
//		return month;
//	}
//
//	public void setMonth(int month) {
//		this.month = month;
//		fireStructureChanged();
//	}

	public Date getSelectedDate() {
		return selectedDate;
	}



	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = model.getStartOfBudgetPeriod(selectedDate);
	}



	public int getColumnCount() {
		return PrefsModel.getInstance().getNumberOfBudgetColumns();
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 0)
			return PrefsModel.getInstance().getTranslator().get(BuddiKeys.BUDGET_CATEGORY);
		if (column >= 1 && column < getColumnCount()){		
			Date columnDate = DateFunctions.getDate(DateFunctions.getYear(selectedDate), DateFunctions.getMonth(selectedDate) - monthOffset + column);

			return new SimpleDateFormat("MMM yyyy").format(columnDate);
		}
		return "";
	}
	
	public Object getValueAt(Object node, int column) {
		if (column == -1)
			return node;
		if (node.getClass().equals(BudgetCategory.class)){
			BudgetCategory bc = (BudgetCategory) node;
			if (column == 0)
				return bc;
			if (column >= 1 && column < getColumnCount())
				return model.getBudgetPeriod(DateFunctions.getDate(DateFunctions.getYear(selectedDate), DateFunctions.getMonth(selectedDate) - monthOffset + column)).getAmount(bc);
		}
		return null;
	}

	public Object getChild(Object parent, int childIndex) {
		if (parent.equals(root)){
			List<BudgetCategory> budgetCategories = new BudgetCategoryListFilteredByParent(model, null);
			if (childIndex < budgetCategories.size())
				return budgetCategories.get(childIndex);
		}
		if (parent instanceof BudgetCategory){
			List<BudgetCategory> budgetCategories = new BudgetCategoryListFilteredByParent(model, (BudgetCategory) parent);
			if (childIndex < budgetCategories.size())
				return budgetCategories.get(childIndex);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if (parent.equals(root)){
			List<BudgetCategory> budgetCategories = new BudgetCategoryListFilteredByParent(model, null);
			return budgetCategories.size();
		}
		if (parent instanceof BudgetCategory){
			List<BudgetCategory> budgetCategories = new BudgetCategoryListFilteredByParent(model, (BudgetCategory) parent);
			return budgetCategories.size();
		}
		
		return 0;
	}

	public int getIndexOfChild(Object parent, Object child) {
		if (parent == null || child == null)
			return -1;
		
		if (parent.equals(root) && child instanceof BudgetCategory){
			return new BudgetCategoryListFilteredByParent(model, null).indexOf(child);
		}
		if (parent instanceof BudgetCategory && child instanceof BudgetCategory){
			return new BudgetCategoryListFilteredByParent(model, (BudgetCategory) parent).indexOf(child);
		}
		return -1;
	}
	
	
	
	@Override
	public void setValueAt(Object value, Object node, int column) {
		if (column >= 1 && column < getColumnCount()){
			if (node instanceof BudgetCategory){
				BudgetCategory bc = (BudgetCategory) node;
				try {
					long amount = Long.parseLong(value.toString());
					model.getBudgetPeriod(DateFunctions.getDate(DateFunctions.getYear(selectedDate), DateFunctions.getMonth(selectedDate) - monthOffset + column)).setAmount(bc, amount);
				}
				catch (NumberFormatException nfe){}
			}
		}
	}

	@Override
	public boolean isCellEditable(Object node, int column) {
		if (node instanceof BudgetCategory && column >= 1 && column < getColumnCount())
			return true;
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < getChildCount(root); i++) {
			Object o1 = getChild(root, i);
			sb.append(o1).append(" [");
			for (int j = 0; j < getChildCount(o1); j++){
				Object o2 = getChild(o1, j);
				sb.append(o2);
				if (j < (getChildCount(o1) - 1))
					sb.append(", ");
			}
			sb.append("]  ");
		}
		
		return sb.toString();
	}
	
	public void fireStructureChanged(){
		modelSupport.fireStructureChanged();
	}
}
