package org.homeunix.thecave.buddi.util;

import java.awt.Color;

public class ColorUtil {
	public static Color desaturate(Color c){
		final int highestValue = Math.max(c.getBlue(), Math.max(c.getRed(), c.getGreen()));
		final Color result = new Color(((highestValue * 2) + c.getRed()) / 3, ((highestValue * 2) + c.getGreen()) / 3, ((highestValue * 2) + c.getBlue()) / 3);
		return result;
	}
}
