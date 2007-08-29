/*
 * Created on Aug 3, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.exception;


/**
 * The primary exception thrown when there is a problem with the data model.
 * 
 * It is recommended to throw the instance of the problem data model if at
 * all possible, as this helps in debugging.
 * @author wyatt
 *
 */
public class InvalidActionException extends Exception {
	public static final long serialVersionUID = 0;
	
	public InvalidActionException() {
		super();
	}
	public InvalidActionException(String message) {
		super(message);
	}
	public InvalidActionException(Throwable cause) {
		super(cause);
	}
	public InvalidActionException(String message, Throwable cause) {
		super(message, cause);
	}
}
