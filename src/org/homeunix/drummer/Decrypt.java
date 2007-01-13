/*
 * Created on Jan 12, 2007 by wyatt
 */
package org.homeunix.drummer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.homeunix.drummer.model.util.AESCryptoCipher;

public class Decrypt {

	/**
	 * Used to decrypt a data file.  Can be run from jar with the
	 * command 'java -classpath Buddi.jar org.homeunix.drummer.Decrypt'.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		final JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setFileHidingEnabled(true);
		jfc.setFileFilter(new FileFilter(){
			public boolean accept(File arg0) {
				if (arg0.isDirectory() 
						|| arg0.getName().endsWith(Const.DATA_FILE_EXTENSION))
					return true;
				else
					return false;
			}

			public String getDescription() {
				return "Buddi Files";
			}
		});
		jfc.setDialogTitle("Open Buddi File");
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			File file = jfc.getSelectedFile();
			if (!file.exists() || file.isDirectory()){
				System.out.println("You must point to the file to decrypt!");
				System.exit(1);
			}

			AESCryptoCipher cipher = new AESCryptoCipher(128);
			InputStream plaintext = cipher.decrypt(new FileInputStream(file));
			BufferedReader in = new BufferedReader(new InputStreamReader(plaintext));

			String line;
			while ((line = in.readLine()) != null){
				System.out.println(line);
			}
		}
	}

}
