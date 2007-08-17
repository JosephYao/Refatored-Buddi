/*
 * Created on Aug 4, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.swing;

import java.util.List;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.Type;
import org.homeunix.thecave.buddi.model.FilteredLists.FilteredAccountList;
import org.homeunix.thecave.buddi.model.FilteredLists.FilteredTypeList;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.util.BuddiInternalFormatter;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

public class AccountTreeTableModel extends AbstractTreeTableModel {

	private final DataModel model;
	private final Object root;
	
	public AccountTreeTableModel(DataModel model) {
		super(new Object());
		this.model = model;
		this.root = getRoot();
	}
	
	public int getColumnCount() {
		return 3;
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 1)
			return PrefsModel.getInstance().getTranslator().get(BuddiKeys.ACCOUNT);
		if (column == 2)
			return PrefsModel.getInstance().getTranslator().get(BuddiKeys.AMOUNT);
		return "";
	}
	
	public Object getValueAt(Object node, int column) {
		if (column == -1)
			return node;
		if (column == 0)
			return "";
		if (node.getClass().equals(Account.class)){
			Account a = (Account) node;
			if (column == 1)
				return BuddiInternalFormatter.getFormattedNameForAccount(a).replaceAll("<html>", "<html>&nbsp&nbsp&nbsp ");
			if (column == 2)
				return BuddiInternalFormatter.getFormattedCurrency(a.getBalance(), a.getType().isCredit());
		}
		if (node.getClass().equals(Type.class)){
			Type t = (Type) node;
			if (column == 1)
				return BuddiInternalFormatter.getFormattedNameForType(t);
			if (column == 2) {
				int amount = 0;
				for (Account a : new FilteredAccountList(model, t)) {
					amount += a.getBalance();
				}
				return BuddiInternalFormatter.getFormattedCurrency(amount, t.isCredit());
			}
		}
		return null;
	}

	public Object getChild(Object parent, int childIndex) {
		if (parent.equals(root)){
			List<Type> types = new FilteredTypeList(model);
			if (childIndex < types.size())
				return types.get(childIndex);
		}
		if (parent instanceof Type){
			List<Account> accounts = new FilteredAccountList(model, (Type) parent);
			if (childIndex < accounts.size())
				return accounts.get(childIndex);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if (parent.equals(root)){
			List<Type> types = new FilteredTypeList(model);
			return types.size();
		}
		if (parent instanceof Type){
			List<Account> accounts = new FilteredAccountList(model, (Type) parent);
			return accounts.size();
		}
		
		return 0;
	}

	public int getIndexOfChild(Object parent, Object child) {
		if (parent == null || child == null)
			return -1;
		
		if (parent.equals(root) && child instanceof Type){
			List<Type> types = new FilteredTypeList(model);
			return types.indexOf(child);
		}
		
		if (parent instanceof Type && child instanceof Account){
			List<Account> accounts = new FilteredAccountList(model, (Type) parent);
			return accounts.indexOf(child);
		}
		return -1;
	}
	
	public void fireStructureChanged(){
		modelSupport.fireStructureChanged();
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
}
