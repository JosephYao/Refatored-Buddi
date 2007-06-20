<? include "./inc/header.php" ?>

<h1>Frequently Asked Questions</h1>

<h2>How do I install Buddi?</h2>
<p>
There is no need to install Buddi; it is a standalong executable.  Just copy it to where ever you want it to stay (I recommend C:\Program Files\Buddi\ if you are on Windows, or /Applications if you are on a Mac, but you can run it from anywhere).  On its first execution, it will create preference files (see 'How do I uninstall Buddi?' for the location of these files).  That's all!
</p>

<p>
On Debian / Ubuntu, I have created a specal installer pakage which puts Buddi in the applications menu under the Office applications.  To use this, simply <a href='http://buddi.sourceforge.net/buddi.deb'>download the Debian package</a> from the web site.  You can then either open it with the GDebi Package Instaler, or run from the console 'dkpg -i Buddi-version.deb', where 'version' is the version number of the file.
</p>

<p>
Note that in all cases, you need Sun's Java VM installed.  On Windows, Buddi will prompt you for this if you don't have it.  On Debian / Ubuntu, if you have the Multiverse / Universe repositories included in your Sources.list, GDebi will install Java and its associated programs automatically.  On other platforms, you will have to ensure that you have Java 1.5 yourself.
</p>

<h2>Will Buddi run on my Operating System?</h2>
<p>
Probably yes.  If you have a modern operating system with a copy of Sun's Java Virtual Machine (version 1.5 or higher), it should work fine.  This includes most recent versions of Windows, Macintosh OS X 10.4, most Linux distributions, Solaris, and possibly more.  Please consult the help files for your given operating system if you are unsure whether it supports Java 1.5 or not.
</p>

<h2>Will Buddi work on OS X 10.3 or earlier versions?</h2>
<p>
Unfortunately not.  Apple has decided not to release Java 1.5 for versions of OS X previous to Tiger.  I have looked into backporting Buddi, but because of some of the advanced features of Java 1.5 that I use, such as the encryption routines, I cannot run it on previous versions of Java.
</p>

<h2>How do I launch the program?</h2>
<p>
That depends on what operating system you are running.  If you are in Windows, simply double click on the Buddi.jar file.  If you are on a Macintosh, double click on the Buddi application bundle.  If you are on Linux or some other platform where the association of .jar to the Java VM has not been set up, type 'java -jar Buddi.jar' on the command line.
</p>


<h2>How do I uninstall Buddi?</h2>
<p>
Since I don't use an installer for most distributions, and I do not write anything to the registry, you don't need an uninstaller program.  To uninstall, you can simply remove the Buddi executable (either .exe, .jar, or .app depending on your operating system).
</p>
<p>
The only files which Buddi writes to the hard drive are your data files (Data.*.buddi), and the Preferences file (prefs.xml).  You chose the location of the data files; the preferences file is located differently depending on your operating system.  If you are running on Windows, it will be in C:\Documents and Settings\&lt;user name&gt;\Application Data\Buddi.  If you are running on a Mac, it will be in /Users/&lt;user name&gt;/Library/Application Support/Buddi.  If you are on a different operating system, it will be under ~/.buddi.  Regardless of where it is, you can remove the entire Buddi / .buddi folder if you desire.
</p>
<p>
On Debian, you can use the command 'apt-get remove buddi' to remove it.
</p>
<p>
If you choose to not use Buddi after trying it for a while, I would like to know why.  Please drop me an email, or leave a post on the forums, so that I can improve Buddi for future users.
</p>

<h2>How can I password protect / encrypt my data?</h2>
<p>
John Didion has graciously created the code which performs this function (very elegantly, I may add) using an AES cipher.  It is included in both the Stable and Development branches.
</p>
<p>
When using encryption, MAKE SURE you don't forget your password.  You CANNOT read your data file without it.
</p>

<h2>I forgot my password!  What can I do now?</h2>
<p>
Try very hard to remember it.  Other than that, there is nothing you can do.  There are no back doors on the encryption system.
</p>

<h2>But I had 2 years worth of data in there!  Are you sure I can't get in?!?</h2>
<p>
Yes, I am sure.  If you are prone to forget passwords, it may be best to not encrypt the data file.
</p>

<h2>How to I make an account to accumulate money over multiple months?</h2>
<p>
This can be accomplished via Prepaid Accounts, implemented via a Credit account.  Please see <a href='./prepaid.php'>Prepaid Accounts</a> for details.
</p>

<h2>Whenever I restart Buddi, it says the data file is corrupt.  What is happening?</h2>
<p>
The exact error message is 'There was  a problem reading the data file: Data.buddi The data file exists, but it appears to be corrupted. Do  you want to overwrite, and create a new file? All information currently in the file will be lost.'
</p>
<p>
This means that there was something wrong with your data file.  I have only seen a couple of people who have experienced this error on recent versions.  If you have the unfortunate chance of being one of these, please contact me - I am trying to figure out what causes this error, and I will need to talk with you about it.
</p>
<p>
Even if you experience this error message, all is not lost.  By default, Buddi keeps 10 backup files containing your data from previous 10 sessions.  For the most part, the most recent session (Data.0.buddi) has a good version of the data.  To revert to a previous good data file, you can do the following:
<ol>
<li>With Buddi not running, rename Data.buddi to Data.buddi.corrupted.  This will let me try to troubleshoot the cause of the problem later.</li>
<li>Start Buddi.  Since it does not find a data file when it start, it will automatically create a new one.  It will prompt you if you want to encrypt the data file; just hit No here.</li>
<li>Once you are in the Buddi main window, click File -> Restore From Backup File.  It will prompt you for the file to restore from.  Select the backup file Data.0.buddi, and hit OK.  This will copy the contents of the backup file Data.0.buddi to the new (currently blank) data file Data.buddi, and attempt to open it.</li>
</ol>
</p>
<p>
This should work; if it does not, try repeating these steps again, but using Data.X.buddi instead of Data.0.buddi (where X is the number of the backup file; the higher the numbers, the further in the past the backup is from).
</p>


<h2>Can I use Buddi for commercial activities?</h2>
<p>
Sure thing!  Buddi is released under the GNU General Public License, which is completely compatible with being used in businesses.
Of course, if you do make money from using Buddi, I would hope that you strongly consider sending me a donation.
</p>


<h2>Why doesn't Buddi do X?  I want it to do X!</h2>
<p>
Drop me an email, and I will see what I can do.  Many features currently in Buddi originated from requests from people who tried it, and wanted a little bit more.
</p>
<p>
That being said, I am quite busy with other things in my life (family, job, etc), and cannot guarantee that all suggestions will be implemented.  If you really want something done, and I am taking too long you can try <a href="http://sourceforge.net/donate/index.php?group_id=167026">bribing me with a donation</a>, and I will see about putting some more effort into it. 8-)  Even better, you can do it yourself and send me a patch.
</p>
<p>
Of course, I will not implement features that distract from the main purpose of the program - to provide a simple budgeting program for non-accountants.  If you want something that is geared more towards professional accountants, try something like GNUCash.
</p>

<h2>What's up with your stupid version numbers?</h2>
<p>
I use a versioning scheme with four numbers, in the form X.Y.F.B, where X is the major release, Y is the minor version, F is the Feature number, and B is the bugfix number.  Even minor versions (0, 2, 4...) are considered stable, while odd minor numbers are considered development code.  The Bugfix number is incremented if the new version only contains bug fixes, without any new features; the Feature number is incremented if there are new features, as well.
</p>
<p>
For instance the first stable version was 1.0.0.0; the first bugfix for the stable branch was 1.0.0.1.  The second feature release for the 2.4 branch is 2.4.2.0, and so forth.
</p>
<p>
Any particular stable branch will receive no new major features, but will continue to benefit from bugfixes, while the development branch will continue with all the major development.  Once the development version is considered complete, it will become the next stable version; for instance, development version 1.1.6.0 became stable version 1.2.0.0.
</p>
<p>
The terms 'Stable' and 'Development' may be misleading - any version which I release has passed at least rudimentary testing, and I consider it to be pretty good.  In fact, I regularly use the development versions for my own finances.  However, there may be file format changes, or minor bugs, which are introduced in the development versions, which would not be in the stable version.
</p>
<p>
If you don't like downloading new versions every few weeks, then use the stable version.  If you want to always keep up to date with current features, use the development version.
</p>

<hr>

<p>
I'm sure that there are many more questions than this that people have; if you're stuck on something, feel free to <a 
href='&#109;&#097;&#105;&#108;&#116;&#111;:&#119;&#121;&#097;&#116;&#116;&#046;&#111;&#108;&#115;&#111;&#110;&#064;&#103;&#109;&#097;&#105;&#108;&#046;&#099;&#111;&#109;'>ask me</a>.
</p>

<? include "./inc/footer.php" ?>
