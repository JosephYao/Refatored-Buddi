/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model.impl;

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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.homeunix.drummer.model.Categories;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.DataModel;
import org.homeunix.drummer.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Categories</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.impl.CategoriesImpl#getCategories <em>Categories</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.CategoriesImpl#getAllCategories <em>All Categories</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CategoriesImpl extends EObjectImpl implements Categories {
	/**
	 * The cached value of the '{@link #getCategories() <em>Categories</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategories()
	 * @generated
	 * @ordered
	 */
	protected EList categories = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CategoriesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ModelPackage.eINSTANCE.getCategories();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataModel getAllCategories() {
		if (eContainerFeatureID != ModelPackage.CATEGORIES__ALL_CATEGORIES) return null;
		return (DataModel)eContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllCategories(DataModel newAllCategories) {
		if (newAllCategories != eContainer || (eContainerFeatureID != ModelPackage.CATEGORIES__ALL_CATEGORIES && newAllCategories != null)) {
			if (EcoreUtil.isAncestor(this, newAllCategories))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAllCategories != null)
				msgs = ((InternalEObject)newAllCategories).eInverseAdd(this, ModelPackage.DATA_MODEL__ALL_CATEGORIES, DataModel.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newAllCategories, ModelPackage.CATEGORIES__ALL_CATEGORIES, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CATEGORIES__ALL_CATEGORIES, newAllCategories, newAllCategories));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getCategories() {
		if (categories == null) {
			categories = new EObjectContainmentEList(Category.class, this, ModelPackage.CATEGORIES__CATEGORIES);
		}
		return categories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ModelPackage.CATEGORIES__ALL_CATEGORIES:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, ModelPackage.CATEGORIES__ALL_CATEGORIES, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ModelPackage.CATEGORIES__CATEGORIES:
					return ((InternalEList)getCategories()).basicRemove(otherEnd, msgs);
				case ModelPackage.CATEGORIES__ALL_CATEGORIES:
					return eBasicSetContainer(null, ModelPackage.CATEGORIES__ALL_CATEGORIES, msgs);
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
	public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs) {
		if (eContainerFeatureID >= 0) {
			switch (eContainerFeatureID) {
				case ModelPackage.CATEGORIES__ALL_CATEGORIES:
					return eContainer.eInverseRemove(this, ModelPackage.DATA_MODEL__ALL_CATEGORIES, DataModel.class, msgs);
				default:
					return eDynamicBasicRemoveFromContainer(msgs);
			}
		}
		return eContainer.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - eContainerFeatureID, null, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ModelPackage.CATEGORIES__CATEGORIES:
				return getCategories();
			case ModelPackage.CATEGORIES__ALL_CATEGORIES:
				return getAllCategories();
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
			case ModelPackage.CATEGORIES__CATEGORIES:
				getCategories().clear();
				getCategories().addAll((Collection)newValue);
				return;
			case ModelPackage.CATEGORIES__ALL_CATEGORIES:
				setAllCategories((DataModel)newValue);
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
			case ModelPackage.CATEGORIES__CATEGORIES:
				getCategories().clear();
				return;
			case ModelPackage.CATEGORIES__ALL_CATEGORIES:
				setAllCategories((DataModel)null);
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
			case ModelPackage.CATEGORIES__CATEGORIES:
				return categories != null && !categories.isEmpty();
			case ModelPackage.CATEGORIES__ALL_CATEGORIES:
				return getAllCategories() != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //CategoriesImpl
