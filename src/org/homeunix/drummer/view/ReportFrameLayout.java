/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.plugins.BuddiReportPlugin;
import org.homeunix.drummer.prefs.PrefsInstance;

public class ReportFrameLayout extends AbstractFrame {
	public final static long serialVersionUID = 0;
	protected final JTree reportTree;
	protected final JButton okButton;
	protected final JButton editButton;
	protected final String HTMLPage;
	
	protected DefaultMutableTreeNode selectedNode;

	
	public ReportFrameLayout(BuddiReportPlugin reportPlugin, final Date startDate, final Date endDate){
		this.setTitle(reportPlugin.getTitle());
		
		HTMLPage = reportPlugin.getReportHTML(startDate, endDate);
		
		reportTree = new JTree(reportPlugin.getReportTreeModel(startDate, endDate));
		reportTree.setRootVisible(false);
		reportTree.setShowsRootHandles(true);
		reportTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		reportTree.setCellRenderer(reportPlugin.getTreeCellRenderer());

		okButton = new JButton(Translate.getInstance().get(TranslateKeys.OK));
		editButton = new JButton(Translate.getInstance().get(TranslateKeys.EDIT));

		Dimension buttonSize = new Dimension(100, okButton.getPreferredSize().height);
		okButton.setPreferredSize(buttonSize);
		editButton.setPreferredSize(buttonSize);

		reportTree.setBackground(Color.WHITE);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(editButton);
		buttonPanel.add(okButton);

		editButton.setEnabled(false);

		JScrollPane reportLabelScroller = new JScrollPane(reportTree);

		JPanel reportPanelSpacer = new JPanel(new BorderLayout());
		reportPanelSpacer.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		reportPanelSpacer.add(reportLabelScroller, BorderLayout.CENTER);

		JPanel reportPanel = new JPanel(new BorderLayout());
		reportPanel.setBorder(BorderFactory.createTitledBorder(""));
		reportPanel.add(reportPanelSpacer, BorderLayout.CENTER);

		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(reportPanel, BorderLayout.CENTER);

		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		
		if (Buddi.isMac()){
			reportTree.putClientProperty("Quaqua.Tree.style", "striped");
			reportLabelScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			reportLabelScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		}
		
		initActions();
	}
	
	@Override
	public Component getPrintedComponent() {
		return reportTree;
	}
	
	@Override
	protected AbstractFrame initActions() {
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent arg0) {
				PrefsInstance.getInstance().checkWindowSanity();
				
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setX(arg0.getComponent().getX());
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setY(arg0.getComponent().getY());
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setWidth(arg0.getComponent().getWidth());
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setHeight(arg0.getComponent().getHeight());
								
				PrefsInstance.getInstance().savePrefs();
				
				super.componentHidden(arg0);
			}
		});
		
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PrefsInstance.getInstance().checkWindowSanity();
				
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setX(ReportFrameLayout.this.getX());
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setY(ReportFrameLayout.this.getY());
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setWidth(ReportFrameLayout.this.getWidth());
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setHeight(ReportFrameLayout.this.getHeight());
								
				PrefsInstance.getInstance().savePrefs();
				
				ReportFrameLayout.this.setVisible(false);
			}
		});
		
		reportTree.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent arg0) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) reportTree.getLastSelectedPathComponent();
				
				if (node == null)
					return;
				
				selectedNode = node;
			}
		});
		
		return this;
	}
	
	public String getHTMLPage(){
		return HTMLPage;
	}
	
	@Override
	protected AbstractFrame initContent() {
		return this;
	}
	
	@Override
	public AbstractFrame updateButtons() {
		return this;
	}
	
	@Override
	public AbstractFrame updateContent() {
		return this;
	}
}
