/*
 * Created on 8/11/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.moogiesoft.buddi.plugins.skinchooser;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.homeunix.drummer.Buddi;
import org.homeunix.thecave.moss.util.Log;



/**
 * @author KlaebeN
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class SkinChooser extends JFrame implements ListSelectionListener,ActionListener
{
	public final static long serialVersionUID = 0;
	
    public static HashMap skins;

    JList list;
    
    OptionsFrame callback;

    JLabel screenShot;
    JFrame frame;
    JButton selectButton;
    JButton cancelButton;
    
    static
    {
        skins = new HashMap();
        try
        {
            // get the system class loader
//            ClassLoader classLoader = ClassLoader.getSystemClassLoader();

            URL url;

            // create a File instance representing the the skin directory
            File rootFile = new File(Buddi.getWorkingDir()+File.separator+"skins");


            // if the directory exists then for each subfolder attempt to load the Look and Feel 
            if (rootFile!=null && rootFile.exists())
            {
                File[] dir = rootFile.listFiles();

                Log.debug(dir);
                
                for (int i = 0; i < dir.length; i++)
                {
                    // attempt to find the information file associated with the 'skin'
                    File infoFile = new File(dir[i].getCanonicalPath() + File.separator + "skinInfo.txt");
                    
                    Log.debug("Looking for file " + infoFile.getAbsolutePath());
                    
                    // if found....
                    if (infoFile.exists())
                    {
                        Skin skin = new Skin();
                        
                        // read in the skin's name and the class which the Look and Feel uses to initalize. 
                        BufferedReader br=new BufferedReader(new FileReader(infoFile));
                        skin.name = br.readLine();
                        skin.entryPoint = br.readLine();

                        // find all the JAR files in the sub directory
                        File[] files = dir[i].listFiles();
                        ArrayList list = new ArrayList();
                        list.add(dir[i]);
                        for (int j = 0; j < files.length; j++)
                        {
                            if (files[j].getName().toLowerCase().endsWith(".jar"))
                                list.add(files[j]);
                        }

                        // create a list of URLs of any JAR files found
                        URL[] urls = new URL[list.size()];
                        for (int j = 0; j < list.size(); j++)
                        {
                            urls[j] = ((File) list.get(j)).toURL();
                        }
                        skin.urls=urls;

                        // load the skin's preview icon
                        url = (new File(dir[i].getCanonicalPath() + File.separator + "preview.png")).toURL();
                        IconifiedTextItemRender.setIconUseActualDimensions(skin.entryPoint, url);

                        // load the skin's full size screen shot
                        url = (new File(dir[i].getCanonicalPath() + File.separator + "screenshot.png")).toURL();
                        skin.screenShot = ImageIO.read(url);


                        skins.put(skin.entryPoint, skin);
                    }

                }

            }
        } catch (Exception e)
        {
            System.out.println("Error Loading Skins...");
            e.printStackTrace();
        }
    }

    public SkinChooser(OptionsFrame callback)
    {
        
        this.callback=callback;
        frame=this;
        selectButton=new JButton("Select");
        cancelButton=new JButton("Cancel");
        selectButton.addActionListener(this);
        cancelButton.addActionListener(this);
        
        FlowLayout flow=new FlowLayout(FlowLayout.RIGHT);
        flow.setHgap(10);
        JPanel panel=new JPanel(flow);
        panel.add(cancelButton);
        panel.add(selectButton);
        
		this.setTitle("Skin Chooser");

        
        screenShot = new JLabel();
        screenShot.setPreferredSize(new Dimension(500, 375));
        list = new JList();
        list.addListSelectionListener(this);
        list.setCellRenderer(new IconifiedTextItemRender());
        DefaultListModel model = new DefaultListModel();


        if (skins.size() < 1)
        {
            model.addElement(new IconTextItem("No skins found in plugin directory.", "noSkins"));
        } else
        {
            Iterator it = skins.values().iterator();
            ArrayList tempList=new ArrayList();
            while (it.hasNext())
            {
                tempList.add(new SkinItem((Skin) it.next()));
            }
            Collections.sort(tempList);
            
            it=tempList.iterator();
            
            while (it.hasNext())
            {
                model.addElement(it.next());
            }
        }

        list.setModel(model);
        list.setSelectedIndex(0);
        
        //JPanel p=new JPanel(new BorderLayout());
        //p.setPreferredSize(new Dimension(400,600));
        //p.add(list,BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(250, 375));

        // list.set

        //scrollPane.add(p);

        setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.WEST);
        getContentPane().add(screenShot, BorderLayout.CENTER);
        getContentPane().add(panel, BorderLayout.SOUTH);

        setSize(750, 440);
        setLocationRelativeTo(null);
        //Dimension higt=this.
        setVisible(true);
        //        }
        //        catch (IOException e)
        //        {
        //            e.printStackTrace();
        //        }

        //  model.addElement()
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
public void valueChanged(ListSelectionEvent e)
    {
        
        //System.out.println(index);
        for (int index=e.getFirstIndex();index<=e.getLastIndex();index++)
        {
            if (list.isSelectedIndex(index))
	        
	        {
	            //System.out.println("a");
	            SkinItem skinItem=((SkinItem)list.getModel().getElementAt(index));
	            Skin skin=skinItem.getSkin();
	            //panel.getGraphics().drawImage(skin.screenShot,0,0,null);
	            //panel.repaint();
	            screenShot.setIcon(new ImageIcon(skin.screenShot));

	            //frame.dispose();
	            //Gui.runTimeLookAndFeelChange(skin);
	        }
        }
        
        
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        // TODO Auto-generated method stub
        
        Object src=e.getSource();
        
        if (src==cancelButton)
        {
            frame.dispose();
        }
        else if (src==selectButton)
        {
            SkinItem skinItem=(SkinItem)list.getSelectedValue();
            if (skinItem!=null)
            {
                if (skinItem.getSkin()!=null)
                {
	                try
	                {
	                	callback.setLAF(skinItem.getSkin());
	                	
	                	/** below is code to allow for changes in skin parameters... currently not working correctly. */
//	                    
//	                    // associate a new URLClassLoader with the skin using the list of URLs
//	                 //   URLClassLoader classLoader = new URLClassLoader(skinItem.getSkin().urls, ClassLoader.getSystemClassLoader());
//	                    
//	                    for (int i=0;i<skinItem.getSkin().urls.length;i++)
//	                    {
//	                    	SkinChangerPlugin.addURL(skinItem.getSkin().urls[i]);
//	                    }
//	                    
//	                    // attempt to find an optional configuration class, if found associate the class with the skin instance.
//	                    try
//	                    {
//	                        Class configureClass = ClassLoader.getSystemClassLoader().loadClass("Configure");
//	                        
//	                            Object config=configureClass.newInstance();
//	//                            config.settingsWindow(config);
//	                            
//			                	  Class[] classes=new Class[1];
//			                	  classes[0]=Object.class;
//			                	  Method method=configureClass.getMethod("settingsWindow",classes);
//			                	  Object[] objects=new Object[1];
//			                	  objects[0]=config;
//			                	  method.invoke(configureClass.newInstance(),objects);
//
//	                    } catch (ClassNotFoundException ee)
//	                    {
//	
//	                    }

	                    
	
		                frame.dispose();
	                }
                    catch (Exception eee)
                    {
                    	eee.printStackTrace();
        		      System.out.println("Error: " + eee);
        		      String str="Error: " + eee+"\n";
        		      
        		      for (int i=0;i<eee.getStackTrace().length && i<25;i++)
        		      {
        		    	  str=str+eee.getStackTrace()[i].toString()+"\n";
        		      }
        		      JOptionPane.showMessageDialog(null,str);
                    }
                }
            }
            
        }
        
    }
}