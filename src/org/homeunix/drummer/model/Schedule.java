/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model;

import java.util.Date;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Schedule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.Schedule#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Schedule#getFrequencyType <em>Frequency Type</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Schedule#getScheduleDay <em>Schedule Day</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Schedule#getLastDateCreated <em>Last Date Created</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Schedule#getEndDate <em>End Date</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Schedule#getScheduleName <em>Schedule Name</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Schedule#getScheduleWeek <em>Schedule Week</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Schedule#getScheduleMonth <em>Schedule Month</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.model.ModelPackage#getSchedule()
 * @model
 * @generated
 */
public interface Schedule extends Transaction {
	/**
	 * Returns the value of the '<em><b>Start Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Date</em>' attribute.
	 * @see #setStartDate(Date)
	 * @see org.homeunix.drummer.model.ModelPackage#getSchedule_StartDate()
	 * @model dataType="org.homeunix.drummer.model.Date" required="true"
	 * @generated
	 */
	Date getStartDate();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Schedule#getStartDate <em>Start Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Date</em>' attribute.
	 * @see #getStartDate()
	 * @generated
	 */
	void setStartDate(Date value);

	/**
	 * Returns the value of the '<em><b>Frequency Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Frequency Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Frequency Type</em>' attribute.
	 * @see #setFrequencyType(String)
	 * @see org.homeunix.drummer.model.ModelPackage#getSchedule_FrequencyType()
	 * @model required="true"
	 * @generated
	 */
	String getFrequencyType();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Schedule#getFrequencyType <em>Frequency Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Frequency Type</em>' attribute.
	 * @see #getFrequencyType()
	 * @generated
	 */
	void setFrequencyType(String value);

	/**
	 * Returns the value of the '<em><b>Schedule Day</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schedule Day</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schedule Day</em>' attribute.
	 * @see #setScheduleDay(int)
	 * @see org.homeunix.drummer.model.ModelPackage#getSchedule_ScheduleDay()
	 * @model required="true"
	 * @generated
	 */
	int getScheduleDay();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Schedule#getScheduleDay <em>Schedule Day</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schedule Day</em>' attribute.
	 * @see #getScheduleDay()
	 * @generated
	 */
	void setScheduleDay(int value);

	/**
	 * Returns the value of the '<em><b>Last Date Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Date Created</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Date Created</em>' attribute.
	 * @see #setLastDateCreated(Date)
	 * @see org.homeunix.drummer.model.ModelPackage#getSchedule_LastDateCreated()
	 * @model dataType="org.homeunix.drummer.model.Date" required="true"
	 * @generated
	 */
	Date getLastDateCreated();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Schedule#getLastDateCreated <em>Last Date Created</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Date Created</em>' attribute.
	 * @see #getLastDateCreated()
	 * @generated
	 */
	void setLastDateCreated(Date value);

	/**
	 * Returns the value of the '<em><b>End Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Date</em>' attribute.
	 * @see #setEndDate(Date)
	 * @see org.homeunix.drummer.model.ModelPackage#getSchedule_EndDate()
	 * @model dataType="org.homeunix.drummer.model.Date" required="true"
	 * @generated
	 */
	Date getEndDate();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Schedule#getEndDate <em>End Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Date</em>' attribute.
	 * @see #getEndDate()
	 * @generated
	 */
	void setEndDate(Date value);

	/**
	 * Returns the value of the '<em><b>Schedule Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schedule Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schedule Name</em>' attribute.
	 * @see #setScheduleName(String)
	 * @see org.homeunix.drummer.model.ModelPackage#getSchedule_ScheduleName()
	 * @model required="true"
	 * @generated
	 */
	String getScheduleName();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Schedule#getScheduleName <em>Schedule Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schedule Name</em>' attribute.
	 * @see #getScheduleName()
	 * @generated
	 */
	void setScheduleName(String value);

	/**
	 * Returns the value of the '<em><b>Schedule Week</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schedule Week</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schedule Week</em>' attribute.
	 * @see #setScheduleWeek(int)
	 * @see org.homeunix.drummer.model.ModelPackage#getSchedule_ScheduleWeek()
	 * @model required="true"
	 * @generated
	 */
	int getScheduleWeek();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Schedule#getScheduleWeek <em>Schedule Week</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schedule Week</em>' attribute.
	 * @see #getScheduleWeek()
	 * @generated
	 */
	void setScheduleWeek(int value);

	/**
	 * Returns the value of the '<em><b>Schedule Month</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schedule Month</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schedule Month</em>' attribute.
	 * @see #setScheduleMonth(int)
	 * @see org.homeunix.drummer.model.ModelPackage#getSchedule_ScheduleMonth()
	 * @model required="true"
	 * @generated
	 */
	int getScheduleMonth();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Schedule#getScheduleMonth <em>Schedule Month</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schedule Month</em>' attribute.
	 * @see #getScheduleMonth()
	 * @generated
	 */
	void setScheduleMonth(int value);

} // Schedule