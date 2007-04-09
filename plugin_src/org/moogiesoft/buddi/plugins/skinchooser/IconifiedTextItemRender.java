package org.moogiesoft.buddi.plugins.skinchooser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;


public class IconifiedTextItemRender extends DataItemRender 
{
	public final static long serialVersionUID = 0;
	
 //   static FileSystemView fileSystemView;
    
    static HashMap icons=new HashMap(10);
    private Icon icon;
    
    public IconifiedTextItemRender()
    {
        super();


    }
//    static Icon defaultIcon;
//    
//    static
//    {
//        try
//        {
//		    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//
//		    java.net.URL url = classLoader.getResource("com/moogiesoft/peeredFTP/images/icons/DefaultIcon.gif");
//
//
//            BufferedImage img=ImageIO.read(url);
//            Image img1=img.getScaledInstance(YSpacing,YSpacing,Image.SCALE_AREA_AVERAGING);
//            defaultIcon=new ImageIcon(img1);
//            
//            ImageIO.read(url);
//            
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            
//        }
//    }
    
    static public Icon getIcon(Object userID)
    {
        
        Icon icon=(Icon) icons.get(userID);
//        if (icon==null)
//        {
//            //TODO: request client to get the icon for this user
//            icons.put(userID,defaultIcon);
//            icon=defaultIcon;
//        }
        return icon;
        
    }
    
    @SuppressWarnings("unchecked")
	static public void setIcon(String ID,URL url) throws IOException
    {
		BufferedImage img=ImageIO.read(url);
		Image img1=img.getScaledInstance(YSpacing,YSpacing,Image.SCALE_AREA_AVERAGING);
		ImageIcon icon=new ImageIcon(img1);
		icons.put(ID,icon);
    }
    
    @SuppressWarnings("unchecked")
	static void setIconUseActualDimensions(String ID,URL url) throws IOException
    {
        BufferedImage img=ImageIO.read(url);
        ImageIcon icon=new ImageIcon(img);
        icons.put(ID,icon);
    }

    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus)
    
        {
        IconTextItem v=null;
        v=((IconTextItem) value);
        
        super.getVerifiedListCellRendererComponent(list,v.getName(),index,isSelected,cellHasFocus);
     

        
        
        
        icon=getIcon(v.getIconID());
        setIcon(icon);
        setPreferredSize(new Dimension(this.getSize().width,icon.getIconHeight()));
        
        return this;
    }
}
