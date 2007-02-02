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

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Source;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.components.SourceAmountCellRenderer;
import org.homeunix.drummer.view.components.SourceNameCellRenderer;
import org.homeunix.drummer.view.components.SourceTreeCellRenderer;
import org.homeunix.drummer.view.components.SourceTreeTableModel;
import org.homeunix.thecave.moss.util.Log;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.AlternateRowHighlighter;

public abstract class ListPanelLayout extends AbstractPanel {
	public static final long serialVersionUID = 0;

	protected final JXTreeTable tree;
	protected final JButton newButton;
	protected final JButton editButton;
	protected final JButton deleteButton;
	protected final JButton openButton;
//	protected final JPanel openButtonPanel;
	protected final JLabel balanceLabel;

	protected final SourceTreeTableModel treeModel;
	
	private static Color LIGHT_BLUE = new Color(237, 243, 254);
//	private static Color SELECTED = new Color(181, 213, 255);
	private static Color WHITE = Color.WHITE;

	protected Source selectedSource;

	protected ListPanelLayout(){
		treeModel = new SourceTreeTableModel();
		tree = new JXTreeTable(treeModel);
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.setAutoResizeMode(JXTreeTable.AUTO_RESIZE_LAST_COLUMN);
		
		tree.setTreeCellRenderer(new SourceTreeCellRenderer());
		tree.getColumn(1).setCellRenderer(new SourceNameCellRenderer());
		tree.getColumn(2).setCellRenderer(new SourceAmountCellRenderer());
		
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		//TODO Try to make this automatically adjust based on depth
		int treeColumnWidth = 100;
		tree.getColumn(0).setMaxWidth(treeColumnWidth);
		tree.getColumn(0).setMinWidth(treeColumnWidth);
		tree.getColumn(0).setPreferredWidth(treeColumnWidth);
		
//		tree.getColumn(1).setPreferredWidth(PrefsInstance.getInstance().getColumnWidth(getTableNumber(), 1));
//		tree.getColumn(2).setPreferredWidth(PrefsInstance.getInstance().getColumnWidth(getTableNumber(), 2));
//		tree.packAll();
		
		JScrollPane listScroller = new JScrollPane(tree);

		newButton = new JButton(Translate.getInstance().get(TranslateKeys.NEW));
		editButton = new JButton(Translate.getInstance().get(TranslateKeys.EDIT));
		deleteButton = new JButton(Translate.getInstance().get(TranslateKeys.DELETE));
		openButton = new JButton(Translate.getInstance().get(TranslateKeys.OPEN));
		balanceLabel = new JLabel();

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

//		openButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//		openButtonPanel.add(openButton);

		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(buttonPanelRight, BorderLayout.EAST);
		buttonPanel.add(buttonPanelLeft, BorderLayout.WEST);

//		JPanel mainBorderPanel = new JPanel();
//		mainBorderPanel.setLayout(new BorderLayout());
//		mainBorderPanel.setBorder(BorderFactory.createTitledBorder(""));

		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

//		mainBorderPanel.add(listScrollerPanel, BorderLayout.CENTER);
//		mainBorderPanel.add(buttonPanel, BorderLayout.SOUTH);
//
//		mainPanel.add(mainBorderPanel, BorderLayout.CENTER);
//		mainPanel.add(openButtonPanel, BorderLayout.SOUTH);
//		mainPanel.add(balanceLabelPanel, BorderLayout.NORTH);
		mainPanel.add(listScrollerPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		tree.addHighlighter(new AlternateRowHighlighter(WHITE, LIGHT_BLUE, Color.BLACK));
		
		if (Buddi.isMac()){
//			tree.putClientProperty("Quaqua.Table.style", "striped");
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			listScroller.putClientProperty("Quaqua.Component.visualMargin", new Insets(7,12,3,12));
//			balanceLabel.putClientProperty("Quaqua.Component.style", "mini");
		}

		//this.setDefaultButton(openButton);
		this.setPreferredSize(new Dimension(450, 300));
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		this.setVisible(true);

		//Call the method to add actions to the buttons
		initActions();
		updateContent();
		updateButtons();
	}

	@Override
	protected AbstractPanel initActions() {
		tree.addTreeSelectionListener(new TreeSelectionListener(){
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
				
				ListPanelLayout.this.updateButtons();
			}
		});

		tree.addTreeExpansionListener(new TreeExpansionListener(){
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
		});
		
//		tree.getColumn(1).addPropertyChangeListener(new PropertyChangeListener(){
//			public void propertyChange(PropertyChangeEvent evt) {
//				if (evt.getPropertyName().equals("preferredWidth") && evt.getNewValue() instanceof Integer){
//					PrefsInstance.getInstance().setColumnWidth(getTableNumber(), 1, (Integer) evt.getNewValue());
//					PrefsInstance.getInstance().savePrefs();
//				}
//			}
//		});
//		
//		tree.getColumn(2).addPropertyChangeListener(new PropertyChangeListener(){
//			public void propertyChange(PropertyChangeEvent evt) {
//				if (evt.getPropertyName().equals("preferredWidth") && evt.getNewValue() instanceof Integer){
//					PrefsInstance.getInstance().setColumnWidth(getTableNumber(), 2, (Integer) evt.getNewValue());
//					PrefsInstance.getInstance().savePrefs();
//				}
//			}
//		});
		
		return this;
	}

	public AbstractPanel updateButtons(){
		if (selectedSource == null){
			editButton.setEnabled(false);
			deleteButton.setEnabled(false);
			openButton.setEnabled(false);
			deleteButton.setText(Translate.getInstance().get(TranslateKeys.DELETE));
		}
		else{
			editButton.setEnabled(true);
			deleteButton.setEnabled(true);
			openButton.setEnabled(true);

			if (selectedSource.isDeleted())
				deleteButton.setText(Translate.getInstance().get(TranslateKeys.UNDELETE));
			else
				deleteButton.setText(Translate.getInstance().get(TranslateKeys.DELETE));
		}
		return this;
	}
	
	@Override
	public AbstractPanel updateContent() {
		tree.packAll();
		return this;
	}

	public JXTreeTable getTree(){
		return tree;
	}
	
	/**
	 * Keep track of which type of table this is - Account or Category.
	 * Used internally for saving column width to preferences. 
	 * @return
	 */
	protected abstract int getTableNumber();
}