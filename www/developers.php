<? include "./inc/header.php" ?>

<h1>Developer Information</h1>

<p>
If you are interested in adding to my project in any way, please do so!  The source is included with each distributed version in the source.zip file.  I use Eclipse with the EMF plugin for editing and compilation.  If you load the unzipped source into a new Eclipse project, everything should work just fine.
</p>

<p>
If you are interested in translating Buddi into another language, there is just one file which you need to duplicate and modify: en-US.lang, located in the Language directory.  (If you are running on Macintosh OS X, you will need to ctrl-click on the application package, select Show Package Contents, and go into the Contents -> Resources -> Java -> Languages folder.)  Once you duplicate it, rename the file to the correct language name, for instance en-US (for US Engligh),  or ja-JP (for Japanese).
</p>

<p>
The .lang file contains pairs of keywords and values, in the format 'KEYWORD=Value String'.  This file is used for all text within the program itself.  Simply replace the english value string with the equivalent text from the language you are translating into.
</p>

<p>
Once you have completed the translation, you can choose the new language from the <a href='/preferences.php'>Preferences</a> screen.
</p>

<? include "./inc/footer.php" ?>