/*
 * Created on Aug 28, 2005 by wyatt
 */
package org.homeunix.drummer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.homeunix.drummer.Const;

public class FileFunctions {
	
	private FileFunctions(){}
	
	public static void copyFile(File source, File dest) throws IOException {
		FileChannel in = null, out = null;
		try {          
			in = new FileInputStream(source).getChannel();
			out = new FileOutputStream(dest).getChannel();
			
			in.transferTo( 0, in.size(), out);
			
		}
		catch (FileNotFoundException fnfe){
			if (Const.DEVEL) Log.debug(fnfe);
		}
		finally{
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		}
	}
}
