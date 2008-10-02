/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.Map;

import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.TransactionSplit;
import org.homeunix.thecave.buddi.plugin.api.exception.InvalidValueException;

public class TransactionSplitImpl extends ModelObjectImpl implements TransactionSplit {	
	
	private long amount;
	private Source source;
	
	public long getAmount(){
		return amount;
	}
	
	public Source getSource(){
		return source;
	}
	
	public void setAmount(long amount){
		setChanged();
		this.amount = amount;
	}
	
	public void setSource(Source source) throws InvalidValueException {
		setChanged();
		this.source = source;
	}
	
	TransactionSplit clone(Map<ModelObject, ModelObject> originalToCloneMap) throws CloneNotSupportedException {

		if (originalToCloneMap.get(this) != null)
			return (TransactionSplit) originalToCloneMap.get(this);
		
		TransactionSplitImpl t = new TransactionSplitImpl();

		t.document = (Document) originalToCloneMap.get(document);
		t.amount = amount;
		t.source = (Source) ((SourceImpl) source).clone(originalToCloneMap);
		t.modifiedTime = new Time(modifiedTime);
		
		originalToCloneMap.put(this, t);

		return t;
	}
}
