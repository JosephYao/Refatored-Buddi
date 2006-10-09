/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.homeunix.drummer.prefs.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PrefsFactoryImpl extends EFactoryImpl implements PrefsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PrefsFactory init() {
		try {
			PrefsFactory thePrefsFactory = (PrefsFactory)EPackage.Registry.INSTANCE.getEFactory("urn:org.homeunix.drummer.prefs.ecore"); 
			if (thePrefsFactory != null) {
				return thePrefsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PrefsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrefsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case PrefsPackage.DICT_DATA: return createDictData();
			case PrefsPackage.DICT_ENTRY: return createDictEntry();
			case PrefsPackage.INTERVAL: return createInterval();
			case PrefsPackage.INTERVALS: return createIntervals();
			case PrefsPackage.LIST_ATTRIBUTES: return createListAttributes();
			case PrefsPackage.LIST_ENTRY: return createListEntry();
			case PrefsPackage.LISTS: return createLists();
			case PrefsPackage.PLUGIN: return createPlugin();
			case PrefsPackage.PREFS: return createPrefs();
			case PrefsPackage.USER_PREFS: return createUserPrefs();
			case PrefsPackage.VERSION: return createVersion();
			case PrefsPackage.WINDOW_ATTRIBUTES: return createWindowAttributes();
			case PrefsPackage.WINDOWS: return createWindows();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DictData createDictData() {
		DictDataImpl dictData = new DictDataImpl();
		return dictData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DictEntry createDictEntry() {
		DictEntryImpl dictEntry = new DictEntryImpl();
		return dictEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interval createInterval() {
		IntervalImpl interval = new IntervalImpl();
		return interval;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Intervals createIntervals() {
		IntervalsImpl intervals = new IntervalsImpl();
		return intervals;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ListAttributes createListAttributes() {
		ListAttributesImpl listAttributes = new ListAttributesImpl();
		return listAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ListEntry createListEntry() {
		ListEntryImpl listEntry = new ListEntryImpl();
		return listEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Lists createLists() {
		ListsImpl lists = new ListsImpl();
		return lists;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Plugin createPlugin() {
		PluginImpl plugin = new PluginImpl();
		return plugin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Prefs createPrefs() {
		PrefsImpl prefs = new PrefsImpl();
		return prefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserPrefs createUserPrefs() {
		UserPrefsImpl userPrefs = new UserPrefsImpl();
		return userPrefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Version createVersion() {
		VersionImpl version = new VersionImpl();
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WindowAttributes createWindowAttributes() {
		WindowAttributesImpl windowAttributes = new WindowAttributesImpl();
		return windowAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Windows createWindows() {
		WindowsImpl windows = new WindowsImpl();
		return windows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrefsPackage getPrefsPackage() {
		return (PrefsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static PrefsPackage getPackage() {
		return PrefsPackage.eINSTANCE;
	}

} //PrefsFactoryImpl
