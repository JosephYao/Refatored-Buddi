/*
 * Created on May 6, 2006 by wyatt
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
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.Formatter;

public abstract class ReportFrameLayout extends AbstractFrame {
	public static final long serialVersionUID = 0;

	protected final JTree reportTree;
	protected final JButton okButton;
	protected final JButton editButton;
	protected final Date startDate;
	protected final Date endDate;
	
	protected DefaultMutableTreeNode selectedNode;
	
	public ReportFrameLayout(Date startDate, Date endDate){
		this.startDate = startDate;
		this.endDate = endDate;
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
	
	public abstract String getHtmlReport();
	
	@Override
	protected AbstractFrame initActions() {
		this.addComponentListener(new ComponentAdapter(){
//			@Override
//			public void componentResized(ComponentEvent arg0) {
//				if (Const.DEVEL) Log.debug("Reports window resized");
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
	
	protected StringBuffer getHtmlHeader(TranslateKeys reportTitle){
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"); 
		sb.append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<title>");
		sb.append(Translate.getInstance().get(reportTitle));
		sb.append("</title>\n");
		
		sb.append("<style type=\"text/css\">\n");
		sb.append(".red { color: red; }");
		sb.append("h1 { font-size: large; }");
		sb.append("table.main { background-color: black; width: 100%; }\n");
		sb.append("table.transactions { background-color: white; width: 100%; padding-left: 3em; }\n");
		sb.append("table.main tr { padding-bottom: 1em; }\n");
		sb.append("table.main th { width: 20%; background-color: #DDE}\n");
		sb.append("table.main td { width: 20%; background-color: #EEF}\n");
		sb.append("table.transactions td { width: 30%; background-color: white}\n");
		sb.append("</style>\n");
		
		sb.append("</head>\n");
		sb.append("<body>\n");
		
		sb.append("<h1>");
		sb.append(Translate.getInstance().get(reportTitle));
		sb.append(" ("); 
		sb.append(Formatter.getInstance().getDateFormat().format(startDate));
		sb.append(" - ");
		sb.append(Formatter.getInstance().getDateFormat().format(endDate));
		sb.append(") ");
		sb.append("</h1>\n");
		
		return sb;
	}
}
