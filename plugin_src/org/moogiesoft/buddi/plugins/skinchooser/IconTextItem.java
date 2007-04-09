/*
 * Created on 28/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author KlaebeN
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.moogiesoft.buddi.plugins.skinchooser;




public class IconTextItem extends DataItem
{
    private static final String[] DES={"Name","ID"};
    

    private String lowercaseName;
    private Object iconID;

    
    public String getLowerCaseName()
    {
        return lowercaseName;
    }
    
    public void setName(String name)
    {
        super.setName(name);
        lowercaseName=name.toLowerCase();
    }
    
    public void setIconID(Object id)
    {
        getData()[1]=id;
        iconID=id;
    }
    
    public Object getIconID()
    {
        return iconID;
    }
    

    
    private IconTextItem()
    {
        super(null,null,null);
    }
    
    public IconTextItem(String name,Object id)
    {
        super(new Object[2],name,DES);
       
        getData()[1]=id;
        
        lowercaseName=name.toLowerCase();
        this.iconID=id;
        
        
    }
    
    public IconTextItem(Object[] data, String name, String[] dataDescription)
    {
        super(data,name,dataDescription);
 
        
    }

    public int compareTo(Object obj)
    {
        IconTextItem other=(IconTextItem) obj;
        return lowercaseName.compareTo(other.getLowerCaseName());

    }

    

}
