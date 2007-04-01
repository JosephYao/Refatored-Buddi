/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.plugins;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.jar.JarEntry;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.roydesign.ui.JScreenMenuItem;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.MainBuddiFrame;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.plugins.BuddiPluginHelper.DateChoice;
import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;
import org.homeunix.drummer.plugins.BuddiPluginImpl.BuddiExportPluginImpl;
import org.homeunix.drummer.plugins.BuddiPluginImpl.BuddiGraphPluginImpl;
import org.homeunix.drummer.plugins.BuddiPluginImpl.BuddiImportPluginImpl;
import org.homeunix.drummer.plugins.BuddiPluginImpl.BuddiReportPluginImpl;
import org.homeunix.drummer.plugins.interfaces.BuddiExportPlugin;
import org.homeunix.drummer.plugins.interfaces.BuddiGraphPlugin;
import org.homeunix.drummer.plugins.interfaces.BuddiImportPlugin;
import org.homeunix.drummer.plugins.interfaces.BuddiPanelPlugin;
import org.homeunix.drummer.plugins.interfaces.BuddiPlugin;
import org.homeunix.drummer.plugins.interfaces.BuddiReportPlugin;
import org.homeunix.drummer.prefs.Plugin;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.AbstractFrame;
import org.homeunix.drummer.view.components.CustomDateDialog;
import org.homeunix.thecave.moss.jar.JarLoader;
import org.homeunix.thecave.moss.util.Log;


public class PluginFactory<T extends BuddiPlugin> {

	public static List<JScreenMenuItem> getExportMenuItems(final AbstractFrame frame){
		List<JScreenMenuItem> exportMenuItems = new LinkedList<JScreenMenuItem>();

		PluginFactory<BuddiExportPlugin> factory = new PluginFactory<BuddiExportPlugin>();
		List<BuddiExportPlugin> exportPlugins = factory.getPluginObjects(BuddiExportPluginImpl.class);

		//Iterate through each plugin, and create a menu item for each.
		for (BuddiExportPlugin plugin : exportPlugins) {
			JScreenMenuItem menuItem = new JScreenMenuItem(plugin.getDescription());

			//This is where the user's custom code is actually run
			addExportActionListener(plugin, menuItem, frame);
//			menuItem.addUserFrame(ExportHTML.class);
//			if (plugin.getCorrectWindows() != null && plugin.getCorrectWindows().length > 0){
//				for (Class c : plugin.getCorrectWindows()){
//					if (c != null)
//						menuItem.addUserFrame(c);
//				}
//			}

			exportMenuItems.add(menuItem);
		}

		return exportMenuItems;
	}

	public static List<JScreenMenuItem> getImportMenuItems(final AbstractFrame frame){
		List<JScreenMenuItem> importMenuItems = new LinkedList<JScreenMenuItem>();

		PluginFactory<BuddiImportPlugin> factory = new PluginFactory<BuddiImportPlugin>();
		List<BuddiImportPlugin> importPlugins = factory.getPluginObjects(BuddiImportPluginImpl.class);

		//Iterate through each plugin, and create a menu item for each.
		for (BuddiImportPlugin plugin : importPlugins) {
			JScreenMenuItem menuItem = new JScreenMenuItem(plugin.getDescription());

			//This is where the user's custom code is actually run
			addImportActionListener(plugin, menuItem, frame);

			importMenuItems.add(menuItem);
		}

		return importMenuItems;
	}

	public static List<JPanel> getReportPanelLaunchers(){
		return getPanelLaunchers(BuddiReportPluginImpl.class);
	}

	public static List<JPanel> getGraphPanelLaunchers(){
		return getPanelLaunchers(BuddiGraphPluginImpl.class);
	}

	private static List<JPanel> getPanelLaunchers(Class<? extends BuddiPanelPlugin> pluginType){
		List<JPanel> panelItems = new LinkedList<JPanel>();

		PluginFactory<BuddiPanelPlugin> factory = new PluginFactory<BuddiPanelPlugin>();
		List<BuddiPanelPlugin> panelPlugins = factory.getPluginObjects(pluginType);

		//Iterate through each plugin, and create a menu item for each.
		for (BuddiPanelPlugin plugin : panelPlugins) {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

			//Select the correct options for the dropdown, based on the plugin
			Vector<DateChoice> dateChoices;
			if (plugin.getDateRangeType().equals(DateRangeType.INTERVAL))
				dateChoices = BuddiPluginHelper.getInterval();
			else if (plugin.getDateRangeType().equals(DateRangeType.START_ONLY))
				dateChoices = BuddiPluginHelper.getStartOnly();
			else if (plugin.getDateRangeType().equals(DateRangeType.END_ONLY))
				dateChoices = BuddiPluginHelper.getEndOnly();
			else
				dateChoices = new Vector<DateChoice>();

			//Get the combobox 
			final JComboBox dateSelect = new JComboBox(dateChoices);
			dateSelect.setPreferredSize(new Dimension(Math.max(150, dateSelect.getPreferredSize().width), dateSelect.getPreferredSize().height));

			//Add the launchers
			addPanelActionListener(plugin, dateSelect);

			panel.add(new JLabel(Translate.getInstance().get(plugin.getDescription())));
			panel.add(dateSelect);

			panelItems.add(panel);
		}

		return panelItems;
	}

	/**
	 * Returns a list of all the class names (in filesystem format, 
	 * i.e. org/homeunix/drummer/Test.class) of classes which
	 * implement the BuddiPlugin interface.  This is a relatively
	 * expensive operation - we have to instantiate each class
	 * within the jar file to be sure it is the correct type -
	 * so use this method with care. 
	 * @param jarFile
	 * @return
	 */
	public static Vector<String> getAllPluginsFromJar(File jarFile){
		Vector<String> classNames = new Vector<String>();

		for (JarEntry entry : JarLoader.getAllClasses(jarFile)) {
			try {
				Log.debug("Loading " + entry.getName() + " (" + filesystemToClass(entry.getName()) + ")");
				Object o = JarLoader.getObject(jarFile, filesystemToClass(entry.getName()));
				if (o instanceof BuddiPlugin) {
					Log.info("Found BuddiPlugin: " + entry.getName());
					classNames.add(entry.getName());
				}
				else {
					Log.info("Not of type BuddiPlugin: " + o);
				}
			}
			catch (Exception e){
				Log.error(e);
			}
		}

		return classNames;
	}


	/**
	 * Returns a list of plugin objects, after instantiating and checking 
	 * type.
	 * @param pluginType The class of the type of objects required.  This is needed to allow type checking on a generic.
	 * @return List of plugin objects of type pluginType.
	 */
	@SuppressWarnings("unchecked")
	private List<T> getPluginObjects(Class<? extends T> pluginTypeClass){
		List<T> pluginEntries = new LinkedList<T>();

		Object pluginType;
		try{
			pluginType = pluginTypeClass.newInstance();
		}
		catch(Exception e){
			Log.critical("Cannot create plugin type object to compare instances against.  I cannot load any plugins:" + e);
			return pluginEntries;
		}

		try{
			for (String pluginClass : Const.BUILT_IN_PLUGINS) {
				Object pluginObject = Class.forName(pluginClass).newInstance();

				if (pluginType instanceof BuddiExportPlugin
						&& pluginObject instanceof BuddiExportPlugin){
					if (Const.DEVEL) Log.info("Adding Export Plugin " + pluginObject);
					pluginEntries.add((T) pluginObject);
				}
				else if (pluginType instanceof BuddiImportPlugin
						&& pluginObject instanceof BuddiImportPlugin){
					if (Const.DEVEL) Log.info("Adding Import Plugin " + pluginObject);
					pluginEntries.add((T) pluginObject);
				}
				else if (pluginType instanceof BuddiReportPlugin
						&& pluginObject instanceof BuddiReportPlugin){
					if (Const.DEVEL) Log.info("Adding Report Plugin " + pluginObject);
					pluginEntries.add((T) pluginObject);
				}
				else if (pluginType instanceof BuddiGraphPlugin
						&& pluginObject instanceof BuddiGraphPlugin){
					if (Const.DEVEL) Log.info("Adding Graph Plugin " + pluginObject);
					pluginEntries.add((T) pluginObject);
				}
			}
		}
		catch(Exception e){
			Log.warning("Loading built in plugins: " + e);
		}

		// TODO Currently we load every jar separately.  This can potentially be slow - perhaps we should test first.
		Vector<Plugin> badPlugins = new Vector<Plugin>();
		for (Object o1 : PrefsInstance.getInstance().getPrefs().getLists().getPlugins()) {
			if (o1 instanceof Plugin){
				Plugin plugin = (Plugin) o1;

				File jarFile = new File(plugin.getJarFile());
				Translate.getInstance().loadPluginLanguages(jarFile, PrefsInstance.getInstance().getPrefs().getLanguage());
				String className = plugin.getClassName();
				className = filesystemToClass(className);
				try{
					Object pluginObject = JarLoader.getObject(jarFile, className);

					if (pluginType instanceof BuddiExportPlugin
							&& pluginObject instanceof BuddiExportPlugin){
						pluginEntries.add((T) pluginObject);
					}
					else if (pluginType instanceof BuddiImportPlugin
							&& pluginObject instanceof BuddiImportPlugin){
						pluginEntries.add((T) pluginObject);
					}
					else if (pluginType instanceof BuddiPanelPlugin
							&& pluginObject instanceof BuddiPanelPlugin){
						pluginEntries.add((T) pluginObject);
					}
				}
				catch(Exception e){
					Log.warning("Loading custom plugins: " + e);
					badPlugins.add(plugin);
				}
			}
		}

		//If there were any bad plugins, we remove them.
		if (badPlugins.size() > 0){
			Log.warning("Removing bad plugins: " + badPlugins);
			PrefsInstance.getInstance().getPrefs().getLists().getPlugins().removeAll(badPlugins);
		}

		return pluginEntries;

	}

	private static void addExportActionListener(final BuddiExportPlugin plugin, JScreenMenuItem menuItem, final AbstractFrame frame){
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (plugin instanceof BuddiExportPlugin){
					File file = null;
					BuddiExportPlugin exportPlugin = (BuddiExportPlugin) plugin;

					if (exportPlugin.isPromptForFile()){
						//Create a file chooser to give plugin a file. 
						final JFileChooser jfc = new JFileChooser();
						jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

						//Sets the title
						if (exportPlugin.getFileChooserTitle() != null)
							jfc.setDialogTitle(exportPlugin.getFileChooserTitle());

						//Sets the filter
						if (exportPlugin.getFileFilter() != null)
							jfc.setFileFilter(exportPlugin.getFileFilter());

						if (jfc.showSaveDialog(MainBuddiFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
							if (jfc.getSelectedFile().isDirectory()){
								//Cannot select a directory
								JOptionPane.showMessageDialog(
										null, 
										Translate.getInstance().get(TranslateKeys.CANNOT_SAVE_OVER_DIR), 
										Translate.getInstance().get(TranslateKeys.CHOOSE_BACKUP_FILE), 
										JOptionPane.ERROR_MESSAGE);
								return;
							}
							else{
								file = jfc.getSelectedFile();
							}
						}

					}

					if (!exportPlugin.isPromptForFile() || file != null){
						try{
							((BuddiExportPlugin) plugin).exportData(frame, file);
						}
						catch (Exception e){
							Log.error(e);
						}
						catch (Error e){
							Log.error(e);
						}
					}
					else{
						Log.warning("Plugin did not run - plugin requires valid file, which was not selected.");
					}

					//Update all the accounts with the new totals, etc.  Probably not needed for Import plugins, but 
					// it's a pretty cheap operation anyway, and it is better safe than sorry...
					DataInstance.getInstance().calculateAllBalances();
					MainBuddiFrame.getInstance().getAccountListPanel().updateContent();

				}
			}
		});
	}

	private static void addImportActionListener(final BuddiImportPlugin plugin, JScreenMenuItem menuItem, final AbstractFrame frame){
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (plugin instanceof BuddiImportPlugin){
					File file = null;
					BuddiImportPlugin importPlugin = (BuddiImportPlugin) plugin;

					if (importPlugin.isPromptForFile()){
						//Create a file chooser to give plugin a file. 
						final JFileChooser jfc = new JFileChooser();
						jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

						//Sets the title
						if (importPlugin.getFileChooserTitle() != null)
							jfc.setDialogTitle(importPlugin.getFileChooserTitle());

						//Sets the filter
						if (importPlugin.getFileFilter() != null)
							jfc.setFileFilter(importPlugin.getFileFilter());

						if (jfc.showOpenDialog(MainBuddiFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
							if (jfc.getSelectedFile().isDirectory()){
								//Cannot select a directory
								JOptionPane.showMessageDialog(
										null, 
										Translate.getInstance().get(TranslateKeys.CANNOT_SAVE_OVER_DIR), 
										Translate.getInstance().get(TranslateKeys.CHOOSE_BACKUP_FILE), 
										JOptionPane.ERROR_MESSAGE);
								return;
							}
							else{
								file = jfc.getSelectedFile();
							}
						}

					}

					if (!importPlugin.isPromptForFile() || file != null){
						try {
							((BuddiImportPlugin) plugin).importData(frame, file);
						}
						catch (Exception e){
							Log.error(e);
						}
						catch (Error e){
							Log.error(e);
						}
					}
					else {
						Log.warning("Plugin did not run - plugin requires valid file, which was not selected.");
					}

					//Update all the accounts with the new totals, etc.
					DataInstance.getInstance().calculateAllBalances();
					MainBuddiFrame.getInstance().getAccountListPanel().updateContent();
				}
			}
		});
	}

	private static void addPanelActionListener(final BuddiPanelPlugin plugin, final JComboBox dateSelect){
		dateSelect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//Find out which item was clicked on
				Object o = dateSelect.getSelectedItem();

				//If the object was a date choice, access the encoded dates
				if (o instanceof DateChoice 
						&& plugin instanceof BuddiPanelPlugin){
					DateChoice choice = (DateChoice) o;

					//As long as the choice is not custom, our job is easy
					if (!choice.isCustom()){
						//Launch a new reports window with given parameters
						BuddiPluginHelper.openNewPanelPluginWindow(
								(BuddiPanelPlugin) plugin, 
								choice.getStartDate(), 
								choice.getEndDate()
						);
					}
					//If they want a custom window, it's a little 
					// harder... we need to open the custom date
					// window, which then launches the plugin.
					else{
						new CustomDateDialog(
								MainBuddiFrame.getInstance(),
								(BuddiPanelPlugin) plugin
						).openWindow();
					}
				}

				dateSelect.setSelectedItem(null);
			}
		});
	}

	/**
	 * Converts classes from filesystem type (i.e., org/homeunix/thecave/Test.class)
	 * to Class type (i.e., org.homeunix.thecave.Test).
	 * @param filesystemClassName class in Filesystem type
	 * @return
	 */
	private static String filesystemToClass(String filesystemClassName){
		return filesystemClassName.replaceAll(".class$", "").replaceAll("/", ".");
	}
}
