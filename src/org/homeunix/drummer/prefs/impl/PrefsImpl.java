/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.homeunix.drummer.prefs.DictEntry;
import org.homeunix.drummer.prefs.Prefs;
import org.homeunix.drummer.prefs.PrefsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Prefs</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getDataFile <em>Data File</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getLanguage <em>Language</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#isShowDeletedAccounts <em>Show Deleted Accounts</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#isShowDeletedCategories <em>Show Deleted Categories</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getDateFormat <em>Date Format</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getDescDict <em>Desc Dict</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getMemoDict <em>Memo Dict</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PrefsImpl extends EObjectImpl implements Prefs {
	/**
	 * The default value of the '{@link #getDataFile() <em>Data File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataFile()
	 * @generated
	 * @ordered
	 */
	protected static final String DATA_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDataFile() <em>Data File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataFile()
	 * @generated
	 * @ordered
	 */
	protected String dataFile = DATA_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected static final String LANGUAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected String language = LANGUAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #isShowDeletedAccounts() <em>Show Deleted Accounts</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowDeletedAccounts()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_DELETED_ACCOUNTS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowDeletedAccounts() <em>Show Deleted Accounts</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowDeletedAccounts()
	 * @generated
	 * @ordered
	 */
	protected boolean showDeletedAccounts = SHOW_DELETED_ACCOUNTS_EDEFAULT;

	/**
	 * The default value of the '{@link #isShowDeletedCategories() <em>Show Deleted Categories</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowDeletedCategories()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_DELETED_CATEGORIES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowDeletedCategories() <em>Show Deleted Categories</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowDeletedCategories()
	 * @generated
	 * @ordered
	 */
	protected boolean showDeletedCategories = SHOW_DELETED_CATEGORIES_EDEFAULT;

	/**
	 * The default value of the '{@link #getDateFormat() <em>Date Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String DATE_FORMAT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDateFormat() <em>Date Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateFormat()
	 * @generated
	 * @ordered
	 */
	protected String dateFormat = DATE_FORMAT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDescDict() <em>Desc Dict</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescDict()
	 * @generated
	 * @ordered
	 */
	protected EList descDict = null;

	/**
	 * The cached value of the '{@link #getMemoDict() <em>Memo Dict</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemoDict()
	 * @generated
	 * @ordered
	 */
	protected EList memoDict = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PrefsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PrefsPackage.eINSTANCE.getPrefs();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDataFile() {
		return dataFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataFile(String newDataFile) {
		String oldDataFile = dataFile;
		dataFile = newDataFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__DATA_FILE, oldDataFile, dataFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLanguage(String newLanguage) {
		String oldLanguage = language;
		language = newLanguage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__LANGUAGE, oldLanguage, language));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowDeletedAccounts() {
		return showDeletedAccounts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowDeletedAccounts(boolean newShowDeletedAccounts) {
		boolean oldShowDeletedAccounts = showDeletedAccounts;
		showDeletedAccounts = newShowDeletedAccounts;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__SHOW_DELETED_ACCOUNTS, oldShowDeletedAccounts, showDeletedAccounts));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowDeletedCategories() {
		return showDeletedCategories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowDeletedCategories(boolean newShowDeletedCategories) {
		boolean oldShowDeletedCategories = showDeletedCategories;
		showDeletedCategories = newShowDeletedCategories;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__SHOW_DELETED_CATEGORIES, oldShowDeletedCategories, showDeletedCategories));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDateFormat(String newDateFormat) {
		String oldDateFormat = dateFormat;
		dateFormat = newDateFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__DATE_FORMAT, oldDateFormat, dateFormat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMemoDict() {
		if (memoDict == null) {
			memoDict = new EObjectContainmentEList(DictEntry.class, this, PrefsPackage.PREFS__MEMO_DICT);
		}
		return memoDict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDescDict() {
		if (descDict == null) {
			descDict = new EObjectContainmentEList(DictEntry.class, this, PrefsPackage.PREFS__DESC_DICT);
		}
		return descDict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case PrefsPackage.PREFS__DESC_DICT:
					return ((InternalEList)getDescDict()).basicRemove(otherEnd, msgs);
				case PrefsPackage.PREFS__MEMO_DICT:
					return ((InternalEList)getMemoDict()).basicRemove(otherEnd, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case PrefsPackage.PREFS__DATA_FILE:
				return getDataFile();
			case PrefsPackage.PREFS__LANGUAGE:
				return getLanguage();
			case PrefsPackage.PREFS__SHOW_DELETED_ACCOUNTS:
				return isShowDeletedAccounts() ? Boolean.TRUE : Boolean.FALSE;
			case PrefsPackage.PREFS__SHOW_DELETED_CATEGORIES:
				return isShowDeletedCategories() ? Boolean.TRUE : Boolean.FALSE;
			case PrefsPackage.PREFS__DATE_FORMAT:
				return getDateFormat();
			case PrefsPackage.PREFS__DESC_DICT:
				return getDescDict();
			case PrefsPackage.PREFS__MEMO_DICT:
				return getMemoDict();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case PrefsPackage.PREFS__DATA_FILE:
				setDataFile((String)newValue);
				return;
			case PrefsPackage.PREFS__LANGUAGE:
				setLanguage((String)newValue);
				return;
			case PrefsPackage.PREFS__SHOW_DELETED_ACCOUNTS:
				setShowDeletedAccounts(((Boolean)newValue).booleanValue());
				return;
			case PrefsPackage.PREFS__SHOW_DELETED_CATEGORIES:
				setShowDeletedCategories(((Boolean)newValue).booleanValue());
				return;
			case PrefsPackage.PREFS__DATE_FORMAT:
				setDateFormat((String)newValue);
				return;
			case PrefsPackage.PREFS__DESC_DICT:
				getDescDict().clear();
				getDescDict().addAll((Collection)newValue);
				return;
			case PrefsPackage.PREFS__MEMO_DICT:
				getMemoDict().clear();
				getMemoDict().addAll((Collection)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case PrefsPackage.PREFS__DATA_FILE:
				setDataFile(DATA_FILE_EDEFAULT);
				return;
			case PrefsPackage.PREFS__LANGUAGE:
				setLanguage(LANGUAGE_EDEFAULT);
				return;
			case PrefsPackage.PREFS__SHOW_DELETED_ACCOUNTS:
				setShowDeletedAccounts(SHOW_DELETED_ACCOUNTS_EDEFAULT);
				return;
			case PrefsPackage.PREFS__SHOW_DELETED_CATEGORIES:
				setShowDeletedCategories(SHOW_DELETED_CATEGORIES_EDEFAULT);
				return;
			case PrefsPackage.PREFS__DATE_FORMAT:
				setDateFormat(DATE_FORMAT_EDEFAULT);
				return;
			case PrefsPackage.PREFS__DESC_DICT:
				getDescDict().clear();
				return;
			case PrefsPackage.PREFS__MEMO_DICT:
				getMemoDict().clear();
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case PrefsPackage.PREFS__DATA_FILE:
				return DATA_FILE_EDEFAULT == null ? dataFile != null : !DATA_FILE_EDEFAULT.equals(dataFile);
			case PrefsPackage.PREFS__LANGUAGE:
				return LANGUAGE_EDEFAULT == null ? language != null : !LANGUAGE_EDEFAULT.equals(language);
			case PrefsPackage.PREFS__SHOW_DELETED_ACCOUNTS:
				return showDeletedAccounts != SHOW_DELETED_ACCOUNTS_EDEFAULT;
			case PrefsPackage.PREFS__SHOW_DELETED_CATEGORIES:
				return showDeletedCategories != SHOW_DELETED_CATEGORIES_EDEFAULT;
			case PrefsPackage.PREFS__DATE_FORMAT:
				return DATE_FORMAT_EDEFAULT == null ? dateFormat != null : !DATE_FORMAT_EDEFAULT.equals(dateFormat);
			case PrefsPackage.PREFS__DESC_DICT:
				return descDict != null && !descDict.isEmpty();
			case PrefsPackage.PREFS__MEMO_DICT:
				return memoDict != null && !memoDict.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (dataFile: ");
		result.append(dataFile);
		result.append(", language: ");
		result.append(language);
		result.append(", showDeletedAccounts: ");
		result.append(showDeletedAccounts);
		result.append(", showDeletedCategories: ");
		result.append(showDeletedCategories);
		result.append(", dateFormat: ");
		result.append(dateFormat);
		result.append(')');
		return result.toString();
	}

} //PrefsImpl
