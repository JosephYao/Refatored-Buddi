//------------------------------------------------------------------------------
//Copyright (c) 1999-2001 Matt Welsh.  All Rights Reserved.
//------------------------------------------------------------------------------
package org.homeunix.drummer.view.components.autocomplete;

import java.util.Set;

/**
 * This interface defines the API that dictionaries for autocomplete components
 * must implement. Note that implementations of this interface should perform
 * look ups as quickly as possible to avoid delays as the user types.
 *
 * @author Matt Welsh (matt@matt-welsh.com)
 */
public interface AutoCompleteDictionary extends Set<String>{
	
	/**
	 * Perform a lookup and returns the closest matching string to the passed
	 * string.
	 *
	 * @param s The string to use as the base for the lookup. How this routine
	 *          is implemented determines the behaviour of the component.
	 *          Typically, the closest matching string that completely contains
	 *          the given string is returned.
	 */
	public String lookup(String s);
}
