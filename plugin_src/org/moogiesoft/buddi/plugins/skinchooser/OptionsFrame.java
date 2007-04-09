

package org.moogiesoft.buddi.plugins.skinchooser;
 
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.homeunix.drummer.prefs.PrefsInstance;


public class OptionsFrame extends JFrame implements ActionListener
{
	public final static long serialVersionUID = 0;

	private JButton skinChooser;
	private JButton quitButton;
	private JButton helpButton;
	private JButton changeButton;
	private Skin skin;
	
	static public void main (String[] args)
	{
		new OptionsFrame();
	}
	
	public void setLAF(Skin skin)
	{
	    skinChooser.setText(skin.name);
	    pack();
	    this.skin=skin;
	}
	
	public OptionsFrame()
	{
	}
	
	public void showFrame()
	{
		this.setTitle("Options");
   
        String name="none";
      
        try 
        {
			File settingsFile = new File(PrefsInstance.getInstance().getPreferencesFolder().getAbsolutePath() + File.separator + "ui.settings");
			
			if (settingsFile.exists())
			{
				BufferedReader br=new BufferedReader(new FileReader(settingsFile));
				
				String lnf=br.readLine();
				br.close();
				
		        skin=(Skin) SkinChooser.skins.get(lnf);
		        
		        if (skin!=null) name=skin.name;
			}

			skinChooser=new JButton(name);
			skinChooser.addActionListener(this);
			
			quitButton=new JButton("Quit");
			quitButton.addActionListener(this);
			
			changeButton=new JButton("Change");
			changeButton.addActionListener(this);
			
			helpButton=new JButton("Help");
			helpButton.addActionListener(this);

			
			JPanel panel=new JPanel();
			
			panel.add(new JLabel("Select a GUI Skin"));
			panel.add(skinChooser);
		
			JPanel topPanel=new JPanel(new BorderLayout());
			topPanel.setLayout(new BorderLayout());
			topPanel.add(BorderLayout.NORTH, panel);
			topPanel.add(BorderLayout.CENTER, changeButton);
			topPanel.add(BorderLayout.SOUTH,helpButton);
			getContentPane().add(BorderLayout.CENTER,topPanel);
			getContentPane().add(BorderLayout.SOUTH, quitButton);
	
	
			pack();
			setLocation(50,50);
			setVisible(true);
        }
        catch (Exception ex)
        {
		      ex.printStackTrace();
		      System.out.println("Error setting look and feel: " + ex);
		      String str="Error setting look and feel: " + ex+"\n";
		      
		      for (int i=0;i<ex.getStackTrace().length && i<25;i++)
		      {
		    	  str=str+ex.getStackTrace()[i].toString()+"\n";
		      }
		      JOptionPane.showMessageDialog(null,str);
        }
		
	}

	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==changeButton)
		{
			this.dispose();
			File file = new File(PrefsInstance.getInstance().getPreferencesFolder().getAbsolutePath() + File.separator + "ui.settings");
			try
			{
			    PrintStream ps=new PrintStream(new FileOutputStream(file));
                String skinEntryPoint="none";
                skinEntryPoint=skin.entryPoint;
			    ps.println(skinEntryPoint);
			    ps.close();
			    SkinChangerPlugin.reset();
			}
			catch (IOException ex)
			{

			      ex.printStackTrace();
			      System.out.println("Error writing settings: " + ex);
			      String str="Error writing settings: " + ex+"\n";
			      
			      for (int i=0;i<ex.getStackTrace().length && i<25;i++)
			      {
			    	  str=str+ex.getStackTrace()[i].toString()+"\n";
			      }
			      JOptionPane.showMessageDialog(null,str);
			}
		}
		else if(e.getSource()==skinChooser)
		{
			new SkinChooser(this);
		}
		else if (e.getSource()==helpButton)
		{
			new HelpFrame().showFrame();
		}
        else
        {
        	this.dispose();
        }

	}
}

