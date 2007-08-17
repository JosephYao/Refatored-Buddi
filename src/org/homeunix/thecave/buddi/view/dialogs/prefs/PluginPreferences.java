/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.dialogs.prefs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.model.prefs.PluginInfo;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.BuddiPluginFactory;
import org.homeunix.thecave.moss.plugin.MossPlugin;
import org.homeunix.thecave.moss.swing.model.DefaultGenericListModel;
import org.homeunix.thecave.moss.swing.window.MossPanel;

public class PluginPreferences extends MossPanel implements PrefsPanel, ActionListener {
	public static final long serialVersionUID = 0;

	private final JButton addButton;
	private final JButton removeButton;
	private final JList pluginList;
	private final DefaultGenericListModel<PluginInfo> pluginListModel;

	public PluginPreferences() {
		addButton = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_ADD));
		removeButton = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_REMOVE));
		pluginListModel = new DefaultGenericListModel<PluginInfo>();
		pluginList = new JList(pluginListModel);

	}

	@Override
	public void init() {
		super.init();

		this.setLayout(new BorderLayout());

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

		pluginListModel.clear();
		for (PluginInfo info : PrefsModel.getInstance().getPluginInfo()) {
			pluginListModel.addElement(info);
			pluginList.setSelectedValue(info, false);
		}

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

				if (value instanceof PluginInfo){
					PluginInfo info = (PluginInfo) value;
					this.setText(info.getName() + " (" + info.getJarFile() + ")");
				}
				else 
					this.setText("Not of type PluginInfo");

				return this;
			}
		});

		this.add(pluginListScroller, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);

	}

	public void save() {
		PrefsModel.getInstance().clearPluginInfo();
		for (PluginInfo info : pluginListModel.getList()) {
			PrefsModel.getInstance().addPluginInfo(info);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addButton)){
			final JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setFileFilter(new FileFilter(){
				public boolean accept(File arg0) {
					if (arg0.getAbsolutePath().endsWith(".jar") ||
							arg0.isDirectory())
						return true;
					else
						return false;
				}

				@Override
				public String getDescription() {
					return PrefsModel.getInstance().getTranslator().get(BuddiKeys.JAR_FILES);
				}
			});
			jfc.setDialogTitle(PrefsModel.getInstance().getTranslator().get(BuddiKeys.CHOOSE_PLUGIN_JAR));
			if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				List<MossPlugin> plugins = BuddiPluginFactory.getValidPluginsFromJar(jfc.getSelectedFile(), Const.VERSION);
				if (plugins.size() == 0){
					String[] options = new String[1];
					options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK);

					JOptionPane.showOptionDialog(
							PluginPreferences.this, 
							PrefsModel.getInstance().getTranslator().get(BuddiKeys.NO_PLUGINS_IN_JAR), 
							PrefsModel.getInstance().getTranslator().get(BuddiKeys.NO_PLUGINS_IN_JAR_TITLE), 
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE,
							null,
							options,
							options[0]
					);
				}
				for (MossPlugin plugin : plugins) {
					PluginInfo info = new PluginInfo();
					info.setJarFile(jfc.getSelectedFile().getAbsolutePath());
					info.setClassName(plugin.getClass().getCanonicalName());
					info.setName(plugin.getName());
					pluginListModel.addElement(info);
				}
			}
		}
		else if (e.getSource().equals(removeButton)){
			if (pluginList.getSelectedValues().length > 0) {
				for (Object o : pluginList.getSelectedValues()) {
					if (o instanceof PluginInfo)
						pluginListModel.removeElement((PluginInfo) o);	
				}
			}
		}
	} 
}
