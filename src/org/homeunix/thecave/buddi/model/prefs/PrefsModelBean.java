/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.prefs;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;



public class PrefsModelBean {
	//Window location, size
	private Dimension mainWindowSize;
	private Point mainWindowLocation;
	private Dimension transactionWindowSize;
	private Point transactionWindowLocation;
	private Point scheduledWindowLocation;
	private Point preferencesWindowLocation;
	
	//Plugin-saved information.  The plugin author will access this
	// via a string key.  The current implementation of
	// PluginPreferenceBean contains a single string; you can
	// extend this class to get more data if you wish.  Be sure
	// to create standard getters and setters for all data you wish
	// to save.
	private Map<String, String> pluginPreferences;
	
	//Data file information
	private String lastDataFile;
	
	//State
	private String lastVersion;
	
	//Locale
	private String language;
	private String dateFormat;
	private String currencySign;
	private boolean showCurrencyAfterAmount;
	
	//View Options
	private boolean showDeleted;
	private boolean showAutoComplete;
	private boolean showCleared;
	private boolean showReconciled;
	private boolean showFlatAccounts;
	private boolean showFlatBudget;
	//Currently hard coded to 4.  If we change this, we will need to change the menu item code
	// which displays the dates to copy to / from, as well as other things.
	private int numberOfBudgetColumns; 
	
	//Network Options
	private boolean showProxySettings;
	private String proxyServer;
	private int port;
	//TODO get more options here
	
	//Advanced Options
	private int numberOfBackups = 10;
	private int autosaveDelay = 30;
	private boolean sendCrashReports;
	private boolean showUpdateNotifications;
	private boolean showPromptAtStartup;
	
	
	//Define the max / min number of budget columns visible.
	private int MIN_BUDGET_COLUMNS = 2;
	private int MAX_BUDGET_COLUMNS = 13;
	
	public int getNumberOfBudgetColumns() {
		if (numberOfBudgetColumns < MIN_BUDGET_COLUMNS || numberOfBudgetColumns > MAX_BUDGET_COLUMNS)
			numberOfBudgetColumns = 4;
		
		return numberOfBudgetColumns;
	}
	
	public boolean isShowFlatAccounts() {
		System.out.println("Flat Accounts == " + showFlatAccounts);
		return showFlatAccounts;
	}
	public void setShowFlatAccounts(boolean showFlatAccounts) {
		System.out.println("Flat Accounts = " + showFlatAccounts);
		this.showFlatAccounts = showFlatAccounts;
	}
	public boolean isShowFlatBudget() {
		return showFlatBudget;
	}
	public void setShowFlatBudget(boolean showFlatBudget) {
		this.showFlatBudget = showFlatBudget;
	}

	public void setNumberOfBudgetColumns(int numberOfBudgetColumns) {
		if (numberOfBudgetColumns < MIN_BUDGET_COLUMNS)
			numberOfBudgetColumns = MIN_BUDGET_COLUMNS;
		if (numberOfBudgetColumns > MAX_BUDGET_COLUMNS)
			numberOfBudgetColumns = MAX_BUDGET_COLUMNS;
		this.numberOfBudgetColumns = numberOfBudgetColumns;
	}

	public String getCurrencySign() {
		return currencySign;
	}

	public void setCurrencySign(String currencySign) {
		this.currencySign = currencySign;
	}

	public int getAutosaveDelay() {
		return autosaveDelay;
	}

	public void setAutosaveDelay(int autosaveDelay) {
		this.autosaveDelay = autosaveDelay;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLastDataFile() {
		return lastDataFile;
	}

	public void setLastDataFile(String lastOpenedDataFile) {
		this.lastDataFile = lastOpenedDataFile;
	}

	public int getNumberOfBackups() {
		return numberOfBackups;
	}

	public void setNumberOfBackups(int numberOfBackups) {
		this.numberOfBackups = numberOfBackups;
	}

	public String getProxyServer() {
		return proxyServer;
	}

	public void setProxyServer(String proxyServer) {
		this.proxyServer = proxyServer;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isShowAutoComplete() {
		return showAutoComplete;
	}

	public void setShowAutoComplete(boolean showAutoComplete) {
		this.showAutoComplete = showAutoComplete;
	}

	public boolean isShowCleared() {
		return showCleared;
	}

	public void setShowCleared(boolean showCleared) {
		this.showCleared = showCleared;
	}

	public boolean isShowCurrencyAfterAmount() {
		return showCurrencyAfterAmount;
	}

	public void setShowCurrencyAfterAmount(boolean showCurrencyAfterAmount) {
		this.showCurrencyAfterAmount = showCurrencyAfterAmount;
	}

	public boolean isShowDeleted() {
		return showDeleted;
	}

	public void setShowDeleted(boolean showDeleted) {
		this.showDeleted = showDeleted;
	}

	public boolean isShowPromptAtStartup() {
		return showPromptAtStartup;
	}

	public void setShowPromptAtStartup(boolean showPromptAtStartup) {
		this.showPromptAtStartup = showPromptAtStartup;
	}

	public boolean isShowProxySettings() {
		return showProxySettings;
	}

	public void setShowProxySettings(boolean showProxySettings) {
		this.showProxySettings = showProxySettings;
	}

	public boolean isShowReconciled() {
		return showReconciled;
	}

	public void setShowReconciled(boolean showReconciled) {
		this.showReconciled = showReconciled;
	}

//	public boolean isShowTypes() {
//		return showTypes;
//	}
//
//	public void setShowTypes(boolean showTypes) {
//		this.showTypes = showTypes;
//	}

	public boolean isShowUpdateNotifications() {
		return showUpdateNotifications;
	}

	public void setShowUpdateNotifications(boolean showUpdateNotifications) {
		this.showUpdateNotifications = showUpdateNotifications;
	}

	public String getLastVersion() {
		return lastVersion;
	}

	public void setLastVersion(String lastVersion) {
		this.lastVersion = lastVersion;
	}

	public boolean isSendCrashReports() {
		return sendCrashReports;
	}

	public void setSendCrashReports(boolean sendCrashReports) {
		this.sendCrashReports = sendCrashReports;
	}

	public Point getMainWindowLocation() {
		return mainWindowLocation;
	}

	public void setMainWindowLocation(Point mainWindowLocation) {
		this.mainWindowLocation = mainWindowLocation;
	}

	public Dimension getMainWindowSize() {
		return mainWindowSize;
	}

	public void setMainWindowSize(Dimension mainWindowSize) {
		this.mainWindowSize = mainWindowSize;
	}

	public Map<String, String> getPluginPreferences() {
		if (pluginPreferences == null)
			pluginPreferences = new HashMap<String, String>();
		return pluginPreferences;
	}

	public void setPluginPreferences(Map<String, String> pluginPreferences) {
		this.pluginPreferences = pluginPreferences;
	}

	public Point getPreferencesWindowLocation() {
		return preferencesWindowLocation;
	}

	public void setPreferencesWindowLocation(Point prefsWindowLocation) {
		this.preferencesWindowLocation = prefsWindowLocation;
	}

	public Point getScheduledWindowLocation() {
		return scheduledWindowLocation;
	}

	public void setScheduledWindowLocation(Point scheduledWindowLocation) {
		this.scheduledWindowLocation = scheduledWindowLocation;
	}

	public Point getTransactionWindowLocation() {
		return transactionWindowLocation;
	}

	public void setTransactionWindowLocation(Point transactionWindowLocation) {
		this.transactionWindowLocation = transactionWindowLocation;
	}

	public Dimension getTransactionWindowSize() {
		return transactionWindowSize;
	}

	public void setTransactionWindowSize(Dimension transactionWindowSize) {
		this.transactionWindowSize = transactionWindowSize;
	}
}
