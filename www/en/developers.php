<? include "./inc/header.php" ?>

<h1>Developer Information</h1>

<p>
If you are interested in adding to my project in any way, please do so!  The source is can be downloaded freely from <a href='http://sourceforge.net/projects/buddi'>http://sourceforge.net/projects/buddi</a>.  I use Eclipse with the EMF plugin for editing and compilation.  If you load the unpacked source into a new Eclipse project, everything should work just fine.
</p>

<p>
As of version 1.7.5, you can write report and / or graph plugins for Buddi and have them included in the Reports window.  To do this, implement ones of the classes org.homeunix.drummer.plugins.BuddiReportPlugin (for reports) or BuddiGraphPlugin (for graphs).  You can look at the source code in org.homeunix.drummer.plugins.{reports|graphs} for examples of this.  After you have compiled your code, include it on the classpath and start Buddi with the --plugins option.  This flag takes a comma separated (no whitespace) list of classes to attempt to add at startup.
</p>

<p>
If you are interested in translating Buddi into another language, there is just one file which you need to duplicate and modify: English.lang.  This file is embedded within the .jar file: you can either unzip the .jar, or download a copy from the Subversion repository.  Once you duplicate it, rename the file to the correct language, as written in that language, for instance Engligh, or Espanol.  If this is a specific localization, use the format Language_(Localization), for instance English_(US).  Do not use spaces, as this can cause problems sometimes.
</p>

<p>
The .lang file contains pairs of keywords and values, in the format 'KEYWORD=Value String'.  This file is used for all text within the program itself.  Simply replace the english value string with the equivalent text from the language you are translating into.
</p>

<p>
If there are special characters (represented by Unicode) in your translation, you will have to use the Java unicode escape sequences, in the form /uXXXX.  The program native2ascii, included with the Java SDK, should be able to automate this.  If you have questions, please ask me.
</p>

<p>
As of Buddi version 1.7.8, I embed the langauge files within the .jar; however, you can still use the Languages directory to use your custom language files immediately.  (Create a new folder Languages in the same directory as Buddi.jar (Buddi.exe on Windows).  If you send me a copy, I will include your language in the next release, so that everyone can enjoy it.
</p>

<p>
Once you have completed the translation, you can choose the new language from the <a href='./preferences.php'>Preferences</a> screen.
</p>

<? include "./inc/footer.php" ?>
