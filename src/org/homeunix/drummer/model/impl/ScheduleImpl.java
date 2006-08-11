/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model.impl;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.homeunix.drummer.model.ModelPackage;
import org.homeunix.drummer.model.Schedule;
import org.homeunix.drummer.model.Transaction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Schedule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.impl.ScheduleImpl#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.ScheduleImpl#getFrequencyType <em>Frequency Type</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.ScheduleImpl#getScheduleDay <em>Schedule Day</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.ScheduleImpl#getLastDateCreated <em>Last Date Created</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.ScheduleImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.ScheduleImpl#getSampleTransaction <em>Sample Transaction</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScheduleImpl extends EObjectImpl implements Schedule {
	/**
	 * The default value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date START_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartDate()
	 * @generated
	 * @ordered
	 */
	protected Date startDate = START_DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getFrequencyType() <em>Frequency Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrequencyType()
	 * @generated
	 * @ordered
	 */
	protected static final int FREQUENCY_TYPE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getFrequencyType() <em>Frequency Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrequencyType()
	 * @generated
	 * @ordered
	 */
	protected int frequencyType = FREQUENCY_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getScheduleDay() <em>Schedule Day</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScheduleDay()
	 * @generated
	 * @ordered
	 */
	protected static final int SCHEDULE_DAY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getScheduleDay() <em>Schedule Day</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScheduleDay()
	 * @generated
	 * @ordered
	 */
	protected int scheduleDay = SCHEDULE_DAY_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastDateCreated() <em>Last Date Created</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastDateCreated()
	 * @generated
	 * @ordered
	 */
	protected static final Date LAST_DATE_CREATED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastDateCreated() <em>Last Date Created</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastDateCreated()
	 * @generated
	 * @ordered
	 */
	protected Date lastDateCreated = LAST_DATE_CREATED_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date END_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndDate()
	 * @generated
	 * @ordered
	 */
	protected Date endDate = END_DATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSampleTransaction() <em>Sample Transaction</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSampleTransaction()
	 * @generated
	 * @ordered
	 */
	protected Transaction sampleTransaction = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ScheduleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SCHEDULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartDate(Date newStartDate) {
		Date oldStartDate = startDate;
		startDate = newStartDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SCHEDULE__START_DATE, oldStartDate, startDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getFrequencyType() {
		return frequencyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrequencyType(int newFrequencyType) {
		int oldFrequencyType = frequencyType;
		frequencyType = newFrequencyType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SCHEDULE__FREQUENCY_TYPE, oldFrequencyType, frequencyType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getScheduleDay() {
		return scheduleDay;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScheduleDay(int newScheduleDay) {
		int oldScheduleDay = scheduleDay;
		scheduleDay = newScheduleDay;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SCHEDULE__SCHEDULE_DAY, oldScheduleDay, scheduleDay));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getLastDateCreated() {
		return lastDateCreated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastDateCreated(Date newLastDateCreated) {
		Date oldLastDateCreated = lastDateCreated;
		lastDateCreated = newLastDateCreated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SCHEDULE__LAST_DATE_CREATED, oldLastDateCreated, lastDateCreated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndDate(Date newEndDate) {
		Date oldEndDate = endDate;
		endDate = newEndDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SCHEDULE__END_DATE, oldEndDate, endDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transaction getSampleTransaction() {
		return sampleTransaction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSampleTransaction(Transaction newSampleTransaction, NotificationChain msgs) {
		Transaction oldSampleTransaction = sampleTransaction;
		sampleTransaction = newSampleTransaction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.SCHEDULE__SAMPLE_TRANSACTION, oldSampleTransaction, newSampleTransaction);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSampleTransaction(Transaction newSampleTransaction) {
		if (newSampleTransaction != sampleTransaction) {
			NotificationChain msgs = null;
			if (sampleTransaction != null)
				msgs = ((InternalEObject)sampleTransaction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SCHEDULE__SAMPLE_TRANSACTION, null, msgs);
			if (newSampleTransaction != null)
				msgs = ((InternalEObject)newSampleTransaction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SCHEDULE__SAMPLE_TRANSACTION, null, msgs);
			msgs = basicSetSampleTransaction(newSampleTransaction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SCHEDULE__SAMPLE_TRANSACTION, newSampleTransaction, newSampleTransaction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.SCHEDULE__SAMPLE_TRANSACTION:
				return basicSetSampleTransaction(null, msgs);
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
			case ModelPackage.SCHEDULE__START_DATE:
				return getStartDate();
			case ModelPackage.SCHEDULE__FREQUENCY_TYPE:
				return new Integer(getFrequencyType());
			case ModelPackage.SCHEDULE__SCHEDULE_DAY:
				return new Integer(getScheduleDay());
			case ModelPackage.SCHEDULE__LAST_DATE_CREATED:
				return getLastDateCreated();
			case ModelPackage.SCHEDULE__END_DATE:
				return getEndDate();
			case ModelPackage.SCHEDULE__SAMPLE_TRANSACTION:
				return getSampleTransaction();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.SCHEDULE__START_DATE:
				setStartDate((Date)newValue);
				return;
			case ModelPackage.SCHEDULE__FREQUENCY_TYPE:
				setFrequencyType(((Integer)newValue).intValue());
				return;
			case ModelPackage.SCHEDULE__SCHEDULE_DAY:
				setScheduleDay(((Integer)newValue).intValue());
				return;
			case ModelPackage.SCHEDULE__LAST_DATE_CREATED:
				setLastDateCreated((Date)newValue);
				return;
			case ModelPackage.SCHEDULE__END_DATE:
				setEndDate((Date)newValue);
				return;
			case ModelPackage.SCHEDULE__SAMPLE_TRANSACTION:
				setSampleTransaction((Transaction)newValue);
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
			case ModelPackage.SCHEDULE__START_DATE:
				setStartDate(START_DATE_EDEFAULT);
				return;
			case ModelPackage.SCHEDULE__FREQUENCY_TYPE:
				setFrequencyType(FREQUENCY_TYPE_EDEFAULT);
				return;
			case ModelPackage.SCHEDULE__SCHEDULE_DAY:
				setScheduleDay(SCHEDULE_DAY_EDEFAULT);
				return;
			case ModelPackage.SCHEDULE__LAST_DATE_CREATED:
				setLastDateCreated(LAST_DATE_CREATED_EDEFAULT);
				return;
			case ModelPackage.SCHEDULE__END_DATE:
				setEndDate(END_DATE_EDEFAULT);
				return;
			case ModelPackage.SCHEDULE__SAMPLE_TRANSACTION:
				setSampleTransaction((Transaction)null);
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
			case ModelPackage.SCHEDULE__START_DATE:
				return START_DATE_EDEFAULT == null ? startDate != null : !START_DATE_EDEFAULT.equals(startDate);
			case ModelPackage.SCHEDULE__FREQUENCY_TYPE:
				return frequencyType != FREQUENCY_TYPE_EDEFAULT;
			case ModelPackage.SCHEDULE__SCHEDULE_DAY:
				return scheduleDay != SCHEDULE_DAY_EDEFAULT;
			case ModelPackage.SCHEDULE__LAST_DATE_CREATED:
				return LAST_DATE_CREATED_EDEFAULT == null ? lastDateCreated != null : !LAST_DATE_CREATED_EDEFAULT.equals(lastDateCreated);
			case ModelPackage.SCHEDULE__END_DATE:
				return END_DATE_EDEFAULT == null ? endDate != null : !END_DATE_EDEFAULT.equals(endDate);
			case ModelPackage.SCHEDULE__SAMPLE_TRANSACTION:
				return sampleTransaction != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (startDate: ");
		result.append(startDate);
		result.append(", frequencyType: ");
		result.append(frequencyType);
		result.append(", scheduleDay: ");
		result.append(scheduleDay);
		result.append(", lastDateCreated: ");
		result.append(lastDateCreated);
		result.append(", endDate: ");
		result.append(endDate);
		result.append(')');
		return result.toString();
	}

} //ScheduleImpl