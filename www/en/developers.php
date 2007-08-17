<? include "./inc/header.php" ?>

<h1>Developer Information</h1>

<p>
If you are interested in adding to my project in any way, please do so!  The source is can be downloaded freely from <a href='http://sourceforge.net/projects/buddi'>http://sourceforge.net/projects/buddi</a>.  I use Eclipse with the EMF plugin for editing and compilation.  If you load the unpacked source into a new Eclipse project, everything should work just fine.  For more information on the software needed to do this, please see my short <a href='emf.php'>EMF Tutorial</a> and how it relates to Buddi.
</p>

<p>
It has been pointed out to me that sometimes Eclipse and associated programs are not as simple as I make them out to be in the above paragraph, and that sometimes it does not 'just work'.  To help in those situations, Buddi is able to be compiled just with the command line tool javac.  While this will not create a single .jar file for distribution, it will at least allow would-be developers to play with it, and test their changes.  To compile from the command line (on a *nix environment; for Windows, you will have to modify this slightly), you will need to do the following:
</p>

<ul>
<li>
<p>
Check out the newest source from SVN:
</p>
<p class="code">
svn co https://buddi.svn.sf.net/svnroot/buddi/trunk 
</p>
</li>

<li>
<p>
Go to the trunk/src directory:
</p>
<p class="code">
cd trunk/src 
</p>
</li>

<li>
<p>
Execute the following command: 
</p>
<p class="code"> 
javac -cp ../lib/BrowserLauncher2-10rc4.jar:\<br/>
../lib/MRJAdapter-1.0.9.jar:\<br/>
../lib/emf/emf.common_2.2.1.v200609210005.jar:\<br/>
../lib/emf/emf.ecore.xmi_2.2.1.v200609210005.jar:\<br/>
../lib/emf/emf.ecore_2.2.1.v200609210005.jar:\<br/>
../lib/jcalendar-1.3.2.jar:../lib/jcommon-1.0.5.jar:\<br/>
../lib/jfreechart-1.0.1.jar:../lib/moss-0.5.3.jar:\<br/>
../lib/swingx-2006_10_27.jar:\<br/>
../lib/truefilter-1.2.jar:. \<br/>
org/homeunix/drummer/Buddi.java
</p>
<p>
This should compile without any errors. If you see any problems here, please let me know. 
</p>
</li>

<li>
<p>
Before you run Buddi, you need to make a symlink of the Languages directory in the current directory (to access the translations): 
</p>
<p class="code">
ln -s ../Languages . 
</p>
</li>


<li>
<p>
Still in that same directory, you can run Buddi with the command (again, all on one line): 
</p>
<p class="code"> 
java -cp ../lib/BrowserLauncher2-10rc4.jar:\<br/>
../lib/MRJAdapter-1.0.9.jar:\<br/>
../lib/emf/emf.common_2.2.1.v200609210005.jar:\<br/>
../lib/emf/emf.ecore.xmi_2.2.1.v200609210005.jar:\<br/>
../lib/emf/emf.ecore_2.2.1.v200609210005.jar:\<br/>
../lib/jcalendar-1.3.2.jar:../lib/jcommon-1.0.5.jar:\<br/>
../lib/jfreechart-1.0.1.jar:../lib/moss-0.5.3.jar:\<br/>
../lib/swingx-2006_10_27.jar:\<br/>
../lib/truefilter-1.2.jar:. \<br/>
org.homeunix.drummer.Buddi 
</p>
</li>
</ul>

<h2>Plugins</h2>
<p>
As of version 1.9.2, you can write four kinds of plugins: import, export, report, and graph.  For more information on this, please see the <a href='plugins.php'>Plugins</a> page.
</p>


<h2>Translations</h2>
<p>
For information about translating Buddi, please see the <a href='translations.php'>Translations</a> page.
</p>

<? include "./inc/footer.php" ?>
