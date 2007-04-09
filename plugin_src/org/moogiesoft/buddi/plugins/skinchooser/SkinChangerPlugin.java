package org.moogiesoft.buddi.plugins.skinchooser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.homeunix.drummer.plugins.interfaces.BuddiPlugin;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.MainFrame;



public class SkinChangerPlugin implements BuddiPlugin, Runnable
{


	// NOTE! It seems that i have to use System Properties as when Buddi is 
	// loaded by the windows executable loader static variables get 
	// reset and so singletons are impossible!
	
	/**
	 * Constructor
	 * 
	 * When an SkinChangerPlugin object is first constructed no logic is performed. Otherwise when Buddi first adds the plugin Buddi will be reset and
	 * so the plugin is never actually added to Buddi's list of plugins.
	 * 
	 * The second time a SkinChangerPlugin is constructed the look and feel (aka skin) is loaded and set and buddi is restarted.
	 * 
	 * Subsequent constructions of SkinChangerPlugin objects will only cause a change in look and feel and restart buddi if the reset() method has been
	 * called previously.
	 * 
	 */
	public SkinChangerPlugin()
	{
		try
		{
			File settingsFile = new File(PrefsInstance.getInstance().getPreferencesFolder().getAbsolutePath() + File.separator + "ui.settings");
			
			String lnf="none";
			if (settingsFile.exists())
			{
				BufferedReader br=new BufferedReader(new FileReader(settingsFile));

				// read in the look and feel identifer (the initalizer class) from the settings file
		        lnf=br.readLine();
		        br.close();
			}
		
			// make sure it is not the first object to be constructed and we want to change the look and feel...
			if (System.getProperties().getProperty("FIRST")!=null && (System.getProperties().getProperty("LNF_LOADING")==null))
			{
				  System.getProperties().setProperty("LNF_LOADING","true");

				  // obtain the the associated skin if present
				  Skin skin=(Skin) SkinChooser.skins.get(lnf);
				  
				  if (skin!=null)
				  {
				      
				      for (int i=0;i<skin.urls.length;i++)
				      {
				          addURL(skin.urls[i]);
				      }
				      
				      UIManager.setLookAndFeel(lnf);
				
				      // if the skin has an additional configuration class associated with it, initalize the class and perform additional configuration.
					  try
					  {
					      Class configureClass=skin.getClass().getClassLoader().loadClass("Configure");
					      if (configureClass != null)
					      {
					
					    	  Class[] classes=new Class[1];
					    	  classes[0]=Object.class;
					    	  Method method=configureClass.getMethod("configure",classes);
					    	  Object[] objects=new Object[1];
					    	  objects[0]=UIManager.getLookAndFeel();
					    	  method.invoke(configureClass.newInstance(),objects);
					
					      }
					  }
					  catch (ClassNotFoundException ee)
					  {
					      System.out.println("Non-fatal error: No configuration class associated with "+skin.name+" skin.");
				      }
				  }
				  
				 JFrame.setDefaultLookAndFeelDecorated(true);	// to decorate frames 
				 JDialog.setDefaultLookAndFeelDecorated(true);	// to decorate dialogs
				 
				 // start the thread to show the options frame
			     new Thread(this,"wait for main GUI").start();
			}
			
			// set the "first-time" property flag
			System.getProperties().setProperty("FIRST","true");
		}
	  catch(Exception ex)
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
	
	/**
	 * Resets the look and feel of Buddi
	 *
	 */
	public static void reset()
	{
		System.getProperties().remove("LNF_LOADING");
		new SkinChangerPlugin();
	}
	
	/**
	 * Adds a JAR URL to the system Class Loader. Used to load the look and feel JARs at run time.
	 * 
	 * @param url the URL to the JAR
	 * @throws IOException
	 */
    static public void addURL(URL url) throws IOException
    {
      URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
      Class sysclass = URLClassLoader.class;
      try
      {
        Method method = sysclass.getDeclaredMethod("addURL", new Class[]{URL.class});
        method.setAccessible(true);
        method.invoke(sysloader, new Object[]{url});
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
	
	public String getDescription() {
		return "SkinChangerPlugin";
	}

	public void run() {
		try
		{
			
			// wait until the Main Buddi Frame is constucted. This needs to occur as 
			// when Buddi restarts after adding a plugin the look and 
			// feel is not correctly updated. 
			while (MainFrame.getInstance()==null)
			{
				Thread.sleep(100);
			}
			
			// restart Buddi
			MainFrame.restartProgram();
			
			// wait until the Main Buddi Frame is constucted.
			while (MainFrame.getInstance()==null)
			{
				Thread.sleep(100);
			}

		}catch (Exception e)
		{
			e.printStackTrace();
		}
			
		// display the plugin' options frame
		new OptionsFrame().showFrame();
	}

}
