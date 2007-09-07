/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.BuddiPluginFactory;
import org.homeunix.thecave.buddi.plugin.api.BuddiPreferencePlugin;
import org.homeunix.thecave.buddi.plugin.api.exception.PluginException;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.util.InternalFormatter;
import org.homeunix.thecave.buddi.view.menu.bars.BuddiMenuBar;
import org.homeunix.thecave.moss.swing.MossFrame;
import org.homeunix.thecave.moss.util.Log;

public class PreferencesFrame extends MossFrame implements ActionListener {
	public static final long serialVersionUID = 0;
	
	private final JTabbedPane tabs;
	private final List<BuddiPreferencePlugin> preferencePanels;
	
	private final JButton okButton;
	private final JButton cancelButton;
	
	public PreferencesFrame() {
		super("Resources/Buddi.gif");
		tabs = new JTabbedPane();
		
		preferencePanels = BuddiPluginFactory.getPreferencePlugins();		
		okButton = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK));
		cancelButton = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_CANCEL));
	}
	
	@Override
	public void init() {
		super.init();

		//Load each plugin into the Preferences panel
		for (BuddiPreferencePlugin panel : preferencePanels) {
			tabs.addTab(PrefsModel.getInstance().getTranslator().get(panel.getName()), getWrapperPanel(panel.getPreferencesPanel()));
			
		}
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);

		okButton.setPreferredSize(InternalFormatter.getButtonSize(okButton));
		cancelButton.setPreferredSize(InternalFormatter.getButtonSize(cancelButton));
		
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		for (BuddiPreferencePlugin panel : preferencePanels) {
			try {
				panel.load();
			}
			catch (PluginException pe){
				pe.printStackTrace(Log.getPrintStream());
			}
		}
		
		this.setJMenuBar(new BuddiMenuBar(this));
		this.setTitle(TextFormatter.getTranslation(BuddiKeys.PREFERENCES));
		this.getRootPane().setDefaultButton(okButton);
		this.setLayout(new BorderLayout());
		this.add(tabs, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	
	@Override
	public void closeWindowWithoutPrompting() {
		PrefsModel.getInstance().setPreferencesWindowLocation(this.getLocation());
		PrefsModel.getInstance().save();

		MainFrame.updateAllContent();
		
		super.closeWindowWithoutPrompting();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(okButton)){
			for (BuddiPreferencePlugin panel : preferencePanels) {
				try {
					panel.save();
				}
				catch (PluginException pe){
					pe.printStackTrace(Log.getPrintStream());
				}
			}
			
			PrefsModel.getInstance().save();
			
			this.closeWindow();
		}
		else if (e.getSource().equals(cancelButton)){
			this.closeWindow();
		}
	}
	
	private JPanel getWrapperPanel(JPanel panel){
		JPanel wrapper = new JPanel(new BorderLayout());
		wrapper.add(panel, BorderLayout.NORTH);
		return wrapper;
	}
}
