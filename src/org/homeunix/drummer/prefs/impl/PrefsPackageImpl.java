/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.homeunix.drummer.prefs.DictData;
import org.homeunix.drummer.prefs.DictEntry;
import org.homeunix.drummer.prefs.Interval;
import org.homeunix.drummer.prefs.Intervals;
import org.homeunix.drummer.prefs.ListAttributes;
import org.homeunix.drummer.prefs.ListEntry;
import org.homeunix.drummer.prefs.Prefs;
import org.homeunix.drummer.prefs.PrefsFactory;
import org.homeunix.drummer.prefs.PrefsPackage;

import org.homeunix.drummer.prefs.UserPrefs;
import org.homeunix.drummer.prefs.Version;
import org.homeunix.drummer.prefs.WindowAttributes;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PrefsPackageImpl extends EPackageImpl implements PrefsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dictDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dictEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass intervalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass intervalsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass listAttributesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass listEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass prefsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass userPrefsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass windowAttributesEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.homeunix.drummer.prefs.PrefsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PrefsPackageImpl() {
		super(eNS_URI, PrefsFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PrefsPackage init() {
		if (isInited) return (PrefsPackage)EPackage.Registry.INSTANCE.getEPackage(PrefsPackage.eNS_URI);

		// Obtain or create and register package
		PrefsPackageImpl thePrefsPackage = (PrefsPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof PrefsPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new PrefsPackageImpl());

		isInited = true;

		// Create package meta-data objects
		thePrefsPackage.createPackageContents();

		// Initialize created meta-data
		thePrefsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePrefsPackage.freeze();

		return thePrefsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDictData() {
		return dictDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDictData_Number() {
		return (EAttribute)dictDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDictData_Memo() {
		return (EAttribute)dictDataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDictData_Amount() {
		return (EAttribute)dictDataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDictData_To() {
		return (EAttribute)dictDataEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDictData_From() {
		return (EAttribute)dictDataEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDictEntry() {
		return dictEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDictEntry_Entry() {
		return (EAttribute)dictEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDictEntry_Data() {
		return (EReference)dictEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInterval() {
		return intervalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInterval_Name() {
		return (EAttribute)intervalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInterval_Length() {
		return (EAttribute)intervalEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInterval_Days() {
		return (EAttribute)intervalEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntervals() {
		return intervalsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIntervals_AllIntervals() {
		return (EReference)intervalsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getListAttributes() {
		return listAttributesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getListAttributes_Unrolled() {
		return (EAttribute)listAttributesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getListEntry() {
		return listEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getListEntry_Entry() {
		return (EAttribute)listEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getListEntry_Attributes() {
		return (EReference)listEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPrefs() {
		return prefsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_DataFile() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_Language() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_ShowDeletedAccounts() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_ShowDeletedCategories() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_DateFormat() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_BudgetPeriod() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_ShowAccountTypes() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_EnableUpdateNotifications() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPrefs_TransactionsWindow() {
		return (EReference)prefsEClass.getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPrefs_GraphsWindow() {
		return (EReference)prefsEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPrefs_MainWindow() {
		return (EReference)prefsEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPrefs_LastVersionRun() {
		return (EReference)prefsEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPrefs_ListEntries() {
		return (EReference)prefsEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPrefs_Intervals() {
		return (EReference)prefsEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPrefs_ReportsWindow() {
		return (EReference)prefsEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPrefs_DescDict() {
		return (EReference)prefsEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_SelectedInterval() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_ShowAutoComplete() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_CurrencySymbol() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_ShowCreditLimit() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_ShowInterestRate() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_ShowAdvanced() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_NumberOfBackups() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrefs_LookAndFeelClass() {
		return (EAttribute)prefsEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUserPrefs() {
		return userPrefsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUserPrefs_Prefs() {
		return (EReference)userPrefsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVersion() {
		return versionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVersion_Version() {
		return (EAttribute)versionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWindowAttributes() {
		return windowAttributesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWindowAttributes_X() {
		return (EAttribute)windowAttributesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWindowAttributes_Y() {
		return (EAttribute)windowAttributesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWindowAttributes_Width() {
		return (EAttribute)windowAttributesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWindowAttributes_Height() {
		return (EAttribute)windowAttributesEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrefsFactory getPrefsFactory() {
		return (PrefsFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		dictDataEClass = createEClass(DICT_DATA);
		createEAttribute(dictDataEClass, DICT_DATA__NUMBER);
		createEAttribute(dictDataEClass, DICT_DATA__MEMO);
		createEAttribute(dictDataEClass, DICT_DATA__AMOUNT);
		createEAttribute(dictDataEClass, DICT_DATA__TO);
		createEAttribute(dictDataEClass, DICT_DATA__FROM);

		dictEntryEClass = createEClass(DICT_ENTRY);
		createEAttribute(dictEntryEClass, DICT_ENTRY__ENTRY);
		createEReference(dictEntryEClass, DICT_ENTRY__DATA);

		intervalEClass = createEClass(INTERVAL);
		createEAttribute(intervalEClass, INTERVAL__NAME);
		createEAttribute(intervalEClass, INTERVAL__LENGTH);
		createEAttribute(intervalEClass, INTERVAL__DAYS);

		intervalsEClass = createEClass(INTERVALS);
		createEReference(intervalsEClass, INTERVALS__ALL_INTERVALS);

		listAttributesEClass = createEClass(LIST_ATTRIBUTES);
		createEAttribute(listAttributesEClass, LIST_ATTRIBUTES__UNROLLED);

		listEntryEClass = createEClass(LIST_ENTRY);
		createEAttribute(listEntryEClass, LIST_ENTRY__ENTRY);
		createEReference(listEntryEClass, LIST_ENTRY__ATTRIBUTES);

		prefsEClass = createEClass(PREFS);
		createEAttribute(prefsEClass, PREFS__DATA_FILE);
		createEAttribute(prefsEClass, PREFS__LANGUAGE);
		createEAttribute(prefsEClass, PREFS__SHOW_DELETED_ACCOUNTS);
		createEAttribute(prefsEClass, PREFS__SHOW_DELETED_CATEGORIES);
		createEAttribute(prefsEClass, PREFS__DATE_FORMAT);
		createEAttribute(prefsEClass, PREFS__BUDGET_PERIOD);
		createEAttribute(prefsEClass, PREFS__SHOW_ACCOUNT_TYPES);
		createEAttribute(prefsEClass, PREFS__ENABLE_UPDATE_NOTIFICATIONS);
		createEAttribute(prefsEClass, PREFS__SELECTED_INTERVAL);
		createEAttribute(prefsEClass, PREFS__SHOW_AUTO_COMPLETE);
		createEAttribute(prefsEClass, PREFS__CURRENCY_SYMBOL);
		createEAttribute(prefsEClass, PREFS__SHOW_CREDIT_LIMIT);
		createEAttribute(prefsEClass, PREFS__SHOW_INTEREST_RATE);
		createEAttribute(prefsEClass, PREFS__SHOW_ADVANCED);
		createEAttribute(prefsEClass, PREFS__NUMBER_OF_BACKUPS);
		createEAttribute(prefsEClass, PREFS__LOOK_AND_FEEL_CLASS);
		createEReference(prefsEClass, PREFS__INTERVALS);
		createEReference(prefsEClass, PREFS__GRAPHS_WINDOW);
		createEReference(prefsEClass, PREFS__LAST_VERSION_RUN);
		createEReference(prefsEClass, PREFS__MAIN_WINDOW);
		createEReference(prefsEClass, PREFS__LIST_ENTRIES);
		createEReference(prefsEClass, PREFS__REPORTS_WINDOW);
		createEReference(prefsEClass, PREFS__DESC_DICT);
		createEReference(prefsEClass, PREFS__TRANSACTIONS_WINDOW);

		userPrefsEClass = createEClass(USER_PREFS);
		createEReference(userPrefsEClass, USER_PREFS__PREFS);

		versionEClass = createEClass(VERSION);
		createEAttribute(versionEClass, VERSION__VERSION);

		windowAttributesEClass = createEClass(WINDOW_ATTRIBUTES);
		createEAttribute(windowAttributesEClass, WINDOW_ATTRIBUTES__X);
		createEAttribute(windowAttributesEClass, WINDOW_ATTRIBUTES__Y);
		createEAttribute(windowAttributesEClass, WINDOW_ATTRIBUTES__WIDTH);
		createEAttribute(windowAttributesEClass, WINDOW_ATTRIBUTES__HEIGHT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(dictDataEClass, DictData.class, "DictData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDictData_Number(), ecorePackage.getEString(), "number", null, 1, 1, DictData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDictData_Memo(), ecorePackage.getEString(), "memo", null, 1, 1, DictData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDictData_Amount(), ecorePackage.getELong(), "amount", null, 1, 1, DictData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDictData_To(), ecorePackage.getEString(), "to", null, 1, 1, DictData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDictData_From(), ecorePackage.getEString(), "from", null, 1, 1, DictData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dictEntryEClass, DictEntry.class, "DictEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDictEntry_Entry(), ecorePackage.getEString(), "entry", null, 1, 1, DictEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDictEntry_Data(), this.getDictData(), null, "data", null, 1, 1, DictEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(intervalEClass, Interval.class, "Interval", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInterval_Name(), ecorePackage.getEString(), "name", null, 1, 1, Interval.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInterval_Length(), ecorePackage.getELong(), "length", null, 1, 1, Interval.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInterval_Days(), ecorePackage.getEBoolean(), "days", null, 1, 1, Interval.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(intervalsEClass, Intervals.class, "Intervals", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIntervals_AllIntervals(), this.getInterval(), null, "allIntervals", null, 0, -1, Intervals.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(listAttributesEClass, ListAttributes.class, "ListAttributes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getListAttributes_Unrolled(), ecorePackage.getEBoolean(), "unrolled", null, 1, 1, ListAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(listEntryEClass, ListEntry.class, "ListEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getListEntry_Entry(), ecorePackage.getEString(), "entry", null, 1, 1, ListEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getListEntry_Attributes(), this.getListAttributes(), null, "attributes", null, 1, 1, ListEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(prefsEClass, Prefs.class, "Prefs", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPrefs_DataFile(), ecorePackage.getEString(), "dataFile", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrefs_Language(), ecorePackage.getEString(), "language", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrefs_ShowDeletedAccounts(), ecorePackage.getEBoolean(), "showDeletedAccounts", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrefs_ShowDeletedCategories(), ecorePackage.getEBoolean(), "showDeletedCategories", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrefs_DateFormat(), ecorePackage.getEString(), "dateFormat", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrefs_BudgetPeriod(), ecorePackage.getEString(), "budgetPeriod", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrefs_ShowAccountTypes(), ecorePackage.getEBoolean(), "showAccountTypes", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrefs_EnableUpdateNotifications(), ecorePackage.getEBoolean(), "enableUpdateNotifications", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrefs_SelectedInterval(), ecorePackage.getEString(), "selectedInterval", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrefs_ShowAutoComplete(), ecorePackage.getEBoolean(), "showAutoComplete", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrefs_CurrencySymbol(), ecorePackage.getEString(), "currencySymbol", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrefs_ShowCreditLimit(), ecorePackage.getEBoolean(), "showCreditLimit", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrefs_ShowInterestRate(), ecorePackage.getEBoolean(), "showInterestRate", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrefs_ShowAdvanced(), ecorePackage.getEBoolean(), "showAdvanced", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrefs_NumberOfBackups(), ecorePackage.getEInt(), "numberOfBackups", null, 0, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrefs_LookAndFeelClass(), ecorePackage.getEString(), "lookAndFeelClass", null, 0, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPrefs_Intervals(), this.getIntervals(), null, "intervals", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPrefs_GraphsWindow(), this.getWindowAttributes(), null, "graphsWindow", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPrefs_LastVersionRun(), this.getVersion(), null, "lastVersionRun", null, 0, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPrefs_MainWindow(), this.getWindowAttributes(), null, "mainWindow", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPrefs_ListEntries(), this.getListEntry(), null, "listEntries", null, 0, -1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPrefs_ReportsWindow(), this.getWindowAttributes(), null, "reportsWindow", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPrefs_DescDict(), this.getDictEntry(), null, "descDict", null, 0, -1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPrefs_TransactionsWindow(), this.getWindowAttributes(), null, "transactionsWindow", null, 1, 1, Prefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(userPrefsEClass, UserPrefs.class, "UserPrefs", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUserPrefs_Prefs(), this.getPrefs(), null, "prefs", null, 1, 1, UserPrefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(versionEClass, Version.class, "Version", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVersion_Version(), ecorePackage.getEString(), "version", null, 1, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(windowAttributesEClass, WindowAttributes.class, "WindowAttributes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getWindowAttributes_X(), ecorePackage.getEInt(), "x", null, 1, 1, WindowAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWindowAttributes_Y(), ecorePackage.getEInt(), "y", null, 1, 1, WindowAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWindowAttributes_Width(), ecorePackage.getEInt(), "width", null, 1, 1, WindowAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWindowAttributes_Height(), ecorePackage.getEInt(), "height", null, 1, 1, WindowAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //PrefsPackageImpl
