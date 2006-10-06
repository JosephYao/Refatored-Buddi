/*
 * Created on Oct 5, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import org.homeunix.drummer.view.components.text.JHintTextField;

public class SearchField extends JPanel {
	public static final long serialVersionUID = 0;

	private final JHintTextField text;
	private final JLabel button;

	public SearchField(String hint){
		super(new BorderLayout());
		text = new JHintTextField(hint);
		this.add(text, BorderLayout.CENTER);

		button = new CancelButton();
		this.add(button, BorderLayout.EAST);

		text.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					cancelButton();
				}
				else {
					changed();
				}
			}
		});
	}

	public String getText(){
		if (text.isTextFieldEmpty())
			return "";
		else
			return text.getText();
	}

	class CancelButton extends JLabel {
		public static final long serialVersionUID = 0;

		private final int size;

		CancelButton(){
			this(15);
		}

		CancelButton(int size){
			super();
			this.setPreferredSize(new Dimension(size + 3, size));
			this.size = size;

			this.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					SearchField.this.cancelButton();
				}
			});
		}

		@Override
		public void paint(Graphics oldGraphics) {
			super.paint(oldGraphics);

			Graphics2D g = (Graphics2D) oldGraphics;

			//Draw the circle
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			final int circleL = size;
			final int circleX = this.getWidth() - circleL - 3;
			final int circleY = (this.getHeight() - 1 - circleL)/2;
			g.setColor(Color.GRAY);
			g.fillOval(circleX, circleY, circleL, circleL);

			//Draw the X
			final int lineL = circleL - 8;
			final int lineX = circleX + 4;
			final int lineY = circleY + 4;
			g.setColor(Color.WHITE);
			g.setStroke(new BasicStroke(2));
			g.drawLine(lineX, lineY, lineX + lineL, lineY + lineL);
			g.drawLine(lineX, lineY + lineL, lineX + lineL, lineY);
		}
	}



	private void cancelButton(){
		button.requestFocus();
		text.setValue("");
		fireSearchTextChangedEvent(new SearchTextChangedEvent(SearchField.this));
	}

	private void changed(){
		fireSearchTextChangedEvent(new SearchTextChangedEvent(SearchField.this));
	}

	public class SearchTextChangedEvent extends EventObject {
		public static final long serialVersionUID = 0;

		public SearchTextChangedEvent(Object source) {
			super(source);
		}
	}

	public interface SearchTextChangedEventListener extends EventListener {
		public void searchTextChangedEventOccurred(SearchTextChangedEvent evt);
	}

	// Create the listener list
	protected EventListenerList listenerList =
		new EventListenerList();

	// This methods allows classes to register for MyEvents
	public void addSearchTextChangedEventListener(SearchTextChangedEventListener listener) {
		listenerList.add(SearchTextChangedEventListener.class, listener);
	}

	// This methods allows classes to unregister for MyEvents
	public void removeSearchTextChangedEventListener(SearchTextChangedEventListener listener) {
		listenerList.remove(SearchTextChangedEventListener.class, listener);
	}

	// This private class is used to fire MyEvents
	void fireSearchTextChangedEvent(SearchTextChangedEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i=0; i<listeners.length; i+=2) {
			if (listeners[i]==SearchTextChangedEventListener.class) {
				((SearchTextChangedEventListener)listeners[i+1]).searchTextChangedEventOccurred(evt);
			}
		}
	}
}
