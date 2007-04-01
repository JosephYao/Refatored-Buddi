/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author wyatt
 *
 * The layout which ReportsPanel and GraphsPanel extend from.
 * 
 */
public abstract class InfomationPanelLayout extends JPanel {
	public static final long serialVersionUID = 0;
	protected final JPanel pluginsPanel;
	
	
	protected InfomationPanelLayout(){		
		pluginsPanel = new JPanel();
		pluginsPanel.setLayout(new BoxLayout(pluginsPanel, BoxLayout.Y_AXIS));
				
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 7, 17));
		
		mainPanel.add(pluginsPanel, BorderLayout.NORTH);
		mainPanel.add(new JLabel(), BorderLayout.CENTER);
						
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);				
	}	
}
