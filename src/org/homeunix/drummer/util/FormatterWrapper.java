/*
 * Created on Apr 11, 2007 by wyatt
 */
package org.homeunix.drummer.util;

import java.text.DateFormat;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
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
	 * Technically, this is not a translation method, so it could be argued 
	 * that it should not be here.  I put it here to avoid the creation of
	 * another class to do just one function.
	 * @param value The currency amount, in cents (as per Buddi's internal 
	 * representation of currency).  For instance, to represent the value
	 * $123.45, you would pass in 12345.
	 * @param red Wrap the return string in HTML font tags to make it red.
	 * @param negate Multiply the value by *1 before rendering.  
	 * @return A string with proper decimal places, plus the user's defined 
	 * currency symbol in the correct position (whether before or after the
	 * amount).  Optionally it will be wrapped in red font tags.
	 */
	public static String getFormattedCurrencyGeneric(long value, boolean red, boolean negate){
		if (negate)
			value *= -1;

		boolean symbolAfterAmount = PrefsInstance.getInstance().getPrefs().isCurrencySymbolAfterAmount();
		String symbol = PrefsInstance.getInstance().getPrefs().getCurrencySymbol();

		String formatted = 
			(red ? "<font color='red'>" : "")
			+ (symbolAfterAmount ? "" : symbol)
			+ Formatter.getDecimalFormat().format((double) value / 100.0)  
			+ (symbolAfterAmount ? symbol : "")
			+ (red ? "</font>" : "");

		return formatted;
	}
	
	public static String getFormattedCurrencySimple(long value, boolean negate){
		if (negate)
			value *= -1;
		
		return getFormattedCurrencyGeneric(value, value < 0, false);
	}

	public static String getFormattedCurrencyForAccount(long value, boolean isCredit){
		if (isCredit)
			return getFormattedCurrencyGeneric(value, value <= 0, true);
		else
			return getFormattedCurrencyGeneric(value, value < 0, false);
	}

	public static String getFormattedCurrencyForCategory(long value, boolean isIncome){
		if (isIncome)
			return getFormattedCurrencyGeneric(value, value < 0, false);
		else
			return getFormattedCurrencyGeneric(value, value >= 0, false);
	}

	public static String getFormattedCurrencyForTransaction(long value, boolean toSelectedAccount){
		if (!toSelectedAccount && value >= 0
				|| toSelectedAccount && value < 0)
			return getFormattedCurrencyGeneric(value, true, false);
		else
			return getFormattedCurrencyGeneric(value, false, false);
	}

	public static String getFormattedNameForAccount(Account a){
		return getFormattedNameGeneric(a.toString(), a.isCredit());
	}

	public static String getFormattedNameForCategory(Category c){
		return getFormattedNameGeneric(c.toString(), !c.isIncome());
	}

	public static String getFormattedNameForType(Type t){
		return getFormattedNameGeneric(t.toString(), t.isCredit());
	}

	public static String getFormattedNameGeneric(String name, boolean isRed){
		String formatted = 
			(isRed ? "<font color='red'>" : "")
			+ Translate.getInstance().get(name)  
			+ (isRed ? "</font>" : "");

		return formatted;
	}


}
