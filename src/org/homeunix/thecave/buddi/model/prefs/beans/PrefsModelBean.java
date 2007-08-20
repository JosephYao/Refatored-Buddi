/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.prefs.beans;

import java.util.List;



public class PrefsModelBean {
	//Window location, size
	private WindowPlacementBean mainWindowPlacement;
	private WindowPlacementBean transactionsPlacement;
	private WindowPlacementBean scheduledPlacement;
	private WindowPlacementBean prefsPlacement;
	
	private List<PluginInfoBean> plugins;
	
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
//	private boolean showTypes; //You cannot turn this off now.
	private boolean showAutoComplete;
	private boolean showCleared;
	private boolean showReconciled;
	private boolean showPromptAtStartup;
	private boolean showUpdateNotifications;
	private int numberOfBudgetColumns;
	
	//Network Options
	private boolean showProxySettings;
	private String proxyServer;
	//TODO get more options here
	
	//Advanced Options
	private int numberOfBackups = 10;
	private boolean sendCrashReports;
	
	
	private int MIN_BUDGET_COLUMNS = 2;
	private int MAX_BUDGET_COLUMNS = 13;
	public int getNumberOfBudgetColumns() {
		if (numberOfBudgetColumns < MIN_BUDGET_COLUMNS || numberOfBudgetColumns > MAX_BUDGET_COLUMNS)
			numberOfBudgetColumns = 4;
		
		return numberOfBudgetColumns;
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

	public WindowPlacementBean getMainWindowPlacement() {
		return mainWindowPlacement == null ? new WindowPlacementBean() : mainWindowPlacement;
	}

	public void setMainWindowPlacement(WindowPlacementBean accounts) {
		this.mainWindowPlacement = accounts;
	}

	public WindowPlacementBean getTransactionsPlacement() {
		return transactionsPlacement == null ? new WindowPlacementBean() : transactionsPlacement;
	}

	public void setTransactionsPlacement(WindowPlacementBean transactions) {
		this.transactionsPlacement = transactions;
	}
	
	public WindowPlacementBean getPrefsPlacement() {
		return prefsPlacement == null ? new WindowPlacementBean() : prefsPlacement;
	}

	public void setPrefsPlacement(WindowPlacementBean prefsPlacement) {
		this.prefsPlacement = prefsPlacement;
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

	public List<PluginInfoBean> getPlugins() {
		return plugins;
	}

	public void setPlugins(List<PluginInfoBean> plugins) {
		this.plugins = plugins;
	}

	public WindowPlacementBean getScheduledPlacement() {
		return scheduledPlacement == null ? new WindowPlacementBean() : scheduledPlacement;
	}

	public void setScheduledPlacement(WindowPlacementBean scheduledPlacement) {
		this.scheduledPlacement = scheduledPlacement;
	}
}
