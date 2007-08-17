/*
 * Created on Apr 11, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.util;

import java.text.DateFormat;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Type;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.util.BuddiInternalFormatter;
import org.homeunix.thecave.moss.util.Formatter;

public class APICommonFormatter {
	public static DateFormat getDateFormat(){
		return Formatter.getDateFormat(PrefsModel.getInstance().getDateFormat());
	}
	
	/**
	 * Return the translation for the given key.
	 * @param key
	 * @return
	 */
	public static String getTranslation(String key){
		return PrefsModel.getInstance().getTranslator().get(key);
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
		return BuddiInternalFormatter.getFormattedCurrency(value, negate);
	}
	
	public static String getFormattedCurrency(long value){
		return getFormattedCurrency(value, false);
	}

	/**
	 * Returns the name, formatted as a String.  This method complies
	 * with the Buddi Formatting Standard, and should be used whenever
	 * possible.
	 * @param a
	 * @return
	 */
	public static String getFormattedNameForAccount(Account a){
		return getFormattedNameGeneric(a.toString(), a.getType().isCredit());
	}

	/**
	 * Returns the name, formatted as a String.  This method complies
	 * with the Buddi Formatting Standard, and should be used whenever
	 * possible.
	 * @param c
	 * @return
	 */
	public static String getFormattedNameForCategory(BudgetCategory c){
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
		return BuddiInternalFormatter.getFormattedNameGeneric(name, isRed);
	}
	
//	public static boolean isRed(ImmutableSource s){
//		return BuddiInternalFormatter.isRed(((ImmutableSourceImpl) s).getImpl());
//	}
//
//	public static boolean isRed(ImmutableAccount a, long value){
//		return BuddiInternalFormatter.isRed(((ImmutableAccountImpl) a).getImpl(), value);
//	}
//	
//	public static boolean isRed(ImmutableCategory c, long value){
//		return BuddiInternalFormatter.isRed(((ImmutableCategoryImpl) c).getImpl(), value);
//	}
//
//	public static boolean isRed(ImmutableType t){
//		return BuddiInternalFormatter.isRed(((ImmutableTypeImpl) t).getImpl());
//	}
//
//	public static boolean isRed(ImmutableType t, long value){
//		return BuddiInternalFormatter.isRed(((ImmutableTypeImpl) t).getImpl(), value);
//	}
//	
//	public static boolean isRed(ImmutableTransaction t){
//		return BuddiInternalFormatter.isRed(((ImmutableTransactionImpl) t).getImpl());
//	}
//	
//	public static boolean isRed(ImmutableTransaction t, boolean toSelectedAccount){
//		return BuddiInternalFormatter.isRed(((ImmutableTransactionImpl) t).getImpl(), toSelectedAccount);
//	}
//
//	public static boolean isRed(long value){
//		return BuddiInternalFormatter.isRed(value);
//	}
}
