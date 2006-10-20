/*
 * Created on Oct 19, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.homeunix.drummer.controller.MainBuddiFrame;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;

public class JDocumentDialog extends JDialog {
	public static final long serialVersionUID = 0;
	
	public JDocumentDialog(String document) {
		JTextArea docArea = new JTextArea(document);
		docArea.setEditable(false);
		
		JScrollPane docScroller = new JScrollPane(docArea);
		
		JButton done = new JButton(Translate.getInstance().get(TranslateKeys.DONE));
		done.setPreferredSize(new Dimension(Math.max(100, done.getPreferredSize().width), done.getPreferredSize().height));
		done.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JDocumentDialog.this.setVisible(false);
			}
		});
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(done);
		
		this.setPreferredSize(new Dimension(640, 480));
		this.setLayout(new BorderLayout());
		this.add(docScroller, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.pack();
		this.setLocationRelativeTo(MainBuddiFrame.getInstance());
		this.setVisible(true);
	}
}
