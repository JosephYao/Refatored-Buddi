/*
 * Created on Aug 11, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import org.homeunix.thecave.buddi.model.beans.ModelObjectBean;

public interface ModelObject extends Comparable<ModelObject> {
	
	/**
	 * Returns the model associated with this given model object.  This is not 
	 * meant for direct access, and doing so can easily break the data model.
	 * Don't use this unless you know exactly what you are doing!
	 * @return
	 */
	public DataModel getModel();
	
	/**
	 * Returns the bean associated with this given model object.  This is not 
	 * meant for direct access, and doing so can easily break the data model.
	 * Don't use this unless you know exactly what you are doing!
	 * @return
	 */
	public ModelObjectBean getBean();
	
	/**
	 * Call this from the model absraction layer after all operations which
	 * change a value. 
	 */
	public void modify();

	/**
	 * This should be implemented to return the same value for otherwise
	 * equal objects.  The suggested method is to return the hashCode
	 * for the UID string.
	 * @return
	 */
	public int hashCode();
		
	public int compareTo(ModelObject o);
	
	/**
	 * Return true for equal values.  The suggested method for this is
	 * to return equals() for the UID strings.
	 * @param obj
	 * @return
	 */
	public boolean equals(Object obj);
	
	/**
	 * Returns the UID string for this object.
	 * @return
	 */
	public String getUid();
}
