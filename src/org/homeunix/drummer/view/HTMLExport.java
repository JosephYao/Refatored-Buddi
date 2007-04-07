/*
 * Created on Apr 5, 2007 by wyatt
 */
package org.homeunix.drummer.view;

import java.io.File;
import java.io.IOException;

public interface HTMLExport {
	
	/**
	 * The implementing method should write an HTML Export to disk,
	 * in the folder specified by indexFile.getParentFile(), with
	 * the main HTML file (i.e. the index) stored in indexFile.  Any
	 * supporting files (graphics, linked content, etc) should be
	 * in the same folder, or sub folders as appropriate.  All created
	 * files MAY be marked as deleteOnExit if desired by the implementing
	 * author.
	 * 
	 * Depending on the goal of the HTML output, it MAY be correct to not
	 * have lins to other pages from here; for instance, if the HTML
	 * export is to be used for printing.  This should be clearly defined
	 * for the implementing program.
	 *  
	 * @param indexFile The index HTML file.  All supporting documents will
	 * be in the same folder as this one is.
	 * @throws IOException If the file(s) cannot be created.
	 */
	public File exportToHTML() throws IOException;
}
