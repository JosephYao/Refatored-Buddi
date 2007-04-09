
package org.moogiesoft.buddi.plugins.skinchooser;


abstract public class DataItem implements Comparable
{
    // the first cell of the data needs to be a String which names/identifies the data.
    private Object[] data;
    private String name;
    private String[] dataDescription;
    
    private DataItem()
    {
        
    }
    
    public DataItem(Object[] data, String name, String[] dataDescription)
    {
        this.data=data;
        this.name=name;
        data[0]=name;
        this.dataDescription=dataDescription;
    }
    
    public void setName(String name)
    {
        this.name=name;
        data[0]=name;
    }
    
    public Object[] getData()
    {
        return data;
    }
    public String getName()
    {
        return name;
    }
    
    public String[] getDataDescription()
    {
        return dataDescription;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    abstract public int compareTo(Object o);

    
}
