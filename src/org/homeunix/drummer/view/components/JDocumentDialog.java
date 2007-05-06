/*
 * Created on Oct 19, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractDialog;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardContainer;

public class JDocumentDialog extends AbstractDialog {
	public static final long serialVersionUID = 0;
	
	private final JTextArea docArea;
	
	public JDocumentDialog(String document) {
		super((Frame) null);
		docArea = new JTextArea(document);
	}
	
	public StandardContainer init() {
		docArea.setWrapStyleWord(true);
		docArea.setLineWrap(true);
		docArea.setEditable(false);
		
		JScrollPane docScroller = new JScrollPane(docArea);
		docScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		docScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JButton done = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_DONE));
		done.setPreferredSize(new Dimension(Math.max(100, done.getPreferredSize().width), done.getPreferredSize().height));
		done.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JDocumentDialog.this.closeWindow();
			}
		});
		
		this.getRootPane().setDefaultButton(done);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(done);
		
		this.setLayout(new BorderLayout());
		this.add(docScroller, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);

		
		return this;
	}
	
	public StandardContainer updateButtons() {
		return this;
	}
	
	public StandardContainer updateContent() {
		return this;
	}
}
