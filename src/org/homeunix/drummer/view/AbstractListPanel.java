/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Source;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.components.DefaultTreeCellRenderer;
import org.homeunix.drummer.view.components.SourceAmountCellRenderer;
import org.homeunix.drummer.view.components.SourceNameCellRenderer;
import org.homeunix.drummer.view.model.SourceTreeTableModel;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardContainer;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.AlternateRowHighlighter;

public abstract class AbstractListPanel extends AbstractBuddiPanel implements TreeExpansionListener, TreeSelectionListener {
	public static final long serialVersionUID = 0;

	protected final JXTreeTable tree;
	protected final JButton newButton;
	protected final JButton editButton;
	protected final JButton deleteButton;
	protected final JButton openButton;
	protected final JLabel balanceLabel;

	protected final SourceTreeTableModel treeModel;
	
//	protected static Color LIGHT_BLUE = new Color(237, 243, 254);
//	protected static Color SELECTED = new Color(181, 213, 255);
//	protected static Color WHITE = Color.WHITE;

	protected Source selectedSource;

	public AbstractListPanel(){
		treeModel = new SourceTreeTableModel();
		tree = new JXTreeTable(treeModel);

		newButton = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_NEW));
		editButton = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_EDIT));
		deleteButton = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_DELETE));
		openButton = new JButton(Translate.getInstance().get(TranslateKeys.OPEN));
		balanceLabel = new JLabel();
	}

	public StandardContainer init() {
		
		newButton.addActionListener(this);
		editButton.addActionListener(this);
		deleteButton.addActionListener(this);
		openButton.addActionListener(this);
		tree.addTreeSelectionListener(this);
		tree.addTreeExpansionListener(this);
		
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.setAutoResizeMode(JXTreeTable.AUTO_RESIZE_LAST_COLUMN);
		tree.setTreeCellRenderer(new DefaultTreeCellRenderer());
		tree.getColumn(1).setCellRenderer(new SourceNameCellRenderer());
		tree.getColumn(2).setCellRenderer(new SourceAmountCellRenderer());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addHighlighter(new AlternateRowHighlighter(Const.COLOR_EVEN_ROW, Const.COLOR_ODD_ROW, Color.BLACK));
		
		JScrollPane listScroller = new JScrollPane(tree);

		JPanel balanceLabelPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		balanceLabelPanel.add(balanceLabel);

		JPanel listScrollerPanel = new JPanel(new BorderLayout());
		listScrollerPanel.add(listScroller, BorderLayout.CENTER);
		listScrollerPanel.add(balanceLabelPanel, BorderLayout.SOUTH);

		Dimension buttonSize = new Dimension(100, newButton.getPreferredSize().height);
		newButton.setPreferredSize(buttonSize);
		editButton.setPreferredSize(buttonSize);
		deleteButton.setPreferredSize(buttonSize);
		openButton.setPreferredSize(buttonSize);

		JPanel buttonPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanelRight.add(openButton);

		JPanel buttonPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanelLeft.add(newButton);
		buttonPanelLeft.add(editButton);
		buttonPanelLeft.add(deleteButton);

		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(buttonPanelRight, BorderLayout.EAST);
		buttonPanel.add(buttonPanelLeft, BorderLayout.WEST);

		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		mainPanel.add(listScrollerPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		if (OperatingSystemUtil.isMac()){
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			listScroller.putClientProperty("Quaqua.Component.visualMargin", new Insets(7,12,3,12));
		}

		this.setPreferredSize(new Dimension(450, 300));
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		
		return this;
	}
	
	public AbstractBuddiPanel updateButtons(){
		if (selectedSource == null){
			editButton.setEnabled(false);
			deleteButton.setEnabled(false);
			openButton.setEnabled(false);
			deleteButton.setText(Translate.getInstance().get(TranslateKeys.BUTTON_DELETE));
		}
		else{
			editButton.setEnabled(true);
			deleteButton.setEnabled(true);
			openButton.setEnabled(true);

			if (selectedSource.isDeleted())
				deleteButton.setText(Translate.getInstance().get(TranslateKeys.BUTTON_UNDELETE));
			else
				deleteButton.setText(Translate.getInstance().get(TranslateKeys.BUTTON_DELETE));
		}
		return this;
	}
	
	public AbstractBuddiPanel updateContent() {
		tree.packAll();
				
		int treeColumnWidth = treeModel.getRoot().getDepth() * 15 + 15;
		tree.getColumn(0).setMaxWidth(treeColumnWidth);
		tree.getColumn(0).setMinWidth(treeColumnWidth);
		tree.getColumn(0).setPreferredWidth(treeColumnWidth);
		
		return this;
	}

	public JXTreeTable getTree(){
		return tree;
	}
	
	public void valueChanged(TreeSelectionEvent arg0) {
		if (arg0 != null && arg0.getNewLeadSelectionPath() != null){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) arg0.getNewLeadSelectionPath().getLastPathComponent();

			if (node == null)
				return;

			if (node.getUserObject() instanceof Source){ 
				selectedSource = (Source) node.getUserObject();
				if (Const.DEVEL) Log.debug(selectedSource);
			}
			else{
				if (Const.DEVEL) Log.debug("Object not of type Source");
				selectedSource = null;
			}
		}
		
		AbstractListPanel.this.updateButtons();
	}
	
	public void treeCollapsed(TreeExpansionEvent arg0) {				
		Object o = arg0.getPath().getLastPathComponent();

		if (Const.DEVEL) Log.debug("Rolled node: " + o.toString());

		if (o instanceof DefaultMutableTreeNode){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) o;
			PrefsInstance.getInstance().setListAttributes(node.getUserObject().toString(), false);
		}
		else {
			Log.error("Unknown object in treeExpansionListener: " + o);
		}
	}

	public void treeExpanded(TreeExpansionEvent arg0) {
		Object o = arg0.getPath().getLastPathComponent();

		if (Const.DEVEL) Log.debug("Unrolled node: " + o.toString());

		if (o instanceof DefaultMutableTreeNode){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) o;
			PrefsInstance.getInstance().setListAttributes(node.getUserObject().toString(), true);
			PrefsInstance.getInstance().savePrefs();
		}
		else {
			Log.error("Unknown object in treeExpansionListener: " + o);
		}
		
		tree.packAll();
	}
}