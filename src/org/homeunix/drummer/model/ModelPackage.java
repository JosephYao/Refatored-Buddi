/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.homeunix.drummer.model.ModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "urn:org.homeunix.drummer.model.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.homeunix.drummer.model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelPackage eINSTANCE = org.homeunix.drummer.model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.SourceImpl <em>Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.SourceImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getSource()
	 * @generated
	 */
	int SOURCE = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Deleted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__DELETED = 1;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__CREATION_DATE = 2;

	/**
	 * The number of structural features of the '<em>Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.AccountImpl <em>Account</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.AccountImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getAccount()
	 * @generated
	 */
	int ACCOUNT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT__NAME = SOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Deleted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT__DELETED = SOURCE__DELETED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT__CREATION_DATE = SOURCE__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Balance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT__BALANCE = SOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Starting Balance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT__STARTING_BALANCE = SOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Credit Limit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT__CREDIT_LIMIT = SOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Interest Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT__INTEREST_RATE = SOURCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Due Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT__DUE_DATE = SOURCE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Account Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT__ACCOUNT_TYPE = SOURCE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Account</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.AccountsImpl <em>Accounts</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.AccountsImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getAccounts()
	 * @generated
	 */
	int ACCOUNTS = 1;

	/**
	 * The feature id for the '<em><b>Accounts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTS__ACCOUNTS = 0;

	/**
	 * The feature id for the '<em><b>All Accounts</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTS__ALL_ACCOUNTS = 1;

	/**
	 * The number of structural features of the '<em>Accounts</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.AutoSaveInfoImpl <em>Auto Save Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.AutoSaveInfoImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getAutoSaveInfo()
	 * @generated
	 */
	int AUTO_SAVE_INFO = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTO_SAVE_INFO__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTO_SAVE_INFO__AMOUNT = 1;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTO_SAVE_INFO__NUMBER = 2;

	/**
	 * The feature id for the '<em><b>Memo</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTO_SAVE_INFO__MEMO = 3;

	/**
	 * The feature id for the '<em><b>To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTO_SAVE_INFO__TO = 4;

	/**
	 * The feature id for the '<em><b>From</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTO_SAVE_INFO__FROM = 5;

	/**
	 * The number of structural features of the '<em>Auto Save Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTO_SAVE_INFO_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.CategoriesImpl <em>Categories</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.CategoriesImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getCategories()
	 * @generated
	 */
	int CATEGORIES = 3;

	/**
	 * The feature id for the '<em><b>Categories</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORIES__CATEGORIES = 0;

	/**
	 * The feature id for the '<em><b>All Categories</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORIES__ALL_CATEGORIES = 1;

	/**
	 * The number of structural features of the '<em>Categories</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORIES_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.CategoryImpl <em>Category</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.CategoryImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getCategory()
	 * @generated
	 */
	int CATEGORY = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__NAME = SOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Deleted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__DELETED = SOURCE__DELETED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__CREATION_DATE = SOURCE__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Budgeted Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__BUDGETED_AMOUNT = SOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Income</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__INCOME = SOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__PARENT = SOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Children</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__CHILDREN = SOURCE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Category</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.DataModelImpl <em>Data Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.DataModelImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getDataModel()
	 * @generated
	 */
	int DATA_MODEL = 5;

	/**
	 * The feature id for the '<em><b>All Transactions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_MODEL__ALL_TRANSACTIONS = 0;

	/**
	 * The feature id for the '<em><b>All Lists</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_MODEL__ALL_LISTS = 1;

	/**
	 * The feature id for the '<em><b>All Categories</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_MODEL__ALL_CATEGORIES = 2;

	/**
	 * The feature id for the '<em><b>All Types</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_MODEL__ALL_TYPES = 3;

	/**
	 * The feature id for the '<em><b>All Accounts</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_MODEL__ALL_ACCOUNTS = 4;

	/**
	 * The number of structural features of the '<em>Data Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_MODEL_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.ListsImpl <em>Lists</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.ListsImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getLists()
	 * @generated
	 */
	int LISTS = 6;

	/**
	 * The feature id for the '<em><b>All Lists</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LISTS__ALL_LISTS = 0;

	/**
	 * The feature id for the '<em><b>All Auto Save</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LISTS__ALL_AUTO_SAVE = 1;

	/**
	 * The number of structural features of the '<em>Lists</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LISTS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.ScheduleImpl <em>Schedule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.ScheduleImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getSchedule()
	 * @generated
	 */
	int SCHEDULE = 7;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.TransactionImpl <em>Transaction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.TransactionImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getTransaction()
	 * @generated
	 */
	int TRANSACTION = 9;

	/**
	 * The feature id for the '<em><b>Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__AMOUNT = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__DATE = 2;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__NUMBER = 3;

	/**
	 * The feature id for the '<em><b>Memo</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__MEMO = 4;

	/**
	 * The feature id for the '<em><b>Balance From</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__BALANCE_FROM = 5;

	/**
	 * The feature id for the '<em><b>Balance To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__BALANCE_TO = 6;

	/**
	 * The feature id for the '<em><b>Scheduled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__SCHEDULED = 7;

	/**
	 * The feature id for the '<em><b>Cleared</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__CLEARED = 8;

	/**
	 * The feature id for the '<em><b>Reconciled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__RECONCILED = 9;

	/**
	 * The feature id for the '<em><b>UID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__UID = 10;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__FROM = 11;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__TO = 12;

	/**
	 * The number of structural features of the '<em>Transaction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION_FEATURE_COUNT = 13;

	/**
	 * The feature id for the '<em><b>Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__AMOUNT = TRANSACTION__AMOUNT;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__DESCRIPTION = TRANSACTION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__DATE = TRANSACTION__DATE;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__NUMBER = TRANSACTION__NUMBER;

	/**
	 * The feature id for the '<em><b>Memo</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__MEMO = TRANSACTION__MEMO;

	/**
	 * The feature id for the '<em><b>Balance From</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__BALANCE_FROM = TRANSACTION__BALANCE_FROM;

	/**
	 * The feature id for the '<em><b>Balance To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__BALANCE_TO = TRANSACTION__BALANCE_TO;

	/**
	 * The feature id for the '<em><b>Scheduled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__SCHEDULED = TRANSACTION__SCHEDULED;

	/**
	 * The feature id for the '<em><b>Cleared</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__CLEARED = TRANSACTION__CLEARED;

	/**
	 * The feature id for the '<em><b>Reconciled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__RECONCILED = TRANSACTION__RECONCILED;

	/**
	 * The feature id for the '<em><b>UID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__UID = TRANSACTION__UID;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__FROM = TRANSACTION__FROM;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__TO = TRANSACTION__TO;

	/**
	 * The feature id for the '<em><b>Start Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__START_DATE = TRANSACTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Frequency Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__FREQUENCY_TYPE = TRANSACTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Schedule Day</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__SCHEDULE_DAY = TRANSACTION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Last Date Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__LAST_DATE_CREATED = TRANSACTION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>End Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__END_DATE = TRANSACTION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Schedule Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__SCHEDULE_NAME = TRANSACTION_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Schedule Week</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__SCHEDULE_WEEK = TRANSACTION_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Schedule Month</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__SCHEDULE_MONTH = TRANSACTION_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__MESSAGE = TRANSACTION_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Schedule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE_FEATURE_COUNT = TRANSACTION_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.TransactionsImpl <em>Transactions</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.TransactionsImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getTransactions()
	 * @generated
	 */
	int TRANSACTIONS = 10;

	/**
	 * The feature id for the '<em><b>All Transactions</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTIONS__ALL_TRANSACTIONS = 0;

	/**
	 * The feature id for the '<em><b>Transactions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTIONS__TRANSACTIONS = 1;

	/**
	 * The feature id for the '<em><b>Scheduled Transactions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTIONS__SCHEDULED_TRANSACTIONS = 2;

	/**
	 * The number of structural features of the '<em>Transactions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTIONS_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.TypeImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Credit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__CREDIT = 1;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.TypesImpl <em>Types</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.TypesImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getTypes()
	 * @generated
	 */
	int TYPES = 12;

	/**
	 * The feature id for the '<em><b>All Types</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPES__ALL_TYPES = 0;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPES__TYPES = 1;

	/**
	 * The number of structural features of the '<em>Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPES_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '<em>Date</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.Date
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getDate()
	 * @generated
	 */
	int DATE = 13;


	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.model.Account <em>Account</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Account</em>'.
	 * @see org.homeunix.drummer.model.Account
	 * @generated
	 */
	EClass getAccount();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Account#getBalance <em>Balance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Balance</em>'.
	 * @see org.homeunix.drummer.model.Account#getBalance()
	 * @see #getAccount()
	 * @generated
	 */
	EAttribute getAccount_Balance();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Account#getStartingBalance <em>Starting Balance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Starting Balance</em>'.
	 * @see org.homeunix.drummer.model.Account#getStartingBalance()
	 * @see #getAccount()
	 * @generated
	 */
	EAttribute getAccount_StartingBalance();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Account#getCreditLimit <em>Credit Limit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Credit Limit</em>'.
	 * @see org.homeunix.drummer.model.Account#getCreditLimit()
	 * @see #getAccount()
	 * @generated
	 */
	EAttribute getAccount_CreditLimit();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Account#getInterestRate <em>Interest Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Interest Rate</em>'.
	 * @see org.homeunix.drummer.model.Account#getInterestRate()
	 * @see #getAccount()
	 * @generated
	 */
	EAttribute getAccount_InterestRate();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Account#getDueDate <em>Due Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Due Date</em>'.
	 * @see org.homeunix.drummer.model.Account#getDueDate()
	 * @see #getAccount()
	 * @generated
	 */
	EAttribute getAccount_DueDate();

	/**
	 * Returns the meta object for the reference '{@link org.homeunix.drummer.model.Account#getAccountType <em>Account Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Account Type</em>'.
	 * @see org.homeunix.drummer.model.Account#getAccountType()
	 * @see #getAccount()
	 * @generated
	 */
	EReference getAccount_AccountType();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.model.Accounts <em>Accounts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Accounts</em>'.
	 * @see org.homeunix.drummer.model.Accounts
	 * @generated
	 */
	EClass getAccounts();

	/**
	 * Returns the meta object for the containment reference list '{@link org.homeunix.drummer.model.Accounts#getAccounts <em>Accounts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Accounts</em>'.
	 * @see org.homeunix.drummer.model.Accounts#getAccounts()
	 * @see #getAccounts()
	 * @generated
	 */
	EReference getAccounts_Accounts();

	/**
	 * Returns the meta object for the container reference '{@link org.homeunix.drummer.model.Accounts#getAllAccounts <em>All Accounts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>All Accounts</em>'.
	 * @see org.homeunix.drummer.model.Accounts#getAllAccounts()
	 * @see #getAccounts()
	 * @generated
	 */
	EReference getAccounts_AllAccounts();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.model.AutoSaveInfo <em>Auto Save Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Auto Save Info</em>'.
	 * @see org.homeunix.drummer.model.AutoSaveInfo
	 * @generated
	 */
	EClass getAutoSaveInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.AutoSaveInfo#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.homeunix.drummer.model.AutoSaveInfo#getDescription()
	 * @see #getAutoSaveInfo()
	 * @generated
	 */
	EAttribute getAutoSaveInfo_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.AutoSaveInfo#getAmount <em>Amount</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Amount</em>'.
	 * @see org.homeunix.drummer.model.AutoSaveInfo#getAmount()
	 * @see #getAutoSaveInfo()
	 * @generated
	 */
	EAttribute getAutoSaveInfo_Amount();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.AutoSaveInfo#getNumber <em>Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number</em>'.
	 * @see org.homeunix.drummer.model.AutoSaveInfo#getNumber()
	 * @see #getAutoSaveInfo()
	 * @generated
	 */
	EAttribute getAutoSaveInfo_Number();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.AutoSaveInfo#getMemo <em>Memo</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Memo</em>'.
	 * @see org.homeunix.drummer.model.AutoSaveInfo#getMemo()
	 * @see #getAutoSaveInfo()
	 * @generated
	 */
	EAttribute getAutoSaveInfo_Memo();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.AutoSaveInfo#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>To</em>'.
	 * @see org.homeunix.drummer.model.AutoSaveInfo#getTo()
	 * @see #getAutoSaveInfo()
	 * @generated
	 */
	EAttribute getAutoSaveInfo_To();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.AutoSaveInfo#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>From</em>'.
	 * @see org.homeunix.drummer.model.AutoSaveInfo#getFrom()
	 * @see #getAutoSaveInfo()
	 * @generated
	 */
	EAttribute getAutoSaveInfo_From();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.model.Categories <em>Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Categories</em>'.
	 * @see org.homeunix.drummer.model.Categories
	 * @generated
	 */
	EClass getCategories();

	/**
	 * Returns the meta object for the container reference '{@link org.homeunix.drummer.model.Categories#getAllCategories <em>All Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>All Categories</em>'.
	 * @see org.homeunix.drummer.model.Categories#getAllCategories()
	 * @see #getCategories()
	 * @generated
	 */
	EReference getCategories_AllCategories();

	/**
	 * Returns the meta object for the containment reference list '{@link org.homeunix.drummer.model.Categories#getCategories <em>Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Categories</em>'.
	 * @see org.homeunix.drummer.model.Categories#getCategories()
	 * @see #getCategories()
	 * @generated
	 */
	EReference getCategories_Categories();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.model.Category <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Category</em>'.
	 * @see org.homeunix.drummer.model.Category
	 * @generated
	 */
	EClass getCategory();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Category#getBudgetedAmount <em>Budgeted Amount</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Budgeted Amount</em>'.
	 * @see org.homeunix.drummer.model.Category#getBudgetedAmount()
	 * @see #getCategory()
	 * @generated
	 */
	EAttribute getCategory_BudgetedAmount();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Category#isIncome <em>Income</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Income</em>'.
	 * @see org.homeunix.drummer.model.Category#isIncome()
	 * @see #getCategory()
	 * @generated
	 */
	EAttribute getCategory_Income();

	/**
	 * Returns the meta object for the reference '{@link org.homeunix.drummer.model.Category#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see org.homeunix.drummer.model.Category#getParent()
	 * @see #getCategory()
	 * @generated
	 */
	EReference getCategory_Parent();

	/**
	 * Returns the meta object for the reference list '{@link org.homeunix.drummer.model.Category#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Children</em>'.
	 * @see org.homeunix.drummer.model.Category#getChildren()
	 * @see #getCategory()
	 * @generated
	 */
	EReference getCategory_Children();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.model.DataModel <em>Data Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Model</em>'.
	 * @see org.homeunix.drummer.model.DataModel
	 * @generated
	 */
	EClass getDataModel();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.model.DataModel#getAllCategories <em>All Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>All Categories</em>'.
	 * @see org.homeunix.drummer.model.DataModel#getAllCategories()
	 * @see #getDataModel()
	 * @generated
	 */
	EReference getDataModel_AllCategories();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.model.Schedule <em>Schedule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Schedule</em>'.
	 * @see org.homeunix.drummer.model.Schedule
	 * @generated
	 */
	EClass getSchedule();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Schedule#getStartDate <em>Start Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Date</em>'.
	 * @see org.homeunix.drummer.model.Schedule#getStartDate()
	 * @see #getSchedule()
	 * @generated
	 */
	EAttribute getSchedule_StartDate();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Schedule#getFrequencyType <em>Frequency Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Frequency Type</em>'.
	 * @see org.homeunix.drummer.model.Schedule#getFrequencyType()
	 * @see #getSchedule()
	 * @generated
	 */
	EAttribute getSchedule_FrequencyType();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Schedule#getScheduleDay <em>Schedule Day</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schedule Day</em>'.
	 * @see org.homeunix.drummer.model.Schedule#getScheduleDay()
	 * @see #getSchedule()
	 * @generated
	 */
	EAttribute getSchedule_ScheduleDay();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Schedule#getLastDateCreated <em>Last Date Created</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Date Created</em>'.
	 * @see org.homeunix.drummer.model.Schedule#getLastDateCreated()
	 * @see #getSchedule()
	 * @generated
	 */
	EAttribute getSchedule_LastDateCreated();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Schedule#getEndDate <em>End Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End Date</em>'.
	 * @see org.homeunix.drummer.model.Schedule#getEndDate()
	 * @see #getSchedule()
	 * @generated
	 */
	EAttribute getSchedule_EndDate();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Schedule#getScheduleName <em>Schedule Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schedule Name</em>'.
	 * @see org.homeunix.drummer.model.Schedule#getScheduleName()
	 * @see #getSchedule()
	 * @generated
	 */
	EAttribute getSchedule_ScheduleName();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Schedule#getScheduleWeek <em>Schedule Week</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schedule Week</em>'.
	 * @see org.homeunix.drummer.model.Schedule#getScheduleWeek()
	 * @see #getSchedule()
	 * @generated
	 */
	EAttribute getSchedule_ScheduleWeek();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Schedule#getScheduleMonth <em>Schedule Month</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schedule Month</em>'.
	 * @see org.homeunix.drummer.model.Schedule#getScheduleMonth()
	 * @see #getSchedule()
	 * @generated
	 */
	EAttribute getSchedule_ScheduleMonth();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Schedule#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.homeunix.drummer.model.Schedule#getMessage()
	 * @see #getSchedule()
	 * @generated
	 */
	EAttribute getSchedule_Message();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.model.DataModel#getAllTypes <em>All Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>All Types</em>'.
	 * @see org.homeunix.drummer.model.DataModel#getAllTypes()
	 * @see #getDataModel()
	 * @generated
	 */
	EReference getDataModel_AllTypes();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.model.DataModel#getAllTransactions <em>All Transactions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>All Transactions</em>'.
	 * @see org.homeunix.drummer.model.DataModel#getAllTransactions()
	 * @see #getDataModel()
	 * @generated
	 */
	EReference getDataModel_AllTransactions();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.model.DataModel#getAllLists <em>All Lists</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>All Lists</em>'.
	 * @see org.homeunix.drummer.model.DataModel#getAllLists()
	 * @see #getDataModel()
	 * @generated
	 */
	EReference getDataModel_AllLists();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.model.DataModel#getAllAccounts <em>All Accounts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>All Accounts</em>'.
	 * @see org.homeunix.drummer.model.DataModel#getAllAccounts()
	 * @see #getDataModel()
	 * @generated
	 */
	EReference getDataModel_AllAccounts();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.model.Lists <em>Lists</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Lists</em>'.
	 * @see org.homeunix.drummer.model.Lists
	 * @generated
	 */
	EClass getLists();

	/**
	 * Returns the meta object for the container reference '{@link org.homeunix.drummer.model.Lists#getAllLists <em>All Lists</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>All Lists</em>'.
	 * @see org.homeunix.drummer.model.Lists#getAllLists()
	 * @see #getLists()
	 * @generated
	 */
	EReference getLists_AllLists();

	/**
	 * Returns the meta object for the containment reference list '{@link org.homeunix.drummer.model.Lists#getAllAutoSave <em>All Auto Save</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>All Auto Save</em>'.
	 * @see org.homeunix.drummer.model.Lists#getAllAutoSave()
	 * @see #getLists()
	 * @generated
	 */
	EReference getLists_AllAutoSave();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.model.Source <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source</em>'.
	 * @see org.homeunix.drummer.model.Source
	 * @generated
	 */
	EClass getSource();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Source#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.homeunix.drummer.model.Source#getName()
	 * @see #getSource()
	 * @generated
	 */
	EAttribute getSource_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Source#isDeleted <em>Deleted</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Deleted</em>'.
	 * @see org.homeunix.drummer.model.Source#isDeleted()
	 * @see #getSource()
	 * @generated
	 */
	EAttribute getSource_Deleted();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Source#getCreationDate <em>Creation Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Date</em>'.
	 * @see org.homeunix.drummer.model.Source#getCreationDate()
	 * @see #getSource()
	 * @generated
	 */
	EAttribute getSource_CreationDate();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.model.Transaction <em>Transaction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transaction</em>'.
	 * @see org.homeunix.drummer.model.Transaction
	 * @generated
	 */
	EClass getTransaction();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Transaction#getAmount <em>Amount</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Amount</em>'.
	 * @see org.homeunix.drummer.model.Transaction#getAmount()
	 * @see #getTransaction()
	 * @generated
	 */
	EAttribute getTransaction_Amount();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Transaction#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.homeunix.drummer.model.Transaction#getDescription()
	 * @see #getTransaction()
	 * @generated
	 */
	EAttribute getTransaction_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Transaction#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see org.homeunix.drummer.model.Transaction#getDate()
	 * @see #getTransaction()
	 * @generated
	 */
	EAttribute getTransaction_Date();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Transaction#getNumber <em>Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number</em>'.
	 * @see org.homeunix.drummer.model.Transaction#getNumber()
	 * @see #getTransaction()
	 * @generated
	 */
	EAttribute getTransaction_Number();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Transaction#getMemo <em>Memo</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Memo</em>'.
	 * @see org.homeunix.drummer.model.Transaction#getMemo()
	 * @see #getTransaction()
	 * @generated
	 */
	EAttribute getTransaction_Memo();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Transaction#getBalanceFrom <em>Balance From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Balance From</em>'.
	 * @see org.homeunix.drummer.model.Transaction#getBalanceFrom()
	 * @see #getTransaction()
	 * @generated
	 */
	EAttribute getTransaction_BalanceFrom();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Transaction#getBalanceTo <em>Balance To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Balance To</em>'.
	 * @see org.homeunix.drummer.model.Transaction#getBalanceTo()
	 * @see #getTransaction()
	 * @generated
	 */
	EAttribute getTransaction_BalanceTo();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Transaction#isScheduled <em>Scheduled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scheduled</em>'.
	 * @see org.homeunix.drummer.model.Transaction#isScheduled()
	 * @see #getTransaction()
	 * @generated
	 */
	EAttribute getTransaction_Scheduled();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Transaction#isCleared <em>Cleared</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cleared</em>'.
	 * @see org.homeunix.drummer.model.Transaction#isCleared()
	 * @see #getTransaction()
	 * @generated
	 */
	EAttribute getTransaction_Cleared();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Transaction#isReconciled <em>Reconciled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reconciled</em>'.
	 * @see org.homeunix.drummer.model.Transaction#isReconciled()
	 * @see #getTransaction()
	 * @generated
	 */
	EAttribute getTransaction_Reconciled();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Transaction#getUID <em>UID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>UID</em>'.
	 * @see org.homeunix.drummer.model.Transaction#getUID()
	 * @see #getTransaction()
	 * @generated
	 */
	EAttribute getTransaction_UID();

	/**
	 * Returns the meta object for the reference '{@link org.homeunix.drummer.model.Transaction#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see org.homeunix.drummer.model.Transaction#getFrom()
	 * @see #getTransaction()
	 * @generated
	 */
	EReference getTransaction_From();

	/**
	 * Returns the meta object for the reference '{@link org.homeunix.drummer.model.Transaction#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see org.homeunix.drummer.model.Transaction#getTo()
	 * @see #getTransaction()
	 * @generated
	 */
	EReference getTransaction_To();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.model.Transactions <em>Transactions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transactions</em>'.
	 * @see org.homeunix.drummer.model.Transactions
	 * @generated
	 */
	EClass getTransactions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.homeunix.drummer.model.Transactions#getTransactions <em>Transactions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transactions</em>'.
	 * @see org.homeunix.drummer.model.Transactions#getTransactions()
	 * @see #getTransactions()
	 * @generated
	 */
	EReference getTransactions_Transactions();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.model.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see org.homeunix.drummer.model.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Type#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.homeunix.drummer.model.Type#getName()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.model.Type#isCredit <em>Credit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Credit</em>'.
	 * @see org.homeunix.drummer.model.Type#isCredit()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_Credit();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.model.Types <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Types</em>'.
	 * @see org.homeunix.drummer.model.Types
	 * @generated
	 */
	EClass getTypes();

	/**
	 * Returns the meta object for the container reference '{@link org.homeunix.drummer.model.Types#getAllTypes <em>All Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>All Types</em>'.
	 * @see org.homeunix.drummer.model.Types#getAllTypes()
	 * @see #getTypes()
	 * @generated
	 */
	EReference getTypes_AllTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.homeunix.drummer.model.Types#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see org.homeunix.drummer.model.Types#getTypes()
	 * @see #getTypes()
	 * @generated
	 */
	EReference getTypes_Types();

	/**
	 * Returns the meta object for the container reference '{@link org.homeunix.drummer.model.Transactions#getAllTransactions <em>All Transactions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>All Transactions</em>'.
	 * @see org.homeunix.drummer.model.Transactions#getAllTransactions()
	 * @see #getTransactions()
	 * @generated
	 */
	EReference getTransactions_AllTransactions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.homeunix.drummer.model.Transactions#getScheduledTransactions <em>Scheduled Transactions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Scheduled Transactions</em>'.
	 * @see org.homeunix.drummer.model.Transactions#getScheduledTransactions()
	 * @see #getTransactions()
	 * @generated
	 */
	EReference getTransactions_ScheduledTransactions();

	/**
	 * Returns the meta object for data type '{@link java.util.Date <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Date</em>'.
	 * @see java.util.Date
	 * @model instanceClass="java.util.Date"
	 * @generated
	 */
	EDataType getDate();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelFactory getModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals  {
		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.model.impl.AccountImpl <em>Account</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.model.impl.AccountImpl
		 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getAccount()
		 * @generated
		 */
		EClass ACCOUNT = eINSTANCE.getAccount();

		/**
		 * The meta object literal for the '<em><b>Balance</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNT__BALANCE = eINSTANCE.getAccount_Balance();

		/**
		 * The meta object literal for the '<em><b>Starting Balance</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNT__STARTING_BALANCE = eINSTANCE.getAccount_StartingBalance();

		/**
		 * The meta object literal for the '<em><b>Credit Limit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNT__CREDIT_LIMIT = eINSTANCE.getAccount_CreditLimit();

		/**
		 * The meta object literal for the '<em><b>Interest Rate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNT__INTEREST_RATE = eINSTANCE.getAccount_InterestRate();

		/**
		 * The meta object literal for the '<em><b>Due Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNT__DUE_DATE = eINSTANCE.getAccount_DueDate();

		/**
		 * The meta object literal for the '<em><b>Account Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACCOUNT__ACCOUNT_TYPE = eINSTANCE.getAccount_AccountType();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.model.impl.AccountsImpl <em>Accounts</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.model.impl.AccountsImpl
		 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getAccounts()
		 * @generated
		 */
		EClass ACCOUNTS = eINSTANCE.getAccounts();

		/**
		 * The meta object literal for the '<em><b>Accounts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACCOUNTS__ACCOUNTS = eINSTANCE.getAccounts_Accounts();

		/**
		 * The meta object literal for the '<em><b>All Accounts</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACCOUNTS__ALL_ACCOUNTS = eINSTANCE.getAccounts_AllAccounts();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.model.impl.AutoSaveInfoImpl <em>Auto Save Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.model.impl.AutoSaveInfoImpl
		 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getAutoSaveInfo()
		 * @generated
		 */
		EClass AUTO_SAVE_INFO = eINSTANCE.getAutoSaveInfo();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTO_SAVE_INFO__DESCRIPTION = eINSTANCE.getAutoSaveInfo_Description();

		/**
		 * The meta object literal for the '<em><b>Amount</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTO_SAVE_INFO__AMOUNT = eINSTANCE.getAutoSaveInfo_Amount();

		/**
		 * The meta object literal for the '<em><b>Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTO_SAVE_INFO__NUMBER = eINSTANCE.getAutoSaveInfo_Number();

		/**
		 * The meta object literal for the '<em><b>Memo</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTO_SAVE_INFO__MEMO = eINSTANCE.getAutoSaveInfo_Memo();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTO_SAVE_INFO__TO = eINSTANCE.getAutoSaveInfo_To();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTO_SAVE_INFO__FROM = eINSTANCE.getAutoSaveInfo_From();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.model.impl.CategoriesImpl <em>Categories</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.model.impl.CategoriesImpl
		 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getCategories()
		 * @generated
		 */
		EClass CATEGORIES = eINSTANCE.getCategories();

		/**
		 * The meta object literal for the '<em><b>All Categories</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CATEGORIES__ALL_CATEGORIES = eINSTANCE.getCategories_AllCategories();

		/**
		 * The meta object literal for the '<em><b>Categories</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CATEGORIES__CATEGORIES = eINSTANCE.getCategories_Categories();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.model.impl.CategoryImpl <em>Category</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.model.impl.CategoryImpl
		 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getCategory()
		 * @generated
		 */
		EClass CATEGORY = eINSTANCE.getCategory();

		/**
		 * The meta object literal for the '<em><b>Budgeted Amount</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CATEGORY__BUDGETED_AMOUNT = eINSTANCE.getCategory_BudgetedAmount();

		/**
		 * The meta object literal for the '<em><b>Income</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CATEGORY__INCOME = eINSTANCE.getCategory_Income();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CATEGORY__CHILDREN = eINSTANCE.getCategory_Children();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CATEGORY__PARENT = eINSTANCE.getCategory_Parent();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.model.impl.DataModelImpl <em>Data Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.model.impl.DataModelImpl
		 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getDataModel()
		 * @generated
		 */
		EClass DATA_MODEL = eINSTANCE.getDataModel();

		/**
		 * The meta object literal for the '<em><b>All Categories</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_MODEL__ALL_CATEGORIES = eINSTANCE.getDataModel_AllCategories();

		/**
		 * The meta object literal for the '<em><b>All Types</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_MODEL__ALL_TYPES = eINSTANCE.getDataModel_AllTypes();

		/**
		 * The meta object literal for the '<em><b>All Transactions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_MODEL__ALL_TRANSACTIONS = eINSTANCE.getDataModel_AllTransactions();

		/**
		 * The meta object literal for the '<em><b>All Lists</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_MODEL__ALL_LISTS = eINSTANCE.getDataModel_AllLists();

		/**
		 * The meta object literal for the '<em><b>All Accounts</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_MODEL__ALL_ACCOUNTS = eINSTANCE.getDataModel_AllAccounts();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.model.impl.ListsImpl <em>Lists</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.model.impl.ListsImpl
		 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getLists()
		 * @generated
		 */
		EClass LISTS = eINSTANCE.getLists();

		/**
		 * The meta object literal for the '<em><b>All Lists</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LISTS__ALL_LISTS = eINSTANCE.getLists_AllLists();

		/**
		 * The meta object literal for the '<em><b>All Auto Save</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LISTS__ALL_AUTO_SAVE = eINSTANCE.getLists_AllAutoSave();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.model.impl.ScheduleImpl <em>Schedule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.model.impl.ScheduleImpl
		 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getSchedule()
		 * @generated
		 */
		EClass SCHEDULE = eINSTANCE.getSchedule();

		/**
		 * The meta object literal for the '<em><b>Start Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEDULE__START_DATE = eINSTANCE.getSchedule_StartDate();

		/**
		 * The meta object literal for the '<em><b>Frequency Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEDULE__FREQUENCY_TYPE = eINSTANCE.getSchedule_FrequencyType();

		/**
		 * The meta object literal for the '<em><b>Schedule Day</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEDULE__SCHEDULE_DAY = eINSTANCE.getSchedule_ScheduleDay();

		/**
		 * The meta object literal for the '<em><b>Last Date Created</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEDULE__LAST_DATE_CREATED = eINSTANCE.getSchedule_LastDateCreated();

		/**
		 * The meta object literal for the '<em><b>End Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEDULE__END_DATE = eINSTANCE.getSchedule_EndDate();

		/**
		 * The meta object literal for the '<em><b>Schedule Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEDULE__SCHEDULE_NAME = eINSTANCE.getSchedule_ScheduleName();

		/**
		 * The meta object literal for the '<em><b>Schedule Week</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEDULE__SCHEDULE_WEEK = eINSTANCE.getSchedule_ScheduleWeek();

		/**
		 * The meta object literal for the '<em><b>Schedule Month</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEDULE__SCHEDULE_MONTH = eINSTANCE.getSchedule_ScheduleMonth();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEDULE__MESSAGE = eINSTANCE.getSchedule_Message();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.model.impl.SourceImpl <em>Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.model.impl.SourceImpl
		 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getSource()
		 * @generated
		 */
		EClass SOURCE = eINSTANCE.getSource();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE__NAME = eINSTANCE.getSource_Name();

		/**
		 * The meta object literal for the '<em><b>Deleted</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE__DELETED = eINSTANCE.getSource_Deleted();

		/**
		 * The meta object literal for the '<em><b>Creation Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE__CREATION_DATE = eINSTANCE.getSource_CreationDate();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.model.impl.TransactionImpl <em>Transaction</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.model.impl.TransactionImpl
		 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getTransaction()
		 * @generated
		 */
		EClass TRANSACTION = eINSTANCE.getTransaction();

		/**
		 * The meta object literal for the '<em><b>Amount</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTION__AMOUNT = eINSTANCE.getTransaction_Amount();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTION__DESCRIPTION = eINSTANCE.getTransaction_Description();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTION__DATE = eINSTANCE.getTransaction_Date();

		/**
		 * The meta object literal for the '<em><b>Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTION__NUMBER = eINSTANCE.getTransaction_Number();

		/**
		 * The meta object literal for the '<em><b>Memo</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTION__MEMO = eINSTANCE.getTransaction_Memo();

		/**
		 * The meta object literal for the '<em><b>Balance From</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTION__BALANCE_FROM = eINSTANCE.getTransaction_BalanceFrom();

		/**
		 * The meta object literal for the '<em><b>Balance To</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTION__BALANCE_TO = eINSTANCE.getTransaction_BalanceTo();

		/**
		 * The meta object literal for the '<em><b>Scheduled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTION__SCHEDULED = eINSTANCE.getTransaction_Scheduled();

		/**
		 * The meta object literal for the '<em><b>Cleared</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTION__CLEARED = eINSTANCE.getTransaction_Cleared();

		/**
		 * The meta object literal for the '<em><b>Reconciled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTION__RECONCILED = eINSTANCE.getTransaction_Reconciled();

		/**
		 * The meta object literal for the '<em><b>UID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTION__UID = eINSTANCE.getTransaction_UID();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSACTION__FROM = eINSTANCE.getTransaction_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSACTION__TO = eINSTANCE.getTransaction_To();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.model.impl.TransactionsImpl <em>Transactions</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.model.impl.TransactionsImpl
		 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getTransactions()
		 * @generated
		 */
		EClass TRANSACTIONS = eINSTANCE.getTransactions();

		/**
		 * The meta object literal for the '<em><b>Transactions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSACTIONS__TRANSACTIONS = eINSTANCE.getTransactions_Transactions();

		/**
		 * The meta object literal for the '<em><b>All Transactions</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSACTIONS__ALL_TRANSACTIONS = eINSTANCE.getTransactions_AllTransactions();

		/**
		 * The meta object literal for the '<em><b>Scheduled Transactions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSACTIONS__SCHEDULED_TRANSACTIONS = eINSTANCE.getTransactions_ScheduledTransactions();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.model.impl.TypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.model.impl.TypeImpl
		 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__NAME = eINSTANCE.getType_Name();

		/**
		 * The meta object literal for the '<em><b>Credit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__CREDIT = eINSTANCE.getType_Credit();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.model.impl.TypesImpl <em>Types</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.model.impl.TypesImpl
		 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getTypes()
		 * @generated
		 */
		EClass TYPES = eINSTANCE.getTypes();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPES__TYPES = eINSTANCE.getTypes_Types();

		/**
		 * The meta object literal for the '<em><b>All Types</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPES__ALL_TYPES = eINSTANCE.getTypes_AllTypes();

		/**
		 * The meta object literal for the '<em>Date</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.Date
		 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getDate()
		 * @generated
		 */
		EDataType DATE = eINSTANCE.getDate();

	}

} //ModelPackage
