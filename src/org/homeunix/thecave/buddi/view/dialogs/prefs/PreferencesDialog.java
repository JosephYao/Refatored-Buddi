/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.view.dialogs.prefs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.util.InternalFormatter;
import org.homeunix.thecave.moss.swing.window.MossFrame;

public class PreferencesDialog extends MossFrame implements ActionListener {
	public static final long serialVersionUID = 0;
	
	private final JTabbedPane tabs;
	
	private final ViewPreferences view;
	private final PluginPreferences plugin;
	private final LocalePreferences locale;
	private final NetworkPreferences network;
	private final AdvancedPreferences advanced;
	
	private final JButton okButton;
	private final JButton cancelButton;
	
	public PreferencesDialog(MossFrame frame) {
		super(frame);
		
		tabs = new JTabbedPane();
		
		view = new ViewPreferences();
		plugin = new PluginPreferences();
		locale = new LocalePreferences();
		network = new NetworkPreferences();
		advanced = new AdvancedPreferences();
		
		okButton = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK));
		cancelButton = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_CANCEL));
	}
	
	@Override
	public void init() {
		super.init();

		view.open();
		plugin.open();
		locale.open();
		network.open();
		advanced.open();
		
		tabs.addTab(PrefsModel.getInstance().getTranslator().get(BuddiKeys.VIEW), view);
		tabs.addTab(PrefsModel.getInstance().getTranslator().get(BuddiKeys.PLUGINS), plugin);
		tabs.addTab(PrefsModel.getInstance().getTranslator().get(BuddiKeys.LOCALE), locale);
		tabs.addTab(PrefsModel.getInstance().getTranslator().get(BuddiKeys.NETWORK), network);
		tabs.addTab(PrefsModel.getInstance().getTranslator().get(BuddiKeys.ADVANCED), advanced);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);

		okButton.setPreferredSize(InternalFormatter.getButtonSize(okButton));
		cancelButton.setPreferredSize(InternalFormatter.getButtonSize(cancelButton));
		
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		this.getRootPane().setDefaultButton(okButton);
		this.setLayout(new BorderLayout());
		this.add(tabs, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public Object closeWindow() {
		PrefsModel.getInstance().setPreferencesWindowSize(this.getSize());
		PrefsModel.getInstance().setPreferencesWindowLocation(this.getLocation());
		PrefsModel.getInstance().save();

		
		return super.closeWindow();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(okButton)){
			view.save();
			plugin.save();
			locale.save();
			network.save();
			advanced.save();
			
			PrefsModel.getInstance().save();
			
			this.closeWindow();
		}
		else if (e.getSource().equals(cancelButton)){
			this.closeWindow();
		}
	}
}
