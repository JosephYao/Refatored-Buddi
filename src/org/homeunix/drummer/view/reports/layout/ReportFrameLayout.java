/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view.reports.layout;

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
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.controller.PrefsInstance;
import org.homeunix.drummer.view.AbstractBudgetFrame;

public abstract class ReportFrameLayout extends AbstractBudgetFrame {
	public static final long serialVersionUID = 0;

	protected final JTree reportTree;
	protected final JButton okButton;
	protected final JButton editButton;
	
	protected DefaultMutableTreeNode selectedNode;
	
	public ReportFrameLayout(Date startDate, Date endDate){
		reportTree = new JTree(buildReport(startDate, endDate));
		reportTree.setRootVisible(false);
		reportTree.setShowsRootHandles(true);
		reportTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		reportTree.setCellRenderer(getTreeCellRenderer());

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
				
		//Call the method to add actions to the buttons
		initActions();
		
		//Show the window
		openWindow();
	}
	
	protected abstract TreeModel buildReport(Date startDate, Date endDate);
	
	protected abstract TreeCellRenderer getTreeCellRenderer();
	
	@Override
	public Component getPrintedComponent() {
		return reportTree;
	}
	
	@Override
	protected AbstractBudgetFrame initActions() {
		this.addComponentListener(new ComponentAdapter(){
//			@Override
//			public void componentResized(ComponentEvent arg0) {
//				Log.debug("Reports window resized");
//				
//				PrefsInstance.getInstance().getPrefs().getReportsWindow().setHeight(arg0.getComponent().getHeight());
//				PrefsInstance.getInstance().getPrefs().getReportsWindow().setWidth(arg0.getComponent().getWidth());
//				
//				PrefsInstance.getInstance().savePrefs();
//				
//				super.componentResized(arg0);
//			}
			
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
}
