/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.Map;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.Split;
import org.homeunix.thecave.buddi.model.TransactionSplit;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;


public class SplitImpl extends SourceImpl implements Split {
	
	@Override
	ModelObject clone(Map<ModelObject, ModelObject> originalToCloneMap) throws CloneNotSupportedException {
		if (originalToCloneMap.get(this) != null)
			return (TransactionSplit) originalToCloneMap.get(this);

		TransactionSplitImpl t = new TransactionSplitImpl();

		t.document = (Document) originalToCloneMap.get(document);
		t.modifiedTime = new Time(modifiedTime);

		originalToCloneMap.put(this, t);

		return t;
	}
	
	public String getFullName() {
		return PrefsModel.getInstance().getTranslator().get(BuddiKeys.SPLIT);
	}
}
