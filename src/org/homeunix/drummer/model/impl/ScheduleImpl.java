/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model.impl;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.homeunix.drummer.model.ModelPackage;
import org.homeunix.drummer.model.Schedule;

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
 *   <li>{@link org.homeunix.drummer.model.impl.ScheduleImpl#getScheduleName <em>Schedule Name</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.ScheduleImpl#getScheduleWeek <em>Schedule Week</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.ScheduleImpl#getScheduleMonth <em>Schedule Month</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScheduleImpl extends TransactionImpl implements Schedule {
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
	protected static final String FREQUENCY_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFrequencyType() <em>Frequency Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrequencyType()
	 * @generated
	 * @ordered
	 */
	protected String frequencyType = FREQUENCY_TYPE_EDEFAULT;

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
	 * The default value of the '{@link #getScheduleName() <em>Schedule Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScheduleName()
	 * @generated
	 * @ordered
	 */
	protected static final String SCHEDULE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getScheduleName() <em>Schedule Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScheduleName()
	 * @generated
	 * @ordered
	 */
	protected String scheduleName = SCHEDULE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getScheduleWeek() <em>Schedule Week</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScheduleWeek()
	 * @generated
	 * @ordered
	 */
	protected static final int SCHEDULE_WEEK_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getScheduleWeek() <em>Schedule Week</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScheduleWeek()
	 * @generated
	 * @ordered
	 */
	protected int scheduleWeek = SCHEDULE_WEEK_EDEFAULT;

	/**
	 * The default value of the '{@link #getScheduleMonth() <em>Schedule Month</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScheduleMonth()
	 * @generated
	 * @ordered
	 */
	protected static final int SCHEDULE_MONTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getScheduleMonth() <em>Schedule Month</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScheduleMonth()
	 * @generated
	 * @ordered
	 */
	protected int scheduleMonth = SCHEDULE_MONTH_EDEFAULT;

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
	public String getFrequencyType() {
		return frequencyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrequencyType(String newFrequencyType) {
		String oldFrequencyType = frequencyType;
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
	public String getScheduleName() {
		return scheduleName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScheduleName(String newScheduleName) {
		String oldScheduleName = scheduleName;
		scheduleName = newScheduleName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SCHEDULE__SCHEDULE_NAME, oldScheduleName, scheduleName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getScheduleWeek() {
		return scheduleWeek;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScheduleWeek(int newScheduleWeek) {
		int oldScheduleWeek = scheduleWeek;
		scheduleWeek = newScheduleWeek;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SCHEDULE__SCHEDULE_WEEK, oldScheduleWeek, scheduleWeek));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getScheduleMonth() {
		return scheduleMonth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScheduleMonth(int newScheduleMonth) {
		int oldScheduleMonth = scheduleMonth;
		scheduleMonth = newScheduleMonth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SCHEDULE__SCHEDULE_MONTH, oldScheduleMonth, scheduleMonth));
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
				return getFrequencyType();
			case ModelPackage.SCHEDULE__SCHEDULE_DAY:
				return new Integer(getScheduleDay());
			case ModelPackage.SCHEDULE__LAST_DATE_CREATED:
				return getLastDateCreated();
			case ModelPackage.SCHEDULE__END_DATE:
				return getEndDate();
			case ModelPackage.SCHEDULE__SCHEDULE_NAME:
				return getScheduleName();
			case ModelPackage.SCHEDULE__SCHEDULE_WEEK:
				return new Integer(getScheduleWeek());
			case ModelPackage.SCHEDULE__SCHEDULE_MONTH:
				return new Integer(getScheduleMonth());
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
				setFrequencyType((String)newValue);
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
			case ModelPackage.SCHEDULE__SCHEDULE_NAME:
				setScheduleName((String)newValue);
				return;
			case ModelPackage.SCHEDULE__SCHEDULE_WEEK:
				setScheduleWeek(((Integer)newValue).intValue());
				return;
			case ModelPackage.SCHEDULE__SCHEDULE_MONTH:
				setScheduleMonth(((Integer)newValue).intValue());
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
			case ModelPackage.SCHEDULE__SCHEDULE_NAME:
				setScheduleName(SCHEDULE_NAME_EDEFAULT);
				return;
			case ModelPackage.SCHEDULE__SCHEDULE_WEEK:
				setScheduleWeek(SCHEDULE_WEEK_EDEFAULT);
				return;
			case ModelPackage.SCHEDULE__SCHEDULE_MONTH:
				setScheduleMonth(SCHEDULE_MONTH_EDEFAULT);
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
				return FREQUENCY_TYPE_EDEFAULT == null ? frequencyType != null : !FREQUENCY_TYPE_EDEFAULT.equals(frequencyType);
			case ModelPackage.SCHEDULE__SCHEDULE_DAY:
				return scheduleDay != SCHEDULE_DAY_EDEFAULT;
			case ModelPackage.SCHEDULE__LAST_DATE_CREATED:
				return LAST_DATE_CREATED_EDEFAULT == null ? lastDateCreated != null : !LAST_DATE_CREATED_EDEFAULT.equals(lastDateCreated);
			case ModelPackage.SCHEDULE__END_DATE:
				return END_DATE_EDEFAULT == null ? endDate != null : !END_DATE_EDEFAULT.equals(endDate);
			case ModelPackage.SCHEDULE__SCHEDULE_NAME:
				return SCHEDULE_NAME_EDEFAULT == null ? scheduleName != null : !SCHEDULE_NAME_EDEFAULT.equals(scheduleName);
			case ModelPackage.SCHEDULE__SCHEDULE_WEEK:
				return scheduleWeek != SCHEDULE_WEEK_EDEFAULT;
			case ModelPackage.SCHEDULE__SCHEDULE_MONTH:
				return scheduleMonth != SCHEDULE_MONTH_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer();
		result.append(this.getScheduleName());

		return result.toString();
	}

} //ScheduleImpl