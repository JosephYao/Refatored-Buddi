Buddi Offline Help

Information:
By default, when you click on Help, Buddi will open a browser window pointed
to the official Buddi web page, http://buddi.sourceforge.net.  This will
guarantee that you always see the most recent version; unless you have
special requirements, I recommend that you just use the online version.

However, some people do not have Internet access at all times, but still
would like to view the documentation.  For these people, I have provided an
offline help bundle.  If Buddi sees these local help files, it will open
them instead of the online version when you request help.



Quickstart:
Unzip the Offline_Help-VERSION.zip file.  Put the resulting Help directory 
into Buddi's working dorectory.  You can find the working directory as 
follows:

	Macintosh OS X:
Ctrl-click on the Buddi.app application bundle, and select 'Show package 
contents'.  Navigate to Contents -> Resources -> Java, and copy the Help 
directory here.

	Windows:
This is probably the same location as your Buddi.exe file.  However, you can 
configure the working directory with the shortcut used to launch Buddi.  If
this does not work, look to the Troubleshooting section to find your working
directory.

	Linux / Other:
Like in Windows, this is usually the same directory as Buddi.jar resides in.
Sometimes this can change depending on the procedure you used to start Buddi;
if this location does not work, look to the Troubleshooting section.



Troubleshooting:
There are a couple of ways in which you can find your working directory with 
Buddi.  The easiest one is to view the debugging information, by running
Buddi with the flag '-v 5'.

Another way to see where your working directory is, is to edit a language file
and see where the resulting Languages folder shows up.
