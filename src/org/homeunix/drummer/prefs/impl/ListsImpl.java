/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.homeunix.drummer.prefs.DictEntry;
import org.homeunix.drummer.prefs.ListEntry;
import org.homeunix.drummer.prefs.Lists;
import org.homeunix.drummer.prefs.PrefsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Lists</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.impl.ListsImpl#getDescDict <em>Desc Dict</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.ListsImpl#getListEntries <em>List Entries</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ListsImpl extends EObjectImpl implements Lists {
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
	 * The cached value of the '{@link #getListEntries() <em>List Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getListEntries()
	 * @generated
	 * @ordered
	 */
	protected EList listEntries = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ListsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PrefsPackage.Literals.LISTS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDescDict() {
		if (descDict == null) {
			descDict = new EObjectContainmentEList(DictEntry.class, this, PrefsPackage.LISTS__DESC_DICT);
		}
		return descDict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getListEntries() {
		if (listEntries == null) {
			listEntries = new EObjectContainmentEList(ListEntry.class, this, PrefsPackage.LISTS__LIST_ENTRIES);
		}
		return listEntries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PrefsPackage.LISTS__DESC_DICT:
				return ((InternalEList)getDescDict()).basicRemove(otherEnd, msgs);
			case PrefsPackage.LISTS__LIST_ENTRIES:
				return ((InternalEList)getListEntries()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PrefsPackage.LISTS__DESC_DICT:
				return getDescDict();
			case PrefsPackage.LISTS__LIST_ENTRIES:
				return getListEntries();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PrefsPackage.LISTS__DESC_DICT:
				getDescDict().clear();
				getDescDict().addAll((Collection)newValue);
				return;
			case PrefsPackage.LISTS__LIST_ENTRIES:
				getListEntries().clear();
				getListEntries().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case PrefsPackage.LISTS__DESC_DICT:
				getDescDict().clear();
				return;
			case PrefsPackage.LISTS__LIST_ENTRIES:
				getListEntries().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PrefsPackage.LISTS__DESC_DICT:
				return descDict != null && !descDict.isEmpty();
			case PrefsPackage.LISTS__LIST_ENTRIES:
				return listEntries != null && !listEntries.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ListsImpl