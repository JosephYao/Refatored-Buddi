/*
 * Created on Jun 16, 2007 by wyatt
 */
package net.sourceforge.buddi.api.manager;

import java.io.File;
import java.io.IOException;

public class BuddiUtil {
	/**
	 * Simple test to see if a folder is writable.  File.canWrite()
	 * does not work on all Windows folders, as the folder may
	 * be set RO but permissions allow it to be written.
	 * This is in response to bug #1716654, "Error creating
	 * a new file in My Documents folder".
	 * @param folder
	 * @return
	 */
	public static boolean isFolderWritable(File folder){
		if (!folder.isDirectory())
			return false;
		if (folder.canWrite())
			return true;
		File testFile = null;
		final int MAX_CHECKS = 25;
		for (int i = 0; i <= MAX_CHECKS && (testFile == null || testFile.exists()); i++){
			testFile = new File(folder.getAbsolutePath() + File.separator + "." + Math.random());
			if (i == MAX_CHECKS)
				return false; //Could not test file - all tests already existed
		}

		try {
			testFile.createNewFile();
			if (testFile.exists()){
				testFile.delete();
				return true;
			}
		}
		catch (IOException ioe){}

		return false;
	}
}
