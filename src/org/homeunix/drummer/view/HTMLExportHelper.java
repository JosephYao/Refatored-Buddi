/*
 * Created on Oct 7, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;

public class HTMLExportHelper {


	/**
	 * Get a StringBuilder with an HTML header, including some CSS for a 
	 * basic printable page. 
	 * @param title The main title - appears on the browser title, as well as on the top of the page
	 * @param subtitle The subtitle - optional - appears right below main title.  Set to null to not use.
	 * @param startDate Start date.  Set to null if this is not a report with date range.
	 * @param endDate End date.  Set to null if this is not a report with date range. 
	 * @return
	 */
	public static StringBuilder getHtmlHeader(String title, String subtitle, Date startDate, Date endDate){
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"); 
		sb.append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<title>");
		sb.append(Translate.getInstance().get(title));
		sb.append("</title>\n");

		sb.append("<style type=\"text/css\">\n");
		sb.append(".red { color: red; }\n");
		sb.append(".blue { color: blue; }\n");
		sb.append(".green { color: green; }\n");
		
		sb.append(".right { text-align: right; }\n");
		sb.append(".center { text-align: center; }\n");
		sb.append(".left { text-align: left; }\n");
		
		sb.append("h1 { font-size: x-large; }\n");
		sb.append("h2 { font-size: large; }\n");
		sb.append("h3 { font-size: medium; }\n");
		sb.append("h4 { font-size: small; }\n");
		sb.append("h5 { font-size: x-small; }\n");
		
		sb.append("table.main { background-color: black; width: 100%; }\n");
//		sb.append("table.transactions { background-color: white; width: 100%; padding-left: 3em; }\n");
		sb.append("table.main tr { padding-bottom: 1em; }\n");
		sb.append("table.main th { width: 20%; background-color: #DDE}\n");
		sb.append("table.main td { width: 20%; background-color: #EEF}\n");
//		sb.append("table.transactions td { width: 30%; background-color: white}\n");
		sb.append("</style>\n");

		sb.append("</head>\n");
		sb.append("<body>\n");

		sb.append("<h1>").append(Translate.getInstance().get(title)).append("</h1>");

		if (subtitle != null){
			sb.append("<h2>").append(Translate.getInstance().get(subtitle)).append("</h2>\n");
		}

		if (startDate != null && endDate != null){
			sb.append("<h2>"); 
			sb.append(Formatter.getInstance().getDateFormat().format(startDate));
			sb.append(" - ");
			sb.append(Formatter.getInstance().getDateFormat().format(endDate));
			sb.append("</h2>");
		}


		return sb;
	}
	
	public static StringBuilder getHtmlFooter(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("</html>");
		
		return sb;
	}

	/**
	 * Saves the HTML and associated images to disk.  Returns the
	 * file object of the associated HTML index on success, or null
	 * on failure. 
	 */
	public static File createHTML(String name, HTMLWrapper html){
		final int countMax = 1000;

		File dataFolder = new File(PrefsInstance.getInstance().getPrefs().getDataFile()).getParentFile();
		File htmlFolder = null;
		int counter = 0;
		//Get a unique folder name which has not yet been used.
		while (counter < countMax && (htmlFolder == null || htmlFolder.exists())){
			htmlFolder = new File(dataFolder.getAbsolutePath() + File.separator + name + (counter > 0 ? "_" + counter : ""));
			counter++;
		}
		//We could not create a folder, after 1000 tries.  Exit.
		if (counter == countMax){
			Log.warning("Could not find a folder to use in the data folder, after 1000 tries.  Cancelling HTML export.");
			return null;
		}

		//We could not create a folder, even though we found a file.  Exit.
		if (!htmlFolder.mkdir()){
			Log.warning("Could not create folder '" + htmlFolder.getAbsolutePath() + "'.  Cancelling HTML export.");
			return null;
		}

		File htmlFile = new File(htmlFolder.getAbsolutePath() + File.separator + "index.html");
		htmlFolder.deleteOnExit();
		htmlFile.deleteOnExit();
		try{
			OutputStreamWriter out = new OutputStreamWriter(
					new BufferedOutputStream(
							new FileOutputStream(htmlFile)), "UTF-8");
			out.write(html.getHtml());
			out.close();
//			BufferedWriter bw = new BufferedWriter(
//					new FileWriter(htmlFile));
//			bw.write(html.getHtml());
//			bw.close();
		}
		catch (IOException ioe){
			Log.error("Could not write HTML file.");
			return null;
		}

		if (html.getImages() != null){
			for (String imgName : html.getImages().keySet()) {
				if (imgName != null && html.getImages().get(imgName) != null){
					File f = new File(htmlFolder.getAbsolutePath() + File.separator + imgName);
					try {
						ImageIO.write(html.getImages().get(imgName), "png", f);
						f.deleteOnExit();
					}
					catch (IOException ioe){
						Log.warning("Error writing file " + f.getAbsolutePath());
					}
				}
			}
		}

		return htmlFile;
	}
	
	public static class HTMLWrapper {
		private String html;
		private Map<String, BufferedImage> images;

		public HTMLWrapper(String html, Map<String, BufferedImage> images) {
			this.html = html;
			this.images = images;
		}
		public String getHtml() {
			return html;
		}
		public Map<String, BufferedImage> getImages() {
			return images;
		}
	}
}
