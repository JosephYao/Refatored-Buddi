/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.plugin.api.model.MutableSource;

public abstract class MutableSourceImpl extends ImmutableSourceImpl implements MutableSource {

	public MutableSourceImpl(Source source) {
		super(source);
	}
}
