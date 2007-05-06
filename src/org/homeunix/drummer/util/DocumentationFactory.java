/*
 * Created on Oct 19, 2006 by wyatt
 */
package org.homeunix.drummer.util;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import net.roydesign.ui.JScreenMenu;
import net.roydesign.ui.JScreenMenuItem;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.view.components.JDocumentDialog;
import org.homeunix.thecave.moss.jar.JarLoader;
import org.homeunix.thecave.moss.util.FileFunctions;

public class DocumentationFactory {
	
	/**
	 * Returns a menu containing links to documents
	 * @return JScreenMenu which launches document viewer
	 */
	public static JScreenMenu getDocumentsMenu(){
		JScreenMenu documentsMenu = new JScreenMenu(Translate.getInstance().get(TranslateKeys.DOCUMENTS));
		
		for (String s : Const.BUNDLED_DOCUMENTS) {
			JScreenMenuItem item = new JScreenMenuItem(s.replaceAll(".txt$", ""));
			final String string = getDocumentText(s);
			item.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					JDocumentDialog dialog = new JDocumentDialog(string);
					dialog.openWindow(new Dimension(640, 480), null);
				}
			});
			documentsMenu.add(item);
		}
		
		return documentsMenu;
	}
	
	/**
	 * Returns a menu containing links to licenses
	 * @return JScreenMenu which launches document viewer
	 */
	public static JScreenMenu getLicensesMenu(){
		JScreenMenu licensesMenu = new JScreenMenu(Translate.getInstance().get(TranslateKeys.LICENCES));
		
		for (String s : Const.BUNDLED_LICENSES) {
			JScreenMenuItem item = new JScreenMenuItem(s.replaceAll(".txt$", ""));
			final String string = getDocumentText(s);
			item.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					new JDocumentDialog(string);
				}
			});
			licensesMenu.add(item);
		}
		
		return licensesMenu;		
	}
	
	private static String getDocumentText(String documentName){
		try{
			InputStream is = JarLoader.getSystemResourceAsStream(documentName);
			if (is == null)
				throw new IOException("Null filestream");
			return FileFunctions.readTextStream(is);
		}
		catch (IOException ioe){
			return "Cannot open file" + documentName + ".\n\nIf you are running from source, this is normal.\nIf you are running from a compiled version, this is not - please contact Wyatt Olson <wyatt.olson@gmail.com>.";
		}
	}
}
