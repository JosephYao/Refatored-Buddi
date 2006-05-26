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
public interface ModelPackage extends EPackage{
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
	int SOURCE = 5;

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
	 * The number of structural features of the the '<em>Source</em>' class.
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
	 * The feature id for the '<em><b>Account Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT__ACCOUNT_TYPE = SOURCE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the the '<em>Account</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 3;

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
	 * The feature id for the '<em><b>All Accounts</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTS__ALL_ACCOUNTS = 0;

	/**
	 * The feature id for the '<em><b>Accounts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTS__ACCOUNTS = 1;

	/**
	 * The number of structural features of the the '<em>Accounts</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.CategoriesImpl <em>Categories</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.CategoriesImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getCategories()
	 * @generated
	 */
	int CATEGORIES = 2;

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
	 * The number of structural features of the the '<em>Categories</em>' class.
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
	int CATEGORY = 3;

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
	 * The number of structural features of the the '<em>Category</em>' class.
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
	int DATA_MODEL = 4;

	/**
	 * The feature id for the '<em><b>All Transactions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_MODEL__ALL_TRANSACTIONS = 0;

	/**
	 * The feature id for the '<em><b>All Accounts</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_MODEL__ALL_ACCOUNTS = 1;

	/**
	 * The feature id for the '<em><b>All Types</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_MODEL__ALL_TYPES = 2;

	/**
	 * The feature id for the '<em><b>All Categories</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_MODEL__ALL_CATEGORIES = 3;

	/**
	 * The number of structural features of the the '<em>Data Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_MODEL_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.TransactionImpl <em>Transaction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.TransactionImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getTransaction()
	 * @generated
	 */
	int TRANSACTION = 6;

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
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__TO = 7;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__FROM = 8;

	/**
	 * The number of structural features of the the '<em>Transaction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.TransactionsImpl <em>Transactions</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.TransactionsImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getTransactions()
	 * @generated
	 */
	int TRANSACTIONS = 7;

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
	 * The number of structural features of the the '<em>Transactions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTIONS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.model.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.model.impl.TypeImpl
	 * @see org.homeunix.drummer.model.impl.ModelPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 8;

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
	 * The number of structural features of the the '<em>Type</em>' class.
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
	int TYPES = 9;

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
	 * The number of structural features of the the '<em>Types</em>' class.
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
	int DATE = 10;


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

} //ModelPackage
