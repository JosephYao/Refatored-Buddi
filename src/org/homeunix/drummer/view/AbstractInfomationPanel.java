/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.homeunix.thecave.moss.gui.abstracts.window.StandardContainer;

/**
 * @author wyatt
 *
 * The layout which ReportsPanel and GraphsPanel extend from.
 * 
 */
public abstract class AbstractInfomationPanel extends AbstractBuddiPanel {
	public static final long serialVersionUID = 0;
	protected final JPanel pluginsPanel;
	
	public AbstractInfomationPanel(){		
		pluginsPanel = new JPanel();
	}
	
	public StandardContainer init() {
		pluginsPanel.setLayout(new BoxLayout(pluginsPanel, BoxLayout.Y_AXIS));
		
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 7, 17));
		
		mainPanel.add(pluginsPanel, BorderLayout.NORTH);
		mainPanel.add(new JLabel(), BorderLayout.CENTER);
						
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);				

		return this;
	}
	
	public void actionPerformed(ActionEvent e) {}
	
	public StandardContainer updateButtons() {
		return this;
	}
	
	public StandardContainer updateContent() {
		return this;
	}
}
