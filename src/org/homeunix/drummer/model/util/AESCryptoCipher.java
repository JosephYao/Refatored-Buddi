package org.homeunix.drummer.model.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.JOptionPane;

import org.eclipse.emf.ecore.resource.URIConverter;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.thecave.moss.gui.JPasswordInputDialog;
import org.homeunix.thecave.moss.util.Log;

public class AESCryptoCipher implements URIConverter.Cipher {
	private static final String ALGORITHM = "AES/CFB8/PKCS5Padding";
	private static final int SALT_LENGTH = 16;
	private static final String KEY_ALGORITHM = "AES";
	private static final String XML_PROLOGUE = "<?xml";

	private static SecureRandom random;

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

	private int keySize;
	private Boolean encrypted;
	private Key key;
	private byte[] salt;
	
	public AESCryptoCipher() {
		try {
			this.keySize = Cipher.getMaxAllowedKeyLength(ALGORITHM) / 8;
		} catch (Exception ex) {
			// all implementations of Java 1.5 should support AES
			throw new RuntimeException(ex);
		}
	}
	
	public AESCryptoCipher(int keyLength) {
		try {
			this.keySize = Math.min(keyLength / 8, Cipher.getMaxAllowedKeyLength(ALGORITHM) / 8);
		} catch (Exception ex) {
			// all implementations of Java 1.5 should support AES
			throw new RuntimeException(ex);
		}
	}

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
			JPasswordInputDialog jpid = new JPasswordInputDialog(
					Translate.getInstance().get(TranslateKeys.ENTER_PASSWORD),
					Translate.getInstance().get(TranslateKeys.ENTER_PASSWORD_TITLE),
					Translate.getInstance().get(TranslateKeys.PASSWORD),
					Translate.getInstance().get(TranslateKeys.CONFIRM_PASSWORD),
					Translate.getInstance().get(TranslateKeys.PASSWORDS_DONT_MATCH),
					Translate.getInstance().get(TranslateKeys.ERROR),
					Translate.getInstance().get(TranslateKeys.NO_PASSWORD_ENTERED),
					Translate.getInstance().get(TranslateKeys.ERROR),
					Translate.getInstance().get(TranslateKeys.OK),
					Translate.getInstance().get(TranslateKeys.CANCEL)
			);
			String password = jpid.askForPassword(true, true);
			
			if (password == null){
				this.encrypted = false;
				return out;
			}

			// generate the salt for encryption
			this.salt = randomBytes(SALT_LENGTH);

			this.key = PBKDF2.getInstance().passwordToKey(
					password, this.keySize, KEY_ALGORITHM, this.salt);
		}

		// Write the salt to the output stream so we can use it when decrypting the file.
		out.write(this.salt);

		// now create the encryption cipher 
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, this.key, new IvParameterSpec(this.salt));
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
		final int prologueLength = XML_PROLOGUE.length();

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
			this.salt = null;
			
			return buffered;
		}

		// Read the salt of the encrypted file and cache it. This is done each time 
		// since the user may be loading a different data file than the time before.

		this.salt = readBytes(SALT_LENGTH, buffered);

		Cipher cipher;
		boolean correctPassword = false;
		do {
			// ask for the password, which we use to generate the AES key
			JPasswordInputDialog jpid = new JPasswordInputDialog(
					Translate.getInstance().get(TranslateKeys.ENTER_PASSWORD),
					Translate.getInstance().get(TranslateKeys.ENTER_PASSWORD_TITLE),
					Translate.getInstance().get(TranslateKeys.PASSWORD),
					Translate.getInstance().get(TranslateKeys.CONFIRM_PASSWORD),
					Translate.getInstance().get(TranslateKeys.PASSWORDS_DONT_MATCH),
					Translate.getInstance().get(TranslateKeys.ERROR),
					Translate.getInstance().get(TranslateKeys.NO_PASSWORD_ENTERED),
					Translate.getInstance().get(TranslateKeys.ERROR),
					Translate.getInstance().get(TranslateKeys.OK),
					Translate.getInstance().get(TranslateKeys.CANCEL)
			);
			String password = jpid.askForPassword(false, false);
			
			if (password == null){
				JOptionPane.showMessageDialog(
						null, 
						Translate.getInstance().get(TranslateKeys.EMPTY_PASSWORD), 
						Translate.getInstance().get(TranslateKeys.EMPTY_PASSWORD_TITLE), 
						JOptionPane.INFORMATION_MESSAGE
				);

				System.exit(1);
			}

			if (Const.DEVEL) Log.debug("Read password");
			
			this.key = PBKDF2.getInstance().passwordToKey(
					password, this.keySize, KEY_ALGORITHM, this.salt);

			// now create the decryption cipher
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, this.key, new IvParameterSpec(this.salt));
			
			if (Const.DEVEL) Log.debug("Created CIS");
			CipherInputStream cis = new CipherInputStream(buffered, cipher);

			cis.mark(prologueLength);

			test = new byte[prologueLength];
			read = cis.read(test);
			String testStr = new String(test);
			if (Const.DEVEL) Log.debug("testStr == " + testStr);
			
			//Check if the file is really decoded.
			correctPassword = new Boolean(XML_PROLOGUE.equals(testStr));

			cis.reset();
			buffered.reset();
			
			if (!correctPassword){
				JOptionPane.showMessageDialog(
						null,
						Translate.getInstance().get(TranslateKeys.INCORRECT_PASSWORD),
						Translate.getInstance().get(TranslateKeys.INCORRECT_PASSWORD_TITLE),
						JOptionPane.ERROR_MESSAGE);
			}
		} while (!correctPassword);
		
		cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, this.key, new IvParameterSpec(this.salt));
		return new CipherInputStream(buffered, cipher);		
	}

	public void finish(InputStream in) throws Exception {
		// TODO Auto-generated method stub		
	}
	
	public class CipherException extends Exception {
		public final static long serialVersionUID = 0;
		
		public CipherException(){
			super();
		}

		public CipherException(String message){
			super(message);
		}
	}
}