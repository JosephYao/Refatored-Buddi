/*
 * Created on Aug 4, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.swing;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.BudgetPeriodType;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.FilteredLists;
import org.homeunix.thecave.buddi.model.FilteredLists.BudgetCategoryListFilteredByParent;
import org.homeunix.thecave.buddi.model.periods.BudgetPeriodMonthly;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

public class BudgetTreeTableModel extends AbstractTreeTableModel {

	private final DataModel model;
	private final Object root;
//	private int year;
//	private int month;
	private Date selectedDate;
	private BudgetPeriodType selectedBudgetPeriodType;
	
	private final Map<BudgetPeriodType, List<BudgetCategory>> budgetCategoriesByType;
	
	private int monthOffset; //Where the selected month is in relation to the 
							 // edge of the table.
	
	public BudgetTreeTableModel(DataModel model) {
		super(new Object());
		this.budgetCategoriesByType = new HashMap<BudgetPeriodType, List<BudgetCategory>>();
		this.model = model;
		this.root = getRoot();
		this.selectedDate = new Date();
//		this.year = DateFunctions.getYear(new Date());
//		this.month = DateFunctions.getMonth(new Date());
		this.monthOffset = 2; //This puts the current month in the middle of three columns.
		
		for (BudgetPeriodType type : Const.BUDGET_PERIOD_TYPES) {
			budgetCategoriesByType.put(type, new FilteredLists.BudgetCategoryListFilteredByPeriodType(model, type));
		}
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
		this.selectedDate = getSelectedBudgetPeriodType().getStartOfBudgetPeriod(selectedDate);
	}

	public BudgetPeriodType getSelectedBudgetPeriodType(){
		if (selectedBudgetPeriodType == null)
			selectedBudgetPeriodType = new BudgetPeriodMonthly();
		return selectedBudgetPeriodType;
	}
	
	public void setSelectedBudgetPeriodType(BudgetPeriodType periodType){
		this.selectedBudgetPeriodType = periodType;
		
		setSelectedDate(getSelectedDate());
	}

	public int getColumnCount() {
		return PrefsModel.getInstance().getNumberOfBudgetColumns();
	}
	
//	@Override
//	public Class<?> getColumnClass(int column) {
//		if (column == 0)
//			return String.class;
//		if (column >= 1 && column < getColumnCount())
//			return Long.class;
//		return super.getColumnClass(column);
//	}
//	
	@Override
	public String getColumnName(int column) {
		if (column == 0)
			return PrefsModel.getInstance().getTranslator().get(BuddiKeys.BUDGET_CATEGORY);

		if (column >= 1 && column < getColumnCount())
			return new SimpleDateFormat(getSelectedBudgetPeriodType().getDateFormat()).format(getColumnDate(column));
			
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
				return bc.getAmount(getColumnDate(column));
		}
		return null;
	}

	public Object getChild(Object parent, int childIndex) {
		if (parent.equals(root)){
			List<BudgetCategory> budgetCategories = new BudgetCategoryListFilteredByParent(model, budgetCategoriesByType.get(getSelectedBudgetPeriodType()), null);
			if (childIndex < budgetCategories.size())
				return budgetCategories.get(childIndex);
		}
		if (parent instanceof BudgetCategory){
			List<BudgetCategory> budgetCategories = new BudgetCategoryListFilteredByParent(model, budgetCategoriesByType.get(getSelectedBudgetPeriodType()), (BudgetCategory) parent);
			if (childIndex < budgetCategories.size())
				return budgetCategories.get(childIndex);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if (parent.equals(root)){
			List<BudgetCategory> budgetCategories = new BudgetCategoryListFilteredByParent(model, budgetCategoriesByType.get(getSelectedBudgetPeriodType()), null);
			return budgetCategories.size();
		}
		if (parent instanceof BudgetCategory){
			List<BudgetCategory> budgetCategories = new BudgetCategoryListFilteredByParent(model, budgetCategoriesByType.get(getSelectedBudgetPeriodType()), (BudgetCategory) parent);
			return budgetCategories.size();
		}
		
		return 0;
	}

	public int getIndexOfChild(Object parent, Object child) {
		if (parent == null || child == null)
			return -1;
		
		if (parent.equals(root) && child instanceof BudgetCategory){
			return new BudgetCategoryListFilteredByParent(model, budgetCategoriesByType.get(getSelectedBudgetPeriodType()), null).indexOf(child);
		}
		if (parent instanceof BudgetCategory && child instanceof BudgetCategory){
			return new BudgetCategoryListFilteredByParent(model, budgetCategoriesByType.get(getSelectedBudgetPeriodType()), (BudgetCategory) parent).indexOf(child);
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
					bc.setAmount(getColumnDate(column), amount);
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
	
	private Date getColumnDate(int column){
		return getSelectedBudgetPeriodType().getBudgetPeriodOffset(getSelectedDate(), column - monthOffset);
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
