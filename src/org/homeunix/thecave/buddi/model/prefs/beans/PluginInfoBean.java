/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.prefs.beans;



public class PluginInfoBean {
	private String jarFile;
	private String className;
	private String name;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getJarFile() {
		return jarFile;
	}
	public void setJarFile(String jarFile) {
		this.jarFile = jarFile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
