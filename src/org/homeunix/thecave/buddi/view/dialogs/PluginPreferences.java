/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import org.homeunix.thecave.buddi.Buddi;
import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.i18n.keys.MessageKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.BuddiPluginFactory;
import org.homeunix.thecave.buddi.plugin.api.BuddiPreferencePlugin;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.moss.plugin.MossPlugin;
import org.homeunix.thecave.moss.swing.model.DefaultGenericListModel;
import org.homeunix.thecave.moss.util.FileFunctions;
import org.homeunix.thecave.moss.util.Version;

public class PluginPreferences extends BuddiPreferencePlugin implements ActionListener {
	public static final long serialVersionUID = 0;

	private final JButton addButton;
	private final JButton removeButton;
	private final JList pluginList;
	private final DefaultGenericListModel<File> pluginListModel;

	private final List<File> filesToAdd = new LinkedList<File>();
	private final List<File> filesToRemove = new LinkedList<File>();

	public PluginPreferences() {
		addButton = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_ADD));
		removeButton = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_REMOVE));
		pluginListModel = new DefaultGenericListModel<File>();
		pluginList = new JList(pluginListModel);
	}

	public JPanel getPreferencesPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		Dimension buttonSize = new Dimension(Math.max(100, removeButton.getPreferredSize().width), removeButton.getPreferredSize().height);
		addButton.setPreferredSize(buttonSize);
		removeButton.setPreferredSize(buttonSize);
		removeButton.setEnabled(false);
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);

		addButton.addActionListener(this);
		removeButton.addActionListener(this);

		JScrollPane pluginListScroller = new JScrollPane(pluginList);

		pluginList.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){
					removeButton.setEnabled(pluginList.getSelectedIndex() != -1);
				}
			}
		});

		pluginList.setCellRenderer(new DefaultListCellRenderer(){
			public static final long serialVersionUID = 0;

			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

				if (value instanceof File && ((File) value).getName().endsWith(Const.PLUGIN_EXTENSION)){
					//TODO Read the plugin, and list the names of all valid plugins in the .jar file.
//					PluginInfo info = (PluginInfo) value;
//					this.setText(info.getName() + " (" + info.getJarFile() + ")");
					File file = (File) value;
					this.setText(file.getName());
				}
				else 
					this.setText("Not of correct file type");

				return this;
			}
		});

		panel.add(pluginListScroller, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);

		return panel;
	}

	public void load() {
		pluginListModel.clear();
		for (File file : BuddiPluginFactory.getPluginFiles()) {
			pluginListModel.addElement(file);
			pluginList.setSelectedValue(file, false);
		}		
	}

	public void save() {
		//At save time, we actually copy the files over to the new location, and remove ones which
		// are to be removed.

		for (File f : filesToRemove) {
			f.delete();
		}

		if (!Buddi.getPluginsFolder().exists()){
			if (!Buddi.getPluginsFolder().mkdirs()){
				String[] options = new String[1];
				options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK);

				JOptionPane.showOptionDialog(
						null, 
						TextFormatter.getTranslation(MessageKeys.MESSAGE_ERROR_DELETING_PLUGIN), 
						TextFormatter.getTranslation(MessageKeys.MESSAGE_ERROR_DELETING_PLUGIN_TITLE), 
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null,
						options,
						options[0]
				);
			}
		}


		for (File f : filesToAdd) {
			try {
				FileFunctions.copyFile(f, new File(Buddi.getPluginsFolder().getAbsolutePath() + File.separator + f.getName()));
			}
			catch (IOException ioe){
				String[] options = new String[1];
				options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK);

				JOptionPane.showOptionDialog(
						null, 
						TextFormatter.getTranslation(MessageKeys.MESSAGE_ERROR_DELETING_PLUGIN), 
						TextFormatter.getTranslation(MessageKeys.MESSAGE_ERROR_DELETING_PLUGIN_TITLE), 
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null,
						options,
						options[0]
				);
			}
		}

	}

	public String getName() {
		return BuddiKeys.PLUGINS.toString();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addButton)){
			final JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setFileFilter(new FileFilter(){
				public boolean accept(File arg0) {
					if (arg0.getAbsolutePath().endsWith(Const.PLUGIN_EXTENSION) ||
							arg0.isDirectory())
						return true;
					else
						return false;
				}

				@Override
				public String getDescription() {
					return PrefsModel.getInstance().getTranslator().get(BuddiKeys.FILE_DESCRIPTION_BUDDI_PLUGINS);
				}
			});
			jfc.setDialogTitle(PrefsModel.getInstance().getTranslator().get(BuddiKeys.CHOOSE_PLUGIN_JAR));
			if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				List<MossPlugin> plugins = BuddiPluginFactory.getMossPluginsFromJar(jfc.getSelectedFile(), Const.VERSION);
				if (plugins.size() == 0){
					String[] options = new String[1];
					options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK);

					JOptionPane.showOptionDialog(
							null, 
							PrefsModel.getInstance().getTranslator().get(BuddiKeys.NO_PLUGINS_IN_JAR), 
							PrefsModel.getInstance().getTranslator().get(BuddiKeys.NO_PLUGINS_IN_JAR_TITLE), 
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE,
							null,
							options,
							options[0]
					);
				}
				else {
					filesToAdd.add(jfc.getSelectedFile());
					pluginListModel.addElement(jfc.getSelectedFile());
				}
			}
		}
		else if (e.getSource().equals(removeButton)){
			if (pluginList.getSelectedValues().length > 0) {
				for (Object o : pluginList.getSelectedValues()) {
					filesToAdd.remove(o);
					filesToRemove.add((File) o);
				}
			}
		}
	}

	public Version getMaximumVersion() {
		return null;
	}

	public Version getMinimumVersion() {
		return null;
	}
}
