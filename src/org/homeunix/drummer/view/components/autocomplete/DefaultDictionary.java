//------------------------------------------------------------------------------
//Copyright (c) 1999-2001 Matt Welsh.  All Rights Reserved.
//------------------------------------------------------------------------------
package org.homeunix.drummer.view.components.autocomplete;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A default implementation of the autocomplete dictionary. This implementation
 * is based upon the TreeSet collection class to provide quick lookups and
 * default sorting.
 * 
 * @author Matt Welsh (matt@matt-welsh.com)
 */
public class DefaultDictionary extends TreeSet<String> implements AutoCompleteDictionary {
	public static final long serialVersionUID = 0;
	
	//----------------------------------------------------------------------------
	// Public methods
	//----------------------------------------------------------------------------
	
	public DefaultDictionary(){
		super(new Comparator<String>(){
			public int compare(String arg0, String arg1) {
				return arg0.toLowerCase().compareTo(arg1.toLowerCase());
			}
		});
	}
	
	/**
	 * Perform a lookup. This routine returns the closest matching string that
	 * completely contains the given string, or null if none is found.
	 *
	 * @param curr The string to use as the base for the lookup.
	 * @return curr The closest matching string that completely contains the
	 *              given string.
	 */
	public String lookup(String curr) {
		if("".equals(curr)) {
			return null;
		}
		try {
			SortedSet<String> tailSet = this.tailSet(curr);
			if(tailSet != null) {
				String firstObj = tailSet.first();
				if(firstObj != null) {
					String first = firstObj;
					if(first.startsWith(curr)) {
						return first;
					}
				}
			}
		}
		catch (Exception e) {
			return null;
		}
		return null;
	}
} 