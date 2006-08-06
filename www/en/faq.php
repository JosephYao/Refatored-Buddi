<? include "./inc/header.php" ?>

<h1>Frequently Asked Questions</h1>

<h2>How do I run the program?</h2>

<p>
That depends on what operating system you are running.  If you are in Windows, simply double click on the Buddi.jar file.  If you are on a Macintosh, double click on the Buddi application bundle.  If you are on Linux or some other platform where the association of .jar to the Java VM has not been set up, type 'java -jar Buddi.jar' on the command line.
</p>

<h2>Why doesn't this stupid program work?</h2>

<p>
You need to have Java 1.5 installed for Buddi to work. If you are running OS X, you need to have Tiger installed and the Java 1.5 update installed. If you are running on a different platform, please consult the documentation for that system.
</p>

<h2>What's up with your stupid version numbers!</h2>

<p>
I use a versioning scheme similar to the Linux kernels: X.Y.Z, where X is the major release, Y is the minor version, and Z is the bugfix number.  Even minor versions (0, 2, 4...) are considered stable, while odd minor numbers are considered development code.  For instance the first stable version is 1.0.0; the first bugfix will be 1.0.1, etc.  At the time of release, the code branched into two: the 1.0 branch, which will receive no new features, but will continue to benefit from bugfixes, and the 1.1 line, which will continue with major development.  Once it is considered complete, it will become 1.2.0, the next stable version, and so on.
</p>
<p>
The terms 'Stable' and 'Development' may be misleading - any program which I release has passed at least rudimentary testing, and I consider it to be pretty good.  In fact, I use the development versions for my own finances.  However, there may be file format changes, or minor bugs, which are introduced in the development versions, which would not be in the stable version.
</p>

<h2>Whenever I restart Buddi, it looses all the information, giving me the message "There was  a problem reading the data file: Data.buddi The data file exists, but it appears to be corrupted. Do  you want to overwrite, and create a new file? All information  currently in the file will be lost.".  What is happening?
<p>
This can happen if you use special characters for the account or category names.  I am not aware of a solution at this time; a workaround is to avoid using special characters in the account or category names.
</p>

<h2>Why doesn't Buddi do {X}?  I want it to do {X}!</h2>

<p>
Drop me a line, and I will see what I can do.  Many features currently in Buddi originated from requests from people who tried it, and wanted a little bit more.
</p>
<p>
That being said, I am quite busy with other things in my life (family, job, etc), and cannot guarantee that all suggestions will be implemented.  If you really want something done, and I am taking too long you can try <a href="http://sourceforge.net/donate/index.php?group_id=167026">bribing me with a donation</a>, and I will probably put some more effort into it. 8-)
</p>
<p>
Of course, I will not implement features that distract from the main purpose of the program - to provide a simple budgeting program for non-accountants.  If you want something that is geared more towards professional accountants, try something like GNUCash.
</p>


<p>
I've not gotten enough feedback yet to see what the most frequent questions are. If you're stuck on something, feel free to <a 
href='&#109;&#097;&#105;&#108;&#116;&#111;:&#119;&#121;&#097;&#116;&#116;&#046;&#111;&#108;&#115;&#111;&#110;&#064;&#103;&#109;&#097;&#105;&#108;&#046;&#099;&#111;&#109;'>ask me</a>.
</p>

<? include "./inc/footer.php" ?>
