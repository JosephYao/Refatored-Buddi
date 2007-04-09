package org.moogiesoft.buddi.plugins.skinchooser;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class DataItemRender extends JLabel implements ListCellRenderer 
{
	public final static long serialVersionUID = 0;
    
    public DataItemRender()
    {
        super();
        setOpaque(true);
    }
    static public int YSpacing;

    
    static
    {
        YSpacing=FileSystemView.getFileSystemView().getSystemIcon(new File(System.getProperty("user.dir"))).getIconHeight();
        JLabel l=new JLabel("TESTING 123");
        YSpacing=Math.max(YSpacing,l.getHeight());
        
    }
    
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus)
    
     {
        DataItem v=null;
        v=((DataItem) value);

        return getVerifiedListCellRendererComponent(list,v.getName(),index,isSelected,cellHasFocus);
     }
    
    protected Component getVerifiedListCellRendererComponent(
        JList list,
        String v,
        int index,
        boolean isSelected,
        boolean cellHasFocus)

    {    
        
        
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        

        
        setText(v);
        setPreferredSize(new Dimension(this.getSize().width,YSpacing));
        
        setFont(list.getFont());
        
        return this;
    }
}
