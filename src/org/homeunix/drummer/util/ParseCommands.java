package org.homeunix.drummer.util;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wyatt
 * This class makes Unix style command line parsing easy for Java applications.
 * Please see below for instructions on how to use it.
 * For a copy of the LGPL license, please see 
 *  http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright (C) 2005 by Wyatt Olson * * This library is free software; you can redistribute it and/or * modify it under the terms of the GNU Lesser General Public * License as published by the Free Software Foundation; either * version 2.1 of the License, or (at your option) any later version. *  * This library is distributed in the hope that it will be useful, * but WITHOUT ANY WARRANTY; without even the implied warranty of * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU * Lesser General Public License for more details. *  * You should have received a copy of the GNU Lesser General Public * License along with this library; if not, write to the Free Software * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *  
 *   TO USE:
 *   1) In calling method (probably main()), set up which variables you want to 
 *   use.  For this example, we will be setting a Boolean called 'temp'.
 *   	Boolean temp = false;
 *   
 *   2) Create a Map, and associate command line flags with the objects:
 *   	Map<String, Object> map = new HashMap<String, Object>();
 *   	map.put("-t", temp);
 *   
 *   3) Call the parse method, and set map to the return value:
 *		try{
 *			map = ParseCommands.parse(args, map, "HELP GOES HERE");
 *		}
 *		catch (ParseException pe){
 *			Log.critical(pe);
 *			System.exit(1);
 *		}
 *		
 *	4) Get the return values from the map:
 *		temp = (Boolean) map.get("-t");	
 * 
 */
public class ParseCommands {
	
	/**
	 * Parses command line arguments, and returns them as a mapping of flag (-x) to variable.  
	 * The input variables within flagVariables should be Objects (eg, Integer instead of int).
	 * Currently Boolean, Integer, String, Double , Character, and Float are supported; it is 
	 * simple to add more as required. 
	 * @param args Command line argument as given to the main method
	 * @param flagVariables Map of flags to variable which is filled by the flag.
	 * @param help The usage instructions which are shown if the command is type incorrectly.
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Object> parse(String[] args, Map<String, Object> flagVariables, String help) throws ParseException{
		HashMap<String, Object> flags = new HashMap<String, Object>();
		Set<String> inputValuesKeys = flagVariables.keySet();
		
		//If there are no arguments, print help (this may need to change, depending on the calling
		// program and it's requirements).
		if (args.length == 0){
			//System.out.println(help);
			return flags;
		}
		
		//We loop through each string, looking for the __ replacement keyword.
		for (int i = 0; i < args.length; i++) {
			args[i] = args[i].replaceAll("__", " ");
		}
		
		//We loop through each argument, looking for matches.
		for (int i = 0; i < args.length; i++) {
			//If the argument is in the map as a key (eg, -g, -s, etc), take the index
			if (!inputValuesKeys.contains(args[i])){
				if (!args[i].equals("-h"))	//Don't say 'unknown argument' for the -h flag.
					System.out.println("Error: unknown argument '" + args[i] + "'.");
				System.out.println(help);
				throw new ParseException("Unknown flag: " + args[i]);
			}
			else{	
				try{
					//What sort of variable is associated with the current flag?  We look at the 
					// type of the variable, and depending on the type, parse the value differnt
					// ways.
					Object value = flagVariables.get(args[i]);
					
					//System.out.println(value);
					
					//Is the flag boolean?  If so, the presence of the flag indicates true.
					if (value.getClass().getName().equals("java.lang.Boolean")){
						value = new Boolean(true);
						flags.put(args[i], value);
					}
					//If the flag is not boolean, we will need to grab the argument after it.
					// To do this, we increment i, and parse the value.
					else{
						if (args.length - 1 > i) {
							i++;
							
							if (value.getClass().getName().equals("java.lang.Integer")){
								value = Integer.valueOf(args[i]);
							}
							else if (value.getClass().getName().equals("java.lang.String")){
								value = args[i];
							}
							else if (value.getClass().getName().equals("java.lang.Double")){
								value = Double.valueOf(args[i]);
							}
							else if (value.getClass().getName().equals("java.lang.Float")){	
								value = Float.valueOf(args[i]);
							}
							else if (value.getClass().getName().equals("java.lang.Character")){
								if (args[i].length() > 1){
									throw new ParseException("Invalid character: " + args[i]);
								}
								value = new Character(args[i].toCharArray()[0]);
							}
						
							flags.put(args[i - 1], value);
						}
						else {
							System.out.println(help);
							throw new ParseException("Argument expected");
						}
					}
				}
				catch (NumberFormatException nfe){
					System.out.println(help);
					throw new ParseException(nfe);
				}
			}
		}
		return flags;
	}
	
	public static class ParseException extends Exception{
		static final long serialVersionUID = 0;
		
		ParseException(Exception e){
			super(e);
		}
		
		ParseException(String s){
			super(s);
		}

	}
	
	/**
	 * Simple method to find the index in an array which matches a specified string.
	 * @param str
	 * @param array
	 * @return
	 */
/*	private static int matchesArray(String str, Object[] array){
		for (int i = 0; i < array.length; i++){
			if (str.matches((String) array[i]))
				return i;
		}
		return -1;
	}
*/	
}
