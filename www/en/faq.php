<? include "./inc/header.php" ?>

<h1>Frequently Asked Questions</h1>

<h2>How do I launch the program?</h2>
<p>
That depends on what operating system you are running.  If you are in Windows, simply double click on the Buddi.jar file.  If you are on a Macintosh, double click on the Buddi application bundle.  If you are on Linux or some other platform where the association of .jar to the Java VM has not been set up, type 'java -jar Buddi.jar' on the command line.
</p>

<h2>How can I password protect / encrypt my data?</h2>
<p>
John Didion has graciously sent me a patch which performs this function (very elegantly, I may add) using an AES cipher.  As of now, it is included in both the Stable and Development branches.
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
The exact error message is 'There was  a problem reading the data file: Data.buddi The data file exists, but it appears to be corrupted. Do  you want to overwrite, and create a new file? All information  currently in the file will be lost.'
</p>
<p>
On old versions, this was caused when there are special characters included in the data files (whether it be with the Account names, Transaction fields, etc).  This has been fixed in version 1.3.1 Development / 1.2.1 Stable.
</p>
<p>
In newer versions, this should not happen at all.  If it happens to you, please contact me and let me know the details.
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
I use a versioning scheme similar to the Linux kernels: X.Y.Z, where X is the major release, Y is the minor version, and Z is the bugfix number.  Even minor versions (0, 2, 4...) are considered stable, while odd minor numbers are considered development code.  For instance the first stable version was 1.0.0; the first bugfix for the stable branch was 1.0.1, etc.
</p>
<p>
Any particular stable branch will receive no new features, but will continue to benefit from bugfixes, while the development branch will continue with all the major development.  Once the development version is considered complete, it will become the next stable version; for instance, development version 1.1.6 became stable version 1.2.0.
</p>
<p>
The terms 'Stable' and 'Development' may be misleading - any version which I release has passed at least rudimentary testing, and I consider it to be pretty good.  In fact, I regularly use the development versions for my own finances.  However, there may be file format changes, or minor bugs, which are introduced in the development versions, which would not be in the stable version.
</p>
<p>
If you don't like downloading new versions every few weeks, then use the stable version.  If you want to always keep up to date with current features, use the development version.
</p>

<h2>Export Reports to HTML doesn't work for me</h2>
<p>
If, from a Report window, you click on Export to HTML and it does not do anything, make sure that there are no spaces in the path to your data store (where Data.buddi is kept).  There seems to be a problem with the package that I use to open the web browser, which doesn't like spaces in the path name.  I am not aware of a solution at this time; as a workaround, you can manually open the .html file (is should be called report_XXX.html, in the same directory as the data store).  Note that this is a temporary file, and will be deleted when Buddi exits.
</p>

<h2>Why doesn't this stupid program work?</h2>
<p>
You need to have Java 1.5 installed for Buddi to work. If you are running OS X, you need to have Tiger installed and the <a href="http://www.apple.com/support/downloads/java2se50release1.html">Java 1.5 update installed</a>. If you are running on a different platform, please consult the documentation for that system.
</p>


<hr>

<p>
I'm sure that there are many more questions than this that people have; if you're stuck on something, feel free to <a 
href='&#109;&#097;&#105;&#108;&#116;&#111;:&#119;&#121;&#097;&#116;&#116;&#046;&#111;&#108;&#115;&#111;&#110;&#064;&#103;&#109;&#097;&#105;&#108;&#046;&#099;&#111;&#109;'>ask me</a>.
</p>

<? include "./inc/footer.php" ?>
