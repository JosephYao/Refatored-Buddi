/*
 * Created on May 12, 2006 by wyatt
 */
package org.homeunix.drummer;


public class Const {
	public static final String VERSION = "1.7.1";
	public static final String BRANCH = "UNSTABLE";
	
	public final static String LANGUAGE_EXTENSION = ".lang";
	public final static String LANGUAGE_FOLDER = "Languages";
	
	public final static String HELP_FOLDER = "Help";
	public final static String HELP_FILE = "index.html";
	
	public final static String DATA_FILE_EXTENSION = ".buddi";
	public final static String DATA_DEFAULT_FILENAME = "Data";
	
	public final static String DONATE_URL = "http://sourceforge.net/donate/index.php?group_id=167026";
	public final static String PROJECT_URL = "http://buddi.sourceforge.net/";
	public final static String DOWNLOAD_URL = "http://prdownloads.sourceforge.net/buddi/Buddi-";
	public final static String DOWNLOAD_URL_DMG = ".dmg?download";
	public final static String DOWNLOAD_URL_ZIP = ".zip?download";
	public final static String VERSION_FILE = "version.txt";
	
	public final static String[] DATE_FORMATS = {
		"yyyy-MM-dd",
		"yyyy-MMM-dd",
		"MMMM dd, yyyy",
		"MMM dd yyyy",
		"yyyy/MMM/d",
		"yyyy/MM/dd",
		"dd.MM.yyyy",
		"dd/MM/yyyy"
	};
	
	public final static String[] CURRENCY_FORMATS = {
		"$",
		"\u00a3",  //British Pounds
		"\u20ac",  //Euro
		"\u00a5",  //Yen
		"\u20b1",  //Peso
		"\u20a3",  //French Franc
		"S\u20a3", //Swiss Franc (?)
		"Rs", //Indian Rupees
		"Kr"  //Norwegian
		
	};
	
	public final static String[] DAYS_IN_WEEK = {
		"SUNDAY",
		"MONDAY",
		"TUESDAY",
		"WEDNESDAY",
		"THURSDAY",
		"FRIDAY",
		"SATURDAY"
	};
	
	private Const(){}
}
