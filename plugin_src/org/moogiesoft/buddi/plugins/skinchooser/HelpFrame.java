package org.moogiesoft.buddi.plugins.skinchooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.rtf.RTFEditorKit;

import org.homeunix.drummer.Buddi;



public class HelpFrame extends JFrame{

	public final static long serialVersionUID = 0;
	
	public HelpFrame()
	{
	}
	
	/**
	 * Displays the Help file Frame
	 *
	 */
	public void showFrame()
	{
		this.setTitle("Help");

		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		
		// Create an RTF editor window
		RTFEditorKit rtf = new RTFEditorKit();
		JEditorPane editor = new JEditorPane();
		editor.setEditorKit( rtf );
		editor.setBackground( Color.white );

		// This text could be big so add a scroll pane
		JScrollPane scroller = new JScrollPane();
		scroller.getViewport().add( editor );
		scroller.setAutoscrolls(false);
		
		topPanel.add( scroller, BorderLayout.CENTER );

		// Load an RTF file into the editor
		try {
			InputStream in=new FileInputStream(new File(Buddi.getWorkingDir()+File.separator+"help.rtf"));
			if (in==null)
			{
				JOptionPane.showMessageDialog(null,"Help File Not Found!");
			}
			else
				rtf.read( in, editor.getDocument(), 0 );
		}
		catch( FileNotFoundException e )
		{
			JOptionPane.showMessageDialog(null,"Help File Not Found!");
		}
		catch( IOException e )
		{
			JOptionPane.showMessageDialog(null,"Error reading Help File!");
		}
		catch( BadLocationException e )
		{
			JOptionPane.showMessageDialog(null,"Help File Not Found!");
		}
		
		editor.setCaretPosition(0);
		editor.setEditable(false);
	
		topPanel.setPreferredSize(new Dimension(480,640));
		setContentPane(topPanel);
		pack();
        this.setLocationRelativeTo(null);
		setVisible(true);
	}
}