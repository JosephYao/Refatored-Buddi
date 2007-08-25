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
import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.i18n.keys.MessageKeys;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.moss.swing.dialog.JPasswordInputDialog;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.crypto.IncorrectPasswordException;

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
			String[] options = new String[2];
			options[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_YES);
			options[1] = TextFormatter.getTranslation(ButtonKeys.BUTTON_NO);

			int choice = JOptionPane.showOptionDialog(
					null, 
					TextFormatter.getTranslation(BuddiKeys.ENCRYPT_DATA_FILE_YES_NO),
					TextFormatter.getTranslation(BuddiKeys.ENCRYPT_DATA_FILE_TITLE),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[0]);
			this.encrypted = new Boolean(JOptionPane.YES_OPTION == choice);
		}

		if (!this.encrypted.booleanValue()) {
			return out;
		}

		if (this.key == null) {
			JPasswordInputDialog jpid = new JPasswordInputDialog(
					TextFormatter.getTranslation(MessageKeys.MESSAGE_ENTER_PASSWORD),
					TextFormatter.getTranslation(MessageKeys.MESSAGE_ENTER_PASSWORD_TITLE),
					TextFormatter.getTranslation(BuddiKeys.HINT_PASSWORD),
					TextFormatter.getTranslation(BuddiKeys.HINT_CONFIRM_PASSWORD),
					TextFormatter.getTranslation(MessageKeys.MESSAGE_ERROR_PASSWORDS_DONT_MATCH),
					TextFormatter.getTranslation(BuddiKeys.ERROR),
					TextFormatter.getTranslation(MessageKeys.MESSAGE_ERROR_NO_PASSWORD_ENTERED),
					TextFormatter.getTranslation(BuddiKeys.ERROR),
					TextFormatter.getTranslation(ButtonKeys.BUTTON_OK),
					TextFormatter.getTranslation(ButtonKeys.BUTTON_CANCEL)
			);
			String password = new String(jpid.askForPassword(
					null,
					true, 
					false));

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
					TextFormatter.getTranslation(MessageKeys.MESSAGE_ENTER_PASSWORD),
					TextFormatter.getTranslation(MessageKeys.MESSAGE_ENTER_PASSWORD_TITLE),
					TextFormatter.getTranslation(BuddiKeys.HINT_PASSWORD),
					TextFormatter.getTranslation(BuddiKeys.HINT_CONFIRM_PASSWORD),
					TextFormatter.getTranslation(MessageKeys.MESSAGE_ERROR_PASSWORDS_DONT_MATCH),
					TextFormatter.getTranslation(BuddiKeys.ERROR),
					TextFormatter.getTranslation(MessageKeys.MESSAGE_ERROR_NO_PASSWORD_ENTERED),
					TextFormatter.getTranslation(BuddiKeys.ERROR),
					TextFormatter.getTranslation(ButtonKeys.BUTTON_OK),
					TextFormatter.getTranslation(ButtonKeys.BUTTON_CANCEL)
			);
			String password = new String(jpid.askForPassword(
					null,
					false, 
					true));

			if (password == null){
				String[] options = new String[1];
				options[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_OK);

				JOptionPane.showOptionDialog(
						null, 
						TextFormatter.getTranslation(MessageKeys.MESSAGE_EMPTY_PASSWORD), 
						TextFormatter.getTranslation(MessageKeys.MESSAGE_EMPTY_PASSWORD_TITLE), 
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						null,
						options,
						options[0]
				);

				throw new IncorrectPasswordException(TextFormatter.getTranslation(MessageKeys.MESSAGE_ERROR_NO_PASSWORD_ENTERED));
			}
			//If the password is empty, it is not correct.
			else if (!password.equals("")){

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
			}
			if (!correctPassword){
				String[] options = new String[1];
				options[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_OK);

				JOptionPane.showOptionDialog(
						null,
						TextFormatter.getTranslation(BuddiKeys.INCORRECT_PASSWORD),
						TextFormatter.getTranslation(BuddiKeys.INCORRECT_PASSWORD_TITLE),
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null,
						options,
						options[0]);
			}
		} while (!correctPassword);

		cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, this.key, new IvParameterSpec(this.salt));
		return new CipherInputStream(buffered, cipher);		
	}

	public void finish(InputStream in) throws Exception {
		// TODO Auto-generated method stub		
	}
}