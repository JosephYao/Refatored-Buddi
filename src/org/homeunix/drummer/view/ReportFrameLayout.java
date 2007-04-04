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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.TransactionsFrame;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.interfaces.BuddiReportPlugin;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.prefs.WindowAttributes;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardContainer;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.AlternateRowHighlighter;

public class ReportFrameLayout extends AbstractBuddiFrame {
	public final static long serialVersionUID = 0;
	protected final JXTreeTable tree;

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

		tree = new JXTreeTable();
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.setSelectionModel(new DefaultListSelectionModel());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addHighlighter(new AlternateRowHighlighter(Const.COLOR_EVEN_ROW, Const.COLOR_ODD_ROW, Color.BLACK));
		tree.setAutoResizeMode(JXTreeTable.AUTO_RESIZE_LAST_COLUMN);

		okButton = new JButton(Translate.getInstance().get(TranslateKeys.OK));
		editButton = new JButton(Translate.getInstance().get(TranslateKeys.EDIT));

		Dimension buttonSize = new Dimension(100, okButton.getPreferredSize().height);
		okButton.setPreferredSize(buttonSize);
		editButton.setPreferredSize(buttonSize);

		tree.setBackground(Color.WHITE);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(editButton);
		buttonPanel.add(okButton);

		editButton.setEnabled(false);

		JScrollPane reportLabelScroller = new JScrollPane(tree);

		JPanel reportPanelSpacer = new JPanel(new BorderLayout());
		reportPanelSpacer.add(reportLabelScroller, BorderLayout.CENTER);

		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(reportPanelSpacer, BorderLayout.CENTER);

		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);

		if (OperatingSystemUtil.isMac()){
			tree.putClientProperty("Quaqua.Tree.style", "striped");
			reportLabelScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			reportLabelScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		}

		reportFrameInstances.add(this);
	}

	public Component getPrintedComponent() {
		return tree;
	}

	/* (non-Javadoc)
	 * @see org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame#preCloseWindow()
	 */
	@Override
	public Object closeWindow() {
		saveWindowPosition();
		
		return super.closeWindow();
	}
	
	public AbstractFrame init() {
		
		okButton.addActionListener(this);
		editButton.addActionListener(this);
				
		tree.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				editButton.setEnabled(selectedNode != null && selectedNode.getUserObject() instanceof Transaction);

				if (arg0.getClickCount() >= 2){
					editButton.doClick();
				}
				super.mouseClicked(arg0);
			}
		});

		return this;
	}

	private void saveWindowPosition(){
		PrefsInstance.getInstance().checkWindowSanity();

		WindowAttributes wa = PrefsInstance.getInstance().getPrefs().getWindows().getReportsWindow();
		wa.setX(this.getX());
		wa.setY(this.getY());
		wa.setWidth(this.getWidth());
		wa.setHeight(this.getHeight());

		PrefsInstance.getInstance().savePrefs();

		reportFrameInstances.remove(this);
	}

	public String getHTMLPage(){
		return HTMLPage;
	}

	public AbstractFrame updateButtons() {
		return this;
	}

	public AbstractFrame updateContent() {
//		HTMLPage = reportPlugin.getReportHTML(startDate, endDate);
//		reportTree.setModel(reportPlugin.getReportTreeModel(startDate, endDate));
		tree.setTreeTableModel(reportPlugin.getTreeTableModel(startDate, endDate));
//		reportTree.setCellRenderer(reportPlugin.getTreeCellRenderer());

		for (int i = 0; i < reportPlugin.getTableCellRenderers().size(); i++){
			tree.getColumn(i + 1).setCellRenderer(reportPlugin.getTableCellRenderers().get(i));
		}
		tree.setTreeCellRenderer(reportPlugin.getTreeCellRenderer());

		this.setTitle(reportPlugin.getTitle() 
				+ " (" 
				+ Formatter.getInstance().getDateFormat().format(startDate)
				+ " - "
				+ Formatter.getInstance().getDateFormat().format(endDate)
				+ ")");

		return this;
	}

	public static void updateAllReportWindows(){
		for (ReportFrameLayout rfl : Collections.unmodifiableCollection(reportFrameInstances)) {
			if (rfl != null)
				rfl.updateContent();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(okButton)){				
			closeWindow();
		}
		else if (e.getSource().equals(editButton)){
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
	}

	public StandardContainer clear() {
		return this;
	}
}
