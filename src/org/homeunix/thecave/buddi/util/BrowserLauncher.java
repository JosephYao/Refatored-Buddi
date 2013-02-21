package org.homeunix.thecave.buddi.util;

import java.io.IOException;

import ca.digitalcave.moss.common.OperatingSystemUtil;

public class BrowserLauncher {

	public static void open(String url){
		final Runtime rt = Runtime.getRuntime();

		try{
			if (OperatingSystemUtil.isWindows()) {
				// this doesn't support showing urls in the form of "page.html#nameLink" 
				rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);
			}
			else if (OperatingSystemUtil.isMac()) {
				rt.exec( "open " + url);
			}
			else {

				// Do a best guess on unix until we get a platform independent way
				// Build a list of browsers to try, in this order.
				String[] browsers = {"chromium-browser", "google-chrome", "epiphany", "firefox", "mozilla", "konqueror", "netscape", "opera", "links", "lynx"};

				// Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
				StringBuilder cmd = new StringBuilder();
				for (int i=0; i<browsers.length; i++){
					cmd.append(i == 0  ? "" : " || " );
					cmd.append(browsers[i]);
					cmd.append(" \"");
					cmd.append(url);
					cmd.append("\" ");
				}

				rt.exec(new String[] { "sh", "-c", cmd.toString() });

			}
		}
		catch (IOException e){}
	}
}
