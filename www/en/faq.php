<? include "./inc/header.php" ?>

<h1>Frequently Asked Questions</h1>

<h2>How do I run the program?</h2>
<p>
That depends on what operating system you are running.  If you are in Windows, simply double click on the Buddi.jar file.  If you are on a Macintosh, double click on the Buddi application bundle.  If you are on Linux or some other platform where the association of .jar to the Java VM has not been set up, type 'java -jar Buddi.jar' on the command line.
</p>

<h2>Why doesn't this stupid program work?</h2>
<p>
You need to have Java 1.5 installed for Buddi to work. If you are running OS X, you need to have Tiger installed and the <a href="http://www.apple.com/support/downloads/java2se50release1.html">Java 1.5 update installed</a>. If you are running on a different platform, please consult the documentation for that system.
</p>

<h2>How can I password protect / encrypt my data?</h2>
<p>
From within Buddi, you can't.  This is for a few of reasons:  
<ul>
<li>I wanted to keep this program as simple as possible</li>
<li>Generally homebrew encryption is not secure; the rule of thumb is to leave encryption to cryptographers</li>
<li>I feel that this is something that should be taken care of by the OS</li>
</ul>
</p>
<p>
That being said, most modern operating systems provide some functionality for encrypting specific folders.  If you are running on a Macintosh, you can use an encrypted disk image to store your data file.  Simply create the disk image from Disk Utility, put your data file inside of it, and tell Buddi to use the new location (usually something like '/Volumes/Buddi Data').  I personally use this method, in combination with an Automator script, to mount the encrypted disk when I launch Buddi.  Contact me if you want a copy of this simple script.
</p>
<p>
In Linux, you can do something similar with loopback images, although I have not looked into the details.  If you are running Linux, hopefully you are good enough at reading documentation to be able to piece this together yourself.
</p>
<p>
On Windows XP Professional, I believe that you can encrypt specific folders, although if I remember correctly, XP Home is crippled, and does not support this at all.  I personally don't run Windows, so you will have to find out how to do this yourself.
</p>
<p>
For those operating systems which do not support encryption, you can use the free program <a href='http://www.gnupg.org/'>GPG</a> to encrypt the directory in which the files exist, although this will require a little more work, and is beyond the scope of this FAQ.
</p>
<p>
If anyone has successfully done this on another operating system and wants to share their procedure, please send it to me and I will include it here.
</p>

<h2>Whenever I restart Buddi, it says the data file is corrupt.  What is happening?</h2>
<p>
The exact error message is 'There was  a problem reading the data file: Data.buddi The data file exists, but it appears to be corrupted. Do  you want to overwrite, and create a new file? All information  currently in the file will be lost.'
</p>
<p>
This is caused when there are special characters included in the data files (whether it be with the Account names, Transaction fields, etc).  This has been fixed in version 1.3.1 Development / 1.2.1 Stable.
</p>

<h2>Why doesn't Buddi do X?  I want it to do X!</h2>
<p>
Drop me a line, and I will see what I can do.  Many features currently in Buddi originated from requests from people who tried it, and wanted a little bit more.
</p>
<p>
That being said, I am quite busy with other things in my life (family, job, etc), and cannot guarantee that all suggestions will be implemented.  If you really want something done, and I am taking too long you can try <a href="http://sourceforge.net/donate/index.php?group_id=167026">bribing me with a donation</a>, and I will probably put some more effort into it. 8-)
</p>
<p>
Of course, I will not implement features that distract from the main purpose of the program - to provide a simple budgeting program for non-accountants.  If you want something that is geared more towards professional accountants, try something like GNUCash.
</p>

<h2>What's up with your stupid version numbers?</h2>
<p>
I use a versioning scheme similar to the Linux kernels: X.Y.Z, where X is the major release, Y is the minor version, and Z is the bugfix number.  Even minor versions (0, 2, 4...) are considered stable, while odd minor numbers are considered development code.  For instance the first stable version was 1.0.0; the first bugfix for the stable branch was 1.0.1, etc.
</p>
<p>
The stable branch will receive no new features, but will continue to benefit from bugfixes, while the development branch will continue to recieve major development.  Once the development version is considered complete, it will become the next stable version; for instance, development version 1.1.6 became stable version 1.2.0.
</p>
<p>
The terms 'Stable' and 'Development' may be misleading - any version which I release has passed at least rudimentary testing, and I consider it to be pretty good.  In fact, I use the development versions for my own finances.  However, there may be file format changes, or minor bugs, which are introduced in the development versions, which would not be in the stable version.
</p>
<p>
If you don't like downloading new versions every few weeks, then use the stable version.  If you want to always keep up to date with current features, use the development version.
</p>


<hr>

<p>
I've not gotten enough feedback yet to see what the most frequent questions are. If you're stuck on something, feel free to <a 
href='&#109;&#097;&#105;&#108;&#116;&#111;:&#119;&#121;&#097;&#116;&#116;&#046;&#111;&#108;&#115;&#111;&#110;&#064;&#103;&#109;&#097;&#105;&#108;&#046;&#099;&#111;&#109;'>ask me</a>.
</p>

<? include "./inc/footer.php" ?>
