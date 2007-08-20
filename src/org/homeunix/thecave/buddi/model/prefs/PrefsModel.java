/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.prefs;

import java.awt.Dimension;
import java.awt.Point;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiTranslator;
import org.homeunix.thecave.buddi.model.prefs.beans.PluginInfoBean;
import org.homeunix.thecave.buddi.model.prefs.beans.PrefsModelBean;
import org.homeunix.thecave.moss.data.list.WrapperList;
import org.homeunix.thecave.moss.util.FileFunctions;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;
import org.homeunix.thecave.moss.util.Version;

public class PrefsModel {
	//Singleton Instance
	public static PrefsModel getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static PrefsModel instance = new PrefsModel();		
	}

	private PrefsModelBean prefsModel;
	private BuddiTranslator translate;
	
	//If you want to load from a custom location, set this static variable 
	// before you call the singleton getInstance() method. 
	private static File prefsFile = OperatingSystemUtil.getPreferencesFile("Buddi", "Buddi3_Prefs.xml");

	private PrefsModel() {
		try {
			XMLDecoder prefsDecoder = new XMLDecoder(new FileInputStream(prefsFile));
			prefsModel = (PrefsModelBean) prefsDecoder.readObject();
		}
		catch (RuntimeException re){
			newPrefsFile();
		}
		catch (Exception e){
			newPrefsFile();
		}
	}
	
	private void newPrefsFile(){
		//There was a problem loading the file; create a new one.
		prefsModel = new PrefsModelBean();
		prefsModel.setCurrencySign("$");
		prefsModel.setLanguage("English"); //TODO Prompt for language
		prefsModel.setNumberOfBackups(10);
		prefsModel.setShowAutoComplete(true);
		prefsModel.setShowDeleted(true);
		prefsModel.setSendCrashReports(true);

		//Save the file
		save();
	}

	public void save() {
		if (prefsFile != null){
			try {
				if (!prefsFile.getParentFile().exists())
					prefsFile.getParentFile().mkdirs();
				XMLEncoder encoder = new XMLEncoder(new FileOutputStream(prefsFile));
				encoder.writeObject(prefsModel);
				encoder.flush();
				encoder.close();
			}
			catch (FileNotFoundException fnfe){
				Log.error("Problem saving preferences file: ", fnfe);
			}
		}
	}
	
	/**
	 * Streams the current Preferences object as an XML encoded string.  This is primarily meant
	 * for troubleshooting crashes. 
	 * @return
	 */
	public String saveToString(){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(baos);
		encoder.writeObject(prefsModel);
		encoder.flush();
		encoder.close();
		
		return baos.toString();
	}

	public BuddiTranslator getTranslator(){
		if (translate == null)
			translate = new BuddiTranslator();
		return translate;
	}
	
	public Version getLastVersion(){
		if (prefsModel.getLastVersion() == null)
			return null;
		return new Version(prefsModel.getLastVersion());
	}
	
	public void updateVersion(){
		prefsModel.setLastVersion(Const.VERSION.toString());
	}

	public String getCurrencySign() {
		return prefsModel.getCurrencySign();
	}

	public void setCurrencySign(String currencySign) {
		if (currencySign != null && currencySign.length() > 0)
			prefsModel.setCurrencySign(currencySign);
	}

	public String getDateFormat() {
		if (prefsModel.getDateFormat() == null || prefsModel.getDateFormat().length() == 0) 
			prefsModel.setDateFormat("yyyy/MM/dd");
		return prefsModel.getDateFormat();
	}

	public void setDateFormat(String dateFormat) {
		if (dateFormat != null && dateFormat.length() > 0)
			prefsModel.setDateFormat(dateFormat);
	}

	public String getLanguage() {
		return (prefsModel.getLanguage() != null && prefsModel.getLanguage().length() > 0
				? prefsModel.getLanguage()
						: "English");
	}

	public void setLanguage(String language) {
		if (language != null && language.length() > 0)
			prefsModel.setLanguage(language);
	}

	public File getLastDataFile() {
		if (prefsModel.getLastDataFile() == null)
			return null;
		return new File(prefsModel.getLastDataFile());
	}

	public void setLastOpenedDataFile(File lastOpenedDataFile) {
		prefsModel.setLastDataFile(lastOpenedDataFile.getAbsolutePath());
	}

	public Dimension getMainWindowSize() {
		return (prefsModel.getMainWindowPlacement().getSize() != null
				? prefsModel.getMainWindowPlacement().getSize() 
						: new Dimension(500, 300));
	}

//	public Dimension getBudgetWindowSize() {
//		return (prefsModel.getBudgetPlacement().getSize() != null
//				? prefsModel.getBudgetPlacement().getSize() 
//						: new Dimension(900, 500));
//	}
//
	public Dimension getTransactionWindowSize() {
		return (prefsModel.getTransactionsPlacement().getSize() != null
				? prefsModel.getTransactionsPlacement().getSize() 
						: new Dimension(600, 400));
	}

	public Dimension getScheduledTransactionWindowSize() {
		return (prefsModel.getScheduledPlacement().getSize() != null
				? prefsModel.getScheduledPlacement().getSize() 
						: new Dimension(600, 400));
	}
	
//	public Dimension getReportWindowSize() {
//		return (prefsModel.getReportsPlacement().getSize() != null
//				? prefsModel.getReportsPlacement().getSize() 
//						: new Dimension(800, 400));
//	}

	public Dimension getPreferencesWindowSize() {
		return (prefsModel.getPrefsPlacement().getSize() != null
				? prefsModel.getPrefsPlacement().getSize() 
						: new Dimension(400, 300));
	}

	public Point getMainWindowLocation() {
		return (prefsModel.getMainWindowPlacement().getLocation() != null
				? prefsModel.getMainWindowPlacement().getLocation() 
						: new Point(100, 100));
	}

//	public Point getBudgetWindowLocation() {
//		return (prefsModel.getBudgetPlacement().getLocation() != null
//				? prefsModel.getBudgetPlacement().getLocation() 
//						: new Point(100, 100));
//	}

	public Point getTransactionWindowLocation() {
		return (prefsModel.getTransactionsPlacement().getLocation() != null
				? prefsModel.getTransactionsPlacement().getLocation() 
						: new Point(100, 100));
	}

	public Point getScheduledTransactionWindowLocation() {
		return (prefsModel.getScheduledPlacement().getLocation() != null
				? prefsModel.getScheduledPlacement().getLocation() 
						: new Point(100, 100));
	}

//	public Point getReportWindowLocation() {
//		return (prefsModel.getReportsPlacement().getLocation() != null
//				? prefsModel.getReportsPlacement().getLocation() 
//						: new Point(100, 100));
//	}
	
	public Point getPreferencesWindowLocation() {
		return (prefsModel.getPrefsPlacement().getLocation() != null
				? prefsModel.getPrefsPlacement().getLocation() 
						: new Point(100, 100));
	}

	public void setMainWindowSize(Dimension size){
		if (size != null)
			prefsModel.getMainWindowPlacement().setSize(size);
	}

//	public void setBudgetWindowSize(Dimension size){
//		if (size != null)
//			prefsModel.getBudgetPlacement().setSize(size);
//	}

	public void setTransactionWindowSize(Dimension size){
		if (size != null)
			prefsModel.getTransactionsPlacement().setSize(size);
	}
	
	public void setScheduledTransactionWindowSize(Dimension size){
		if (size != null)
			prefsModel.getScheduledPlacement().setSize(size);
	}

//	public void setReportWindowSize(Dimension size){
//		if (size != null)
//			prefsModel.getReportsPlacement().setSize(size);
//	}
	
	public void setPreferencesWindowSize(Dimension size){
		if (size != null)
			prefsModel.getPrefsPlacement().setSize(size);
	}


	public void setMainWindowLocation(Point location){
		if (location != null)
			prefsModel.getMainWindowPlacement().setLocation(location);
	}

//	public void setBudgetWindowLocation(Point location){
//		if (location != null)
//			prefsModel.getBudgetPlacement().setLocation(location);
//	}

	public void setTransactionWindowLocation(Point location){
		if (location != null)
			prefsModel.getTransactionsPlacement().setLocation(location);
	}

	public void setScheduledTransactionWindowLocation(Point location){
		if (location != null)
			prefsModel.getScheduledPlacement().setLocation(location);
	}

//	public void setReportWindowLocation(Point location){
//		if (location != null)
//			prefsModel.getReportsPlacement().setLocation(location);
//	}
	
	public void setPreferencesWindowLocation(Point location){
		if (location != null)
			prefsModel.getPrefsPlacement().setLocation(location);
	}

	public int getNumberOfBackups() {
		return prefsModel.getNumberOfBackups();
	}

	public void setNumberOfBackups(int numberOfBackups) {
		prefsModel.setNumberOfBackups(numberOfBackups);
	}

	public String getProxyServer() {
		return (prefsModel.getProxyServer() != null ? prefsModel.getProxyServer() : "");
	}

	public void setProxyServer(String proxyServer) {
		if (proxyServer != null)
			prefsModel.setProxyServer(proxyServer);
	}

	public boolean isShowAutoComplete() {
		return prefsModel.isShowAutoComplete();
	}

	public void setShowAutoComplete(boolean showAutoComplete) {
		prefsModel.setShowAutoComplete(showAutoComplete);
	}

	public boolean isShowCleared() {
		return prefsModel.isShowCleared();
	}

	public void setShowCleared(boolean showCleared) {
		prefsModel.setShowCleared(showCleared);
	}

	public boolean isShowCurrencyAfterAmount() {
		return prefsModel.isShowCurrencyAfterAmount();
	}

	public void setShowCurrencyAfterAmount(boolean showCurrencyAfterAmount) {
		prefsModel.setShowCurrencyAfterAmount(showCurrencyAfterAmount);
	}

	public boolean isShowDeleted() {
		return prefsModel.isShowDeleted();
	}

	public void setShowDeleted(boolean showDeleted) {
		prefsModel.setShowDeleted(showDeleted);
	}

	public boolean isShowPromptAtStartup() {
		return prefsModel.isShowPromptAtStartup();
	}

	public void setShowPromptAtStartup(boolean showPromptAtStartup) {
		prefsModel.setShowPromptAtStartup(showPromptAtStartup);
	}

	public boolean isShowProxySettings() {
		return prefsModel.isShowProxySettings();
	}

	public void setShowProxySettings(boolean showProxySettings) {
		prefsModel.setShowProxySettings(showProxySettings);
	}

	public boolean isShowReconciled() {
		return prefsModel.isShowReconciled();
	}

	public void setShowReconciled(boolean showReconciled) {
		prefsModel.setShowReconciled(showReconciled);
	}

	public boolean isShowUpdateNotifications() {
		return prefsModel.isShowUpdateNotifications();
	}

	public void setShowUpdateNotifications(boolean showUpdateNotifications) {
		prefsModel.setShowUpdateNotifications(showUpdateNotifications);
	}

	public int getNumberOfBudgetColumns() {
		return prefsModel.getNumberOfBudgetColumns();
	}

	public void setNumberOfBudgetColumns(int numberOfBudgetColumns) {
		prefsModel.setNumberOfBudgetColumns(numberOfBudgetColumns);
	}

	public static void setPrefsFile(File file){
		if (file != null && FileFunctions.isFolderWritable(file)){
			PrefsModel.prefsFile = file;
		}
	}
	
	public boolean isSendCrashReports() {
		return prefsModel.isSendCrashReports();
	}

	public void setSendCrashReports(boolean sendCrashReports) {
		prefsModel.setSendCrashReports(sendCrashReports);
	}
	
	public void addPluginInfo(PluginInfo info){
		prefsModel.getPlugins().add(info.getBean());
	}
	
	public void removePluginInfo(PluginInfo info){
		prefsModel.getPlugins().remove(info.getBean());
	}
	
	public void clearPluginInfo(){
		prefsModel.getPlugins().clear();
	}
	
	public List<PluginInfo> getPluginInfo(){
		if (prefsModel.getPlugins() == null)
			prefsModel.setPlugins(new LinkedList<PluginInfoBean>());
		return new PluginInfoWrapperList(prefsModel.getPlugins());
	}
	
	private class PluginInfoWrapperList extends WrapperList<PluginInfo, PluginInfoBean> {
		public PluginInfoWrapperList(List<PluginInfoBean> beans) {
			super(beans, false);
		}
		
		@Override
		public PluginInfoBean getWrappedObject(PluginInfo object) {
			return object.getBean();
		}
		
		@Override
		public PluginInfo getWrapperObject(PluginInfoBean object) {
			return new PluginInfo(object);
		}
		
		
	}
}
