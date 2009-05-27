/*
 * Created on Jan 10, 2008 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import org.homeunix.thecave.moss.application.document.exception.DocumentSaveException;

public class ConcurrentSaveException extends DocumentSaveException {
	private static final long serialVersionUID = 1L;

	public ConcurrentSaveException() {
		super();
	}
	
	public ConcurrentSaveException(String message) {
		super(message);
	}
}
