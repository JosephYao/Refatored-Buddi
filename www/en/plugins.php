<? include "./inc/header.php" ?>

<h1>How to use Plugins</h1>

<p>
Plugins are small modules by a third party which expand Buddi's functionality.  Currently, there are four types of plugins - Import, Export, Report, and Graph.  Plugins are distributed in .jar files (functionally equivalent to a .zip file of Java programs).
</p>

<p>
To load a plugin into Buddi, simply go to the Preferences screen, under the Plugins tab.  Click on Add, and select the plugin's .jar file which you want to load.  When you hit OK, the plugins will be loaded.  It's as easy as that!
</p>

<p>
Once the plugin is loaded, you can access it by the File -> Import / Export menu (if it is an Import / Export plugin), or on the Reports tab in the main window (if it is a Report / Graph plugin).
</p>

<h1>How to make Plugins</h1>

<p>
Plugins are quite simple to make, assuming you have a programming background.  All you need to do is implement one of the plugin interfaces located in the package org.homeunix.drummer.plugins.intnerfaces.  The four plugin types are represented here by the files BuddiExportPlugin, BuddiImportPlugin, BuddiReportPlugin, and BuddiGraphPlugin.
</p>

<p>
There are a few methods which you need to implement to create the plugin.  These are pretty self explanatory - for instance, getDescription() is the description of the plugin (as seen on the Reports window), export() is the method which contains the third party used to export the data to another format.  For hints on how to do this, you can check out the built-in plugins, located in the org.homeunix.drummer.plugins.* directories.
</p>

<? include "./inc/footer.php" ?>
