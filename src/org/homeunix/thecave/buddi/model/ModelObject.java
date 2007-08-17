/*
 * Created on Aug 11, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.beans.ModelObjectBean;
import org.homeunix.thecave.buddi.model.exception.DataModelProblemException;

public abstract class ModelObject {
	private final DataModel model;
	private final ModelObjectBean bean;
	
	public ModelObject(DataModel model, ModelObjectBean bean) {
		if (model == null)
			throw new DataModelProblemException("Model cannot be null", getModel());
		if (bean == null)
			throw new DataModelProblemException("Model Bean cannot be null.", getModel());
		this.model = model;
		this.bean = bean;
	}
	
	DataModel getModel(){
		return model;
	}
	
	/**
	 * Call this from the model absraction layer after all operations which
	 * change a value. 
	 */
	public void modify(){
		//TODO Call this on change operations for all objects 
		getBean().setModifiedDate(new Date());
	}

	@Override
	public int hashCode() {
		return bean.getUid().hashCode();
	}
	
	ModelObjectBean getBean(){
		return bean;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof ModelObject)
			return getBean().equals(((ModelObject) obj).getBean());
		
		return false;
	}
}
