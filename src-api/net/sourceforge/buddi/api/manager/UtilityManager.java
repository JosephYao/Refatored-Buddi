package net.sourceforge.buddi.api.manager;

public interface UtilityManager {
    /**
     * Returns the translation, based on the given string.
     * @param key The key to translate
     * @return The translation in currently loaded language
     */
    public String translate(String key);
    
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
     * @param negate Multiply the value by *1 before rendering.
     * 
     * @return A string with proper decimal places, plus the user's defined 
     * currency symbol in the correct position (whether before or after the
     * amount).  Optionally it will be wrapped in red font tags.
     */
    public String getFormattedCurrency(long value, boolean negate);

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
     * 
     * @return A string with proper decimal places, plus the user's defined 
     * currency symbol in the correct position (whether before or after the
     * amount).  Optionally it will be wrapped in red font tags.
     */
    public String getFormattedCurrency(long value);
}
