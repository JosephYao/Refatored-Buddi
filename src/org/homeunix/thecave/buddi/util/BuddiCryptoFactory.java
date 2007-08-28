/*
 * Created on Aug 21, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.util;

import org.homeunix.thecave.moss.util.crypto.CipherException;
import org.homeunix.thecave.moss.util.crypto.MossCryptoFactory;

/**
 * A class which extends CipherStreamFactory and provides the header and canary values
 * for Buddi to use when saving / loading data files.
 * 
 * @author wyatt
 *
 */
public class BuddiCryptoFactory  extends MossCryptoFactory {
	private static final byte[] CANARY = "0123456789abcdef".getBytes();
	private static final byte[] HEADER = "buddi3_data".getBytes();

	public BuddiCryptoFactory() throws CipherException {}
	
	@Override
	public byte[] getCanary() {
		return CANARY;
	}

	@Override
	public byte[] getHeader() {
		return HEADER;
	}
}
