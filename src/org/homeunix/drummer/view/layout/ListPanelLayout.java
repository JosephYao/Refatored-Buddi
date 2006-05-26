/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view.layout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Strings;
import org.homeunix.drummer.model.Source;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractBudgetPanel;
import org.homeunix.drummer.view.components.SourceCellRenderer;

public abstract class ListPanelLayout extends AbstractBudgetPanel {
	public static final long serialVersionUID = 0;

	protected final JTree tree;
	protected final JButton newButton;
	protected final JButton editButton;
	protected final JButton deleteButton;
	protected final JButton openButton;
	protected final JPanel openButtonPanel;
	protected final JLabel balanceLabel;
	
	protected final DefaultMutableTreeNode root;
	protected final DefaultTreeModel treeModel; 
	
	protected Source selectedSource;
	
	protected ListPanelLayout(){
		root = new DefaultMutableTreeNode();
		treeModel = new DefaultTreeModel(root);
		tree = new JTree(treeModel);
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		SourceCellRenderer renderer = new SourceCellRenderer();
		tree.setCellRenderer(renderer);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		JScrollPane listScroller = new JScrollPane(tree);
		
		newButton = new JButton(Strings.inst().get(Strings.NEW));
		editButton = new JButton(Strings.inst().get(Strings.EDIT));
		deleteButton = new JButton(Strings.inst().get(Strings.DELETE));
		openButton = new JButton(Strings.inst().get(Strings.OPEN));
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
		buttonPanelRight.add(editButton);
		buttonPanelRight.add(newButton);
		
		JPanel buttonPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanelLeft.add(deleteButton);
		
		openButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		openButtonPanel.add(openButton);
		
		JPanel buttonPanel = new JPanel(new BorderLayout());
		//buttonPanel.setBorder(BorderFactory.createTitledBorder(""));
		buttonPanel.add(buttonPanelRight, BorderLayout.EAST);
		buttonPanel.add(buttonPanelLeft, BorderLayout.WEST);
		//buttonPanel.add(openButtonPanel, BorderLayout.NORTH);
				
		JPanel mainBorderPanel = new JPanel();
		mainBorderPanel.setLayout(new BorderLayout());
		mainBorderPanel.setBorder(BorderFactory.createTitledBorder(""));
		
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 7, 17));
		
		mainBorderPanel.add(listScrollerPanel, BorderLayout.CENTER);
		mainBorderPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		mainPanel.add(mainBorderPanel, BorderLayout.CENTER);
		mainPanel.add(openButtonPanel, BorderLayout.SOUTH);
		
		if (Buddi.isMac()){
			tree.putClientProperty("Quaqua.Tree.style", "striped");
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
	protected AbstractBudgetPanel initActions() {
		tree.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent arg0) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				
				if (node == null)
					return;
				
				if (node.getUserObject() instanceof Source){ 
					selectedSource = (Source) node.getUserObject();
					Log.debug(selectedSource);
				}
				else{
					Log.debug("Object not of type Source");
					selectedSource = null;
				}
				
				ListPanelLayout.this.updateButtons();
			}
		});

		return this;
	}
	
	public AbstractBudgetPanel updateButtons(){
		if (selectedSource == null){
			editButton.setEnabled(false);
			deleteButton.setEnabled(false);
			openButton.setEnabled(false);
			deleteButton.setText(Strings.inst().get(Strings.DELETE));
		}
		else{
			editButton.setEnabled(true);
			deleteButton.setEnabled(true);
			openButton.setEnabled(true);
			
			if (selectedSource.isDeleted())
				deleteButton.setText(Strings.inst().get(Strings.UNDELETE));
			else
				deleteButton.setText(Strings.inst().get(Strings.DELETE));
		}
		return this;
	}
	
	public JTree getTree(){
		return tree;
	}
}
