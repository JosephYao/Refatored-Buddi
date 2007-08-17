/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.BuddiReportPlugin;
import org.homeunix.thecave.buddi.util.BuddiPluginFactory;
import org.homeunix.thecave.buddi.view.menu.bars.ReportFrameMenuBar;
import org.homeunix.thecave.moss.swing.window.MossAssociatedDocumentFrame;
import org.homeunix.thecave.moss.swing.window.MossDocumentFrame;

/**
 * @author wyatt
 */
public class ReportFrame extends MossAssociatedDocumentFrame {
	public static final long serialVersionUID = 0;
	
	public ReportFrame(MossDocumentFrame parentFrame){
		super(parentFrame, "BudgetFrame" + ((DataModel) parentFrame.getDocument()).getUid());	
	}
	
	public void init() {
		super.init();
		
		JPanel pluginsPanel = new JPanel();
		pluginsPanel.setLayout(new BoxLayout(pluginsPanel, BoxLayout.Y_AXIS));
						
		//TODO Create the correct pulldowns
		for (BuddiReportPlugin report : BuddiPluginFactory.getReportPlugins()) {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JLabel label = new JLabel(PrefsModel.getInstance().getTranslator().get(report.getDescription()));
			panel.add(label);
			pluginsPanel.add(panel);
		}
		
		
		this.setLayout(new BorderLayout());
		this.add(pluginsPanel, BorderLayout.CENTER);
		this.setJMenuBar(new ReportFrameMenuBar(this));
		String dataFile = getDocument().getFile() == null ? "" : " - " + getDocument().getFile();
		this.setTitle(PrefsModel.getInstance().getTranslator().get(BuddiKeys.MY_REPORTS) + dataFile + " - " + PrefsModel.getInstance().getTranslator().get(BuddiKeys.BUDDI));
	}
	
	@Override
	public Object closeWindow() {
		PrefsModel.getInstance().setReportWindowSize(this.getSize());
		PrefsModel.getInstance().setReportWindowLocation(this.getLocation());
		PrefsModel.getInstance().save();
				
		return super.closeWindow();
	}
	
	public void actionPerformed(ActionEvent e) {}	
}
