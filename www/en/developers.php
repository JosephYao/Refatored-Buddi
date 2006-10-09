<? include "./inc/header.php" ?>

<h1>Developer Information</h1>

<p>
If you are interested in adding to my project in any way, please do so!  The source is can be downloaded freely from <a href='http://sourceforge.net/projects/buddi'>http://sourceforge.net/projects/buddi</a>.  I use Eclipse with the EMF plugin for editing and compilation.  If you load the unpacked source into a new Eclipse project, everything should work just fine.
</p>

<p>
As of version 1.9.2, you can write four kinds of plugins: import, export, report, and graph.  For more information on this, please see the <a href='plugins.php'>Plugins</a> page.
</p>

If you are interested in translating Buddi into another language, there is just one file which you need to duplicate and modify: English.lang.  This file is embedded within the .jar file: you can either unzip the .jar, or download a copy from the Subversion repository.  Once you duplicate it, rename the file to the correct language, as written in that language, for instance Deutsch, or Espanol (not all operating systems can use Unicode for file names; it is better if you only use simple ASCII characters).  If this is a specific localization, use the format Language_(Localization), for instance English_(US).  Do not use spaces, as this can cause problems sometimes.  Remember to include the .lang at the end - otherwise, Buddi won't recognise it as a language file.
<p>
</p>

<p>
The .lang file contains pairs of keywords and values, in the format 'KEYWORD=Value String'.  This file is used for all text within the program itself.  Simply replace the english value string with the equivalent text from the language you are translating into.
</p>

<p>
If there are special characters (represented by Unicode) in your translation, you will have to use the Java unicode escape sequences, in the form /uXXXX.  The program native2ascii, included with the Java SDK, should be able to automate this.  If you have questions, please ask me.
</p>

<p>
Currently, I have embeded the langauge files within the .jar; however, you can still use the Languages directory to use your custom language files immediately.  (Create a new folder Languages in the same directory as Buddi.jar, or Buddi.exe on Windows.  On the Mac, there is a Languages directory within the application bundle under Contents -> Resources -> Java -> Languages).  Once you have completed the translation, you can choose the new language from the <a href='./preferences.php'>Preferences</a> screen.
</p>

<p>
All translations have been done by Buddi users - if you send me a copy of your translation, I will include your language in the next release, so that everyone can enjoy it.
</p>

<? include "./inc/footer.php" ?>
