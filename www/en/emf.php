<? include "./inc/header.php" ?>

<h1>EMF Mini Tutorial</h1>

<p>
This mini tutorial is to help you get up to speed with how I have used EMF (the Eclipse Modeling Framework) when creating the data models for Buddi.  For a more in-depth look at what EMF is, how it works, etc, please see some of the official documentation for it (listed below).
</p>


<p>
This page is divided into three main sections: What software to use to get up an running; the workflow for a typical model editing operation, and some things to look out for when editing the model.
</p>

<h2>What software to get</h2>

<p>
I use a combination of Eclipse, ArgoUML, and some Eclipse plugins (to ease the conversion between formats):
</p>

<ul>
<li><a href='http://www.eclipse.org/'>Eclipse Homepage</a></li>
<li><a href='http://argouml.tigris.org/'>ArgoUML Homepage</a></li>
<li><a href='http://www.eclipse.org/emf/'>Eclipse EMF</a></li>
<li><a href='http://argo2ecore.sourceforge.net/'>Argo2Ecore Plugin</a> (download <a href='http://sourceforge.net/projects/argo2ecore'>here</a>)</li>
</ul>

<p>
I use Eclipse 3.2 for development; older versions will probably not work correctly.  (Also, the EMF version I am using is meant for Eclipse 3.2).
</p>

<p>
To install the Argo2Ecore plugin, just unzip the downloaded file to the plugins directory in Eclipse (when Eclipse is not running).
</p>

<p>
To install the EMF plugin, please see <a href='http://www.eclipse.org/emf/updates/'>the EMF Update manager site</a> for more details.
</p>

<h2>Workflow for Editing the Data Model</h2>

<p>
The existing data model is in the source, in the file model/Buddi.zargo.  Open this in ArgoUML, and you will see a UML diagram representing the data model.
</p>

<p>
Since the end result of this is to get code to store the data model in an XML file, I have had to modify the UML slightly from what it theoretically should look like.  You will notice, for instance, that there is one container object (DataModel), which contains Accounts, Categories, etc. objects.  These subobjects in turn contain lists of Account objects, Category objects, etc.  (I have included the 'container' objects to make the XML a little cleaner than it would oterhwise be).
</p>

<p>
On the left side, you will notice a list of classes used in the model (you may have to expand the package folders to see all of them).  Note that all objects used in the model must exist in the package org.homeunix.drummer.model - new objects are by default created outside of any package, so you must move them to this package before saving.  (Nothing will go horribly wrong if you forget this: all that will happen is that Argo2Ecore will throw an error and not convert the file, and you have to go back to ArgoUML again.)
</p>

<p>
For the sake of this tutorial, let's assume you just added a new field to the Account object: myField.  Save the model, export it to XMI (File -> Export to XMI), and put the exported file in the model directory along with the .zargo file.
</p>

<p>
You can now go to Eclipse, and look in the model folder.  If you have already created a .ecore and a .genmodel, you will have to delete these first.  Right click on the XMI file, and click on Convert to Ecore.  (If you don't see this, you have not installed the Argo2Ecore plugin).  This will make a Buddi.ecore file.  Right click on this one, and click New -> Other.  Under the Eclipse Modeling Framework folder, select the EMF Model option.  (If you don't see this, you have not installed the EMF plugin.)  Click next.  Click next again.  Ensure that Ecore model is selected, and click next again.  Click Load, and then next again.  Now hit Finish.  If all went well, you will see Buddi.genmodel open in your editing pane.  Right click on the root node (Buddi, in this case), and click Generate Model Code.
</p>

<p>
FYI, I have made the Account, Category, Type, and Transaction objects implement the comparable interface to allow sorting.  EMF doesn't seem to support this by default, so whenever I re-generate the model, I have to go to these three interfaces (located in org.homeunix.drummer.model.{Account|Category|Transaction|Type} and add the Comparable option to the class definition.  For Account and Category, I use Comparable&lt;Source&gt;; for Transaction, I use Comparable&lt;Transaction&gt;; for Type I use Comparable&lt;Type&gt;.  Look at the next paragraph for how to do this; if you don't do it properly, you will get compile-time errors in DataInstance and others.  (If anyone knows how to automate this, I would appreciate a note telling me how.  Since I don't edit the Data model much at all, I haven't bothered to look into it in any great depth.)
</p>

<p>
For Account and Category, you should use the following class definitions:<br>
public interface Category extends Source, Comparable&lt;Source&gt;<br>
public interface Account extends Source, Comparable&lt;Source&gt;<br><br>
For Transaction, you should use this one:<br>
public interface Transaction extends EObject, Comparable&lt;Transaction&gt;<br><br>
For Type, you should use this one:<br>
public interface Type extends EObject, Comparable&lt;Type&gt;
</p>

<p>
The vast majority of data access is done via the DataInstance class, located in org.homeunix.drummer.model.  (The exception to this is in the Transactions window: to allow the automatic updates to the windows, you must use the methods within the TransactionListModel in org.homeunix.drummer.view.model - a static instance of this class is kept in the TransactionsFrame class, and is called baseModel.)  DataInstance is a Singleton object, so you need to create / get a copy via the getInstance() method.  There are helper methods for most operations (get, add, remove).  It is recommended to use these when possible, instead of accessing the dataModel object directly.
</p>



<h2>Things to look out for</h2>

<p>
Nothing stands out to me right now - once people start working on this, and encounter issues, I will list them here.
</p>

<h2>Links</h2>

<ul>
<li><a href='http://www.eclipse.org/emf/'>The EMF Homepage</a></li>
<li><a href='http://dev.eclipse.org/viewcvs/indextools.cgi/*checkout*/org.eclipse.emf/doc/org.eclipse.emf.doc/references/overview/EMF.html'>EMF Overview</a> (nice documentation with information of methods other than the one I am using</li>
<li><a href='http://www.eclipse.org/emf/faq/faq.php'>EMF FAQ</a></li>
</ul>

<? include "./inc/footer.php" ?>
