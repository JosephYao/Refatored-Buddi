<? include "./inc/header.php" ?>

<h1>News</h1>

<h2>Encryption Added</h2>
<p>
Data file encryption has been added in Development release 1.7.3.  If you are interested and able, I would greatly appreciate it if you could do some testing on the new program.  Just make sure to back up your data files first!
</p>

<h2>1.6.1 (September 10 2006)</h2>
<ul>
<li>Added Spanish translation (thanks to Diego Garcia for submitting this)</li>
<li>Fixed a bug which did not allow all categories to be shown in the transactions window, if there are too many defined</li>
</ul>

<h2>1.6.0 (September 5 2006)</h2>
<ul>
<li>
Stable release incorporating the 1.5.x development.  The biggest changes since 1.4 are in the preferences window, especially the localization options.  The currency symbol is now separate from the language file, which helps reduce the number of localizations I need to include.  Also, I changed the display format for the Transactions window, which should make for fewer bugs when long strings are entered.  For complete details of differences between 1.4 and 1.6, please see the 1.5 changelog.
</li>
</ul>

<h2>1.4.1 (August 19 2006)</h2>
<ul>
<li>Fixed a bug relating to Double rounding error when reading the value from JDecimalField.  This could result in some numbers (e.g., 19.74) which were impossible to enter.  Now it converts directly to long from String, eliminating any possible trouble with rounding errors.
</ul>

<h2>1.4.0 (August 17 2006)</h2>
<ul>
<li>Incorporated Scheduled Transactions into the Stable Version</li>
<li>Added Export to HTML to the Stable Version</li>
</ul>

<h2>Scheduled Transactions (August 14 2004)</h2>
<p>
Scheduled transactions have been added to Development version 1.3.3. This means that repetitive transactions (putting aside $20 a month for a rainly day, paying weekly bills, etc) can be automated. 
</p><p> 
This new feature comes at the cost of changing the data format - you can use your old data in the new version, but once modified, you cannot go back. Please make backups of your data files before you run this version. 
</p><p> 
I would greatly appreciate all the feedback and bug reports that I can get on this new feature before I include it in the stable version. Feel free to use the tracker system, or just email me. 
</p>

<h2>1.2.1 (August 10 2006)</h2>
<ul>
<li>Added Norwegian translation (thanks to Gudbrand Hegge)</li>
<li>Fixed incorrect translation key</li>
<li>Updated some items in en-US localization</li>
</ul>

<h2>1.2.0 (August 6 2006)</h2>
<ul>
<li>New official stable version, with plenty of new features.  Some of the more important ones include new reports, searchable transactions, etc.  It is recommended for all current users of the Stable branch to upgrade to this new version.  There is a change to the preferences file format, which means that you will have to select the location of your data file again when you first start the program; however, the data file format is still unchanged.
</ul>

<h2>1.0.1 (July 7 2006)</h2>
<ul>
<li>Moved model (EMF) jars to source code.  This should make it easier for users to view / edit the source code, as you shouldn't have to modify Eclipse plugins</li>
</ul>


<h2>1.0.0 (June 23 2006)</h2>
<ul>
<li>Official stable version</li>
<li>Finalized German translations</li>
</ul>

<h2>1.0.0 RC5 (June 20 2006)</h2>
<ul>
<li>Added grayed out description fields to empty fields (Description, etc)</li>
<li>Fixed transactions window to allow smaller window sizes without cutting portions off</li>
<li>Fixed problem when recording without all fields, the fields already entered were deleted</li>
<li>Fixed translation problems</li>
<li>Added the total for expense / income on pie graph title</li>
<li>Changed to not show categories in certain graphs that don't have any activity for this time period.</li>
</ul>

<h2>1.0.0 RC 4 (June 19 2006)</h2>
<ul>
<li>Made the amount field larger in the transaction edit pane.</li>
<li>Sometimes the cents get rounded down, probably due to double rounding errors.  Replaced the JNumberField with a DecimalField class modified from http://rangiroa.essi.fr/cours/turorial%20java/uiswing/components/textfield.html.  I moved the double -> long conversion to this class, as well.</li>
<li>Maximum value for decimalField was about 200,000.00, due to int overflow errors; this is fixed, and it is now in the hundred trillions.</li>
<li>Changed order of fields on the transactions list screen.  Now it is different from the editable transaction pane, but it seems to flow better.</li>
<li>Added German translation (many thanks to Torsten Hinsche).</li>
</ul>

<h2>1.0.0 RC 3 (June 8 2006)</h2>
<ul>
<li>The third release cantidate for 1.0 stable.  Please report any bugs that you may find.</li>
<li>Fixed reports screen if there was many categories (scroll bar did not work before)</li>
<li>Misc. UI improvements</li>
</ul>

<h2>1.0 RC 2 (June 6 2006)</h2>
<ul>
<li>The second release cantidate for 1.0 stable.  Please report any bugs that you may find.</li>
<li>Misc. UI improvements</li>
</ul>

<h2>1.0 RC 1 (June 4 2006)</h2>
<ul>
<li>The first release cantidate for 1.0 stable.  Please report any bugs that you may find.</li>
<li>Warn if you have unsaved changes to a transaction, and you click on a different one.</li>
<li>Changed the transaction edit screen's format to be (hopefully) layed out in a more logical manner.</li>
<li>Misc. UI improvements</li>
<li>Misc. changes to file layout structure, to prepare for translations.  Put all help documentation (both on the website and offline) under a subfolder matchine the language name (e.g., Help/en/index.html).</li>
<li>Change to the preferences file structure.  Buddi will automatically create a new file, but you will lose your preferences (such as data file location, language, screen positions, etc).  It's all OK; don't panic.</li>
</ul>

<h2>RSS Support Added (May 30 2006)</h2>
<ul>
<li>
I have included a link to SourceForge's RSS syndication at the bottom of the website.  This link is for syndication of file releases; on Buddi's sourceforge project page, there are other RSS links included which allow you to monitor other features.
</li>
</ul>

<h2>0.95 Beta (May 30 2006)</h2>
<ul>
<li>Added local help documents</li>
<li>Each window remembers size / screen position on close</li>
<li>Misc. UI improvements</li>
<li>Automatically creates a backup file each day on the first run of the day.</li>
<li>Added menu options to create a new data store and restore from a previous backup.</li>
</ul>

<h2>0.91 Beta (May 26 2006)</h2>
<ul>
<li>Interim bugfix release, to fix display problem introduced with 0.9.  Windows (and possibly all non-Macintosh Java VMs) did not like the TreeCellRenderer that I included in 0.9, and the accounts and categories lists would not display.
</li>
</ul>

<h2>0.9 Beta (May 25 2006)</h2>
<ul>
<li>Added printing to all windows: Accounts, Categories, Transactions, Graphs, and Reports</li>
<li>Completed online help documentation (http://buddi.sourceforge.net)</li>
<li>Many minor improvements to UI</li>
</ul>

<h2>0.8 Beta (May 24 2006) - First public release.</h2>
<ul>
<li>Includes ability to edit accounts, categories, and budget amounts</li>
<li>Can show reports and graphs on net worth, expenses, etc.</li>
<li>Should be stable enough for everday use, although if there are changes to the data format in the future, you may have to convert the data file.</li>
</ul>

<? include "./inc/footer.php" ?>
