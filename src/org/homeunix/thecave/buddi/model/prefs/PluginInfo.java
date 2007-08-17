/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.prefs;

import org.homeunix.thecave.buddi.model.prefs.beans.PluginInfoBean;

public class PluginInfo {

	private PluginInfoBean bean;
	
	public PluginInfo() {
		this(new PluginInfoBean());
	}
	
	PluginInfo(PluginInfoBean bean) {
		this.bean = bean;
	}
	
	PluginInfoBean getBean(){
		return bean;
	}
	
	public String getName() {
		return bean.getName();
	}
	public void setName(String name) {
		bean.setName(name);
	}
	public String getClassName() {
		return bean.getClassName();
	}
	public void setClassName(String className) {
		bean.setClassName(className);
	}
	public String getJarFile() {
		return bean.getJarFile();
	}
	public void setJarFile(String jarFile) {
		bean.setJarFile(jarFile);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PluginInfo)
			return bean.equals(((PluginInfo) obj).getBean());
		return false;
	}
	
	@Override
	public int hashCode() {
		return bean.hashCode();
	}
}
