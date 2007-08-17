/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.beans.AccountBean;
import org.homeunix.thecave.buddi.model.beans.BudgetCategoryBean;
import org.homeunix.thecave.buddi.model.beans.ScheduledTransactionBean;
import org.homeunix.thecave.buddi.model.beans.TransactionBean;
import org.homeunix.thecave.buddi.model.exception.DataModelProblemException;
import org.homeunix.thecave.moss.util.DateFunctions;

public class Transaction extends ModelObject {	
	public Transaction(DataModel model, Date date, String description, long amount, Source from, Source to) {
		this(model, new TransactionBean());

		this.setDate(date);
		this.setDescription(description);
		this.setAmount(amount);
		this.setFrom(from);
		this.setTo(to);
	}
	
	Transaction(DataModel model, TransactionBean transaction) {
		super(model, transaction);
	}
	
	
	public Boolean isCleared() {
		return getTransactionBean().isCleared();
	}
	public void setCleared(Boolean cleared) {
		getTransactionBean().setCleared(cleared);
		getModel().setChanged();
	}
	public Date getDate() {
		return getTransactionBean().getDate();
	}
	public void setDate(Date date) {
		if (date == null)
			throw new DataModelProblemException("Date cannot be null", getModel());
		getTransactionBean().setDate(date);
		getModel().setChanged();
	}
	public String getDescription() {
		return getTransactionBean().getDescription();
	}
	public void setDescription(String description) {
		if (description == null)
			throw new DataModelProblemException("Description cannot be null", getModel());
		getTransactionBean().setDescription(description);
		getModel().setChanged();
	}
	public String getMemo() {
		return getTransactionBean().getMemo();
	}
	public void setMemo(String memo) {
		getTransactionBean().setMemo(memo);
		getModel().setChanged();
	}
	public String getNumber() {
		return getTransactionBean().getNumber();
	}
	public void setNumber(String number) {
		getTransactionBean().getNumber();
		getModel().setChanged();
	}
	public Boolean isReconciled() {
		return getTransactionBean().isReconciled();
	}
	public void setReconciled(Boolean reconciled) {
		getTransactionBean().setReconciled(reconciled);
		getModel().setChanged();
	}
	public Boolean getScheduled() {
		return getTransactionBean().getScheduled();
	}
	public void setScheduled(Boolean scheduled) {
		getTransactionBean().setScheduled(scheduled);
		getModel().setChanged();
	}
	public Source getFrom(){
		if (getTransactionBean().getFrom() == null)
			return null;
		if (getTransactionBean().getFrom() instanceof AccountBean)
			return new Account(getModel(), (AccountBean) getTransactionBean().getFrom());
		else
			return new BudgetCategory(getModel(), (BudgetCategoryBean) getTransactionBean().getFrom());
	}
	public void setFrom(Source from){
		if (from == null)
			throw new DataModelProblemException("From cannot be null", getModel());
		if (!getModel().getAccounts().contains(from) && !getModel().getBudgetCategories().contains(from))
			throw new DataModelProblemException("From source must be entered in getModel()", getModel());
		
		getTransactionBean().setFrom(from.getSourceBean());
		getModel().setChanged();
	}
	public Source getTo(){
		if (getTransactionBean().getTo() == null)
			return null;
		if (getTransactionBean().getTo() instanceof AccountBean)
			return new Account(getModel(), (AccountBean) getTransactionBean().getTo());
		else
			return new BudgetCategory(getModel(), (BudgetCategoryBean) getTransactionBean().getTo());
	}
	public void setTo(Source to){
		if (to == null)
			throw new DataModelProblemException("To cannot be null", getModel());
		if (!getModel().getAccounts().contains(to) && !getModel().getBudgetCategories().contains(to))
			throw new DataModelProblemException("To source must be entered in getModel()", getModel());
		
		getTransactionBean().setTo(to.getSourceBean());
		getModel().setChanged();
	}
	
	
//	public void setTo(Source to){
//		if (!getModel().getAccounts().contains(to) && !getModel().getBudgetCategories().contains(to))
//			throw new InvalidValueException("Source must be entered in getModel()");
//
//		getTransactionBean().setSourceFrom(false);
//		getTransactionBean().setSource(to.getSourceBean());
//		getModel().setChanged();
//	}
//	public void setFrom(Source from){
//		if (!getModel().getAccounts().contains(from) && !getModel().getBudgetCategories().contains(from))
//			throw new InvalidValueException("Source must be entered in getModel()");
//
//		getTransactionBean().setSourceFrom(true);
//		getTransactionBean().setSource(from.getSourceBean());
//		getModel().setChanged();
//	}
//	public Source getTo(){
//		if (!getTransactionBean().isSourceFrom()){
//			if (getTransactionBean().getSource() instanceof AccountBean)
//				return new Account(getModel(), (AccountBean) getTransactionBean().getSource());
//			else
//				return new BudgetCategory(getModel(), (BudgetCategoryBean) getTransactionBean().getSource());
//		}
//		return null;
//	}
//	public Source getFrom(){
//		if (getTransactionBean().isSourceFrom()){
//			if (getTransactionBean().getSource() instanceof AccountBean)
//				return new Account(getModel(), (AccountBean) getTransactionBean().getSource());
//			else
//				return new BudgetCategory(getModel(), (BudgetCategoryBean) getTransactionBean().getSource());
//		}
//		return null;		
//	}
	
	public long getBalanceFrom() {
		return getTransactionBean().volatileGetBalanceFrom();
	}
	void setBalanceFrom(long balanceFrom) {
		getTransactionBean().volatileSetBalanceFrom(balanceFrom);
	}
	public long getBalanceTo() {
		return getTransactionBean().volatileGetBalanceTo();
	}
	void setBalanceTo(long balanceTo) {
		getTransactionBean().volatileSetBalanceTo(balanceTo);
	}

	public int compareTo(ModelObject arg0) {
		if (arg0 instanceof Transaction){
			Transaction t = (Transaction) arg0;
			if (this.equals(t))
				return 0;

			//We want to sort schedued transactions by name, for the list
			if (getTransactionBean() instanceof ScheduledTransactionBean && t.getTransactionBean() instanceof ScheduledTransactionBean)
				return ((ScheduledTransactionBean) getTransactionBean()).getScheduleName().compareTo(((ScheduledTransactionBean) t.getTransactionBean()).getScheduleName());

			//For regular transactions, first we sort by date 
			if (!DateFunctions.isSameDay(this.getDate(), t.getDate()))
				return this.getDate().compareTo(t.getDate());

			//Next we sort by debit / credit.  This is a nebulous beast, because of negative 
			// amounts, credit accounts, splits, etc.
			// We check the transaction source, and use some logic to determine if this means it
			// is inflow or outflow. 
			if (this.isInflow() != t.isInflow()){
				if (this.isInflow())
					return -1;
				return 1;
			}

			//If everything else is the same, we sort on description.
			else 
				return this.getDescription().compareTo(t.getDescription());
		}
		return super.compareTo(arg0);
	}

	public boolean isInflow(){
		if (getTransactionBean().getFrom() instanceof BudgetCategoryBean){
			return this.getAmount() >= 0;
		}
		if (getTransactionBean().getTo() instanceof BudgetCategoryBean){
			return this.getAmount() < 0;
		}
		
		//If neither sources are BudgetCategory, this is not an inflow.
		return false;
	}

	public long getAmount(){
		return getTransactionBean().getAmount();
	}
	public void setAmount(long amount){
		getTransactionBean().setAmount(amount);
	}
	
	TransactionBean getTransactionBean(){
		return (TransactionBean) getBean();
	}
	
	@Override
	public String toString() {
		return getDescription() + " (" + getDate() + "): 0x" + getBean().getUid() + ": Amount = " + getAmount() + ", From = " + getFrom() + ", To = " + getTo();
	}
}
