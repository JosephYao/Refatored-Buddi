/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model;

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

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
 *   <li>{@link org.homeunix.drummer.model.Schedule#getSampleTransaction <em>Sample Transaction</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.model.ModelPackage#getSchedule()
 * @model
 * @generated
 */
public interface Schedule extends EObject {
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
	 * @see #setFrequencyType(int)
	 * @see org.homeunix.drummer.model.ModelPackage#getSchedule_FrequencyType()
	 * @model required="true"
	 * @generated
	 */
	int getFrequencyType();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Schedule#getFrequencyType <em>Frequency Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Frequency Type</em>' attribute.
	 * @see #getFrequencyType()
	 * @generated
	 */
	void setFrequencyType(int value);

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
	 * Returns the value of the '<em><b>Sample Transaction</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sample Transaction</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sample Transaction</em>' containment reference.
	 * @see #setSampleTransaction(Transaction)
	 * @see org.homeunix.drummer.model.ModelPackage#getSchedule_SampleTransaction()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Transaction getSampleTransaction();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Schedule#getSampleTransaction <em>Sample Transaction</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sample Transaction</em>' containment reference.
	 * @see #getSampleTransaction()
	 * @generated
	 */
	void setSampleTransaction(Transaction value);

} // Schedule