/*
 * Created on Sep 2, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.impl.ModelFactory;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.model.swing.BackupManagerListModel;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.util.InternalFormatter;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.moss.exception.DocumentLoadException;
import org.homeunix.thecave.moss.exception.DocumentSaveException;
import org.homeunix.thecave.moss.exception.OperationCancelledException;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.MossDialog;
import org.homeunix.thecave.moss.swing.MossDocumentFrame;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;
import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.decorator.HighlighterFactory;

public class BackupManagerDialog extends MossDialog implements ActionListener {
	public static final long serialVersionUID = 0;

	private final JXList backupList;
	private final BackupManagerListModel model;
	private final Document document;
	private final JButton loadButton;
	private final JButton cancelButton;
	
	public BackupManagerDialog(MossDocumentFrame owner) {
		super(owner);
		
		document = (Document) owner.getDocument();
		model = new BackupManagerListModel(document);
		
		backupList = new JXList(model);
		loadButton = new JButton(TextFormatter.getTranslation(ButtonKeys.BUTTON_LOAD));
		cancelButton = new JButton(TextFormatter.getTranslation(ButtonKeys.BUTTON_CANCEL));
	}
	
	@Override
	public void init() {
		super.init();

		cancelButton.setPreferredSize(InternalFormatter.getButtonSize(cancelButton));
		loadButton.setPreferredSize(InternalFormatter.getButtonSize(loadButton));
		
		cancelButton.addActionListener(this);
		loadButton.addActionListener(this);
		
		backupList.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				updateButtons();
			}
		});
		
		backupList.addHighlighter(HighlighterFactory.createAlternateStriping(Const.COLOR_EVEN_ROW, Const.COLOR_ODD_ROW));
		backupList.setCellRenderer(new DefaultListCellRenderer(){
			public static final long serialVersionUID = 0;
			
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			
				if (value instanceof File){
					File f = (File) value;
					Date date = model.getDate(f);
					String formattedDate = Formatter.getDateFormat(PrefsModel.getInstance().getDateFormat() + " HH:mm:ss").format(date);
					
					this.setText(formattedDate + " (" + f.getName() + ")");
				}
				
				return this;
			}
		});
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(cancelButton);
		buttonPanel.add(loadButton);
		
		JScrollPane scroller = new JScrollPane(backupList);
		scroller.setPreferredSize(new Dimension(350, 250));
		
		this.setTitle(TextFormatter.getTranslation(BuddiKeys.BACKUP_MANAGER));
		this.setLayout(new BorderLayout());
		this.add(scroller, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.getRootPane().setDefaultButton(loadButton);
		
	}
	
	@Override
	public void updateButtons() {
		super.updateButtons();
		
		loadButton.setEnabled(backupList.getSelectedIndex() != -1);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(cancelButton)){
			this.closeWindow();
		}
		else if (e.getSource().equals(loadButton)){
			
			String[] options = new String[2];
			options[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_YES);
			options[1] = TextFormatter.getTranslation(ButtonKeys.BUTTON_NO);

			if (JOptionPane.showOptionDialog(
					null, 
					TextFormatter.getTranslation(BuddiKeys.MESSAGE_RESTORE_FROM_BACKUP),
					TextFormatter.getTranslation(BuddiKeys.MESSAGE_RESTORE_FROM_BACKUP_TITLE),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE,
					null,
					options,
					options[0]
			) == 0) {  //The index of the Yes button.
				try {
					try {
						document.save();
					}
					catch (DocumentSaveException dse){
						Log.error("Error saving data file prior to restore.  Continuing with restore anyway.");
						dse.printStackTrace(Log.getPrintStream());
					}					
					
					Document newDoc = ModelFactory.createDocument((File) backupList.getSelectedValue());
					newDoc.setFile(document.getFile());
					
					MainFrame mainWndow = new MainFrame(newDoc);
					mainWndow.openWindow(
							PrefsModel.getInstance().getWindowSize(newDoc.getFile() + ""), 
							PrefsModel.getInstance().getWindowLocation(newDoc.getFile() + ""), 
							true);
					
					Log.info("User restored file " + ((File) backupList.getSelectedValue()).getName() + " to " + document.getFile().getAbsolutePath());
					closeWindow();
				}
				catch (OperationCancelledException oce){}
				catch (WindowOpenException woe){
					woe.printStackTrace(Log.getPrintStream());
				}
				catch (DocumentLoadException dle){
					Log.error("There was an error loading the file " + document.getFile());
					dle.printStackTrace(Log.getPrintStream());
				}
			}
			else {
				Log.info("User cancelled file restore.");
			}
			
		}
	}
}
