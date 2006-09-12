package org.homeunix.drummer.model.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

import org.eclipse.emf.ecore.resource.URIConverter;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.view.components.PasswordInputPane;

public class AESCryptoCipher implements URIConverter.Cipher {
	private static final String ENCRYPTION_ALGORITHM = "AES/CFB8/PKCS5Padding";
	private static final int ENCRYPTION_IV_LENGTH = 16;
	private static final String ENCRYPTION_KEY_ALGORITHM = "AES";
	private static final String PBE_ALGORITHM = "PBEWithMD5AndDES";
	private static final int PBE_IV_LENGTH = 8;	
	private static final int PBE_ITERATIONS = 1000;
	private static final String XML_PROLOGUE = "<?xml";

	private static KeyGenerator keygen;
	private static SecureRandom random;

	private static Key generateKey() {
		if (null == AESCryptoCipher.keygen) {
			// TODO - we should allow users to use the maximum allowed keysize if
			// they desire. However, using anything larger than 128 may make the 
			// data file non-portable. For this reason, we hardcode 128 until we
			// figure out how to provide a way for the user to specify the key size.
			//int keysize = Cipher.getMaxAllowedKeyLength(ENCRYPTION_ALGORITHM);
			int keysize = 128;

			try {
				AESCryptoCipher.keygen = KeyGenerator.getInstance(ENCRYPTION_KEY_ALGORITHM);
				AESCryptoCipher.keygen.init(keysize);
			} catch (Exception ex) {
				// all implementations of Java 1.5 should support AES
				throw new RuntimeException(ex);
			}
		}

		return AESCryptoCipher.keygen.generateKey();
	}

	private static byte[] randomBytes(int length) {
		if (null == AESCryptoCipher.random) {
			// TODO - does this need to be seeded?
			AESCryptoCipher.random = new SecureRandom();
		}

		byte[] bytes = new byte[length];	
		AESCryptoCipher.random.nextBytes(bytes);

		return bytes;
	}

	private static byte[] readBytes(int length, InputStream in) throws Exception {
		byte[] bytes = new byte[length];
		int read = in.read(bytes);	

		if (read != length) {
			throw new Exception("expected length != actual length");
		}

		return bytes;
	}

	private static byte[] transformWithPassword(byte[] bytes, byte[] iv, String password, int mode) 
	throws Exception {
		// generate the key
		PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBE_ALGORITHM);
		SecretKey pbeKey = keyFactory.generateSecret(pbeKeySpec);
		PBEParameterSpec pbeParamSpec = new PBEParameterSpec(iv, PBE_ITERATIONS);

		// encrypt the input
		Cipher keyCipher = Cipher.getInstance(PBE_ALGORITHM);
		keyCipher.init(mode, pbeKey, pbeParamSpec);
		return keyCipher.doFinal(bytes);
	}

	private Boolean encrypted;
	// we cache this data for performance - otherwise we'd have to
	// recalculate them each time the file is read/written
	private Key key;
	private byte[] encryptedKeyBytes;
	private byte[] pbeIV;
	private byte[] encryptionIV;

	public void setEncrypted(boolean encrypted){
		this.encrypted = encrypted;
	}
	
	public OutputStream encrypt(OutputStream out) throws Exception {
		if (this.encrypted == null) {
			// ask the user if the stream should be encrypted
			int choice = JOptionPane.showConfirmDialog(
					null, 
					Translate.getInstance().get(TranslateKeys.ENCRYPT_DATA_FILE_YES_NO),
					Translate.getInstance().get(TranslateKeys.ENCRYPT_DATA_FILE_TITLE),
					JOptionPane.YES_NO_OPTION);
			this.encrypted = new Boolean(JOptionPane.YES_OPTION == choice);
		}

		if (!this.encrypted.booleanValue()) {
			return out;
		}

		if (this.key == null) {
			String password = PasswordInputPane.askForPassword(true);
			
			if (password == null){
				this.encrypted = false;
				return out;
			}

			// this is the key we will use to encrypt the data 
			this.key = generateKey();

			// create the IV for the password generation algorithm
			this.pbeIV = randomBytes(PBE_IV_LENGTH);

			// generate the IV for encryption
			this.encryptionIV = randomBytes(ENCRYPTION_IV_LENGTH);

			// turn the password into an AES key
			this.encryptedKeyBytes = transformWithPassword(
					key.getEncoded(), this.pbeIV, password, Cipher.ENCRYPT_MODE);
		}

		// write the header to the output stream. this has the format 
		// (delimeters are not written):
		// PBE IV|ENCRYPTION IV|ENCRYPTED KEY LENGTH|ENCRYPTED KEY
		out.write(this.pbeIV);
		out.write(this.encryptionIV);
		out.write(this.encryptedKeyBytes.length);
		out.write(this.encryptedKeyBytes);

		// now create the encryption cipher 
		Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, this.key, new IvParameterSpec(this.encryptionIV));
		return new CipherOutputStream(out, cipher);
	}

	public void finish(OutputStream out) throws Exception {
		// this is a workaround for a bug in EMF - ResourceImpl.save only closes the
		// underlying stream, but for a cipher stream to work correctly, it must be
		// closed directly
		out.close();
	}

	public InputStream decrypt(InputStream in) throws Exception {
		BufferedInputStream buffered = null;

		if (in instanceof BufferedInputStream) {
			buffered = (BufferedInputStream) in;
		} else {
			buffered = new BufferedInputStream(in);
		}

		// Test if the input stream is encrypted. This is rudimentary -
		// it just checks if the stream starts with <?xml
		int prologueLength = XML_PROLOGUE.length();

		buffered.mark(prologueLength);

		byte[] test = new byte[prologueLength];
		int read = buffered.read(test);
		if (read < prologueLength) {
			this.encrypted = Boolean.FALSE;
		} 
		else {
			// TODO - we should probably create this string using the same
			// encoding as EMF - how to figure this out?
			String testStr = new String(test);
			this.encrypted = new Boolean(!XML_PROLOGUE.equals(testStr));
		}

		buffered.reset();		

		if (!encrypted.booleanValue()) {
			// we're loading an unencrypted file, so clear the cached key, etc.
			this.key = null;
			this.pbeIV = null;
			this.encryptionIV = null;
			this.encryptedKeyBytes = null;

			return buffered;
		}

		// Read the header of the encrypted file and cache the key and other
		// information. This is done each time since the user may be loading
		// a different data file than the time before.

		this.pbeIV = readBytes(PBE_IV_LENGTH, buffered);
		this.encryptionIV = readBytes(ENCRYPTION_IV_LENGTH, buffered);
		int keyLength = buffered.read();
		this.encryptedKeyBytes = readBytes(keyLength, buffered);

		// ask for the password, which we use to decrypt the AES key
		String password = PasswordInputPane.askForPassword(false);

		// Decrypt the key bytes
		byte[] decryptedKeyBytes = transformWithPassword(
				this.encryptedKeyBytes, this.pbeIV, password, Cipher.DECRYPT_MODE);

		// Create the key from the key bytes
		this.key = new SecretKeySpec(decryptedKeyBytes, ENCRYPTION_KEY_ALGORITHM);

		// now create the decryption cipher
		Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, this.key, new IvParameterSpec(this.encryptionIV));
		return new CipherInputStream(buffered, cipher);		
	}

	public void finish(InputStream in) throws Exception {
		// TODO Auto-generated method stub		
	}
}