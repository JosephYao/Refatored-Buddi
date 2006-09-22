/*
 * Created on Aug 28, 2005 by wyatt
 */
package org.homeunix.drummer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

	/*
	 * Taken from http://www.javazoid.com/foj_file.html, with 
	 * major modification to support generic InputStream reading
	 */
	public static String readTextStream(InputStream stream) throws IOException {
		StringBuilder sb = new StringBuilder(1024);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));			
		char[] chars = new char[1024];

		while(reader.read(chars) > -1){
			sb.append(String.valueOf(chars));
		}

		reader.close();
		return sb.toString();
	}
}
