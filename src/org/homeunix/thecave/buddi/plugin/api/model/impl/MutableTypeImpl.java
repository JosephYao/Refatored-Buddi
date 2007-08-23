/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import org.homeunix.thecave.buddi.model.Type;
import org.homeunix.thecave.buddi.plugin.api.model.MutableType;

public abstract class MutableTypeImpl extends ImmutableTypeImpl implements MutableType {

	public MutableTypeImpl(Type type) {
		super(type);
	}
}
