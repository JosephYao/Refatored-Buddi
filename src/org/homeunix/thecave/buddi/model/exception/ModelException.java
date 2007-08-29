/*
 * Created on Aug 3, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.exception;


/**
 * The primary exception thrown when there is a problem with the data model.
 * 
 * It is recommended to throw the instance of the problem data model if at
 * all possible, as this helps in debugging.
 * @author wyatt
 *
 */
public class ModelException extends Exception {
	public static final long serialVersionUID = 0;
	
	public ModelException() {
		super();
	}
	public ModelException(String message) {
		super(message);
	}
	public ModelException(Throwable cause) {
		super(cause);
	}
	public ModelException(String message, Throwable cause) {
		super(message, cause);
	}
}
