/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class ReportPanelLayout extends JPanel {
	public static final long serialVersionUID = 0;
	protected final JPanel reportsPanel;
	
	
	protected ReportPanelLayout(){		
		reportsPanel = new JPanel();
		reportsPanel.setLayout(new BoxLayout(reportsPanel, BoxLayout.Y_AXIS));
				
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 7, 17));
		
		mainPanel.add(reportsPanel, BorderLayout.NORTH);
		mainPanel.add(new JLabel(), BorderLayout.CENTER);
						
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);				
	}	
}
