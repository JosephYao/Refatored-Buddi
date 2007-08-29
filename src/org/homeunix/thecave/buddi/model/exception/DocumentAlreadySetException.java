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
public class DocumentAlreadySetException extends InvalidValueException {
	public static final long serialVersionUID = 0;
	
	public DocumentAlreadySetException() {
		super();
	}
	public DocumentAlreadySetException(String message) {
		super(message);
	}
	public DocumentAlreadySetException(Throwable cause) {
		super(cause);
	}
	public DocumentAlreadySetException(String message, Throwable cause) {
		super(message, cause);
	}
}
