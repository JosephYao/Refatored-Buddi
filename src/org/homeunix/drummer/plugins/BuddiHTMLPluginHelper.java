/*
 * Created on Oct 7, 2006 by wyatt
 */
package org.homeunix.drummer.plugins;

import java.util.Date;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.thecave.moss.util.Formatter;

public class BuddiHTMLPluginHelper {
	public static StringBuffer getHtmlHeader(TranslateKeys reportTitle, Date startDate, Date endDate){
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"); 
		sb.append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<title>");
		sb.append(Translate.getInstance().get(reportTitle));
		sb.append("</title>\n");

		sb.append("<style type=\"text/css\">\n");
		sb.append(".red { color: red; }");
		sb.append("h1 { font-size: large; }");
		sb.append("table.main { background-color: black; width: 100%; }\n");
		sb.append("table.transactions { background-color: white; width: 100%; padding-left: 3em; }\n");
		sb.append("table.main tr { padding-bottom: 1em; }\n");
		sb.append("table.main th { width: 20%; background-color: #DDE}\n");
		sb.append("table.main td { width: 20%; background-color: #EEF}\n");
		sb.append("table.transactions td { width: 30%; background-color: white}\n");
		sb.append("</style>\n");

		sb.append("</head>\n");
		sb.append("<body>\n");

		sb.append("<h1>");
		sb.append(Translate.getInstance().get(reportTitle));
		sb.append(" ("); 
		sb.append(Formatter.getInstance().getDateFormat().format(startDate));
		sb.append(" - ");
		sb.append(Formatter.getInstance().getDateFormat().format(endDate));
		sb.append(") ");
		sb.append("</h1>\n");

		return sb;
	}
}
