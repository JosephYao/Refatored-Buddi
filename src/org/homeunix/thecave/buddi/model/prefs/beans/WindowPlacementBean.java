/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.prefs.beans;

import java.awt.Dimension;
import java.awt.Point;

public class WindowPlacementBean {
	private Dimension size;
	private Point location;
	
	
	public Point getLocation() {
		return location;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public Dimension getSize() {
		return size;
	}
	public void setSize(Dimension size) {
		this.size = size;
	}
}
