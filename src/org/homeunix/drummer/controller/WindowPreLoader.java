/*
 * Created on Apr 8, 2007 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.prefs.WindowAttributes;
import org.homeunix.drummer.view.TransactionsFrame;
import org.homeunix.thecave.moss.util.SwingWorker;

public class WindowPreLoader {
	
	private final Map<Account, SwingWorker> transactionsFrames;
	
//	public static WindowPreLoader getInstance() {
//		return SingletonHolder.instance;
//	}
//
//	private static class SingletonHolder {
//		private static WindowPreLoader instance = new WindowPreLoader();
//	}
//	
	public WindowPreLoader() {
		transactionsFrames = new HashMap<Account, SwingWorker>();
		
		for (Account a : SourceController.getAccounts()) {
			final Account account = a;
			SwingWorker worker = new SwingWorker(){
				@Override
				public Object construct() {
					WindowAttributes wa = PrefsInstance.getInstance().getPrefs().getWindows().getTransactionsWindow();
					Dimension dimension = new Dimension(wa.getWidth(), wa.getHeight());
					Point position = new Point(wa.getX(), wa.getY());

					TransactionsFrame tf = new TransactionsFrame(account);
					tf.openWindowInvisible(dimension, position);
					
					return tf;
				}
			};
			
			transactionsFrames.put(a, worker);
			worker.setPriority(Thread.MIN_PRIORITY);
			worker.start();
		}
	}
	
	public TransactionsFrame getTransactionsFrame(Account a){
		return (TransactionsFrame) transactionsFrames.get(a).get();
	}
}
