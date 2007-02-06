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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.homeunix.drummer.controller.TransactionsFrame;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.interfaces.BuddiReportPlugin;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class ReportFrameLayout extends AbstractFrame {
	public final static long serialVersionUID = 0;
	protected final JTree reportTree;
	protected final JButton okButton;
	protected final JButton editButton;
	protected String HTMLPage = "";
	protected final BuddiReportPlugin reportPlugin;
	protected final Date startDate, endDate;
	
	protected static final Vector<ReportFrameLayout> reportFrameInstances = new Vector<ReportFrameLayout>();
	
	protected DefaultMutableTreeNode selectedNode;

	
	public ReportFrameLayout(BuddiReportPlugin reportPlugin, final Date startDate, final Date endDate){
		this.reportPlugin = reportPlugin;
		this.startDate = startDate;
		this.endDate = endDate;
		
		reportTree = new JTree();
		reportTree.setRootVisible(false);
		reportTree.setShowsRootHandles(true);
		reportTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		updateContent();
		
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
//		reportPanelSpacer.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		reportPanelSpacer.add(reportLabelScroller, BorderLayout.CENTER);

//		JPanel reportPanel = new JPanel(new BorderLayout());
//		reportPanel.setBorder(BorderFactory.createTitledBorder(""));
//		reportPanel.add(reportPanelSpacer, BorderLayout.CENTER);

		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(reportPanelSpacer, BorderLayout.CENTER);

		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		
		if (OperatingSystemUtil.isMac()){
			mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
			reportTree.putClientProperty("Quaqua.Tree.style", "striped");
			reportLabelScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			reportLabelScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		}
		
		reportFrameInstances.add(this);
		
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
				saveWindowPosition();
				super.componentHidden(arg0);
			}
		});
		
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				saveWindowPosition();				
				ReportFrameLayout.this.setVisible(false);
			}
		});
		
		reportTree.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent arg0) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) reportTree.getLastSelectedPathComponent();
				selectedNode = node;
			}
		});
		
		reportTree.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				editButton.setEnabled(selectedNode != null && selectedNode.getUserObject() instanceof Transaction);
				
				if (arg0.getClickCount() >= 2){
					editButton.doClick();
				}
				super.mouseClicked(arg0);
			}
		});
		
		editButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Object o = selectedNode.getUserObject();
				if (o instanceof Transaction){
					Transaction transaction = (Transaction) o;
					Account a;
					if (transaction.getTo() instanceof Account)
						a = (Account) transaction.getTo();
					else if (transaction.getFrom() instanceof Account)
						a = (Account) transaction.getFrom();	
					else
						a = null;
					
					if (a != null) {
						new TransactionsFrame(a, transaction);
					}
				}
			}
		});
		
		return this;
	}
	
	private void saveWindowPosition(){
		PrefsInstance.getInstance().checkWindowSanity();
		
		PrefsInstance.getInstance().getPrefs().getWindows().getReportsWindow().setX(this.getX());
		PrefsInstance.getInstance().getPrefs().getWindows().getReportsWindow().setY(this.getY());
		PrefsInstance.getInstance().getPrefs().getWindows().getReportsWindow().setWidth(this.getWidth());
		PrefsInstance.getInstance().getPrefs().getWindows().getReportsWindow().setHeight(this.getHeight());
						
		PrefsInstance.getInstance().savePrefs();
		
		reportFrameInstances.remove(this);
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
		HTMLPage = reportPlugin.getReportHTML(startDate, endDate);
		reportTree.setModel(reportPlugin.getReportTreeModel(startDate, endDate));
		reportTree.setCellRenderer(reportPlugin.getTreeCellRenderer());
		this.setTitle(reportPlugin.getTitle());
		
		return this;
	}
	
	public static void updateAllReportWindows(){
		for (ReportFrameLayout rfl : Collections.unmodifiableCollection(reportFrameInstances)) {
			if (rfl != null)
				rfl.updateContent();
		}
	}
}
