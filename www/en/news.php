<? include "./inc/header.php" ?>

<h1>News</h1>

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
