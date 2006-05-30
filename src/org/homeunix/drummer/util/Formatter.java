/*
 * Created on May 9, 2006 by wyatt
 */
package org.homeunix.drummer.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.homeunix.drummer.controller.PrefsInstance;


public class Formatter {

	public static Formatter getInstance() {
		return SingletonHolder.instance;
	}
	
	private static class SingletonHolder {
		private static Formatter instance = new Formatter();		
	}
	
	private final NumberFormat decimalFormat; 
	private final SimpleDateFormat dateFormat;
	private final SimpleDateFormat shortDateFormat;
	private final SimpleDateFormat filenameDateFormat;
	private final LengthFormat lengthFormat;
	
	private Formatter(){
		decimalFormat = DecimalFormat.getInstance();
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);
		
		shortDateFormat = new SimpleDateFormat("MM/dd");
		filenameDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String dateFormatString = PrefsInstance.getInstance().getPrefs().getDateFormat();
		dateFormat = new SimpleDateFormat(dateFormatString);
		
		lengthFormat = new LengthFormat();
		lengthFormat.setLength(17);
	}
	
	public NumberFormat getDecimalFormat(){
		return decimalFormat;
	}
	
	public SimpleDateFormat getDateFormat(){
		return dateFormat;
	}
	
	public LengthFormat getLengthFormat(){
		return lengthFormat;
	}
	
	public SimpleDateFormat getShortDateFormat(){
		return shortDateFormat;
	}

	public SimpleDateFormat getFilenameDateFormat(){
		return filenameDateFormat;
	}

	public class LengthFormat{
		private int length;
				
		public String format(Object o){
			StringBuffer sb = new StringBuffer();
			sb.append(o.toString());
			
			if (length < sb.toString().length()){
				sb.delete(length, sb.length());
				sb.append("...");
			}
			
			return sb.toString();
		}

		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}
	}
}
