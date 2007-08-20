package org.homeunix.drummer.model.util;

import java.security.Key;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class PBKDF2 {
	private static final int DEFAULT_ITERATIONS = 1000;
	private static final String DEFAULT_MAC_ALGORITHM = "HmacMD5";
	
	private static PBKDF2 instance;
	
	public static PBKDF2 getInstance() {
		if (null == instance) {
			instance = new PBKDF2();
		}
		return instance;
	}
	
	private int iterations;
	private String macAlgorithm;
	
	public PBKDF2() {
		this(DEFAULT_ITERATIONS, DEFAULT_MAC_ALGORITHM);
	}
	
	public PBKDF2(int iterations, String macAlgorithm) {
		this.iterations = iterations;
		this.macAlgorithm = macAlgorithm;
	}
		
	public Key passwordToKey(String password, int keyLength, String keyAlgorithm, byte[] salt) 
			throws Exception {
		byte[] bytes = passwordToKeyBytes(password, keyLength, salt);
		return new SecretKeySpec(bytes, keyAlgorithm);
	}
	
	public byte[] passwordToKeyBytes(String password, int keyLength, byte[] salt) throws Exception {
		return passwordToKeyBytes(password.getBytes(), keyLength, salt);
	}
	
	public byte[] passwordToKeyBytes(byte[] password, int keyLength, byte[] salt) throws Exception {		
		Mac mac = Mac.getInstance(this.macAlgorithm); 
		mac.init(new SecretKeySpec(password, this.macAlgorithm));
		
		int digestLength = mac.getMacLength();
		int blocks = (int) Math.ceil((double) keyLength / digestLength);
		
		byte[] buf = new byte[digestLength * blocks];
		
		for (int i = 0; i < blocks; i++) {
			System.arraycopy(deriveKeyBytes(mac, salt, i+1), 0, 
					 		 buf, (i * digestLength), digestLength);
		}
		
		// we may have more bytes than we need
		byte[] key = new byte[keyLength];
		System.arraycopy(buf, 0, key, 0, keyLength);
		
		return key;
	}
	
	private byte[] deriveKeyBytes(Mac mac, byte[] salt, int block) {
		// create a unique salt for this block by adding the block number to the 
		// salt, and use it to initialize the buffer we will use to generate the hash
		byte[] buf = new byte[salt.length + 4];
		System.arraycopy(salt, 0, buf, 0, salt.length);
		for (int i = 0; i < 4; i++) {
			buf[salt.length + i] = (byte) (block >> (8 * (3 - i)));
		}
		
		// create the array that will contain the portion of the key
		// dervied from the current block 
		byte[] blockHash = new byte[mac.getMacLength()];
		
		// for each iteration, apply the digest to the password and the
		// block salt, and xor the result with the blockHash
		for (int i = 0; i < this.iterations; i++) {
			buf = mac.doFinal(buf);
			for (int j = 0; j < blockHash.length; j++) {
				blockHash[j] ^= buf[j];
			}
		}
		
		return blockHash;
	}
}
