/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.keys.BudgetExpenseDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.BudgetIncomeDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodKeys;
import org.homeunix.thecave.buddi.i18n.keys.TypeCreditDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.TypeDebitDefaultKeys;
import org.homeunix.thecave.buddi.model.FilteredLists.AccountListFilteredByType;
import org.homeunix.thecave.buddi.model.FilteredLists.BudgetCategoryListFilteredByParent;
import org.homeunix.thecave.buddi.model.WrapperLists.WrapperAccountList;
import org.homeunix.thecave.buddi.model.WrapperLists.WrapperBudgetCategoryList;
import org.homeunix.thecave.buddi.model.WrapperLists.WrapperScheduledTransactionList;
import org.homeunix.thecave.buddi.model.WrapperLists.WrapperTransactionList;
import org.homeunix.thecave.buddi.model.WrapperLists.WrapperTypeList;
import org.homeunix.thecave.buddi.model.beans.BudgetPeriodBean;
import org.homeunix.thecave.buddi.model.beans.DataModelBean;
import org.homeunix.thecave.buddi.model.beans.ModelObjectBean;
import org.homeunix.thecave.buddi.model.beans.TransactionBean;
import org.homeunix.thecave.buddi.model.converter.ModelConverter;
import org.homeunix.thecave.buddi.model.exception.DataModelProblemException;
import org.homeunix.thecave.buddi.model.exception.DocumentLoadException;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.util.BudgetPeriodUtil;
import org.homeunix.thecave.moss.exception.DocumentSaveException;
import org.homeunix.thecave.moss.model.AbstractDocument;
import org.homeunix.thecave.moss.util.Log;

public class DataModel extends AbstractDocument {

	private DataModelBean dataModel;
	private final Map<String, ModelObjectBean> uidMap = new HashMap<String, ModelObjectBean>();

	/**
	 * Attempts to load a data model from file.  Works with Buddi 3 and legacy formats (although
	 * of course Buddi 3 is preferred). 
	 * @param file File to load
	 * @throws DocumentLoadException
	 */
	public DataModel(File file) throws DocumentLoadException {
		if (file == null)
			throw new DocumentLoadException("Error loading model: specfied file is null.");

		//This wil let us know where to save the file to.
		Log.debug("Data file: " + this.getFile());

		this.setFile(file);

		Log.debug("Data file: " + this.getFile());

		//Try to load the data model
		try {
			XMLDecoder decoder = new XMLDecoder(new FileInputStream(file));

			Object temp = decoder.readObject();
			if (temp instanceof DataModelBean){
				dataModel = (DataModelBean) temp;
			}
			else {
				throw new DocumentLoadException("Loaded data file not of type DataModelBean");
			}

			//Save this as the last data model.
			PrefsModel.getInstance().setLastOpenedDataFile(file);

			//Specify that we did not change this data model.
			resetChanged();
		}
		catch (FileNotFoundException fnfe){
			//TODO Throw normal exception.  Calling code should catch it, and create new data file.
			throw new DocumentLoadException("Error loading model: File " + file.getAbsolutePath() + " not found.");			
		}
		catch (NoSuchElementException nsee){
			//This is probably an old version of the data file.  Try to convert it...
			Log.warning("Unable to load Buddi 3 format data model.  Trying Buddi 2 format...");

			dataModel = ModelConverter.convert(file);

			//We do not save this by default
			setChanged();

			setFile(null);
		}

		//Refresh the UID Map...
		refreshUidMap();
	}

	/**
	 * Creates a new data model, with some default types and categories.
	 */
	public DataModel() {
		dataModel = new DataModelBean();
		setFile(null); //A null dataFile will prompt for location on first save.

		for (BudgetExpenseDefaultKeys s : BudgetExpenseDefaultKeys.values()){
			addBudgetCategory(new BudgetCategory(this, s.toString(), false));
		}
		for (BudgetIncomeDefaultKeys s : BudgetIncomeDefaultKeys.values()){
			addBudgetCategory(new BudgetCategory(this, s.toString(), true));
		}

		for (TypeDebitDefaultKeys s : TypeDebitDefaultKeys.values()){
			addType(new Type(this, s.toString(), false));
		}

		for (TypeCreditDefaultKeys s : TypeCreditDefaultKeys.values()){
			addType(new Type(this, s.toString(), true));
		}	

		refreshUidMap();

		setChanged();
	}

//	public void loadDataModel(File file) throws ModelLoadingException {

//	}

	/**
	 * Saves the data file to the current file.  If the file has not yet been set,
	 * this method silently returns.
	 * @throws SaveModelException
	 */
	public void save() throws DocumentSaveException {
		saveInternal(getFile(), false);
	}

	/**
	 * Saves the data file to the specified file.  If the specified file is 
	 * null, silently return without error and without saving.
	 * @param file
	 * @throws SaveModelException
	 */
	public void saveAs(File file) throws DocumentSaveException {
		saveInternal(file, true);
	}

	private void saveInternal(File file, boolean resetUid) throws DocumentSaveException {
		if (file == null)
			throw new DocumentSaveException("Error saving data file: specified file is null!");
		try {
			XMLEncoder encoder = new XMLEncoder(new FileOutputStream(file));

			//TODO Test to make sure this is reset at the right time.
			if (resetUid)
				dataModel.setUid(getGeneratedUid(dataModel));

			encoder.writeObject(dataModel);
			encoder.flush();
			encoder.close();

			//Save where we last saved this file.
			setFile(file);
			PrefsModel.getInstance().setLastOpenedDataFile(file);


			resetChanged();
		}
		catch (FileNotFoundException fnfe){
			throw new DocumentSaveException("Error saving data file", fnfe);
		}
	}

	/**
	 * Streams the current Data Model object as an XML encoded string.  This is primarily meant
	 * for troubleshooting crashes. 
	 * @return
	 */
	public String saveToString(){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(baos);
		encoder.writeObject(dataModel);
		encoder.flush();
		encoder.close();

		return baos.toString();
	}

	/**
	 * Returns a date that is the beginning of the budget period which contains
	 * the given date.  This depends on the value of getPeriodType.
	 * @param date
	 * @return
	 */
	public Date getStartOfBudgetPeriod(Date date){
		return BudgetPeriodUtil.getStartOfBudgetPeriod(getPeriodType(), date);
	}

	public BudgetPeriodKeys getPeriodType() {
		if (dataModel.getPeriodType() == null)
			setPeriodType(BudgetPeriodKeys.BUDGET_PERIOD_MONTH);
		return BudgetPeriodKeys.valueOf(dataModel.getPeriodType());
	}
	public void setPeriodType(BudgetPeriodKeys periodType) {
		dataModel.setPeriodType(periodType.toString());
	}

	public List<Account> getAccounts() {
		return new WrapperAccountList(this, dataModel.getAccounts());
	}

	/**
	 * Convenience class, to return all sources (Accounts and BudgetCategories).
	 * This method generates a list on the fly.  It is not automatically
	 * updated, so don't rely on it for model updates.
	 * @return
	 */
	public List<Source> getSources() {
		List<Source> sources = new LinkedList<Source>();
		for (Account a : getAccounts()) {
			sources.add(a);
		}
		for (BudgetCategory bc : getBudgetCategories()) {
			sources.add(bc);
		}

		return sources;
	}

	public String getPeriodKey(Date periodDate){
		return getPeriodType() + ":" + getStartOfBudgetPeriod(periodDate).getTime();
	}

	/**
	 * Reverses getPeriodKey
	 * @param periodKey
	 * @return
	 */
	public Date getPeriodDate(String periodKey){
		String[] splitKey = periodKey.split(":");
		if (splitKey.length > 1){
			long l = Long.parseLong(splitKey[1]);
			return getStartOfBudgetPeriod(new Date(l));
		}

		throw new DataModelProblemException("Cannot parse date from key " + periodKey, this);
	}

	public BudgetPeriod getBudgetPeriod(Date periodDate){
		return getBudgetPeriod(getPeriodKey(periodDate));
	}

	/**
	 * Returns a list of BudgetPeriods, covering the entire range of periods
	 * occupied by startDate to endDate.
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<BudgetPeriod> getBudgetPeriodsInRange(Date startDate, Date endDate){
		List<BudgetPeriod> budgetPeriods = new LinkedList<BudgetPeriod>();

		Date temp = BudgetPeriodUtil.getStartOfBudgetPeriod(getPeriodType(), startDate);

		while (temp.before(BudgetPeriodUtil.getEndOfBudgetPeriod(getPeriodType(), endDate))){
			budgetPeriods.add(getBudgetPeriod(temp));
			temp = BudgetPeriodUtil.getNextBudgetPeriod(getPeriodType(), temp);
		}

		return budgetPeriods;
	}

	public BudgetPeriod getBudgetPeriod(String periodKey){
		if (dataModel.getBudgetPeriods().get(periodKey) == null) {
			dataModel.getBudgetPeriods().put(periodKey, new BudgetPeriodBean());
			dataModel.getBudgetPeriods().get(periodKey).setPeriodDate(getPeriodDate(periodKey));
			if (Const.DEVEL) Log.debug("Added new budget period for date " + periodKey);

			setChanged();
		}
		return new BudgetPeriod(this, dataModel.getBudgetPeriods().get(periodKey));		
	}

	public List<BudgetCategory> getBudgetCategories(){
		return new WrapperBudgetCategoryList(this, dataModel.getBudgetCategories());
	}

	public List<ScheduledTransaction> getScheduledTransactions() {
		return new WrapperScheduledTransactionList(this, dataModel.getScheduledTransactions());
	}

	public List<Transaction> getTransactions() {
		return new WrapperTransactionList(this, dataModel.getTransactions());
	}

	/**
	 * Returns transactions which are associated with the given source.  This list will update
	 * with changes to the data model.
	 * @param source
	 * @return
	 */
	public List<Transaction> getTransactions(Source source){
		return new FilteredLists.TransactionListFilteredBySource(this, this.getTransactions(), source);
	}

	public List<Transaction> getTransactions(Date startDate, Date endDate){
		return new FilteredLists.TransactionListFilteredByDate(this,
				this.getTransactions(),
				startDate,
				endDate);
	}

	public List<Transaction> getTransactions(Source source, Date startDate, Date endDate){
		return new FilteredLists.TransactionListFilteredByDate(this,
				this.getTransactions(source),
				startDate,
				endDate);
	}

	public List<Type> getTypes() {
		return new WrapperTypeList(this, dataModel.getTypes());
	}

	public void addTransaction(Transaction transaction){
		checkValid(transaction, true, false);

		dataModel.getTransactions().add(transaction.getTransactionBean());
		setChanged();		
	}
	
	public void addScheduledTransaction(ScheduledTransaction scheduledTransaction){
		checkValid(scheduledTransaction, true, false);

		dataModel.getScheduledTransactions().add(scheduledTransaction.getScheduledTranasactionBean());
		setChanged();		
	}

	public void removeTransaction(Transaction transaction){
		checkValid(transaction, false, false);

		dataModel.getTransactions().remove(transaction.getTransactionBean());
		setChanged();		
	}

	public void removeScheduledTransaction(ScheduledTransaction scheduledTransaction){
		checkValid(scheduledTransaction, false, false);

		dataModel.getScheduledTransactions().remove(scheduledTransaction.getScheduledTranasactionBean());
		setChanged();		
	}

	public void addAccount(Account account){
		checkValid(account, true, false);

		dataModel.getAccounts().add(account.getAccountBean());
		setChanged();
	}

	public void removeAccount(Account account){
		checkValid(account, false, false);

		//Check if this is being used somewhere.  If so, 
		// we do the 'delete flag' deletion.
		boolean notPermanent = false;

		if (this.getTransactions(account).size() > 0)
			notPermanent = true;
		for (ScheduledTransaction st : this.getScheduledTransactions()) {
			if (st.getFrom().equals(account) || st.getTo().equals(account))
				notPermanent = true;
		}


		//Actually remove or set the delete flag as needed.
		if (notPermanent)
			account.setDeleted(true);
		else 
			dataModel.getAccounts().remove(account.getAccountBean());

		setChanged();
	}

	public void undeleteAccount(Account account){
		checkValid(account, false, false);

		account.setDeleted(false);

		setChanged();
	}

	public void addBudgetCategory(BudgetCategory budgetCategory){
		checkValid(budgetCategory, true, false);

		dataModel.getBudgetCategories().add(budgetCategory.getBudgetCategoryBean());
		setChanged();
	}

	public void removeBudgetCategory(BudgetCategory budgetCategory){
		checkValid(budgetCategory, false, false);

		List<BudgetCategory> catsToDelete = new FilteredLists.BudgetCategoryListFilteredByChildren(this, budgetCategory);

		//We either delete all the categories permanently, or none.
		// We run through each one and test to see if we can delete
		// it permanently; if we find one which we cannot, we
		// flag the list as such with permanent = false.
		//
		// Check if this is being used somewhere.  If so, 
		// we do the 'delete flag' deletion.
		boolean notPermanent = false;

		for (BudgetCategory bc : catsToDelete) {
			if (this.getTransactions(bc).size() > 0)
				notPermanent = true;
			for (ScheduledTransaction st : this.getScheduledTransactions()) {
				if (st.getFrom().equals(bc) || st.getTo().equals(bc))
					notPermanent = true;
			}
		}

		//We now execute the deletion.
		for (BudgetCategory bc : catsToDelete) {
			if (notPermanent){
				bc.setDeleted(true);
			}
			else {
				dataModel.getBudgetCategories().remove(bc.getBudgetCategoryBean());
			}
		}

		setChanged();
	}

	public void undeleteBudgetCategory(BudgetCategory budgetCategory){
		checkValid(budgetCategory, false, false);

		if (budgetCategory.getParent() != null && budgetCategory.getParent().isDeleted()){
			undeleteBudgetCategory(budgetCategory.getParent());
		}
	}

	public void addType(Type type){
		checkValid(type, true, false);

		dataModel.getTypes().add(type.getTypeBean());
		setChanged();
	}

	public void removeType(Type type){
		checkValid(type, false, false);

		dataModel.getTypes().remove(type.getTypeBean());
		setChanged();
	}

	public String getUid(ModelObjectBean object){
		return object.getUid();
	}

	/**
	 * Perform all the sanity checks here, for code simplicity
	 * @param object The object to be modified
	 * @param isAddOperation Is this an add operation? (This affects some of the checks,
	 * such as if the UID is already entered into the model).
	 * @param isUidRefresh Is this being called from the refreshUid method?
	 */
	private void checkValid(ModelObject object, boolean isAddOperation, boolean isUidRefresh){
		if (!object.getModel().equals(this))
			throw new DataModelProblemException("Cannot modify an object not in this model.", this);

		if (isAddOperation){
//			if (this.getUid(object.getBean()) != null)
//			throw new DataModelProblemException("Cannot have the same UID for multiple objects in the model.", this);

			if (object instanceof Account){
				for (Account a : getAccounts()) {
					if (a.getName().equalsIgnoreCase(((Account) object).getName()))
						throw new DataModelProblemException("Cannot have multiple accounts with the same name.", this);
				}
			}

			if (object instanceof BudgetCategory){
				for (BudgetCategory bc : getBudgetCategories()) {
					if (bc.getFullName().equalsIgnoreCase(((BudgetCategory) object).getFullName()))
						throw new DataModelProblemException("Cannot have multiple budget categories with the same name.", this);
				}
			}
			
			if (object instanceof ScheduledTransaction){
				for (ScheduledTransaction s : getScheduledTransactions()) {
					if (s.getScheduleName().equalsIgnoreCase(((ScheduledTransaction) object).getScheduleName()))
						throw new DataModelProblemException("Cannot have multiple scheduled transactions with the same name.", this);
				}				
			}
		}

		if (isAddOperation || isUidRefresh){
			if (uidMap.get(object.getBean().getUid()) != null)
				//TODO Do something drastic here... perhaps save an emergency data file, prompt, and quit.
				throw new DataModelProblemException("Identical UID already in model!  Model is probably corrupt!", this); 
		}

	}

	public ModelObjectBean getObjectByUid(String uid){
		return uidMap.get(uid);
	}

	public static String getGeneratedUid(ModelObjectBean object){
		long time = System.currentTimeMillis();
		int random = (int) (Math.random() * 0xFFFF);
		int hash = object.hashCode() & 0xFFFF;

		String uid = object.getClass().getCanonicalName() + Long.toHexString(time) + "," + Integer.toHexString(random) + "," + Integer.toHexString(hash);

		return uid;
	}

	public void registerUid(String uid, ModelObjectBean object){
		uidMap.put(uid, object);
	}

	/**
	 * Updates the balances of all accounts 
	 */
	public void updateAllBalances(){
		for (Account a : getAccounts()) {
			a.updateBalance();
		}
	}

	public void refreshUidMap(){
		uidMap.clear();

		for (Account a : getAccounts()) {
			checkValid(a, false, true);
			uidMap.put(a.getAccountBean().getUid(), a.getAccountBean());
		}

		for (BudgetCategory bc : getBudgetCategories()) {
			checkValid(bc, false, true);
			uidMap.put(bc.getBudgetCategoryBean().getUid(), bc.getBudgetCategoryBean());
		}

		for (BudgetPeriodBean bp : dataModel.getBudgetPeriods().values()) {
			checkValid(new BudgetPeriod(this, bp), false, true);
			uidMap.put(bp.getUid(), bp);			
		}

		for (Type t : getTypes()) {
			checkValid(t, false, true);
			uidMap.put(t.getTypeBean().getUid(), t.getTypeBean());	
		}

		for (Transaction t : getTransactions()) {
			checkValid(t, false, true);
			uidMap.put(t.getTransactionBean().getUid(), t.getTransactionBean());	
		}	
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n--Accounts and Types--\n");
		for (Type t : getTypes()) {
			sb.append(t).append("\n");

			for (Account a : new AccountListFilteredByType(this, t)) {
				sb.append("\t").append(a).append("\n");
			}
		}

		sb.append("\n--Budget Categories--\n");

		//We only show 3 levels here; if there are more, we figure that it is not needed to
		// output them in the toString method, as it is just for troubleshooting.
		for (BudgetCategory bc : new BudgetCategoryListFilteredByParent(this, null)) {
			sb.append(bc).append("\n");
			for (BudgetCategory child1 : new BudgetCategoryListFilteredByParent(this, bc)) {
				sb.append("\t").append(child1).append("\n");
				for (BudgetCategory child2 : new BudgetCategoryListFilteredByParent(this, child1)) {
					sb.append("\t\t").append(child2).append("\n");	
				}				
			}
		}

		sb.append("\n--Budget Periods--\n");
		List<String> periodDates = new LinkedList<String>(dataModel.getBudgetPeriods().keySet());
		Collections.sort(periodDates);
		for (String d : periodDates) {
			sb.append(d).append(getBudgetPeriod(d));
		}
		sb.append("\n--Transactions--\n");
		sb.append("Total transactions: ").append(getTransactions().size()).append("\n");
		for (Transaction t : getTransactions()) {
			sb.append(t.toString()).append("\n");
		}
		sb.append("--");

		return sb.toString();
	}

	public String getUid(){
		return dataModel.getUid();
	}

	public Transaction getTransactionPrototype(){
		TransactionBean tb = new TransactionBean();
		tb.setDate(new Date());
		tb.setDescription("This is a semi long description");
		tb.setNumber("1234567890-1234567890");
		tb.setAmount(1234567890);

		return new Transaction(this, tb);
	}
}
