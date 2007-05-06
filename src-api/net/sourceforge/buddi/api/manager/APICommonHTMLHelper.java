/*
 * Created on Oct 7, 2006 by wyatt
 */
package net.sourceforge.buddi.api.manager;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sourceforge.buddi.api.model.ImmutableSource;
import net.sourceforge.buddi.api.model.ImmutableTransaction;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.BuddiInternalFormatter;
import org.homeunix.thecave.moss.util.Log;

public class APICommonHTMLHelper {


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

		//Screen CSS
		sb.append("<style type='text/css' media='screen'>\n");
		sb.append("body { background-color: #a3b8ce; color: #222; min-width: 50em; margin: 1em; padding: 0em; }\n");
		sb.append(".content { border: 1em solid white; background-color: white; margin: 0em; }\n");
		sb.append(".header { background-color: #59a1ea; padding-left: 4em; height: 8em; margin: 0em; top: -1em; font-variant: small-caps; font-weight: bold; font-size-adjust: 0.6; border-left: 8em solid #dfc700; }\n");
		sb.append(".separator { height: 0.4em; background-color: black; color: black; }\n");
		sb.append(".empty { font-size-adjust: 0; height: 0em; }\n");

			
		sb.append(".red { color: red; }\n");
		
		sb.append(".right { text-align: right; }\n");
		sb.append(".center { text-align: center; }\n");
		sb.append(".left { text-align: left; }\n");
		
		sb.append(".center_img { display: block; margin-left: auto; margin-right: auto; }\n");
		
		sb.append("h1 { font-size: x-large; }\n");
		sb.append("h2 { font-size: large; }\n");
		sb.append("h3 { font-size: medium; }\n");
		sb.append("h4 { font-size: small; }\n");
		sb.append("h5 { font-size: x-small; }\n");

		sb.append("hr { color: black; background-color: black; height: 0.3em; border: 0em; }\n");
		
		sb.append("table.main { background-color: black; width: 100%; }\n");
		sb.append("table.main tr { padding-bottom: 1em; }\n");
		sb.append("table.main th { background-color: #DDE}\n");
		sb.append("table.main td { background-color: #EEF}\n");
		sb.append("</style>\n");
		
		//Printing CSS
		sb.append("<style type='text/css' media='print'>\n");
		sb.append("body { background-color: white; color: black; }\n");
		sb.append(".content { border: 1em solid white; background-color: white; margin: 0em; }\n");
		sb.append(".header { height: 8em; margin: 0em; top: -1em; font-variant: small-caps; font-weight: bold; font-size-adjust: 0.6; }\n");
		sb.append(".separator { height: 0.4em; background-color: black; color: black; }\n");
		sb.append(".empty { font-size-adjust: 0; height: 0em; }\n");

//		sb.append(".red { color: red; }\n");
		
		sb.append(".right { text-align: right; }\n");
		sb.append(".center { text-align: center; }\n");
		sb.append(".left { text-align: left; }\n");
		
		sb.append(".center_img { display: block; margin-left: auto; margin-right: auto; }\n");
		
		sb.append("h1 { font-size: x-large; }\n");
		sb.append("h2 { font-size: large; }\n");
		sb.append("h3 { font-size: medium; }\n");
		sb.append("h4 { font-size: small; }\n");
		sb.append("h5 { font-size: x-small; }\n");

		sb.append("hr { color: black; background-color: black; height: 0.3em; border: 0em; }\n");
		
		sb.append("table.main { border: 0.01cm solid black; width: 100%; }\n");
		sb.append("table.main tr { padding-bottom: 1em; }\n");
		sb.append("table.main th { border-bottom: 0.01cm solid black; border-right: 0.01cm solid black; background-color: #DDE}\n");
		sb.append("table.main td { border-bottom: 0.01cm solid black; border-right: 0.01cm solid black; background-color: #EEF}\n");
		sb.append("</style>\n");
		

		sb.append("</head>\n");
		sb.append("<body>\n<div class='separator'></div>\n");

		sb.append("<div class='header'>\n<div class='empty'>&nbsp;</div>");
		sb.append("<h1>").append(Translate.getInstance().get(title)).append("</h1>\n");

		if (subtitle != null){
			sb.append("<h2>").append(Translate.getInstance().get(subtitle)).append("</h2>\n");
		}

		if (startDate != null && endDate != null){
			sb.append("<h2>"); 
			sb.append(BuddiInternalFormatter.getDateFormat().format(startDate));
			sb.append(" - ");
			sb.append(BuddiInternalFormatter.getDateFormat().format(endDate));
			sb.append("</h2>\n");
		}
		else if (startDate != null){
			sb.append("<h2>"); 
			sb.append(BuddiInternalFormatter.getDateFormat().format(startDate));
			sb.append("</h2>\n");			
		}
		else if (endDate != null){
			sb.append("<h2>"); 
			sb.append(BuddiInternalFormatter.getDateFormat().format(endDate));
			sb.append("</h2>\n");	
		}

		sb.append("</div>\n<div class='separator'></div>\n<div class='content'>\n\n");
		
		return sb;
	}
	
	/**
	 * Get the HTML footer, matched to the header supplied from getHtmlHeader()
	 * @return
	 */
	public static StringBuilder getHtmlFooter(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("</div>\n</body>\n</html>");
		
		return sb;
	}
	
	/**
	 * Returns an HTML table row consisting of information from the given transaction. 
	 * @param t Transaction to display.
	 * @param associatedSource Associated source.  This would be the account which 
	 * the transaction frame is associated with, for instace.  This can be null
	 * if there is none.
	 * @return
	 */
	public static StringBuilder getHtmlTransactionRow(ImmutableTransaction t, ImmutableSource associatedSource){
		StringBuilder sb = new StringBuilder();
		
		sb.append("<tr><td width='20%'>");
		sb.append(BuddiInternalFormatter.getDateFormat().format(t.getDate()));
		
		sb.append("</td><td width='30%'>");
		sb.append(Translate.getInstance().get(t.getDescription()));
		
		sb.append("</td><td width='35%'>");
		System.out.println(t);
		System.out.println(t.getFrom());
		System.out.println(t.getFrom().toString());
		sb.append(Translate.getInstance().get(t.getFrom().toString()));
		sb.append(Translate.getInstance().get(TranslateKeys.HTML_TO));
		sb.append(Translate.getInstance().get(t.getTo().toString()));

		boolean red;
		if (associatedSource != null)
			red = APICommonFormatter.isRed(t, t.getFrom().equals(associatedSource));
		else 
			red = APICommonFormatter.isRed(t);
		
		sb.append("</td><td width='15%' class='right" + (red ? " red'" : "'") + "'>");
		sb.append(APICommonFormatter.getFormattedCurrency(t.getAmount()));
		sb.append("</td></tr>\n");
		
		return sb;		
	}

	/**
	 * Returns the start of a table for displaying transactions,
	 * including the header row.
	 * @return
	 */
	public static StringBuilder getHtmlTransactionHeader(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("<table class='main'>\n");
		sb.append("<tr><th>");
		sb.append(Translate.getInstance().get(TranslateKeys.DATE));
		sb.append("</th><th>");
		sb.append(Translate.getInstance().get(TranslateKeys.DESCRIPTION));
		sb.append("</th><th>");
		sb.append(Translate.getInstance().get(TranslateKeys.SOURCE_TO_FROM));
		sb.append("</th><th>");
		sb.append(Translate.getInstance().get(TranslateKeys.AMOUNT));
		sb.append("</th></tr>\n");

		return sb;
	}
	
	/**
	 * Returns the end of a table for displaying transactions.
	 * @return
	 */
	public static StringBuilder getHtmlTransactionFooter(){
		return new StringBuilder("</table>\n\n");
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
