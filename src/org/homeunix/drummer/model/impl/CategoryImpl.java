/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model.impl;

import java.util.Collection;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.homeunix.drummer.Strings;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.ModelPackage;
import org.homeunix.drummer.model.Source;
import org.homeunix.drummer.util.Formatter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Category</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.impl.CategoryImpl#getBudgetedAmount <em>Budgeted Amount</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.CategoryImpl#isIncome <em>Income</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.CategoryImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.CategoryImpl#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CategoryImpl extends SourceImpl implements Category {
	/**
	 * The default value of the '{@link #getBudgetedAmount() <em>Budgeted Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBudgetedAmount()
	 * @generated
	 * @ordered
	 */
	protected static final long BUDGETED_AMOUNT_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getBudgetedAmount() <em>Budgeted Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBudgetedAmount()
	 * @generated
	 * @ordered
	 */
	protected long budgetedAmount = BUDGETED_AMOUNT_EDEFAULT;

	/**
	 * The default value of the '{@link #isIncome() <em>Income</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIncome()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INCOME_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIncome() <em>Income</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIncome()
	 * @generated
	 * @ordered
	 */
	protected boolean income = INCOME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected Category parent = null;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList children = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CategoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ModelPackage.eINSTANCE.getCategory();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getBudgetedAmount() {
		return budgetedAmount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBudgetedAmount(long newBudgetedAmount) {
		long oldBudgetedAmount = budgetedAmount;
		budgetedAmount = newBudgetedAmount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CATEGORY__BUDGETED_AMOUNT, oldBudgetedAmount, budgetedAmount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIncome() {
		return income;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIncome(boolean newIncome) {
		boolean oldIncome = income;
		income = newIncome;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CATEGORY__INCOME, oldIncome, income));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Category getParent() {
		if (parent != null && parent.eIsProxy()) {
			Category oldParent = parent;
			parent = (Category)eResolveProxy((InternalEObject)parent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.CATEGORY__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Category basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getChildren() {
		if (children == null) {
			children = new EObjectResolvingEList(Category.class, this, ModelPackage.CATEGORY__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setParent(Category newParent) {
		if (newParent != this){
			Category oldParent = parent;
			parent = newParent;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CATEGORY__PARENT, oldParent, parent));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ModelPackage.CATEGORY__NAME:
				return getName();
			case ModelPackage.CATEGORY__DELETED:
				return isDeleted() ? Boolean.TRUE : Boolean.FALSE;
			case ModelPackage.CATEGORY__CREATION_DATE:
				return getCreationDate();
			case ModelPackage.CATEGORY__BUDGETED_AMOUNT:
				return new Long(getBudgetedAmount());
			case ModelPackage.CATEGORY__INCOME:
				return isIncome() ? Boolean.TRUE : Boolean.FALSE;
			case ModelPackage.CATEGORY__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case ModelPackage.CATEGORY__CHILDREN:
				return getChildren();
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
			case ModelPackage.CATEGORY__NAME:
				setName((String)newValue);
				return;
			case ModelPackage.CATEGORY__DELETED:
				setDeleted(((Boolean)newValue).booleanValue());
				return;
			case ModelPackage.CATEGORY__CREATION_DATE:
				setCreationDate((Date)newValue);
				return;
			case ModelPackage.CATEGORY__BUDGETED_AMOUNT:
				setBudgetedAmount(((Long)newValue).longValue());
				return;
			case ModelPackage.CATEGORY__INCOME:
				setIncome(((Boolean)newValue).booleanValue());
				return;
			case ModelPackage.CATEGORY__PARENT:
				setParent((Category)newValue);
				return;
			case ModelPackage.CATEGORY__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection)newValue);
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
			case ModelPackage.CATEGORY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ModelPackage.CATEGORY__DELETED:
				setDeleted(DELETED_EDEFAULT);
				return;
			case ModelPackage.CATEGORY__CREATION_DATE:
				setCreationDate(CREATION_DATE_EDEFAULT);
				return;
			case ModelPackage.CATEGORY__BUDGETED_AMOUNT:
				setBudgetedAmount(BUDGETED_AMOUNT_EDEFAULT);
				return;
			case ModelPackage.CATEGORY__INCOME:
				setIncome(INCOME_EDEFAULT);
				return;
			case ModelPackage.CATEGORY__PARENT:
				setParent((Category)null);
				return;
			case ModelPackage.CATEGORY__CHILDREN:
				getChildren().clear();
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
			case ModelPackage.CATEGORY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ModelPackage.CATEGORY__DELETED:
				return deleted != DELETED_EDEFAULT;
			case ModelPackage.CATEGORY__CREATION_DATE:
				return CREATION_DATE_EDEFAULT == null ? creationDate != null : !CREATION_DATE_EDEFAULT.equals(creationDate);
			case ModelPackage.CATEGORY__BUDGETED_AMOUNT:
				return budgetedAmount != BUDGETED_AMOUNT_EDEFAULT;
			case ModelPackage.CATEGORY__INCOME:
				return income != INCOME_EDEFAULT;
			case ModelPackage.CATEGORY__PARENT:
				return parent != null;
			case ModelPackage.CATEGORY__CHILDREN:
				return children != null && !children.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String toString() {
		if (getParent() == null) {
			return Strings.inst().get(getName());
		}
		else {
			return getParent().toString() + " " + Strings.inst().get(getName());
		}
	}
	
	public String toStringLong(){
		StringBuffer sb = new StringBuffer();
		sb.append(toString());
		sb.append(" : ");
		sb.append(Strings.inst().get(Strings.CURRENCY_SIGN));
		sb.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) getBudgetedAmount() / 100.0)));
		
		return sb.toString();
	}

	@Override
	public int compareTo(Source arg0) {
		if (arg0 instanceof Category){
			Category c = (Category) arg0;
			if (this.isIncome() != c.isIncome()){
				return -1 * new Boolean(this.isIncome()).compareTo(new Boolean(c.isIncome()));
			}
		}
		return super.compareTo(arg0);
	}

} //CategoryImpl
