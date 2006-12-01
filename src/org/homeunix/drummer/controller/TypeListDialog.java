/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Type;
import org.homeunix.drummer.view.AbstractDialog;
import org.homeunix.drummer.view.TypesListDialogLayout;
import org.homeunix.thecave.moss.util.Log;

public class TypeListDialog extends TypesListDialogLayout {
	public static final long serialVersionUID = 0;

	public TypeListDialog(){
		super(MainBuddiFrame.getInstance());
	}
		
	@Override
	protected AbstractDialog initActions() {
		doneButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				TypeListDialog.this.setVisible(false);
				TypeListDialog.this.dispose();
			}
		});
		
		newButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new TypeModifyDialog().openWindow();
				if (Const.DEVEL) Log.debug("Done creating");
				updateContent();
			}
		});
		
		editButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Object o = list.getSelectedValue();
				if (o instanceof Type){
					Type t = (Type) o;
					new TypeModifyDialog().loadType(t).openWindow();
					if (Const.DEVEL) Log.debug("Done editing.");
					updateContent();
				}
			}
		});
		
		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				TypeListDialog.this.updateButtons();
			}
		});
		
		return this;
	}

	@Override
	protected AbstractDialog initContent() {
		updateContent();
		
		return this;
	}

	public AbstractDialog updateContent(){
		
		Vector<Type> types = DataInstance.getInstance().getTypes();
		list.setListData(types);
		
		return this;
	}


}
