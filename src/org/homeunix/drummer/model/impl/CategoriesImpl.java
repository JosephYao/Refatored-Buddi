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
 *   <li>{@link org.homeunix.drummer.model.impl.CategoriesImpl#getAllCategories <em>All Categories</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.CategoriesImpl#getCategories <em>Categories</em>}</li>
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
		return ModelPackage.Literals.CATEGORIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataModel getAllCategories() {
		if (eContainerFeatureID != ModelPackage.CATEGORIES__ALL_CATEGORIES) return null;
		return (DataModel)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAllCategories(DataModel newAllCategories, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newAllCategories, ModelPackage.CATEGORIES__ALL_CATEGORIES, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllCategories(DataModel newAllCategories) {
		if (newAllCategories != eInternalContainer() || (eContainerFeatureID != ModelPackage.CATEGORIES__ALL_CATEGORIES && newAllCategories != null)) {
			if (EcoreUtil.isAncestor(this, newAllCategories))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAllCategories != null)
				msgs = ((InternalEObject)newAllCategories).eInverseAdd(this, ModelPackage.DATA_MODEL__ALL_CATEGORIES, DataModel.class, msgs);
			msgs = basicSetAllCategories(newAllCategories, msgs);
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
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.CATEGORIES__ALL_CATEGORIES:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetAllCategories((DataModel)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.CATEGORIES__ALL_CATEGORIES:
				return basicSetAllCategories(null, msgs);
			case ModelPackage.CATEGORIES__CATEGORIES:
				return ((InternalEList)getCategories()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID) {
			case ModelPackage.CATEGORIES__ALL_CATEGORIES:
				return eInternalContainer().eInverseRemove(this, ModelPackage.DATA_MODEL__ALL_CATEGORIES, DataModel.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.CATEGORIES__ALL_CATEGORIES:
				return getAllCategories();
			case ModelPackage.CATEGORIES__CATEGORIES:
				return getCategories();
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
			case ModelPackage.CATEGORIES__ALL_CATEGORIES:
				setAllCategories((DataModel)newValue);
				return;
			case ModelPackage.CATEGORIES__CATEGORIES:
				getCategories().clear();
				getCategories().addAll((Collection)newValue);
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
			case ModelPackage.CATEGORIES__ALL_CATEGORIES:
				setAllCategories((DataModel)null);
				return;
			case ModelPackage.CATEGORIES__CATEGORIES:
				getCategories().clear();
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
			case ModelPackage.CATEGORIES__ALL_CATEGORIES:
				return getAllCategories() != null;
			case ModelPackage.CATEGORIES__CATEGORIES:
				return categories != null && !categories.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //CategoriesImpl
