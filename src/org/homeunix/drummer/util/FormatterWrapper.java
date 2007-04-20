/*
 * Created on Apr 11, 2007 by wyatt
 */
package org.homeunix.drummer.util;

import java.text.DateFormat;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Source;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.model.Type;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.util.Formatter;

public class FormatterWrapper {
	public static DateFormat getDateFormat(){
		return Formatter.getDateFormat(PrefsInstance.getInstance().getPrefs().getDateFormat());
	}

	/**
	 * Converts a long value (in cents: 10000 == $100.00) to a string
	 * with proper decimal values, with the user's desired currency
	 * sign in the user's specified position (whether behind or in front
	 * of the amount).  It is highly recommended that you use this method
	 * to output monetary values, as it presents the user with a constant
	 * look for currency.
	 * 
	 * @param value The currency amount, in cents (as per Buddi's internal 
	 * representation of currency).  For instance, to represent the value
	 * $123.45, you would pass in 12345.
	 * @param red Wrap the return string in HTML font tags to make it red.
	 * @param negate Multiply the value by *1 before rendering.  
	 * @return A string with proper decimal places, plus the user's defined 
	 * currency symbol in the correct position (whether before or after the
	 * amount).  Optionally it will be wrapped in red font tags.
	 */
	public static String getFormattedCurrency(long value, boolean negate){
		if (negate)
			value *= -1;

		boolean symbolAfterAmount = PrefsInstance.getInstance().getPrefs().isCurrencySymbolAfterAmount();
		String symbol = PrefsInstance.getInstance().getPrefs().getCurrencySymbol();

		String formatted = 
//			(red ? "<font color='red'>" : "")
			(symbolAfterAmount ? "" : symbol)
			+ Formatter.getDecimalFormat().format((double) value / 100.0)  
			+ (symbolAfterAmount ? " " + symbol : "");
//			+ (red ? "</font>" : "");

		return formatted;
	}
	
	public static String getFormattedCurrency(long value){
		return getFormattedCurrency(value, false);
	}
	
//	/**
//	 * Returns a formatted value as a String.  If negate is true, we multiply
//	 * by -1 first (can be useful when you want to display credit balances
//	 * as positive numbers, for instance). 
//	 * @param value
//	 * @param negate
//	 * @return
//	 */
//	public static String getFormattedCurrencySimple(long value, boolean negate){
//		if (negate)
//			value *= -1;
//		
//		return getFormattedCurrencyGeneric(value, value < 0, false);
//	}
//
//	/**
//	 * Returns a formatted value as a String.  This method complies
//	 * with the Buddi Formatting Standard, and should be used 
//	 * whenever possible.
//	 * @param value
//	 * @param isCredit
//	 * @return
//	 */
//	public static String getFormattedCurrencyForAccount(long value, boolean isCredit){
//		if (isCredit)
//			return getFormattedCurrencyGeneric(value, value <= 0, true);
//		else
//			return getFormattedCurrencyGeneric(value, value < 0, false);
//	}
//
//	/**
//	 * Returns a formatted value as a String.  This method complies
//	 * with the Buddi Formatting Standard, and should be used 
//	 * whenever possible.
//	 * @param value
//	 * @param isIncome
//	 * @return
//	 */
//	public static String getFormattedCurrencyForCategory(long value, boolean isIncome){
//		if (isIncome)
//			return getFormattedCurrencyGeneric(value, value < 0, false);
//		else
//			return getFormattedCurrencyGeneric(value, value >= 0, false);
//	}
//
//	/**
//	 * Returns a formatted value as a String.  This method complies
//	 * with the Buddi Formatting Standard, and should be used 
//	 * whenever possible.
//	 * 
//	 * This method formats the value based on the associated 
//	 * destination source: if the destination source is
//	 * an account, then we show the value as black, otherwise, red. 
//	 * @param value
//	 * @param toSelectedAccount
//	 * @return
//	 */
//	public static String getFormattedCurrencyForTransaction(long value, boolean toSelectedAccount){
//		if (!toSelectedAccount && value >= 0
//				|| toSelectedAccount && value < 0)
//			return getFormattedCurrencyGeneric(value, true, false);
//		else
//			return getFormattedCurrencyGeneric(value, false, false);
//	}
//	
//	/**
//	 * Returns a formatted values as a String.  This method complies with
//	 * the Buddi Formatting Standard, and should be used whenever possible.
//	 * 
//	 * This method formats based on the combination of amount (positive /
//	 * negative), and the type of the destination source.  If To is a Category 
//	 * and Value is positive (or zero), OR if To is an Account and Value is 
//	 * negative, then the result is Red; otherwise, the result is black. 
//	 * @param t
//	 * @return
//	 */
//	public static String getFormattedCurrencyForTransaction(Transaction t){
//		if ((t.getTo() instanceof Category && t.getAmount() >= 0)
//				|| (t.getTo() instanceof Account && t.getAmount() < 0)){
//			return getFormattedCurrencyGeneric(t.getAmount(), true, false);
//		}
//		else {
//			return getFormattedCurrencyGeneric(t.getAmount(), false, false);
//		}
//	}

	/**
	 * Returns the name, formatted as a String.  This method complies
	 * with the Buddi Formatting Standard, and should be used whenever
	 * possible.
	 * @param a
	 * @return
	 */
	public static String getFormattedNameForAccount(Account a){
		return getFormattedNameGeneric(a.toString(), a.isCredit());
	}

	/**
	 * Returns the name, formatted as a String.  This method complies
	 * with the Buddi Formatting Standard, and should be used whenever
	 * possible.
	 * @param c
	 * @return
	 */
	public static String getFormattedNameForCategory(Category c){
		return getFormattedNameGeneric(c.toString(), !c.isIncome());
	}

	/**
	 * Returns the name, formatted as a String.  This method complies
	 * with the Buddi Formatting Standard, and should be used whenever
	 * possible.
	 * @param t
	 * @return
	 */
	public static String getFormattedNameForType(Type t){
		return getFormattedNameGeneric(t.toString(), t.isCredit());
	}

	/**
	 * Returns the given string, optionally wrapped in red font tags.
	 * @param name
	 * @param isRed
	 * @return
	 */
	public static String getFormattedNameGeneric(String name, boolean isRed){
		String formatted = 
			(isRed ? "<font color='red'>" : "")
			+ Translate.getInstance().get(name)  
			+ (isRed ? "</font>" : "");

		return formatted;
	}
	
	public static boolean isRed(Source s){
		if (s instanceof Account){
			return ((Account) s).isCredit();
		}
		else if (s instanceof Category){
			return !((Category) s).isIncome();
		}
		else
			return false;
	}

	public static boolean isRed(Account a, long value){
		if (a.isCredit())
			return value <= 0;
		else
			return value < 0;
	}
	
	public static boolean isRed(Category c, long value){
		if (c.isIncome())
			return value < 0;
		else
			return value >= 0;
	}

	public static boolean isRed(Type t){
		return t.isCredit();
	}

	public static boolean isRed(Transaction t){
		if ((t.getTo() instanceof Category && t.getAmount() >= 0)
				|| (t.getTo() instanceof Account && t.getAmount() < 0))
			return true;
		else
			return false;

	}
	
	public static boolean isRed(Transaction t, boolean toSelectedAccount){
		if (!toSelectedAccount && t.getAmount() >= 0
				|| toSelectedAccount && t.getAmount() < 0)
			return true;
		else
			return false;
	}

	public static boolean isRed(long value){
		if (value < 0)
			return true;
		else
			return false;
	}
//
//	public static boolean isRed(Account a){
//		
//	}
//
//	public static boolean isRed(Account a){
//		
//	}
//
//	public static boolean isRed(Account a){
//		
//	}
//
//	public static boolean isRed(Account a){
//		
//	}

}
