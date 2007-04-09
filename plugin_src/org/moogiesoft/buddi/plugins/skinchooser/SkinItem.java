package org.moogiesoft.buddi.plugins.skinchooser;



public class SkinItem extends IconTextItem
{
    private Skin skin;
    
    public Skin getSkin()
    {
        return skin;
    }
    
    private SkinItem()
    {
        super(null,null,null);
    }
    
    public SkinItem(Skin skin)
    {
        super(skin.name,skin.entryPoint);
        this.skin=skin;
    }
    
    public SkinItem(Object[] data, String name, String[] dataDescription)
    {
        super(data,name,dataDescription);
 
        
    }

}
