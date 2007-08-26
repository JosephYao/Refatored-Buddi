/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodType;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.swing.BudgetDateSpinnerModel;
import org.homeunix.thecave.buddi.model.swing.BudgetTreeTableModel;
import org.homeunix.thecave.buddi.util.BudgetPeriodUtil;
import org.homeunix.thecave.buddi.view.swing.BudgetCategoryNameCellRenderer;
import org.homeunix.thecave.buddi.view.swing.DecimalCellEditor;
import org.homeunix.thecave.buddi.view.swing.DecimalCellRenderer;
import org.homeunix.thecave.buddi.view.swing.TranslatorListCellRenderer;
import org.homeunix.thecave.moss.swing.MossDecimalField;
import org.homeunix.thecave.moss.swing.MossPanel;
import org.homeunix.thecave.moss.util.DateFunctions;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

public class MyBudgetPanel extends MossPanel implements ActionListener {
	public static final long serialVersionUID = 0;

	private final JXTreeTable tree;
	private final JLabel balanceLabel;

	private final JSpinner dateSpinner;
	private final JComboBox periodTypeComboBox;

	private final BudgetTreeTableModel treeTableModel;
	
	private final MainFrame parent;
	
	public MyBudgetPanel(MainFrame parent) {
		super(true);
		this.parent = parent;
		this.treeTableModel = new BudgetTreeTableModel((DataModel) parent.getDocument());
		tree = new JXTreeTable(treeTableModel);

		balanceLabel = new JLabel();
		dateSpinner = new JSpinner(new BudgetDateSpinnerModel(treeTableModel));
//		dateSpinner = new JSpinner(new SpinnerDateModel(new Date(), DateFunctions.getDate(1900, Calendar.JANUARY), DateFunctions.getDate(3000, Calendar.DECEMBER), Calendar.MONTH));
//		monthComboBox = new JComboBox(new DefaultComboBoxModel(MonthKeys.values()));
		periodTypeComboBox = new JComboBox(BudgetPeriodType.values());
		
		open();
	}

	public List<BudgetCategory> getSelectedBudgetCategories(){
		List<BudgetCategory> budgetCategories = new LinkedList<BudgetCategory>();
		
		for (Integer i : tree.getSelectedRows()) {
			budgetCategories.add((BudgetCategory) tree.getModel().getValueAt(i, -1));
		}
		
		return budgetCategories;
	}
	
	public void actionPerformed(ActionEvent e) {
//		if (e.getSource().equals(monthComboBox)){
//			treeTableModel.setMonth(monthComboBox.getSelectedIndex());
//			updateContent();
//		}
		if (e.getSource().equals(periodTypeComboBox)){
			treeTableModel.setSelectedBudgetPeriodType((BudgetPeriodType) periodTypeComboBox.getSelectedItem());
			updateContent();
		}
	}

	public void init() {
		super.init();
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.setAutoResizeMode(JXTreeTable.AUTO_RESIZE_ALL_COLUMNS);
		tree.setClosedIcon(null);
		tree.setOpenIcon(null);
		tree.setLeafIcon(null);
		tree.setTreeCellRenderer(new BudgetCategoryNameCellRenderer());
		
//		dateSpinner.setModel(model)
//		

		for (int i = 1; i < treeTableModel.getColumnCount(); i++){
			MossDecimalField editor = new MossDecimalField(0, true, 2);
			final KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
			//This passes arrow keys on to the table, to allow cell navigation.
			manager.addKeyEventDispatcher(new KeyEventDispatcher(){
				public boolean dispatchKeyEvent(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_UP
							|| e.getKeyCode() == KeyEvent.VK_DOWN
							|| e.getKeyCode() == KeyEvent.VK_RIGHT
							|| e.getKeyCode() == KeyEvent.VK_LEFT){
						manager.redispatchEvent(tree, e);
						return true;
					}
					if ((OperatingSystemUtil.isMac() && e.isMetaDown()) || (!OperatingSystemUtil.isMac() && e.isControlDown())){
						manager.redispatchEvent(parent, e);
					}
					return false;
				}
			});
			tree.getColumn(i).setCellRenderer(new DecimalCellRenderer());
			tree.getColumn(i).setCellEditor(new DecimalCellEditor(editor));
		}
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addHighlighter(HighlighterFactory.createAlternateStriping(Const.COLOR_EVEN_ROW, Const.COLOR_ODD_ROW));
		
		tree.addTreeExpansionListener(new TreeExpansionListener(){
			public void treeCollapsed(TreeExpansionEvent event) {
				Object o = event.getPath().getPath()[event.getPath().getPath().length - 1];
				if (o instanceof BudgetCategory){
					BudgetCategory bc = (BudgetCategory) o;
					bc.setExpanded(false);
				}
			}
			public void treeExpanded(TreeExpansionEvent event) {
				Object o = event.getPath().getPath()[event.getPath().getPath().length - 1];
				if (o instanceof BudgetCategory){
					BudgetCategory bc = (BudgetCategory) o;
					bc.setExpanded(true);
				}				
			}
		});

		JScrollPane listScroller = new JScrollPane(tree);

//		dateSpinner.setPreferredSize(new Dimension(300, dateSpinner.getPreferredSize().height));
//		dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MMMM yyyy"));
		dateSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				if (dateSpinner.getValue() instanceof Date)
					treeTableModel.setSelectedDate((Date) dateSpinner.getValue());
				updateContent();
			}
		});
		
		periodTypeComboBox.setSelectedItem(BudgetPeriodType.BUDGET_PERIOD_MONTH);
		periodTypeComboBox.addActionListener(this);
		periodTypeComboBox.setRenderer(new TranslatorListCellRenderer());

		JPanel balanceLabelPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		balanceLabelPanel.add(balanceLabel);

		JPanel listScrollerPanel = new JPanel(new BorderLayout());
		listScrollerPanel.add(listScroller, BorderLayout.CENTER);
		listScrollerPanel.add(balanceLabelPanel, BorderLayout.SOUTH);

		JPanel spinnerPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		spinnerPanelRight.add(periodTypeComboBox);
		spinnerPanelRight.add(dateSpinner);


		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());

		mainPanel.add(spinnerPanelRight, BorderLayout.NORTH);
		mainPanel.add(listScrollerPanel, BorderLayout.CENTER);

		if (OperatingSystemUtil.isMac()){
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}

		tree.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent arg0) {
				parent.updateContent();
//				tree.editCellAt(tree.getSelectedRow(), tree.getSelectedColumn());
			}
		});
		
		updateButtons();

//		String dataFile = model.getFile() == null ? "" : " - " + model.getFile();
//		this.setTitle(PrefsModel.getInstance().getTranslator().get(BuddiKeys.MY_BUDGET) + dataFile + " - " + PrefsModel.getInstance().getTranslator().get(BuddiKeys.BUDDI));
//		this.setJMenuBar(new BudgetFrameMenuBar(this));

		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
	}

	public void updateContent() {
		super.updateContent();
		
		//Refresh the column headers.  For some stupid reason you have
		// to do it manually, even though the model itself updates and 
		// fires a change event.
		for (int i = 1; i < treeTableModel.getColumnCount(); i++)
			tree.getColumn(i).setHeaderValue(treeTableModel.getColumnName(i));
		
		//Update the date spinner
//		dateSpinner.setModel(new BudgetDateSpinnerModel(treeTableModel));
		dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, BudgetPeriodUtil.getDateFormat(treeTableModel.getSelectedBudgetPeriodType())));
//		dateSpinner.setEditor(new JSpinner.DefaultEditor(dateSpinner));
		
		//Fire a change event on the table model.
		treeTableModel.fireStructureChanged();
		
		//Restore the state of the expanded / unrolled nodes.
		for (BudgetCategory bc : ((DataModel) parent.getDocument()).getBudgetCategories()) {
			TreePath path = new TreePath(new Object[]{treeTableModel.getRoot(), bc});
			if (bc.isExpanded())
				tree.expandPath(path);
			else
				tree.collapsePath(path);
		}
	}
}
