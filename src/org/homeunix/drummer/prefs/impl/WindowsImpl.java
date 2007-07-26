/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.homeunix.drummer.prefs.PrefsPackage;
import org.homeunix.drummer.prefs.WindowAttributes;
import org.homeunix.drummer.prefs.Windows;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Windows</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.impl.WindowsImpl#getReportsWindow <em>Reports Window</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.WindowsImpl#getGraphsWindow <em>Graphs Window</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.WindowsImpl#getTransactionsWindow <em>Transactions Window</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.WindowsImpl#getMainWindow <em>Main Window</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WindowsImpl extends EObjectImpl implements Windows {
	/**
	 * The cached value of the '{@link #getReportsWindow() <em>Reports Window</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReportsWindow()
	 * @generated
	 * @ordered
	 */
	protected WindowAttributes reportsWindow = null;

	/**
	 * The cached value of the '{@link #getGraphsWindow() <em>Graphs Window</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraphsWindow()
	 * @generated
	 * @ordered
	 */
	protected WindowAttributes graphsWindow = null;

	/**
	 * The cached value of the '{@link #getTransactionsWindow() <em>Transactions Window</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransactionsWindow()
	 * @generated
	 * @ordered
	 */
	protected WindowAttributes transactionsWindow = null;

	/**
	 * The cached value of the '{@link #getMainWindow() <em>Main Window</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMainWindow()
	 * @generated
	 * @ordered
	 */
	protected WindowAttributes mainWindow = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WindowsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PrefsPackage.Literals.WINDOWS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WindowAttributes getTransactionsWindow() {
		return transactionsWindow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTransactionsWindow(WindowAttributes newTransactionsWindow, NotificationChain msgs) {
		WindowAttributes oldTransactionsWindow = transactionsWindow;
		transactionsWindow = newTransactionsWindow;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PrefsPackage.WINDOWS__TRANSACTIONS_WINDOW, oldTransactionsWindow, newTransactionsWindow);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransactionsWindow(WindowAttributes newTransactionsWindow) {
		if (newTransactionsWindow != transactionsWindow) {
			NotificationChain msgs = null;
			if (transactionsWindow != null)
				msgs = ((InternalEObject)transactionsWindow).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.WINDOWS__TRANSACTIONS_WINDOW, null, msgs);
			if (newTransactionsWindow != null)
				msgs = ((InternalEObject)newTransactionsWindow).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.WINDOWS__TRANSACTIONS_WINDOW, null, msgs);
			msgs = basicSetTransactionsWindow(newTransactionsWindow, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.WINDOWS__TRANSACTIONS_WINDOW, newTransactionsWindow, newTransactionsWindow));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WindowAttributes getReportsWindow() {
		return reportsWindow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReportsWindow(WindowAttributes newReportsWindow, NotificationChain msgs) {
		WindowAttributes oldReportsWindow = reportsWindow;
		reportsWindow = newReportsWindow;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PrefsPackage.WINDOWS__REPORTS_WINDOW, oldReportsWindow, newReportsWindow);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReportsWindow(WindowAttributes newReportsWindow) {
		if (newReportsWindow != reportsWindow) {
			NotificationChain msgs = null;
			if (reportsWindow != null)
				msgs = ((InternalEObject)reportsWindow).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.WINDOWS__REPORTS_WINDOW, null, msgs);
			if (newReportsWindow != null)
				msgs = ((InternalEObject)newReportsWindow).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.WINDOWS__REPORTS_WINDOW, null, msgs);
			msgs = basicSetReportsWindow(newReportsWindow, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.WINDOWS__REPORTS_WINDOW, newReportsWindow, newReportsWindow));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WindowAttributes getGraphsWindow() {
		return graphsWindow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGraphsWindow(WindowAttributes newGraphsWindow, NotificationChain msgs) {
		WindowAttributes oldGraphsWindow = graphsWindow;
		graphsWindow = newGraphsWindow;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PrefsPackage.WINDOWS__GRAPHS_WINDOW, oldGraphsWindow, newGraphsWindow);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGraphsWindow(WindowAttributes newGraphsWindow) {
		if (newGraphsWindow != graphsWindow) {
			NotificationChain msgs = null;
			if (graphsWindow != null)
				msgs = ((InternalEObject)graphsWindow).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.WINDOWS__GRAPHS_WINDOW, null, msgs);
			if (newGraphsWindow != null)
				msgs = ((InternalEObject)newGraphsWindow).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.WINDOWS__GRAPHS_WINDOW, null, msgs);
			msgs = basicSetGraphsWindow(newGraphsWindow, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.WINDOWS__GRAPHS_WINDOW, newGraphsWindow, newGraphsWindow));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WindowAttributes getMainWindow() {
		return mainWindow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMainWindow(WindowAttributes newMainWindow, NotificationChain msgs) {
		WindowAttributes oldMainWindow = mainWindow;
		mainWindow = newMainWindow;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PrefsPackage.WINDOWS__MAIN_WINDOW, oldMainWindow, newMainWindow);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMainWindow(WindowAttributes newMainWindow) {
		if (newMainWindow != mainWindow) {
			NotificationChain msgs = null;
			if (mainWindow != null)
				msgs = ((InternalEObject)mainWindow).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.WINDOWS__MAIN_WINDOW, null, msgs);
			if (newMainWindow != null)
				msgs = ((InternalEObject)newMainWindow).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.WINDOWS__MAIN_WINDOW, null, msgs);
			msgs = basicSetMainWindow(newMainWindow, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.WINDOWS__MAIN_WINDOW, newMainWindow, newMainWindow));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PrefsPackage.WINDOWS__REPORTS_WINDOW:
				return basicSetReportsWindow(null, msgs);
			case PrefsPackage.WINDOWS__GRAPHS_WINDOW:
				return basicSetGraphsWindow(null, msgs);
			case PrefsPackage.WINDOWS__TRANSACTIONS_WINDOW:
				return basicSetTransactionsWindow(null, msgs);
			case PrefsPackage.WINDOWS__MAIN_WINDOW:
				return basicSetMainWindow(null, msgs);
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
			case PrefsPackage.WINDOWS__REPORTS_WINDOW:
				return getReportsWindow();
			case PrefsPackage.WINDOWS__GRAPHS_WINDOW:
				return getGraphsWindow();
			case PrefsPackage.WINDOWS__TRANSACTIONS_WINDOW:
				return getTransactionsWindow();
			case PrefsPackage.WINDOWS__MAIN_WINDOW:
				return getMainWindow();
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
			case PrefsPackage.WINDOWS__REPORTS_WINDOW:
				setReportsWindow((WindowAttributes)newValue);
				return;
			case PrefsPackage.WINDOWS__GRAPHS_WINDOW:
				setGraphsWindow((WindowAttributes)newValue);
				return;
			case PrefsPackage.WINDOWS__TRANSACTIONS_WINDOW:
				setTransactionsWindow((WindowAttributes)newValue);
				return;
			case PrefsPackage.WINDOWS__MAIN_WINDOW:
				setMainWindow((WindowAttributes)newValue);
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
			case PrefsPackage.WINDOWS__REPORTS_WINDOW:
				setReportsWindow((WindowAttributes)null);
				return;
			case PrefsPackage.WINDOWS__GRAPHS_WINDOW:
				setGraphsWindow((WindowAttributes)null);
				return;
			case PrefsPackage.WINDOWS__TRANSACTIONS_WINDOW:
				setTransactionsWindow((WindowAttributes)null);
				return;
			case PrefsPackage.WINDOWS__MAIN_WINDOW:
				setMainWindow((WindowAttributes)null);
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
			case PrefsPackage.WINDOWS__REPORTS_WINDOW:
				return reportsWindow != null;
			case PrefsPackage.WINDOWS__GRAPHS_WINDOW:
				return graphsWindow != null;
			case PrefsPackage.WINDOWS__TRANSACTIONS_WINDOW:
				return transactionsWindow != null;
			case PrefsPackage.WINDOWS__MAIN_WINDOW:
				return mainWindow != null;
		}
		return super.eIsSet(featureID);
	}

} //WindowsImpl